package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("NTWAUEBPZ")
public class TransactionStatusQueryRspDetailXml {
	
	@XStreamAlias("BBKNBR")  //银行号
	private String bankNo;
	
	@XStreamAlias("ACCNBR")  //帐号
	private String accountNo;
	
	@XStreamAlias("CCYNBR")  //币种
	private String currencyNo;
	
	@XStreamAlias("TRSAMT")  //金额
	private String amount;
	
	@XStreamAlias("OPRDAT")  //经办日
	private String operateDate;
	
	@XStreamAlias("YURREF")  //交易参考号
	private String reqNo;
	
	@XStreamAlias("RTNFLG")  //业务处理结果
	private String rtnFlag;
	
	@XStreamAlias("RSV30Z")  //保留字 30
	private String rsvMsg;

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getRtnFlag() {
		return rtnFlag;
	}

	public void setRtnFlag(String rtnFlag) {
		this.rtnFlag = rtnFlag;
	}

	public String getRsvMsg() {
		return rsvMsg;
	}

	public void setRsvMsg(String rsvMsg) {
		this.rsvMsg = rsvMsg;
	}

	public TransactionStatusQueryRspDetailXml() {
		super();
	}
	
}
