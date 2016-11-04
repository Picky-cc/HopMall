package com.zufangbao.sun.yunxin.service;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RepaymentPlanExtraChargeServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	
	
	@Test
	@Sql("classpath:test/yunxin/paymentPlanExtraCharge/testPaymentPlanExtraChargeNormal.sql")
	public void testGetAssetSetExtraChargeModels(){
		
		String assetSetUuid = "88450378-e6fd-4857-9562-22971b05b932";
		Map<String , BigDecimal> amountMap = repaymentPlanExtraChargeService.getAssetSetExtraChargeModels(assetSetUuid);
		
		Assert.assertEquals(2, amountMap.size());
	    
	}
	

	@Test
	@Sql("classpath:test/yunxin/paymentPlanExtraCharge/testPaymentPlanExtraChargeNormal.sql")
	public void  testGetTotalOverDueFee(){
		String assetSetUuid = "88450378-e6fd-4857-9562-22971b05b932";
		BigDecimal totalAmount = repaymentPlanExtraChargeService.getTotalOverDueFee(assetSetUuid);
		
		Assert.assertEquals(new BigDecimal("190.00"), totalAmount);
	}
	
	@Test
	@Sql("classpath:test/yunxin/paymentPlanExtraCharge/testPaymentPlanExtraChargeNormal.sql")
	public void  testGetTotalAmount(){
		String assetSetUuid = "88450378-e6fd-4857-9562-22971b05b932";
		BigDecimal totalAmount = repaymentPlanExtraChargeService.getTotalAmount(assetSetUuid);
		
		Assert.assertEquals(new BigDecimal("190.00"), totalAmount);
	}
	
	
}
