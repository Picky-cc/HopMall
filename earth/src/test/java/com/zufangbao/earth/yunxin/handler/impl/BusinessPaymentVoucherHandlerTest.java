package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherSession;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherCommandModel;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherDetail;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.account.VirtualAccountFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.VirtualAccountFlowService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class BusinessPaymentVoucherHandlerTest {

	@Autowired 
	private ContractHandler contractHandler;
	@Autowired 
	private BusinessPaymentVoucherHandler businessPaymentVoucherHandler;
	@Autowired
	private ContractService contractService;
	@Autowired
	private VirtualAccountService virtualAccountService;
	@Autowired
	private JournalVoucherService journalVoucherService;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private CashFlowService cashFlowService;
	@Autowired
	private VirtualAccountFlowService virtualAccountFlowService;
	@Autowired
	private BusinessPaymentVoucherSession businessPaymentVoucherSession;
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/outer_transfer_assets_by_business_pay_voucher.sql")
	public void test_outer_transfer_loan_assets_full(){
		SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.load(SourceDocumentDetail.class, 1L);
		String ledgerBookNo = "yunxin_ledger_book";
		String assetSetUuid = "asset_uuid_1";
		String borrower_customerUuid = "customerUuid1";
		String company_customerUuid = "company_customer_uuid_1";
		BigDecimal bookingAmount = new BigDecimal("1000");
		try {
			
			businessPaymentVoucherHandler.compensatory_recover_loan_asset_detail(new Long(1), null, null, null);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}

		//校验明细状态
		assertEquals(SourceDocumentDetailStatus.SUCCESS,sourceDocumentDetail.getStatus());
		//校验资产状态
		AssetSet assetSet = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
		assertEquals(AssetClearStatus.CLEAR,assetSet.getAssetStatus());
		
		VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByCustomerUuid(company_customerUuid);
		//校验余额
		assertEquals(0,new BigDecimal("10005").subtract(bookingAmount).compareTo(virtualAccount.getTotalBalance()));
		
		//校验ledger book明细余额
		BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(ledgerBookNo, company_customerUuid);
		assertEquals(0,balance.compareTo(virtualAccount.getTotalBalance()));
		
		//校验ledger_book的应收资产
		BigDecimal receivable_amount = ledgerBookStatHandler.get_receivable_amount(ledgerBookNo, assetSetUuid, borrower_customerUuid);
		assertEquals(0,BigDecimal.ZERO.compareTo(receivable_amount));
		
		//校验jv
		List<JournalVoucher> jvList = journalVoucherService.list(JournalVoucher.class, new Filter());
		assertEquals(1,jvList.size());
		JournalVoucher jv = jvList.get(0);
		assertEquals(JournalVoucherType.TRANSFER_BILL_BY_VOUCHER,jv.getJournalVoucherType());
		assertEquals(JournalVoucherStatus.VOUCHER_ISSUED,jv.getStatus());
		assertEquals(0,sourceDocumentDetail.getAmount().compareTo(jv.getBookingAmount()));
		
		//校验流水
		List<VirtualAccountFlow> virtualAccountFlowList = virtualAccountFlowService.list(VirtualAccountFlow.class, new Filter());
		assertEquals(1,virtualAccountFlowList.size());
		VirtualAccountFlow virtualAccountFlow = virtualAccountFlowList.get(0);
		assertEquals(AccountSide.CREDIT,virtualAccountFlow.getAccountSide());
		assertEquals(0,bookingAmount.compareTo(virtualAccountFlow.getTransactionAmount()));
		assertEquals(0,virtualAccount.getTotalBalance().compareTo(virtualAccountFlow.getBalance()));
		assertEquals(jv.getJournalVoucherUuid(),virtualAccountFlow.getBusinessDocumentNo());
		
	}
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/test_business_payment_voucher.sql")
	public void  test_businessPaymentVoucherNoDoc(){
		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		
		BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
		detail.setAmount(new BigDecimal("500"));
		detail.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail.setRepaymentPlanNo("plan_no1");
		detail.setPayer(0);
		
		BusinessPaymentVoucherDetail detail1 = new BusinessPaymentVoucherDetail();
		detail1.setAmount(new BigDecimal("600"));
		detail1.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail1.setRepaymentPlanNo("plan_no1");
		detail1.setPayer(0);
		details.add(detail1);
		String jsonString = JsonUtils.toJsonString(details);
		
		BusinessPaymentVoucherCommandModel model = new BusinessPaymentVoucherCommandModel();
		model.setFn("10002");
		model.setRequestNo("1323456");
		model.setTransactionType(0);
		model.setVoucherType(0);
		model.setVoucherAmount(new BigDecimal("500"));
		model.setFinancialContractNo("YX_AMT_001");
		model.setReceivableAccountNo("486411356");
		model.setPaymentAccountNo("146474156");
		model.setPaymentName("张三");
		model.setPaymentBank("招商银行");
		model.setBankTransactionNo("serial_no_1");
		model.setDetail(jsonString);
		model.setCheckFailedMsg("校验失败信息");
		
		try
		{
		List<CashFlow> cashFlowList = businessPaymentVoucherHandler.businessPaymentVoucher(model,"127.0.0.1");
		fail();
		}
		catch(ApiException e )
		{
			assertTrue(true);	
		}
		
	}
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/test_business_payment_voucher.sql")
	public void  test_businessPaymentVoucherHaveDocumentNoDetail(){
		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		
		BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
		detail.setAmount(new BigDecimal("0.10"));
		detail.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail.setRepaymentPlanNo("plan_no1");
		detail.setPayer(0);
		
		BusinessPaymentVoucherDetail detail1 = new BusinessPaymentVoucherDetail();
		detail1.setAmount(new BigDecimal("0.10"));
		detail1.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail1.setRepaymentPlanNo("plan_no1");
		detail1.setPayer(0);
		details.add(detail1);
		String jsonString = JsonUtils.toJsonString(details);
		
		BusinessPaymentVoucherCommandModel model = new BusinessPaymentVoucherCommandModel();
		model.setFn("10002");
		model.setRequestNo("1323456");
		model.setTransactionType(0);
		model.setVoucherType(0);
		model.setVoucherAmount(new BigDecimal("0.10"));
		model.setFinancialContractNo("YX_AMT_001");
		model.setReceivableAccountNo("156a15");
		model.setPaymentAccountNo("serial_no");
		model.setPaymentName("张建明");
		model.setPaymentBank("招商银行");
		model.setBankTransactionNo("112049");
		model.setDetail(jsonString);
		model.setCheckFailedMsg("校验失败信息");
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(1L, "batch_pay_record_uuid_1");
		
		List<SourceDocumentDetail> details1 = sourceDocumentDetailService.getDetailsBySourceDocumentUuid(sourceDocument.getSourceDocumentUuid(), sourceDocument.getOutlierSerialGlobalIdentity());
		assertEquals(0, details1.size());
		List<CashFlow> cashFlowList = businessPaymentVoucherHandler.businessPaymentVoucher(model,"127.0.0.1");
		SourceDocument sourceDocument2 = sourceDocumentService.getSourceDocumentBy(1L, cashFlowList.get(0).getCashFlowUuid());
		
		List<SourceDocumentDetail> details2 = sourceDocumentDetailService.getDetailsBySourceDocumentUuid(sourceDocument2.getSourceDocumentUuid(), sourceDocument2.getOutlierSerialGlobalIdentity());
		assertEquals(1, cashFlowList.size());
		assertEquals(1, details2.size());
		
	
	}
	
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/test_business_payment_voucher.sql")
	public void  test_businessPaymentVoucherHave2CashFlow(){
		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		
		BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
		detail.setAmount(new BigDecimal("0.10"));
		detail.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail.setRepaymentPlanNo("plan_no1");
		detail.setPayer(0);
		
		BusinessPaymentVoucherDetail detail1 = new BusinessPaymentVoucherDetail();
		detail1.setAmount(new BigDecimal("0.10"));
		detail1.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail1.setRepaymentPlanNo("plan_no1");
		detail1.setPayer(0);
		details.add(detail1);
		String jsonString = JsonUtils.toJsonString(details);
		
		BusinessPaymentVoucherCommandModel model = new BusinessPaymentVoucherCommandModel();
		model.setFn("10002");
		model.setRequestNo("1323456");
		model.setTransactionType(0);
		model.setVoucherType(0);
		model.setVoucherAmount(new BigDecimal("0.10"));
		model.setFinancialContractNo("YX_AMT_001");
		model.setReceivableAccountNo("156a15");
		model.setPaymentAccountNo("serial_no_3");
		model.setPaymentName("张建明");
		model.setPaymentBank("招商银行");
		model.setBankTransactionNo("1122049");
		model.setDetail(jsonString);
		model.setCheckFailedMsg("校验失败信息");
		List<CashFlow> cashFlowList1 = cashFlowService.getCashFlowListBy("serial_no_3", "张建明", new BigDecimal("0.10"));
		assertEquals(2, cashFlowList1.size());
		try {
			List<CashFlow> cashFlowList = businessPaymentVoucherHandler.businessPaymentVoucher(model,"127.0.0.1");
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		
		
		
	
	}
	
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/test_business_payment_voucher.sql")
	public void  test_businessPaymentVoucherHaveDetail(){
		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		
		BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
		detail.setAmount(new BigDecimal("0.10"));
		detail.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail.setRepaymentPlanNo("plan_no1");
		detail.setPayer(0);
		
		BusinessPaymentVoucherDetail detail1 = new BusinessPaymentVoucherDetail();
		detail1.setAmount(new BigDecimal("0.10"));
		detail1.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail1.setRepaymentPlanNo("plan_no1");
		detail1.setPayer(0);
		details.add(detail1);
		String jsonString = JsonUtils.toJsonString(details);
		
		BusinessPaymentVoucherCommandModel model = new BusinessPaymentVoucherCommandModel();
		model.setFn("10002");
		model.setRequestNo("1323456");
		model.setTransactionType(0);
		model.setVoucherType(0);
		model.setVoucherAmount(new BigDecimal("0.10"));
		model.setFinancialContractNo("YX_AMT_001");
		model.setReceivableAccountNo("156a15");
		model.setPaymentAccountNo("serial_no_2");
		model.setPaymentName("张建明");
		model.setPaymentBank("招商银行");
		model.setBankTransactionNo("1122049");
		model.setDetail(jsonString);
		model.setCheckFailedMsg("校验失败信息");
		try {
			List<CashFlow> cashFlowList = businessPaymentVoucherHandler.businessPaymentVoucher(model,"127.0.0.1");
			fail();
		} catch (ApiException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/businessPaymentVoucher/scan_sd_and_transfer_by_business_pay_voucher.sql")
	public void test_check_and_transfer_asstes(){
		try {
			businessPaymentVoucherSession.handler_compensatory_loan_asset_recover();
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class,1L);
		assertEquals(SourceDocumentExcuteResult.SUCCESS,sourceDocument.getExcuteResult());
		assertEquals(SourceDocumentExcuteStatus.DONE,sourceDocument.getExcuteStatus());
		List<SourceDocumentDetail> details = sourceDocumentDetailService.getDetailsBySourceDocumentUuid(sourceDocument.getSourceDocumentUuid(), sourceDocument.getOutlierSerialGlobalIdentity());
		assertEquals(2,details.size());
		
		for (SourceDocumentDetail sourceDocumentDetail : details) {
			assertEquals(SourceDocumentDetailStatus.SUCCESS,sourceDocumentDetail.getStatus());
		}
		List<JournalVoucher> jvList = journalVoucherService.list(JournalVoucher.class, new Filter());
		assertEquals(2,jvList.size());
		
		
	}
	
}
