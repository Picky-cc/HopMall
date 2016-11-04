package com.zufangbao.earth.yunxin.unionpay.model;

public class PaymentDetailInfoModel {
	
	private String sn; // 记录序号

	private String bankCode; // 银行代码

	private String accountNo; // 银行卡号

	private String accountName; // 持卡人姓名
	
	private String province; //开户行所在省
	
	private String city; //开户行所在市
	
	private String bankName; //开户行名称

	private String amount; // 金额

	private String remark; // 备注
	
	private String idNum; // 证件编号

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public PaymentDetailInfoModel() {
		super();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public PaymentDetailInfoModel(String sn, String bankCode, String accountNo,
			String accountName, String province, String city, String bankName,
			String amount, String remark, String idNum) {
		super();
		this.sn = sn;
		this.bankCode = bankCode;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.province = province;
		this.city = city;
		this.bankName = bankName;
		this.amount = amount;
		this.remark = remark;
		this.idNum = idNum;
	}
}
