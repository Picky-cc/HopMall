package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.DeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TransferApplicationServiceYunxinTest {

	@Autowired
	private TransferApplicationService transferApplicationService;

	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RepaymentPlanService RepaymentPlanService;
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testExistsUnFailTransferApplicationToday(){
		Order order = orderService.load(Order.class, 2L);
		boolean result = transferApplicationService.existUnFailTransferApplication(order);
		assertFalse(result);
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testExistsUnFailTransferApplicationTodayCase2(){
		Order order = orderService.load(Order.class, 2L);
		boolean result = transferApplicationService.existUnFailTransferApplication(order);
		transferApplicationService.delete(TransferApplication.class, 1L);
		assertFalse(result);
	}

	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testGetTodayTransferApplicationListBy(){
		Order order = orderService.load(Order.class, 2L);
		List<ExecutingDeductStatus> statusList = new ArrayList<ExecutingDeductStatus>();
		statusList.add(ExecutingDeductStatus.FAIL);
		
		List<TransferApplication> transferApplicationList = transferApplicationService.getTransferApplicationListBy(order,statusList);
		assertEquals(1,transferApplicationList.size());
		assertEquals(new Long(1L),transferApplicationList.get(0).getId());
		
		statusList.remove(ExecutingDeductStatus.FAIL);
		statusList.add(ExecutingDeductStatus.SUCCESS);
		transferApplicationList = transferApplicationService.getTransferApplicationListBy(order,statusList);
		assertEquals(0,transferApplicationList.size());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void getToBeBatchedTransferApplicationListByDueDate(){
		List<TransferApplication> transferApplicationList = transferApplicationService.getNeedPayTransferApplicationListByDueDate(new Date());
		assertEquals(2,transferApplicationList.size());
		assertEquals(new Long(3L),transferApplicationList.get(0).getId());
		assertEquals(new Long(4L),transferApplicationList.get(1).getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void testGetSingleTransferApplicationBy(){
		TransferApplication transferApplication = transferApplicationService.getSingleTransferApplicationBy(1L);
		assertEquals(new Long(5),transferApplication.getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void testGetInProcessTransferApplicationList(){
		TransferApplication transferApplication1 = transferApplicationService.load(TransferApplication.class, 1L);
		TransferApplication transferApplication2 = transferApplicationService.load(TransferApplication.class, 3L);
		transferApplication1.setExecutingDeductStatus(ExecutingDeductStatus.DOING);
		transferApplication1.setDeductStatus(DeductStatus.UNCLEAR);
		
		transferApplication2.setExecutingDeductStatus(ExecutingDeductStatus.DOING);
		transferApplication2.setDeductStatus(DeductStatus.UNCLEAR);
		
		transferApplicationService.save(transferApplication1);
		transferApplicationService.save(transferApplication2);
		List<TransferApplication> transferApplicationList = transferApplicationService.getInProcessTransferApplicationList();
		assertEquals(2,transferApplicationList.size());
		assertEquals(new Long(1),transferApplicationList.get(0).getId());
		assertEquals(new Long(3),transferApplicationList.get(1).getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testExistsSucTransferApplication(){
		Order order = orderService.load(Order.class, 2L);
		boolean result = transferApplicationService.isAllTransferApplicationFail(order);
		assertTrue(result);
		
		TransferApplication transferApplication = new TransferApplication();
		transferApplication.setOrder(order);
		transferApplication.setExecutingDeductStatus(ExecutingDeductStatus.SUCCESS);
		transferApplication.setDeductStatus(DeductStatus.CLEAR);
		ContractAccount contractAccount = contractAccountService.load(ContractAccount.class, 1L);
		transferApplication.setContractAccount(contractAccount);
		transferApplicationService.save(transferApplication);
		result = transferApplicationService.isAllTransferApplicationFail(order);
		assertFalse(result);
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testExtractTransferApplicationNoListFrom(){
		Order order = orderService.load(Order.class, 2L);
		List<String> noList = transferApplicationService.extractTransferApplicationNoListFrom(order);
		assertEquals(1,noList.size());
		assertEquals("no_1",noList.get(0));
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void testGetLastCreatedTransferApplicationBy(){
		Order order = orderService.load(Order.class, 2L);
		TransferApplication transferApplication = transferApplicationService.getLastCreatedTransferApplicationBy(order);
		assertEquals(new Long(4L),transferApplication.getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateBatchPaymentRecord.sql")
	public void testGetTransferApplicationListBy(){
		List<TransferApplication> transferApplicationList = transferApplicationService.getTransferApplicationListBy(1L, ExecutingDeductStatus.FAIL, DateUtils.asDay("2015-10-10"));
		assertEquals(1,transferApplicationList.size());
		assertEquals(new Long(1L),transferApplicationList.get(0).getId());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTransferApplication.sql")
	public void testQueryTransferApplicationAfterDay(){
		String queryDate = "1999-04-01";
		List<TransferApplication> result = transferApplicationService.queryTheDateTransferApplication(queryDate, 1L);
		assertTrue(result.isEmpty());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTransferApplication.sql")
	public void testQueryTransferApplicationBeforeDay(){
		String queryDate = "1999-03-30";
		List<TransferApplication> result = transferApplicationService.queryTheDateTransferApplication(queryDate, 1L);
		assertTrue(result.isEmpty());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTransferApplication.sql")
	public void testQueryTransferApplicationForNormal(){
		String queryDate = "1999-03-31";
		List<TransferApplication> result = transferApplicationService.queryTheDateTransferApplication(queryDate, 1L);
		assertEquals(1,result.size());
		TransferApplication transferApplication = result.get(0);
		assertEquals(new BigDecimal("1.00"), transferApplication.getAmount());
		assertEquals("DKHD-001-01-20160308-1910", transferApplication.getTransferApplicationNo());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTransferApplicationForBorder.sql")
	public void testQueryTransferApplicationForBorder(){
		String queryDate = "1999-03-31";
		List<TransferApplication> result = transferApplicationService.queryTheDateTransferApplication(queryDate, 1L);
		assertEquals(1,result.size());
		TransferApplication transferApplication = result.get(0);
		assertEquals(new BigDecimal("1.00"), transferApplication.getAmount());
		assertEquals("DKHD-001-01-20160308-1910", transferApplication.getTransferApplicationNo());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTransferApplicationForBorderLess.sql")
	public void testQueryTransferApplicationForBorderLess(){
		String queryDate = "1999-03-31";
		List<TransferApplication> result = transferApplicationService.queryTheDateTransferApplication(queryDate, 1L);
		assertTrue(result.isEmpty());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testGetTransferApplicationListByOnLinePaymentQuery.sql")
	public void testGetTransferApplicationListByOnLinePaymentQuery(){
		TransferApplicationQueryModel transferApplicationQueryModel = new TransferApplicationQueryModel();
		transferApplicationQueryModel.setAccountName("王二");
		transferApplicationQueryModel.setBillingNo("DKHD-001-");
		
		transferApplicationQueryModel.setStartDateString("1999-04-01");
		transferApplicationQueryModel.setEndDateString("1999-04-01");
		Page page = new Page(0);
//		List<TransferApplication> transferApplications = transferApplicationService.getTransferApplicationListByOnLinePaymentQuery(transferApplicationQueryModel ,page);
//		assertEquals(1, transferApplications.size());
		
		Map<String, Object> data = transferApplicationService.getTransferApplicationListByOnLinePaymentQuery(transferApplicationQueryModel, page);
		
		List<TransferApplication> transferApplications = (List<TransferApplication>) data.get("list");
		assertEquals(1, transferApplications.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testGetRecentDayTransferApplicationList.sql")
	public void testGetRecentDayTransferApplicationList() {
		AssetSet assetSet = RepaymentPlanService.load(AssetSet.class, 1L);
		List<TransferApplication> transferApplicationList = transferApplicationService.getRecentDayTransferApplicationList(assetSet);
		assertEquals(2, transferApplicationList.size());
		TransferApplication transferApplication = transferApplicationList.get(0);
		String dateAsStr = DateUtils.format(transferApplication.getCreateTime());
		assertEquals("2016-03-31", dateAsStr);
		
		assetSet = RepaymentPlanService.load(AssetSet.class, 2L);
		transferApplicationList = transferApplicationService.getRecentDayTransferApplicationList(assetSet);
		assertEquals(0, transferApplicationList.size());
	}

}
