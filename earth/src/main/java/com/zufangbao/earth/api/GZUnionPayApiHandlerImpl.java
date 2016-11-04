package com.zufangbao.earth.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.entity.Result;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg.ErrMSg4DirectBank;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.api.exception.UnknownUkeyException;
import com.zufangbao.earth.api.xml.BatchPaymentProcessingResultXml;
import com.zufangbao.earth.api.xml.BatchPaymentReqXml;
import com.zufangbao.earth.api.xml.BatchPaymentRtnXml;
import com.zufangbao.earth.api.xml.PaymentDetail;
import com.zufangbao.earth.api.xml.TransactionDetail;
import com.zufangbao.earth.api.xml.TransactionDetailQueryParams;
import com.zufangbao.earth.api.xml.TransactionDetailQueryReqXml;
import com.zufangbao.earth.api.xml.TransactionQueryRtnXml;
import com.zufangbao.earth.api.xml.TransactionStatusQueryReqXml;
import com.zufangbao.earth.api.xml.TransactionStatusQueryRspDetailXml;
import com.zufangbao.earth.api.xml.TransactionStatusQueryRspXml;
import com.zufangbao.earth.api.xml.XmlPacketRtnInfo;
import com.zufangbao.earth.util.XmlUtil;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailNode;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.GateWay;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.FlowService;
import com.zufangbao.sun.service.PaymentChannelService;

@Component("GZUnionPayApiHandler")
public class GZUnionPayApiHandlerImpl implements GZUnionPayApiHandler {

	@Autowired
	private IGZUnionPayApiComponent igzUnionPayApiComponent;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RequestRecordService requestRecordService;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private CMBSuperBankPaymentAndQuery cmbSuperBankPaymentAndQuery;
	
	@Autowired
	private UnionpayPaymentAndQuery unionpayPaymentAndQuery;
	
	@Autowired
	private PaymentChannelService paymentChannelService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private CMBSuperBankHandler cmbSuperBankHandler;
	
