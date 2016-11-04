package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;

public interface LedgerBookHandler {
	
	public void amortize_loan_asset_to_receivable(LedgerBook book, AssetSet loan_asset)throws AlreadayCarryOverException, InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException;

	
	public void recover_receivable_loan_asset(LedgerBook book, AssetSet loan_asset,
			String journalVoucherUUid,String businessVoucherUUid, 
			String sourceDocumentUUid, LedgerTradeParty payableparty, BigDecimal totalAmount, DepositeAccountInfo depositeAccount, boolean ifRecoveryMoney)throws LackBusinessVoucherException,AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException;

	public void repurchase_to_payable(LedgerBook book, AssetSet loan_asset,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid, LedgerTradeParty payableparty) throws InsufficientBalanceException,
			InvalidCarryOverException, InvalidLedgerException;
	
	public List<LedgerItem> get_overdue_receivable_loan_asset_at(
			LedgerBook book, Date now);

	public void classify_receivable_loan_asset_to_overdue(Date baseLine,LedgerBook book,AssetSet asset)throws AlreadayCarryOverException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException;

	public void book_receivable_load_guarantee_and_assets_sold_for_repurchase(LedgerBook book, AssetCategory assetCategory, LedgerTradeParty guranteeparty, BigDecimal bookingAmount);
	
	public void recover_receivable_guranttee(LedgerBook book,
			AssetSet asset, String journalVoucherUUid,
			String businessVoucherUUid, String sourceDocumentUUid, LedgerTradeParty gurranteeParty, LedgerTradeParty payableparty, DepositeAccountInfo ledgerBookBankAccount, BigDecimal voucherAmount, boolean ifRecoveryMoney)throws AlreadayCarryOverException, LackBusinessVoucherException,InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException;

	
	public void claim_penalty_on_overdue_loan(LedgerBook book,
			 AssetSet loan_asset,  AssetValuationDetail penaltyDetail)throws InvalidLedgerException;

	public void paid_receivable_overdue_and_penalty(LedgerBook book,
			AssetSet loan_asset,  String businessVoucherUUid,
			String journalVoucherUUid, String sourceDocumentUUid,
			LedgerTradeParty payableparty, DepositeAccountInfo ledgerBookBankAccount)throws AlreadayCarryOverException, LackBusinessVoucherException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException;

	@Deprecated
	public void book_loan_asset(LedgerBook book, AssetSet loan_asset, LedgerTradeParty party)throws DuplicateAssetsException;
	
	public void book_cost_remittance_fee_and_payable_remittance_fee(AssetSet assetSet, BigDecimal amount, LedgerBook ledgerBook, LedgerTradeParty ledgerTradeParty);

	
	public void write_off_asset_set(LedgerBook book,AssetCategory assetCategory,String JounarVoucher, String BusinessVoucher, String SourceDocument) throws InvalidWriteOffException;
	
	public void write_off_asset_sets(List<String> assetSetUuids, FinancialContract financialContract);
	
	public abstract void book_loan_asset_V2(LedgerBook book, AssetSet loan_asset,
			LedgerTradeParty party) throws DuplicateAssetsException, InvalidLedgerException;
	public void book_loan_assets(String bookNo, List<AssetSet> assets, LedgerTradeParty party)throws DuplicateAssetsException;


	boolean is_zero_balanced_account(LedgerBook book, String accountName,
			AssetCategory loan_asset);
	
	public void refresh_receivable_overDue_fee(LedgerBook book, AssetCategory assetCategory,LedgerTradeParty ledgerTradeParty,Map<String, BigDecimal> overdue_fee_account_amount_map) throws InvalidLedgerException, InvalidWriteOffException;

	public void book_bank_saving_and_deposit_received(LedgerBook book, AssetCategory assetCategory,LedgerTradeParty ledgerTradeParty, DepositeAccountInfo depositeAccountInfo,
			String journaVoucherUuid,String businessVoucherUuid, String sourceDocumentUuid, List<BigDecimal> amountList);
	
	public void book_compensatory_remittance_virtual_account(LedgerBook book,LedgerTradeParty ledgerTradeParty, String journaVoucherUuid,
			String businessVoucherUuid,String sourceDocumentUuid, BigDecimal bookingAmount, AssetCategory assetoryCategory);
}

