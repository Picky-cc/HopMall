package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public interface LedgerItemService extends GenericService<LedgerItem>{

	public AccountSide get_balanced_account_side(List<LedgerItem> ledgerItems);
	public AccountSide get_balanced_account_side(LedgerItem ledgerItem);
	public boolean isBalanced(List<LedgerItem> ledgerItems);
	public BigDecimal getBalancedAmount(String ledgerBookNo, String accountName,String firstPartyId, String sndPartyId, String relatedfstAssetUuid, String realtedContractUuid);
	
	public LedgerTradeParty getAssetsImportLedgerTradeParty(FinancialContract financialContract, Customer customer);
	public LedgerTradeParty getOnlineDeductBanksavingLedgerTradeParty(AssetSet assetSet);
	public LedgerTradeParty getGuranteLedgerTradeParty(Order order);
	public LedgerTradeParty getPayableLedgerTradeParty(Order order);
	public LedgerTradeParty getGuranteLedgerTradeParty(AssetSet assetSet);
	public LedgerTradeParty getPayableLedgerTradeParty(AssetSet assetSet);
	public LedgerTradeParty getCustomerPayBankSavingLedgerTradeParty(Order order);
	public LedgerTradeParty getAppPayBankSavingLedgerTradeParty(Order order);
	public LedgerTradeParty getCustomerPayBankSavingLedgerTradeParty(AssetSet assetSet);
	public LedgerTradeParty getAppPayBankSavingLedgerTradeParty(AssetSet assetSet);
	public AssetCategory getDepositAssetCategoryByCustomer(Customer customer);
	
	public boolean is_zero_balanced_account(String accountName, LedgerBook ledgerBookNo, AssetCategory asset);
	public boolean is_total_balance_zero_accounts(List<String> accountName, LedgerBook ledgerBookNo, AssetCategory asset);
	public boolean is_zero_balanced_overdue_asset(LedgerBook ledgerBookNo, AssetCategory asset);
	
	public List<LedgerItem> get_booked_ledgers_of_asset(LedgerBook book, String AssetUUid);
	public List<LedgerItem> get_all_ledgers_of_asset(LedgerBook book,AssetCategory assetCategory);
	public List<LedgerItem> get_ledgers_of_asset_in_taccount(LedgerBook book, String AssetUUid,  String AccountName,int cycle);
	public List<LedgerItem> get_booked_ledgers_of_asset_in_taccount(String AccountName,LedgerBook book,String AssetUUid);
	
	public List<LedgerItem> get_booked_ledgers_of_asset_in_taccounts(List<String> accountNameList,LedgerBook book,String AssetUUid);
	
	public List<LedgerItem> get_CarriedOver_ledgers_of_asset_in_taccount(LedgerBook book,String AssetUUid,String AccountName);
	public List<LedgerItem> get_all_ledgers_of_asset_in_taccount(LedgerBook book,String AssetUUid,String AccountName);
	public LedgerItem get_ledger_item(String bookNo, String ledgerItemUuid, int cycle);
	public List<LedgerItem> get_amortizable_loan_ledger_at_date(LedgerBook book, Date now);

	public List<LedgerItem> get_booked_ledgers_in_TAccount(LedgerBook book, TAccountInfo account);
	public List<LedgerItem> get_all_ledgers_in_TAccount(LedgerBook book, TAccountInfo account);
		public List<LedgerItem> batch_carry_over(LedgerBook book,
			AssetCategory assetCategory, LedgerTradeParty party,
			List<LedgerItem> bookedLedger, HashMap<String,String> AccountName,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid) throws InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException;

	public List<LedgerItem> batch_carry_over_to_bank_saving_v2(LedgerBook book,
			AssetCategory assetCategory, LedgerTradeParty party,
			List<LedgerItem> bookedLedger, final HashMap<String,String> debitAccountToAccountMappingTable,final HashMap<String,Map.Entry<String,String> > debit_credit_trigger_table,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid,BigDecimal totalBankSavingAmount, Map<String, Integer> accountNamePriority) throws InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException;
	
	public List<LedgerItem> partial_carry_over_One_To_One(List<LedgerItem> fromLedgerList,LedgerBook book,AssetCategory forwardAsset,LedgerTradeParty forwardParty,String toAccountName,String journalVoucher,String businesVoucher,String sourceDocument,
			BigDecimal voucherBookAmount) throws InsufficientBalanceException, InvalidLedgerException;
	
	public Map<String, LedgerItem> book_M_Debit_M_Credit_ledgers(
			LedgerBook book, AssetCategory assetCategory, LedgerTradeParty party,
			HashMap<String, BigDecimal> dispersTable);
	
	public String build_book_3lvl_lifecycle_filter(LedgerBook book,
			List<TAccountInfo> accountList, int ordinal);
	public List<LedgerItem> get_ledgers_by(List<String> ledgerItems);
	
	
	
	public List<LedgerItem> get_ledgers_and_forward_ledgers_by_jvs(String ledgerBookNo, List<String> journalVoucherUuid);
	public abstract void write_off_asset_in_book(LedgerBook book,
			AssetCategory asset, String JounarVoucher, String BusinessVoucher, String SourceDocument)
			throws InvalidWriteOffException;
	public abstract void write_off_ledgers_accounts(LedgerBook book,AssetCategory assetCategory, String accountName, String countAccountName) throws InvalidWriteOffException;
	
	public void lapse_guarantee_of_asset(LedgerBook book, String assetSetUuid, AssetCategory assetCategory);
	public abstract void roll_back_ledgers_by_voucher(LedgerBook book,
			AssetCategory asset, String JounarVoucher, String BusinessVoucher, String SourceDocument);
	public abstract BigDecimal get_balance_of_account(List<String> accountNameList, LedgerBook ledgerBook,
			AssetCategory asset);
	public abstract BigDecimal get_absolute_alance_of_account(String AccountName,LedgerBook book, AssetCategory asset);
	public abstract Map<String,LedgerItem> book_M_Debit_M_Credit_ledgers_V2(LedgerBook book,
			AssetCategory asset, AccountDispersor accountDispersor);
	
	public boolean exsitsIndependentsRemittanceBy(String ledgerBookNo, String sdUuid, BigDecimal amount);
}