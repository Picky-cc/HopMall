package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.exception.OfflineBillAutidtException;
import com.zufangbao.earth.yunxin.exception.SourceDocumentNotExistException;
import com.zufangbao.earth.yunxin.handler.OfflineBillHandler;
import com.zufangbao.earth.yunxin.web.controller.YunxinOfflinePaymentControllerSpec;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillStatus;
import com.zufangbao.sun.yunxin.entity.OnAccountStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchShowModel;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.exception.OfflineBillCreateException;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillModel;
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
public class OfflineBillHandlerTest {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OfflineBillHandler offlineBillHandler;
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private OfflineBillService offlineBillService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	private Principal principal;
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testCreateOrderByAssetSet() {
		String guaranteeOrderRepaymentUuids = "[\"repayment_bill_id_1\",\"repayment_bill_id_2\"]";
		String bankShowName = "中国银行";
		String accountNo = "accountNo";
		String accountName = "开户人";
		String serialNo = "serial_no_1";
		String comment = "comment_1";
		String offlineBillNo = "offlineBillNo_1";
		OfflineBillCreateModel offlineBillCreateModel = new OfflineBillCreateModel(bankShowName, accountName,
				serialNo, accountNo, guaranteeOrderRepaymentUuids, comment);
		
		try {
			offlineBillHandler.createOfflineBillForGuaranteOrderMatch(offlineBillCreateModel);
		} catch (OfflineBillCreateException e) {
			e.printStackTrace();
			fail();
		} catch (VoucherException e) {
			e.printStackTrace();
			fail();
		}
		//校验 offlineBill
		List<OfflineBill> offlineBills = offlineBillService.list(OfflineBill.class, new Filter());
		assertEquals(1,offlineBills.size());
		OfflineBill offlineBill = offlineBills.get(0);
		
		assertEquals("2000.00",offlineBill.getAmount().toString());
		assertEquals(bankShowName,offlineBill.getBankShowName());
		assertEquals(comment,offlineBill.getComment());
		
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(), offlineBill.getCreateTime()));
		assertFalse(StringUtils.isEmpty(offlineBill.getOfflineBillNo()));
		assertEquals(OfflineBillStatus.PAID,offlineBill.getOfflineBillStatus());
		assertEquals(accountName,offlineBill.getPayerAccountName());
		assertEquals(accountNo,offlineBill.getPayerAccountNo());
		
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
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testCreateOrderByUnvalid() {
		//repayment_bill_id_3的数据
		String guaranteeOrderRepaymentUuids = "[\"repayment_bill_id_3\"]";
		String bankShowName = "中国银行";
		String accountNo = "accountNo";
		String accountName = "开户人";
		String serialNo = "serial_no_1";
		String comment = "comment_1";
		String offlineBillNo = "offlineBillNo_1";
		OfflineBillCreateModel offlineBillCreateModel = new OfflineBillCreateModel(bankShowName, accountName,
				serialNo, accountNo, guaranteeOrderRepaymentUuids, comment);
		
		try {
			offlineBillHandler.createOfflineBillForGuaranteOrderMatch(offlineBillCreateModel);
		} catch (OfflineBillCreateException e) {
			e.printStackTrace();
			assertTrue(true);
		} catch (VoucherException e) {
			e.printStackTrace();
			fail();
		}
		List<OfflineBill> offlineBills = offlineBillService.list(OfflineBill.class, new Filter());
		assertEquals(0,offlineBills.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testSmartMatchOrderListWithWrongUuid.sql")
	public void testSmartMatchOrderListWithWrongUuid() {
		String offlineBillUuid = "not-exist-offline-bill-uuid";
		List<OfflineBill> offlineBillListBefore = offlineBillService.list(OfflineBill.class, new Filter().addEquals("offlineBillUuid", offlineBillUuid));
		assertTrue(offlineBillListBefore.isEmpty());
		List<Order> orderList = offlineBillHandler.smartMatchOrderListBy(offlineBillUuid);
		assertTrue(orderList.isEmpty());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testSmartMatchOrderListWithNoResult.sql")
	public void testSmartMatchOrderListWithNoResult() {
		String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
		List<OfflineBill> offlineBillListBefore = offlineBillService.list(OfflineBill.class, new Filter().addEquals("offlineBillUuid", offlineBillUuid));
		assertFalse(offlineBillListBefore.isEmpty());
		
		List<Order> orderList = offlineBillHandler.smartMatchOrderListBy(offlineBillUuid);
		assertTrue(orderList.isEmpty());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testSmartMatchOrderListWithCustomerBill.sql")
	public void testSmartMatchOrderListWithCustomerBill() {
		String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
		List<OfflineBill> offlineBillListBefore = offlineBillService.list(OfflineBill.class, new Filter().addEquals("offlineBillUuid", offlineBillUuid));
		assertFalse(offlineBillListBefore.isEmpty());
		
		List<Order> orderList = offlineBillHandler.smartMatchOrderListBy(offlineBillUuid);
		assertEquals(1, orderList.size());
		assertEquals(OrderType.NORMAL, orderList.get(0).getOrderType());
		assertEquals("JS272F890AC51F8E71", orderList.get(0).getOrderNo());
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testCreate_offlineBill_and_create_sourcedocument() {
		String bankShowName = "中国银行_1";
		String accountNo = "accountNo_1";
		String accountName = "开户人_1";
		String serialNo = "serial_no_1";
		String comment = "comment_1";
		String amount = new String("10.00");
		String tradeTimeString = "2016-10-10 10:40:40";
		OfflineBillCreateModel offlineBillCreateModel = new OfflineBillCreateModel(bankShowName, accountName,
				serialNo, accountNo, comment, amount,tradeTimeString);
		OfflineBill offlineBillInDB = offlineBillHandler.create_offline_bill_and_create_source_document(offlineBillCreateModel);
		assertNotNull(offlineBillInDB);
		//校验 offlineBill
		List<OfflineBill> offlineBills = offlineBillService.list(OfflineBill.class, new Filter());
		assertEquals(1,offlineBills.size());
		OfflineBill offlineBill = offlineBills.get(0);
		
		assertEquals(amount.toString(),offlineBill.getAmount().toString());
		assertEquals(bankShowName,offlineBill.getBankShowName());
		assertEquals(comment,offlineBill.getComment());
		
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(), offlineBill.getCreateTime()));
		assertFalse(StringUtils.isEmpty(offlineBill.getOfflineBillNo()));
		assertEquals(OfflineBillStatus.PAID,offlineBill.getOfflineBillStatus());
		assertEquals(accountName,offlineBill.getPayerAccountName());
		assertEquals(accountNo,offlineBill.getPayerAccountNo());
		assertEquals(tradeTimeString,DateUtils.format(offlineBill.getTradeTime(),"yyyy-MM-dd HH:mm:ss"));
		
		//校验sourceDocument的生成
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
		assertEquals(tradeTimeString,DateUtils.format(sourceDocument.getOutlierTradeTime(),"yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/createVouchersForOfflineBill.sql")
	public void test(){
		SourceDocument sourceDocument = new SourceDocument();
		sourceDocument.setOutlierDocumentUuid("outlier_document_uuid");
		sourceDocumentService.save(sourceDocument);
		List<OfflineBillModel> models = genericDaoSupport.searchForList("select new com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillModel(bo,sd) from OfflineBill as bo, SourceDocument as sd",OfflineBillModel.class);
		assertEquals(1,models.size());
		
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderWithNoSourceDocument.sql")
	public void testConnectionOrderWithNoSourceDocument() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			Map<String, Object> map = new HashMap<String, Object>();
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, new BigDecimal("1000"), 1L, "127.0.0.1");
			fail();
		} catch (SourceDocumentNotExistException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderWithIllegalAmountOfSourceDocument.sql")
	public void testConnectionOrderWithIllegalAmountOfSourceDocument() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
			assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
			SourceDocument sourceDocument = sourceDocumentList.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, sourceDocument.getOutlierAmount().add(BigDecimal.ONE), 1L, "127.0.0.1");
			fail();
		} catch (SourceDocumentNotExistException e) {
			fail();
		} catch (Exception e) {

		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderWithIllegalAmountOfBusinessVoucher.sql")
	public void testConnectionOrderWithIllegalAmountOfBusinessVoucher() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
			assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
			SourceDocument sourceDocument = sourceDocumentList.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("JS272F890AC51F8E71", sourceDocument.getOutlierAmount() + "");
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, sourceDocument.getOutlierAmount(), 1L, "127.0.0.1");
			fail();
		} catch (SourceDocumentNotExistException e) {
			fail();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderWithAuditLapsedGuarantee.sql")
	public void testConnectionOrderWithLapsedGuranteeOrder() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
			assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
			SourceDocument sourceDocument = sourceDocumentList.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("JS0000002","100");
			map.put("DB272F890AC51F8E71", "10.00");
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, new BigDecimal("110.00"), 1L, "127.0.0.1");
			fail();
		} catch (OfflineBillAutidtException e) {
			assertEquals(YunxinOfflinePaymentControllerSpec.AUDIT_WITH_LAPSED_GUARANTEE_ORDER,e.getMessage());
			//校验结算单JS0000002未关联
			Order order = orderService.load(Order.class, 2L);
			BusinessVoucher bv = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			//assertNull(bv);
			assertEquals(OrderClearStatus.UNCLEAR,order.getClearingStatus());
		} catch (Exception e) {
			
		}
	}

	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderOfPart.sql")
	public void testConnectionOrderOfPart() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			String orderNo = "JS272F890AC51F8E71";
			Order order = orderService.getOrder(orderNo);
			assertEquals(OrderType.NORMAL, order.getOrderType());
			assertEquals(OrderClearStatus.UNCLEAR, order.getClearingStatus());
			assertEquals(ExecutingSettlingStatus.CREATE, order.getExecutingSettlingStatus());
			BigDecimal connectionAmount = order.getTotalRent().subtract(BigDecimal.ONE);
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBillUuid);
			assertFalse(CollectionUtils.isEmpty(sourceDocumentList));
			SourceDocument sourceDocument = sourceDocumentList.get(0);
			assertEquals(-1, connectionAmount.compareTo(sourceDocument.getOutlierAmount()));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(orderNo, connectionAmount + "");
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount, 1L, "127.0.0.1");
			
			BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			assertNotNull(businessVoucher);
			assertEquals(connectionAmount, businessVoucher.getSettlementAmount());
			assertEquals(BusinessVoucherStatus.VOUCHER_ISSUING, businessVoucher.getBusinessVoucherStatus());
			
		} catch (SourceDocumentNotExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionOrderOfAll.sql")
	public void testConnectionOrderOfAll() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			String orderNo = "JS272F890AC51F8E71";
			Order order = orderService.getOrder(orderNo);
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
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount, 1L, "127.0.0.1");
			
			BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			assertNotNull(businessVoucher);
			assertEquals(connectionAmount, businessVoucher.getSettlementAmount());
			assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED, businessVoucher.getBusinessVoucherStatus());
			Order orderAfter = orderService.getOrder(orderNo);
			assertEquals(OrderClearStatus.CLEAR, orderAfter.getClearingStatus());
			assertEquals(ExecutingSettlingStatus.SUCCESS, orderAfter.getExecutingSettlingStatus());
			AssetSet assetSet = orderAfter.getAssetSet();
			assertEquals(AssetClearStatus.CLEAR, assetSet.getAssetStatus());
			assertEquals(OnAccountStatus.WRITE_OFF, assetSet.getOnAccountStatus());
			
		} catch (SourceDocumentNotExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/testOfflineBillDetail.sql")
	public void testGetOrderMatchShowModelBy(){
		String offlineBillUuid = "offline_bill";
		List<OrderMatchShowModel> orderMatchShowModelList = offlineBillHandler.getOrderMatchShowModelBy(offlineBillUuid);
		assertEquals(2,orderMatchShowModelList.size());
		OrderMatchShowModel orderMatchShowModel_1 = orderMatchShowModelList.get(0);
		Order order_1 = orderMatchShowModel_1.getOrder();
		assertEquals("repayment_bill_id_1",order_1.getRepaymentBillId());
		assertEquals(0,new BigDecimal("1000").compareTo(orderMatchShowModel_1.getPaidAmount()));
		assertEquals(0,new BigDecimal("1000").compareTo(orderMatchShowModel_1.getIssuedAmount()));
		
		OrderMatchShowModel orderMatchShowModel_2 = orderMatchShowModelList.get(1);
		Order order_2 = orderMatchShowModel_2.getOrder();
		assertEquals("repayment_bill_id_2",order_2.getRepaymentBillId());
		assertEquals(0,new BigDecimal("1200").compareTo(orderMatchShowModel_2.getPaidAmount()));
		assertEquals(0,new BigDecimal("1200").compareTo(orderMatchShowModel_2.getIssuedAmount()));
	}
	
	/*@Test
	@Sql("classpath:test/yunxin/offlineBill/testConnectionGuaranteeOrderOfAll.sql")
	public void testConnectionGuaranteeOrderOfAll() {
		try {
			String offlineBillUuid = "8a855c1514b7480dba6ffba6450221cf";
			String orderNo = "DB272F890AC51F8E71";
			Order order = orderService.getOrder(orderNo);
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
			offlineBillHandler.connectionOrder(offlineBillUuid, map, connectionAmount);
			
			BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			assertNotNull(businessVoucher);
			assertEquals(connectionAmount, businessVoucher.getSettlementAmount());
			assertEquals(BusinessVoucherStatus.VOUCHER_ISSUED, businessVoucher.getBusinessVoucherStatus());
			Order orderAfter = orderService.getOrder(orderNo);
			assertEquals(OrderClearStatus.CLEAR, orderAfter.getClearingStatus());
			assertEquals(ExecutingSettlingStatus.SUCCESS, orderAfter.getExecutingSettlingStatus());
			
			AssetSet assetSet = orderAfter.getAssetSet();
			assertEquals(GuaranteeStatus.HAS_GUARANTEE, assetSet.getGuaranteeStatus());
			
		} catch (SourceDocumentNotExistException e) {
			fail();
		} catch (IllegalInputAmountException e) {
			fail();
		}
	}*/
}
