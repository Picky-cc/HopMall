package com.zufangbao.sun.handler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.entity.directbank.cmb.XmlPacket;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.ArriveRecordStatus;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;
import com.zufangbao.sun.handler.CMBDirectBankHandler;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.utils.XMLFormatUtil;

/**
 * 
 * @author zjm
 *
 */
@Component("CMBDirectBankHandler")
public class CMBDirectBankHandlerImpl implements CMBDirectBankHandler {

	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	private static final Log logger = LogFactory.getLog(CMBDirectBankHandlerImpl.class);

	private String remote_call_query_account_flow_list(Account account,
			String endDate, String startDate, Map<String, String> config) {
		String request_xml_data = create_flow_query_request_msg(account,
				startDate, endDate, config);

		return send_request(request_xml_data, config);
	}

	@Override
	public int saveNewFlow(App app, Account account, List<Map> flow) {
		int recordCount = 0;
		for (Map propDtl : flow) {
			String serial_no = propDtl.get("REFNBR").toString();
			if (is_new_transcation_record(serial_no)) {
				save_app_arrive_record(app, account, propDtl);
				recordCount ++;
			}
		}
		return recordCount;
	}

	protected Result parse_response_to_flow_list(String response_result) {
		Result result = new Result();
		if (response_result == null || "".equals(response_result)) {
			return result.fail().message("响应报文解析失败");
		}

		XmlPacket pktRsp = XmlPacket.valueOf(response_result);
		if (pktRsp == null) {
			return result.fail().message("响应报文解析失败");
		}

		if (pktRsp.isError()) {
			return result.fail().message("取账户交易明细失败：" + pktRsp.getERRMSG());
		}

		List<Map> account_flow_list = new ArrayList<Map>();
		int size = pktRsp.getSectionSize("NTQTSINFZ");
		for (int i = 0; i < size; i++) {
			Map propDtl = pktRsp.getProperty("NTQTSINFZ", i);
			account_flow_list.add(propDtl);
		}
		return result.success().data("account_flow_list", account_flow_list);
	}

	private boolean is_new_transcation_record(String serialNo) {

		List<AppArriveRecord> appArriveRecordList = appArriveRecordService
				.getArriveRecordBySerialNo(serialNo);

		return CollectionUtils.isEmpty(appArriveRecordList);
	}

	private void save_app_arrive_record(App app, Account account, Map propDtl) {
		AppArriveRecord appArriveRecord = create_app_arrive_record(app,
				account, propDtl);
		appArriveRecordService.save(appArriveRecord);
	}

	private AppArriveRecord create_app_arrive_record(App app, Account account,
			Map propDtl) {
		if (is_debit_flow(propDtl)) {
			return create_debit_flow(app, account, propDtl);
		} else {
			return create_credit_flow(app, account, propDtl);
		}
	}

	private AppArriveRecord create_debit_flow(App app, Account account,
			Map propDtl) {
		return new AppArriveRecord(app, get_key_value(propDtl, "REFNBR"),
				Constant.DEBIT, account.getAccountNo(), account
						.getAccountName().toString(), get_key_value(propDtl,
						"RPYACC"), get_key_value(propDtl, "RPYNAM"),new BigDecimal(get_key_value(propDtl,
						"TRSAMTD")), get_transaction_time(propDtl),
				ArriveRecordStatus.NotDeducted, null, get_key_value(propDtl,
						"YURREF"), get_key_value(propDtl, "NARYUR"));
	}

	private AppArriveRecord create_credit_flow(App app, Account account,
			Map propDtl) {
		return new AppArriveRecord(app, get_key_value(propDtl, "REFNBR"),
				Constant.CREDIT, get_key_value(propDtl, "RPYACC"),
				get_key_value(propDtl, "RPYNAM"), account.getAccountNo(),account.getAccountName(),
				new BigDecimal(get_key_value(propDtl, "TRSAMTC")),
				get_transaction_time(propDtl), ArriveRecordStatus.NotDeducted,
				null, get_key_value(propDtl, "YURREF"), get_key_value(propDtl,
						"NARYUR"));
	}

