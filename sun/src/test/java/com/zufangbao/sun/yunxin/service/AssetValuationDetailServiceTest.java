package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AssetValuationDetailServiceTest {
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Test
	@Sql("classpath:test/yunxin/assetValuation/testAssetValuation.sql")
	public void testGetAssetValuationDetailListByAsset() {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet, new Date());
		assertEquals(2, assetValuationDetails.size());
		AssetValuationDetail assetValuationDetail1 = assetValuationDetails.get(0);
		assertEquals(1L,assetValuationDetail1.getId());
		assertEquals(AssetValuationSubject.REPAYMENT_AMOUNT,assetValuationDetail1.getSubject());
		
		AssetValuationDetail assetValuationDetail2 = assetValuationDetails.get(1);
		assertEquals(2L,assetValuationDetail2.getId());
		assertEquals(AssetValuationSubject.PENALTY_INTEREST,assetValuationDetail2.getSubject());
		
	}

	@Test
	@Sql("classpath:test/yunxin/assetValuation/testAssetValuation.sql")
	public void testGetAssetValuationDetailListByAssetForNull() {
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(null, new Date());
		assertEquals(0,assetValuationDetails.size());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/assetValuation/get_asset_valuation_detail_by_asset_set_subject_and_date_with_no_data.sql")
	public void get_asset_valuation_detail_by_asset_set_subject_and_date_with_no_data() {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		assertNotNull(assetSet);
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 1L);
		assertNotNull(assetValuationDetail);
		assertEquals(AssetValuationSubject.REPAYMENT_AMOUNT, assetValuationDetail.getSubject());
		
		Date day = DateUtils.asDay("2016-03-15");
		AssetValuationDetail result = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet, AssetValuationSubject.PENALTY_INTEREST, day);
		assertNull(result);
	}
	
	@Test
	@Sql("classpath:test/yunxin/assetValuation/get_asset_valuation_detail_by_asset_set_subject_and_date_with_data.sql")
	public void get_asset_valuation_detail_by_asset_set_subject_and_date_with_data() {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 1L);
		assertNotNull(assetSet);
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.load(AssetValuationDetail.class, 1L);
		assertNotNull(assetValuationDetail);
		assertEquals(AssetValuationSubject.REPAYMENT_AMOUNT, assetValuationDetail.getSubject());
		
		Date day = DateUtils.asDay("2016-03-15");
		AssetValuationDetail result = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet, AssetValuationSubject.REPAYMENT_AMOUNT, day);
		assertNotNull(result);
		assertEquals(assetValuationDetail.getId(), result.getId());
		assertEquals(assetValuationDetail.getSubject(),result.getSubject());
	}
}
