package com.zufangbao.sun.yunxin.entity.model;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;

public class PaymentChannelQueryModel {
	
	private int gateway=-1;
	
	private int creditStatus=-1;
	
	private int debitStatus=-1;
	
	private int businessType=-1;
	
	private String keyWord;

	public int getGateway() {
		return gateway;
	}

	public void setGateway(int gateway) {
		this.gateway = gateway;
	}
	
	public PaymentInstitutionName getGatewayEnum(){
		return PaymentInstitutionName.fromValue(gateway);
	}

	public int getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(int creditStatus) {
		this.creditStatus = creditStatus;
	}
	
	public ChannelWorkingStatus getCreditStatusEnum(){
		return ChannelWorkingStatus.formValue(creditStatus);
	}

	public int getDebitStatus() {
		return debitStatus;
	}

	public void setDebitStatus(int debitStatus) {
		this.debitStatus = debitStatus;
	}
	
	public ChannelWorkingStatus getDebitStatusEnum(){
		return ChannelWorkingStatus.formValue(debitStatus);
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	
	public BusinessType getBusinessTypeEnum(){
		return BusinessType.fromValue(businessType);
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public boolean isValidParameter(Object obj){
		if(obj == null){
			return false;
		}else if(obj instanceof String){
			return StringUtils.isNotEmpty(obj.toString());
		}
		return true;
	}

	public PaymentChannelQueryModel() {
		super();
	}
	
	public PaymentChannelQueryModel(int gateway, int creditStatus,
			int debitStatus, int businessType, String keyWord) {
		super();
		this.gateway = gateway;
		this.creditStatus = creditStatus;
		this.debitStatus = debitStatus;
		this.businessType = businessType;
		this.keyWord = keyWord;
	}

}
