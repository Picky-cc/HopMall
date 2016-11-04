package com.zufangbao.sun.yunxin.entity.model;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.utils.EnumUtil;

public class AccountFlowModel {
	
	private int accountSide;
	
	private int transactionType;
	
	private String key;

	public int getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}

	public AccountSide getAccountSideEnum(){
		return EnumUtil.fromOrdinal(AccountSide.class,accountSide);
	}
	
	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public VirtualAccountTransactionType getVirtualAccountTransactionTypeEnum(){
		return EnumUtil.fromOrdinal(VirtualAccountTransactionType.class,transactionType);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
