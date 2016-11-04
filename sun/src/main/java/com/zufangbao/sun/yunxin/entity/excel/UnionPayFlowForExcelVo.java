package com.zufangbao.sun.yunxin.entity.excel;

public class UnionPayFlowForExcelVo {

	private String AppOrderNo;

	public String getAppOrderNo() {
		return AppOrderNo;
	}

	public void setAppOrderNo(String appOrderNo) {
		AppOrderNo = appOrderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getQueryOrderNo() {
		return queryOrderNo;
	}

	public void setQueryOrderNo(String queryOrderNo) {
		this.queryOrderNo = queryOrderNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTranscationResult() {
		return transcationResult;
	}

	public void setTranscationResult(String transcationResult) {
		this.transcationResult = transcationResult;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	private String orderTime;
	private String queryOrderNo;
	private String amount;
	private String userName;
	private String transcationResult;
	private String handleTime;
	private String clearTime;

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

}
