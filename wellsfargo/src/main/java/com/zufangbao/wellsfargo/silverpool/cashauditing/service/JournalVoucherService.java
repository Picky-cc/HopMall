
package com.zufangbao.wellsfargo.silverpool.cashauditing.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalAccount;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCheckingLevel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCompleteness;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherSearchModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;

public interface JournalVoucherService extends GenericService<JournalVoucher>{
	public JournalVoucher getJournalVoucherByVoucherUUID(String journalVoucherUUID);
	
	public List<JournalVoucher> getJournalVoucherListByVoucherUUIDList(List<String> voucherUuidList);
	public void save(JournalVoucher journalVoucher);
	public int issueVourcher(String journalVoucherUUID);
	public void issueVourcher(JournalVoucher journalVoucher);
	public boolean exists(String notificationRecordUuid);
	
	public List<JournalVoucher> getJournalVoucherByCashFlowSerialNoAndJournalAccount(String cashFlowSerialNo, JournalAccount journalAccount);
	
	public List<JournalVoucher> getJournalVoucherBySourceDocumentCashFlowSerialNoAndJournalAccount(String source_document_cash_flow_serial_no, JournalAccount journalAccount);
	public int countJournalVoucherBySourceDocumentCashFlowSerialNoAndJournalAccount(String source_document_cash_flow_serial_no, JournalAccount journalAccount);
	
	public JournalVoucher getJournalVoucherByNotificationUUIDAndJournalAccount(String notificationRecordUuid, JournalAccount journalAccount);
	public BigDecimal getBookingAmountSumOfIssueJournalVoucherBy(String billingPlanUuid, Long companyId, JournalAccount journalAccount);
	public BigDecimal getIssuedAmountByIssueJV(String cashFlowUuid, JournalAccount journalAccount);
	public List<JournalVoucher> getIssuedJVListBy(String cashFlowUuid, JournalAccount journalAccount);
	public List<String> getBillUuidsInDebitIssuedJVListBy(String cashFlowUuid, Collection<String> billingPlanUuids);
	public void lapseJournalVoucher(JournalVoucher journalVoucher);
	public void lapseJournalVoucherList(List<JournalVoucher> journalVoucherList);
	public Set<String> getJournalVoucherUuidsFrom(List<JournalVoucher> journalVoucherList);
	public Set<String> getBillingPlanUuidsFrom(List<JournalVoucher> journalVoucherList);
	public JournalVoucher getIssuedJournalVoucherBy(String journalVoucherUuid, JournalAccount journalAccount);
	public Date getLastPaidTimeByIssuedJournalVoucherAndJournalAccount(String billingPlanUuid,Long companyId, JournalAccount journalAccount);
	
	public boolean existCashFlow(String cashFlowUid);
	
	public void saveCashFlow(AppArriveRecord appArriveRecord);
	
	public JournalVoucher getJournalVoucherByCashFlowUid(String cashFlowUid);
	
	public boolean isExistNotificationByCashFlow(AppArriveRecord cashFlow);
	
	public List<JournalVoucher> getNotificationByCashFlow(AppArriveRecord cashFlow);
	
	public boolean isJournalVoucherComplete(JournalVoucher notification,AppArriveRecord cashFlow);
	
	public List<JournalVoucher> getVouchersByBillingPlanUidAndIssued(String billingPlanUuid, Long companyId);
	
	public int fillBusinessVoucherUuid(String businessUuid,String businessVoucherTypeUuid, List<JournalVoucher> journalVouchers);
	
	public JournalVoucher refreshCompletenes(JournalVoucher journalVoucher);
	
	public List<JournalVoucher> getJournalVoucherBy(String cashFlowUuid, AccountSide accountSide, JournalVoucherCompleteness completeness, Long companyId, JournalVoucherStatus status);

	//public JournalVoucher getInForceJournalVoucherBy(String billingPlanUuid, String cashFlowUuid, String businessVoucherType);
	
	public List<JournalVoucher> getInForceJournalVoucherListBy(JournalVoucherQueryModel jvqModel, Date startIssuedDate, Date endIssuedDate);
	
	public List<JournalVoucher> getInForceJournalVoucherListBy(String billingPlanUuid, String businessVoucherType);
	
	public JournalVoucher getInForceJournalVoucherBy(Long companyId, String billingPlanUuid, String sourceDocumentIdentity, String businessVoucherType);
	public List<JournalVoucher> getJournalVoucherBy(String sourceDocumentUuid, AccountSide accountSide, JournalVoucherStatus journalVoucherStatus, Long companyId);
	public boolean existsJVWithSourceDocumentUuid(String sourceDocumentUuid, Long companyId, AccountSide accountSide, String billingPlanUuid);
	public JournalVoucher createIssuedJournalVoucherBySourceDocument(String billingPlanUuid, SourceDocument sourceDocument, String businessVoucherTypeUuid, BigDecimal bookingAmount, String businessVoucherUuid, JournalVoucherType journalVoucherType);
	public JournalVoucher createIssuedJournalVoucherByCashFlow(String billingPlanUuid, AppArriveRecord appArriveRecord, String businessVoucherTypeUuid, BigDecimal bookingAmount,String businessVoucherUuid, JournalVoucherCheckingLevel journalVoucherCheckingLevel);
	public Set<String> extractSourceDocumentUuid(List<JournalVoucher> journalVoucherList);
	public List<JournalVoucher> getJournalVoucherListBy(JournalVoucherSearchModel searchModel, int begin, int max);
	
	public List<JournalVoucher> getJournalVoucherBySourceDocumentUuid( String sourceDocumentUuid);
	
	public List<JournalVoucher> lapse_issued_jvs_of_cash_flow(String cashFlowUuid, AccountSide accountSide);
	
	public JournalVoucher getInforceDepositJournalVoucher(Long companyId, String sourceDocumentUuid);
	public List<JournalVoucher> getInforceThirdPartyDeductJournalVoucher(Long companyId, String billingPlanUuid);
}
