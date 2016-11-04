package com.zufangbao.wellsfargo.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SettlementModes;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class SourceDocumentHandlerTest {
	
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private TransferApplicationService transferApplicationService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OfflineBillService offlineBillService;
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testCreateSourceDocument(){
		TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		Account receiveAccount = accountService.load(Account.class, 1L);
		sourceDocumentHandler.createSourceDocumentForTransferApplication(transferApplication, 1L, receiveAccount);
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L),sourceDocument.getCompanyId());
		assertEquals("pay_ac_no_1",sourceDocument.getOutlierCounterPartyAccount());
		assertEquals("payer_name_1",sourceDocument.getOutlierCounterPartyName());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),sourceDocument.getCreateTime()));
		assertEquals(SourceDocumentType.NOTIFY,sourceDocument.getSourceDocumentType());
		assertEquals(SourceDocumentStatus.CREATE,sourceDocument.getSourceDocumentStatus());
		assertEquals(0,BigDecimal.ZERO.compareTo(sourceDocument.getBookingAmount()));
		assertEquals("uuid1",sourceDocument.getOutlierDocumentUuid());
		assertEquals("20160323111100",DateUtils.format(sourceDocument.getOutlierTradeTime(),DateUtils.DATE_FORMAT_FULL_DATE_TIME));
		assertEquals("10.00",sourceDocument.getOutlierAmount().toString());
		assertEquals(SettlementModes.REMITTANCE,sourceDocument.getOutlierSettlementModes());
		assertEquals("serial_no_1",sourceDocument.getOutlierSerialGlobalIdentity());
	}
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testCreateSourceDocumentForExists(){
		SourceDocument sourceDocument = new SourceDocument();
		sourceDocument.setOutlierDocumentUuid("uuid1");
		sourceDocument.setSourceAccountSide(AccountSide.DEBIT);
		sourceDocument.setCompanyId(1L);
		sourceDocumentService.save(sourceDocument);
		
		TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		Account receiveAccount = accountService.load(Account.class, 1L);
		sourceDocumentHandler.createSourceDocumentForTransferApplication(transferApplication, 1L, receiveAccount);
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testCreateSourceDocumentByFinancialContract(){
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 1L);
		sourceDocumentHandler.createSouceDocument(DateUtils.asDay("2015-10-13"), financialContract);
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L),sourceDocument.getCompanyId());
		assertEquals("pay_ac_no_1",sourceDocument.getOutlierCounterPartyAccount());
		assertEquals("payer_name_1",sourceDocument.getOutlierCounterPartyName());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),sourceDocument.getCreateTime()));
		assertEquals(SourceDocumentType.NOTIFY,sourceDocument.getSourceDocumentType());
		assertEquals(SourceDocumentStatus.CREATE,sourceDocument.getSourceDocumentStatus());
		assertEquals(0,BigDecimal.ZERO.compareTo(sourceDocument.getBookingAmount()));
		assertEquals("uuid2",sourceDocument.getOutlierDocumentUuid());
		assertEquals("20160111111100",DateUtils.format(sourceDocument.getOutlierTradeTime(),DateUtils.DATE_FORMAT_FULL_DATE_TIME));
		assertEquals("10.00",sourceDocument.getOutlierAmount().toString());
		assertEquals(SettlementModes.REMITTANCE,sourceDocument.getOutlierSettlementModes());
		assertEquals("serial_no_2",sourceDocument.getOutlierSerialGlobalIdentity());
		assertEquals(RepaymentAuditStatus.CREATE,sourceDocument.getAuditStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testCreateSouceDocumentByDate(){
		sourceDocumentHandler.createOnlineSouceDocumentByDate(DateUtils.asDay("2015-10-13"));
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L),sourceDocument.getCompanyId());
		assertEquals("pay_ac_no_1",sourceDocument.getOutlierCounterPartyAccount());
		assertEquals("payer_name_1",sourceDocument.getOutlierCounterPartyName());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),sourceDocument.getCreateTime()));
		assertEquals(SourceDocumentType.NOTIFY,sourceDocument.getSourceDocumentType());
		assertEquals(SourceDocumentStatus.CREATE,sourceDocument.getSourceDocumentStatus());
		assertEquals(0,BigDecimal.ZERO.compareTo(sourceDocument.getBookingAmount()));
		assertEquals("uuid2",sourceDocument.getOutlierDocumentUuid());
		assertEquals("20160111111100",DateUtils.format(sourceDocument.getOutlierTradeTime(),DateUtils.DATE_FORMAT_FULL_DATE_TIME));
		assertEquals("10.00",sourceDocument.getOutlierAmount().toString());
		assertEquals(SettlementModes.REMITTANCE,sourceDocument.getOutlierSettlementModes());
		assertEquals("serial_no_2",sourceDocument.getOutlierSerialGlobalIdentity());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/createVouchersForOfflineBill.sql")
	public void testCreateVouchersForOfflineBill(){
		OfflineBill offlineBill = offlineBillService.load(OfflineBill.class,1L);
		List<Order> orderList = new ArrayList<Order>();
		Order order1 = orderService.load(Order.class, 1L);
		Order order2 = orderService.load(Order.class, 2L);
		orderList.add(order1);
		orderList.add(order2);
		
		String accountNo = "payer_account_no";
		String accountName = "payer_account_name";
		try {
			sourceDocumentHandler.createVouchersForOfflineBill(offlineBill, 1L, orderList);
		} catch (VoucherException e) {
			e.printStackTrace();
			fail();
		}
		
		//校验sourceDocument的生成
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L),sourceDocument.getCompanyId());
		assertEquals("2000.00",sourceDocument.getBookingAmount().toString());
		assertEquals(SourceDocument.FIRSTOUTLIER_OFFLINEBILL,sourceDocument.getFirstOutlierDocType());
		assertEquals(accountName,sourceDocument.getOutlierCounterPartyName());
		assertEquals(accountNo,sourceDocument.getOutlierCounterPartyAccount());
		assertEquals(AccountSide.DEBIT,sourceDocument.getSourceAccountSide());
		assertEquals(SourceDocumentStatus.SIGNED,sourceDocument.getSourceDocumentStatus());
		assertEquals(SourceDocumentType.NOTIFY,sourceDocument.getSourceDocumentType());
		
		//校验journalVoucher
		String repaymentUuid1 = "repayment_bill_id_1";
		String repaymentUuid2 = "repayment_bill_id_2";
		List<JournalVoucher> totalJournalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
		assertEquals(2,totalJournalVoucherList.size());
		List<JournalVoucher> JVList_1 = journalVoucherService.getInForceJournalVoucherListBy(repaymentUuid1,DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(1,JVList_1.size());
		JournalVoucher jv1 = JVList_1.get(0);
		assertEquals(AccountSide.DEBIT,jv1.getAccountSide());
		assertEquals(accountNo,jv1.getSourceDocumentCounterPartyAccount());
		assertEquals(accountName,jv1.getSourceDocumentCounterPartyName());
		assertEquals("1000.00",jv1.getBookingAmount().toString());
		assertEquals(new Long(1L),jv1.getCompanyId());
		assertEquals(JournalVoucherStatus.VOUCHER_ISSUED,jv1.getStatus());
		
		List<JournalVoucher> JVList_2 = journalVoucherService.getInForceJournalVoucherListBy(repaymentUuid2,DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(1,JVList_2.size());
		JournalVoucher jv2 = JVList_2.get(0);
		assertEquals(AccountSide.DEBIT,jv2.getAccountSide());
		assertEquals(accountNo,jv2.getSourceDocumentCounterPartyAccount());
		assertEquals(accountName,jv2.getSourceDocumentCounterPartyName());
		assertEquals("1000.00",jv2.getBookingAmount().toString());
		assertEquals(new Long(1L),jv2.getCompanyId());
		assertEquals(JournalVoucherStatus.VOUCHER_ISSUED,jv2.getStatus());
		
		//校验businessVoucher
		List<BusinessVoucher> bvList = businessVoucherService.list(BusinessVoucher.class, new Filter());
		assertEquals(2,bvList.size());
		BusinessVoucher businessVoucher_1 = businessVoucherService.getBusinessVoucherByBillingPlanUidAndType(repaymentUuid1, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(repaymentUuid1,businessVoucher_1.getBillingPlanUuid());
		assertEquals("1000.00",businessVoucher_1.getSettlementAmount().toString());
		assertEquals(jv1.getBusinessVoucherUuid(),businessVoucher_1.getBusinessVoucherUuid());
		assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED,businessVoucher_1.getBusinessVoucherStatus());
		
		BusinessVoucher businessVoucher_2 = businessVoucherService.getBusinessVoucherByBillingPlanUidAndType(repaymentUuid2, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		assertEquals(repaymentUuid2,businessVoucher_2.getBillingPlanUuid());
		assertEquals("1000.00",businessVoucher_2.getSettlementAmount().toString());
		assertEquals(jv2.getBusinessVoucherUuid(),businessVoucher_2.getBusinessVoucherUuid());
		assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED,businessVoucher_2.getBusinessVoucherStatus());
	}
}
