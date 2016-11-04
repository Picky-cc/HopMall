package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RET_DETAIL")
public class UnionpayTransactionResultQueryRetDetail {
	
	@XStreamAlias("SN")
	private String sn; //记录序号
	
	@XStreamAlias("ACCOUNT")
	private String account; //账号
	
	@XStreamAlias("ACCOUNT_NAME")
	private String accountName; //账号名
	
	@XStreamAlias("AMOUNT")
	private String amount; // 金额
	
	@XStreamAlias("CUST_USERID")
	private String custUserId; //自定义用户名
	
	@XStreamAlias("REMARK")
	private String remark; // 备注
	
	@XStreamAlias("COMPLETE_TIME")
	private String completeTime; //交易时间
	
	@XStreamAlias("SETT_DATE")
	private String settDate; //清算时间
	
	@XStreamAlias("RET_CODE")
	private String retCode; // 返回码
	
	@XStreamAlias("ERR_MSG")
	private String errMessge; // 错误文本
	
	@XStreamAlias("RESERVE1")
	private String reserve1; // 备用字段1
	
	@XStreamAlias("RESERVE2")
	private String reserve2; // 备用字段2

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getSettDate() {
		return settDate;
	}

	public void setSettDate(String settDate) {
		this.settDate = settDate;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getErrMessge() {
		return errMessge;
	}

	public void setErrMessge(String errMessge) {
		this.errMessge = errMessge;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public UnionpayTransactionResultQueryRetDetail() {
		super();
	}
	
}
