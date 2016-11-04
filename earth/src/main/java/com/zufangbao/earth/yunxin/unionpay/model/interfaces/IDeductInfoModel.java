package com.zufangbao.earth.yunxin.unionpay.model.interfaces;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

public interface IDeductInfoModel {
	
	public void setReqNo(String reqNo);
	
	public void setAmount(BigDecimal amount);
	
	public void setContractAccount(ContractAccount contractAccount);
	
	public void setPaymentChannel(PaymentChannel paymentChannel);
	
	public void setBusinessCode(String businessCode);
	
	public void setBankCode(String bankCode);
	
	public String getReqNo();
	
	public BigDecimal getAmount();
	
	public ContractAccount getContractAccount();
	
	public PaymentChannel getPaymentChannel();
	
	public String getBusinessCode();
	
	public String getBankCode();
	
}
