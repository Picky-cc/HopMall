package com.zufangbao.earth.yunxin.handler.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;
import com.zufangbao.wellsfargo.yunxin.handler.SettlementOrderHandler;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SettlementOrderHandlerTest {
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private SettlementOrderService settlementOrderService;
	@Autowired
	private SettlementOrderHandler settlementOrderHandler;
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/test_create_all_settlement_order_with_no_need.sql")
	public void test_create_all_settlement_order_with_no_need() {
		settlementOrderHandler.createAllSettlementOrder();
		List<SettlementOrder> allSettlementOrder = settlementOrderService.list(SettlementOrder.class, new Filter());
		Assert.assertTrue(allSettlementOrder.isEmpty());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/test_create_all_settlement_order.sql")
	public void test_create_all_settlement_order() {
		List<AssetSet> allAssetSet = repaymentPlanService.loadAll(AssetSet.class);
		Assert.assertEquals(1, allAssetSet.size());
		Assert.assertEquals(SettlementStatus.NOT_OCCURRED, allAssetSet.get(0).getSettlementStatus());
		List<SettlementOrder> allSettlementOrder = settlementOrderService.list(SettlementOrder.class, new Filter());
		Assert.assertTrue(allSettlementOrder.isEmpty());
		settlementOrderHandler.createAllSettlementOrder();
		List<AssetSet> allAssetSetAfter = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(SettlementStatus.CREATE, allAssetSetAfter.get(0).getSettlementStatus());
		List<SettlementOrder> allSettlementOrderAfter = settlementOrderService.list(SettlementOrder.class, new Filter());
		Assert.assertFalse(allSettlementOrderAfter.isEmpty());
		
	}
	
	
	
}
