package com.zufangbao.sun.yunxin.service;

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

import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration(defaultRollback = true)
public class DeductApplicationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private  DeductApplicationService deductApplicationService;
	
	
	

	@Test
	@Sql("classpath:test/yunxin/deductApi/test_getDeductApplicationByRepaymentPlanCodeAndInprocessing.sql")
	public void test_getDeductApplicationByRepaymentPlanCodeAndInprocessing() {
		
		String assetSetUuid ="1234567";
		List<DeductApplication> deductApplications  = deductApplicationService.getDeductApplicationByRepaymentPlanCodeAndInprocessing(assetSetUuid);
		Assert.assertEquals(2, deductApplications.size());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/deductApi/test_getDeductApplicationByRepaymentPlanCodeAndInprocessingNoData.sql")
	public void test_getDeductApplicationByRepaymentPlanCodeAndInprocessingNoData() {
		
		String assetSetUuid ="12345678";
		List<DeductApplication> deductApplications  = deductApplicationService.getDeductApplicationByRepaymentPlanCodeAndInprocessing(assetSetUuid);
		Assert.assertEquals(0, deductApplications.size());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/deductApi/test_getDeductApplicationByRepaymentPlanCode.sql")
	public void test_getDeductApplicationByRepaymentPlanCode() {
		
		String assetSetUuid ="1234567";
		List<DeductApplication> deductApplications  = deductApplicationService.getDeductApplicationByRepaymentPlanCode(assetSetUuid);
		Assert.assertEquals(2, deductApplications.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/deductApi/get_processing_or_success_list.sql")
	public void get_processing_or_success_list() {
		
		String assetSetUuid ="1234567";
		List<DeductApplication> deductApplications  = deductApplicationService.get_processing_or_success_list(assetSetUuid);
		Assert.assertEquals(2, deductApplications.size());
	}
	
}
