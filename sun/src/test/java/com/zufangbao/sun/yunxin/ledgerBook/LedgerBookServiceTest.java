package com.zufangbao.sun.yunxin.ledgerBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.DuplicateAssetsException;
import com.zufangbao.sun.ledgerbook.InsufficientBalanceException;
import com.zufangbao.sun.ledgerbook.InsufficientPenaltyException;
import com.zufangbao.sun.ledgerbook.InvalidCarryOverException;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.ledgerbook.InvalidWriteOffException;
import com.zufangbao.sun.ledgerbook.LackBusinessVoucherException;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerBookHandlerImpl;
import com.zufangbao.sun.ledgerbook.LedgerItem;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerLifeCycle;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetExtraCharge;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LedgerBookServiceTest {
	@Autowired LedgerItemService ledgerItemService;
	@Autowired
	private LedgerBookHandler anMeiTuLedgerBookHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	private HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
	
	@Autowired
	public LedgerBookTestUtils ledgerBookTestUtils;
	
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;

	private String LedgerBookNo="YUNXIN_AMEI_ledger_book";
	private String ledgerBookOrgnizationId="14";
	private String guranteepCompanyId="16";
	private String ledgerBookNo_test = "yunxin_ledger_book";
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_book_loan_asset() throws DuplicateAssetsException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 
		 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
			
		List<LedgerItem> debitRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, book, loan_asset.getAssetUuid());
		List<LedgerItem> absorbsvingRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING, book, loan_asset.getAssetUuid());
		List<LedgerItem> revenueRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE, book, loan_asset.getAssetUuid());
				
		List<LedgerItem> allRecords=new ArrayList();
		allRecords.addAll(absorbsvingRecords);
		allRecords.addAll(revenueRecords);
		allRecords.addAll(debitRecords);
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(allRecords);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.FST_UNEARNED_LOAN_ASSET+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,new HashMap());
		LedgerItem debitRecord=debitRecords.get(0);
		assertEquals(debitRecord.getDefaultDate(),loan_asset.getAssetRecycleDate());
		LedgerBookTestUtils.assertParty(debitRecord,party);
		LedgerBookTestUtils.assertParty(absorbsvingRecords.get(0),party);
		LedgerBookTestUtils.assertParty(revenueRecords.get(0),party);
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_book_loan_asset_v2() throws DuplicateAssetsException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getCustomerUuid()+"");
		 BigDecimal loanFee = new BigDecimal("10");
		 BigDecimal techFee = new BigDecimal("20");
		 BigDecimal otherFee = new BigDecimal("30");
		 AssetSetExtraCharge loanExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(),
				 loanFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE);
		 AssetSetExtraCharge techExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(), 
				 techFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE);
		 AssetSetExtraCharge otherExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(), 
				 otherFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
		 repaymentPlanExtraChargeService.save(loanExtraCharge);
		 repaymentPlanExtraChargeService.save(techExtraCharge);
		 repaymentPlanExtraChargeService.save(otherExtraCharge);
		 try {
			 anMeiTuLedgerBookHandler.book_loan_asset_V2(book,loan_asset,party);
		 } catch(Exception e){
			 fail();
		 }
			
		List<LedgerItem> debitRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, book, loan_asset.getAssetUuid());
		List<LedgerItem> absorbsvingRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING, book, loan_asset.getAssetUuid());
		List<LedgerItem> revenueRecords=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE, book, loan_asset.getAssetUuid());
				
		List<LedgerItem> allRecords=new ArrayList();
		allRecords.addAll(absorbsvingRecords);
		allRecords.addAll(revenueRecords);
		allRecords.addAll(debitRecords);
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(allRecords);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE+AccountSide.DEBIT.getAlias(),loanFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE+AccountSide.DEBIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE+AccountSide.DEBIT.getAlias(),otherFee);
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,new HashMap());
		LedgerItem debitRecord=debitRecords.get(0);
		assertEquals(debitRecord.getDefaultDate(),loan_asset.getAssetRecycleDate());
		LedgerBookTestUtils.assertParty(debitRecord,party);
		LedgerBookTestUtils.assertParty(absorbsvingRecords.get(0),party);
		LedgerBookTestUtils.assertParty(revenueRecords.get(0),party);
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_book_loan_asset_twice() throws DuplicateAssetsException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		 try
		 {
			 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		 }
		 catch(DuplicateAssetsException e)
		 {
			 
		 }
	}

	
	
	
	
	
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_amortize_loan_asset() throws AlreadayCarryOverException, DuplicateAssetsException, InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException  {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 
			AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
			
		 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		Date now=new Date();
		Date yesterday=DateUtils.addDays(now, -1);
		List<LedgerItem> qualifiedLedgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,yesterday);
		assertEquals(0,qualifiedLedgers.size());
		 qualifiedLedgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,now);
		assertEquals(1,qualifiedLedgers.size());
		
		LedgerItem qualifiedLedger=qualifiedLedgers.get(0);
		anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
		
		
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> unearned_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		
		List<LedgerItem> defferd_incom_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_DEFERRED_INCOME,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> revenue_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_REVENUE,book,
				loan_asset.getAssetUuid());
		
		LedgerItem receivableLoan=receivable_asset_ledgers.get(0);
		
		
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(unearned_asset_ledgers);
		ledgersForTest.addAll(defferd_incom_ledgers);
		ledgersForTest.addAll(revenue_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.FST_UNEARNED_LOAN_ASSET+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.FST_UNEARNED_LOAN_ASSET+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		assertEquals(true,ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,book ,assetCategory));
		assertEquals(true,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,book ,assetCategory));
		
		assertEquals(false,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST,book ,assetCategory));
		assertEquals(false,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book ,assetCategory));
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.unearned_loan_to_recievable_carry_over_table);
		
		LedgerBookTestUtils.assertParty(receivableLoan,party);

	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_amortize_loan_asset_v2() throws AlreadayCarryOverException, DuplicateAssetsException, InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException  {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		BigDecimal loanServiceFee = new BigDecimal("100.00");
		BigDecimal techFee = new BigDecimal("110.00");
		BigDecimal otherFee = new BigDecimal("120.00");
		AssetSetExtraCharge loanExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(),
				loanServiceFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE);
		 AssetSetExtraCharge techExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(), 
				 techFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE);
		 AssetSetExtraCharge otherExtraCharge = new AssetSetExtraCharge(loan_asset.getAssetUuid(), 
				 otherFee, ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
		 repaymentPlanExtraChargeService.save(loanExtraCharge);
		 repaymentPlanExtraChargeService.save(techExtraCharge);
		 repaymentPlanExtraChargeService.save(otherExtraCharge);
		 try {
		 anMeiTuLedgerBookHandler.book_loan_asset_V2(book,loan_asset,party);
		 } catch(Exception e){
			 e.printStackTrace();
			 fail();
		 }
		 
		BigDecimal totalFee =  loanServiceFee.add(techFee).add(otherFee);
		 
		Date now=new Date();
		Date yesterday=DateUtils.addDays(now, -1);
		List<LedgerItem> qualifiedLedgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,yesterday);
		assertEquals(0,qualifiedLedgers.size());
		 qualifiedLedgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,now);
		assertEquals(5,qualifiedLedgers.size());
		
		LedgerItem qualifiedLedger=qualifiedLedgers.get(0);
		anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
		
		
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> unearned_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		
		List<LedgerItem> defferd_incom_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_DEFERRED_INCOME,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> revenue_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_REVENUE,book,
				loan_asset.getAssetUuid());
		LedgerItem receivableLoan=receivable_asset_ledgers.get(0);
		
		
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(unearned_asset_ledgers);
		ledgersForTest.addAll(defferd_incom_ledgers);
		ledgersForTest.addAll(revenue_ledgers);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE+AccountSide.CREDIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE+AccountSide.CREDIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE+AccountSide.CREDIT.getAlias(),otherFee);
		
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_SERVICE_FEE+AccountSide.CREDIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_TECH_FEE+AccountSide.CREDIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_OTHER_FEE+AccountSide.CREDIT.getAlias(),otherFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_SERVICE_FEE+AccountSide.CREDIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_TECH_FEE+AccountSide.CREDIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_INCOME_LOAN_OTHER_FEE+AccountSide.CREDIT.getAlias(),otherFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE+AccountSide.DEBIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE+AccountSide.DEBIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE+AccountSide.DEBIT.getAlias(),otherFee);
		
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE+AccountSide.DEBIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE+AccountSide.DEBIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE+AccountSide.DEBIT.getAlias(),otherFee);
		
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_SERVICE_FEE+AccountSide.DEBIT.getAlias(),loanServiceFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_TECH_FEE+AccountSide.DEBIT.getAlias(),techFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_DEFERRED_INCOME_LOAN_OTHER_FEE+AccountSide.DEBIT.getAlias(),otherFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		assertEquals(true,ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,book ,assetCategory));
		assertEquals(true,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,book ,assetCategory));
		
		assertEquals(false,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST,book ,assetCategory));
		assertEquals(false,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book ,assetCategory));
		
		assertEquals(true,ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_DEFERRED_INCOME_FEE,book ,assetCategory));
		assertEquals(0,totalFee.negate().compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_REVENUE_INCOME_FEE),book ,assetCategory)));
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.unearned_loan_to_recievable_carry_over_table);
		
		LedgerBookTestUtils.assertParty(receivableLoan,party);

	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_amortize_loan_asset_twice() throws DuplicateAssetsException, InsufficientBalanceException, InvalidCarryOverException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		
		 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		Date now=new Date();
		List<LedgerItem> qualified_ledgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,now);
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		LedgerItem qualified_ledger=qualified_ledgers.get(0);
		try{
			anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
			
			anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
			fail();
		}
		catch(AlreadayCarryOverException e)
		{
			
		}
		catch(InsufficientBalanceException e)
		{
			
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_receivable_loan_asset_v2() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		 LedgerTradeParty party = recover_loan_helper_v2(loan_asset, book,loan_asset.getAssetInitialValue());
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> incomning_estimate_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_interest_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INTEREST,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(bank_saving_zsy_ledgers);
		ledgersForTest.addAll(incomning_estimate_ledgers);
		ledgersForTest.addAll(revenue_interest_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank = 
				LedgerBookTestUtils.create_recievable_loan_to_bank_saving_table(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST, book,assetCategory ));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE, book,assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
		
		
		LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
		LedgerItem incomning_ledger=revenue_interest_ledgers.get(0);
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty("");
		LedgerBookTestUtils.assertParty(bankSaving,party);
		LedgerBookTestUtils.assertParty(incomning_ledger,party);
	}
	

	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_roll_back_by_vouchers() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException, InvalidWriteOffException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		recover_loan_helper_v2(loan_asset, book,loan_asset.getAssetInitialValue().subtract(BigDecimal.valueOf(50)));
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		BigDecimal interest_before_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST), book, assetCategory);
		assertEquals(0,interest_before_rollback.compareTo(BigDecimal.valueOf(50)));
		BigDecimal principle_before_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE), book, assetCategory);
		assertEquals(0,principle_before_rollback.compareTo(BigDecimal.valueOf(0)));
		BigDecimal bank_saving_before_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory);
		assertEquals(0,bank_saving_before_rollback.compareTo(BigDecimal.valueOf(950)));
		
		
		ledgerItemService.roll_back_ledgers_by_voucher(book, assetCategory, JournalVoucherUUid, BusinessVoucherUUid, SourceDocumentUUid);
		BigDecimal interest_after_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST), book, assetCategory);
		assertEquals(0,interest_after_rollback.compareTo(BigDecimal.valueOf(100)));
		BigDecimal principl_after_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE), book, assetCategory);
		assertEquals(0,principl_after_rollback.compareTo(BigDecimal.valueOf(900)));
		BigDecimal bank_saving_after_rollback=ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory);
		assertEquals(0,bank_saving_after_rollback.compareTo(BigDecimal.valueOf(0)));
		

	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_write_off_asset() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException, InvalidWriteOffException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		recover_loan_helper_v2(loan_asset, book,loan_asset.getAssetInitialValue().subtract(BigDecimal.valueOf(50)));
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		try
		{
		ledgerItemService.write_off_asset_in_book(book, assetCategory, JournalVoucherUUid, BusinessVoucherUUid, SourceDocumentUUid);
		fail();
		}
		catch(InvalidWriteOffException ex)
		{
			assertEquals(true,true);
		}
		
		ledgerItemService.roll_back_ledgers_by_voucher(book, assetCategory, JournalVoucherUUid, BusinessVoucherUUid, SourceDocumentUUid);
		
		ledgerItemService.write_off_asset_in_book(book, assetCategory, JournalVoucherUUid, BusinessVoucherUUid, SourceDocumentUUid);
		
		List<LedgerItem> allledgers=ledgerItemService.get_all_ledgers_of_asset(book,assetCategory);
		
		HashMap<String,List<LedgerItem>> last_account_group=(HashMap<String, List<LedgerItem>>) 
				allledgers.stream().collect(Collectors.groupingBy(LedgerItem::lastAccountName));
		for(String last_account:last_account_group.keySet())
		{
			BigDecimal balance_of_accounmt=ledgerItemService.get_balance_of_account(Arrays.asList(last_account), book, assetCategory);
			assertEquals(0,balance_of_accounmt.compareTo(BigDecimal.valueOf(0)));
		}
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_partial_recover_receivable_loan_asset_v2() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		LedgerTradeParty party = recover_loan_helper_v2(loan_asset, book,loan_asset.getAssetInitialValue().subtract(BigDecimal.valueOf(50)));
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> incomning_estimate_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_interest_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INTEREST,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(bank_saving_zsy_ledgers);
		ledgersForTest.addAll(incomning_estimate_ledgers);
		ledgersForTest.addAll(revenue_interest_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue().subtract(BigDecimal.valueOf(50)));
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue().subtract(BigDecimal.valueOf(50)));
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank = LedgerBookTestUtils.create_recievable_loan_to_bank_saving_table(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
		
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST,book , assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
		
		
		LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
		LedgerItem incomning_ledger=revenue_interest_ledgers.get(0);
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty("");
		LedgerBookTestUtils.assertParty(bankSaving,party);
		LedgerBookTestUtils.assertParty(incomning_ledger,party);
	}

	private LedgerTradeParty recover_loan_helper_v2(AssetSet loan_asset,
			LedgerBook book,BigDecimal recoverAmount) throws DuplicateAssetsException,
			AlreadayCarryOverException, InsufficientBalanceException,
			InvalidCarryOverException, InvalidLedgerException,
			InsufficientPenaltyException, LackBusinessVoucherException {
		LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 
		 LedgerTradeParty payableparty=new LedgerTradeParty();
		 payableparty.setFstParty(loan_asset.getContract().getApp().getCompany().getId()+"");
		 payableparty.setSndParty(ledgerBookOrgnizationId);
	
		 anMeiTuLedgerBookHandler.book_loan_asset_V2(book,loan_asset,party);
		 anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
		
		String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
		
		LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
		expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
		expectedBankSavingParty.setSndParty("");
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,expectedBankSavingParty,DepositeAccountType.UINON_PAY);
		
		
		
		try
		{
		anMeiTuLedgerBookHandler.
		recover_receivable_loan_asset(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid, payableparty, recoverAmount, bankinfo, true);
		fail();
		}
		catch(LackBusinessVoucherException e)
		{
			
		}
		
		BusinessVoucherUUid="xxxx";
		JournalVoucherUUid="yyy";
		SourceDocumentUUid="zzz";
		anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid, null, recoverAmount, bankinfo, true);
		return party;
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_receivable_loan_asset() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		LedgerTradeParty expectedAssetParty=new LedgerTradeParty();
		expectedAssetParty.setFstParty(ledgerBookOrgnizationId);
		expectedAssetParty.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		
		LedgerTradeParty expectedPayableparty=new LedgerTradeParty();
		Company company=loan_asset.getContract().getApp().getCompany();
		expectedPayableparty.setFstParty(loan_asset.getContract().getApp().getCompany().getId()+"");
		expectedPayableparty.setSndParty(ledgerBookOrgnizationId);
	
		anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,expectedAssetParty);
		anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
		
		String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
		LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
		expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
		expectedBankSavingParty.setSndParty("");
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_YL,expectedBankSavingParty,DepositeAccountType.UINON_PAY);
		try
		{
			anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid, expectedPayableparty, loan_asset.getAssetInitialValue(), bankinfo, true);
		fail();
		}
		catch(LackBusinessVoucherException e)
		{
			
		}
		
		BusinessVoucherUUid="xxxx";
		JournalVoucherUUid="yyy";
		SourceDocumentUUid="zzz";
		anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid, null, loan_asset.getAssetInitialValue(), bankinfo, true);
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_YL,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> incomning_estimate_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_interest_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INTEREST,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(bank_saving_zsy_ledgers);
		ledgersForTest.addAll(incomning_estimate_ledgers);
		ledgersForTest.addAll(revenue_interest_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_YL+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank = 
				LedgerBookTestUtils.create_recievable_loan_to_bank_saving_table(ChartOfAccount.SND_BANK_SAVING_NFQ_YL);
		
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_YL, book,assetCategory));
		
		
		LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
		LedgerItem incomning_ledger=revenue_interest_ledgers.get(0);
		expectedAssetParty.setFstParty(ledgerBookOrgnizationId);
		expectedAssetParty.setSndParty("");
		LedgerBookTestUtils.assertParty(bankSaving,expectedAssetParty);
		LedgerBookTestUtils.assertParty(incomning_ledger,expectedAssetParty);
	}

	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_receivable_loan_asset_recover_twice() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		 LedgerTradeParty party=new LedgerTradeParty();
		 party.setFstParty(ledgerBookOrgnizationId);
		 party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
	
		 LedgerTradeParty payableparty=new LedgerTradeParty();
		 payableparty.setFstParty(loan_asset.getContract().getApp().getCompany().getId()+"");
		 payableparty.setSndParty(ledgerBookOrgnizationId);
		 
		 anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		Date now=new Date();
		List<LedgerItem> qualified_ledgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,now);
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		LedgerItem qualified_ledger=qualified_ledgers.get(0);
		anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
		
		List<LedgerItem> receivableAssets=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				loan_asset.getAssetUuid());
		LedgerItem receivableLoan=receivableAssets.get(0);
		String LoanAssetUUid=receivableLoan.getRelatedLv1AssetUuid();
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
		expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
		expectedBankSavingParty.setSndParty("");
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,expectedBankSavingParty,DepositeAccountType.BANK);
		
		anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid, payableparty, loan_asset.getAssetInitialValue(), bankinfo, true);
		try
		{
			anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid, null, loan_asset.getAssetInitialValue(), bankinfo, true);
		}
		catch(AlreadayCarryOverException e)
		{
			assertEquals(true,true);	

		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_get_ledger_in_every_lvl_account() {
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		//prepare data
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		
		TAccountInfo account=entryBook.get(ChartOfAccount.FST_RECIEVABLE_ASSET);
		List<LedgerItem> qualifiedAssets= ledgerItemService.get_all_ledgers_in_TAccount(book, account);
		assertEquals(3,qualifiedAssets.size());
		
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_classify_receivable_loan_asset_to_overdue_v2() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		Date now=new Date();
		now.setTime(System.currentTimeMillis()-1000*60);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		book_loan_helper_V2(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		//test get_overdue_receivable_loan_asset_at
		List<LedgerItem> overdue_loan_ledgers=anMeiTuLedgerBookHandler.get_overdue_receivable_loan_asset_at(book,now);
		assertEquals(0,overdue_loan_ledgers.size());
		Date nextday=DateUtils.addDays(now,1);
		overdue_loan_ledgers=anMeiTuLedgerBookHandler.get_overdue_receivable_loan_asset_at(book,nextday);
		assertEquals(2,overdue_loan_ledgers.size());
		//classify overdue
		LedgerTradeParty guranteeparty=this.classify_overdue_helper(loan_asset, book);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		
		List<LedgerItem> ledgersForTest=new ArrayList<LedgerItem>();
		
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> pay_able_liability=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_PAYABLE_LIABILITY,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> repurchases=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,book,
				loan_asset.getAssetUuid());
		
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(pay_able_liability);
		ledgersForTest.addAll(repurchases);
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset, allRecordMap, creditAccountNames, debitAccountNames,LedgerBookHandlerImpl.recievable_loan_to_overdue_carry_over_tabLe);
		
		for(LedgerItem item:pay_able_liability)
		{
			LedgerBookTestUtils.assertParty(item,guranteeparty);
		}
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_classify_receivable_loan_asset_to_overdue_twice() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		TAccountInfo overdueAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET);
		TAccountInfo guaranteeAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
		Date now=new Date();
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		
		amortize_loan_helper(loan_asset,book);
		//classify overdue
		LedgerTradeParty guranteeparty=this.classify_overdue_helper(loan_asset, book);
		try
		{
		this.classify_overdue_helper(loan_asset, book);
		fail();
		}
		catch(AlreadayCarryOverException e)
		{
			assertEquals(true,true);	
	
		}
		catch(InvalidLedgerException e)
		{
			assertEquals(true,true);	
	
		}
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_claim_penalty_when_overdue() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		BigDecimal penaltyAmount=assetValuationDetail.getAmount();
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		this.classify_overdue_helper(loan_asset, book);
		List<LedgerItem> overDueAssets=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, loan_asset.getAssetUuid());
		
		assertEquals(1,overDueAssets.size());
		anMeiTuLedgerBookHandler.claim_penalty_on_overdue_loan(book,loan_asset, assetValuationDetail);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount);
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount);
		
		List<LedgerItem> penalty_asset_ledgers=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, book, loan_asset.getAssetUuid());
		List<LedgerItem> investment_ledgers=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE, book, loan_asset.getAssetUuid());
		
		List<LedgerItem> all_ledgers=new ArrayList(){{
			addAll(penalty_asset_ledgers);
			addAll(investment_ledgers);
		}};
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(all_ledgers);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility2(loan_asset, assetValuationDetail.getAssetValueDate(),allRecordMap, creditAccountNames, debitAccountNames,new HashMap());
		LedgerBookTestUtils.assertParty(penalty_asset_ledgers.get(0),party);
		LedgerBookTestUtils.assertParty(investment_ledgers.get(0),party);
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_claim_penalty_when_overdue_v2() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		BigDecimal penaltyAmount=assetValuationDetail.getAmount();
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		
		LedgerTradeParty party=book_loan_helper_V2(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		this.classify_overdue_helper(loan_asset, book);
		List<LedgerItem> overDueAssets=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, loan_asset.getAssetUuid());
		
		assertEquals(2,overDueAssets.size());
		anMeiTuLedgerBookHandler.claim_penalty_on_overdue_loan(book,loan_asset, assetValuationDetail);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount);
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap<String, BigDecimal>();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount);
		
		List<LedgerItem> penalty_asset_ledgers=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, book, loan_asset.getAssetUuid());
		List<LedgerItem> investment_ledgers=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE, book, loan_asset.getAssetUuid());
		
		List<LedgerItem> all_ledgers=new ArrayList(){{
			addAll(penalty_asset_ledgers);
			addAll(investment_ledgers);
		}};
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(all_ledgers);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility2(loan_asset, assetValuationDetail.getAssetValueDate(),allRecordMap, creditAccountNames, debitAccountNames,new HashMap());
		LedgerBookTestUtils.assertParty(penalty_asset_ledgers.get(0),party);
		LedgerBookTestUtils.assertParty(investment_ledgers.get(0),party);
		
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_receivable_loan_guarrantee() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
			LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
			AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
			loan_asset.setAssetRecycleDate(new Date());
			repaymentPlanService.save(loan_asset);
			
			String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
			LedgerTradeParty party=book_loan_helper(loan_asset,book);
			amortize_loan_helper(loan_asset,book);
			LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
			
			AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
			
			claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
			
			LedgerTradeParty payableparty=new LedgerTradeParty();
			payableparty.setFstParty(guranteepCompanyId);
			payableparty.setSndParty(ledgerBookOrgnizationId);
			LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
			expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
			expectedBankSavingParty.setSndParty("");
			DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,expectedBankSavingParty,DepositeAccountType.UINON_PAY);
			
			
			try
			{
				anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty, bankinfo, loan_asset.getAssetInitialValue(), true);
			fail();
			}
			catch(LackBusinessVoucherException e)
			{
				
			}
			 BusinessVoucherUUid="xxxx";
			 JournalVoucherUUid="yyy";
			 SourceDocumentUUid="zzz";
			 
			 anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty, bankinfo, loan_asset.getAssetInitialValue(), true);
			
			List<LedgerItem> guranttee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE,book,
					loan_asset.getAssetUuid());
			List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
					loan_asset.getAssetUuid());
		
			List<LedgerItem> ledgersForTest=new ArrayList();
			ledgersForTest.addAll(guranttee_ledgers);
			ledgersForTest.addAll(bank_saving_zsy_ledgers);
			
			
			HashMap<String,BigDecimal> creditAccountNames=new HashMap();
			LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
			
			HashMap<String,BigDecimal> debitAccountNames=new HashMap();
			LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
			LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
			
			
			HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank=new HashMap<String,String>();
			recievable_to_bank_saving_carry_over_table_with_bank.putAll(LedgerBookHandlerImpl.gurrantee_to_bank_saving_table);
			for(String key:recievable_to_bank_saving_carry_over_table_with_bank.keySet())
			{
				String dest=recievable_to_bank_saving_carry_over_table_with_bank.get(key);
				if(dest.equals(ChartOfAccount.FST_BANK_SAVING))
					recievable_to_bank_saving_carry_over_table_with_bank.put(key, ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
			}
			HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
			AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
			
			
			LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
			assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book,assetCategory));
			assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
			
			
			LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
			LedgerItem gurantee_ledger=guranttee_ledgers.get(0);
			party.setFstParty(ledgerBookOrgnizationId);
			party.setSndParty("");
			LedgerBookTestUtils.assertParty(bankSaving,party);
			LedgerBookTestUtils.assertParty(gurantee_ledger,guranteeparty);
	
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_receivable_loan_guarrantee_twice() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		try{
			LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
			HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
			TAccountInfo overdueAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET);
			TAccountInfo guaranteeAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE);
			AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
			String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
			Date now=new Date();
			LedgerTradeParty party=book_loan_helper(loan_asset,book);
			amortize_loan_helper(loan_asset,book);
			LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
			BigDecimal penaltyAmount=BigDecimal.valueOf(5.0);
			
			AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
			
			claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
			List<LedgerItem> gurantee_ledgers=ledgerItemService.
					get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book, loan_asset.getAssetUuid());
			
			LedgerItem gurantee_ledger=gurantee_ledgers.get(0);
			LedgerTradeParty payableparty=new LedgerTradeParty();
			payableparty.setFstParty(guranteepCompanyId);
			payableparty.setSndParty(ledgerBookOrgnizationId);
			 BusinessVoucherUUid="xxxx";
			 JournalVoucherUUid="yyy";
			 SourceDocumentUUid="zzz";
			 anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty, null, loan_asset.getAssetInitialValue(), true);

			 try{
				 anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty, null, loan_asset.getAssetInitialValue(), true);
			fail();
			}
			catch(AlreadayCarryOverException e)
			{
					assertEquals(true,true);	
			}
		}catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_recover_overdue_loan_and_no_gurrantee_recover() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
	
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		
		AssetValuationDetail assetValuationDetail2 = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		AssetValuationDetail assetValuationDetail3 = assetValuationDetailService.load(AssetValuationDetail.class, 3L);
		BigDecimal penaltyAmount=assetValuationDetail2.getAmount();
		BigDecimal penaltyAmount2=assetValuationDetail3.getAmount();
		
		assetValuationDetail2.setAssetValueDate(loan_asset.getAssetRecycleDate());
		assetValuationDetail3.setAssetValueDate(loan_asset.getAssetRecycleDate());
		
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail2);
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail3);
		
		BigDecimal totalPenalty=BigDecimal.valueOf(20.0);
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,totalPenalty);
		
		
		List<LedgerItem> overdue_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> loan_penalty_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_investment_estimate_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_investment_incoming_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(overdue_asset_ledgers);
		ledgersForTest.addAll(bank_saving_zsy_ledgers);
		ledgersForTest.addAll(loan_penalty_ledgers);
		ledgersForTest.addAll(revenue_investment_estimate_ledgers);
		ledgersForTest.addAll(revenue_investment_incoming_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.CREDIT.getAlias(),totalPenalty);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount2);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.CREDIT.getAlias(),totalPenalty);

		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount2);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue().add(penaltyAmount).add(penaltyAmount2));
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.DEBIT.getAlias(),totalPenalty);
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank = 
				LedgerBookTestUtils.create_recievable_loan_to_bank_saving_table(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book,assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, book,assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
		
		
		LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
		LedgerItem incomning_ledger=revenue_investment_incoming_ledgers.get(0);
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty("");
		LedgerBookTestUtils.assertParty(bankSaving,party);
		LedgerBookTestUtils.assertParty(incomning_ledger,party);
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_recover_overdue_loan_and_no_gurrantee_recover_v2() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
	
		LedgerTradeParty party=book_loan_helper_V2(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		
		AssetValuationDetail assetValuationDetail2 = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		AssetValuationDetail assetValuationDetail3 = assetValuationDetailService.load(AssetValuationDetail.class, 3L);
		BigDecimal penaltyAmount=assetValuationDetail2.getAmount();
		BigDecimal penaltyAmount2=assetValuationDetail3.getAmount();
		
		assetValuationDetail2.setAssetValueDate(loan_asset.getAssetRecycleDate());
		assetValuationDetail3.setAssetValueDate(loan_asset.getAssetRecycleDate());
		
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail2);
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail3);
		
		BigDecimal totalPenalty=BigDecimal.valueOf(20.0);
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,totalPenalty);
		
		
		List<LedgerItem> overdue_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> loan_penalty_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_investment_estimate_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE,book, loan_asset.getAssetUuid());
		List<LedgerItem> revenue_investment_incoming_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(overdue_asset_ledgers);
		ledgersForTest.addAll(bank_saving_zsy_ledgers);
		ledgersForTest.addAll(loan_penalty_ledgers);
		ledgersForTest.addAll(revenue_investment_estimate_ledgers);
		ledgersForTest.addAll(revenue_investment_incoming_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetPrincipalValue());
		
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.CREDIT.getAlias(),totalPenalty);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penaltyAmount2);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.CREDIT.getAlias(),totalPenalty);

		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_INTEREST+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetPrincipalValue());
		
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount2);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),penaltyAmount.add(penaltyAmount2).add(loan_asset.getAssetInitialValue()));
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.DEBIT.getAlias(),totalPenalty);
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank = 
				LedgerBookTestUtils.create_recievable_loan_to_bank_saving_table(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, book,assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
		
		
		LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
		LedgerItem incomning_ledger=revenue_investment_incoming_ledgers.get(0);
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty("");
		LedgerBookTestUtils.assertParty(bankSaving,party);
		LedgerBookTestUtils.assertParty(incomning_ledger,party);
	}
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_overdue_loan_twice() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		TAccountInfo overdueAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET);
		TAccountInfo guaranteeAccount=entryBook.get( ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
		Date now=new Date();
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		BigDecimal penaltyAmount=BigDecimal.valueOf(5.0);
		
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
		BigDecimal penalty=BigDecimal.valueOf(5.0);
		
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,penalty);
		try{
			recover_receivable_overdue_and_penalty_helper(loan_asset,book,penalty);
			fail();
		}
		catch(AlreadayCarryOverException e)
		{
			assertEquals(true,true);	

		}
	}

	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	 public void test_recover_overdue_loan_after_recovered_gurrantee() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		BigDecimal penaltyAmount=assetValuationDetail.getAmount();
		assetValuationDetail.setAssetValueDate(loan_asset.getAssetRecycleDate());
		
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
		LedgerTradeParty payableparty=new LedgerTradeParty();
		payableparty.setFstParty(guranteepCompanyId);
		payableparty.setSndParty(ledgerBookOrgnizationId);
		BigDecimal penalty=BigDecimal.valueOf(5.0);
		this.recover_receivable_guranttee_helper(loan_asset, book,payableparty);
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,penalty);
	
		List<LedgerItem> asset_sold_for_repurchase_ledger=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> payableRepurchase=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_REPURCHASE,book,
				loan_asset.getAssetUuid()); 
		List<LedgerItem> investementIncomings=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> payablePenalty=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY,book,
				loan_asset.getAssetUuid());
				
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(asset_sold_for_repurchase_ledger);
		ledgersForTest.addAll(payableRepurchase);
		ledgersForTest.addAll(investementIncomings);
		ledgersForTest.addAll(payablePenalty);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.DEBIT.getAlias(),penaltyAmount);
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.repurchase_to_payable_table);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, assetCategory));
		
		
		
		LedgerBookTestUtils.assertParty(payableRepurchase.get(0),payableparty);
		LedgerBookTestUtils.assertParty(payablePenalty.get(0),payableparty);
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_recover_gurrantee_after_recovered_overdue_loan() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		
		
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		assetValuationDetail.setAssetValueDate(loan_asset.getAssetRecycleDate());
		
		BigDecimal penaltyAmount=assetValuationDetail.getAmount();
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
		LedgerTradeParty payableparty=new LedgerTradeParty();
		payableparty.setFstParty(guranteepCompanyId);
		payableparty.setSndParty(ledgerBookOrgnizationId);
		
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,penaltyAmount);
		this.recover_receivable_guranttee_helper(loan_asset, book,payableparty);
		List<LedgerItem> asset_sold_for_repurchase_ledger=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> payableRepurchase=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_REPURCHASE,book,
				loan_asset.getAssetUuid()); 
		List<LedgerItem> investementIncomings=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> payablePenalty=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY,book,
				loan_asset.getAssetUuid());
				
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(asset_sold_for_repurchase_ledger);
		ledgersForTest.addAll(payableRepurchase);
		ledgersForTest.addAll(investementIncomings);
		ledgersForTest.addAll(payablePenalty);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING+AccountSide.DEBIT.getAlias(),penaltyAmount);
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.repurchase_to_payable_table);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_FINANCIAL_ASSETS_SOLD_FOR_REPURCHASE, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INVESTMENT_INCOMING, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, assetCategory));
		
		
		
		LedgerBookTestUtils.assertParty(payableRepurchase.get(0),payableparty);
		LedgerBookTestUtils.assertParty(payablePenalty.get(0),payableparty);
		}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_payable_paid() throws AlreadayCarryOverException, LackBusinessVoucherException, DuplicateAssetsException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		LedgerTradeParty party=book_loan_helper(loan_asset,book);
		amortize_loan_helper(loan_asset,book);
		LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
		BigDecimal penaltyAmount=assetValuationDetail.getAmount();
		claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
		LedgerTradeParty payableparty=new LedgerTradeParty();
		payableparty.setFstParty(guranteepCompanyId);
		payableparty.setSndParty(ledgerBookOrgnizationId);
		
		this.recover_receivable_overdue_and_penalty_helper(loan_asset, book,penaltyAmount);
		this.recover_receivable_guranttee_helper(loan_asset, book,payableparty);
		
		this.paid_payable_loan_and_penalty_helper(loan_asset, book);
		
		
		
		List<LedgerItem> repurchase_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_REPURCHASE,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> custody_penalty_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_ledgers=ledgerItemService.
				 get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_YL,book, loan_asset.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(repurchase_ledgers);
		ledgersForTest.addAll(custody_penalty_ledgers);
		ledgersForTest.addAll(bank_saving_ledgers);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_REPURCHASE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY+AccountSide.CREDIT.getAlias(),penaltyAmount);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_YL+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue().add(penaltyAmount));
		
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_PAYABLE_REPURCHASE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penaltyAmount);
		
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		HashMap<String,String> payable_repurchase_and_penalty_to_bankaccount=new HashMap<String,String>();
		payable_repurchase_and_penalty_to_bankaccount.putAll(LedgerBookHandlerImpl.payable_repurchase_and_penalty_to_banksaving);
		for(String key:payable_repurchase_and_penalty_to_bankaccount.keySet())
		{
			String dest=payable_repurchase_and_penalty_to_bankaccount.get(key);
			if(dest.equals(ChartOfAccount.FST_BANK_SAVING))
				payable_repurchase_and_penalty_to_bankaccount.put(key, ChartOfAccount.SND_BANK_SAVING_NFQ_YL);
		}
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,payable_repurchase_and_penalty_to_bankaccount);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_REPURCHASE, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_CUSTODY_LOAN_PENALTY, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_YL, book, assetCategory));
		
		LedgerItem payable_for_repurchase_ledger_ledger=repurchase_ledgers.get(0);
		LedgerItem custody_penalty_ledger=custody_penalty_ledgers.get(0);
		LedgerItem bank_saving_ledger=bank_saving_ledgers.get(0);
	
		
		LedgerBookTestUtils.assertParty(payable_for_repurchase_ledger_ledger,payableparty);
		LedgerBookTestUtils.assertParty(custody_penalty_ledger,payableparty);
		LedgerBookTestUtils.assertParty(bank_saving_ledger,payableparty);
		
		
		
		
	}

	



	
	
	
	private LedgerTradeParty book_loan_helper(AssetSet loan_asset,LedgerBook book) throws DuplicateAssetsException
	{
		LedgerTradeParty party=new LedgerTradeParty();
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		anMeiTuLedgerBookHandler.book_loan_asset(book,loan_asset,party);
		return party;
	
	}
	private LedgerTradeParty book_loan_helper_V2(AssetSet loan_asset,LedgerBook book) throws DuplicateAssetsException
	{
		LedgerTradeParty party=new LedgerTradeParty();
		party.setFstParty(ledgerBookOrgnizationId);
		party.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		try {
			anMeiTuLedgerBookHandler.book_loan_asset_V2(book,loan_asset,party);
		} catch(Exception e){
			e.printStackTrace();
		 fail();
		}
		return party;
	
	}
	private void amortize_loan_helper(AssetSet loan_asset,LedgerBook book) throws AlreadayCarryOverException, InsufficientBalanceException, InvalidCarryOverException, InvalidLedgerException
	{
		Date now=new Date();
		List<LedgerItem> qualified_ledgers=ledgerItemService.get_amortizable_loan_ledger_at_date(book,now);
		
		LedgerItem qualified_ledger=qualified_ledgers.get(0);
		anMeiTuLedgerBookHandler.amortize_loan_asset_to_receivable(book, loan_asset);
			
	}
	
	private LedgerTradeParty classify_overdue_helper(AssetSet loan_asset,LedgerBook book) throws AlreadayCarryOverException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException
	{
		
		Date now=new Date();
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		TAccountInfo account=entryBook.get(ChartOfAccount.FST_RECIEVABLE_ASSET);
		Date nextDay=DateUtils.addDays(now,1);
		LedgerTradeParty guranteeparty=new LedgerTradeParty();
		guranteeparty.setFstParty(ledgerBookOrgnizationId);
		guranteeparty.setSndParty(guranteepCompanyId);
		anMeiTuLedgerBookHandler.classify_receivable_loan_asset_to_overdue(nextDay,book,loan_asset);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		anMeiTuLedgerBookHandler.book_receivable_load_guarantee_and_assets_sold_for_repurchase(book, assetCategory, guranteeparty, loan_asset.getAssetInitialValue());
		return  	guranteeparty;
	}
	private void claim_penalty_helper(AssetSet loan_asset,LedgerBook book,LedgerTradeParty guranteeparty,AssetValuationDetail assetValuationDetail ) throws AlreadayCarryOverException, InvalidLedgerException
	{
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		
		List<LedgerItem> overDueLedgers=
				ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,book,
				loan_asset.getAssetUuid());

		LedgerItem overDue_ledger=overDueLedgers.get(0);
		anMeiTuLedgerBookHandler.claim_penalty_on_overdue_loan(book,loan_asset, assetValuationDetail);
		
		
	}
	private LedgerTradeParty recover_receivable_guranttee_helper(AssetSet loan_asset,LedgerBook book, LedgerTradeParty payableparty) throws AlreadayCarryOverException, LackBusinessVoucherException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException
	{
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		List<LedgerItem> gurantee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE,book,loan_asset.getAssetUuid());
		LedgerItem gurantee_ledger=gurantee_ledgers.get(0);
		
		String BusinessVoucherUUid="aaa";
		String JournalVoucherUUid="bbb";
		String SourceDocumentUUid="ccc";
		 
		 LedgerTradeParty independentparty=new LedgerTradeParty();
		 independentparty.setFstParty(guranteepCompanyId);
		 independentparty.setSndParty(loan_asset.getContract().getCustomer().getId()+"");
		 anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,JournalVoucherUUid,BusinessVoucherUUid,SourceDocumentUUid,independentparty,payableparty, null, loan_asset.getAssetInitialValue(), true);
		 anMeiTuLedgerBookHandler.repurchase_to_payable(book, loan_asset, JournalVoucherUUid, BusinessVoucherUUid, SourceDocumentUUid, payableparty); 
		 return independentparty;

	}

	private LedgerTradeParty recover_receivable_overdue_and_penalty_helper(AssetSet loan_asset,LedgerBook book,BigDecimal totalPenalty) throws AlreadayCarryOverException, InsufficientPenaltyException,LackBusinessVoucherException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException
	{
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		
		String businessVoucherUUid="xxxx";
		String journalVoucherUUid="yyy";
		String sourceDocumentUUid="zzz";
		 
		LedgerTradeParty payableparty=new LedgerTradeParty();
		payableparty.setFstParty(guranteepCompanyId);
		payableparty.setSndParty(ledgerBookOrgnizationId);
		
		LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
		expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
		expectedBankSavingParty.setSndParty("");
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,expectedBankSavingParty,DepositeAccountType.BANK);
		
	 
		anMeiTuLedgerBookHandler.recover_receivable_loan_asset(book, loan_asset, journalVoucherUUid,businessVoucherUUid,sourceDocumentUUid, payableparty, loan_asset.getAssetInitialValue().add(totalPenalty), bankinfo, true);
		anMeiTuLedgerBookHandler.repurchase_to_payable(book, loan_asset, journalVoucherUUid, businessVoucherUUid, sourceDocumentUUid, payableparty); 
		return payableparty;
	}
	
	
	private void paid_payable_loan_and_penalty_helper(AssetSet loan_asset,
			LedgerBook book) throws AlreadayCarryOverException, LackBusinessVoucherException, InvalidLedgerException, InsufficientBalanceException, InvalidCarryOverException {
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		
		String BusinessVoucherUUid="xxxx";
		String JournalVoucherUUid="yyy";
		String SourceDocumentUUid="zzz";
		 
		LedgerTradeParty payableparty=new LedgerTradeParty();
		payableparty.setFstParty(guranteepCompanyId);
		payableparty.setSndParty(ledgerBookOrgnizationId);
		LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
		expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
		expectedBankSavingParty.setSndParty("");
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_YL,expectedBankSavingParty,DepositeAccountType.UINON_PAY);
		
		anMeiTuLedgerBookHandler.paid_receivable_overdue_and_penalty(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid,payableparty, bankinfo);
	
	}
	private void assertBookedOneLedgerItem(LedgerItem costItem,AccountSide accountSide, BigDecimal amount, AssetSet assetSet,String accountName){
		assertEquals(accountSide.ordinal(),costItem.getAccountSide());
		
		assertEquals(true,costItem.InTAccount(entryBook.get(accountName)));
		if(accountSide==AccountSide.DEBIT){
			assertEquals(0,BigDecimal.ZERO.compareTo(costItem.getCreditBalance()));
			assertEquals(amount,costItem.getDebitBalance());
		} else{
			assertEquals(0,BigDecimal.ZERO.compareTo(costItem.getDebitBalance()));
			assertEquals(amount,costItem.getCreditBalance());
		}
		
		assertEquals(assetSet.getContract().getId(),costItem.getContractId());
		//摊销日
		//assertEquals(,costItem.getAmortizedDate());
		assertEquals(assetSet.getAssetUuid(),costItem.getRelatedLv1AssetUuid());
		assertEquals(LedgerLifeCycle.BOOKED,costItem.getLifeCycle());
		assertTrue(StringUtils.isEmpty(costItem.getForwardLedgerUuid()));
		assertTrue(StringUtils.isEmpty(costItem.getBackwardLedgerUuid()));
		assertTrue(DateUtils.isSameDay(new Date(), costItem.getBookInDate()));
		assertNull(costItem.getCarriedOverDate());
	}
	
	private void assertCarriedOneLedgerItem(LedgerItem costItem,AccountSide accountSide, BigDecimal amount, AssetSet assetSet,String accountName,String forwUuid){
		assertEquals(accountSide.ordinal(),costItem.getAccountSide());
		
		assertEquals(true,costItem.InTAccount(entryBook.get(accountName)));
		assertEquals(0,amount.compareTo(costItem.getDebitBalance()));
		assertEquals(0,amount.compareTo(costItem.getCreditBalance()));
		
		assertEquals(assetSet.getContract().getId(),costItem.getContractId());
		//摊销日
		//assertEquals(,costItem.getAmortizedDate());
		assertEquals(assetSet.getAssetUuid(),costItem.getRelatedLv1AssetUuid());
		assertEquals(LedgerLifeCycle.CARRY_OVER,costItem.getLifeCycle());
		assertEquals(forwUuid,costItem.getForwardLedgerUuid());
		//assertTrue(DateUtils.isSameDay(new Date(), costItem.getBookInDate()));
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),costItem.getCarriedOverDate()));
	}
	
	private void assetBookItemBank(LedgerItem carriedItem,String jvuuid,String bvUuid,String sourceDocumentUuid,String backForwardUuid,String forwardUuid, AccountSide accountSide, BigDecimal bigDecimal, String accountName, AssetSet assetSet){
		assertEquals(accountSide.ordinal(),carriedItem.getAccountSide());
		assertEquals(carriedItem.getJournalVoucherUuid(),jvuuid);
		assertEquals(carriedItem.getBusinessVoucherUuid(),bvUuid);
		assertEquals(carriedItem.getSourceDocumentUuid(),sourceDocumentUuid);
		assertEquals(LedgerLifeCycle.BOOKED,carriedItem.getLifeCycle());
		assertEquals(backForwardUuid,carriedItem.getForwardLedgerUuid());
		assertEquals(forwardUuid,carriedItem.getBackwardLedgerUuid());
		assertEquals(assetSet.getAssetUuid(),carriedItem.getRelatedLv1AssetUuid());
	}
	private void assertWriterOffReceiveAsset(LedgerItem ledgerItem_receivale_load, LedgerItem ledgerItem_sold_for_repurchase){
		assertTrue(StringUtils.isEmpty(ledgerItem_receivale_load.getForwardLedgerUuid()));
		assertEquals(0,ledgerItem_receivale_load.getDebitBalance().compareTo(ledgerItem_receivale_load.getCreditBalance()));
		assertEquals(LedgerLifeCycle.WRITTEN_OFF,ledgerItem_receivale_load.getLifeCycle());
		
		assertTrue(StringUtils.isEmpty(ledgerItem_sold_for_repurchase.getForwardLedgerUuid()));
		assertEquals(0,ledgerItem_sold_for_repurchase.getDebitBalance().compareTo(ledgerItem_sold_for_repurchase.getCreditBalance()));
		assertEquals(LedgerLifeCycle.WRITTEN_OFF,ledgerItem_sold_for_repurchase.getLifeCycle());
	}
	
	
	
	
	
	
	
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_book_cost_payable.sql")
	public void test_book_cost_and_payable(){
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		String assetUuid = "asset_uuid_2";
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 2L);
		BigDecimal amount = new BigDecimal("10.00");
		LedgerTradeParty cost_party = new LedgerTradeParty("1","1");
		anMeiTuLedgerBookHandler.book_cost_remittance_fee_and_payable_remittance_fee(assetSet, amount, book, cost_party);
		List<LedgerItem> ledgerItemList = ledgerItemService.get_booked_ledgers_of_asset(book, assetUuid);
		assertEquals(2, ledgerItemList.size());
		List<LedgerItem> costs = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_COST_REMITTANCE_FEE, book, assetSet.getAssetUuid());
		assertEquals(1, costs.size());
		
		
		
		List<LedgerItem> cost_remittance_fee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_COST_REMITTANCE_FEE,book,
				assetSet.getAssetUuid());
		List<LedgerItem> payable_remittance_fee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE,book,
				assetSet.getAssetUuid());
					
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(cost_remittance_fee_ledgers);
		ledgersForTest.addAll(payable_remittance_fee_ledgers);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE+AccountSide.CREDIT.getAlias(),amount);
	
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_COST_REMITTANCE_FEE+AccountSide.DEBIT.getAlias(),amount);
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(assetSet,allRecordMap,creditAccountNames,debitAccountNames,new HashMap());
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
		
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_COST_REMITTANCE_FEE, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE, book, assetCategory));
		
		
		LedgerItem payable_remittance_fee_ledger=payable_remittance_fee_ledgers.get(0);
		LedgerItem cost_remittance_fee_ledger=cost_remittance_fee_ledgers.get(0);
		LedgerTradeParty pay = new LedgerTradeParty(cost_party.getSndParty(),cost_party.getFstParty());
		LedgerBookTestUtils.assertParty(payable_remittance_fee_ledger,pay);
		LedgerBookTestUtils.assertParty(cost_remittance_fee_ledger,cost_party);
		
		LedgerItem costItem = costs.get(0);
		
		List<LedgerItem> fee_item_list = ledgerItemService.get_ledgers_of_asset_in_taccount(book, assetUuid, ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE, LedgerLifeCycle.BOOKED.ordinal());
		assertEquals(1,fee_item_list.size());
		LedgerItem refund_item = fee_item_list.get(0);
		assertBookedOneLedgerItem(refund_item, AccountSide.CREDIT, amount, assetSet, ChartOfAccount.SND_PAYABLE_REMITTANCE_FEE);
		
	}
	
	

	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_recover_receivable_two_parts(){
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		loan_asset.setAssetRecycleDate(new Date());
		repaymentPlanService.save(loan_asset);
		try {
			
			String BusinessVoucherUUid="",JournalVoucherUUid="",SourceDocumentUUid="";
			LedgerTradeParty party=book_loan_helper(loan_asset,book);
			amortize_loan_helper(loan_asset,book);
			LedgerTradeParty guranteeparty=classify_overdue_helper( loan_asset, book);
			BigDecimal penaltyAmount=BigDecimal.valueOf(5.0);
			
			AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 2L);
			
			claim_penalty_helper(loan_asset,book,guranteeparty,assetValuationDetail);
			LedgerTradeParty payableparty=new LedgerTradeParty();
			payableparty.setFstParty(guranteepCompanyId);
			payableparty.setSndParty(ledgerBookOrgnizationId);
			
			BusinessVoucherUUid="xxxx";
			JournalVoucherUUid="yyy";
			SourceDocumentUUid="zzz";
			LedgerTradeParty expectedBankSavingParty=new LedgerTradeParty();
			expectedBankSavingParty.setFstParty(ledgerBookOrgnizationId);
			expectedBankSavingParty.setSndParty("");
			DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,expectedBankSavingParty,DepositeAccountType.BANK);
			
			BigDecimal first=loan_asset.getAssetInitialValue().subtract(new BigDecimal("100"));
			anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty, bankinfo,first, true);
			BigDecimal second=loan_asset.getAssetInitialValue().subtract(first);
			anMeiTuLedgerBookHandler.recover_receivable_guranttee(book,loan_asset,BusinessVoucherUUid,JournalVoucherUUid,SourceDocumentUUid,guranteeparty,payableparty,bankinfo,second, true);
			
			
			List<LedgerItem> guarantee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE,book,
					loan_asset.getAssetUuid());
			List<LedgerItem> bank_saving_zsy_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,book,
					loan_asset.getAssetUuid());
						
			
			List<LedgerItem> ledgersForTest=new ArrayList();
			ledgersForTest.addAll(guarantee_ledgers);
			ledgersForTest.addAll(bank_saving_zsy_ledgers);
			
			HashMap<String,BigDecimal> creditAccountNames=new HashMap();
			LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.CREDIT.getAlias(),first);
			LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.CREDIT.getAlias(),second);
		
			HashMap<String,BigDecimal> debitAccountNames=new HashMap();
			LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
			LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),first);
			LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH+AccountSide.DEBIT.getAlias(),second);
			
			HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
			
			HashMap<String,String> recievable_to_bank_saving_carry_over_table_with_bank=new HashMap<String,String>();
			recievable_to_bank_saving_carry_over_table_with_bank.putAll(LedgerBookHandlerImpl.gurrantee_to_bank_saving_table);
			for(String key:recievable_to_bank_saving_carry_over_table_with_bank.keySet())
			{
				String dest=recievable_to_bank_saving_carry_over_table_with_bank.get(key);
				if(dest.equals(ChartOfAccount.FST_BANK_SAVING))
					recievable_to_bank_saving_carry_over_table_with_bank.put(key, ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH);
			}
			
			LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,recievable_to_bank_saving_carry_over_table_with_bank);
			AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
			
			assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, book, assetCategory));
			assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH, book, assetCategory));
			
			
			LedgerItem bankSaving=bank_saving_zsy_ledgers.get(0);
			LedgerItem guaranttee_ledger=guarantee_ledgers.get(0);
			party.setFstParty(ledgerBookOrgnizationId);
			party.setSndParty("");
			LedgerBookTestUtils.assertParty(bankSaving,party);
			party.setFstParty(ledgerBookOrgnizationId);
			party.setSndParty(guranteepCompanyId);
			LedgerBookTestUtils.assertParty(guaranttee_ledger,party);
		
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void test_book_receivable_overDue_fee(){
		BigDecimal obligation = new BigDecimal("100");
		BigDecimal serviceFee = new BigDecimal("110");
		BigDecimal otherFee = new BigDecimal("120");
		BigDecimal penalty = new BigDecimal("130");
		Map<String,BigDecimal> account_delta_amount = new HashMap<String,BigDecimal>();
		account_delta_amount.put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, penalty);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, obligation);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE, serviceFee);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE, otherFee);
		
		
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		AssetCategory categorty = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		LedgerTradeParty party = new LedgerTradeParty("company_yunxin","ppd");
		
		try {
			anMeiTuLedgerBookHandler.refresh_receivable_overDue_fee(book, categorty, party, account_delta_amount);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
		
		List<LedgerItem> receive_overdue_fee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_FEE,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> revenue_fee_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_OVERDUE_FEE,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> penalty_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> inverstment_estimate_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE,book,
				loan_asset.getAssetUuid());
		
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(receive_overdue_fee_ledgers);
		ledgersForTest.addAll(revenue_fee_ledgers);
		ledgersForTest.addAll(penalty_ledgers);
		ledgersForTest.addAll(inverstment_estimate_ledgers);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OBLIGATION+AccountSide.CREDIT.getAlias(),obligation);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_SERVICE_FEE+AccountSide.CREDIT.getAlias(),serviceFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OTHER_FEE+AccountSide.CREDIT.getAlias(),otherFee);
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE+AccountSide.CREDIT.getAlias(),penalty);
		
		
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION+AccountSide.DEBIT.getAlias(),obligation);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE+AccountSide.DEBIT.getAlias(),serviceFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE+AccountSide.DEBIT.getAlias(),otherFee);
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),penalty);

		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,new HashMap());
		
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OBLIGATION, book, categorty));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_SERVICE_FEE, book, categorty));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OTHER_FEE, book, categorty));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE, book, categorty));
		

		
		//penalty不变
		obligation = new BigDecimal("910");
		serviceFee = new BigDecimal("920");
		otherFee = new BigDecimal("930");
		account_delta_amount = new HashMap<String,BigDecimal>();
		account_delta_amount.put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, penalty);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, obligation);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE, serviceFee);
		account_delta_amount.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE, otherFee);
		try {
			anMeiTuLedgerBookHandler.refresh_receivable_overDue_fee(book, categorty, party, account_delta_amount);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
		assertEquals(0,obligation.negate().compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OBLIGATION), book, categorty)));
		assertEquals(0,serviceFee.negate().compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_SERVICE_FEE), book, categorty)));
		assertEquals(0,otherFee.negate().compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OTHER_FEE), book, categorty)));
		assertEquals(0,penalty.negate().compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE), book, categorty)));
		
		assertEquals(0,obligation.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION), book, categorty)));
		assertEquals(0,serviceFee.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE), book, categorty)));
		assertEquals(0,otherFee.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE), book, categorty)));
		assertEquals(0,penalty.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY), book, categorty)));
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_ledger_book.sql")
	public void testWriteOffLedgerByAccount(){
		LedgerBook book=new LedgerBook(LedgerBookNo,ledgerBookOrgnizationId);
		String assetUuid = "asset_uuid_2";
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 2L);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
		LedgerTradeParty ledgerTradeParty = new LedgerTradeParty("1","2");
		Map<String,BigDecimal> account_amount_map = new HashMap<String,BigDecimal>();
		BigDecimal obligation = new BigDecimal("100");
		BigDecimal serviceFee = new BigDecimal("110");
		BigDecimal otherFee = new BigDecimal("120");
		account_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, obligation);
		account_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE, serviceFee);
		account_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE, otherFee);
		try {
			anMeiTuLedgerBookHandler.refresh_receivable_overDue_fee(book, assetCategory, ledgerTradeParty, account_amount_map);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		try {
			ledgerItemService.write_off_ledgers_accounts(book, assetCategory, ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, ChartOfAccount.TRD_REVENUE_OVERDUE_FEE_OBLIGATION);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, book, assetCategory));
		assertEquals(0,serviceFee.compareTo(ledgerItemService.get_absolute_alance_of_account(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE, book, assetCategory)));
		assertEquals(0,otherFee.compareTo(ledgerItemService.get_absolute_alance_of_account(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE, book, assetCategory)));
		
	}
	
	
		

}
