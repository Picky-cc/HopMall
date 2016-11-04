package com.zufangbao.earth.yunxin.ledgerBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.entity.Result;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.yunxin.handler.OfflineBillHandler;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerBookHandlerImpl;
import com.zufangbao.sun.ledgerbook.LedgerItem;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerLifeCycle;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.ledgerBook.LedgerBookTestUtils;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class LedgerBookIntegrationTest {

	@Autowired
	private LedgerBookHandler anMeiTuLedgerBookHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private LedgerBookTestUtils ledgerBookTestUtils;
	
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Autowired
	private FinancialContractService factoringContractService;
	
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Autowired
	private LedgerBookService LedgerBookService;
	@Autowired
	private OfflineBillHandler offlineBillHandler;
	
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	@Qualifier("mockBankAccountCache")
	private BankAccountCache mockBankAccountCache;
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_loan_import_nfq.sql")
	public void testLedgerBookImportPackage(){
		Long factoringContractId = 1L;
		String fileName = "../sun/src/test/resources/test/yunxin/ledger_book/nfq_ledger_book.xlsx";
		Result result = new Result();
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			result = assetPackageService.importAssetPackagesViaExcel(input,
					factoringContractId, "test", "127.0.0.1");
			Assert.assertEquals("导入成功", result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//校验资产的数量，数据库中已有2份资产，新增2个资产
		List<AssetSet> assetSetList = repaymentPlanService.list(AssetSet.class, new Filter());
		assertEquals(2,assetSetList.size());
		
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		for (AssetSet assetSet : assetSetList) {
			AssetCategory assetCategory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
			
			List<LedgerItem> unearnedLoanRecords=ledgerItemService.get_all_ledgers_of_asset_in_taccount(book, assetSet.getAssetUuid(), ChartOfAccount.FST_UNEARNED_LOAN_ASSET);
			List<LedgerItem> absorbsving_Records=ledgerItemService.get_all_ledgers_of_asset_in_taccount(book, assetSet.getAssetUuid(), ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING);
			List<LedgerItem> revenue_Records=ledgerItemService.get_all_ledgers_of_asset_in_taccount(book, assetSet.getAssetUuid(), ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE);
			if(assetSet.getCurrentPeriod()==1){
				//本金利息有一项为0则不导入
				assertEquals(1,unearnedLoanRecords.size());
				assertEquals(0,absorbsving_Records.size());
			}else if(assetSet.getCurrentPeriod()==2){
				assertEquals(2,unearnedLoanRecords.size());
				assertEquals(1,absorbsving_Records.size());
				assertEquals(LedgerLifeCycle.BOOKED,absorbsving_Records.get(0).getLifeCycle());
			}
			assertEquals(1,revenue_Records.size());
			assertEquals(LedgerLifeCycle.BOOKED,unearnedLoanRecords.get(0).getLifeCycle());
			assertEquals(LedgerLifeCycle.BOOKED,revenue_Records.get(0).getLifeCycle());
			if(assetSet.getCurrentPeriod()==1){
				assertEquals(0,new BigDecimal("0").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING), book, assetCategory)));
				assertEquals(0,new BigDecimal("-7200.00").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE), book, assetCategory)));
				assertEquals(0,new BigDecimal("0").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE), book, assetCategory)));
				assertEquals(0,new BigDecimal("7200.00").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST), book, assetCategory)));
				
			}else if(assetSet.getCurrentPeriod()==2) {
				assertEquals(0,new BigDecimal("-60000").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING), book, assetCategory)));
				assertEquals(0,new BigDecimal("-8400.00").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE), book, assetCategory)));
				assertEquals(0,new BigDecimal("60000.00").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_PRINCIPLE), book, assetCategory)));
				assertEquals(0,new BigDecimal("8400.00").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_INTEREST), book, assetCategory)));
				
			}
		}
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_receivable_loan.sql")
	public void testReceivableLoan(){
		String dateString = "2016-05-01";
		Date date = DateUtils.asDay(dateString);
		//asset1今日到期，asset2未到期
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		try {
			assetValuationDetailHandler.assetValuation(assetSet, date);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//ledger_uuid_3结转为应收贷款
		
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		List<LedgerItem> unearned_asset_ledger = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_UNEARNED_LOAN_ASSET,book,assetSet.getAssetUuid());
		assertEquals(2, unearned_asset_ledger.size());
		
		List<LedgerItem> receviable_assets_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				assetSet.getAssetUuid());
		List<LedgerItem> deferred_income_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_DEFERRED_INCOME,book,
				assetSet.getAssetUuid());
		List<LedgerItem> revenue_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_REVENUE,book,
				assetSet.getAssetUuid());			
		
		List<LedgerItem> ledgersForTest=new ArrayList();
		ledgersForTest.addAll(unearned_asset_ledger);
		ledgersForTest.addAll(receviable_assets_ledgers);
		ledgersForTest.addAll(deferred_income_ledgers);
		ledgersForTest.addAll(revenue_ledgers);
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),assetSet.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),assetSet.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.FST_UNEARNED_LOAN_ASSET+AccountSide.CREDIT.getAlias(),assetSet.getAssetInitialValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.DEBIT.getAlias(),assetSet.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.FST_UNEARNED_LOAN_ASSET+AccountSide.DEBIT.getAlias(),assetSet.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET+AccountSide.DEBIT.getAlias(),assetSet.getAssetInitialValue());
		
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem( ledgersForTest);
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(assetSet,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.unearned_loan_to_recievable_carry_over_table);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
		
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_UNEARNED_LOAN_ASSET, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_LONGTERM_LIABILITY_ABSORB_SAVING, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_REVENUE_INTEREST, book, assetCategory));
		assertFalse(ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_RECIEVABLE_ASSET, book, assetCategory));
		
		LedgerItem receivable_asset_ledger_item = unearned_asset_ledger.get(0);
		assertEquals(0,new BigDecimal("1000").compareTo(receivable_asset_ledger_item.getDebitBalance()));
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_overdue_penalty.sql")
	public void testOverdueLoan(){
		String dateString = "2016-05-02";
		Date date = DateUtils.asDay(dateString);
		//asset1逾期1天，asset2未到期
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		try {
			assetValuationDetailHandler.assetValuation(assetSet, date);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuAssetCategory(assetSet, "", null);
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, book, assetCategory));
		assertEquals(0,new BigDecimal("1000").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET), book, assetCategory)));
		assertEquals(0,new BigDecimal("0.10").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY), book, assetCategory)));
		assertEquals(0,new BigDecimal("-0.10").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE), book, assetCategory)));
		
		dateString = "2016-05-03";
		date = DateUtils.asDay(dateString);
		//asset1逾期1天，asset2未到期
		try {
			assetValuationDetailHandler.assetValuation(assetSet, date);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(0,new BigDecimal("0.20").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY), book, assetCategory)));
		assertEquals(0,new BigDecimal("-0.20").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_REVENUE_INVESTMENT_ESTIMATE), book, assetCategory)));
	}
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_receive_loan_to_bankSaving.sql")
	public void testRecivableToBank(){
		SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 1L);
		try {
			journalVoucherHandler.createJVFromSourceDocument(sourceDocument,mockBankAccountCache, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		// asset1为应收资产, asset2 为逾期资产含罚息
		//测试asset1
		LedgerBook book = LedgerBookService.load(LedgerBook.class, 1L);
		AssetSet asset1 = repaymentPlanService.load(AssetSet.class, 1L);
		BigDecimal amount = asset1.getAssetFairValue();
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuAssetCategory(asset1, "", null);
		
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, book, assetCategory));
		assertEquals(0,amount.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory)));
		
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_receive_overdue_loan_to_bankSaving.sql")
	public void testReceive_overdue_loan_to_bankSaving(){
		//测试asset2
		SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		try {
			journalVoucherHandler.createJVFromSourceDocument(sourceDocument,mockBankAccountCache, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//asset2 为逾期资产含罚息
		AssetSet asset2 = repaymentPlanService.load(AssetSet.class, 2L);
		BigDecimal amount = asset2.getAssetFairValue();
		BigDecimal penalty = new BigDecimal("0.10");
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuAssetCategory(asset2, "", null);
		
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, book, assetCategory));
		assertTrue(ledgerItemService.is_zero_balanced_account(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, book, assetCategory));
		assertEquals(0,amount.compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory)));
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_recover_guarantee.sql")
	public void testBuildAssociationOrderOfAll() {
		//回收担保单
		String offlineBillUuid = "offline_bill_uuid_1";
		String orderNo = "DKHD-001-02-20160308";
		Order order = orderService.load(Order.class,2L);
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 2L);
		assertEquals(OrderType.GUARANTEE, order.getOrderType());
		assertEquals(OrderClearStatus.UNCLEAR, order.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE, order.getExecutingSettlingStatus());
		BigDecimal connectionAmount = order.getTotalRent();
		List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
		assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(-1, connectionAmount.compareTo(sourceDocument.getOutlierAmount()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(orderNo, connectionAmount + "");
		try {
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount,-1,"");
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		List<LedgerItem> gurantee_ledgers=ledgerItemService.
				get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE,book, assetSet.getAssetUuid());
		assertEquals(2,gurantee_ledgers.size());
		LedgerItem gurantee_ledger=gurantee_ledgers.get(1);
		assertEquals(0,new BigDecimal("1000.00").compareTo(gurantee_ledger.getCreditBalance()));
		List<LedgerItem> bank_saving_ledger=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_BANK_SAVING,book, assetSet.getAssetUuid());
		
		assertEquals(1,bank_saving_ledger.size());
		LedgerItem bankSaving=bank_saving_ledger.get(0);
		assertEquals(0,new BigDecimal("1000.00").compareTo(bankSaving.getDebitBalance()));
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_recover_order.sql")
	public void testBuildAssociationOrder_repamentOrder() {
		//回收还款单
		String offlineBillUuid = "offline_bill_uuid_1";
		String orderNo = "DKHD-001-02-20160308";
		Order order = orderService.load(Order.class,2L);
		AssetSet asset2 = repaymentPlanService.load(AssetSet.class, 2L);
		assertEquals(OrderType.NORMAL, order.getOrderType());
		assertEquals(OrderClearStatus.UNCLEAR, order.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE, order.getExecutingSettlingStatus());
		BigDecimal connectionAmount = order.getTotalRent();
		List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
		assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(-1, connectionAmount.compareTo(sourceDocument.getOutlierAmount()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(orderNo, connectionAmount + "");
		try {
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount,-1,"");
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		
		List<LedgerItem> receiveble_loan_asset_list = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET,book, asset2.getAssetUuid());
		assertEquals(2,receiveble_loan_asset_list.size());
		assertEquals(0,new BigDecimal("1000").compareTo(receiveble_loan_asset_list.get(1).getCreditBalance()));
		
		List<LedgerItem> receivable_overdue_loan1_list = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET,book, asset2.getAssetUuid());
		assertEquals(0,receivable_overdue_loan1_list.size());
		List<LedgerItem> receivable_loan1_penalty_list = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY,book, asset2.getAssetUuid());
		assertEquals(0,receivable_loan1_penalty_list.size());
		
		List<LedgerItem> bank_saving_list = ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_BANK_SAVING,book, asset2.getAssetUuid());
		assertEquals(1, bank_saving_list.size());
		assertEquals(0,new BigDecimal("1000").compareTo(bank_saving_list.get(0).getDebitBalance()));
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book/test_recover_order_with_penalty.sql")
	public void testBuildAssociationOrder_repamentOrder_with_penalty() {
		//回收还款单
		String offlineBillUuid = "offline_bill_uuid_1";
		String orderNo = "DKHD-001-02-20160308";
		Order order = orderService.load(Order.class,2L);
		AssetSet asset2 = repaymentPlanService.load(AssetSet.class, 2L);
		assertEquals(OrderType.NORMAL, order.getOrderType());
		assertEquals(OrderClearStatus.UNCLEAR, order.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE, order.getExecutingSettlingStatus());
		BigDecimal connectionAmount = order.getTotalRent();
		List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
		assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(-1, connectionAmount.compareTo(sourceDocument.getOutlierAmount()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(orderNo, connectionAmount + "");
		try {
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount,-1,"");
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		LedgerBook book =  LedgerBookService.load(LedgerBook.class, 1L);
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(asset2);

		assertEquals(0,new BigDecimal("0").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET), book, assetCategory)));
		assertEquals(0,new BigDecimal("0").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY), book, assetCategory)));
		assertEquals(0,new BigDecimal("1000.10").compareTo(ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory)));
		
	}
}
