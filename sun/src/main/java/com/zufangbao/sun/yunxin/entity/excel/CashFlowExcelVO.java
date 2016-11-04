package com.zufangbao.sun.yunxin.entity.excel;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class CashFlowExcelVO {

	@ExcelVoAttribute(name = "流水号", column = "A")
	private String bankSequenceNo;
	@ExcelVoAttribute(name = "借贷标志", column = "B")
	private String accountSide;
	@ExcelVoAttribute(name = "交易金额", column = "C")
	private String transactionAmount;
	@ExcelVoAttribute(name = "瞬时余额", column = "D")
	private String balance;
	@ExcelVoAttribute(name = "银行账号", column = "E")
	private String counterAccountNo;
	@ExcelVoAttribute(name = "银行账号名", column = "F")
	private String counterAccountName;
	@ExcelVoAttribute(name = "入账时间", column = "G")
	private String transactionTime;
	@ExcelVoAttribute(name = "摘要", column = "H")
	private String remark;
	public String getBankSequenceNo() {
		return bankSequenceNo;
	}
	public void setBankSequenceNo(String bankSequenceNo) {
		this.bankSequenceNo = bankSequenceNo;
	}
	public String getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCounterAccountNo() {
		return counterAccountNo;
	}
	public void setCounterAccountNo(String counterAccountNo) {
		this.counterAccountNo = counterAccountNo;
	}
	public String getCounterAccountName() {
		return counterAccountName;
	}
	public void setCounterAccountName(String counterAccountName) {
		this.counterAccountName = counterAccountName;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public CashFlowExcelVO() {
		super();
	}
	public CashFlowExcelVO(String bankSequenceNo, String accountSide,
			String transactionAmount, String balance, String counterAccountNo,
			String counterAccountName, String transactionTime, String remark) {
		super();
		this.bankSequenceNo = bankSequenceNo;
		this.accountSide = accountSide;
		this.transactionAmount = transactionAmount;
		this.balance = balance;
		this.counterAccountNo = counterAccountNo;
		this.counterAccountName = counterAccountName;
		this.transactionTime = transactionTime;
		this.remark = remark;
	}
	
	public CashFlowExcelVO(CashFlow cashFlow){
		this.bankSequenceNo = cashFlow.getBankSequenceNo();
		this.accountSide = cashFlow.getAccountSideMsg();
		this.transactionAmount = cashFlow.getTransactionAmount()==null?StringUtils.EMPTY:cashFlow.getTransactionAmount().toString();
		this.balance = cashFlow.getBalance()==null?StringUtils.EMPTY:cashFlow.getBalance().toString();
		this.counterAccountNo = "\t" + cashFlow.getCounterAccountNo();
		this.counterAccountName = cashFlow.getCounterAccountName();
		this.transactionTime = cashFlow.getTransactionTime()==null?StringUtils.EMPTY:DateUtils.format(cashFlow.getTransactionTime(),DateUtils.LONG_DATE_FORMAT);
		this.remark = cashFlow.getRemark();
	}

	
	
}
