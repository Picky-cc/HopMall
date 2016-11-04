package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RET_DETAIL")
public class UnionpayRealTimePaymentRspXmlRetDetail {
	
	@XStreamAlias("SN")
	private String reqNo;//记录序号
	
	@XStreamAlias("ACCOUNT_NO")
	private String accountNo; //账号
	
	@XStreamAlias("ACCOUNT_NAME")
	private String accountName; //账号名
	
	@XStreamAlias("AMOUNT")
	private String amount; // 金额
	
	@XStreamAlias("CUST_USERID")
	private String custUserId; //自定义用户号
	
	@XStreamAlias("REMARK")
	private String remark; // 备注
	
	@XStreamAlias("RET_CODE")
	private String retCode; // 返回码
	
	@XStreamAlias("ERR_MSG")
	private String errmsg; // 错误文本
	
	@XStreamAlias("RESERVE1")
	private String reserve1; // 备注域1
	
	@XStreamAlias("RESERVE2")
	private String reserve2; // 备注域2

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
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

	public UnionpayRealTimePaymentRspXmlRetDetail() {
		super();
	}
	
}
