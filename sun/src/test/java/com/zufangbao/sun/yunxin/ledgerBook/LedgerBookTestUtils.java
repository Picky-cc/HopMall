package com.zufangbao.sun.yunxin.ledgerBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBookHandlerImpl;
import com.zufangbao.sun.ledgerbook.LedgerItem;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerLifeCycle;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;

@Component
public class LedgerBookTestUtils {

	@Autowired
	private LedgerItemService ledgerItemService;
	
	public static void assertParty(LedgerItem asset,LedgerTradeParty parties )
	{
		assertEquals(parties.getFstParty(),asset.getFirstPartyId());
		assertEquals(parties.getSndParty(),asset.getSecondPartyId());
		
	}

	public static void buildMapping(HashMap<String,BigDecimal> mappingTable,String key,BigDecimal balance )
	{
		mappingTable.put(key+balance.setScale(2,   BigDecimal.ROUND_HALF_UP), balance);
	}

	public static void assetBookedMultiAssetAndLibility2(AssetSet relatedAsset,Date getAssetRecycleDate,
				HashMap<String,LedgerItem> LedgerRecords,HashMap<String,BigDecimal> expectedCreditAccounts,HashMap<String,BigDecimal> expectedDebitAccounts,HashMap<String,String> carryOverTable)
		{
		
			assertEquals(expectedCreditAccounts.size()+expectedDebitAccounts.size(),LedgerRecords.size());
			
			HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
			
			BigDecimal debitallAmount=BigDecimal.ZERO;
			LedgerItem debitRecord=null;
			for(String name:expectedDebitAccounts.keySet())
			{
				debitRecord=LedgerRecords.get(name);
				if(debitRecord==null)
					debitRecord=null;
				debitallAmount=debitallAmount.add(debitRecord.getDebitBalance());
				BigDecimal expectedAmount=expectedDebitAccounts.get(name);
				if(expectedAmount==null)
					expectedAmount=null;
				assertEquals(true,debitRecord.InTAccount(entryBook.get(name.replace(AccountSide.DEBIT.getAlias()+debitRecord.getDebitBalance().setScale(2,   BigDecimal.ROUND_HALF_UP).toString(),""))));
				assertEquals(0,BigDecimal.ZERO.compareTo(debitRecord.getCreditBalance()));
				assertEquals(0,debitRecord.getDebitBalance().compareTo(expectedAmount));
				assertEquals(relatedAsset.getContract().getId(),debitRecord.getContractId());
				if(debitRecord.getAmortizedDate()!=null)
					assertEquals(getAssetRecycleDate,debitRecord.getAmortizedDate());
				assertEquals(relatedAsset.getAssetUuid(),debitRecord.getRelatedLv1AssetUuid());
				assertEquals(LedgerLifeCycle.BOOKED,debitRecord.getLifeCycle());
				if(carryOverTable.values().contains(debitRecord.lastAccountName())){
					assertEquals(DateUtils.isSameDay(new Date(), debitRecord.getBookInDate()),true);
				}
				assertEquals(null, debitRecord.getCarriedOverDate());
			}
			
			BigDecimal creditallAmount=BigDecimal.ZERO;
			for(String name:expectedCreditAccounts.keySet())
			{
				LedgerItem creditRecord=LedgerRecords.get(name);
				if(creditRecord==null){
					//for debug
					creditRecord=null;
				}
				assertNotNull(creditRecord);
				BigDecimal expectedAmount=expectedCreditAccounts.get(name);
				
				boolean accountEquals=creditRecord.InTAccount(entryBook.get(name.replace(AccountSide.CREDIT.getAlias()+creditRecord.getCreditBalance().setScale(2,   BigDecimal.ROUND_HALF_UP).toString(),"")));
				assertEquals(true,accountEquals);
				assertEquals(0,BigDecimal.ZERO.compareTo(creditRecord.getDebitBalance()));
				creditallAmount=creditallAmount.add(creditRecord.getCreditBalance());
				assertEquals(0,creditRecord.getCreditBalance().compareTo(expectedAmount));
				
				assertEquals(relatedAsset.getContract().getId(),creditRecord.getContractId());
				if(creditRecord.getAmortizedDate()!=null)
					assertEquals(getAssetRecycleDate,creditRecord.getAmortizedDate());
				
				assertEquals(relatedAsset.getAssetUuid(),creditRecord.getRelatedLv1AssetUuid());
				assertEquals(LedgerLifeCycle.BOOKED,creditRecord.getLifeCycle());
				if(carryOverTable.values().contains(debitRecord.lastAccountName())){
					assertEquals(DateUtils.isSameDay(new Date(), debitRecord.getBookInDate()),true);
				}
				assertEquals(null, debitRecord.getCarriedOverDate());
			}
			
			HashMap<String, List<LedgerItem> > sameBatchLedgers=new HashMap();
			
			HashMap<String,LedgerItem> uuid_item_mapping=(HashMap<String, LedgerItem>) 
					LedgerRecords.values().stream().collect(Collectors.toMap(LedgerItem::getLedgerUuid, (item)->item));
			
			
			for(LedgerItem item: LedgerRecords.values())
			{
				if(StringUtils.isEmpty(item.getForwardLedgerUuid())&&StringUtils.isEmpty(item.getBackwardLedgerUuid()))
					continue;
				
				if(StringUtils.isEmpty(item.getForwardLedgerUuid())==false )
				{
					LedgerItem otherSide=uuid_item_mapping.get(item.getForwardLedgerUuid());
					assertNotNull(otherSide);
					String otherSideAccountName=carryOverTable.get(item.lastAccountName());
					if(otherSideAccountName==null)
						otherSideAccountName=null;
					assertNotNull(otherSideAccountName);
					boolean account_equals=otherSideAccountName.equals(otherSide.lastAccountName());
					if(account_equals==false) 
						account_equals=false;
					assertEquals(true,account_equals);
					if(StringUtils.isEmpty(otherSide.getBackwardLedgerUuid())==false)
						assertEquals(true,item.getLedgerUuid().equals(otherSide.getBackwardLedgerUuid()));
				 }
			}
			
	}

