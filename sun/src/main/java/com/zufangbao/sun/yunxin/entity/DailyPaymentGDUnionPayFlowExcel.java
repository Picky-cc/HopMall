package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class DailyPaymentGDUnionPayFlowExcel {

	@ExcelVoAttribute(name = "交易流水号", column = "A")
	private String reqNo;
	@ExcelVoAttribute(name = "记录序号", column = "B")
	private String sn;
	@ExcelVoAttribute(name = "收付类型", column = "C")
	private String sfType;
	@ExcelVoAttribute(name = "账户", column = "D")
	private String account;
	@ExcelVoAttribute(name = "账户名", column = "E")
	private String accountName;
	@ExcelVoAttribute(name = "金额", column = "F")
	private String amount;
	@ExcelVoAttribute(name = "扣款成功时间", column = "G")
	private String completeTime;
	@ExcelVoAttribute(name = "清算日期", column = "H")
	private String settDate;
	@ExcelVoAttribute(name = "清算账号", column = "I")
	private String reckonAccount;
	@ExcelVoAttribute(name = "信息", column = "J")
	private String msg;
	
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSfType() {
		return sfType;
	}
	public void setSfType(String sfType) {
		this.sfType = sfType;
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
	public String getReckonAccount() {
		return reckonAccount;
	}
	public void setReckonAccount(String reckonAccount) {
		this.reckonAccount = reckonAccount;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