	@Override
	public String queryTransactionDetail(String reqPacketXml) {
		try {
			XStream xStream = XmlUtil.xStream();
			xStream.alias("SDKPGK", TransactionDetailQueryReqXml.class);
			StringBuffer rtnXml = new StringBuffer();
			rtnXml.append(XmlUtil.XML_Head);
			TransactionQueryRtnXml transactionQueryRtnXml = new TransactionQueryRtnXml();
			XmlPacketRtnInfo xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY,RTNCode.SUC+"","");
			List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();
			try {
				
				TransactionDetailQueryReqXml transactionDetailQueryReqXml = (TransactionDetailQueryReqXml)xStream.fromXML(reqPacketXml);
				if(transactionDetailQueryReqXml==null){
					throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_XML_FORMAT);
				}
				transactionDetailQueryReqXml.validNodes();
				TransactionDetailQueryParams transactionDetailQueryParams = transactionDetailQueryReqXml.getTransactionDetailQueryParams();
				// TODO refactor
				if(GateWay.UnionPay==transactionDetailQueryReqXml.getGateWay()){
					transactionDetailList = execTransactionDetailQuery(transactionDetailQueryParams);
				} else if(GateWay.DirectBank==transactionDetailQueryReqXml.getGateWay()) {
					transactionDetailList = execDirectBankTransactionDetailQuery(transactionDetailQueryParams);
				} else if(GateWay.PABDirectBank == transactionDetailQueryReqXml.getGateWay()) {
					transactionDetailList = execDirectBankTransactionDetailFromDB(transactionDetailQueryParams);
				}else{
					throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMsg.ERR_GATEWAY_UNAVAILABLE);
				}
			}catch(ConversionException e){
				xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY,RTNCode.WRONG_FORMAT+"",ErrMsg.ERR_XML_FORMAT);
			}catch(CannotResolveClassException e){
				e.printStackTrace();
				xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY,RTNCode.WRONG_FORMAT+"",ErrMsg.ERR_XML_FORMAT);
			}catch(TransactionDetailApiException e){
				xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY,e.getCode()+"",e.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY,RTNCode.OTHER_ERROR+"",ErrMsg.SYSTEM_ERROR);
			} finally{
				transactionQueryRtnXml.setXmlPacketInfo(xmlPacketRtnInfo);
				transactionQueryRtnXml.setTransactionDetailList(transactionDetailList);
				String xmlBody = xStream.toXML(transactionQueryRtnXml); 
				rtnXml.append(xmlBody);
			}
			return rtnXml.toString();
		} catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public String executeBatchPayment(String reqXmlPacket) {
		XStream xStream = XmlUtil.xStream();
		xStream.alias("SDKPGK", BatchPaymentReqXml.class);
		StringBuffer rtnXml = new StringBuffer();
		rtnXml.append(XmlUtil.XML_Head);
		BatchPaymentRtnXml batchPaymentRtnXml = new BatchPaymentRtnXml();
		XmlPacketRtnInfo xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_BATCH_PAYMENT, RTNCode.SUC + "", "");
		List<BatchPaymentProcessingResultXml> reee = new ArrayList<BatchPaymentProcessingResultXml>();
		try {
			BatchPaymentReqXml batchPaymentReqXml = (BatchPaymentReqXml) xStream.fromXML(reqXmlPacket);
			if (batchPaymentReqXml == null) {
				throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_XML_FORMAT);
			}
			batchPaymentReqXml.validNodes();
			List<PaymentDetail> paymentDetailList = batchPaymentReqXml.getPaymentDetailList();
			if(requestRecordService.isReqNoExist(paymentDetailList.get(0).getReqNo())) {
				throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMsg.ERR_REPEAT_YURREF);
			}
			if(!(CollectionUtils.isEmpty(paymentDetailList)) && "C10308".equals(paymentDetailList.get(0).getBeneficiaryBankNo()) && "0001".equals(batchPaymentReqXml.getXmlPacketGateWayMode().getGateWay())) {
				String yurref = paymentDetailList.get(0).getReqNo();
				requestRecordService.save(new RequestRecord(GateWay.SuperBank, FunctionName.FUNC_BATCH_PAYMENT,
						yurref, UUID.randomUUID().toString().replace("-", "").substring(2), reqXmlPacket, null));
				return "<?xml version='1.0' encoding='UTF-8'?><SDKPGK><INFO><FUNNAM>NTIBCOPR</FUNNAM><RETCOD>0</RETCOD><ERRMSG></ERRMSG></INFO><NTOPRRTNZ><YURREF>"+ yurref +"</YURREF><RTNFLG>BNK</RTNFLG><ERRCOD>SUC0000</ERRCOD><ERRTXT></ERRTXT></NTOPRRTNZ></SDKPGK>";
			}

			if(batchPaymentReqXml.isSuperBank()){
				reee = cmbSuperBankPaymentAndQuery.execBatchPayment(batchPaymentReqXml, reqXmlPacket);
			}else if(batchPaymentReqXml.isUnionPay()){

				reee = unionpayPaymentAndQuery.execBatchPayment(batchPaymentReqXml, reqXmlPacket);
			}
		} catch (TransactionDetailApiException e) {
			e.printStackTrace();
			xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_BATCH_PAYMENT,e.getCode()+"",e.getMessage());
		} catch (UnknownUkeyException e) {
			e.printStackTrace();
			xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_BATCH_PAYMENT, RTNCode.OTHER_ERROR + "", ErrMsg.ERR_Account);
		} catch (ResponseParseException e) {
			e.printStackTrace();
			xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_BATCH_PAYMENT, RTNCode.OTHER_ERROR + "", ErrMsg.SYSTEM_ERROR);
		} catch(Exception e){
			e.printStackTrace();
			xmlPacketRtnInfo = new XmlPacketRtnInfo(FunctionName.FUNC_BATCH_PAYMENT, RTNCode.OTHER_ERROR + "", ErrMsg.SYSTEM_ERROR);
		} finally {
			batchPaymentRtnXml.setExecResultList(reee);
			batchPaymentRtnXml.setXmlPacketInfo(xmlPacketRtnInfo);
			String xmlBody = xStream.toXML(batchPaymentRtnXml); 
			rtnXml.append(xmlBody);
		}
		return rtnXml.toString();
	}
	
	
	@Override
	public List<TransactionDetail> execTransactionDetailQuery(TransactionDetailQueryParams transactionDetailQueryParams) throws TransactionDetailApiException {
		transactionDetailQueryParams.validForUnionpay();
		TransactionDetailQueryInfoModel queryUnionModel = convert(transactionDetailQueryParams, PaymentChannelType.GuangdongUnionPay);
		TransactionDetailQueryResult transactionDetailQueryResult = igzUnionPayApiComponent.execTransactionDetailQuery(queryUnionModel);
		List<TransactionDetailNode> transactionDetailNodeList = transactionDetailQueryResult.getDetailNodes();
		List<TransactionDetail> transactionDetailList = filterReckonAccountAndGetTransactionDetailList(transactionDetailNodeList,transactionDetailQueryParams.getReckonAccount());
		return transactionDetailList;
	}
	private List<TransactionDetail> filterReckonAccountAndGetTransactionDetailList(List<TransactionDetailNode> transactionDetailNodeList,String reckonAccount) {
		if(CollectionUtils.isEmpty(transactionDetailNodeList)){
			return Collections.emptyList();
		}
		List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();
		transactionDetailNodeList.stream().filter(
				transactionDetailNode -> StringUtils.isEmpty(reckonAccount)?true:StringUtils.equals(transactionDetailNode.getReckonAccount(), reckonAccount)
			).forEach(
				transactionDetailNode -> {
					TransactionDetail transactionDetail = TransactionDetail.createUnionTransactionDetail(transactionDetailNode);
					transactionDetailList.add(transactionDetail);
				}
			);
		return transactionDetailList;
		
	}
	@Override
	public TransactionDetailQueryInfoModel convert(TransactionDetailQueryParams transactionDetailOutQueryParams, PaymentChannelType paymentChannelType) throws TransactionDetailApiException {
		PaymentChannel paymentChannel = financialContractService.getPaymentChannel(transactionDetailOutQueryParams.getAccountNo(), transactionDetailOutQueryParams.getMerId(), paymentChannelType);
		if(paymentChannel==null){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMsg.ERR_MSG_NO_MERID_ACCOUNTNO);
		}
		TransactionDetailQueryInfoModel transactionDetailQueryInfoModel = new TransactionDetailQueryInfoModel(paymentChannel,
				transactionDetailOutQueryParams.getBeginDate(), transactionDetailOutQueryParams.getEndDate(), "", "");
		return transactionDetailQueryInfoModel;
	}

	
	@Override
	public List<TransactionDetail> execDirectBankTransactionDetailQuery(TransactionDetailQueryParams transactionDetailQueryParams)
			throws TransactionDetailApiException {
		transactionDetailQueryParams.validForDirectBank();
		
		Account account = accountService.getAccountByAccountNo(transactionDetailQueryParams.getAccountNo());
		if(account==null){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMSg4DirectBank.ERR_MSG_NO_ACCOUNT);
		}
		Result result = flowService.getHistoryFlowsByAccount(account, transactionDetailQueryParams.getBeginDate(), transactionDetailQueryParams.getEndDate());
		if(!result.isValid()){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,result.getMessage());
		}
		// TODO flowList to spec
		List<FlowRecord> flowRecords = (List<FlowRecord>)result.get("flowList");
		List<TransactionDetail> transactionDetailList = convertToTransactionDetailList(flowRecords);
		return transactionDetailList;
	}
	
	private List<TransactionDetail> execDirectBankTransactionDetailFromDB(TransactionDetailQueryParams transactionDetailQueryParams)
			throws TransactionDetailApiException {
		transactionDetailQueryParams.validForDirectBank();
		
		Account account = accountService.getAccountByAccountNo(transactionDetailQueryParams.getAccountNo());
		if(account==null){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMSg4DirectBank.ERR_MSG_NO_ACCOUNT);
		}
		List<FlowRecord> flowRecords = flowService.getHistoryFlowsByAccountFromDB(account, transactionDetailQueryParams.get_begin_date_date(), transactionDetailQueryParams.get_end_date_date());
		
		List<TransactionDetail> transactionDetailList = convertToTransactionDetailList(flowRecords);
		return transactionDetailList;
	}

	private List<TransactionDetail> convertToTransactionDetailList(List<FlowRecord> flowRecords) {
		if(CollectionUtils.isEmpty(flowRecords)){
			return Collections.emptyList();
		}
		List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();
		for (FlowRecord flowRecord : flowRecords) {
			if(flowRecord==null){
				continue;
			}
			TransactionDetail transactionDetail = TransactionDetail.createDirectBankTransactionDetail(flowRecord);
			transactionDetailList.add(transactionDetail);
		}
		return transactionDetailList;
	}

	//交易查询接口
	@Override
	public String execTransactionResultQuery(String reqXmlPacket) {
		XStream xStream = XmlUtil.xStream();
		xStream.alias("SDKPGK", TransactionStatusQueryReqXml.class);
		StringBuffer rtnXml = new StringBuffer();
		rtnXml.append(XmlUtil.XML_Head);
		
		TransactionStatusQueryRspXml repXml = new TransactionStatusQueryRspXml();
		XmlPacketRtnInfo xmlPacketInfo = new XmlPacketRtnInfo(FunctionName.FUNC_TRANSACTION_STATUS_QUERY, RTNCode.SUC+"", "");
		TransactionStatusQueryRspDetailXml detail = new TransactionStatusQueryRspDetailXml();
		
		try {
			TransactionStatusQueryReqXml reqXml = (TransactionStatusQueryReqXml) xStream.fromXML(reqXmlPacket);
			String queryReqNo = reqXml.getQueryBody().getReqNo();
			
			List<RequestRecord> requestRecords = requestRecordService.getRequestRecordsBy(queryReqNo);
			if(requestRecords.isEmpty()){
				throw new Exception("未查询到交易号为"+queryReqNo+"的记录");
			}
			RequestRecord record = requestRecords.get(0);
			String queryTransactionUuid = record.getTransactionUuid();
			if(record.isSuperBank()){ // 超级银行
				
				detail = cmbSuperBankPaymentAndQuery.execTransactionQuery(record);
				
			}else if(record.isUnionpay()){ // 银联接口
				
				detail = unionpayPaymentAndQuery.execTransactionQuery(record);
				
			}
		
		}catch (Exception e){
			xmlPacketInfo.setErrMSG(e.getMessage());
			xmlPacketInfo.setRetCode(RTNCode.OTHER_ERROR+"");
		}finally {
			repXml.setXmlPacketInfo(xmlPacketInfo);
			repXml.setDetail(detail);
			String xmlBody = xStream.toXML(repXml); 
			rtnXml.append(xmlBody);
		}
		return rtnXml.toString();
	}

}