	public static void assetBookedMultiAssetAndLibility(AssetSet relatedAsset,
	HashMap<String,LedgerItem> LedgerRecords,HashMap<String,BigDecimal> expectedCreditAccounts,HashMap<String,BigDecimal> expectedDebitAccounts,HashMap<String,String> carryOverTable)
	{
	  assetBookedMultiAssetAndLibility2(relatedAsset, relatedAsset.getAssetRecycleDate(),LedgerRecords, expectedCreditAccounts, expectedDebitAccounts, carryOverTable);
	
	}

	public HashMap<String,LedgerItem> accountNameWithLedgerItem( List<LedgerItem> ledgerItems)
	{
		HashMap<String,LedgerItem> map=new HashMap<String,LedgerItem>();
		
		for(LedgerItem item:ledgerItems)
		{
			BigDecimal balance=item.getCreditBalance().compareTo(BigDecimal.ZERO)>0?item.getCreditBalance():item.getDebitBalance();
			map.put(item.lastAccountName()+ledgerItemService.get_balanced_account_side(item).getAlias()+balance.setScale(2,   BigDecimal.ROUND_HALF_UP),item);
		}
		return map;
		
	}
	
	public static HashMap<String,String> create_recievable_loan_to_bank_saving_table(String banksavingAccount){
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank=new HashMap<String,String>();
		recievable_to_bank_saving_carry_over_table_with_bank.putAll(LedgerBookHandlerImpl.recievable_loan_to_bank_saving_table);
		for(Map.Entry<String, String>  entry:LedgerBookHandlerImpl.credit_debit_trigger_table.values()){
			recievable_to_bank_saving_carry_over_table_with_bank.put(entry.getKey(), entry.getValue());
		}
		for(String key:recievable_to_bank_saving_carry_over_table_with_bank.keySet())
		{
			String dest=recievable_to_bank_saving_carry_over_table_with_bank.get(key);
			if(dest.equals(ChartOfAccount.FST_BANK_SAVING))
				recievable_to_bank_saving_carry_over_table_with_bank.put(key, banksavingAccount);
		}
		return recievable_to_bank_saving_carry_over_table_with_bank;
	}

}
