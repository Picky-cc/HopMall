package com.zufangbao.wellsfargo.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.model.OrderViewDetail;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
public class OrderHandlerYunxinTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private OrderHandler orderHandler;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOrderDetailView.sql")
	public void testConvertToOrderDetail(){
		Order order = orderService.load(Order.class, 1L);
		OrderViewDetail orderDetail = orderHandler.convertToOrderDetail(order);
		assertNotNull(orderDetail);
		Order orderView = orderDetail.getOrder();
		assertNotNull(orderView);
		assertEquals(new Long(1L),orderView.getId());
		assertEquals("DKHD-001-01-20160307",orderView.getOrderNo());
		Customer customer = orderDetail.getCustomer();
		assertEquals("D001",customer.getSource());
		ContractAccount contractAccount = orderDetail.getContractAccount();
		assertEquals("payer_name_1",contractAccount.getPayerName());
		assertEquals("pay_ac_no_1",contractAccount.getPayAcNo());
		assertEquals("bind_id",contractAccount.getBindId());
		
	}
	
	@Test
	public void testConvertToOrderDetailForNull(){
		OrderViewDetail orderDetail = orderHandler.convertToOrderDetail(null);
		assertNull(orderDetail);
	}
	
	@Test
	@Sql("classpath:test/yunxin/guaranteeOrder/testCreateGuaranteeOrder.sql")
	public void testCreateGuaranteeOrder() {
		List<AssetSet> assetList = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(GuaranteeStatus.NOT_OCCURRED, assetList.get(0).getGuaranteeStatus());
		Assert.assertEquals(5, assetList.size());
		List<Order> orderList = orderService.list(Order.class, new Filter());
		Assert.assertTrue(orderList.isEmpty());
		
		orderHandler.createGuaranteeOrder();
		
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		Assert.assertEquals(GuaranteeStatus.WAITING_GUARANTEE, assetSet.getGuaranteeStatus());

		AssetSet assetSet2 = repaymentPlanService.load(AssetSet.class, 2L);
		Assert.assertEquals(GuaranteeStatus.NOT_OCCURRED, assetSet2.getGuaranteeStatus());
		
		AssetSet assetSet3 = repaymentPlanService.load(AssetSet.class, 3L);
		Assert.assertEquals(GuaranteeStatus.NOT_OCCURRED, assetSet3.getGuaranteeStatus());
		
		AssetSet assetSet4 = repaymentPlanService.load(AssetSet.class, 4L);
		Assert.assertEquals(GuaranteeStatus.NOT_OCCURRED, assetSet4.getGuaranteeStatus());
		//assetSet5已作废，未处理
		AssetSet assetSet5 = repaymentPlanService.load(AssetSet.class, 5L);
		Assert.assertEquals(GuaranteeStatus.NOT_OCCURRED, assetSet5.getGuaranteeStatus());
		
		List<Order> orderListAfter = orderService.list(Order.class, new Filter());
		Assert.assertEquals(1, orderListAfter.size());
		Order guaranteeOrder = orderListAfter.get(0);
		Assert.assertEquals(OrderType.GUARANTEE, guaranteeOrder.getOrderType());
		Assert.assertEquals(1L, guaranteeOrder.getAssetSet().getId().longValue());
		
		FinancialContract financialContract = assetPackageService.getFinancialContract(assetSet.getContract());
		Date dueDay = DateUtils.addDaysNotIncludingWeekend(new Date(), financialContract.getAdvaMatuterm());
		Assert.assertEquals(DateUtils.asDay(dueDay), DateUtils.asDay(guaranteeOrder.getDueDate()));
		
		
	}
}
