package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.model.OrderQueryModel;
/**
 * @date 2015-05-21
 * @author Tonny.dong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrderServiceYunxinTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private DataSource dataSource;
	@Resource
	private OrderService orderService;
	
	@Resource
	private RepaymentPlanService repaymentPlanService;
	
	@Resource
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AppService appService;
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testGetOrderListByAssetSet(){
		List<Order> orderList = orderService.getOrderListByAssetSetId(1L);
		assertEquals(2,orderList.size());
		
		Order order1 = orderList.get(0);
		assertEquals(Long.valueOf(1),order1.getId());
		Order order2 = orderList.get(1);
		assertEquals(Long.valueOf(2),order2.getId());
	}
		
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testGetTodayAvailableOrderListBy(){
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		List<Order> orderList = orderService.getTodayOrderListBy(assetSet);
		assertEquals(1,orderList.size());
		assertEquals(Long.valueOf(2),orderList.get(0).getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testGetTodayNotClearFailOrderListByCase2(){
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 2L);
		
		List<Order> orderList = orderService.getTodayOrderListBy(assetSet);
		assertEquals(0,orderList.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testGetOrderListBy(){
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		
		Order order = new Order();
		order.setDueDate(new Date());
		order.setAssetSet(assetSet);
		order.setClearingStatus(OrderClearStatus.UNCLEAR);
		order.setExecutingSettlingStatus(ExecutingSettlingStatus.CREATE);
		Customer customer = customerService.load(Customer.class,1L);
		order.setCustomer(customer);
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 1L);
		order.setFinancialContract(financialContract);
		
		orderService.save(order);
		
		List<OrderClearStatus> clearingStatusList = new ArrayList<OrderClearStatus>();
		clearingStatusList.add(OrderClearStatus.UNCLEAR);
		
		List<Order> orderList = orderService.getOrderListBy(assetSet,new Date(),clearingStatusList);
		assertEquals(2,orderList.size());
		assertEquals(new Long(1L),orderList.get(0).getCustomer().getId());
		
		orderList = orderService.getOrderListBy(null,new Date(),clearingStatusList);
		assertEquals(2,orderList.size());
		
		orderList = orderService.getOrderListBy(null,null,clearingStatusList);
		assertEquals(4,orderList.size());
		
		orderList = orderService.getOrderListBy(null,null,new ArrayList<OrderClearStatus>());
		assertEquals(0,orderList.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testgetTodayUnExecutedOrderList(){
		List<Order> orderList = orderService.getSettlementStatementInNormalProcessing();
		assertEquals(1,orderList.size());
		assertEquals(Long.valueOf(2),orderList.get(0).getId());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/createVouchersForOfflineBill.sql")
	public void testListOrderForUuids(){
		String uuid1 = "repayment_bill_id_1";
		String uuid2 = "repayment_bill_id_2";
		List<String> uuids = new ArrayList<String>();
		uuids.add(uuid1);
		uuids.add(uuid2);
		List<Order> orderList = orderService.listOrder(uuids, OrderType.GUARANTEE);
		assertEquals(2,orderList.size());
		assertEquals(new Long(1L),orderList.get(0).getId());
		assertEquals(new Long(2L),orderList.get(1).getId());
		
		//none
		orderList = orderService.listOrder(new ArrayList<String>(), OrderType.GUARANTEE);
		assertEquals(0,orderList.size());
		
		//1
		uuids.remove(uuid1);
		uuids.add("uuid_none");
		orderList = orderService.listOrder(uuids, OrderType.GUARANTEE);
		assertEquals(1,orderList.size());
		assertEquals(new Long(2L), orderList.get(0).getId());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testListOrder.sql")
	public void testListOrder(){
		OrderQueryModel orderQueryModel = new OrderQueryModel();
		Page page = new Page();
		page.setEveryPage(12);
		int begin = page.getBeginIndex();
		int max = page.getMaxResultRecords();
		orderQueryModel.setFinancialContractIds("[1,2]");
		List<Order> orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(6,orderList.size());
		
		//根据服务商
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(6,orderList.size());
		assertEquals(Long.valueOf(6),orderList.get(0).getId());
		assertEquals(Long.valueOf(5),orderList.get(1).getId());
		assertEquals(Long.valueOf(4),orderList.get(2).getId());
		assertEquals(Long.valueOf(3),orderList.get(3).getId());
		assertEquals(Long.valueOf(2),orderList.get(4).getId());
		assertEquals(Long.valueOf(1),orderList.get(5).getId());
		
		//根据信托
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[2]");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(2,orderList.size());
		assertEquals(new Long(6L),orderList.get(0).getId());
		assertEquals(new Long(5L),orderList.get(1).getId());
		
		
		//根据结算状态
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setExecutingSettlingStatusInt(0);
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(1,orderList.size());
		assertEquals(new Long(1L),orderList.get(0).getId());
		
		//根据逾期状态
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setOverDueStatus(0);
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(3,orderList.size());
		assertEquals(new Long(5L),orderList.get(0).getId());
		
		
		//结算单号
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setOrderNo("DKHD-001-01-20160307");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(1,orderList.size());
		assertEquals(new Long(1L),orderList.get(0).getId());
		
		//还款期号
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setSingleLoanContractNo("DKHD-001-01");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(2,orderList.size());
		assertEquals(new Long(2L),orderList.get(0).getId());
		assertEquals(new Long(1L),orderList.get(1).getId());
		
		//结算时间
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setSettlementStartDateString("2016-11-09");
		orderQueryModel.setSettlementEndDateString("2016-11-12");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(2,orderList.size());
		assertEquals(new Long(6L),orderList.get(0).getId());
		assertEquals(new Long(5L),orderList.get(1).getId());
		
		//应还日期
		orderQueryModel = new OrderQueryModel();
		orderQueryModel.setFinancialContractIds("[1,2]");
		orderQueryModel.setAssetRecycleStartDateString("2016-11-11");
		orderQueryModel.setAssetRecycleEndDateString("2016-11-11");
		orderList = orderService.listOrder(orderQueryModel,begin, max);
		assertEquals(2,orderList.size());
		assertEquals(new Long(6L),orderList.get(0).getId());
		assertEquals(new Long(5L),orderList.get(1).getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/audit/testSmartMatchOrder/testMatchOrderExact.sql")
	public void testGetOrderBy(){
		Contract contract = contractService.load(Contract.class,1L); 
		List<ExecutingSettlingStatus> executingSettingStatusList = new ArrayList<ExecutingSettlingStatus>();
		executingSettingStatusList.add(ExecutingSettlingStatus.CREATE);
		executingSettingStatusList.add(ExecutingSettlingStatus.FAIL);
		List<Order> orderList = orderService.get_repayment_order_not_in_process_and_asset_not_clear(contract, DateUtils.asDay("2016-05-17"), AssetSetActiveStatus.OPEN);
		assertEquals(1,orderList.size());
		assertEquals(new Long(2L),orderList.get(0).getId());
		//fail();
		//assertNotNull(order);
		//assertEquals(new Long(1L),order.getId());
	
		//order = orderService.get_repayment_order_not_in_process_and_asset_not_clear(contract, null, null);
		//assertNotNull(order);
		//assertEquals(new Long(3L),order.getId());
		
		//order = orderService.get_repayment_order_not_in_process_and_asset_not_clear(contract, null, null);
		//assertNull(order);
	}
	
	
}
