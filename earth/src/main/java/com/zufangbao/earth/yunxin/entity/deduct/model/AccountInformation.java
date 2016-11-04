package com.zufangbao.earth.yunxin.entity.deduct.model;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;

public class AccountInformation {

	
	private String customerName;
	
	private String bank;
	
	private String addressOfBank;
	
	private String repaymentAccountNo;

	public AccountInformation(ContractAccount contractAccount) {

		this.customerName = contractAccount.getContract().getCustomer().getName();
		this.bank = contractAccount.getBank();
		this.addressOfBank = contractAccount.getProvince()+" "+contractAccount.getCity();
		this.repaymentAccountNo = contractAccount.getPayAcNo();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAddressOfBank() {
		return addressOfBank;
	}

	public void setAddressOfBank(String addressOfBank) {
		this.addressOfBank = addressOfBank;
	}

	public String getRepaymentAccountNo() {
		return repaymentAccountNo;
	}

	public void setRepaymentAccountNo(String repaymentAccountNo) {
		this.repaymentAccountNo = repaymentAccountNo;
	}
	
	
}
