package com.zufangbao.wellsfargo.yunxin.handler;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.InsufficientBalanceException;
import com.zufangbao.sun.ledgerbook.InsufficientPenaltyException;
import com.zufangbao.sun.ledgerbook.InvalidCarryOverException;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.ledgerbook.LackBusinessVoucherException;
import com.zufangbao.sun.yunxin.entity.AssetRecoverType;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;

public interface JournalVoucherHandler {
	public void createJVFromUnSignedOnlineSourceDocuments(BankAccountCache account);
	
	public void create_JV_and_BV_from_sourceDocument(SourceDocument sourceDocument,List<Order> orderList, JournalVoucherType journalVoucherType);
	public JournalVoucher issueJournalVoucher(String repaymentBillId, SourceDocument sourceDocument, BigDecimal bookingAmount, String businessVoucherUuid, JournalVoucherType journalVoucherType); 
	public void update_bv_order_asset_by_jv(Collection<String> repaymentUuids, Long companyId, AccountSide accountSide);
	public void update_bv_order_asset_by_jv(String repaymentUuid, Long companyId, AccountSide accountSide);
	
	public void write_off_forward_ledgers(List<JournalVoucher> journalVoucherList);
	
	//public void recover_loan_asset_or_guarantee_by_jvs(List<JournalVoucher> journalVoucherList);
	//public void recover_loan_asset_or_guarantee(JournalVoucher journalVoucher);
	
	public void recover_loan_asset_or_guarantee(AssetSet assetSet, AssetRecoverType assetRecoverType, BigDecimal bookingAmount,
			String journalVoucherUuid, String businessVoucherUuid, String sourceDocumentUuid, DepositeAccountInfo ledgerBookBankAccount, boolean ifRecoveryMoney) throws LackBusinessVoucherException, AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException;

	void createJVFromSourceDocument(SourceDocument sourceDocument,
			BankAccountCache cache, JournalVoucherType journalVoucherType) throws VoucherException,
			LackBusinessVoucherException, AlreadayCarryOverException,
			InvalidLedgerException, InsufficientPenaltyException;

	void createJVFromUnSignedOnlineSourceDocuments(Long companyId,
			BankAccountCache cache);
}
