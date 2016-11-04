package com.zufangbao.earth.yunxin.task;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
@TransactionConfiguration(defaultRollback = true)
public class AssetTaskTest {

	@Autowired
	private AssetTask assetTask;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	
	@Test
	@Sql("classpath:test/yunxin/asset_valuation.sql")
	public void testAssetValuationAndCreateOrder() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(4, contracts.size());
		List<AssetSet> assets = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(4, assets.size());
		
		assetTask.endYesterdayWorkAndStartTodayWork();
		
		List<AssetSet> assetList = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(4, assetList.size());
		//应还款日是当天的
		Assert.assertEquals(10000L, assetList.get(0).getAssetFairValue().longValue());
		//逾期1天的，生成2条评估
		Assert.assertEquals(10000L, assetList.get(1).getAssetFairValue().longValue());
		//逾期5天的，生成6条评估
		Assert.assertEquals(10005L, assetList.get(2).getAssetFairValue().longValue());
		//逾期6天的，作废还款计划不评估
		Assert.assertEquals(10000L, assetList.get(3).getAssetFairValue().longValue());
		
		for (AssetSet assetSet : assetList) {
			Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSet.getAssetStatus());
			if(assetSet.getId() != 4L) {
				Assert.assertEquals(DateUtils.asDay(new Date()), DateUtils.asDay(assetSet.getLastModifiedTime()));
			}else {
				Assert.assertEquals(DateUtils.asDay("2016-03-15"), DateUtils.asDay(assetSet.getLastModifiedTime()));
			}
		}
		
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		Assert.assertEquals(9, assetValuationDetailList.size());
		
		//测试生成的订单
		List<Order> orderList_byAsset1 = orderService.getOrderListByAssetSetId(1L);
		assertEquals(1,orderList_byAsset1.size());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),orderList_byAsset1.get(0).getDueDate()));
		assertEquals(OrderClearStatus.UNCLEAR,orderList_byAsset1.get(0).getClearingStatus());
		assertEquals(0,new BigDecimal(10000L).compareTo(orderList_byAsset1.get(0).getTotalRent()));
		
		List<Order> orderList_byAsset2 = orderService.getOrderListByAssetSetId(2L);
		assertEquals(1,orderList_byAsset2.size());
		assertEquals(OrderClearStatus.UNCLEAR,orderList_byAsset2.get(0).getClearingStatus());
		assertEquals(0,new BigDecimal(10000L).compareTo(orderList_byAsset2.get(0).getTotalRent()));
		
		List<Order> orderList_byAsset3 = orderService.getOrderListByAssetSetId(3L);
		assertEquals(1,orderList_byAsset3.size());
		assertEquals(OrderClearStatus.UNCLEAR,orderList_byAsset3.get(0).getClearingStatus());
		assertEquals(0,new BigDecimal(10005L).compareTo(orderList_byAsset3.get(0).getTotalRent()));
		
		List<Order> orderList_byAsset4 = orderService.getOrderListByAssetSetId(4L);
		assertEquals(0,orderList_byAsset4.size());
	}

}
