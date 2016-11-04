package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.remittance.CashFlowExportModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.MatchParams;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.VoucherOperationInfo;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;

public interface CashFlowHandler {
	public List<BillMatchResult> getMatchedBillInDbBy(String cashFlowUuid, AccountSide accountSide);
	public List<BillMatchResult> searchMatchedBill(MatchParams matchParams);
	public List<BillMatchResult> searchIssuedVoucher(MatchParams matchParams);
	public List<BillMatchResult> fillMatchResultWith(List<JournalVoucher> journalVoucherList);
	public BillMatchResult generateBillingPlanPartOfDebitBillMatchResultFrom(Order order, MatchParams matchParams);
	public List<BillMatchResult> generateDebitBillMatchResultWithSettlingAmountFrom(List<Order> orderList, Long companyId, AccountSide accountSide);
	public List<BillMatchResult> convertToBillMatchResultList(MatchParams matchParams, List<Order> orderList);
	public List<BillMatchResult> merge(List<BillMatchResult> billMatchResultInMongo, List<BillMatchResult> billMatchResultInMysql, MatchParams matchParams);
//	public void fillBillingPlanDataIn(List<BillMatchResult> billMatchResults, Long companyId);
	public void fillBillingPlanData(BillMatchResult billMatchResult, Long companyId);
	
	public AuditStatus changeVouchers(List<BillMatchResult> billMatchResultList,Long companyId, String cashFlowUuid, AccountSide accountSide, AfterVoucherIssuedHandler afterVoucherIssuedHandler) throws Exception;
	
	public Set<String> checkJVAndBill(List<BillMatchResult> billMatchResultList, Long companyId, String cashFlowUuid);
	public List<String> getDuplicatedBillingPlanUuidByItself(List<BillMatchResult> billMatchResultList);
	public List<String> getDuplicatedBillPlanUuidInDB(List<BillMatchResult> billMatchResultList, String cashFlowUuid);
	public AuditStatus refreshIssuedAmountAndAuditStatus(String cashFlowUuid, boolean isToBeClosed, AccountSide accountSide);
	public void lapseIssuedAndDebitVouchersAndUpdateStatus(String cashFlowUuid, VoucherOperationInfo operationInfo, AccountSide accountSide);
	public List<CashFlow> getUnattachedCashFlowListBy(Long financeCompanyId, String paymentAccountNo, String paymentName, BigDecimal voucherAmount, String bankTransactionNo);
	public List<CashFlowExportModel> getCashFlowExportModelsBy(String hostAccountNo, Date begin, Date end, com.zufangbao.sun.ledgerbook.AccountSide accountSide);
}
