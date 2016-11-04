package com.zufangbao.earth.model;

import java.math.BigDecimal;

public class ManualDeductWebModel {

	private String bankCode; // 银行代码
	private String accountNo; // 银行卡号
	private String accountName; // 持卡人姓名
	private BigDecimal amount; // 金额
	private String remark; // 备注
	private String idCardNum; //身份证号
	private String province; //开户行所在省
	private String city; //开户行所在市
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ManualDeductWebModel() {
		super();
	}
	public ManualDeductWebModel(String bankCode, String accountNo,
			String accountName, BigDecimal amount, String remark,
			String idCardNum, String province, String city) {
		super();
		this.bankCode = bankCode;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.amount = amount;
		this.remark = remark;
		this.idCardNum = idCardNum;
		this.province = province;
		this.city = city;
	}
	
	public boolean validMaxAmount(){
//		if(amount==null ||new BigDecimal("1.00").compareTo(amount)<0){
		if(amount==null){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "ManualBatchDeductWebModel [bankCode=" + bankCode
				+ ", accountNo=" + accountNo + ", accountName=" + accountName
				+ ", amount=" + amount + ", remark=" + remark + ", idCardNum="
				+ idCardNum + ", province=" + province + ", city=" + city + "]";
	}
	
	

}
