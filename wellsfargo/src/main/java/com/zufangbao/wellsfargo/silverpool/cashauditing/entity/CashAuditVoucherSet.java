package com.zufangbao.wellsfargo.silverpool.cashauditing.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;

public class CashAuditVoucherSet {
	private Long companyId;
	private AccountSide accountSide;
	private String journalVoucherBatchUuid;
	private String cashFlowUuid;
	//应该为list，order,asset更新的有顺序
	private List<String> billingPlanUuids = new ArrayList<String>();
	private List<JournalVoucher> issuedVouchers = new ArrayList<JournalVoucher>();
	private List<JournalVoucher> lapsedVouchers = new ArrayList<JournalVoucher>();
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCashFlowUuid() {
		return cashFlowUuid;
	}
	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}
	public List<String> getBillingPlanUuids() {
		return billingPlanUuids;
	}
	public void setBillingPlanUuids(List<String> billingPlanUuids) {
		this.billingPlanUuids = billingPlanUuids;
	}
	public String getJournalVoucherBatchUuid() {
		return journalVoucherBatchUuid;
	}
	public void setJournalVoucherBatchUuid(String journalVoucherBatchUuid) {
		this.journalVoucherBatchUuid = journalVoucherBatchUuid;
	}
	public AccountSide getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}
	public List<JournalVoucher> getIssuedVouchers() {
		return issuedVouchers;
	}
	public void setIssuedVouchers(List<JournalVoucher> issuedVouchers) {
		this.issuedVouchers = issuedVouchers;
	}
	public List<JournalVoucher> getLapsedVouchers() {
		return lapsedVouchers;
	}
	public void setLapsedVouchers(List<JournalVoucher> lapsedVouchers) {
		this.lapsedVouchers = lapsedVouchers;
	}
	
	public CashAuditVoucherSet() {
		super();
	}
	public CashAuditVoucherSet(Long companyId, AccountSide accountSide,String cashFlowUuid){
		this.companyId = companyId;
		this.accountSide = accountSide;
		this.cashFlowUuid = cashFlowUuid;
	}
	
	public CashAuditVoucherSet(Long companyId, AccountSide accountSide,
			String journalVoucherBatchUuid, String cashFlowUuid,
			List<String> billingPlanUuids, List<JournalVoucher> issuedVouchers,
			List<JournalVoucher> lapsedVouchers) {
		super();
		this.companyId = companyId;
		this.accountSide = accountSide;
		this.journalVoucherBatchUuid = journalVoucherBatchUuid;
		this.cashFlowUuid = cashFlowUuid;
		this.billingPlanUuids = billingPlanUuids;
		this.issuedVouchers = issuedVouchers;
		this.lapsedVouchers = lapsedVouchers;
	}
	public void collectLapsedVoucher(List<JournalVoucher> vouchersToBeLapse) {
		lapsedVouchers.addAll(vouchersToBeLapse);
	}
	public void collectIssuedVoucher(JournalVoucher issuedJournalVoucher) {
		issuedVouchers.add(issuedJournalVoucher);
	}
	public void collectBillingPlan(Set<String> lapsedBillingPlanUuid) {
		billingPlanUuids.addAll(lapsedBillingPlanUuid);
	}
	public void collectBillingPlan(String billingPlanUuid) {
		billingPlanUuids.add(billingPlanUuid);
	}
	
	
	
	
}
