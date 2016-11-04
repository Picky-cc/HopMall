package com.zufangbao.sun.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.DeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TransferApplicationServiceTest {

	@Autowired
	private TransferApplicationService transferApplicationService;

	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testExistsUnFailTransferApplicationToday(){
		Order order = orderService.load(Order.class, 2L);
		boolean result = transferApplicationService.existUnFailTransferApplication(order);
		assertTrue(result);
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
	@Sql("classpath:test/yunxin/transferApplication/testCreateTransferApplication.sql")
	public void testGetDeductSucTransferApplicationListBy(){
		Order order = orderService.load(Order.class, 2L);
		
		List<TransferApplication> transferApplicationList = transferApplicationService.getDeductSucTransferApplicationListBy(order);
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
		TransferApplication transferApplication2 = transferApplicationService.load(TransferApplication.class, 2L);
		transferApplication1.setExecutingDeductStatus(ExecutingDeductStatus.DOING);
		transferApplication1.setDeductStatus(DeductStatus.UNCLEAR);
		
		transferApplication2.setExecutingDeductStatus(ExecutingDeductStatus.DOING);
		transferApplication2.setDeductStatus(DeductStatus.UNCLEAR);
		
		transferApplicationService.save(transferApplication1);
		transferApplicationService.save(transferApplication2);
		List<TransferApplication> transferApplicationList = transferApplicationService.getInProcessTransferApplicationList();
		assertEquals(2,transferApplicationList.size());
		assertEquals(new Long(1),transferApplicationList.get(0).getId());
		assertEquals(new Long(2),transferApplicationList.get(1).getId());
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
	@Sql("classpath:test/yunxin/transferApplication/testQueryTheDateTransferApplicationOrderByStatus.sql")
	public void testQueryTheDateTransferApplicationOrderByStatus(){
		String queryDate = "2016-06-03";
		Long financialContractId = 1l;
		List<TransferApplication> orderByList = transferApplicationService.queryTheDateTransferApplicationOrderByStatus(financialContractId, queryDate);
		assertEquals(5, orderByList.size());
		TransferApplication firstOrder = orderByList.get(0);
		assertEquals(ExecutingDeductStatus.SUCCESS, firstOrder.getExecutingDeductStatus());
		assertEquals(ExecutingDeductStatus.FAIL, orderByList.get(1).getExecutingDeductStatus());
		assertEquals(ExecutingDeductStatus.DOING, orderByList.get(2).getExecutingDeductStatus());
		assertEquals(ExecutingDeductStatus.CREATE, orderByList.get(3).getExecutingDeductStatus());
		assertEquals(ExecutingDeductStatus.TIME_OUT, orderByList.get(4).getExecutingDeductStatus());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTheDateTransferApplicationforOrderByLastModifyTime.sql")
	public void testQueryTheDateTransferApplicationOrderByStatusGetTheNewestOrder(){
		String queryDate = "2016-06-03";
		Long financialContractId = 1l;
		List<TransferApplication> orderByList = transferApplicationService.queryTheDateTransferApplicationOrderByStatus(financialContractId, queryDate);
		assertEquals(1, orderByList.size());
		TransferApplication onLineBill = orderByList.get(0);
		System.out.println(onLineBill.getTransferApplicationNo());
		assertEquals(ExecutingDeductStatus.SUCCESS, onLineBill.getExecutingDeductStatus());
		assertEquals(DeductStatus.CLEAR, onLineBill.getDeductStatus());
	}
	@Test
	@Sql("classpath:test/yunxin/transferApplication/testQueryTheDateTransferApplicationforOrderByLastModifyTime.sql")
	public void testQueryTheDateTransferApplicationforOrderByLastModifyTime(){
		String queryDate = "2016-06-03";
		Long financialContractId = 1l;
		List<TransferApplication> orderByLastModifyTimeList = transferApplicationService.queryTheDateTransferApplicationOrderByStatus(financialContractId, queryDate);
		assertEquals(2, orderByLastModifyTimeList.size());
		TransferApplication onLineBill = orderByLastModifyTimeList.get(0);
		assertEquals("2016-06-03 11:39:11", DateUtils.format(onLineBill.getLastModifiedTime(), "yyyy-MM-dd HH:mm:ss"));
		TransferApplication onLineBill2 = orderByLastModifyTimeList.get(1);
		assertEquals("2016-06-03 11:39:30",  DateUtils.format(onLineBill2.getLastModifiedTime(), "yyyy-MM-dd HH:mm:ss"));
	}
}
