package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("NTIBCOPRX")
public class PaymentDetail {
	
	@XStreamAlias("ACCNBR")
	private String accountNo; //付款账号
	
	@XStreamAlias("YURREF")
	private String reqNo; //交易唯一参考号
	
	@XStreamAlias("TRSAMT")
	private String amount; //金额
	
	
	@XStreamAlias("CDTNAM")
	private String payeeName; // 收款人户名
	
	
	@XStreamAlias("CDTEAC")
	private String payeeNo; //收款人账号
	
	
	@XStreamAlias("CDTBRD")
	private String beneficiaryBankNo;  //收款行行号
	
	
	@XStreamAlias("PRONAM")
	private String province; //开户行所在省
	
	
	@XStreamAlias("CITNAM")
	private String city; // 开户行所在市
	
	
	@XStreamAlias("BAKNAM")
	private String bankName; //开户行名称
	
	@XStreamAlias("RMKTXT")
	private String remark; //附言
	
	@XStreamAlias("RSV30Z")
	private String reservedWord; //保留字 30

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeNo() {
		return payeeNo;
	}

	public void setPayeeNo(String payeeNo) {
		this.payeeNo = payeeNo;
	}

	public String getBeneficiaryBankNo() {
		return beneficiaryBankNo;
	}

	public void setBeneficiaryBankNo(String beneficiaryBankNo) {
		this.beneficiaryBankNo = beneficiaryBankNo;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReservedWord() {
		return reservedWord;
	}

	public void setReservedWord(String reservedWord) {
		this.reservedWord = reservedWord;
	}

	public PaymentDetail(String accountNo, String reqNo, String amount,
			String payeeName, String payeeNo, String beneficiaryBankNo,
			String province, String city, String bankName, String remark,
			String reservedWord) {
		super();
		this.accountNo = accountNo;
		this.reqNo = reqNo;
		this.amount = amount;
		this.payeeName = payeeName;
		this.payeeNo = payeeNo;
		this.beneficiaryBankNo = beneficiaryBankNo;
		this.province = province;
		this.city = city;
		this.bankName = bankName;
		this.remark = remark;
		this.reservedWord = reservedWord;
	}

	public PaymentDetail() {
		super();
	}

}
