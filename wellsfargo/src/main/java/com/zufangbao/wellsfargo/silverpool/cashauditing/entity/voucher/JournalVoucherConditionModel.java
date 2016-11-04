package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;

public class JournalVoucherConditionModel {

	private String startTime;

	private String endTime;

	private String tradeNo;

	private String orderNo;

	private int journalVoucherStatus;
	
	private CashFlowChannelType cashFlowChannelType;
	
	private AccountSide accountSide;
	
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

	public String getTradeNo() {
		return tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public JournalVoucherStatus getJournalVoucherStatusEnum() {
		return JournalVoucherStatus.fromOrdinal(journalVoucherStatus);
	}

	public CashFlowChannelType getCashFlowChannelType() {
		return cashFlowChannelType;
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

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setCashFlowChannelType(CashFlowChannelType cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public JournalVoucherConditionModel(String startTime, String endTime,
			String tradeNo, String orderNo, int journalVoucherStatus) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.tradeNo = tradeNo;
		this.orderNo = orderNo;
		this.journalVoucherStatus = journalVoucherStatus;
	}

	public JournalVoucherConditionModel(String startTime, String endTime,
			String tradeNo, String orderNo, int journalVoucherStatus,
			CashFlowChannelType cashFlowChannelType, AccountSide accountSide) {
		this(startTime, endTime, tradeNo, orderNo, journalVoucherStatus);
		this.cashFlowChannelType = cashFlowChannelType;
		this.accountSide = accountSide;
	}

	public JournalVoucherConditionModel() {

	}

}
