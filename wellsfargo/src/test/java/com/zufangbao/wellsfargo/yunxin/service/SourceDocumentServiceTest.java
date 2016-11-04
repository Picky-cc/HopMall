package com.zufangbao.wellsfargo.yunxin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class SourceDocumentServiceTest {
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private OfflineBillService offlineBillService;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testExistsBatchPayRecordUuid(){
		SourceDocument sourceDocument = new SourceDocument();
		sourceDocument.setOutlierDocumentUuid("uuid1");
		sourceDocument.setSourceAccountSide(AccountSide.DEBIT);
		sourceDocument.setCompanyId(1L);
		sourceDocumentService.save(sourceDocument);
		
		boolean result = sourceDocumentService.existsBatchPayRecordUuid("uuid1", AccountSide.DEBIT, 1L);
		assertTrue(result);
		
		result = sourceDocumentService.existsBatchPayRecordUuid("uuid123", AccountSide.DEBIT, 1L);
		assertFalse(result);
	}
	
	@Test
	@Sql("classpath:test/yunxin/voucher/createSourceDoc.sql")
	public void testSignSourceDocument(){
		SourceDocument sourceDocument = new SourceDocument();
		sourceDocument.setOutlierAmount(new BigDecimal("5.00"));
		sourceDocument.setOutlierDocumentUuid("uuid1");
		Long id = (Long)sourceDocumentService.save(sourceDocument);
		sourceDocumentService.signSourceDocument(sourceDocument,new BigDecimal("5.00"));
		
		SourceDocument sourceDocInDb = sourceDocumentService.load(SourceDocument.class, id);
		assertEquals(SourceDocumentStatus.SIGNED,sourceDocInDb.getSourceDocumentStatus());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),sourceDocInDb.getIssuedTime()));
		assertEquals("5.00",sourceDocInDb.getBookingAmount().toString());
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/createVouchersForOfflineBill.sql")
	public void testCreateVouchersForOfflineBill(){
		OfflineBill offlineBill = offlineBillService.load(OfflineBill.class,1L);
		
		Long companyId = 1L;
		
		String accountNo = "payer_account_no";
		String accountName = "payer_account_name";
		sourceDocumentService.createSourceDocumentBy(companyId, offlineBill);
		
		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(SourceDocument.class, new Filter());
		assertEquals(1,sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L),sourceDocument.getCompanyId());
		assertEquals(0,BigDecimal.ZERO.compareTo(sourceDocument.getBookingAmount()));
		assertEquals(SourceDocument.FIRSTOUTLIER_OFFLINEBILL,sourceDocument.getFirstOutlierDocType());
		assertEquals(accountName,sourceDocument.getOutlierCounterPartyName());
		assertEquals(accountNo,sourceDocument.getOutlierCounterPartyAccount());
		assertEquals(AccountSide.DEBIT,sourceDocument.getSourceAccountSide());
		assertEquals(SourceDocumentStatus.CREATE,sourceDocument.getSourceDocumentStatus());
		assertEquals(SourceDocumentType.NOTIFY,sourceDocument.getSourceDocumentType());
		assertEquals(RepaymentAuditStatus.CREATE,sourceDocument.getAuditStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testSourceDocumentl.sql")
	public void testgetSourceDocumentListBy(){
		List<String> sourceDocumentUuids = new ArrayList<String>();
		List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentListBy(sourceDocumentUuids, SourceDocumentStatus.CREATE);
		assertTrue(CollectionUtils.isEmpty(sourceDocumentList));
		
		sourceDocumentUuids.add("source_document_uuid_1");
		sourceDocumentList = sourceDocumentService.getSourceDocumentListBy(sourceDocumentUuids, SourceDocumentStatus.CREATE);
		assertEquals(1,sourceDocumentList.size());
		assertEquals("source_document_uuid_1",sourceDocumentList.get(0).getSourceDocumentUuid());
	}
	
}
