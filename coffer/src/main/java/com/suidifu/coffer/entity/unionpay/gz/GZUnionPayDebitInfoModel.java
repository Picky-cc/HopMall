package com.suidifu.coffer.entity.unionpay.gz;

import java.math.BigDecimal;

/**
 * 广银联代扣详情
 * @author louguanyang
 *
 */
public class GZUnionPayDebitInfoModel {
	/** 记录序号 **/
	private String sn;

	/** 银行代码 **/
	private String bankCode;

	/** 银行卡号 **/
	private String accountNo;

	/** 持卡人姓名 **/
	private String accountName;

	/** 金额 **/
	private BigDecimal amount;

	/** 备注 **/
	private String remark;
	
	/** 身份证号 **/
	private String idCardNum;
	
	/** 开户行所在省 **/
	private String province;
	
	/** 开户行所在市 **/
	private String city;

	public GZUnionPayDebitInfoModel() {
		super();
	}

	public GZUnionPayDebitInfoModel(String sn, String bankCode, String accountNo, String accountName, BigDecimal amount,
			String remark, String idCardNum, String province, String city) {
		super();
		this.sn = sn;
		this.bankCode = bankCode;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.amount = amount;
		this.remark = remark;
		this.idCardNum = idCardNum;
		this.province = province;
		this.city = city;
	}

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
	
}