	private Date get_transaction_time(Map propDtl) {
		return DateUtils.parseDate(get_key_value(propDtl, "ETYDAT")
				+ get_key_value(propDtl, "ETYTIM"), "yyyyMMddHHmmss");
	}

	private String get_key_value(Map propDtl, String key) {
		return propDtl.get(key) == null ? "" : propDtl.get(key).toString();
	}

	private boolean is_debit_flow(Map propDtl) {
		return Constant.CMB_DEBIT_CODE.equals(propDtl.get("AMTCDR"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String create_flow_query_request_msg(Account account,
			String startDate, String endDate, Map<String, String> config) {
		XmlPacket xmlPkt = new XmlPacket(config.getOrDefault(
				"GetTransInfo_Code", "GetTransInfo").toString(), config.get(
				"LGNNAM").toString());
		Map mpAccInfo = new Properties();

		mpAccInfo.put("ACCNBR", account.getAccountNo());// 账号
		mpAccInfo.put("BBKNBR", account.getAttrBankBranchNo());// 分行号
		mpAccInfo.put("BGNDAT", startDate);// 开始日期
		mpAccInfo.put("ENDDAT", endDate);// 结束日期
		xmlPkt.putProperty("SDKTSINFX", mpAccInfo);// 户交易信息查询输入接口
		return xmlPkt.toXmlString();
	}

	private String send_request(String request_xml_data,
			Map<String, String> config) {
		OutputStream os = null;
		BufferedReader br = null;
		logger.info("directbank realtime query requestData:"+request_xml_data);
		try {
			URL url = new URL(config.get("URL").toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(request_xml_data.toString().getBytes("UTF-8"));

			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String response_data = "";
			String line;
			while ((line = br.readLine()) != null) {
				response_data += line;
			}
			logger.info("directbank realtime query responseData:"+response_data);
			return response_data;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != br) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private Result create_fail_result(String message) {
		Result result = new Result();
		return result.fail().message(message);
	}

	@SuppressWarnings("rawtypes")
	protected Result process_result(String response_result) {

		if (response_result == null || "".equals(response_result)) {
			return create_fail_result("响应报文解析失败");
		}

		XmlPacket pktRsp = XmlPacket.valueOf(response_result);
		if (pktRsp == null) {
			return create_fail_result("响应报文解析失败");
		}

		String sRetCod = pktRsp.getRETCOD();

		if (pay_success(sRetCod)) {
			Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
			String sREQSTS = (String) propPayResult.get("REQSTS");
			String sRTNFLG = (String) propPayResult.get("RTNFLG");
			if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
				return create_fail_result("支付失败：" + propPayResult.get("ERRTXT"));
			} else {
				Result result = new Result();
				String serialNo = (String) propPayResult.get("YURREF");
				result.success().setMessage("支付已被银行受理（支付状态：" + sREQSTS + "）! 序列号：" + serialNo);
				result.data("responseData", response_result);
				result.data("serialNo", serialNo);

				return result;
			}
		} else if ("-9".equals(sRetCod)) {
			return create_fail_result("支付未知异常，请查询支付结果确认支付状态，错误信息："
					+ pktRsp.getERRMSG());
		} else {
			return create_fail_result("支付失败：" + pktRsp.getERRMSG());
		}
	}


	private boolean pay_success(String sRetCod) {
		return Constant.CMB_SUCCESS_CODE.equals(sRetCod);
	}

	protected Result change_result_to_flow_list(Result query_result) {
		List<FlowRecord> flowRecordList = new ArrayList<FlowRecord>();
		List<Map> account_flow_list = (List<Map>) query_result.getData().get(
				"account_flow_list");
		if (account_flow_list != null) {
			for (Map propDtl : account_flow_list) {
				if (is_debit_flow(propDtl)) {
					flowRecordList.add(create_debit_flow(propDtl));
				} else {
					flowRecordList.add(create_credit_flow(propDtl));
				}
			}
		}
		query_result.getData().remove("account_flow_list");
		return query_result.data("flowList", flowRecordList);
	}

	private FlowRecord create_credit_flow(Map propDtl) {
		return new FlowRecord(Constant.CREDIT,
				propDtl.get("REFNBR").toString(), null, new BigDecimal(propDtl
						.get("TRSAMTC").toString()), propDtl.get("RPYBNK")==null?null:propDtl.get("RPYBNK")+"",
				propDtl.get("RPYACC") == null ? "" : propDtl.get("RPYACC")
						.toString(), propDtl.get("RPYNAM") == null ? null
						: propDtl.get("RPYNAM").toString(),
				propDtl.get("NARYUR") == null ? "" : propDtl.get("NARYUR")
						.toString(), null, null, DateUtils.parseDate(propDtl
						.get("ETYDAT").toString()
						+ propDtl.get("ETYTIM").toString(), "yyyyMMddHHmmss"),
				new BigDecimal(propDtl.get("TRSBLV") == null ? "0.00" : propDtl
						.get("TRSBLV").toString()),
						get_key_value(propDtl,"REFNBR"),get_key_value(propDtl,"YURREF"));
	}

	private FlowRecord create_debit_flow(Map propDtl) {
		return new FlowRecord(Constant.DEBIT, propDtl.get("REFNBR").toString(),
				new BigDecimal(propDtl.get("TRSAMTD").toString()), null, propDtl.get("RPYBNK")==null?null:propDtl.get("RPYBNK")+"",
				propDtl.get("RPYACC") == null ? "" : propDtl.get("RPYACC")
						.toString(), propDtl.get("RPYNAM") == null ? null
						: propDtl.get("RPYNAM").toString(),
				propDtl.get("NARYUR") == null ? "" : propDtl.get("NARYUR")
						.toString(), null, null, DateUtils.parseDate(propDtl
						.get("ETYDAT").toString()
						+ propDtl.get("ETYTIM").toString(), "yyyyMMddHHmmss"),
				new BigDecimal(propDtl.get("TRSBLV") == null ? "0.00" : propDtl
						.get("TRSBLV").toString()),
						get_key_value(propDtl,"REFNBR"),get_key_value(propDtl,"YURREF"));
	}

	private Result query_account_flow_list(Account account, String startDate,
			String endDate, USBKey USBKey) {
		Map<String, String> config = USBKey.getConfig();
		String response_result = remote_call_query_account_flow_list(account,
				endDate, startDate, config);
		return parse_response_to_flow_list(response_result);
	}

	@SuppressWarnings("unchecked")
	protected String create_balance_query_request_msg(Account account,
			Map<String, String> config) {

		XmlPacket xmlPkt = new XmlPacket(config.getOrDefault("GetAccInfo_Code",
				"GetAccInfo").toString(), config.get("LGNNAM").toString());
		Map mpAccInfo = new Properties();
		mpAccInfo.put("BBKNBR", account.getAttrBankBranchNo());
		mpAccInfo.put("ACCNBR", account.getAccountNo());
		xmlPkt.putProperty("SDKACINFX", mpAccInfo);
		return xmlPkt.toXmlString();

	}

	protected Result process_balance_result(String response_result) {
		Result result = new Result();
		if (null == response_result || response_result.isEmpty()) {
			return create_fail_result("响应报文解析失败");
		}

		XmlPacket pktRsp = XmlPacket.valueOf(response_result);
		if (pktRsp == null) {
			return create_fail_result("响应报文解析失败");
		}

		if (pktRsp.isError()) {
			return create_fail_result("取账户信息失败：" + pktRsp.getERRMSG());
		}

		Map propAcc = pktRsp.getProperty("NTQACINFZ", 0);

		return result.success().data(
				"balance",
				new BigDecimal(propAcc.get("ONLBLV") == null ? "0.00" : propAcc
						.get("ONLBLV").toString()));
	}

//	@Override
	public Result getPaymentInfo(String serialNo, USBKey usbKey) {
		try {
			Map<String, String> config = usbKey.getConfig();
			String request_xml_data = create_payment_info_query_request_msg(
					serialNo, config);
			String response_result = send_request(request_xml_data, config);

			return new Result().success().data("result",
					XMLFormatUtil.formatXML(response_result));

		} catch (Exception e) {
			logger.error(e.getMessage());
			return create_fail_result("系统异常，请稍后再试！");
		}
	}

	@SuppressWarnings("unchecked")
	protected String create_payment_info_query_request_msg(String serialNo,
			Map<String, String> config) {

		XmlPacket xmlPkt = new XmlPacket(config.getOrDefault(
				"GetPaymentInfo_Code", "GetPaymentInfo").toString(), config
				.get("LGNNAM").toString());
		Map mpAccInfo = new Properties();

		String endDate = DateUtils.format(new Date(), "yyyyMMdd");

		String startDate = DateUtils.format(
				DateUtils.addDays(new Date(), -Constant.CMB_DATE_RANGE),
				"yyyyMMdd");

		mpAccInfo.put("BGNDAT", startDate);
		mpAccInfo.put("ENDDAT", endDate);
		mpAccInfo.put("YURREF", serialNo);
		xmlPkt.putProperty("SDKPAYQYX", mpAccInfo);
		return xmlPkt.toXmlString();

	}

	@Override
	public Result queryTodayCashFlow(Account account, USBKey usbKey) {
		try {
			String today = DateUtils.format(new Date(), "yyyyMMdd");

			Result query_result = query_account_flow_list(account, today,
					today, usbKey);

			return change_result_to_flow_list(query_result);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return create_fail_result("系统异常，请稍后再试！");
		}
	}

	@Override
	public Result queryHistoryCashFlow(Account account, String startDate, String endDate, USBKey usbKey) {
		try {
			Result query_result = query_account_flow_list(account, startDate, endDate, usbKey);
			return change_result_to_flow_list(query_result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return create_fail_result("其他错误");
		}
	}

	@Override
	public Result queryAccountBalance(Account account, USBKey usbKey) {
		try {
			Map<String, String> config = usbKey.getConfig();
			String request_xml_data = create_balance_query_request_msg(account, config);
			String response_result = send_request(request_xml_data, config);
			return process_balance_result(response_result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return create_fail_result("系统异常，请稍后再试！");
		}
	}

	@Override
	public void scanBankCashFlow(Account account, App app, USBKey usbKey) {
		try {
			String today = DateUtils.format(new Date(), "yyyyMMdd");
			String yesterday = DateUtils.format(DateUtils.addDays(new Date(), -1), "yyyyMMdd");
			Result scan_result = query_account_flow_list(account, yesterday, today, usbKey);
			if(!scan_result.isValid()){
				logger.info("scan_result is invalid, message["+scan_result.getMessage()+"].");
				return;
			}
			saveNewFlow(app, account, (List<Map>) scan_result.getData().get("account_flow_list"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public Result recordUnsavedCashFlow(String startDate, String endDate,
			Account account, App app, USBKey usbKey) {
		try {
			Result scan_result = query_account_flow_list(account, startDate, endDate, usbKey);
			
			List<Map> result_list = (List<Map>) scan_result.getData().get("account_flow_list");

			int recordCount = saveNewFlow(app, account, result_list);
			
			return new Result().success().data("totalCount", result_list.size()).data("recordCount", recordCount);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return create_fail_result("系统异常，请稍后再试！");
		}
	}

}
