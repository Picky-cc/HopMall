package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration
public class AssetSetServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Test
	@Sql("classpath:test/yunxin/assetValuation/testAssetValuation.sql")
	public void testGetNotClearAssetSetListByAssetRecycleDate(){
		Date date = DateUtils.asDay("2015-10-19");
		List<Long> assetSetIds = repaymentPlanService.get_all_receivable_unclear_asset_set_list(date);
		assertEquals(1,assetSetIds.size());
		Long assetSetId = assetSetIds.get(0);
		assertEquals(Long.valueOf(1L), assetSetId);
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_get_all_needs_assessment_asset_set_list_with_no_date.sql")
	public void test_get_all_needs_assessment_asset_set_list_with_no_date() {
		List<AssetSet> all = repaymentPlanService.list(AssetSet.class,new Filter());
		Assert.assertEquals(2, all.size());
		
		Date date = DateUtils.asDay("2016-06-03");
		
		List<Long> result = repaymentPlanService.get_all_receivable_unclear_asset_set_list(date);
		Assert.assertEquals(0, result.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_get_all_needs_assessment_asset_set_list_with_date.sql")
	public void test_get_all_needs_assessment_asset_set_list_with_date() {
		List<AssetSet> all = repaymentPlanService.list(AssetSet.class,new Filter());
		Assert.assertEquals(3, all.size());
		
		Date date = DateUtils.asDay("2016-06-03");
		
		List<Long> result = repaymentPlanService.get_all_receivable_unclear_asset_set_list(date);
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(Long.valueOf(1L), result.get(0));
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/test_load_all_need_guarantee_asset_set_list.sql")
	public void test_load_all_need_guarantee_asset_set_list() {
		List<AssetSet> all = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(4, all.size());
		
		List<AssetSet> needGuaranteeAssetSetList = repaymentPlanService.loadAllNeedGuaranteeAssetSetList();
		Assert.assertEquals(1, needGuaranteeAssetSetList.size());
		Assert.assertEquals("0d712307-a747-4a57-9fa5-792cf76c1a4c", needGuaranteeAssetSetList.get(0).getAssetUuid());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_load_all_need_guarantee_asset_set_list_no_data.sql")
	public void test_load_all_need_guarantee_asset_set_list_no_data() {
		List<AssetSet> all = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(2, all.size());
		
		List<AssetSet> needGuaranteeAssetSetList = repaymentPlanService.loadAllNeedGuaranteeAssetSetList();
		Assert.assertTrue(needGuaranteeAssetSetList.isEmpty());
	}
	
	@Test
	@Sql("classpath:test/yunxin/GetAllRemindAssetSet.sql")
	public void testGetAllRemindAssetSet() {
		List<AssetSet> all = repaymentPlanService.list(AssetSet.class, new Filter());
		Assert.assertEquals(3, all.size());
		Date addDays = DateUtils.asDay(DateUtils.addDays(new Date(), 2));
		Assert.assertEquals(addDays, all.get(0).getAssetRecycleDate());
		Assert.assertEquals(DateUtils.asDay(DateUtils.today()), all.get(1).getAssetRecycleDate());
		
		List<AssetSet> needs_remind_assetSet_list = repaymentPlanService.get_all_needs_remind_assetSet_list(2);
		Assert.assertEquals(1, needs_remind_assetSet_list.size());
		AssetSet assetSet1 = needs_remind_assetSet_list.get(0);
		Assert.assertEquals(addDays, assetSet1.getAssetRecycleDate());
		Assert.assertEquals(1, assetSet1.getId().longValue());
	}
}
