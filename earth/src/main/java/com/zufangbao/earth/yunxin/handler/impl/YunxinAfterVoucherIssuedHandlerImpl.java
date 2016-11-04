package com.zufangbao.earth.yunxin.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.CashAuditVoucherSet;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.AfterVoucherIssuedHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.BusinessVoucherHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowCacheHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@Component("yunxinAfterVoucherIssuedHandler")
public class YunxinAfterVoucherIssuedHandlerImpl implements AfterVoucherIssuedHandler {

	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BusinessVoucherHandler businessVoucherHandler;
	
	@Autowired
	private CashFlowHandler cashFlowHandler;
	@Autowired
	private CashFlowCacheHandler cashFlowCacheHandler;
	@Override
	public void handlerAfterVoucherIssued(CashAuditVoucherSet cashAuditVoucherSet) {
		//make business voucher;
		//set orderStatus;
		//set repaymetn status;
		//make offlineRecod(batchuuid, cashFlow)
		//ledger book
		List<String> billingPlanUuids = cashAuditVoucherSet.getBillingPlanUuids();
		businessVoucherHandler.createIfNotExist(billingPlanUuids, cashAuditVoucherSet.getCompanyId());
		AuditStatus auditStatus = cashFlowHandler.refreshIssuedAmountAndAuditStatus(cashAuditVoucherSet.getCashFlowUuid(), false, cashAuditVoucherSet.getAccountSide());
		journalVoucherHandler.update_bv_order_asset_by_jv(billingPlanUuids, cashAuditVoucherSet.getCompanyId(), cashAuditVoucherSet.getAccountSide());
		//make offlineRecod(batchuuid, cashFlow)
		recover_related_asset(cashAuditVoucherSet.getLapsedVouchers(), cashAuditVoucherSet.getIssuedVouchers());
		cashFlowCacheHandler.clearRelativeBillOf(cashAuditVoucherSet.getBillingPlanUuids());
		cashFlowCacheHandler.refreshCacheOfMatchedBillBy(cashAuditVoucherSet.getCashFlowUuid(), cashAuditVoucherSet.getAccountSide());
	}
	private void recover_related_asset(List<JournalVoucher> lapsedVouchers, List<JournalVoucher> issuedVouchers){
		journalVoucherHandler.write_off_forward_ledgers(lapsedVouchers);
		//journalVoucherHandler.recover_loan_asset_or_guarantee_by_jvs(issuedVouchers);
	}
	
}
