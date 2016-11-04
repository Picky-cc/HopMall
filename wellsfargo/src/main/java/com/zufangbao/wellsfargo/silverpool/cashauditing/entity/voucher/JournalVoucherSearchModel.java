package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;

public class JournalVoucherSearchModel {

	private String payAccountNo;
	private String payAccountName;

	private int journalVoucherStatus=-1;
	private int cashFlowChannelType=-1;
	private int accountSide=-1;
	
	private String startTime;
	private String endTime;
	
	public void setJournalVoucherStatus(int journalVoucherStatus) {
		this.journalVoucherStatus = journalVoucherStatus;
	}
	
	public int getJournalVoucherStatus() {
		return journalVoucherStatus;
	}
	
	public Date getStartDate() {
		return startTime==null?null:DateUtils.asDay(startTime);
	}

	public Date getEndDate() {
		return endTime==null?null:DateUtils.asDay(endTime);
	}

	public JournalVoucherStatus getJournalVoucherStatusEnum() {
		return JournalVoucherStatus.fromOrdinal(journalVoucherStatus);
	}

	public int getCashFlowChannelType() {
		return cashFlowChannelType;
	}
	
	public CashFlowChannelType getCashFlowChannelTypeEnum() {
		return CashFlowChannelType.fromValue(cashFlowChannelType);
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setCashFlowChannelType(int cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public int getAccountSide() {
		return accountSide;
	}
	
	public AccountSide getAccountSideEnum() {
		return AccountSide.fromValue(accountSide);
	}

	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}
	
	
	
	
	

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public String getPayAccountName() {
		return payAccountName;
	}

	public void setPayAccountName(String payAccountName) {
		this.payAccountName = payAccountName;
	}

	public JournalVoucherSearchModel() {

	}

}
