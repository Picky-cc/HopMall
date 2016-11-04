package com.suidifu.coffer.handler.cmb.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.suidifu.coffer.GlobalSpec.BankCorpEps;
import com.suidifu.coffer.GlobalSpec.ErrorMsg;
import com.suidifu.coffer.entity.BusinessProcessStatus;
import com.suidifu.coffer.entity.BusinessRequestStatus;
import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.CreditModel;
import com.suidifu.coffer.entity.CreditResult;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.entity.QueryCreditModel;
import com.suidifu.coffer.entity.QueryCreditResult;
import com.suidifu.coffer.entity.cmb.BankCodeMapSpec;
import com.suidifu.coffer.entity.cmb.CMBXmlPacket;
import com.suidifu.coffer.entity.cmb.CityCodeMapSpec;
import com.suidifu.coffer.exception.RequestDataException;
import com.suidifu.coffer.exception.ResponseParseException;
import com.suidifu.coffer.handler.cmb.CMBDirectBankPacketHandler;
@Component("cmbDirectBankPacketHandler")
public class CMBDirectBankPacketHandlerImpl implements
		CMBDirectBankPacketHandler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String generateSingleCreditPacket(CreditModel creditModel,
			Map<String, String> workParms) throws RequestDataException {
		
		if (null == creditModel) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_CREDITMODEL);
		}
		if (null == workParms) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_WORKPARMS);
		}
		
		CMBXmlPacket xmlPkt = new CMBXmlPacket(BankCorpEps.CMB_CREDIT_CODE, workParms.getOrDefault("LoginName", StringUtils.EMPTY).toString());
		Map mpModInfo = new Properties();
		mpModInfo.put("BUSCOD", workParms.getOrDefault("BusinessCode", BankCorpEps.CMB_DEFAULT_BUSCOD));
		mpModInfo.put("BUSMOD", workParms.getOrDefault("BusinessMode", BankCorpEps.CMB_DEFAULT_BUSMOD));
		xmlPkt.putProperty("SDKPAYRQX", mpModInfo);
		
		Map mpPayInfo = new Properties();
		mpPayInfo.put("YURREF", creditModel.getTransactionVoucherNo());// 业务参考号
		mpPayInfo.put("DBTACC", creditModel.getSourceAccountNo());// 付方账号
		mpPayInfo.put("DBTBBK", creditModel.getSourceBankInfo().getOrDefault("CityCode", StringUtils.EMPTY));// 付方分行代码 //TODO
		mpPayInfo.put("TRSAMT", creditModel.getTransactionAmount());// 交易金额
		mpPayInfo.put("CCYNBR", workParms.getOrDefault("CurrencyCode", BankCorpEps.CMB_DEFAULT_CURRENCY));// 币种代码
		mpPayInfo.put("STLCHN", workParms.getOrDefault("SettleMode", BankCorpEps.CMB_DEFAULT_SETTLE));// 结算方式代码
		mpPayInfo.put("NUSAGE", creditModel.getPostscript());// 用途
		mpPayInfo.put("BUSNAR", creditModel.getPostscript());// 业务摘要
		mpPayInfo.put("CRTACC", creditModel.getDestinationAccountNo());// 收方账号
		mpPayInfo.put("CRTNAM", creditModel.getDestinationAccountName());// 收方账户名
		
		String destinationBankCode = creditModel.getDestinationBankInfo().getOrDefault("bankCode", StringUtils.EMPTY);
		mpPayInfo.put("BRDNBR", BankCodeMapSpec.BANK_CODE_MAP.getOrDefault(destinationBankCode, StringUtils.EMPTY));// 人行 收方联行号
		
		String sourceBankCode = creditModel.getSourceBankInfo().getOrDefault("BankCode", StringUtils.EMPTY);//TODO
		
		mpPayInfo.put("BNKFLG", sourceBankCode.equalsIgnoreCase(destinationBankCode) ? BankCorpEps.CMB_SAMEBANK_CODE : BankCorpEps.CMB_INTERBANK_CODE);// 系统内外标志
		mpPayInfo.put("CRTBNK", creditModel.getDestinationBankInfo().getOrDefault("bankName", StringUtils.EMPTY));// 收方开户行
		//mpPayInfo.put("CTYCOD", creditModel.getDestinationBankInfo().getOrDefault("CityCode", StringUtils.EMPTY));// 城市代码 //TODO
		String provinceCode = creditModel.getDestinationBankInfo().getOrDefault("ProvinceName", StringUtils.EMPTY);
		String cityCode = creditModel.getDestinationBankInfo().getOrDefault("CityName", StringUtils.EMPTY);
		mpPayInfo.put("CRTADR", CityCodeMapSpec.PROVINCE_CODE_MAP.getOrDefault(provinceCode, StringUtils.EMPTY) + CityCodeMapSpec.CITY_CODE_MAP.getOrDefault(cityCode, StringUtils.EMPTY));// 收方行地址
		
		xmlPkt.putProperty("DCOPDPAYX", mpPayInfo);
		
		return xmlPkt.toXmlString();
	}

	@Override
	public String generateBatchCreditPacket(List<CreditModel> creditModelList,
			Map<String, String> config) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public CreditResult parseSingleCreditPacket(String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		CMBXmlPacket pktRsp = parsePacket(responsePacket);
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(ErrorMsg.ERR_TRANSACTION_ANOMALY + pktRsp.getERRMSG());
		}
		
		Map resultDetail = pktRsp.getProperty("NTQPAYRQZ", 0);
		
		if(null == resultDetail) {
			throw new ResponseParseException(ErrorMsg.ERR_TRANSACTION_ANOMALY + pktRsp.getERRMSG());
		}
		
		CreditResult creditedResult = new CreditResult();
		
		creditedResult.setTransactionVoucherNo(resultDetail.getOrDefault("YURREF", StringUtils.EMPTY).toString());
		creditedResult.setRequestStatus(BusinessRequestStatus.INPROCESS);
		
		return creditedResult;
	}

	@Override
	public List<CreditResult> parseBatchCreditPacket(String responseResult)
			throws ResponseParseException {
		// TODO Auto-generated method stub
		return null;
	}


	private CMBXmlPacket parsePacket(String responsePacket) throws ResponseParseException {
		if(StringUtils.isEmpty(responsePacket)) {
			throw new ResponseParseException(ErrorMsg.ERR_PARSE_PACKET);
		}
		
		CMBXmlPacket pktRsp = CMBXmlPacket.valueOf(responsePacket);
		
		if(null == pktRsp) {
			throw new ResponseParseException(ErrorMsg.ERR_PARSE_PACKET);
		}
		return pktRsp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String generateQueryCreditPacket(QueryCreditModel queryCreditModel, Map<String, String> workParms) throws RequestDataException {
		if(null == queryCreditModel) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_QUERYCREDITMODEL);
		}
		if(null == workParms) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_WORKPARMS);
		}
		
		CMBXmlPacket xmlPkt = new CMBXmlPacket(BankCorpEps.CMB_QUERYCREDIT_CODE, workParms.getOrDefault("LoginName", StringUtils.EMPTY).toString());
		
		Map mpQueryInfo = new Properties();
		String startDate = queryCreditModel.getBeginDate();
		String endDate = queryCreditModel.getEndDate();
		mpQueryInfo.put("BGNDAT", startDate);
		mpQueryInfo.put("ENDDAT", endDate);
		mpQueryInfo.put("YURREF", queryCreditModel.getTransactionVoucherNo());
		
		xmlPkt.putProperty("SDKPAYQYX", mpQueryInfo);
		
		return xmlPkt.toXmlString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public QueryCreditResult parseQueryCreditPacket(String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		CMBXmlPacket pktRsp = parsePacket(responsePacket);
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(ErrorMsg.ERR_QUERY_ANOMALY + pktRsp.getERRMSG());
		}
		
		Map resultDetail = pktRsp.getProperty("NTQPAYQYZ", 0);
		
		QueryCreditResult queryCreditResult = new QueryCreditResult();
		if(null == resultDetail) {
			queryCreditResult.setRequestStatus(BusinessRequestStatus.NOTRECEIVE);
			return queryCreditResult;
		}
		queryCreditResult.setSourceAccountNo(resultDetail.getOrDefault("DBTACC", StringUtils.EMPTY).toString());
		queryCreditResult.setSourceAccountName(resultDetail.getOrDefault("DBTNAM", StringUtils.EMPTY).toString());
		queryCreditResult.setDestinationAccountNo(resultDetail.getOrDefault("CRTACC", StringUtils.EMPTY).toString());
		queryCreditResult.setTransactionAmount(resultDetail.getOrDefault("TRSAMT", BigDecimal.ZERO).toString());
		//queryCreditResult.setOperateDate(resultDetail.getOrDefault("OPRDAT", StringUtils.EMPTY).toString());//TODO
		queryCreditResult.setTransactionVoucherNo(resultDetail.getOrDefault("YURREF", StringUtils.EMPTY).toString());
		
		String requestStatus = resultDetail.getOrDefault("REQSTS", StringUtils.EMPTY).toString();
		if(! BankCorpEps.CMB_REQUEST_STATUS_FIN.equals(requestStatus)) {
			queryCreditResult.setRequestStatus(BusinessRequestStatus.INPROCESS);
			return queryCreditResult;
		}
		String processStatus = resultDetail.getOrDefault("RTNFLG", StringUtils.EMPTY).toString();
		if(BankCorpEps.CMB_PROCESS_STATUS_SUC.equals(processStatus)) {
			queryCreditResult.setProcessStatus(BusinessProcessStatus.SUCCESS);
		}else {
			queryCreditResult.setProcessStatus(BusinessProcessStatus.FAIL);
		}
		
		return queryCreditResult;
	}

	@Override
	public String generateQueryIntradayCashFlowPacket(
			QueryCashFlowModel queryCashFlowModel, Map<String, String> workParms)
			throws RequestDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CashFlowResultModel parseQueryIntradayCashFlowPacket(
			String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
