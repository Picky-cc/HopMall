package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.cache.handler.impl.CashFlowCacheHandlerImpl;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.MatchParams;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.AfterVoucherIssuedHandler;
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
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class CashFlowHandlerTest {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
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
	private OrderService orderService;
	
	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private AfterVoucherIssuedHandler afterVoucherIssuedHandler;
	
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
	
	@Before
	public void init(){
		CashFlowCacheHandlerImpl.setTIMEOUT(TIMEOUT);
		cashFlowCacheHandler.clearAllCache();
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/matchBillResult/test_search_issued_vouchers.sql")
	public void testSearchIssuedVoucher(){
		//没有匹配到order，但找到对应的2条jv
		
		String cashFlowUuid = "cash_flow_uuid_1";
		String account = "account_other1";
		String account_owner_name = "account_owner_name_other1";
		MatchParams matchParams = new MatchParams(cashFlowUuid,1L,account, account_owner_name,new BigDecimal("10"), new Date(), AccountSide.DEBIT,"");
		List<BillMatchResult> billMatchResults = cashFlowHandler.searchIssuedVoucher(matchParams);
		assertEquals(2,billMatchResults.size());
		for (BillMatchResult billMatchResult : billMatchResults) {
			if("repayment_uuid_1".equals(billMatchResult.getBillingPlanUuid())){
				assertEquals(0,new BigDecimal("1001.00").compareTo(billMatchResult.getReceivableAmount()));
				assertEquals(0,new BigDecimal("1.00").compareTo(billMatchResult.getSettlementAmount()));
			} else {
				assertEquals("repayment_uuid_2",billMatchResult.getBillingPlanUuid());
				assertEquals(0,new BigDecimal("1002.00").compareTo(billMatchResult.getReceivableAmount()));
				assertEquals(0,new BigDecimal("2.00").compareTo(billMatchResult.getSettlementAmount()));
			}
		}
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/audit/matchBillResult/test_no_match.sql")
	public void testSearchBillForCase1(){
		//case1:cash_flow 找不到JV，找不到billing_plan;
		String cashFlowUuid = "cash_flow_uuid_1";
		List<BillMatchResult> results = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, AccountSide.DEBIT);
		assertTrue(CollectionUtils.isEmpty(results));
	}
	

	@Test
	@Sql("classpath:test/yunxin/audit/matchBillResult/test_match_jv_and_no_bill.sql")
	public void testSearchBillForCase2(){
		//没有匹配到order，但找到对应的2条jv
		
		String cashFlowUuid = "cash_flow_uuid_1";
		
		List<BillMatchResult> results = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, AccountSide.DEBIT);
		assertEquals(2,results.size());
		for (BillMatchResult billMatchResult : results) {
			if("repayment_uuid_1".equals(billMatchResult.getBillingPlanUuid())){
				assertEquals(0,new BigDecimal("1001.00").compareTo(billMatchResult.getReceivableAmount()));
				assertEquals(0,new BigDecimal("1.00").compareTo(billMatchResult.getSettlementAmount()));
			}else {
				assertEquals("repayment_uuid_2",billMatchResult.getBillingPlanUuid());
				assertEquals(0,new BigDecimal("1002.00").compareTo(billMatchResult.getReceivableAmount()));
				assertEquals(0,new BigDecimal("2.00").compareTo(billMatchResult.getSettlementAmount()));
			}
		}
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/matchBillResult/test_match_jv_and_bill.sql")
	public void testSearchBillForCase3(){
		//match order and jv
		String cashFlowUuid = "cash_flow_uuid_1";
		List<BillMatchResult> results = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, AccountSide.DEBIT);
		assertEquals(1,results.size());
		
		BillMatchResult result = results.get(0);
		assertEquals("repayment_uuid_3",result.getBillingPlanUuid());
		assertEquals(0,new BigDecimal("1.00").compareTo(result.getBookingAmount()));
		assertEquals(0,new BigDecimal("1003.00").compareTo(result.getReceivableAmount()));
		assertEquals(0,new BigDecimal("1.00").compareTo(result.getSettlementAmount()));
		assertEquals(cashFlowUuid,result.getCashFlowUuid());
		assertEquals("journal_voucher_uuid_1",result.getJournalVoucherUuid());
		assertEquals(0,new BigDecimal("0.00").compareTo(result.getCurrentSpecificAmount()));
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/matchBillResult/test_not_match_jv_and_match_bill.sql")
	public void testSearchBillForCase4(){
		//match order,not match jv
		//其他流水已制证repayment_uuid_3   1元
		String cashFlowUuid = "cash_flow_uuid_1";
		List<BillMatchResult> results = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, AccountSide.DEBIT);
		assertEquals(1,results.size());
		
		BillMatchResult result = results.get(0);
		assertEquals("repayment_uuid_3",result.getBillingPlanUuid());
		assertNull(result.getBookingAmount());
		assertEquals(0,new BigDecimal("1003.00").compareTo(result.getReceivableAmount()));
		assertEquals(0,new BigDecimal("1.00").compareTo(result.getSettlementAmount()));
		assertEquals(cashFlowUuid,result.getCashFlowUuid());
		assertTrue(StringUtils.isEmpty(result.getJournalVoucherUuid()));
		assertEquals(0,new BigDecimal("1.00").compareTo(result.getCurrentSpecificAmount()));
		
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
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/changeVoucher/test_issue_voucher_orderIssued.sql")
	public void testChangeVoucher_case2(){
		//足额对账，更新asset状态
		String cashFlowUuid = "cash_flow_uuid_1";
		String journalVoucherUuid_lapse_1 = "journal_voucher_uuid_1";
		String businessVoucherUuid_1 = "business_voucher_uuid_1";
		String billingPlanUuid = "repayment_uuid_3";
		BigDecimal newAmount = new BigDecimal("1003.00");
		
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
		
		List<JournalVoucher> JVList = journalVoucherService.getVouchersByBillingPlanUidAndIssued(billingPlanUuid,companyId);
		assertEquals(1,JVList.size());

		Order order = orderService.getOrderByRepaymentBillId(billingPlanUuid);
		assertEquals(OrderClearStatus.CLEAR,order.getClearingStatus());
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		assertEquals(AssetClearStatus.CLEAR,assetSet.getAssetStatus());
		
		BusinessVoucher bv = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(billingPlanUuid, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(newAmount,bv.getSettlementAmount());
		assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED,bv.getBusinessVoucherStatus());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/audit/changeVoucher/test_issue_voucher_dup.sql")
	public void testHandlerMultiVoucherForSameBillSubmit(){
		
		// 提交的时候上传了两个相同的billingPlan和JV
		String cashFlowUuid = "cash_flow_uuid_1";
		String journalVoucherUuid1 = "journal_voucher_uuid_1";
		String businessVoucherUuid = "business_voucher_uuid_1";
		BigDecimal amount = new BigDecimal("5.00");
		
		String billingPlanUuid2 = "billing_plan_uuid_2";
		String journalVoucherUuid2 = "journal_voucher_uuid_2";
		String emptyJVUuid = "";
		
		ArrayList<BillMatchResult> billMatchResultList = new ArrayList<BillMatchResult>();
		BillMatchResult sameBillAndJV_1 = new BillMatchResult();
		sameBillAndJV_1.setJournalVoucherUuid(emptyJVUuid);
		sameBillAndJV_1.setBookingAmount(amount);
		sameBillAndJV_1.setBillingPlanUuid(billingPlanUuid2);
		sameBillAndJV_1.setCashFlowUuid(cashFlowUuid);
		
		BillMatchResult sameBillAndJV_2 = new BillMatchResult();
		sameBillAndJV_2.setJournalVoucherUuid(emptyJVUuid);
		sameBillAndJV_2.setBookingAmount(amount);
		sameBillAndJV_2.setBillingPlanUuid(billingPlanUuid2);
		sameBillAndJV_2.setCashFlowUuid(cashFlowUuid);
		
		billMatchResultList.add(sameBillAndJV_1);
		billMatchResultList.add(sameBillAndJV_2);
		
		Set<String> dupicatedBillUuids = cashFlowHandler.checkJVAndBill(billMatchResultList, companyId,cashFlowUuid);
		billMatchResultList.remove(sameBillAndJV_2);
		sameBillAndJV_2.setJournalVoucherUuid(journalVoucherUuid2);
		billMatchResultList.add(sameBillAndJV_2);
		
		dupicatedBillUuids = cashFlowHandler.checkJVAndBill(billMatchResultList, companyId,cashFlowUuid);
		assertEquals(1,dupicatedBillUuids.size());
		assertTrue(dupicatedBillUuids.contains(billingPlanUuid2));
	}
	
}