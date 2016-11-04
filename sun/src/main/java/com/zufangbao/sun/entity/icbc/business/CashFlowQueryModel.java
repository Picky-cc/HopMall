package com.zufangbao.sun.entity.icbc.business;

import java.util.Date;

import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.utils.EnumUtil;

public class CashFlowQueryModel {

	private Date startDate;
	
	private Date endDate; 
	
	private String bankSequenceNo;
	
	private AccountSide accountSide;
	
	private String hostAccountNo;

	private int auditStatus = -1;
	
	private CashFlowChannelType cashFlowChannelType;


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBankSequenceNo() {
		return bankSequenceNo;
	}

	public void setBankSequenceNo(String bankSequenceNo) {
		this.bankSequenceNo = bankSequenceNo;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public String getHostAccountNo() {
		return hostAccountNo;
	}

	public void setHostAccountNo(String hostAccountNo) {
		this.hostAccountNo = hostAccountNo;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getDrcrfInt() {
		return accountSide == null ? -1 : accountSide.ordinal();
	}
	

	public CashFlowChannelType getCashFlowChannelType() {
		return cashFlowChannelType;
	}

	public void setCashFlowChannelType(CashFlowChannelType cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public AuditStatus getAuditStatusEnum() {
		AuditStatus auditStatusEnum = EnumUtil.fromOrdinal(AuditStatus.class, auditStatus);
		return auditStatusEnum;
	}

	public CashFlowQueryModel(Date startDate, Date endDate, String bankSequenceNo,
			AccountSide accountSide, String hostAccountNo, int auditStatus,CashFlowChannelType cashFlowChannelType) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.bankSequenceNo = bankSequenceNo;
		this.accountSide = accountSide;
		this.hostAccountNo = hostAccountNo;
		this.auditStatus = auditStatus;
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public CashFlowQueryModel(Date startDate, Date endDate, String hostAccountNo) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.hostAccountNo = hostAccountNo;
	}

}

