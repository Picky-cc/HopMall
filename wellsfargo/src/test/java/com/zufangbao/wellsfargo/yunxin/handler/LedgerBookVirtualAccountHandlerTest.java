package com.zufangbao.wellsfargo.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.DepositeAccountHandler;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItem;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerLifeCycle;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
public class LedgerBookVirtualAccountHandlerTest  extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	
	@Autowired
	@Qualifier("dataStatisticsCacheHandler")
	BankAccountCache bankAccountCache;
	
	@Autowired
	DepositeAccountHandler depositeAccountHandler;
	
	private void asset_ledger_book_without_asset(Long contractId,LedgerItem outAssetLedger,AccountSide outAccountSide,String expectedOutAccount,
			LedgerItem InAssetLedger,AccountSide inAccountSide,String expectedInAccount,
			String jvUuid,String bvUuid,String sdUuid){
		Map<String,TAccountInfo> acount = ChartOfAccount.EntryBook();
		
		assertEquals(jvUuid,outAssetLedger.getJournalVoucherUuid());
		assertEquals(bvUuid,outAssetLedger.getBusinessVoucherUuid());
		assertEquals(sdUuid,outAssetLedger.getSourceDocumentUuid());
		assertEquals(contractId,outAssetLedger.getContractId());
		assertEquals(outAccountSide.ordinal(),outAssetLedger.getAccountSide());
		outAssetLedger.InTAccount(acount.get(expectedOutAccount));
		assertEquals(LedgerLifeCycle.BOOKED,outAssetLedger.getLifeCycle());
		assertEquals(DateUtils.isSameDay(new Date(), outAssetLedger.getBookInDate()),true);
		
		assertEquals(jvUuid,InAssetLedger.getJournalVoucherUuid());
		assertEquals(bvUuid,InAssetLedger.getBusinessVoucherUuid());
		assertEquals(sdUuid,InAssetLedger.getSourceDocumentUuid());
		assertEquals(contractId,InAssetLedger.getContractId());
		assertEquals(inAccountSide.ordinal(),InAssetLedger.getAccountSide());
		InAssetLedger.InTAccount(acount.get(expectedInAccount));
		assertEquals(LedgerLifeCycle.BOOKED,InAssetLedger.getLifeCycle());
		assertEquals(DateUtils.isSameDay(new Date(), InAssetLedger.getBookInDate()),true);
		
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/ledger_book_related/test_deposit_independent_account_assets.sql")
	public void testDeposit_independent_account_assets(){
		LedgerBook book = ledgerBookService.load(LedgerBook.class, 1L);
		Contract contract = contractService.load(Contract.class, 1L);
		String yunxin = "yunxin";
		String customer = "customer";
		LedgerTradeParty bankParty = new LedgerTradeParty(yunxin,customer);
		LedgerTradeParty customerParty = new LedgerTradeParty(customer,yunxin);
		BigDecimal amount = new BigDecimal("10");
		String jvUuid = "jv_uuid_1",bvUuid="bv_uuid_1",sdUuid="sd_uuid_1";
		List<LedgerItem> ledgersBeforeSave = ledgerItemService.list(LedgerItem.class, new Filter());
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 1L);
		try {
			AssetCategory assetCategory = AssetConvertor.convertDepositAssetCategory(contract);
			DepositeAccountInfo bankAccountInfo=bankAccountCache.extractFirstBankAccountFrom(financialContract);
			DepositeAccountInfo custmerAccountInfo=new DepositeAccountInfo(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS,customerParty,DepositeAccountType.VIRTUAL_ACCOUNT);
			ledgerBookVirtualAccountHandler.deposit_independent_account_assets(book, bankAccountInfo, custmerAccountInfo, assetCategory, amount, jvUuid, bvUuid, sdUuid);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		List<LedgerItem> ledgersAfterSave = ledgerItemService.list(LedgerItem.class, new Filter());
		assertEquals(2,ledgersAfterSave.size()-ledgersBeforeSave.size());
		TAccountInfo independent_account_Info = ChartOfAccount.EntryBook().get(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS);
		
		List<LedgerItem> independent_accounts = ledgerItemService.get_all_ledgers_in_TAccount(book, independent_account_Info);
		assertEquals(1,independent_accounts.size());
		LedgerItem independent_ledger = independent_accounts.get(0);
		TAccountInfo bank_saving_account_Info = ChartOfAccount.EntryBook().get(ChartOfAccount.FST_BANK_SAVING);
		List<LedgerItem> bank_accounts = ledgerItemService.get_all_ledgers_in_TAccount(book, bank_saving_account_Info);
		assertEquals(1,bank_accounts.size());
		LedgerItem bank_ledger = bank_accounts.get(0);
		
		asset_ledger_book_without_asset(contract.getId(),bank_ledger, AccountSide.CREDIT,ChartOfAccount.TRD_BANK_SAVING_NFQ_ZSYH_INDEPENDENT_ASSETS, independent_ledger, 
				AccountSide.DEBIT, ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT, jvUuid, bvUuid, sdUuid);
	
		assertEquals(0,BigDecimal.ZERO.compareTo(bank_ledger.getCreditBalance()));
		assertEquals(0,amount.compareTo(bank_ledger.getDebitBalance()));
		
		assertEquals(0,amount.compareTo(independent_ledger.getCreditBalance()));
		assertEquals(0,BigDecimal.ZERO.compareTo(independent_ledger.getDebitBalance()));
	}
	
	@Test
	public void test_write_off_independent_account_assets(){
		LedgerBook book = ledgerBookService.load(LedgerBook.class, 1L);
		Date amortizedDate =  DateUtils.asDay("2016-08-01");
		BigDecimal amount = new BigDecimal("10");
		String jvUuid = "jv_uuid_1",bvUuid="bv_uuid_1",sdUuid="sd_uuid_1";
		Order order = orderService.getOrderById(1L, OrderType.NORMAL);
		try {
			ledgerBookVirtualAccountHandler.inner_transfer_independent_account_assets(book, order, amount, jvUuid, bvUuid, sdUuid);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
}
