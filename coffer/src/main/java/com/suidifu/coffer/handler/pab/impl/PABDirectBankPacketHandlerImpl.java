package com.suidifu.coffer.handler.pab.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo2do.core.utils.DateUtils;
import com.suidifu.coffer.GlobalSpec.BankCorpEps;
import com.suidifu.coffer.GlobalSpec.ErrorMsg;
import com.suidifu.coffer.TransactionErrCodeSpec;
import com.suidifu.coffer.entity.AccountSide;
import com.suidifu.coffer.entity.BusinessProcessStatus;
import com.suidifu.coffer.entity.BusinessRequestStatus;
import com.suidifu.coffer.entity.CashFlowResult;
import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.CreditModel;
import com.suidifu.coffer.entity.CreditResult;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.entity.QueryCreditModel;
import com.suidifu.coffer.entity.QueryCreditResult;
import com.suidifu.coffer.entity.cmb.BankCodeMapSpec;
import com.suidifu.coffer.entity.pab.CommHeader;
import com.suidifu.coffer.entity.pab.PABPacket;
import com.suidifu.coffer.exception.RequestDataException;
import com.suidifu.coffer.exception.ResponseParseException;
import com.suidifu.coffer.handler.pab.PABDirectBankPacketHandler;
@Component("pabDirectBankPacketHandler")
public class PABDirectBankPacketHandlerImpl implements
		PABDirectBankPacketHandler {

	@Override
	public String generateSingleCreditPacket(CreditModel creditModel,
			Map<String, String> workParms) throws RequestDataException {
		if (null == creditModel) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_CREDITMODEL);
		}
		if (null == workParms) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_WORKPARMS);
		}
		PABPacket pkt = new PABPacket();
		Map<String, String> requestData = new HashMap<String, String>();
		
		requestData.put("ThirdVoucher", creditModel.getTransactionVoucherNo());//转账凭证号
		requestData.put("CcyCode", workParms.getOrDefault("ccyCode", BankCorpEps.PAB_DEFAULT_CURRENCY));//货币类型
		requestData.put("OutAcctNo", creditModel.getSourceAccountNo());//付款人账户
		requestData.put("OutAcctName", creditModel.getSourceAccountName());//付款人名称
		
		String destinationBankCode = creditModel.getDestinationBankInfo().getOrDefault("bankCode", StringUtils.EMPTY);
		String destinationBankInfo = BankCodeMapSpec.BANK_CODE_MAP.getOrDefault(destinationBankCode, StringUtils.EMPTY);
		
		String bankCode = destinationBankInfo.isEmpty() ? StringUtils.EMPTY : destinationBankInfo.substring(0, 12);
		String bankName = destinationBankInfo.isEmpty() ? StringUtils.EMPTY : destinationBankInfo.substring(12);
		
		requestData.put("InAcctBankNode", bankCode);//收款人开户行行号，跨行转账建议必输，为人行登记在册的商业银行号
		requestData.put("InAcctRecCode", bankCode);//同上
		requestData.put("InAcctNo", creditModel.getDestinationAccountNo());//收款人账户
		requestData.put("InAcctName", creditModel.getDestinationAccountName());//收款人账户户名
		requestData.put("InAcctBankName", bankName);//收款人开户行名称  格式：xxx银行
		requestData.put("InAcctProvinceCode", creditModel.getDestinationBankInfo().getOrDefault("provinceCode", StringUtils.EMPTY));//收款账户银行开户省代码或省名称,建议跨行转账输入
		requestData.put("InAcctCityName", creditModel.getDestinationBankInfo().getOrDefault("cityCode", StringUtils.EMPTY));//收款账户开户市,建议跨行转账输入
		requestData.put("TranAmount", creditModel.getTransactionAmount());//转出金额
		requestData.put("UseEx", creditModel.getPostscript());//资金用途
		
		String sourceBankCode = creditModel.getSourceBankInfo().getOrDefault("bankCode", StringUtils.EMPTY);
		requestData.put("UnionFlag", sourceBankCode.equalsIgnoreCase(destinationBankCode) ? BankCorpEps.PAB_SAMEBANK_CODE : BankCorpEps.PAB_INTERBANK_CODE);//行内跨行标志  1：行内转账，0：跨行转账
		
		requestData.put("SysFlag", workParms.getOrDefault("sysFlag", BankCorpEps.PAB_DEFAULT_MODE));//转账加急标志  ‘1’—大额 ，等同Y ‘2’—小额”等同N
		requestData.put("AddrFlag", BankCorpEps.PAB_DEFAULT_ADDRFLAG);//同城/异地标志  “1”—同城   “2”—异地
		
		pkt.toXmlString(workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING).toString(), requestData);
		CommHeader commHeader = new CommHeader();
		commHeader.iniForCredit(workParms,calBodyLength(pkt.getBody(),workParms), creditModel.getRequestDate());

		pkt.setHeader(commHeader);
		return pkt.assemble();
	}
	
	private String calBodyLength(String xmlBody, Map<String, String> config) {
		try {
			if(StringUtils.isEmpty(xmlBody)){
				return BankCorpEps.PAB_DEFAULT_BODY_LENGTH;
			}
			return String.format("%010d", xmlBody.getBytes(config.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING)).length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return BankCorpEps.PAB_DEFAULT_BODY_LENGTH;
		}
	}

	@Override
	public String generateBatchCreditPacket(List<CreditModel> creditModelList,
			Map<String, String> workParms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreditResult parseSingleCreditPacket(String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		PABPacket pktRsp = parsePacket(responsePacket, workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING));
		if(pktRsp.isError()) {
			String errCode = pktRsp.getRetCode();
			if(TransactionErrCodeSpec.ERR_CODE_MAP.containsKey(errCode)) {
				return new CreditResult(errCode, pktRsp.getRetMsg());
			}
			throw new ResponseParseException(ErrorMsg.ERR_TRANSACTION_ANOMALY + pktRsp.getRetMsg());
		}
		Map<String, String> resultDetail = pktRsp.extractSingleResultDetail();
		if(resultDetail.isEmpty()) {
			throw new ResponseParseException(ErrorMsg.ERR_TRANSACTION_ANOMALY + pktRsp.getRetMsg());
		}
		
		CreditResult creditedResult = new CreditResult();
		creditedResult.setTransactionVoucherNo(resultDetail.getOrDefault("ThirdVoucher", StringUtils.EMPTY));
		creditedResult.setBankSequenceNo(resultDetail.getOrDefault("FrontLogNo", StringUtils.EMPTY));
		creditedResult.setTransactionAmount(resultDetail.getOrDefault("TranAmount", StringUtils.EMPTY));
		creditedResult.setRequestStatus(BusinessRequestStatus.INPROCESS);
		return creditedResult;
	}
	
	private PABPacket parsePacket(String responsePacket, String encoding) throws ResponseParseException {
		if(StringUtils.isEmpty(responsePacket)) {
			throw new ResponseParseException(ErrorMsg.ERR_PARSE_PACKET);
		}
		
		PABPacket pktRsp = PABPacket.valueOf(responsePacket, encoding);
		
		if(null == pktRsp) {
			throw new ResponseParseException(ErrorMsg.ERR_PARSE_PACKET);
		}
		return pktRsp;
	}

	@Override
	public List<CreditResult> parseBatchCreditPacket(String responsePacket)
			throws ResponseParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateQueryCreditPacket(QueryCreditModel queryCreditModel,
			Map<String, String> workParms) throws RequestDataException {
		if(null == queryCreditModel) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_QUERYCREDITMODEL);
		}
		if(null == workParms) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_WORKPARMS);
		}
		PABPacket pkt = new PABPacket();
		Map<String, String> requestData = new HashMap<String, String>();
		
		requestData.put("OrigThirdVoucher", queryCreditModel.getTransactionVoucherNo());//转账凭证号
		
		pkt.toXmlString(workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING).toString(), requestData);
		CommHeader commHeader = new CommHeader();
		commHeader.iniForQueryCredit(workParms,calBodyLength(pkt.getBody(),workParms), queryCreditModel.getRequestDate());

		pkt.setHeader(commHeader);
		return pkt.assemble();
	}

	@Override
	public QueryCreditResult parseQueryCreditPacket(String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		QueryCreditResult queryCreditResult = new QueryCreditResult();
		
		PABPacket pktRsp = parsePacket(responsePacket, workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING));
		if(BankCorpEps.PAB_NOTRECEIVE_RETCODE.equals(pktRsp.getRetCode())) {
			queryCreditResult.setRequestStatus(BusinessRequestStatus.NOTRECEIVE);
			return queryCreditResult;
		}
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(ErrorMsg.ERR_QUERY_ANOMALY + pktRsp.getRetMsg());
		}
		
		Map<String, String> resultDetail = pktRsp.extractSingleResultDetail();
		if(resultDetail.isEmpty()) {
			queryCreditResult.setRequestStatus(BusinessRequestStatus.INPROCESS);
			return queryCreditResult;
		}
		
		queryCreditResult.setTransactionVoucherNo(resultDetail.getOrDefault("OrigThirdVoucher", StringUtils.EMPTY));
		queryCreditResult.setChannelSequenceNo(resultDetail.getOrDefault("FrontLogNo", StringUtils.EMPTY));
		queryCreditResult.setSourceAccountNo(resultDetail.getOrDefault("OutAcctNo", StringUtils.EMPTY));
		queryCreditResult.setDestinationAccountNo(resultDetail.getOrDefault("InAcctNo", StringUtils.EMPTY));
		queryCreditResult.setDestinationAccountName(resultDetail.getOrDefault("InAcctName", StringUtils.EMPTY));
		queryCreditResult.setTransactionAmount(resultDetail.getOrDefault("TranAmount", StringUtils.EMPTY));
		
		queryCreditResult.setRequestStatus(BusinessRequestStatus.FINISH);

		String processStatus = resultDetail.getOrDefault("Stt", StringUtils.EMPTY);
		if(BankCorpEps.PAB_PROCESS_STATUS_SUC.equals(processStatus)) {
			queryCreditResult.setBusinessResultMsg(resultDetail.getOrDefault("Yhcljg", StringUtils.EMPTY));
			queryCreditResult.setProcessStatus(BusinessProcessStatus.SUCCESS);
			return queryCreditResult;
		}
		if(BankCorpEps.PAB_PROCESS_STATUS_FAI.equals(processStatus)) {
			queryCreditResult.setBusinessResultMsg(resultDetail.getOrDefault("BackRem", StringUtils.EMPTY));
			queryCreditResult.setProcessStatus(BusinessProcessStatus.FAIL);
			return queryCreditResult;
		}
		queryCreditResult.setProcessStatus(BusinessProcessStatus.INPROCESS);
		return queryCreditResult;
	}

	@Override
	public String generateQueryIntradayCashFlowPacket(
			QueryCashFlowModel queryCashFlowModel, Map<String, String> workParms)
			throws RequestDataException {
		if(null == queryCashFlowModel) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_QUERYCASHFLOWMODEL);
		}
		if(null == workParms) {
			throw new RequestDataException(ErrorMsg.ERR_NULL_WORKPARMS);
		}
		PABPacket pkt = new PABPacket();
		Map<String, String> requestData = new HashMap<String, String>();
		
		requestData.put("AcctNo", queryCashFlowModel.getQueryAccountNo());
		requestData.put("CcyCode", workParms.getOrDefault("ccyCode", BankCorpEps.PAB_DEFAULT_CURRENCY));
		requestData.put("BeginDate", queryCashFlowModel.getBeginDate());
		requestData.put("EndDate", queryCashFlowModel.getEndDate());
		requestData.put("PageNo", "1");
		requestData.put("PageSize", workParms.getOrDefault("pageSize", BankCorpEps.PAB_DEFAULT_PAGESIZE));
		
		pkt.toXmlString(workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING).toString(), requestData);
		CommHeader commHeader = new CommHeader();
		commHeader.iniForQueryCashFlow(workParms,calBodyLength(pkt.getBody(),workParms), queryCashFlowModel.getRequestDate());

		pkt.setHeader(commHeader);
		return pkt.assemble();
	}

	@SuppressWarnings("serial")
	@Override
	public CashFlowResultModel parseQueryIntradayCashFlowPacket(
			String responsePacket, Map<String, String> workParms)
			throws ResponseParseException {
		CashFlowResultModel cashFlowResultModel = new CashFlowResultModel();
		List<CashFlowResult> cashFlowResultList = new ArrayList<CashFlowResult>();
		
		PABPacket pktRsp = parsePacket(responsePacket, workParms.getOrDefault("encoding", BankCorpEps.PAB_DEFAULT_ENCODING));
		
		if(BankCorpEps.PAB_EMPTYDETAIL_RETCODE.equals(pktRsp.getRetCode())) {
			return cashFlowResultModel;
		}
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(ErrorMsg.ERR_QUERY_ANOMALY + pktRsp.getRetMsg());
		}
		
		List<Map<String, String>> resultDetails = pktRsp.extractMultipleResultDetail();
		
		if(resultDetails.isEmpty()) {
			return cashFlowResultModel;
		}
		
		for(Map<String, String> resultDetail : resultDetails) {
			CashFlowResult cashFlowResult = new CashFlowResult();
			
			String accountSide = resultDetail.getOrDefault("DcFlag", StringUtils.EMPTY);
			if(BankCorpEps.PAB_CASHFLOW_CREDIT_CODE.equals(accountSide)) {
				cashFlowResult.setAccountSide(AccountSide.DEBIT);
				cashFlowResult.setHostAccountNo(resultDetail.getOrDefault("InAcctNo", StringUtils.EMPTY));
				cashFlowResult.setHostAccountName(resultDetail.getOrDefault("InAcctName", StringUtils.EMPTY));
				cashFlowResult.setCounterAccountNo(resultDetail.getOrDefault("OutAcctNo", StringUtils.EMPTY));
				cashFlowResult.setCounterAccountName(resultDetail.getOrDefault("OutAcctName", StringUtils.EMPTY));
				String bankCode = resultDetail.getOrDefault("OutBankNo", StringUtils.EMPTY);
				String bankName = resultDetail.getOrDefault("OutBankName", StringUtils.EMPTY);
				cashFlowResult.setCounterBankInfo(JSON.toJSONString(new HashMap<String, String>(){{put("bankCode", bankCode);put("bankName", bankName);}}));
			}else {
				cashFlowResult.setAccountSide(AccountSide.CREDIT);
				cashFlowResult.setHostAccountNo(resultDetail.getOrDefault("OutAcctNo", StringUtils.EMPTY));
				cashFlowResult.setHostAccountName(resultDetail.getOrDefault("OutAcctName", StringUtils.EMPTY));
				cashFlowResult.setCounterAccountNo(resultDetail.getOrDefault("InAcctNo", StringUtils.EMPTY));
				cashFlowResult.setCounterAccountName(resultDetail.getOrDefault("InAcctName", StringUtils.EMPTY));
				String bankCode = resultDetail.getOrDefault("InBankNo", StringUtils.EMPTY);
				String bankName = resultDetail.getOrDefault("InBankName", StringUtils.EMPTY);
				cashFlowResult.setCounterBankInfo(JSON.toJSONString(new HashMap<String, String>(){{put("bankCode", bankCode);put("bankName", bankName);}}));
			}
			String tranAmount = resultDetail.getOrDefault("TranAmount", StringUtils.EMPTY);
			if(!tranAmount.isEmpty()) {
				cashFlowResult.setTransactionAmount(new BigDecimal(tranAmount));
			}
			String balance = resultDetail.getOrDefault("AcctBalance", StringUtils.EMPTY);
			try {
				cashFlowResult.setBalance(new BigDecimal(balance));
			} catch (Exception e) {
				
			}
			cashFlowResult.setBankSequenceNo(resultDetail.getOrDefault("HostTrace", StringUtils.EMPTY));
			String acctDate = resultDetail.getOrDefault("AcctDate", StringUtils.EMPTY);
			String txTime = resultDetail.getOrDefault("TxTime", StringUtils.EMPTY);
			if((!acctDate.isEmpty()) && (!txTime.isEmpty())) {
				cashFlowResult.setTransactionTime(DateUtils.parseDate(acctDate + txTime, "yyyyMMddHHmmss"));
			}
			cashFlowResult.setRemark(resultDetail.getOrDefault("Purpose", StringUtils.EMPTY));
			cashFlowResult.setOtherRemark(resultDetail.getOrDefault("AbstractStr_Desc", StringUtils.EMPTY));
			
			cashFlowResultList.add(cashFlowResult);
		}
		cashFlowResultModel.setCashFlowResult(cashFlowResultList);
		return cashFlowResultModel;
	}

}
