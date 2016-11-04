package com.zufangbao.earth.yunxin.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.web.controller.channel.PaymentChannelInformationListModel;
import com.zufangbao.earth.yunxin.handler.PaymentChannelInformationHandler;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.TransactionChannelConfigure;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;


@Component("paymentChannelInformationHandler")
public class PaymentChannelInformationHandlerImpl implements PaymentChannelInformationHandler{

	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private FinancialContractConfigService financialContractConfigService;
	
	@Override
	public Map<String, Object> searchPaymentChannelBy(PaymentChannelQueryModel queryModel, Page page) {
		List<PaymentChannelInformation> paymentChannelInformatinList = paymentChannelInformationService.query(queryModel, page);
		int allSize = paymentChannelInformationService.queryCount(queryModel);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("size", allSize);
		List<PaymentChannelInformationListModel> paymentChannelInformationListModelList = new ArrayList<PaymentChannelInformationListModel>();
		for (PaymentChannelInformation paymentChannelInformation : paymentChannelInformatinList) {
			paymentChannelInformationListModelList.add(new PaymentChannelInformationListModel(paymentChannelInformation));
		}
		resultMap.put("list", paymentChannelInformationListModelList);
		return resultMap;
	}

	@Override
	public Map<String, Object> getPaymentChannelDetails(String paymentChannelUuid) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		String paymentConfigureData = paymentChannelInformation.getPaymentConfigureData();
		PaymentChannelConfigure paymentChannelConfigure = JsonUtils.parse(paymentConfigureData, PaymentChannelConfigure.class);
		String paymentChannelName = paymentChannelInformation.getPaymentChannelName();
		String paymentInstitutionNameMsg = paymentChannelInformation.getPaymentInstitutionNameMsg();
		String outlierChannelName = paymentChannelInformation.getOutlierChannelName();
		String relatedFinancialContractName = paymentChannelInformation.getRelatedFinancialContractName();
		resultData.put("paymentChannelName", paymentChannelName);
		resultData.put("paymentInstitutionNameMsg", paymentInstitutionNameMsg);
		resultData.put("outlierChannelName", outlierChannelName);
		resultData.put("relatedFinancialContractName", relatedFinancialContractName);
		resultData.put("paymentChannelUuid", paymentChannelUuid);
		if(paymentChannelConfigure==null){
			return resultData;
		}
		TransactionChannelConfigure creditChannelConfigure =  paymentChannelConfigure.getCreditChannelConfigure();
		TransactionChannelConfigure debitChannelConfigure = paymentChannelConfigure.getDebitChannelConfigure();
		resultData.put("creditChannelDetails", creditChannelConfigure);
		resultData.put("debitChannelDetails", debitChannelConfigure);
		return resultData;
	}

	@Override
	public boolean savePaymentChannelConfigure(String paymentChannelUuid, String paymentChannelName, PaymentChannelConfigure paymentChannelConfigure) {
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return false;
		}
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		if(paymentChannelInformation == null){
			return false;
		}
		paymentChannelInformation.setPaymentChannelName(paymentChannelName);
		paymentChannelInformation.setBy(paymentChannelConfigure);
		paymentChannelInformationService.updateChannelData(paymentChannelInformation);
		financialContractConfigService.updateFinancialContractConfig(paymentChannelInformation);
		return true;
	}

	@Override
	public String getPaymentChannelConfigureData(String paymentChannelUuid) {
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		String paymentConfigureData = paymentChannelInformation.getPaymentConfigureData();
		return paymentConfigureData;
	}

	@Override
	public FinancialContract getFinancialContractBy(String paymentChannelUuid) {
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		if(paymentChannelInformation == null){
			return null;
		}
		String financialContractUuid = paymentChannelInformation.getRelatedFinancialContractUuid();
		FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
		return financialContract;
	}

	@Override
	public String getPaymentChannelServiceUuidBy(String financialContractUuid, BusinessType businessType, AccountSide accountSide) {
		String uuid = financialContractConfigService.getPaymentChannelInformationUuids(financialContractUuid, businessType, accountSide);
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(uuid);
		if(paymentChannelInformation == null){
			return null;
		}
		if(accountSide == AccountSide.CREDIT){
			return paymentChannelInformation.getCreditPaymentChannelServiceUuid();
			
		}else if(accountSide == AccountSide.DEBIT){
			return paymentChannelInformation.getDebitPaymentChannelServiceUuid();
		}else{
			return null;
		}
	}

}
