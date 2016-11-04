package com.zufangbao.sun.yunxin.entity.model;

import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;


public class TransactionLimitQueryModel {
	
	private int gateway=-1;
	
	private int accountSide=-1;
	
	private String outlierChannelName;
	
	private String keyWord;

	public int getGateway() {
		return gateway;
	}
	
	public PaymentInstitutionName getGatewayEnum() {
		return EnumUtil.fromOrdinal(PaymentInstitutionName.class, gateway);
	}

	public void setGateway(int gateway) {
		this.gateway = gateway;
	}

	public String getOutlierChannelName() {
		return outlierChannelName;
	}

	public void setOutlierChannelName(String outlierChannelName) {
		this.outlierChannelName = outlierChannelName;
	}

	public int getAccountSide() {
		return accountSide;
	}

	public AccountSide getAccountSideEnum() {
		return EnumUtil.fromOrdinal(AccountSide.class, accountSide);
	}
	
	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public TransactionLimitQueryModel() {
	}
	
}

