package com.zufangbao.wellsfargo.silverpool.cashauditing.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.account.AccountSide;

public class MatchParams {
	private Long companyId;
	private String account;
	private String accountOwnerName;
	private String remark;
	private String cashFlowUuid;
	private Date tradeTime;
	private BigDecimal amount;
	private AccountSide accountSide;

	public MatchParams(){

	}
	
	public MatchParams(String cashFlowUuid, Long companyId, String account, String accountOwnerName,BigDecimal amount, Date tradeTime, AccountSide accountSide, String remark){
		this.cashFlowUuid = cashFlowUuid;
		this.companyId = companyId;
		this.account = account;
		this.accountOwnerName = accountOwnerName;
		this.amount = amount;
		this.tradeTime = tradeTime;
		this.accountSide = accountSide;
		this.remark = remark;
	}
	
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountOwnerName() {
		return accountOwnerName;
	}
	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}
	
	public String getCashFlowUuid() {
		return cashFlowUuid;
	}
	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
