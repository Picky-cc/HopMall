package com.zufangbao.earth.yunxin.handler;

import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public interface PaymentChannelInformationHandler {
	
	public Map<String, Object> searchPaymentChannelBy(PaymentChannelQueryModel queryModel, Page page);
	
	public Map<String, Object> getPaymentChannelDetails(String paymentChannelUuid);
	
	public boolean savePaymentChannelConfigure(String paymentChannelUuid, String paymentChannelName, PaymentChannelConfigure paymentChannelConfigure);
	
	public String getPaymentChannelConfigureData(String paymentChannelUuid);
	
	public FinancialContract getFinancialContractBy(String paymentChannelUuid);
	
	public String getPaymentChannelServiceUuidBy(String financialContractUuid, BusinessType businessType, AccountSide accountSide);
	
}
