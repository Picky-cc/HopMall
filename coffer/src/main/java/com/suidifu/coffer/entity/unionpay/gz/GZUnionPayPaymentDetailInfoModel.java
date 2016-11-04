package com.suidifu.coffer.entity.unionpay.gz;

/**
 * 广银联-实时放款-详细参数
 * @author louguanyang
 *
 */
public class GZUnionPayPaymentDetailInfoModel {
	/** 记录序号 **/
	private String sn; 

	/** 银行代码 **/
	private String bankCode;

	/** 银行卡号 **/
	private String accountNo;

	/** 持卡人姓名 **/
	private String accountName;
	
	/** 开户行所在省 **/
	private String province;
	
	/** 开户行所在市 **/
	private String city;
	
	/** 开户行名称 **/
	private String bankName;

	/** 金额 **/
	private String amount;

	/** 备注 **/
	private String remark;
	
	/** 证件编号 **/
	private String idNum;

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	
}
