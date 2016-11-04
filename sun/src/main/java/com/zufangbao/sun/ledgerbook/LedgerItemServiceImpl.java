package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;

@Service("ledgerItemService")
public class LedgerItemServiceImpl  extends GenericServiceImpl<LedgerItem> implements LedgerItemService
{

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinancialContractService financialContractService;

	@Override
	public List<LedgerItem> get_booked_ledgers_of_asset(LedgerBook book,
			String AssetUUid) {
		String queryString = "from LedgerItem where related_lv_1_asset_uuid='"+AssetUUid+"' AND life_cycle="+LedgerLifeCycle.BOOKED.ordinal();
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, null);
		return result;
	}
	
	@Override
	public AccountSide get_balanced_account_side(LedgerItem ledgerItem){
	   List<LedgerItem> items=new ArrayList();
	   items.add(ledgerItem);
	   return get_balanced_account_side(items);
	}
	
	@Override
	public AccountSide get_balanced_account_side(List<LedgerItem> ledgerItems){
		BigDecimal balance = get_balance_of_items(ledgerItems);
		int compResult = balance.compareTo(BigDecimal.ZERO);
		
		if(compResult==0){
			return AccountSide.BOTH;
		} else if(compResult<0){
			return AccountSide.CREDIT;
		} else{
			return AccountSide.DEBIT;
		}
	}

	@Override
	public BigDecimal get_absolute_alance_of_account(String AccountName,LedgerBook book,AssetCategory asset)
	{
		BigDecimal balance=this.get_balance_of_account(Arrays.asList(AccountName),book,asset);
		return balance.abs();
				 
	}
	private BigDecimal get_absolute_balance_of_items(List<LedgerItem> ledgerItems) {
		BigDecimal balance=get_balance_of_items(ledgerItems);
		return balance==null?BigDecimal.ZERO:balance.abs();
	}
		 
	private BigDecimal get_balance_of_items(List<LedgerItem> ledgerItems) {
		BigDecimal debitAmount = BigDecimal.ZERO;
		BigDecimal creditAmount = BigDecimal.ZERO;
		for (LedgerItem ledgerItem : ledgerItems) {
			debitAmount = debitAmount.add(ledgerItem.getDebitBalance());
			creditAmount = creditAmount.add(ledgerItem.getCreditBalance());
		}
		return debitAmount.subtract(creditAmount);
	}
	
	@Override
	public boolean isBalanced(List<LedgerItem> ledgerItems){
		BigDecimal debitAmount = BigDecimal.ZERO;
		BigDecimal creditAmount = BigDecimal.ZERO;
		for (LedgerItem ledgerItem : ledgerItems) {
			debitAmount = debitAmount.add(ledgerItem.getDebitBalance());
			creditAmount = creditAmount.add(ledgerItem.getCreditBalance());
		}
		if(debitAmount.compareTo(creditAmount)==0){
			return true;
		}
		return false;
	}

	
	public List<LedgerItem> get_ledgers_of_asset_in_taccount(LedgerBook book, String AssetUUid,  String AccountName,int cycle){
		return ledgers_of_asset_in_taccount(book, AssetUUid,  Arrays.asList(AccountName),cycle);
	}

	private List<LedgerItem>  ledgers_of_asset_in_taccount(LedgerBook book, String AssetUUid,  List<String> accountNameList,int cycle)
	{
		if(book==null || CollectionUtils.isEmpty(accountNameList)) return Collections.emptyList();
		List<TAccountInfo> accountInfoList = accountNameList.stream().map(accountName -> ChartOfAccount.EntryBook().get(accountName))
				.filter(tAccountInfo -> tAccountInfo!=null).collect(Collectors.toList());
		String queryString = "from LedgerItem where related_lv_1_asset_uuid='"+AssetUUid+"' and ";
		queryString+=build_book_3lvl_lifecycle_filter(book,accountInfoList,cycle);
		HashMap<String,Object> params=new HashMap();
		params.put("lifeCycle", LedgerLifeCycle.fromValue(cycle));
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, params);
		return result;
	}
	@Override
	public List<LedgerItem> get_booked_ledgers_of_asset_in_taccount(
			String accountName, LedgerBook book, String AssetUUid) {
		List<LedgerItem> bookedLedgers = ledgers_of_asset_in_taccount(book,AssetUUid,Arrays.asList(accountName),LedgerLifeCycle.BOOKED.ordinal());
		return bookedLedgers;
	}
	
	@Override
	public List<LedgerItem> get_booked_ledgers_of_asset_in_taccounts(List<String> accountNameList,LedgerBook book,String AssetUUid){
		return ledgers_of_asset_in_taccount(book,AssetUUid,accountNameList,LedgerLifeCycle.BOOKED.ordinal());
	}
	
	@Override
	public List<LedgerItem> get_CarriedOver_ledgers_of_asset_in_taccount(
			LedgerBook book, String AssetUUid, String AccountName) {
		List<LedgerItem> carryOveredLedgers = ledgers_of_asset_in_taccount(book,AssetUUid,Arrays.asList(AccountName),LedgerLifeCycle.CARRY_OVER.ordinal());
		
		return carryOveredLedgers;

	}
	
	@Override
	public List<LedgerItem> get_all_ledgers_of_asset_in_taccount(
			LedgerBook book, String AssetUUid, String AccountName) {
		return	ledgers_of_asset_in_taccount(book,AssetUUid,Arrays.asList(AccountName),-1);	

	}
	
	@Override
	public LedgerItem get_ledger_item(String bookNo, String ledgerItemUuid, int cycle){
		if(StringUtils.isEmpty(bookNo) || StringUtils.isEmpty(ledgerItemUuid)){
			return null;
		}
		StringBuffer queryString = new StringBuffer("from LedgerItem where ledgerUuid=:ledgerUuid and ledgerBookNo=:ledgerBookNo ");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ledgerUuid", ledgerItemUuid);
		params.put("ledgerBookNo", bookNo);
		LedgerLifeCycle lifeCycle = LedgerLifeCycle.fromValue(cycle);
		if(lifeCycle!=null){
			queryString.append(" and lifeCycle=:lifeCycle");
			params.put("lifeCycle", lifeCycle);
		}
		List<LedgerItem> ledgerItemList = genericDaoSupport.searchForList(queryString.toString(), params);
		if(CollectionUtils.isEmpty(ledgerItemList)){
			return null;
		}
		return ledgerItemList.get(0);
	}
	
	@Override
	public List<LedgerItem> get_amortizable_loan_ledger_at_date(LedgerBook book, Date date) {
		String queryString = "from LedgerItem where amortized_date<=:amortizedDate and";
		TAccountInfo unearned_account=ChartOfAccount.EntryBook().get(ChartOfAccount.FST_UNEARNED_LOAN_ASSET);
		
		queryString+=build_book_3lvl_lifecycle_filter(book,Arrays.asList(unearned_account),LedgerLifeCycle.BOOKED.ordinal());
		HashMap<String,Object> params=new HashMap();
		params.put("amortizedDate", date);
	
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, params);
		
		
		return result;
	}

	@Override
	public List<LedgerItem> get_booked_ledgers_in_TAccount(
			LedgerBook book, TAccountInfo account) {
		return get_ledgers_in_TAccount(book,account,LedgerLifeCycle.BOOKED.ordinal());

	}

	private String build_single_3lvl_filter(TAccountInfo account){
		String accountFilter= "";
		if(account.getThirdLevelAccount()!=null&&!StringUtils.isEmpty(account.getThirdLevelAccount().getAccountCode())){
			accountFilter =" (third_Account_uuid='"+account.getThirdLevelAccount().getAccountCode()+"') ";
		}else if(account.getSecondLevelAccount()!=null&&!StringUtils.isEmpty(account.getSecondLevelAccount().getAccountCode())){
			accountFilter =" (second_account_uuid='"+account.getSecondLevelAccount().getAccountCode()+"') ";
		}else if(account.getFirstLevelAccount()!=null&&!StringUtils.isEmpty(account.getFirstLevelAccount().getAccountCode())) {
			accountFilter =" (first_account_uuid='"+account.getFirstLevelAccount().getAccountCode()+"') ";
		}
		return accountFilter;
	}
	
	private String build_3lvl_filters(List<TAccountInfo> acountList){
		String totalSql = acountList.stream().filter(account ->account!=null)
				.map(account -> build_single_3lvl_filter(account))
				.filter(sql->!StringUtils.isEmpty(sql)).collect(Collectors.joining(" OR "));
		
		if(!StringUtils.isEmpty(totalSql)){
			return " AND ("+totalSql+")";
		}
		return "";
	}
	
	public String build_book_3lvl_lifecycle_filter(LedgerBook bookbreif,List<TAccountInfo> account,int lifeCycle)
	{
		String accountFilter="";
		if(bookbreif==null||StringUtils.isEmpty(bookbreif.getLedgerBookNo())) return" 1!=1 ";
		
		accountFilter+="  ledger_book_no='"+bookbreif.getLedgerBookNo()+"'";
		
		if(lifeCycle>=0) {
			accountFilter +=" AND lifeCycle="+lifeCycle;
		}
		accountFilter += build_3lvl_filters(account);
		
		return accountFilter;
	}
	
	private List<LedgerItem> get_ledgers_in_TAccount(LedgerBook book, TAccountInfo account,int lifeCycle)
	{
		String queryString = "from LedgerItem where ";
		queryString+=build_book_3lvl_lifecycle_filter(book,Arrays.asList(account),lifeCycle);
		HashMap<String,Object> params=new HashMap();
		params.put("lifeCycle", LedgerLifeCycle.fromValue(lifeCycle));
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, params);
		return result;
	}
	

	@Override
	public List<LedgerItem> get_all_ledgers_in_TAccount(LedgerBook book,
			TAccountInfo account) {
		return get_ledgers_in_TAccount(book,account,-1);

	}




	
	private LedgerImporter importer=new LedgerImporter();
	
	public Map<String,LedgerItem> book_M_Debit_M_Credit_ledgers(LedgerBook book,AssetCategory asset,LedgerTradeParty party,HashMap<String,BigDecimal> AccountLedger)
	{
		 Map<String,LedgerItem>  result=new  HashMap<String,LedgerItem>();
		Set<String> allAccounts=AccountLedger.keySet();
		String batchUUid=UUID.randomUUID().toString();
		BigDecimal minusOne=BigDecimal.valueOf(-1);
		for(String accountName:allAccounts)
		{
			TAccountInfo account=ChartOfAccount.EntryBook().get(accountName);
			LedgerItem ledger=new LedgerItem();
			importer.copyTAccount(account,ledger);
			importer.copyParty(party, ledger);
			if(asset!=null){
				importer.copyAssetSet(asset, ledger);
			}
			importer.copyBookBreif(book, ledger);
			importer.copyEmptyVoucher(ledger);
			BigDecimal	Amount=AccountLedger.get(accountName);
			if(Amount.compareTo(BigDecimal.ZERO)<0)
			{
				ledger.setCreditBalance(Amount.negate());
				ledger.setDebitBalance(BigDecimal.ZERO);
			}
			else
			{
				ledger.setDebitBalance(Amount);
				ledger.setCreditBalance(BigDecimal.ZERO);
			}
			ledger.setBatchSerialUuid(batchUUid);
			ledger.setBookInDate(new Date());
			ledger.setLifeCycle(LedgerLifeCycle.BOOKED);
			result.put(accountName, ledger);
		}
		return result;
	}	
	
	@Override
	public Map<String,LedgerItem> book_M_Debit_M_Credit_ledgers_V2(LedgerBook book,AssetCategory asset,AccountDispersor accountDispersor)
	{
		 Map<String,LedgerItem>  result=new  HashMap<String,LedgerItem>();
		 HashMap<String,BigDecimal> AccountLedger=accountDispersor.dispersTable();
		 HashMap<String,LedgerTradeParty> AccountParties=accountDispersor.dispersParties();
		 HashMap<String,VoucherSet> voucherSet=accountDispersor.dispersVoucherSet();
		 
		Set<String> allAccounts=AccountLedger.keySet();
		String batchUUid=UUID.randomUUID().toString();
		BigDecimal minusOne=BigDecimal.valueOf(-1);
		for(String accountName:allAccounts)
		{
			TAccountInfo account=ChartOfAccount.EntryBook().get(accountName);
			LedgerItem ledger=new LedgerItem();
			importer.copyTAccount(account,ledger);
			LedgerTradeParty party=AccountParties.get(accountName);
			importer.copyParty(party, ledger);
			if(asset!=null){
				importer.copyAssetSet(asset, ledger);
			}
			importer.copyBookBreif(book, ledger);
			importer.copyEmptyVoucher(ledger);
			BigDecimal	Amount=AccountLedger.get(accountName);
			if(Amount.compareTo(BigDecimal.ZERO)<0)
			{
				ledger.setCreditBalance(Amount.negate());
				ledger.setDebitBalance(BigDecimal.ZERO);
			}
			else
			{
				ledger.setDebitBalance(Amount);
				ledger.setCreditBalance(BigDecimal.ZERO);
			}
			ledger.setBatchSerialUuid(batchUUid);
			ledger.setBookInDate(new Date());
			ledger.setLifeCycle(LedgerLifeCycle.BOOKED);
			VoucherSet vset=voucherSet.get(accountName);
			if(CollectionUtils.isEmpty(voucherSet)==false)
			{
				ledger.setJournalVoucherUuid(vset.getJournalVoucherUuid());
				ledger.setBusinessVoucherUuid(vset.getBusinessVoucherUuid());
				ledger.setSourceDocumentUuid(vset.getSourceDocumentUuid());
				
			}
			result.put(accountName, ledger);
		}
		return result;
	}	
	
	
	
	
	public List<LedgerItem> batch_carry_over(LedgerBook book,
			AssetCategory assetCategory, LedgerTradeParty party,
			List<LedgerItem> bookedLedger, HashMap<String,String> AccountMappingTable,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid) throws InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException
			{
		
				List<LedgerItem> forwardResultLedgerList=new ArrayList();
				Map<String,List<LedgerItem>> accountNameMutilItemsMap = bookedLedger.stream().collect(Collectors.groupingBy(LedgerItem::lastAccountName,TreeMap::new,Collectors.toList()));
				for(Map.Entry<String, List<LedgerItem>> entry :accountNameMutilItemsMap.entrySet()){
					String fromAccount = entry.getKey();
					List<LedgerItem> fromLedgerList=entry.getValue();
					BigDecimal balance=get_absolute_balance_of_items(fromLedgerList);
					if(BigDecimal.ZERO.compareTo(balance)==0){
						continue;
					}
					if(StringUtils.isEmpty(fromAccount)) throw new InvalidCarryOverException();
					String forwardAccount=AccountMappingTable.get(fromAccount);
					if(StringUtils.isEmpty(forwardAccount)) throw new InvalidCarryOverException();
					
					List<LedgerItem>  forwardList=this.partial_carry_over_One_To_One(fromLedgerList, book, assetCategory, party, forwardAccount, journalVoucherUUid, businessVoucherUUid, sourceDocumentUUid, balance);
					forwardResultLedgerList.addAll(forwardList);
				}
				return forwardResultLedgerList;
			}
	
	private List<LedgerItem> sort_by_piority(List<LedgerItem> item_list,HashMap<String,Integer> serial_list)
	{
		item_list.sort(new Compar(serial_list));
		return item_list;
		
	}
	class Compar implements Comparator<LedgerItem>
	{
		private HashMap<String,Integer> table;
		public Compar(HashMap<String,Integer> list) {
			table=list;
		}
		
        public int compare(LedgerItem o1, LedgerItem o2) {
        	int o1_piority=Integer.MAX_VALUE;
        	int o2_piority=Integer.MAX_VALUE;
        	
        	if(o1.lastAccountName()!=null&&table.get(o1.lastAccountName())!=null)
        		o1_piority=table.get(o1.lastAccountName());
        	if(o2.lastAccountName()!=null&&table.get(o2.lastAccountName())!=null)
        		o2_piority=table.get(o2.lastAccountName());
        	
        		
        	if( o1_piority>o2_piority) return 1;
        	else if( o1_piority<o2_piority) return -1;
        	else return 0;
        	
        }
    };
    class CompareAccountName implements Comparator<String>
	{
		private Map<String,Integer> table;
		public CompareAccountName(Map<String,Integer> list) {
			table=list;
		}
		
        public int compare(String o1, String o2) {
        	int o1_piority=Integer.MAX_VALUE;
        	int o2_piority=Integer.MAX_VALUE;
        	
        	if(o1!=null&&table.get(o1)!=null)
        		o1_piority=table.get(o1);
        	if(o2!=null&&table.get(o2)!=null)
        		o2_piority=table.get(o2);
        	
        		
        	if( o1_piority>o2_piority) return 1;
        	else if( o1_piority<o2_piority) return -1;
        	else return 0;
        	
        }
    };
	
	public List<LedgerItem> batch_carry_over_to_bank_saving_v2(LedgerBook book,
			AssetCategory assetCategory, LedgerTradeParty party,
			List<LedgerItem> bookedLedger, final HashMap<String,String> debitAccountToAccountMappingTable,final HashMap<String,Map.Entry<String,String> > debit_credit_trigger_table,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid,BigDecimal totalBankSavingAmount, Map<String, Integer> accountNamePriority) throws InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException
			{
				boolean bank_saving_split_mode=false;
				List<LedgerItem> forwardResultLedgerList=new ArrayList();
				BigDecimal rest=totalBankSavingAmount.abs();
				HashMap<LedgerItem,BigDecimal> to_bank_saving_ledgers=new HashMap();
				Map<String,List<LedgerItem>> accountNameMutilItemsMap = bookedLedger.stream().collect(Collectors.groupingBy(LedgerItem::lastAccountName,TreeMap::new,Collectors.toList()));
				List<String> accountNameList = new ArrayList<String>(accountNameMutilItemsMap.keySet());
				accountNameList.sort(new CompareAccountName(accountNamePriority));
				
				HashMap<String,BigDecimal> amount_split_table=new HashMap<String,BigDecimal>();
				HashMap<String,String> AccountToAccountMappingTableClone=new HashMap<String,String>();
				AccountToAccountMappingTableClone.putAll(debitAccountToAccountMappingTable);
				
				amount_split_table = extract_debit_account_balance_table(book, assetCategory,
						rest, accountNameMutilItemsMap, accountNameList,
						amount_split_table, AccountToAccountMappingTableClone);
				
				amount_split_table = extract_trigger_credit_balance_table(book, assetCategory,
						debit_credit_trigger_table, accountNameList,
						amount_split_table, AccountToAccountMappingTableClone);
				
				forwardResultLedgerList = carry_over_by_account_balance_table(book, assetCategory, party,
						journalVoucherUUid, businessVoucherUUid,
						sourceDocumentUUid, forwardResultLedgerList,
						accountNameMutilItemsMap, amount_split_table,
						AccountToAccountMappingTableClone);
				
				if(bank_saving_split_mode==false)
				{
					forwardResultLedgerList=pack_bank_saving_ledgers(totalBankSavingAmount,forwardResultLedgerList);
				}
				return forwardResultLedgerList;
			}

	private List<LedgerItem> carry_over_by_account_balance_table(LedgerBook book,
			AssetCategory assetCategory, LedgerTradeParty party,
			String journalVoucherUUid, String businessVoucherUUid,
			String sourceDocumentUUid,
			List<LedgerItem> forwardResultLedgerList,
			Map<String, List<LedgerItem>> accountNameMutilItemsMap,
			HashMap<String, BigDecimal> amount_split_table,
			HashMap<String, String> AccountToAccountMappingTableClone)
			throws InvalidCarryOverException, InsufficientBalanceException,
			InvalidLedgerException {
		for(String fromAccount :amount_split_table.keySet())
		{
			BigDecimal balance=amount_split_table.get(fromAccount);
			List<LedgerItem> fromLedgerList=accountNameMutilItemsMap.get(fromAccount);
			if(CollectionUtils.isEmpty(fromLedgerList)){
				continue;
			}
			String forwardAccount=AccountToAccountMappingTableClone.get(fromAccount);
			if(StringUtils.isEmpty(forwardAccount)) throw new InvalidCarryOverException();
			List<LedgerItem>  forwardList=this.partial_carry_over_One_To_One(fromLedgerList, book, assetCategory, party, forwardAccount, journalVoucherUUid, businessVoucherUUid, sourceDocumentUUid, balance);
			forwardResultLedgerList.addAll(forwardList);
		}
		return forwardResultLedgerList;
	}

	private HashMap<String, BigDecimal> extract_trigger_credit_balance_table(
			LedgerBook book,
			AssetCategory assetCategory,
			final HashMap<String, Map.Entry<String, String>> debit_credit_trigger_table,
			List<String> accountNameList,
			HashMap<String, BigDecimal> amount_split_table,
			HashMap<String, String> AccountToAccountMappingTableClone) {
		for(String fromAccount :accountNameList)
		{
			Map.Entry<String,String> triggered_pair=debit_credit_trigger_table.get(fromAccount);
			if(triggered_pair==null) continue;
			BigDecimal triggered_balance=amount_split_table.get(fromAccount);
			if(triggered_balance==null) continue;
			BigDecimal balance = get_absolute_alance_of_account(triggered_pair.getKey(), book, assetCategory);
			amount_split_table.put(triggered_pair.getKey(), triggered_balance.compareTo(balance)<0?triggered_balance:balance);
			AccountToAccountMappingTableClone.put(triggered_pair.getKey(), triggered_pair.getValue());
		}
		return amount_split_table;
	}

	private HashMap<String, BigDecimal> extract_debit_account_balance_table(LedgerBook book,
			AssetCategory assetCategory, BigDecimal rest,
			Map<String, List<LedgerItem>> accountNameMutilItemsMap,
			List<String> accountNameList,
			HashMap<String, BigDecimal> amount_split_table,
			HashMap<String, String> AccountToAccountMappingTableClone)
			throws InvalidCarryOverException {
		for(String fromAccount :accountNameList)
		{
			if(StringUtils.isEmpty(fromAccount)) throw new InvalidCarryOverException();
			List<LedgerItem> fromLedgerList=accountNameMutilItemsMap.get(fromAccount);
			BigDecimal balance=get_absolute_balance_of_items(fromLedgerList);
			if(BigDecimal.ZERO.compareTo(balance)==0){
				continue;
			}
			String forward_account_from_debit_account=AccountToAccountMappingTableClone.get(fromAccount);
			TAccountInfo account=ChartOfAccount.EntryBook().get(forward_account_from_debit_account);
			if(account==null){
				continue;
			}
			BigDecimal newRest=rest;
			if(account.getFirstLevelAccount().getAccountCode().equals(ChartOfAccount.FST_BANK_SAVING_CODE)
					|| account.getFirstLevelAccount().getAccountCode().equals(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS_CODE))
			{
				if(newRest.compareTo(BigDecimal.ZERO)<=0) continue;
				newRest=rest.subtract(balance);
				if(newRest.compareTo(BigDecimal.ZERO)<0&&rest.compareTo(BigDecimal.ZERO)>0)
					balance=rest;
				rest=newRest;
			}
			amount_split_table.put(fromAccount, balance);
		}
		return amount_split_table;
	}


	private List<LedgerItem> pack_bank_saving_ledgers(BigDecimal totalBankSavingAmount,
			List<LedgerItem> ResultLedgerList) {
		HashMap<String,LedgerItem> uuid_item_mapping=(HashMap<String, LedgerItem>) 
				ResultLedgerList.stream().collect(Collectors.toMap(LedgerItem::getLedgerUuid, (item)->item));
		List<LedgerItem> bank_saving_items=new ArrayList<LedgerItem>();
		bank_saving_items.addAll(ResultLedgerList);
		List<LedgerItem> newResult=new ArrayList<LedgerItem>();
		LedgerItem packed_bank_saving=null;
		String newBatchUuid=UUID.randomUUID().toString();
		for(LedgerItem item:bank_saving_items)
		{
			if(item.getFirstAccountName().equals(ChartOfAccount.FST_BANK_SAVING)
					||
				item.getFirstAccountName().equals(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS_CODE))
			{
				ResultLedgerList.remove(item);
				if(packed_bank_saving==null) { 
						packed_bank_saving=new LedgerItem();
						BeanUtils.copyProperties(item, packed_bank_saving);
						packed_bank_saving.setBatchSerialUuid(newBatchUuid);
						if(item.balanceSide().equals(AccountSide.DEBIT)) {
							packed_bank_saving.setDebitBalance(totalBankSavingAmount);
						  packed_bank_saving.setCreditBalance(BigDecimal.ZERO);
						}  else {
							packed_bank_saving.setCreditBalance(totalBankSavingAmount);
							packed_bank_saving.setDebitBalance(BigDecimal.ZERO);
						}
						packed_bank_saving.setBackwardLedgerUuid("");
						newResult.add(packed_bank_saving);
					}
				LedgerItem forwardItem=uuid_item_mapping.get(item.getBackwardLedgerUuid());
				forwardItem.setForwardLedgerUuid(packed_bank_saving.getLedgerUuid());
				forwardItem.setBatchSerialUuid(newBatchUuid);
				
			}
		}
		if(packed_bank_saving!=null)
			ResultLedgerList.add(packed_bank_saving);
		return ResultLedgerList;
	}
	
	
	
	//TODO partial:  M-partial -> one carried
	public List<LedgerItem> partial_carry_over_One_To_One(List<LedgerItem> fromLedgerList,LedgerBook book,AssetCategory forwardAsset,LedgerTradeParty forwardParty,String toAccountName,String journalVoucher,String businesVoucher,String sourceDocument,
			BigDecimal voucherBookAmount) throws InsufficientBalanceException, InvalidLedgerException {
		List<LedgerItem> all_ledgers = new ArrayList<LedgerItem>();
		
		//book_one_to_one
		AccountSide balanceSide=get_balanced_account_side(fromLedgerList);
		if(balanceSide==AccountSide.BOTH){
			return Collections.emptyList();
		}
		if(CollectionUtils.isEmpty(fromLedgerList)){
			throw new InvalidLedgerException();
		}
		LedgerItem partial_from_ledger = fromLedgerList.get(0).generateCounterLedgerWithSameAccount(balanceSide,voucherBookAmount,journalVoucher,businesVoucher,sourceDocument);		
		all_ledgers.add(partial_from_ledger);
		
		LedgerItem forwardLedger=new LedgerItem();
		
		partial_from_ledger.setForwardLedgerUuid(forwardLedger.getLedgerUuid());
		
		TAccountInfo account=ChartOfAccount.EntryBook().get(toAccountName);
		importer.copyTAccount(account,forwardLedger);
		importer.copyParty(forwardParty,forwardLedger);
		importer.copyAssetSet(forwardAsset, forwardLedger);
		importer.copyBookBreif(book, forwardLedger);
		importer.copyEmptyVoucher(forwardLedger);
		if(balanceSide==AccountSide.CREDIT) {
			forwardLedger.setCreditBalance(partial_from_ledger.getDebitBalance());
			forwardLedger.setDebitBalance(BigDecimal.ZERO);
		} else {
			forwardLedger.setDebitBalance(partial_from_ledger.getCreditBalance());
			forwardLedger.setCreditBalance(BigDecimal.ZERO);
		}
		forwardLedger.setBackwardLedgerUuid(partial_from_ledger.getLedgerUuid());
		forwardLedger.setBookInDate(new Date());
		forwardLedger.setSourceDocumentUuid(sourceDocument);
		forwardLedger.setBusinessVoucherUuid(businesVoucher);
		forwardLedger.setJournalVoucherUuid(journalVoucher);
		forwardLedger.setLifeCycle(LedgerLifeCycle.BOOKED);
		all_ledgers.add(forwardLedger);
		String batchUuid=UUID.randomUUID().toString();
		for(LedgerItem item:all_ledgers)
		{
			item.setBatchSerialUuid(batchUuid);
		}
		return all_ledgers;
		
	}
	
	

	@Override
	public List<LedgerItem> get_ledgers_and_forward_ledgers_by_jvs(String ledgerBookNo, List<String> journalVoucherUuidList) {
		String queryString = "from LedgerItem where ledgerBookNo=:ledgerBookNo AND journalVoucherUuid IN (:journalVoucherUuidList) ";
		
		HashMap<String,Object> params=new HashMap();
		params.put("journalVoucherUuidList", journalVoucherUuidList);
		params.put("ledgerBookNo", ledgerBookNo);
		List<LedgerItem> carry_overed_ledger_items = this.genericDaoSupport.searchForList(queryString, params);
		List<String> forwardLedgerUuids = carry_overed_ledger_items.stream().map(ledgerItem->ledgerItem.getForwardLedgerUuid()).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(forwardLedgerUuids)){
			return Collections.emptyList();
		}
		List<LedgerItem> forwardItems =  get_ledgers_by(forwardLedgerUuids);
		List<LedgerItem> allLedgers = new ArrayList<LedgerItem>();
		allLedgers.addAll(carry_overed_ledger_items);
		allLedgers.addAll(forwardItems);
		return allLedgers;
	}

	@Override
	public List<LedgerItem> get_ledgers_by(List<String> ledgerItems) {
		if(CollectionUtils.isEmpty(ledgerItems)){
			return Collections.emptyList();
		}
		String queryString = "from LedgerItem where ledgerUuid IN (:ledgerUuids) ";
		return this.genericDaoSupport.searchForList(queryString, "ledgerUuids",ledgerItems);
	}
	
	@Override
	public BigDecimal get_balance_of_account(List<String> accountNameList, LedgerBook ledgerBook, AssetCategory asset)
	{
		String queryString = "select sum(debitBalance-creditBalance) From LedgerItem where relatedLv1AssetUuid=:assetUuid AND ledgerBookNo=:ledgerBookNo AND lifeCycle!=:writeOff";
		List<TAccountInfo> accountInfoList = accountNameList.stream().map(accountName -> ChartOfAccount.EntryBook().get(accountName))
				.filter(tAccountInfo -> tAccountInfo!=null).collect(Collectors.toList());
		queryString += build_3lvl_filters(accountInfoList);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("assetUuid", asset.getFstLvLAssetUuid());
		params.put("ledgerBookNo", ledgerBook.getLedgerBookNo());
		params.put("writeOff", LedgerLifeCycle.WRITTEN_OFF);
		List<BigDecimal> amounts = genericDaoSupport.searchForList(queryString,params);
		if(CollectionUtils.isEmpty(amounts)){
			return BigDecimal.ZERO;
		}
		BigDecimal amount = amounts.get(0);
		// if no ledger?
		if(amount==null){
			return BigDecimal.ZERO;
		}
		return amount;
	}
	
	@Override
	public BigDecimal getBalancedAmount(String ledgerBookNo, String accountName,String firstPartyId, String sndPartyId, String relatedfstAssetUuid, String realtedContractUuid){
		if(StringUtils.isEmpty(firstPartyId) && StringUtils.isEmpty(sndPartyId)  && StringUtils.isEmpty(relatedfstAssetUuid)  && StringUtils.isEmpty(realtedContractUuid) ){
			return BigDecimal.ZERO;
		}
		StringBuffer queryString = new StringBuffer("select sum(debitBalance-creditBalance) from LedgerItem where ledgerBookNo=:ledgerBookNo AND lifeCycle!=:writeOff ");
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(firstPartyId)){
			queryString.append(" AND firstPartyId=:firstPartyId ");
			params.put("firstPartyId", firstPartyId);
		}
		if(!StringUtils.isEmpty(sndPartyId)){
			queryString.append(" AND secondPartyId=:secondPartyId ");
			params.put("secondPartyId", sndPartyId);
		}
		if(!StringUtils.isEmpty(relatedfstAssetUuid)){
			queryString.append(" AND relatedLv1AssetUuid=:relatedLv1AssetUuid ");
			params.put("relatedLv1AssetUuid", relatedfstAssetUuid);
		}
		if(!StringUtils.isEmpty(realtedContractUuid)){
			queryString.append(" AND contractUuid=:contractUuid ");
			params.put("contractUuid", realtedContractUuid);
		}
		TAccountInfo account =ChartOfAccount.EntryBook().get(accountName);
		queryString.append(build_3lvl_filters(Arrays.asList(account)));
		params.put("ledgerBookNo", ledgerBookNo);
		params.put("writeOff", LedgerLifeCycle.WRITTEN_OFF);
		List<BigDecimal> amounts = genericDaoSupport.searchForList(queryString.toString(), params);
		if(CollectionUtils.isEmpty(amounts)){
			return BigDecimal.ZERO;
		}
		BigDecimal amount = amounts.get(0);
		if(amount==null){
			return BigDecimal.ZERO;
		}
		return amount;
	}
	
	@Override
	public LedgerTradeParty getAssetsImportLedgerTradeParty(FinancialContract financialContract, Customer customer){
		return new LedgerTradeParty(financialContract.getCompany().getUuid()+"",customer.getCustomerUuid());
	}
	
	@Override
	public LedgerTradeParty getOnlineDeductBanksavingLedgerTradeParty(AssetSet assetSet){
		FinancialContract financialContract = financialContractService.getFinancialContractBy(assetSet.getContract().getFinancialContractUuid());
		return new LedgerTradeParty(financialContract.getCompany().getUuid()+"","");
	}
	
	@Override
	public LedgerTradeParty getGuranteLedgerTradeParty(Order order){
		FinancialContract financialContract = order.getFinancialContract();
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(financialContract.getCompany().getUuid()+"",companyCustomer==null?"":companyCustomer.getCustomerUuid());
	}
	@Override
	public LedgerTradeParty getPayableLedgerTradeParty(Order order){
		FinancialContract financialContract = order.getFinancialContract();
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(companyCustomer==null?"":companyCustomer.getCustomerUuid(),financialContract.getCompany().getUuid()+"");
	}
	@Override
	public LedgerTradeParty getGuranteLedgerTradeParty(AssetSet assetSet){
		FinancialContract financialContract = financialContractService.getFinancialContractBy(assetSet.getContract().getFinancialContractUuid());
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(financialContract.getCompany().getUuid()+"",companyCustomer==null?"":companyCustomer.getCustomerUuid());
	}
	@Override
	public LedgerTradeParty getPayableLedgerTradeParty(AssetSet assetSet){
		FinancialContract financialContract = financialContractService.getFinancialContractBy(assetSet.getContract().getFinancialContractUuid());
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(companyCustomer==null?"":companyCustomer.getCustomerUuid(),financialContract.getCompany().getUuid()+"");
	}
	
	@Override
	public LedgerTradeParty getCustomerPayBankSavingLedgerTradeParty(Order order){
		Customer customer = order.getCustomer();
		return new LedgerTradeParty(customer.getCustomerUuid(),"");
	}
	@Override
	public LedgerTradeParty getAppPayBankSavingLedgerTradeParty(Order order){
		FinancialContract financialContract = order.getFinancialContract();
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(companyCustomer.getCustomerUuid(),"");
	}
	@Override
	public LedgerTradeParty getCustomerPayBankSavingLedgerTradeParty(AssetSet assetSet){
		Contract contract = assetSet.getContract();
		return new LedgerTradeParty(contract.getCustomer().getCustomerUuid(),"");
	}
	@Override
	public LedgerTradeParty getAppPayBankSavingLedgerTradeParty(AssetSet assetSet){
		Contract contract = assetSet.getContract();
		Customer companyCustomer = customerService.getCustomer(contract.getApp(), CustomerType.COMPANY);
		return new LedgerTradeParty(companyCustomer.getCustomerUuid(),"");
	}
	
	@Override
	public AssetCategory getDepositAssetCategoryByCustomer(Customer customer){
		AssetCategory emptyAssetCategory = AssetConvertor.convertEmptyAssetCategory();
		if(customer==null){
			return emptyAssetCategory;
		}
		if(customer.getCustomerType()==CustomerType.COMPANY){
			return AssetConvertor.convertAppDepositAssetCategory();
		}else if(customer.getCustomerType()==CustomerType.PERSON){
			Contract contract= contractService.getContractByCustomer(customer);
			if(contract==null){
				return emptyAssetCategory;
			}
			return AssetConvertor.convertDepositAssetCategory(contract);
		}
		return emptyAssetCategory;
	}
	
	
	@Override
	public boolean is_zero_balanced_account(String accountName, LedgerBook ledgerBookNo, AssetCategory asset) {
		
		return BigDecimal.ZERO.compareTo(get_balance_of_account(Arrays.asList(accountName),ledgerBookNo,asset))==0;
	}
	
	@Override
	public boolean is_total_balance_zero_accounts(List<String> accountName, LedgerBook ledgerBookNo, AssetCategory asset){
		return BigDecimal.ZERO.compareTo(get_balance_of_account(accountName,ledgerBookNo,asset))==0;
	}
	
	public boolean is_zero_balanced_overdue_asset(LedgerBook book, AssetCategory assetCategory){
		return is_total_balance_zero_accounts(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,
				ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE), book, assetCategory);
	}
	
	 
	
	private List<LedgerItem> get_ledgers_by_voucher(String ledgerBookNo,String JounarVoucher,String BusinessVoucher,String SourceDocument)
	{
		String queryString = "from LedgerItem where ledgerBookNo=:ledgerBookNo AND lifeCycle=:lifeCycle AND (journalVoucherUuid=:journalVoucher or businessVoucherUuid=:businessVoucher or sourceDocumentUuid=:sourceDocument)";
		
		HashMap<String,Object> params=new HashMap();
		params.put("journalVoucher", JounarVoucher);
		params.put("ledgerBookNo", ledgerBookNo);
		params.put("businessVoucher", BusinessVoucher);
		params.put("sourceDocument", SourceDocument);
		params.put("lifeCycle", LedgerLifeCycle.BOOKED);
		List<LedgerItem> carry_overed_ledger_items = this.genericDaoSupport.searchForList(queryString, params);
		List<String> forwardLedgerUuids = carry_overed_ledger_items.stream().map(ledgerItem->ledgerItem.getForwardLedgerUuid()).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(forwardLedgerUuids)){
			return Collections.emptyList();
		}
		List<LedgerItem> forwardItems =  get_ledgers_by(forwardLedgerUuids);
		List<LedgerItem> allLedgers = new ArrayList<LedgerItem>();
		allLedgers.addAll(carry_overed_ledger_items);
		allLedgers.addAll(forwardItems);
		return allLedgers;
		
	}
	
	public List<LedgerItem> get_all_ledgers_of_asset(LedgerBook book,AssetCategory asset)
	{

			String queryString = "from LedgerItem where ledger_book_no='"+book.getLedgerBookNo()+"' and related_lv_1_asset_uuid='"+asset.getFstLvLAssetUuid()+"' and life_cycle="+LedgerLifeCycle.BOOKED.ordinal();
			List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, null);
			return result;
	}
	@Override
	public void write_off_asset_in_book(LedgerBook book,AssetCategory asset,String JounarVoucher,String BusinessVoucher,String SourceDocument) throws InvalidWriteOffException
	{
		if(!this.is_zero_balanced_account(ChartOfAccount.FST_BANK_SAVING, book, asset))
			throw new InvalidWriteOffException() ;
		
		List<LedgerItem> allledgers=get_all_ledgers_of_asset(book,asset);
		write_off_none_zero_ledgers(book, asset, allledgers);
	}
	
	@Override
	public void write_off_ledgers_accounts(LedgerBook book,AssetCategory assetCategory, String accountName, String countAccountName) throws InvalidWriteOffException{
		BigDecimal accountBalance = get_balance_of_account(Arrays.asList(accountName), book, assetCategory);
		BigDecimal counterAccountBalance = get_balance_of_account(Arrays.asList(countAccountName), book, assetCategory);
		if(BigDecimal.ZERO.compareTo(accountBalance)==0 &&
				BigDecimal.ZERO.compareTo(counterAccountBalance)==0)
			return;
		if(accountBalance.abs().compareTo(counterAccountBalance.abs())!=0){
			throw new InvalidWriteOffException() ;
		}
		List<LedgerItem> allLedgers = new ArrayList<LedgerItem>();
		List<LedgerItem> ledgerItems = get_booked_ledgers_of_asset_in_taccount(accountName, book, assetCategory.getFstLvLAssetUuid());
		List<LedgerItem> counterLedgerItems = get_booked_ledgers_of_asset_in_taccount(countAccountName, book, assetCategory.getFstLvLAssetUuid());
		allLedgers.addAll(ledgerItems);
		allLedgers.addAll(counterLedgerItems);
		write_off_none_zero_ledgers(book, assetCategory, allLedgers);
	}
	

	private void write_off_none_zero_ledgers(LedgerBook book, AssetCategory asset,
			List<LedgerItem> allledgers) {
		HashMap<String,List<LedgerItem>> last_lvl_account_group=(HashMap<String, List<LedgerItem>>) 
				allledgers.stream().collect(Collectors.groupingBy(LedgerItem::lastAccountName));
		Set<String> last_lvl_accounts=last_lvl_account_group.keySet();
		AccountDispersor dispersor=new AccountDispersor();
		for(String last_account:last_lvl_accounts)
		{
			BigDecimal balance=this.get_balance_of_account(Arrays.asList(last_account), book, asset);
			
			AccountSide counterside;
			if(BigDecimal.ZERO.compareTo(balance)==0) continue;
			else if(BigDecimal.ZERO.compareTo(balance)>0) counterside=AccountSide.DEBIT;
			else counterside=AccountSide.CREDIT;
			
			balance=balance.abs();
			List<LedgerItem> items=last_lvl_account_group.get(last_account);
			LedgerTradeParty party=new LedgerTradeParty();
			importer.extractParty(party, items.get(0));
			dispersor.dispers(last_account, balance,counterside,party);
			
		}
		
		Map<String,LedgerItem> writeOffLegers=book_M_Debit_M_Credit_ledgers_V2(book,asset,dispersor);
		for(LedgerItem item:writeOffLegers.values())
		{
		
			this.save(item);
		}
	}
	
	@Override
	public void lapse_guarantee_of_asset(LedgerBook book, String assetSetUuid, AssetCategory assetCategory){
		List<LedgerItem> guaranteeLedgers=this.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book, assetSetUuid);
		List<LedgerItem> repurchaseLedgers=this.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE, book, assetSetUuid);
		List<LedgerItem> allLedgers = new ArrayList<LedgerItem>();
		allLedgers.addAll(guaranteeLedgers);
		allLedgers.addAll(repurchaseLedgers);
		
		roll_back_ledgers(allLedgers, book, assetCategory, "", "", "");
	}
	
	private void roll_back_ledgers(List<LedgerItem> allLedgers,LedgerBook book,AssetCategory asset,String JounarVoucher,String BusinessVoucher,String SourceDocument){
		HashMap<String,List<LedgerItem>> ledger_pairs=(HashMap<String, List<LedgerItem>>) allLedgers.stream().collect(
				Collectors.groupingBy(LedgerItem::getBatchSerialUuid));
		AccountDispersor dispersor=new AccountDispersor();
		
		for(String batch:ledger_pairs.keySet())
		{
			List<LedgerItem> batch_ledgers=ledger_pairs.get(batch);
			for(LedgerItem item:batch_ledgers)
			{
				AccountSide side=item.balanceSide();
				if(side.equals(AccountSide.BOTH)) continue;
				BigDecimal absAmount=item.getAbsoluteBalance();
				LedgerTradeParty party=new LedgerTradeParty();
				importer.extractParty(party, item);
				dispersor.dispers(item.lastAccountName(), absAmount,side.counterSide(),party,JounarVoucher,BusinessVoucher,SourceDocument);
			}
		}
		Map<String,LedgerItem> writeOffLegers=book_M_Debit_M_Credit_ledgers_V2(book,asset,dispersor);
		for(LedgerItem item:writeOffLegers.values()){
			this.save(item);
		}
	}
	
	@Override
	public void roll_back_ledgers_by_voucher(LedgerBook book,AssetCategory asset,String JounarVoucher,String BusinessVoucher,String SourceDocument)
	{
		List<LedgerItem> allLedgers=this.get_ledgers_by_voucher(book.getLedgerBookNo(),JounarVoucher, BusinessVoucher, SourceDocument);
		
		roll_back_ledgers(allLedgers, book, asset, JounarVoucher, BusinessVoucher, SourceDocument);
		
	}
	
	public boolean exsitsIndependentsRemittanceBy(String ledgerBookNo, String sdUuid, BigDecimal amount){
		TAccountInfo account=ChartOfAccount.EntryBook().get(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_INTER_ACCOUNT_REMITTANCE);
		if(account==null ) return false;
		String queryString = "from LedgerItem where ledgerBookNo=:ledgerBookNo AND lifeCycle!=:writeOff "
				+ " AND debitBalance=:debitBalance AND sourceDocumentUuid=:sourceDocumentUuid ";
		queryString+=build_3lvl_filters(Arrays.asList(account));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ledgerBookNo", ledgerBookNo);
		params.put("writeOff", LedgerLifeCycle.WRITTEN_OFF);
		params.put("sourceDocumentUuid", sdUuid);
		params.put("debitBalance", amount);
		List<LedgerItem> result = this.genericDaoSupport.searchForList(queryString, params);
		if(CollectionUtils.isEmpty(result)){
			return false;
		}
		return true;
	}
	
}
