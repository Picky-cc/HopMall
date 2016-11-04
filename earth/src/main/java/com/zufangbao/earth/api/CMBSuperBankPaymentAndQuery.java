package com.zufangbao.earth.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.exception.UnknownUkeyException;
import com.zufangbao.earth.api.xml.BatchPaymentProcessingResultXml;
import com.zufangbao.earth.api.xml.BatchPaymentReqXml;
import com.zufangbao.earth.api.xml.PaymentDetail;
import com.zufangbao.earth.api.xml.TransactionStatusQueryRspDetailXml;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.GateWay;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.FlowService;
import com.zufangbao.sun.service.PaymentChannelService;

@Component("CMBSuperBankPaymentAndQuery")
public class CMBSuperBankPaymentAndQuery implements IPaymentAndQuery{
	
	@Autowired
	private IGZUnionPayApiComponent gZUnionPayHandler;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RequestRecordService requestRecordService;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private PaymentChannelService paymentChannelService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private CMBSuperBankHandler cmbSuperBankHandler;
	

	@Override
	public List<BatchPaymentProcessingResultXml> execBatchPayment(BatchPaymentReqXml reqXml, String reqXmlPacket) throws ResponseParseException, UnknownUkeyException {
		List<PaymentDetail> paymentDetailList = reqXml.getPaymentDetailList();
		List<CreditedModel> creditedDetailList = convertXX(paymentDetailList, reqXmlPacket);
		List<CreditedResult> rt = cmbSuperBankHandler.batchCredited(creditedDetailList);
		List<BatchPaymentProcessingResultXml> reee =convertYY(rt, paymentDetailList);
		return reee;
	}

	@Override
	public TransactionStatusQueryRspDetailXml execTransactionQuery(RequestRecord record) throws Exception{
		QueryTransactionModel queryModel = new QueryTransactionModel();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
		queryModel.setAccountNo(record.getMerchantId());
		queryModel.setEndDate(formater.format(new Date()));
		queryModel.setBeginDate(formater.format(DateUtils.addDays(new Date(), -99)));
//		queryModel.setBusCode(busCode);
//		queryModel.setMaxAmount(maxAmount);
//		queryModel.setMinAmount(minAmount);
//		queryModel.setProcessResult(processResult);
//		queryModel.setRequestStatus(requestStatus);
		queryModel.setTransactionUuid(record.getTransactionUuid());
		
		List<QueryTransactionResult> queryResultList = cmbSuperBankHandler.queryTransaction(queryModel);
		if(queryResultList.isEmpty()){
			throw new Exception("未查询到交易号为"+record.getReqNo()+"的交易状态");
		}
		QueryTransactionResult queryResult = queryResultList.get(0);
		
		TransactionStatusQueryRspDetailXml detail = new TransactionStatusQueryRspDetailXml();
		
		detail.setAccountNo(queryResult.getAccountNo());
		detail.setAmount(queryResult.getTransactionAmount());
		detail.setBankNo(queryResult.getBankNo());
		detail.setCurrencyNo(queryResult.getCurrencyCode());
		detail.setOperateDate(queryResult.getOperateDate());
		detail.setReqNo(record.getReqNo());
		detail.setRtnFlag(queryResult.getProcessResult());
		return detail;
	}
	
	private List<CreditedModel> convertXX(List<PaymentDetail> paymentDetailList, String reqXmlPacket) {
		List<CreditedModel> creditedModelList = new ArrayList<CreditedModel>();
		for (PaymentDetail paymentDetail : paymentDetailList) {
			String accountNo = paymentDetail.getAccountNo();
			Account account = accountService.getAccountByAccountNo(accountNo);
			String transactionUuid = UUID.randomUUID().toString().replace("-", "").substring(2);
			creditedModelList.add(new CreditedModel(account == null ? ""
					: account.getAccountName(), accountNo, account == null ? ""
					: account.getAttr().getOrDefault("bankNo", "").toString(), paymentDetail
					.getPayeeName(), paymentDetail.getPayeeNo(),
					BankCodeMapSpec.BANK_CODE_MAP.getOrDefault(paymentDetail.getBeneficiaryBankNo(), ""),
					paymentDetail.getAmount(), "10", paymentDetail.getRemark(),
					transactionUuid));
			
			requestRecordService.save(new RequestRecord(GateWay.SuperBank, FunctionName.FUNC_BATCH_PAYMENT,
					paymentDetail.getReqNo(),transactionUuid, reqXmlPacket, paymentDetail.getAccountNo()));
		}
		return creditedModelList;
	}
	
	private List<BatchPaymentProcessingResultXml> convertYY(List<CreditedResult> result, List<PaymentDetail> paymentDetailList){
		
		List<BatchPaymentProcessingResultXml>  rt = new ArrayList<BatchPaymentProcessingResultXml>();
		for (int i = 0; i < result.size(); i++) {
			CreditedResult creditedResult = result.get(i);
			rt.add(new BatchPaymentProcessingResultXml(paymentDetailList.get(i).getReqNo(),
					"BNK", creditedResult.getResultCode(), creditedResult.getErrorMsg()));
		}
		return rt;
	}

}
