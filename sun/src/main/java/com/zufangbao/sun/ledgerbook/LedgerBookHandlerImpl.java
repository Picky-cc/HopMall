package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanExtraChargeHandler;
@Component("ledgerBookHandler")
public class LedgerBookHandlerImpl implements LedgerBookHandler{
	
	@Autowired
	private LedgerItemService ledgerBookService;
	@Autowired
	private LedgerBookService ledger_book_service;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private RepaymentPlanExtraChargeHandler repaymentPlanExtraChargeHandler;

	private static final Log logger = LogFactory.getLog(LedgerBookHandlerImpl.class);
	
	
	@Override
	public boolean is_zero_balanced_account(LedgerBook book,String accountName, AssetCategory loan_asset)
	{
		return ledgerBookService.is_zero_balanced_account(accountName, book, loan_asset);
		
	}
	@Override
	public void book_loan_asset(LedgerBook book, AssetSet loan_asset,
			LedgerTradeParty party) throws DuplicateAssetsException {
		
		List<LedgerItem> ledgers=ledgerBookService.get_all_ledgers_of_asset_in_taccount(book, loan_asset.getAssetUuid(), ChartOfAccount.FST_UNEARNED_LOAN_ASSET);
		if(CollectionUtils.isEmpty(ledgers)==false)
		{
			throw new DuplicateAssetsException();
		}
			
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, loan_asset.getAssetInitialValue(), AccountSide.DEBIT);
		BigDecimal principle=loan_asset.getAssetPrincipalValue();
		BigDecimal interest=loan_asset.getAssetInterestValue();
		dispersor.dispers(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE, interest, AccountSide.CREDIT);
		dispersor.dispers(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING, principle, AccountSide.CREDIT);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		Map<String,LedgerItem> Ledgers=ledgerBookService.book_M_Debit_M_Credit_ledgers(book,assetCategory,party,dispersor.dispersTable());
		for(LedgerItem ledger:Ledgers.values())
		{
			ledgerBookService.save(ledger);
		}
	}

	public final static HashMap<String,String> book_loan_asset_fee_and_defferred_income_service_table=new HashMap<String,String>()
	{{
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE,ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_SERVICE_FEE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_TECH_FEE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_OTHER_FEE);
	}};
	
	@Override
	public void book_loan_asset_V2(LedgerBook book, AssetSet loan_asset,
			LedgerTradeParty party) throws DuplicateAssetsException, InvalidLedgerException {
		
		List<LedgerItem> ledgers=ledgerBookService.get_all_ledgers_of_asset_in_taccount(book, loan_asset.getAssetUuid(), ChartOfAccount.FST_UNEARNED_LOAN_ASSET);
		if(CollectionUtils.isEmpty(ledgers)==false)
		{
			throw new DuplicateAssetsException();
		}
			
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		Map<String,BigDecimal> loanMap = repaymentPlanExtraChargeHandler.getLoanFeeBy(loan_asset.getAssetUuid());
		loanMap.put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE, loan_asset.getAssetPrincipalValue());
		loanMap.put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST, loan_asset.getAssetInterestValue());

		AccountDispersor dispersor=new AccountDispersor();
		for (Map.Entry<String,BigDecimal> loanFeeEntry:loanMap.entrySet()){
			String account = loanFeeEntry.getKey();
			BigDecimal amount = loanFeeEntry.getValue();
			String counterAccount = book_loan_asset_fee_and_defferred_income_service_table.get(account);
			if(StringUtils.isEmpty(counterAccount)){
				throw new InvalidLedgerException();
			}
			if(amount==null || amount.compareTo(BigDecimal.ZERO)==0){
				continue;
			}
			dispersor.dispers(account,amount , AccountSide.DEBIT);
			dispersor.dispers(counterAccount, amount, AccountSide.CREDIT);
		}
		
		Map<String,LedgerItem> Ledgers=ledgerBookService.book_M_Debit_M_Credit_ledgers(book,assetCategory,party,dispersor.dispersTable());
		
		for(LedgerItem ledger:Ledgers.values())
		{
			ledgerBookService.save(ledger);
		}
	}
	
	@Override
	public void book_loan_assets(String bookNo, List<AssetSet> assets, LedgerTradeParty party){
		try {
			LedgerBook book = ledger_book_service.getBookByBookNo(bookNo);
			for (AssetSet assetSet : assets) {
				book_loan_asset_V2(book, assetSet, party);
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error(" book loan asset occur error,bookNo["+bookNo+"],party["+party+"].");
			throw e;
		}
	}
	
	public final static HashMap<String,String> unearned_loan_to_recievable_carry_over_table=new HashMap<String,String>()
	{{
		put(ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_SERVICE_FEE,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_SERVICE_FEE);
		put(ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_TECH_FEE,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_TECH_FEE);
		put(ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_OTHER_FEE,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_OTHER_FEE);
		put(ChartOfAccount.SND_DEFERRED_INCOME_FEE,ChartOfAccount.SND_REVENUE_INCOME_FEE);
		put(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,ChartOfAccount.SND_REVENUE_INTEREST);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE);
		put(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE);
		put(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET);
		
	}};
	@Override
	public void amortize_loan_asset_to_receivable(LedgerBook book,AssetSet loan_asset) throws AlreadayCarryOverException, InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException {
		List<LedgerItem> bookedUnearnedledgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, book, loan_asset.getAssetUuid());
		List<LedgerItem> interestEstimateledgers_and_defferredFeeLedgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccounts
				(Arrays.asList(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,ChartOfAccount.SND_DEFERRED_INCOME_FEE), book, loan_asset.getAssetUuid());
		
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		if(CollectionUtils.isEmpty(bookedUnearnedledgers))
			throw new InsufficientBalanceException();
		//interest or principal may be zero separately. do not use method(is_zero_balanced_account) on principal or interest;
		if(ledgerBookService.is_zero_balanced_account(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, book, assetCategory))
			throw new InsufficientBalanceException();
		LedgerItem unearnedledger=bookedUnearnedledgers.get(0);
		LedgerTradeParty party=new LedgerTradeParty();
		party.setFstParty(unearnedledger.getFirstPartyId());
		party.setSndParty(unearnedledger.getSecondPartyId());
		List<LedgerItem> fromLedgerList=new ArrayList<LedgerItem>();
		
		fromLedgerList.addAll(bookedUnearnedledgers);
		fromLedgerList.addAll(interestEstimateledgers_and_defferredFeeLedgers);
		
		List<LedgerItem> carried_over_Ledgers=ledgerBookService.batch_carry_over(book,assetCategory,party,fromLedgerList,unearned_loan_to_recievable_carry_over_table,null,null,null);
		for(LedgerItem ledger:carried_over_Ledgers)
		{
			if(ledger.lastAccountName().equals(ChartOfAccount.SND_REVENUE_INTEREST))
				ledger.setSecondPartyId("");
			ledgerBookService.save(ledger);
		}
	}
	
	private String guardBeforeLoanRecover(LedgerBook bookNo, AssetCategory asset,String journalVoucherUUid,
			String businessVouhcer,
			String sourceDocumentUUid)
			throws InvalidLedgerException, AlreadayCarryOverException,
			LackBusinessVoucherException {
		String currentLoanAccount;
		boolean isReceivableAssetZero = ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, bookNo, asset);
		boolean isOverDueZero = ledgerBookService.is_zero_balanced_overdue_asset(bookNo, asset);
		if(isReceivableAssetZero==true&& isOverDueZero==true){
			throw new AlreadayCarryOverException();
		}
		if(isOverDueZero==false && isReceivableAssetZero==true) {
				currentLoanAccount=ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET;
		} 
		else  if(isOverDueZero==true&&isReceivableAssetZero==false)
		{
			currentLoanAccount=ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET;
		}
		else
			throw new InvalidLedgerException();
		guardForVouchers(journalVoucherUUid,businessVouhcer,sourceDocumentUUid);

		return currentLoanAccount;
	}
	public void guardForVouchers(String journalVoucher,String businessVouhcer,String sourceDocumentUUid) throws LackBusinessVoucherException
	{
		if(StringUtils.isEmpty(journalVoucher)
				&&StringUtils.isEmpty(businessVouhcer)&&StringUtils.isEmpty(sourceDocumentUUid))
			throw new LackBusinessVoucherException();
	}

	private LedgerImporter importer=new LedgerImporter();
	
	
	private boolean gurranteeRecovered(List<LedgerItem> gurranteeLedgers)
	{
		if(CollectionUtils.isEmpty(gurranteeLedgers))return false;
		for(LedgerItem item:gurranteeLedgers) {
			if(item.getLifeCycle()==LedgerLifeCycle.CARRY_OVER)
				return true;
		}
		return false;
	}
	
	
