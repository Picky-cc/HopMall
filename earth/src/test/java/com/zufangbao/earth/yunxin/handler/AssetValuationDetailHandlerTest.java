package com.zufangbao.earth.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AssetValuationDetailHandlerTest{

	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	@Test
	@Sql("classpath:test/yunxin/asset_valuation_with_no_need_valuation.sql")
	public void asset_valuation_with_no_need_valuation() {
		List<AssetSet> assetList = repaymentPlanService.list(AssetSet.class, new Filter());
		for (AssetSet assetSet : assetList) {
			Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSet.getAssetStatus());
		}
		Assert.assertEquals(2, assetList.size());		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		if(!assetValuationDetails.isEmpty()) {
			Assert.fail();
		}
		
		Date date = DateUtils.parseDate("2016-06-02", "yyyy-MM-dd");
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
		assetList = repaymentPlanService.list(AssetSet.class, new Filter());
		for (AssetSet assetSet : assetList) {
			Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSet.getAssetStatus());
		}
		assetValuationDetails = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		if(!assetValuationDetails.isEmpty()) {
			Assert.fail();
		}
	}

	@Test
	@Sql("classpath:test/yunxin/asset_valuation.sql")
	public void asset_valuation() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(4, contracts.size());
		List<AssetSet> assetsBefore = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(4, assetsBefore.size());
		
		Date date = new Date();
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
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
		
	}
	
	@Test
	public void testExtractIdsFrom(){
		AssetValuationDetail assetDetail_1 = new AssetValuationDetail();
		assetDetail_1.setId(1L);
		AssetValuationDetail assetDetail_2 = new AssetValuationDetail();
		assetDetail_2.setId(2L);
		List<AssetValuationDetail> assetValuationDetailList = new ArrayList<AssetValuationDetail>();
		assetValuationDetailList.add(assetDetail_1);
		assetValuationDetailList.add(assetDetail_2);
		
		List<Long> ids = assetValuationDetailHandler.extractIdsFrom(assetValuationDetailList);
		assertEquals(2,ids.size());
		assertEquals(new Long(1L),ids.get(0));
		assertEquals(new Long(2L),ids.get(1));
	}
	
	@Test
	public void testExtractIdsFromForEmptyOrNull(){
		List<AssetValuationDetail> assetValuationDetailList = new ArrayList<AssetValuationDetail>();
		List<Long> ids = assetValuationDetailHandler.extractIdsFrom(assetValuationDetailList);
		assertEquals(0,ids.size());
		
		AssetValuationDetail assetDetail_1 = new AssetValuationDetail();
		assetDetail_1.setId(1L);
		assetValuationDetailList.add(assetDetail_1);
		assetValuationDetailList.add(null);
		ids = assetValuationDetailHandler.extractIdsFrom(assetValuationDetailList);
		assertEquals(1,ids.size());
		assertEquals(new Long(1L),ids.get(0));
	}
	
	@Test
	public void testExtractIdsJsonFrom(){
		AssetValuationDetail assetDetail_1 = new AssetValuationDetail();
		assetDetail_1.setId(1L);
		AssetValuationDetail assetDetail_2 = new AssetValuationDetail();
		assetDetail_2.setId(2L);
		List<AssetValuationDetail> assetValuationDetailList = new ArrayList<AssetValuationDetail>();
		assetValuationDetailList.add(assetDetail_1);
		assetValuationDetailList.add(assetDetail_2);
		
		String json = assetValuationDetailHandler.extractIdsJsonFrom(assetValuationDetailList);
		assertEquals("[1,2]",json);
	}
	
	@Test
	@Sql("classpath:test/yunxin/modifyOrderAmount/modify_asset_and_order_amount.sql")
	public void test_add_asset_valuation_and_modify_amount_of_order(){
		//原来总金额10010元
		BigDecimal deltaAmount = new BigDecimal("-11.00");
		Date valutionDay = DateUtils.asDay("2016-05-18");
		Order orderInDb = orderService.load(Order.class, 3L);
		assetValuationDetailHandler.add_asset_valuation_and_modify_amount_of_asset_order(orderInDb, deltaAmount, valutionDay, null);
		AssetSet assetSet_after = repaymentPlanService.load(AssetSet.class,1L);
		
		assertEquals(0,new BigDecimal ("9999").compareTo(assetSet_after.getAssetFairValue()));
		AssetValuationDetail valuationDetail = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet_after, AssetValuationSubject.AMOUNT_ADJUSTMENT, DateUtils.asDay("2016-05-18"));
		assertNotNull(valuationDetail);
		assertEquals(0,deltaAmount.compareTo(valuationDetail.getAmount()));
		Order order = orderService.load(Order.class, 3L);
		
		assertEquals(0,new BigDecimal("9999").compareTo(order.getTotalRent()));
		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet_after, valutionDay);
		String assetValuationDetailIds = assetValuationDetailHandler.extractIdsJsonFrom(assetValuationDetails);
		
		assertEquals(assetValuationDetailIds,order.getUserUploadParam());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/asset_valuation_20160529.sql")
	public void asset_valuation_same_day() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(1, contracts.size());
		List<AssetSet> assetsBefore = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsBefore.size());
		AssetSet assetSetBefore = assetsBefore.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetSetBefore.getAssetRecycleDate());
		
		Date date = DateUtils.parseDate("2016-05-29", "yyyy-MM-dd");
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
		List<AssetSet> assetsAfter = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsAfter.size());
		AssetSet assetSetAfter = assetsAfter.get(0);
		//应还款日是当天的
		Assert.assertEquals(10000L, assetSetAfter.getAssetFairValue().longValue());
		Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSetAfter.getAssetStatus());
		Assert.assertEquals(DateUtils.asDay(new Date()), DateUtils.asDay(assetSetAfter.getLastModifiedTime()));
		
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		Assert.assertEquals(1, assetValuationDetailList.size());

		AssetValuationDetail assetValuationDetail = assetValuationDetailList.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetValuationDetail.getCreatedDate());
		Assert.assertEquals("应还金额", 10000L, assetValuationDetail.getAmount().longValue());
	}
	
	@Test
	@Sql("classpath:test/yunxin/asset_valuation_20160529.sql")
	public void asset_valuation_overdue_one_day() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(1, contracts.size());
		List<AssetSet> assetsBefore = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsBefore.size());
		AssetSet assetSetBefore = assetsBefore.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetSetBefore.getAssetRecycleDate());
		
		Date date = DateUtils.parseDate("2016-05-30", "yyyy-MM-dd");
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
		List<AssetSet> assetsAfter = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsAfter.size());
		AssetSet assetSetAfter = assetsAfter.get(0);
		//逾期1天的，生成2条评估
		Assert.assertEquals(10000L, assetSetAfter.getAssetFairValue().longValue());
		Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSetAfter.getAssetStatus());
		Assert.assertEquals(DateUtils.asDay(new Date()), DateUtils.asDay(assetSetAfter.getLastModifiedTime()));
		
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		Assert.assertEquals(2, assetValuationDetailList.size());

		AssetValuationDetail assetValuationDetail = assetValuationDetailList.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetValuationDetail.getCreatedDate());
		Assert.assertEquals("应还金额", 10000L, assetValuationDetail.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail1 = assetValuationDetailList.get(1);
		Assert.assertEquals(DateUtils.asDay("2016-05-30"), assetValuationDetail1.getCreatedDate());
		Assert.assertEquals("免息期第一天", 0L, assetValuationDetail1.getAmount().longValue());
	}

	@Test
	@Sql("classpath:test/yunxin/asset_valuation_20160529.sql")
	public void asset_valuation_overdue_five_day() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(1, contracts.size());
		List<AssetSet> assetsBefore = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsBefore.size());
		AssetSet assetSetBefore = assetsBefore.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetSetBefore.getAssetRecycleDate());
		
		Date date = DateUtils.parseDate("2016-06-03", "yyyy-MM-dd");
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
		List<AssetSet> assetsAfter = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsAfter.size());
		AssetSet assetSetAfter = assetsAfter.get(0);
		//逾期5天的，生成6条评估
		Assert.assertEquals(10005L, assetSetAfter.getAssetFairValue().longValue());
		Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSetAfter.getAssetStatus());
		Assert.assertEquals(DateUtils.asDay(new Date()), DateUtils.asDay(assetSetAfter.getLastModifiedTime()));
		
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		Assert.assertEquals(6, assetValuationDetailList.size());
		
		AssetValuationDetail assetValuationDetail = assetValuationDetailList.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetValuationDetail.getCreatedDate());
		Assert.assertEquals("应还金额", 10000L, assetValuationDetail.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail1 = assetValuationDetailList.get(1);
		Assert.assertEquals(DateUtils.asDay("2016-05-30"), assetValuationDetail1.getCreatedDate());
		Assert.assertEquals("免息期第一天", 0L, assetValuationDetail1.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail2 = assetValuationDetailList.get(2);
		Assert.assertEquals(DateUtils.asDay("2016-05-31"), assetValuationDetail2.getCreatedDate());
		Assert.assertEquals(0L, assetValuationDetail2.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail3 = assetValuationDetailList.get(3);
		Assert.assertEquals(DateUtils.asDay("2016-06-01"), assetValuationDetail3.getCreatedDate());
		Assert.assertEquals(0L, assetValuationDetail3.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail4 = assetValuationDetailList.get(4);
		Assert.assertEquals(DateUtils.asDay("2016-06-02"), assetValuationDetail4.getCreatedDate());
		Assert.assertEquals("免息期最后一天", 0L, assetValuationDetail4.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail5 = assetValuationDetailList.get(5);
		Assert.assertEquals(DateUtils.asDay("2016-06-03"), assetValuationDetail5.getCreatedDate());
		Assert.assertEquals("逾期第5天，开始计算罚息的第一天", 5L, assetValuationDetail5.getAmount().longValue());
	}
	
	@Test
	@Sql("classpath:test/yunxin/asset_valuation_20160529.sql")
	public void asset_valuation_overdue_six_day() {
		List<Contract> contracts = contractService.list(Contract.class, new Filter());
		Assert.assertEquals(1, contracts.size());
		List<AssetSet> assetsBefore = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsBefore.size());
		AssetSet assetSetBefore = assetsBefore.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetSetBefore.getAssetRecycleDate());
		
		Date date = DateUtils.parseDate("2016-06-04", "yyyy-MM-dd");
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(date);
		for (Long assetSetId : assetSetIds) {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
			try {
				assetValuationDetailHandler.assetValuation(assetSet, date);
			} catch (Exception e) {
				fail();
			}
		}
		
		List<AssetSet> assetsAfter = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(1, assetsAfter.size());
		AssetSet assetSetAfter = assetsAfter.get(0);
		//逾期6天的，生成7条评估
		Assert.assertEquals(10010L, assetSetAfter.getAssetFairValue().longValue());
		Assert.assertEquals(AssetClearStatus.UNCLEAR, assetSetAfter.getAssetStatus());
		Assert.assertEquals(DateUtils.asDay(new Date()), DateUtils.asDay(assetSetAfter.getLastModifiedTime()));
		
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.list(AssetValuationDetail.class, new Filter());
		Assert.assertEquals(7, assetValuationDetailList.size());
	
		AssetValuationDetail assetValuationDetail = assetValuationDetailList.get(0);
		Assert.assertEquals(DateUtils.asDay("2016-05-29"), assetValuationDetail.getCreatedDate());
		Assert.assertEquals("应还金额", 10000L, assetValuationDetail.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail1 = assetValuationDetailList.get(1);
		Assert.assertEquals(DateUtils.asDay("2016-05-30"), assetValuationDetail1.getCreatedDate());
		Assert.assertEquals("免息期第一天", 0L, assetValuationDetail1.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail2 = assetValuationDetailList.get(2);
		Assert.assertEquals(DateUtils.asDay("2016-05-31"), assetValuationDetail2.getCreatedDate());
		Assert.assertEquals(0L, assetValuationDetail2.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail3 = assetValuationDetailList.get(3);
		Assert.assertEquals(DateUtils.asDay("2016-06-01"), assetValuationDetail3.getCreatedDate());
		Assert.assertEquals(0L, assetValuationDetail3.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail4 = assetValuationDetailList.get(4);
		Assert.assertEquals(DateUtils.asDay("2016-06-02"), assetValuationDetail4.getCreatedDate());
		Assert.assertEquals("免息期最后一天", 0L, assetValuationDetail4.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail5 = assetValuationDetailList.get(5);
		Assert.assertEquals(DateUtils.asDay("2016-06-03"), assetValuationDetail5.getCreatedDate());
		Assert.assertEquals("逾期第5天，开始计算罚息的第一天", 5L, assetValuationDetail5.getAmount().longValue());
		
		AssetValuationDetail assetValuationDetail6 = assetValuationDetailList.get(6);
		Assert.assertEquals(DateUtils.asDay("2016-06-04"), assetValuationDetail6.getCreatedDate());
		Assert.assertEquals("逾期第6天，开始计算罚息的第二天", 5L, assetValuationDetail6.getAmount().longValue());
		
	}
}