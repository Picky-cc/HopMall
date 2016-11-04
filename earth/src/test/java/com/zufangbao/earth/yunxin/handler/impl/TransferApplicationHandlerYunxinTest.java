package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.DeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OnAccountStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TransferApplicationHandlerYunxinTest {

	@Autowired
	private TransferApplicationHandler transferApplicationHandler;

	@Autowired
	private TransferApplicationService transferApplicationService;

	@Autowired
	private BatchPayRecordService batchPayRecordService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OrderService orderService;

	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testCreateTodayTransferApplicationByOrder() {
		Order order = orderService.load(Order.class, 2L);
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(1,transferApplicationList.size());
		transferApplicationHandler.createTodayTransferApplicationByOrder(order);
		
		transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
		TransferApplication transferApplication = transferApplicationList.get(1);
		
		assertEquals(0,new BigDecimal("1000.00").compareTo(transferApplication.getAmount()));
		assertEquals(new Long(1L),transferApplication.getContractAccount().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),transferApplication.getCreateTime()));
		assertEquals(ExecutingDeductStatus.CREATE,transferApplication.getExecutingDeductStatus());
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(new Long(2L),transferApplication.getOrder().getId());
		
		//校验订单状态
		Order orderAfter= orderService.load(Order.class, 2L);
		assertEquals(ExecutingSettlingStatus.DOING,orderAfter.getExecutingSettlingStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testCreateTodayTransferApplicationByOrderList() {
		Order order = orderService.load(Order.class, 2L);
		assertFalse(order.getAssetSet().isOverdueDate(new Date()));
		List<Order> orderList = Arrays.asList(order);
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(1,transferApplicationList.size());
		transferApplicationHandler.createTransferApplicationByOrderListAndOverDueStatus(orderList, false);
		
		transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
		TransferApplication transferApplication = transferApplicationList.get(1);
		
		assertEquals(0,new BigDecimal("1000.00").compareTo(transferApplication.getAmount()));
		assertEquals(new Long(1L),transferApplication.getContractAccount().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),transferApplication.getCreateTime()));
		assertEquals(ExecutingDeductStatus.CREATE,transferApplication.getExecutingDeductStatus());
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(new Long(2L),transferApplication.getOrder().getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testCreateTodayTransferApplications() {
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(1,transferApplicationList.size());
		transferApplicationHandler.todayRecycleAssetCreateTransferApplications();
		
		transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
		TransferApplication transferApplication = transferApplicationList.get(1);
		assertFalse(transferApplication.getOrder().getAssetSet().isOverdueDate(new Date()));
		assertEquals(0,new BigDecimal("1000.00").compareTo(transferApplication.getAmount()));
		assertEquals(new Long(1L),transferApplication.getContractAccount().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),transferApplication.getCreateTime()));
		assertEquals(ExecutingDeductStatus.CREATE,transferApplication.getExecutingDeductStatus());
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(new Long(2L),transferApplication.getOrder().getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void createBatchPayRecordBy(){
		TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, 4L);
		try {
			BatchPayRecord createBatchPayRecord = transferApplicationHandler.createBatchPayRecordBy(transferApplication);
			assertNotNull(createBatchPayRecord);
			
			List<BatchPayRecord> batchPayRecordList = batchPayRecordService.list(BatchPayRecord.class, new Filter());
			assertEquals(1,batchPayRecordList.size());
			BatchPayRecord batchPayRecord = batchPayRecordList.get(0);
			assertEquals(createBatchPayRecord.getId(),batchPayRecord.getId());
			assertEquals(0,new BigDecimal("2000").compareTo(batchPayRecord.getAmount()));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testUpdate_transfer_order_asset.sql")
	public void updateTransferAppAndOrderAndAssetForSuc(){
		TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		
		transferApplicationHandler.updateTransferAppAndOrderAndAssetBy(transferApplication, true, "ok.", new Date());
		
		transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		assertEquals(DeductStatus.CLEAR,transferApplication.getDeductStatus());
		assertEquals(ExecutingDeductStatus.SUCCESS,transferApplication.getExecutingDeductStatus());
		assertEquals("ok.",transferApplication.getComment());
		Order order = transferApplication.getOrder();
		assertEquals(OrderClearStatus.CLEAR,order.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.SUCCESS,order.getExecutingSettlingStatus());
		AssetSet assetSet = order.getAssetSet();
		assertEquals(AssetClearStatus.CLEAR,assetSet.getAssetStatus());
		assertEquals(OnAccountStatus.WRITE_OFF,assetSet.getOnAccountStatus());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testUpdate_transfer_order_asset.sql")
	public void updateTransferAppAndOrderAndAssetForFail(){
		TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		
		transferApplicationHandler.updateTransferAppAndOrderAndAssetBy(transferApplication, false, "fail.", new Date());
		
		transferApplication = transferApplicationService.load(TransferApplication.class, 1L);
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(ExecutingDeductStatus.FAIL,transferApplication.getExecutingDeductStatus());
		assertEquals("fail.",transferApplication.getComment());
		Order order = transferApplication.getOrder();
		assertEquals(OrderClearStatus.UNCLEAR,order.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.DOING,order.getExecutingSettlingStatus());
		AssetSet assetSet = order.getAssetSet();
		assertEquals(AssetClearStatus.UNCLEAR,assetSet.getAssetStatus());
		assertEquals(OnAccountStatus.ON_ACCOUNT,assetSet.getOnAccountStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testUpdateOrderExecuteStatus.sql")
	public void testUpdateTodayOrderExecuteStatus(){
		transferApplicationHandler.updateTodayOrderExecuteStatus();
		
		Order order1 = orderService.load(Order.class,2L);
		assertEquals(ExecutingSettlingStatus.DOING,order1.getExecutingSettlingStatus());
		
		Order order2 = orderService.load(Order.class,3L);
		assertEquals(ExecutingSettlingStatus.FAIL,order2.getExecutingSettlingStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testUpdateOrderExecuteStatus.sql")
	public void testUpdateTodayOrderExecuteStatusForSingle(){
		Order order1 = orderService.load(Order.class,2L);
		transferApplicationHandler.updateTodayOrderExecuteStatus(order1);
		order1 = orderService.load(Order.class,2L);
		assertEquals(ExecutingSettlingStatus.DOING,order1.getExecutingSettlingStatus());
		
		Order order2 = orderService.load(Order.class,3L);
		transferApplicationHandler.updateTodayOrderExecuteStatus(order2);
		order2 = orderService.load(Order.class,3L);
		assertEquals(ExecutingSettlingStatus.FAIL,order2.getExecutingSettlingStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testOverDueAssetCreateTodayTransferApplicationByOrderList.sql")
	public void testOverDueAssetCreateTodayTransferApplicationByOrderList() {
		Order order = orderService.load(Order.class, 2L);
		assertTrue(order.getAssetSet().isOverdueDate(new Date()));
		List<Order> orderList = Arrays.asList(order);
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(1,transferApplicationList.size());
		transferApplicationHandler.createTransferApplicationByOrderListAndOverDueStatus(orderList, true);
		
		transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
		TransferApplication transferApplication = transferApplicationList.get(1);
		
		assertEquals(0,new BigDecimal("1000.00").compareTo(transferApplication.getAmount()));
		assertEquals(new Long(1L),transferApplication.getContractAccount().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),transferApplication.getCreateTime()));
		assertEquals(ExecutingDeductStatus.CREATE,transferApplication.getExecutingDeductStatus());
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(new Long(2L),transferApplication.getOrder().getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testOverDueAssetCreateTodayTransferApplicationByOrderList.sql")
	public void testOverDueAssetCreateTransferApplications() {
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(1,transferApplicationList.size());
		transferApplicationHandler.overDueAssetCreateTransferApplications();
		
		transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
		TransferApplication transferApplication = transferApplicationList.get(1);
		assertTrue(transferApplication.getOrder().getAssetSet().isOverdueDate(new Date()));		
		assertEquals(0,new BigDecimal("1000.00").compareTo(transferApplication.getAmount()));
		assertEquals(new Long(1L),transferApplication.getContractAccount().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),transferApplication.getCreateTime()));
		assertEquals(ExecutingDeductStatus.CREATE,transferApplication.getExecutingDeductStatus());
		assertEquals(DeductStatus.UNCLEAR,transferApplication.getDeductStatus());
		assertEquals(new Long(2L),transferApplication.getOrder().getId());
	}
}