public final static HashMap<String,String> recievable_loan_to_bank_saving_table=new HashMap<String,String>()
			{{
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_LOAN_SERVICE_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_TECH_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_OTHER_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE,ChartOfAccount.FST_BANK_SAVING);
			}};
			
public final static HashMap<String,Map.Entry<String,String>> credit_debit_trigger_table =new  HashMap<String,Map.Entry<String,String>>()
{{
	put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,new DefaultMapEntry
		(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING));
	put(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,new DefaultMapEntry
		(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,ChartOfAccount.SND_REVENUE_INTEREST));//兼容农分期单笔应收模式
	
}};
public final static HashMap<String,String> repurchase_to_payable_table=new HashMap<String,String>()
			{{
				put(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,ChartOfAccount.SND_PAYABLE_REPURCHASE);
				put(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING,ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY);
				
			}};
public final static HashMap<String,String> gurrantee_to_bank_saving_table=new HashMap<String,String>()
			{{
						put(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE,ChartOfAccount.FST_BANK_SAVING);
												
			}};
		
			
	private final static HashMap<String,Integer> recievable_loan_carry_over_piority=new HashMap<String,Integer>()
	{{
		put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE,0);
		put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST,1);
		put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE,2);
		put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE,3);
		put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE,4);
		
		
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE,0);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST,1);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_LOAN_SERVICE_FEE,2);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_TECH_FEE,3);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_OTHER_FEE,4);
		
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION,5);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE,6);
		put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE,7);
		put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,8);
		
		put(ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE,9);
		put(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,10);
		put(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,10);
		
	}};
	
	
	public final static HashMap<String,String> receivable_revenue_overdue_fee_booking_table=new HashMap<String,String>()
			{{
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OBLIGATION);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_SERVICE_FEE);
				put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OTHER_FEE);
				put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE);
			}};
	@Override
	public void recover_receivable_loan_asset(LedgerBook book,
			 AssetSet loan_asset, String journalVoucherUUid,String businessVoucherUUid,
			 String sourceDocumentUUid, LedgerTradeParty payableparty, BigDecimal totalAmount, DepositeAccountInfo depositeAccount, boolean ifRecoveryMoney)
			throws LackBusinessVoucherException, AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		HashMap<String, String> recievable_to_bank_saving_carry_over_table_with_bank = replace_bank_account(recievable_loan_to_bank_saving_table,depositeAccount);
		
		if(totalAmount==null) totalAmount = BigDecimal.ZERO;
		
		String currentLoanAccount;
		currentLoanAccount = guardBeforeLoanRecover(book,assetCategory,journalVoucherUUid,businessVoucherUUid,sourceDocumentUUid);
		
		List<String> accountNameList = new ArrayList<String>();
		if(currentLoanAccount.equals(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET)){
			accountNameList.add(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET);
			accountNameList.add(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE);
			accountNameList.add(ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE);
			
		} else if(currentLoanAccount.equals(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET))
		{
			accountNameList.add(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET);
			accountNameList.add(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE);
			accountNameList.add(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY);
			accountNameList.add(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE);
			accountNameList.add(ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE);
		}
		List<LedgerItem> recover_ledgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccounts(accountNameList, book, loan_asset.getAssetUuid());
		
		LedgerTradeParty party=depositeAccount.getDeopsite_account_owner_party();
		recover_loan_asset(book,loan_asset,businessVoucherUUid,journalVoucherUUid,sourceDocumentUUid,
				recover_ledgers,recievable_to_bank_saving_carry_over_table_with_bank,credit_debit_trigger_table,
				totalAmount, party, ifRecoveryMoney);
		
	}

	@Override
	public void repurchase_to_payable(LedgerBook book, AssetSet loan_asset,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid, LedgerTradeParty payableparty) throws InsufficientBalanceException,
			InvalidCarryOverException, InvalidLedgerException {
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		boolean isRecovered = ledgerBookService.is_total_balance_zero_accounts(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,
				ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE), book, assetCategory);
		
		if(isRecovered==false) return;
		List<LedgerItem> repurchaseLedgers_investmentIncomingLedgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccounts
				(Arrays.asList(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING), book, loan_asset.getAssetUuid());
		
		List<LedgerItem> carried_over_loan_Ledgers=ledgerBookService.batch_carry_over(book,assetCategory,payableparty,
				repurchaseLedgers_investmentIncomingLedgers,repurchase_to_payable_table,journalVoucherUUid,businessVoucherUUid,
				sourceDocumentUUid);
		
		for(LedgerItem ledger:carried_over_loan_Ledgers) {
				ledgerBookService.save(ledger);
		}
	}

	private HashMap<String, String> replace_bank_account(final HashMap<String,String> table,
			DepositeAccountInfo ledgerBookBankAccount) {
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank=new HashMap<String,String>();
		recievable_to_bank_saving_carry_over_table_with_bank.putAll(table);
		String toBankAccoutName = ledgerBookBankAccount==null?ChartOfAccount.FST_BANK_SAVING:ledgerBookBankAccount.getDeposite_account_name();
		for(String key:recievable_to_bank_saving_carry_over_table_with_bank.keySet())
		{
			String dest=recievable_to_bank_saving_carry_over_table_with_bank.get(key);
			if(dest.equals(ChartOfAccount.FST_BANK_SAVING))
				recievable_to_bank_saving_carry_over_table_with_bank.put(key, toBankAccoutName);
		}
		return recievable_to_bank_saving_carry_over_table_with_bank;
	}
	
	
	private void recover_loan_asset(LedgerBook book,
			 AssetSet loan_asset, String businessVoucherUUid,
			String journalVoucherUUid, String sourceDocumentUUid,List<LedgerItem> bookedLoanledgers,
			HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank, HashMap<String, Map.Entry<String,String>> credit_debit_trigger_table,BigDecimal totalAmount, LedgerTradeParty ledgerTradeParty, boolean ifRecoveryMoney) throws InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException
	{
		List<LedgerItem> carryoverLedgers=new ArrayList();
		carryoverLedgers.addAll(bookedLoanledgers);
		/*LedgerTradeParty party=new LedgerTradeParty();
		this.importer.extractParty(party, carryoverLedgers.get(0));
		party.setSndParty("");*/
		List<LedgerItem> allLedgers=new ArrayList<LedgerItem>();
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		List<LedgerItem> carried_over_loan_Ledgers=ledgerBookService.batch_carry_over_to_bank_saving_v2(book,assetCategory,ledgerTradeParty,
				carryoverLedgers,recievable_to_bank_saving_carry_over_table_with_bank,credit_debit_trigger_table,journalVoucherUUid,businessVoucherUUid,
				sourceDocumentUUid,totalAmount, recievable_loan_carry_over_piority);
		
		for(LedgerItem ledger:carried_over_loan_Ledgers) {
			if(!ifRecoveryMoney){
				if(ledger.getFirstAccountName().equals(ChartOfAccount.FST_BANK_SAVING)
						|| ledger.getFirstAccountName().equals(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS)){
					continue;
				}
			}
			
			if(ledger.lastAccountName().equals(ChartOfAccount.SND_REVENUE_INTEREST))
				ledger.setSecondPartyId("");
			ledgerBookService.save(ledger);
		}
		
		
	}
	
	

	public List<LedgerItem> getbookedRecievableLoanLedgers(Date baseLine,LedgerBook book,AssetSet loan_asset)
	{
		String queryString = "from LedgerItem where default_date<:defaultDate and related_lv_1_asset_uuid='"+loan_asset.getAssetUuid()+"' and";
		TAccountInfo unearned_account=ChartOfAccount.EntryBook().get(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET);
		
		queryString+=ledgerBookService.build_book_3lvl_lifecycle_filter(book,Arrays.asList(unearned_account),LedgerLifeCycle.BOOKED.ordinal());
		HashMap<String,Object> params=new HashMap();
		params.put("defaultDate", baseLine);
		List<LedgerItem> bookedRecievableLoanLedgers = genericDaoSupport.searchForList(queryString, params);
		return bookedRecievableLoanLedgers;
	
	}

	public final static HashMap<String,String> recievable_loan_to_overdue_carry_over_tabLe=new HashMap<String,String>()
			{{
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_LOAN_SERVICE_FEE);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_TECH_FEE);
				put(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_OTHER_FEE);
				put(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET);
			}};
	@Override
	public void classify_receivable_loan_asset_to_overdue(Date baseLine,LedgerBook book,AssetSet loan_asset)throws AlreadayCarryOverException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException{
		List<LedgerItem> bookedRecievableLoanLedgers=getbookedRecievableLoanLedgers(baseLine,book,loan_asset);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		boolean isReceivableAssetZero = ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, book,assetCategory);
		if(isReceivableAssetZero==true)
			throw new InvalidLedgerException();
		
		LedgerItem recievableLoanLedgers=bookedRecievableLoanLedgers.get(0);
		LedgerTradeParty party=new LedgerTradeParty();
		importer.extractParty(party, recievableLoanLedgers);
		
		List<LedgerItem> AllLedgers=ledgerBookService.batch_carry_over(book, assetCategory, party, bookedRecievableLoanLedgers, recievable_loan_to_overdue_carry_over_tabLe, 
				null, null, null);
		for(LedgerItem ledger:AllLedgers)
		{
			ledgerBookService.save(ledger);
		}
		
	}
	
	@Override
	public void book_receivable_load_guarantee_and_assets_sold_for_repurchase(LedgerBook book, AssetCategory assetCategory, LedgerTradeParty guranteeparty, BigDecimal bookingAmount){
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, bookingAmount, AccountSide.DEBIT);
		dispersor.dispers(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,bookingAmount, AccountSide.CREDIT);
		Map<String,LedgerItem> Ledgers=ledgerBookService.book_M_Debit_M_Credit_ledgers(book,assetCategory,guranteeparty,dispersor.dispersTable());
		for(LedgerItem ledger:Ledgers.values())
		{
			ledgerBookService.save(ledger);
		}
	}
	
	@Override
	public void recover_receivable_guranttee(LedgerBook book,
			AssetSet loan_asset, String journalVoucherUUid,
			String businessVoucherUUid, String sourceDocumentUUid,
			LedgerTradeParty gurranteeParty, LedgerTradeParty payableparty, DepositeAccountInfo ledgerBookBankAccount, BigDecimal voucherAmount, boolean ifRecoveryMoney)
			throws AlreadayCarryOverException, LackBusinessVoucherException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		guardForVouchers(journalVoucherUUid,businessVoucherUUid,sourceDocumentUUid);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);

		if(ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book, assetCategory)){
			throw new AlreadayCarryOverException();
		}
		List<LedgerItem> gurantteeLedgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book, loan_asset.getAssetUuid());
		List<LedgerItem> allLedgers=new ArrayList();
		allLedgers.addAll(gurantteeLedgers);
		
		LedgerItem bookedGurranteeledger=gurantteeLedgers.get(0);
		LedgerTradeParty party=new LedgerTradeParty();
		party.setFstParty(bookedGurranteeledger.getFirstPartyId());
		party.setSndParty("");
		
		HashMap<String,String> guaranttee_to_bank_saving_with_account=replace_bank_account(this.gurrantee_to_bank_saving_table, ledgerBookBankAccount);
		List<LedgerItem> carried_over_guranttee=ledgerBookService.batch_carry_over_to_bank_saving_v2(book,assetCategory,party,
				gurantteeLedgers,guaranttee_to_bank_saving_with_account,new HashMap<String,Map.Entry<String,String>>(),journalVoucherUUid,businessVoucherUUid,
				sourceDocumentUUid,voucherAmount, new HashMap<String,Integer>());
		for(LedgerItem ledger:carried_over_guranttee) {
			if(!ifRecoveryMoney){
				if(ledger.getFirstAccountName().equals(ChartOfAccount.FST_BANK_SAVING)
						||ledger.getFirstAccountName().equals(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS)){
					continue;
				}
			}
			ledgerBookService.save(ledger);
		}
		
	}
	
	
	


	public void claim_penalty_on_overdue_loan(LedgerBook book,
			 AssetSet loan_asset, AssetValuationDetail penaltyDetail)throws InvalidLedgerException
	{
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTupPenaltyAssetToAssetCategory(loan_asset, penaltyDetail);
		
		if(ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, assetCategory))
			throw new InvalidLedgerException();
		
		List<LedgerItem> overDueledgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, loan_asset.getAssetUuid());
		LedgerTradeParty party=new LedgerTradeParty();
		this.importer.extractParty(party, overDueledgers.get(0));
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, penaltyDetail.getAmount(), AccountSide.DEBIT);
		dispersor.dispers(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE,penaltyDetail.getAmount() , AccountSide.CREDIT);
		Map<String,LedgerItem> Ledgers=ledgerBookService.book_M_Debit_M_Credit_ledgers(book,assetCategory,party,dispersor.dispersTable());
		for(LedgerItem ledger:Ledgers.values())
		{
			ledgerBookService.save(ledger);
		}
	}
	
	public static HashMap<String,String> payable_repurchase_and_penalty_to_banksaving=new HashMap<String,String>()
			{{
				put(ChartOfAccount.SND_PAYABLE_REPURCHASE,ChartOfAccount.FST_BANK_SAVING);
				put(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY,ChartOfAccount.FST_BANK_SAVING);
			}};

	@Override
	public void paid_receivable_overdue_and_penalty(LedgerBook book,
			AssetSet loan_asset,  String businessVoucherUUid,
			String journalVoucherUUid, String sourceDocumentUUid,
			LedgerTradeParty payableparty, DepositeAccountInfo ledgerBookBankAccount)throws AlreadayCarryOverException, LackBusinessVoucherException,InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException
	{
		guardForVouchers(journalVoucherUUid,businessVoucherUUid,sourceDocumentUUid);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
			
		boolean isPayableRepurchasePaid=ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, assetCategory);
		boolean isPayableCustodyPenaltyPaid=ledgerBookService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, assetCategory);
		
		if(isPayableRepurchasePaid==true&&isPayableCustodyPenaltyPaid==true)
			throw new  InsufficientBalanceException();
		
		HashMap<String,String> payable_repurchase_and_penalty_to_bankaccount=replace_bank_account(payable_repurchase_and_penalty_to_banksaving,ledgerBookBankAccount);
		
		List<LedgerItem> bookedPayableRepurchaseLedgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, loan_asset.getAssetUuid());
		List<LedgerItem> bookedPayablePenaltyLedgers=ledgerBookService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, loan_asset.getAssetUuid());
	
		LedgerTradeParty party=new LedgerTradeParty();
		LedgerItem bookedPayableRepurchaseLedger=bookedPayableRepurchaseLedgers.get(0);
		importer.extractParty(party, bookedPayableRepurchaseLedger);
		
		List<LedgerItem> all_ledgers=new ArrayList<LedgerItem>();
		all_ledgers.addAll(bookedPayableRepurchaseLedgers);
		all_ledgers.addAll(bookedPayablePenaltyLedgers);
		BigDecimal payable_repurchase_amount=ledgerBookService.get_absolute_alance_of_account(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, assetCategory);
		BigDecimal penalty_amount=ledgerBookService.get_absolute_alance_of_account(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, assetCategory);
		BigDecimal total=payable_repurchase_amount.add(penalty_amount);
		
		List<LedgerItem> paid_ledgers=ledgerBookService.batch_carry_over_to_bank_saving_v2(book, assetCategory, party, all_ledgers, payable_repurchase_and_penalty_to_bankaccount,new HashMap<String,Map.Entry<String,String>>(), journalVoucherUUid, businessVoucherUUid, sourceDocumentUUid,total, new HashMap<String,Integer>());
		for(LedgerItem ledger:paid_ledgers)
		{
			ledgerBookService.save(ledger);
		}
	}
	
	@Override
	public List<LedgerItem> get_overdue_receivable_loan_asset_at(
			LedgerBook book, Date date) {
		String queryString = "from LedgerItem where default_date<:defaultDate and ";
		TAccountInfo unearned_account=ChartOfAccount.EntryBook().get(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET);
		queryString+=ledgerBookService.build_book_3lvl_lifecycle_filter(book,Arrays.asList(unearned_account),LedgerLifeCycle.BOOKED.ordinal());
		HashMap<String,Object> params=new HashMap();
		params.put("defaultDate", date);
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, params);
		return result;
	}
	
	@Override
	public void book_cost_remittance_fee_and_payable_remittance_fee(AssetSet assetSet, BigDecimal amount, LedgerBook ledgerBook, LedgerTradeParty ledgerTradeParty) {
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuAssetCategory(assetSet,"",null);
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(ChartOfAccount.SND_COST_REMITTANCE_FEE, amount, AccountSide.DEBIT, ledgerTradeParty, "", "", "");
		dispersor.dispers(ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE, amount, AccountSide.CREDIT, ledgerTradeParty, "", "", "");
		Map<String,LedgerItem> bookedLegerMaps = ledgerBookService.book_M_Debit_M_Credit_ledgers_V2(ledgerBook, assetCategory,dispersor);
		for(LedgerItem item:bookedLegerMaps.values())
		{
			ledgerBookService.save(item);
		}
	}
	
	@Override
	public void write_off_asset_sets(List<String> assetSetUuids, FinancialContract financialContract){
		if(financialContract==null){
			return;
		}
		LedgerBook ledgerBook = ledger_book_service.getBookByBookNo(financialContract.getLedgerBookNo());
		for (String assetSetUuid : assetSetUuids) {
			try {
				AssetCategory assetCategory = new AssetCategory();
				assetCategory.setFstLvLAssetUuid(assetSetUuid);
				write_off_asset_set(ledgerBook, assetCategory,"","","");
			}catch (Exception e){
				logger.error("occur error when write_off_asset_set,with asset_set_uuid["+assetSetUuid+"].");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void write_off_asset_set(LedgerBook book,AssetCategory assetCategory,String JounarVoucher, String BusinessVoucher, String SourceDocument) throws InvalidWriteOffException {
		ledgerBookService.write_off_asset_in_book(book, assetCategory, JounarVoucher, BusinessVoucher, SourceDocument);
	}
	@Override
	public void refresh_receivable_overDue_fee(LedgerBook book, AssetCategory assetCategory, LedgerTradeParty ledgerTradeParty, Map<String, BigDecimal> overdue_fee_account_amount_map) throws InvalidLedgerException, InvalidWriteOffException {
		AccountDispersor dispersor=new AccountDispersor();
		
		for (Map.Entry<String,BigDecimal> overdue_fee_account_delta_amount_entry : overdue_fee_account_amount_map.entrySet()) {
			String asset_side_account = overdue_fee_account_delta_amount_entry.getKey();
			BigDecimal amount = overdue_fee_account_delta_amount_entry.getValue();
			
			if(!receivable_revenue_overdue_fee_booking_table.containsKey(asset_side_account)){
				throw new InvalidLedgerException();
			}
			String liability_side_account = receivable_revenue_overdue_fee_booking_table.get(asset_side_account);
			ledgerBookService.write_off_ledgers_accounts(book, assetCategory, asset_side_account, liability_side_account);
			if(amount.compareTo(BigDecimal.ZERO)==0){
				continue;
			}
			dispersor.dispers(asset_side_account, amount, AccountSide.DEBIT);
			dispersor.dispers(liability_side_account, amount, AccountSide.CREDIT);
			
		}
		
		Map<String,LedgerItem> Ledgers=ledgerBookService.book_M_Debit_M_Credit_ledgers(book,assetCategory,ledgerTradeParty,dispersor.dispersTable());
		
		for(LedgerItem ledger:Ledgers.values())
		{
			ledgerBookService.save(ledger);
		}
	}
	@Override
	public void book_bank_saving_and_deposit_received(LedgerBook book, AssetCategory assetCategory, LedgerTradeParty ledgerTradeParty,
			DepositeAccountInfo depositeAccountInfo,String journaVoucherUuid, String businessVoucherUuid, String sourceDocumentUuid, List<BigDecimal> amountList) {
		AccountDispersor dispersor=new AccountDispersor();
		
		BigDecimal totalAmount = amountList.stream().reduce(BigDecimal.ONE, (result,element)->result = result.add(element));
		dispersor.dispers(depositeAccountInfo==null?ChartOfAccount.FST_BANK_SAVING:depositeAccountInfo.getDeposite_account_name(), totalAmount, AccountSide.DEBIT, ledgerTradeParty, journaVoucherUuid, businessVoucherUuid, sourceDocumentUuid);
		for (BigDecimal amount : amountList) {
			dispersor.dispers(ChartOfAccount.FST_ACCOUNT_RECEIVED_IN_ADVANCE_CODE, amount, AccountSide.CREDIT, ledgerTradeParty, journaVoucherUuid, businessVoucherUuid, sourceDocumentUuid);
		}
		
		Map<String,LedgerItem> bookedLegerMaps = ledgerBookService.book_M_Debit_M_Credit_ledgers_V2(book, assetCategory,dispersor);
		for(LedgerItem item:bookedLegerMaps.values())
		{
			ledgerBookService.save(item);
		}
	}
	@Override
	public void book_compensatory_remittance_virtual_account(LedgerBook book, LedgerTradeParty ledgerTradeParty,
			String journaVoucherUuid, String businessVoucherUuid,
			String sourceDocumentUuid, BigDecimal bookingAmount, AssetCategory assetCategory) {
		
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_INTER_ACCOUNT_REMITTANCE, bookingAmount, AccountSide.DEBIT, ledgerTradeParty, journaVoucherUuid, businessVoucherUuid, sourceDocumentUuid);
		Map<String,LedgerItem> bookedLegerMaps = ledgerBookService.book_M_Debit_M_Credit_ledgers_V2(book, assetCategory,dispersor);
		for(LedgerItem item:bookedLegerMaps.values())
		{
			ledgerBookService.save(item);
		}
		
	}
	
	
}
