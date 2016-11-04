package com.zufangbao.wellsfargo.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SettlementModes;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class JournalVoucherHandlerTest {

		@Autowired
		private GenericDaoSupport genericDaoSupport;
		
		@Autowired
		private JournalVoucherService journalVoucherService;
		
		@Autowired
		private BusinessVoucherService businessVoucherService;
		
		@Autowired
		private JournalVoucherHandler journalVoucherHandler;
		
		@Autowired
		private SourceDocumentService sourceDocumentService;
		
		@Autowired
		private OfflineBillService offlineBillService;
		
		@Autowired
		private OrderService orderService;
		
		@Autowired
		private AppService appService;
		
		@Autowired
		@Qualifier("mockBankAccountCache")
		private BankAccountCache bankAccountCache;
		
		private Page page = new Page(0,10);
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateJVAndUpdateSourceDocument(){
			List<JournalVoucher> journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(0,journalVoucherList.size());
			SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			try {
				journalVoucherHandler.createJVFromSourceDocument(sourceDocument,bankAccountCache, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			//校验journalVoucher
			journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(1,journalVoucherList.size());
			
			JournalVoucher journalVoucher = journalVoucherList.get(0);
			assertEquals(AccountSide.DEBIT,journalVoucher.getAccountSide());
			assertEquals("repayment_bill_id_2",journalVoucher.getBillingPlanUuid());
			assertEquals("10.00",journalVoucher.getBookingAmount().toString());
			assertFalse(StringUtils.isEmpty(journalVoucher.getBusinessVoucherUuid()));
			assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),journalVoucher.getCreatedDate()));
			assertEquals(new Long(1L),journalVoucher.getCompanyId());
			assertEquals(SettlementModes.REMITTANCE,journalVoucher.getSettlementModes());
			assertEquals("serial_no_2",journalVoucher.getSourceDocumentCashFlowSerialNo());
			assertEquals("pay_ac_no_1",journalVoucher.getSourceDocumentCounterPartyAccount());
			assertEquals("payer_name_1",journalVoucher.getSourceDocumentCounterPartyName());
			//journalVoucher.getSourceDocumentCounterPartyUuid()
			assertEquals("batch_pay_record_uuid_2",journalVoucher.getSourceDocumentIdentity());
			assertEquals("source_document_uuid_2",journalVoucher.getSourceDocumentUuid());
			assertEquals(JournalVoucherStatus.VOUCHER_ISSUED,journalVoucher.getStatus());
			assertEquals("20150402094007",DateUtils.format(journalVoucher.getTradeTime(),DateUtils.DATE_FORMAT_FULL_DATE_TIME));
			
			//校验sourceDocument
			sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			assertEquals(SourceDocumentStatus.SIGNED,sourceDocument.getSourceDocumentStatus());
			assertEquals("10.00",sourceDocument.getBookingAmount().toString());
			
			//校验businessVoucher
			List<BusinessVoucher> businessVoucherList = businessVoucherService.list(BusinessVoucher.class, new Filter());
			assertEquals(1,businessVoucherList.size());
			BusinessVoucher busienssVoucher = businessVoucherList.get(0);
			assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED,busienssVoucher.getBusinessVoucherStatus());
			assertEquals("10.00",busienssVoucher.getSettlementAmount().toString());
		}
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateJVAndUpdateSourceDocumentForExistsJV(){
			//sourceDocumen已有JV，则抛异常
			
			List<JournalVoucher> journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(0,journalVoucherList.size());
			JournalVoucher journalVoucher = new JournalVoucher();
			journalVoucher.setCompanyId(1L);
			journalVoucher.setAccountSide(AccountSide.DEBIT);
			journalVoucher.setSourceDocumentUuid("source_document_uuid_2");
			journalVoucherService.save(journalVoucher);
			
			SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			try {
				journalVoucherHandler.createJVFromSourceDocument(sourceDocument,bankAccountCache, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(true);
			}
			//JV不生成，只有之前的jV。BV都不生成
			journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(1,journalVoucherList.size());
			List<BusinessVoucher> businessVoucherList = businessVoucherService.list(BusinessVoucher.class, new Filter());
			assertEquals(0,businessVoucherList.size());
			
		}
		
		/*@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateJVFromSourceDocuments(){
			List<SourceDocument> sourceDocumentList = new ArrayList<SourceDocument>();
			SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			sourceDocumentList.add(sourceDocument);
			journalVoucherHandler.createJVFromSourceDocuments(sourceDocumentList,bankAccountCache);
			
			List<JournalVoucher> journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(1,journalVoucherList.size());
			List<BusinessVoucher> businessVoucherList = businessVoucherService.list(BusinessVoucher.class, new Filter());
			assertEquals(1,businessVoucherList.size());
		}*/
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateJVFromUnSignedSourceDocuments(){
			Long companyId = 1L;
			journalVoucherHandler.createJVFromUnSignedOnlineSourceDocuments(companyId,bankAccountCache);
			
			List<JournalVoucher> journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(1,journalVoucherList.size());
			List<BusinessVoucher> businessVoucherList = businessVoucherService.list(BusinessVoucher.class, new Filter());
			assertEquals(1,businessVoucherList.size());
			SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			assertEquals(SourceDocumentStatus.SIGNED,sourceDocument.getSourceDocumentStatus());
		}
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateJVFromUnSignedSourceDocumentsForAllCompany(){
			journalVoucherHandler.createJVFromUnSignedOnlineSourceDocuments(null);
			
			List<JournalVoucher> journalVoucherList = journalVoucherService.list(JournalVoucher.class, new Filter());
			assertEquals(1,journalVoucherList.size());
			List<BusinessVoucher> businessVoucherList = businessVoucherService.list(BusinessVoucher.class, new Filter());
			assertEquals(1,businessVoucherList.size());
			SourceDocument sourceDocument = sourceDocumentService.load(SourceDocument.class, 2L);
			assertEquals(SourceDocumentStatus.SIGNED,sourceDocument.getSourceDocumentStatus());
		}
		
		@Test
		@Sql("classpath:test/yunxin/offlineBill/createVouchersForOfflineBill.sql")
		public void testCreate_JV_and_BV_from_sourceDocument(){
			OfflineBill offlineBill = offlineBillService.load(OfflineBill.class,1L);
			List<Order> orderList = new ArrayList<Order>();
			Order order1 = orderService.load(Order.class, 1L);
			Order order2 = orderService.load(Order.class, 2L);
			orderList.add(order1);
			orderList.add(order2);
			
			SourceDocument sourceDocumentInDb = SourceDocumentImporter.createSourceDocumentFrom(1L, offlineBill);
			
			String accountNo = "payer_account_no";
			String accountName = "payer_account_name";
			try {
				journalVoucherHandler.create_JV_and_BV_from_sourceDocument(sourceDocumentInDb, orderList, JournalVoucherType.OFFLINE_BILL_ISSUE);
			} catch (Exception e) {
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