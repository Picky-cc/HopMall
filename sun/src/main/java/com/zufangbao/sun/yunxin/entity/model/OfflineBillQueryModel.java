package com.zufangbao.sun.yunxin.entity.model;


public class OfflineBillQueryModel {
	
	private String accountName;
	private String payAcNo;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPayAcNo() {
		return payAcNo;
	}

	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}

	public OfflineBillQueryModel() {
	}

	public OfflineBillQueryModel(String accountName, String payAcNo) {
		super();
		this.accountName = accountName;
		this.payAcNo = payAcNo;
	}
}
