package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.api.model.deduct.RecordStatus;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.AfterVoucherIssuedHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowAutoIssueHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowCacheHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class CashFlowAutoIssueHandlerTest {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private CashFlowAutoIssueHandler cashFlowAutoIssueHandler;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private CashFlowHandler cashFlowHandler;
	
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private CashFlowService cashFlowService;
	
	@Autowired
	private AfterVoucherIssuedHandler afterVoucherIssuedHandler;
	
	@Autowired
	private DeductApplicationService deductApplicationService;
	
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	
	private Page page = new Page(0,10);
	
	@Autowired
	private CashFlowCacheHandler cashFlowCacheHandler;
	
	private Long companyId = 1L;
	
	@Autowired
	private DataSource dataSource;
	
	private static final long TIMEOUT = 1000 * 10 * 2;
	
	private static final long HALF_TIMEOUT = TIMEOUT / 2;
	
	
	/*@Test
	@Sql("classpath:test/yunxin/audit/test_one_cashFlow_deposit_independent_account_assets.sql")
	public void testSystemAudit(){
		CashFlow cashFlow = cashFlowService.getCashFlowByCashFlowUuid("cash_flow_uuid_1");
		try {
			cashFlowAutoIssueHandler.charge_cash_into_virtual_account(cashFlow);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		BigDecimal amount = new BigDecimal("1200.60");
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/changeVoucher/test_issue_voucher.sql")
	public void testChangeVoucher_case1(){
		//部分对账
		String cashFlowUuid = "cash_flow_uuid_1";
		String journalVoucherUuid_lapse_1 = "journal_voucher_uuid_1";
		String journalVoucherUuid_lapse_2 = "journal_voucher_uuid_2";
		String businessVoucherUuid_1 = "business_voucher_uuid_1";
		String businessVoucherUuid_2 = "business_voucher_uuid_2";
		String billingPlanUuid = "repayment_uuid_3";
		BigDecimal newAmount = new BigDecimal("10.00");
		
		
		List<BillMatchResult> billMatchResultList = new ArrayList<BillMatchResult>();
		BillMatchResult billMatchResult = new BillMatchResult();
		billMatchResult.setJournalVoucherUuid(journalVoucherUuid_lapse_1);
		billMatchResult.setBillingPlanUuid(billingPlanUuid);
		billMatchResult.setBookingAmount(newAmount);
		billMatchResult.setCashFlowUuid(cashFlowUuid);
		billMatchResult.setBusinessVoucherUuid(businessVoucherUuid_1);
		billMatchResultList.add(billMatchResult);
		
		try {
			cashFlowHandler.changeVouchers(billMatchResultList, companyId, cashFlowUuid, AccountSide.DEBIT, afterVoucherIssuedHandler);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//jv和bv被废除
		JournalVoucher lapseJV_1 = journalVoucherService.getJournalVoucherByVoucherUUID(journalVoucherUuid_lapse_1);
		JournalVoucher lapseJV_2 = journalVoucherService.getJournalVoucherByVoucherUUID(journalVoucherUuid_lapse_2);
		assertEquals(JournalVoucherStatus.VOUCHER_LAPSE,lapseJV_1.getStatus());
		assertEquals(JournalVoucherStatus.VOUCHER_LAPSE,lapseJV_2.getStatus());
		
		BusinessVoucher bv_2 = businessVoucherService.list(BusinessVoucher.class, new Filter().addEquals("businessVoucherUuid", businessVoucherUuid_2)).get(0);
		assertEquals(BusinessVoucherStatus.VOUCHER_ISSUING,bv_2.getBusinessVoucherStatus());
		assertEquals(0,BigDecimal.ZERO.compareTo(bv_2.getSettlementAmount()));
		
		List<JournalVoucher> JVList = journalVoucherService.getVouchersByBillingPlanUidAndIssued(billingPlanUuid,companyId);
		assertEquals(1,JVList.size());
		JournalVoucher newJV = JVList.get(0);
		assertEquals(newAmount,newJV.getBookingAmount());
		assertEquals(AccountSide.DEBIT, newJV.getAccountSide());
		assertFalse(journalVoucherUuid_lapse_1.equals(newJV.getJournalVoucherUuid()));
		assertEquals(billingPlanUuid,newJV.getBillingPlanUuid());
		assertEquals(cashFlowUuid, newJV.getCashFlowUuid());
		assertEquals(businessVoucherUuid_1, newJV.getBusinessVoucherUuid());
		
		BusinessVoucher bv = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(billingPlanUuid, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(newAmount,bv.getSettlementAmount());
		
		//校验cash
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		assertEquals(0,newAmount.compareTo(appArriveRecord.getIssuedAmount()));
		assertEquals(AuditStatus.ISSUING,appArriveRecord.getAuditStatus());
	}*/
	
	@Test
	@Sql("classpath:test/yunxin/thirdPartyVoucher/recover_assets_by_third_party_deduction.sql")
	public void test_recover_assets_online_deduct_receivable(){
		FinancialContract financialContract = financialContractService.getFinancialContractBy("financial_contract_uuid_1");
		Long companyId = 1L;                                                                                                                                                        
		String assetSetUuid = "asset_uuid_1";
		String customerUuid = "customerUuid1";                                                                  
		BigDecimal bookingAmount = new BigDecimal("1000");
		try {
			cashFlowAutoIssueHandler.recover_assets_online_deduct_by_interface_each_financial_contract(financialContract);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		DeductApplication deductApplication = deductApplicationService.load(DeductApplication.class, 1L);
		assertEquals(RecordStatus.WRITE_OFF,deductApplication.getRecordStatus());
		
		//校验资产
		AssetSet asset_1 = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
		assertEquals(AssetClearStatus.CLEAR,asset_1.getAssetStatus());

		//校验结算单
		List<Order> orderList = orderService.getTodayOrderListBy(asset_1);
		assertEquals(1,orderList.size());
		Order order = orderList.get(0);
		assertEquals(OrderClearStatus.CLEAR,order.getClearingStatus());
		assertEquals(0,order.getTotalRent().compareTo(bookingAmount));
		
		//校验ledger_book
		BigDecimal unearned_amount = ledgerBookStatHandler.get_unearned_amount(financialContract.getLedgerBookNo(), assetSetUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(unearned_amount));
		BigDecimal receivable_amount = ledgerBookStatHandler.get_receivable_amount(financialContract.getLedgerBookNo(), assetSetUuid, customerUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(receivable_amount));
		
		//校验jv
		List<JournalVoucher> jvList = journalVoucherService.getInforceThirdPartyDeductJournalVoucher(companyId, assetSetUuid);
		assertEquals(1,jvList.size());
		assertEquals(0,jvList.get(0).getBookingAmount().compareTo(bookingAmount));
		
		//校验sourceDocument
		SourceDocument sourceDocument = sourceDocumentService.getOneSourceDocuments(SourceDocumentType.NOTIFY, AccountSide.DEBIT, SourceDocumentStatus.SIGNED, SourceDocument.FIRSTOUTLIER_DEDUCTAPPLICATION, deductApplication.getDeductApplicationUuid());
		assertEquals(0,bookingAmount.compareTo(sourceDocument.getBookingAmount()));
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/thirdPartyVoucher/recover_assets_by_prepayment_third_party_deduction.sql")
	public void test_recover_assets_online_deduct_prepayment(){
		FinancialContract financialContract = financialContractService.getFinancialContractBy("financial_contract_uuid_1");
		Long companyId = 1L;                                                                                                                                                        
		String assetSetUuid = "asset_uuid_1";
		String customerUuid = "customerUuid1";                                                                  
		BigDecimal bookingAmount = new BigDecimal("1000");
		
		cashFlowAutoIssueHandler.recover_assets_online_deduct_by_interface_each_financial_contract(financialContract);
		
		DeductApplication deductApplication = deductApplicationService.load(DeductApplication.class, 1L);
		assertEquals(RecordStatus.WRITE_OFF,deductApplication.getRecordStatus());
		
		//校验资产
		AssetSet asset_1 = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
		assertEquals(AssetClearStatus.CLEAR,asset_1.getAssetStatus());

		//校验结算单
		List<Order> orderList = orderService.getTodayOrderListBy(asset_1);
		assertEquals(1,orderList.size());
		Order order = orderList.get(0);
		assertEquals(OrderClearStatus.CLEAR,order.getClearingStatus());
		assertEquals(0,order.getTotalRent().compareTo(bookingAmount));
		
		//校验ledger_book
		BigDecimal unearned_amount = ledgerBookStatHandler.get_unearned_amount(financialContract.getLedgerBookNo(), assetSetUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(unearned_amount));
		BigDecimal receivable_amount = ledgerBookStatHandler.get_receivable_amount(financialContract.getLedgerBookNo(), assetSetUuid, customerUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(receivable_amount));
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(asset_1);
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		BigDecimal banksaving_amount = ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory);
		assertEquals(0,bookingAmount.compareTo(banksaving_amount));
		
		//校验jv
		List<JournalVoucher> jvList = journalVoucherService.getInforceThirdPartyDeductJournalVoucher(companyId, assetSetUuid);
		assertEquals(1,jvList.size());
		assertEquals(0,jvList.get(0).getBookingAmount().compareTo(bookingAmount));
		
		//校验sourceDocument
		SourceDocument sourceDocument = sourceDocumentService.getOneSourceDocuments(SourceDocumentType.NOTIFY, AccountSide.DEBIT, SourceDocumentStatus.SIGNED, SourceDocument.FIRSTOUTLIER_DEDUCTAPPLICATION, deductApplication.getDeductApplicationUuid());
		assertEquals(0,bookingAmount.compareTo(sourceDocument.getBookingAmount()));
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/thirdPartyVoucher/recover_assets_twice_by_third_party_deduction.sql")
	public void test_recover_assets_online_deduct_part_twice(){
		FinancialContract financialContract = financialContractService.getFinancialContractBy("financial_contract_uuid_1");
		Long companyId = 1L;                                                                                                                                                        
		String assetSetUuid = "asset_uuid_1";
		String customerUuid = "customerUuid1";
		BigDecimal totalAmount = new BigDecimal("1000");
		BigDecimal bookingAmount = new BigDecimal("500");
		BigDecimal rest_amount = totalAmount.subtract(bookingAmount);
		
		cashFlowAutoIssueHandler.recover_assets_online_deduct_by_interface_each_financial_contract(financialContract);
		AssetSet asset_1 = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
		
		DeductApplication deductApplication = deductApplicationService.load(DeductApplication.class, 1L);
		assertEquals(RecordStatus.WRITE_OFF,deductApplication.getRecordStatus());
		BigDecimal receival_amount = ledgerBookStatHandler.get_receivable_amount(financialContract.getLedgerBookNo(), assetSetUuid, customerUuid);
		assertEquals(0,rest_amount.compareTo(receival_amount));
		assertEquals(AssetClearStatus.UNCLEAR,asset_1.getAssetStatus());
		
		List<Order> orderList = orderService.getTodayOrderListBy(asset_1);
		assertEquals(1,orderList.size());
		Order order = orderList.get(0);
		assertEquals(OrderClearStatus.CLEAR,order.getClearingStatus());
		assertEquals(0,order.getTotalRent().compareTo(bookingAmount));
		
		//剩下的部分继续销账:将deductApplication_2释放开
		DeductApplication deductApplication_2 = deductApplicationService.load(DeductApplication.class, 2L);
		deductApplication_2.setRecordStatus(RecordStatus.UNRECORDED);
		deductApplicationService.save(deductApplication_2);
		
		cashFlowAutoIssueHandler.recover_assets_online_deduct_by_interface_each_financial_contract(financialContract);
		
		deductApplication_2 = deductApplicationService.load(DeductApplication.class, 2L);
		assertEquals(RecordStatus.WRITE_OFF,deductApplication.getRecordStatus());
		
		AssetSet asset_1_updated = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
		assertEquals(AssetClearStatus.CLEAR,asset_1_updated.getAssetStatus());
		orderList = orderService.getTodayOrderListBy(asset_1_updated);
		assertEquals(2,orderList.size());
		Order order_1 = orderList.get(0);
		assertEquals(OrderClearStatus.CLEAR,order_1.getClearingStatus());
		assertEquals(0,order_1.getTotalRent().compareTo(bookingAmount));
		Order order_2 = orderList.get(0);
		assertEquals(OrderClearStatus.CLEAR,order_2.getClearingStatus());
		assertEquals(0,order_2.getTotalRent().compareTo(rest_amount));
		
		//校验ledgerBook
		BigDecimal receivalb_amount = ledgerBookStatHandler.get_receivable_amount(financialContract.getLedgerBookNo(), assetSetUuid, customerUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(receivalb_amount));
		AssetCategory assetCategory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(asset_1_updated);
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		BigDecimal banksaving_amount = ledgerItemService.get_balance_of_account(Arrays.asList(ChartOfAccount.FST_BANK_SAVING), book, assetCategory);
		assertEquals(0,totalAmount.compareTo(banksaving_amount));
		
	}
	
}