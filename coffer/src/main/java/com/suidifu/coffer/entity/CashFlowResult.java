package com.suidifu.coffer.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CashFlowResult {

	private String hostAccountNo;

	private String hostAccountName;

	private String counterAccountNo;

	private String counterAccountName;

	private String counterAccountAppendix;

	private String counterBankInfo;

	private AccountSide accountSide;

	private Date transactionTime;

	private BigDecimal transactionAmount;

	private BigDecimal balance;

	private String transactionVoucherNo;

	private String bankSequenceNo;

	private String remark;

	private String otherRemark;

	private StrikeBalanceStatus strikeBalanceStatus;// 冲账标志

	public String getHostAccountNo() {
		return hostAccountNo;
	}

	public void setHostAccountNo(String hostAccountNo) {
		this.hostAccountNo = hostAccountNo;
	}

	public String getHostAccountName() {
		return hostAccountName;
	}

	public void setHostAccountName(String hostAccountName) {
		this.hostAccountName = hostAccountName;
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

	public String getCounterAccountAppendix() {
		return counterAccountAppendix;
	}

	public void setCounterAccountAppendix(String counterAccountAppendix) {
		this.counterAccountAppendix = counterAccountAppendix;
	}

	public String getCounterBankInfo() {
		return counterBankInfo;
	}

	public void setCounterBankInfo(String counterBankInfo) {
		this.counterBankInfo = counterBankInfo;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getBankSequenceNo() {
		return bankSequenceNo;
	}

	public void setBankSequenceNo(String bankSequenceNo) {
		this.bankSequenceNo = bankSequenceNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOtherRemark() {
		return otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}

	public StrikeBalanceStatus getStrikeBalanceStatus() {
		return strikeBalanceStatus;
	}

	public void setStrikeBalanceStatus(StrikeBalanceStatus strikeBalanceStatus) {
		this.strikeBalanceStatus = strikeBalanceStatus;
	}

}
