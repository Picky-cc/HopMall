package com.zufangbao.sun.yunxin.entity.remittance;

public class AccountInfoModel {
	
	/**
	 * 账户名
	 */
	private String accountName="";
	
	/**
	 * 账户号
	 */
	private String accountNo="";
	
	/**
	 * 开户行
	 */
	private String bankName="";
	
	/**
	 * 所在地    省
	 */
	private String province="";
	
	/**
	 * 所在地     市
	 */
	private String city="";
	
	/**
	 * 证件号
	 */
	private String idNumber="";
	
	/**
	 * 恒生银行编码
	 */
	private String bankCode="";

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public AccountInfoModel() {
		super();
	}

	public AccountInfoModel(String accountName, String accountNo,
			String bankName, String province, String city, String idNumber, String bankCode) {
		super();
		this.accountName = accountName == null ? "" : accountName;
		this.accountNo   = accountNo   == null ? "" : accountNo;
		this.bankName    = bankName    == null ? "" : bankName;
		this.province    = province    == null ? "" : province;
		this.city        = city        == null ? "" : city;
		this.idNumber    = idNumber    == null ? "" : idNumber;
		this.bankCode    = bankCode    == null ? "" : bankCode;
	}

}
