package com.zufangbao.sun.yunxin.ledgerBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class LedgerBookHandlerTest {

	@Autowired LedgerItemService ledgerItemService;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	private HashMap<String,TAccountInfo> entryBook=ChartOfAccount.EntryBook();
	
	@Autowired
	public LedgerBookTestUtils ledgerBookTestUtils;
	private String ledgerBookNo = "yunxin_ledger_book";
	private String ledgerBookOrgnizationId="14";
	private String guranteepCompanyId="16";

	@Test
	@Sql("classpath:test/yunxin/ledger_book/handlerTest/test_recover_asset_mullit_accounts_twice.sql")
	public void testPartialCarryOver(){
		LedgerBook book = ledgerBookService.getBookByBookNo(ledgerBookNo);
		String assetUuid = "asset_uuid_1";
		AssetSet loan_asset = repaymentPlanService.load(AssetSet.class, 1L);
		AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(loan_asset);
		LedgerTradeParty ledgerTradeParty = new LedgerTradeParty("1","2");
		LedgerTradeParty payTradeParty = new LedgerTradeParty("2","1");
		Map<String,BigDecimal> account_amount_map = new HashMap<String,BigDecimal>();
		String jvUuid = "jvUuid";
		String bvUuid = "bvUuid";
		String sdUuid = "sdUuid";
		
		BigDecimal overdue_principal = new BigDecimal("1000");
		BigDecimal overdue_fee_obligation = new BigDecimal("1");
		BigDecimal overdue_fee_service_fee = new BigDecimal("100");
		BigDecimal overdue_fee_other_fee = new BigDecimal("1");
		BigDecimal overdue_penalty = new BigDecimal("1");
		
		
		DepositeAccountInfo bankinfo=new DepositeAccountInfo(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH,ledgerTradeParty,DepositeAccountType.UINON_PAY);
		//recover twice
		try {
			ledgerBookHandler.recover_receivable_loan_asset(book, loan_asset, jvUuid, bvUuid, sdUuid, payTradeParty, new BigDecimal("900"), bankinfo, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//recover rest
		try {
			ledgerBookHandler.recover_receivable_loan_asset(book, loan_asset, jvUuid, bvUuid, sdUuid, payTradeParty, new BigDecimal("203"), bankinfo, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		/*List<LedgerItem> ledgersForTest=new ArrayList();
		
		List<LedgerItem> receivable_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_RECIEVABLE_ASSET,book,
				loan_asset.getAssetUuid());
		List<LedgerItem> bank_saving_asset_ledgers=ledgerItemService.get_booked_ledgers_of_asset_in_taccount(ChartOfAccount.FST_BANK_SAVING,book,
				loan_asset.getAssetUuid());
		
		ledgersForTest.addAll(receivable_asset_ledgers);
		ledgersForTest.addAll(bank_saving_asset_ledgers);
		
		
		HashMap<String,BigDecimal> creditAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_REVENUE_INTEREST+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(creditAccountNames,ChartOfAccount.SND_DEFERRED_INCOME_INTEREST_ESTIMATE+AccountSide.CREDIT.getAlias(),loan_asset.getAssetInterestValue());
		
		HashMap<String,BigDecimal> debitAccountNames=new HashMap();
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInitialValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
		LedgerBookTestUtils.buildMapping(debitAccountNames,ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE+AccountSide.DEBIT.getAlias(),loan_asset.getAssetInterestValue());
	
		HashMap<String,LedgerItem> allRecordMap=ledgerBookTestUtils.accountNameWithLedgerItem(ledgersForTest);
		
		LedgerBookTestUtils.assetBookedMultiAssetAndLibility(loan_asset,allRecordMap,creditAccountNames,debitAccountNames,LedgerBookHandlerImpl.unearned_loan_to_recievable_carry_over_table);
		*/
		
		boolean isReceiVableZero = ledgerItemService.is_zero_balanced_account(ChartOfAccount.FST_RECIEVABLE_ASSET, book, assetCategory);
		assertTrue(isReceiVableZero);
		BigDecimal bankAmount = ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory);
		assertEquals(0,new BigDecimal("1103").compareTo(bankAmount));
		
	}
}
