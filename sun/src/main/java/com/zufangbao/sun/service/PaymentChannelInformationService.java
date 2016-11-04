package com.zufangbao.sun.service;

import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelQueryModel;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel;

public interface PaymentChannelInformationService extends GenericService<PaymentChannelInformation> {
	
	public void updateChannelData(PaymentChannelInformation paymentChannelInformation);
	
	public List<PaymentChannelInformation> query(PaymentChannelQueryModel queryModel, Page page);
	
	public int queryCount(PaymentChannelQueryModel queryModel);
	
	public List<PaymentChannelInformation> getPaymentChannelBy(String financialContractUuid);
	
	public PaymentChannelInformation getPaymentChannelInformationBy(String paymentChannelUuid);
	
	public boolean isUnique(String paymentChannelName, String paymentChannelUuid);
	
	public Map<String, Object> getPaymentChannelCreditDetailForSwitchDetail(String paymentChannelUuid);
	
	public Map<String, Object> getPaymentChannelDebitDetailForSwitchDetail(String paymentChannelUuid);
	
	public PaymentChannelConfigure getPaymentChannelConfigureBy(String paymentChannelUuid);
	
	public List<Map<String, Object>> getDebitStrategyList(List<String> paymentChannelUuidList);
	
	public List<Map<String, Object>> getCreditStrategyList(List<String> paymentChannelUuidList);
	
	public List<TransferApplicationStatisticsModel> getTransferApplicationStatistics();
	
	public Map<String, Object> extractBriefDebitInfo(PaymentChannelInformation paymentChannelInformation);
	
	public Map<String, Object> extractBriefCreditInfo(PaymentChannelInformation paymentChannelInformation);
	
	public List<String> getAllOutlierChannelNames();
	
	public PaymentChannelInformation getPaymentChannelInformationBy(PaymentInstitutionName paymentInstitutionName, String outlierChannelName);
	
}
