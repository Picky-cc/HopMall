package com.zufangbao.sun.yunxin.service;

import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration(defaultRollback = true)
public class DeductApplicationDetailServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	@Autowired
	private  DeductApplicationDetailService deductApplicationDetailService;
	
	

	@Test
	@Sql("classpath:test/yunxin/deductApi/test_getRepaymentDetailsBy.sql")
	public void test_getRepaymentDetailsBy() {
		
		String deductApplicationUuid ="9d61fa8b-76b4-4ade-b402-b725bc92391a";
		List<DeductApplicationRepaymentDetail> repaymentDetails   = deductApplicationDetailService.getRepaymentDetailsBy(deductApplicationUuid);
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/deductApi/testGetDeductApplicationUuidByAssetSetUuid.sql")
	public void testGetDeductApplicationUuidByAssetSetUuid() {
		
		String assetSetUuid ="1234567";
		Set<String> deductApplicationUuidList = deductApplicationDetailService.getDeductApplicationUuidByAssetUuid(assetSetUuid);
		
		Assert.assertEquals(2, deductApplicationUuidList.size());
	}
	
}
