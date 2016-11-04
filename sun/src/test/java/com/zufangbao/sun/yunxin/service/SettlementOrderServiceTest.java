package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SettlementOrderServiceTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private SettlementOrderService settlementOrderService;
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCountSettlementOrder.sql")
	public void testCountSettlementOrderList(){
		int count = settlementOrderService.countSettlementOrderList(Arrays.asList(1L), SettlementStatus.CREATE);
		assertEquals(1,count);
		count = settlementOrderService.countSettlementOrderList(Arrays.asList(1L), SettlementStatus.NOT_OCCURRED);
		assertEquals(1,count);
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testGetSettlementOrderByQuery.sql")
	public void testQuerySettlementOrderList(){
		SettlementOrderQueryModel settlementOrderQueryModel = new SettlementOrderQueryModel();
		settlementOrderQueryModel.setFinancialContractIds("[1]");
		List<SettlementOrder> orders = settlementOrderService.getSettlementOrderListBy(settlementOrderQueryModel, null, 0, 0);
		assertEquals(2,orders.size());

		settlementOrderQueryModel = new SettlementOrderQueryModel();
		settlementOrderQueryModel.setFinancialContractIds("[1]");
		settlementOrderQueryModel.setSettlementStatus(SettlementStatus.CREATE.ordinal());
		orders = settlementOrderService.getSettlementOrderListBy(settlementOrderQueryModel, null, 0, 0);
		assertEquals(1,orders.size());
		assertEquals(new Long(2L),orders.get(0).getId());
		
	}
	
}
