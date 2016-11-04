package com.zufangbao.wellsfargo.yunxin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

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
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class JournalVoucherServiceYunxinTest {

		@Autowired
		private GenericDaoSupport genericDaoSupport;
		
		@Autowired
		private JournalVoucherService journalVoucherService;
		
		@Autowired
		private SourceDocumentService sourceDocumentService;
		
		@Autowired
		private AppService appService;
		
		private Page page = new Page(0,10);
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testExistsJVWithSourceDocumentUuid(){
			JournalVoucher journalVoucher = new JournalVoucher();
			journalVoucher.setSourceDocumentUuid("source_document_uuid_2");
			journalVoucher.setCompanyId(1L);
			journalVoucher.setAccountSide(AccountSide.DEBIT);
			journalVoucherService.save(journalVoucher);
			
			boolean result = journalVoucherService.existsJVWithSourceDocumentUuid("source_document_uuid_2", 1L, AccountSide.DEBIT, null);
			assertTrue(result);
			
			result = journalVoucherService.existsJVWithSourceDocumentUuid("source_document_uuid_1", 1L, AccountSide.CREDIT, null);
			assertFalse(result);
			
			result = journalVoucherService.existsJVWithSourceDocumentUuid("aa", 1L, AccountSide.DEBIT, null);
			assertFalse(result);
		}
		
		@Test
		@Sql("classpath:test/yunxin/voucher/createJVBySourceDoc.sql")
		public void testCreateIssuedJournalVoucher(){
			
			SourceDocument sourceDocumentInDb = sourceDocumentService.load(SourceDocument.class, 1L);
			
			String accountNo = "pay_ac_no_1";
			String accountName = "payer_name_1";
			String orderUuids = "uuid1";
			BigDecimal amount = new BigDecimal("1.00");
			try {
				journalVoucherService.createIssuedJournalVoucherBySourceDocument(orderUuids, sourceDocumentInDb, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, amount, "", JournalVoucherType.OFFLINE_BILL_ISSUE);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			
			//校验journalVoucher
			List<JournalVoucher> JVList_1 = journalVoucherService.getInForceJournalVoucherListBy(orderUuids,DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			assertEquals(1,JVList_1.size());
			JournalVoucher jv1 = JVList_1.get(0);
			assertEquals(AccountSide.DEBIT,jv1.getAccountSide());
			assertEquals(accountNo,jv1.getSourceDocumentCounterPartyAccount());
			assertEquals(accountName,jv1.getSourceDocumentCounterPartyName());
			assertEquals(amount.toString(),jv1.getBookingAmount().toString());
			assertEquals(new Long(1L),jv1.getCompanyId());
			assertEquals(JournalVoucherStatus.VOUCHER_ISSUED,jv1.getStatus());
			assertEquals(orderUuids,jv1.getBillingPlanUuid());
			
		}
	}