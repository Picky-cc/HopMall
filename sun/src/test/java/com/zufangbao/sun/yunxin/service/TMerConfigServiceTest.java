package com.zufangbao.sun.yunxin.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.sun.yunxin.entity.api.TMerConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration(defaultRollback = true)
public class TMerConfigServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private TMerConfigService tMerConfigService;
	
	@Test
	public void test_no_result_0() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig(null, null);
		Assert.assertNull(tMerConfig);
	}

	@Test
	public void test_no_result_1() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("", null);
		Assert.assertNull(tMerConfig);
	}

	@Test
	public void test_no_result_2() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig(null, "");
		Assert.assertNull(tMerConfig);
	}
	
	@Test
	public void test_no_result_3() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("merId", null);
		Assert.assertNull(tMerConfig);
	}

	@Test
	public void test_no_result_4() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("merId", "");
		Assert.assertNull(tMerConfig);
	}
	
	@Test
	public void test_no_result_5() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig(null, "secret");
		Assert.assertNull(tMerConfig);
	}
	
	@Test
	public void test_no_result_6() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("", "secret");
		Assert.assertNull(tMerConfig);
	}
	
	@Test
	public void test_no_result_7() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("merId", "secret");
		Assert.assertNull(tMerConfig);
	}
	
	@Test
	@Sql("classpath:test/yunxin/testMerConfig.sql")
	public void test_getTMerConfig() {
		TMerConfig tMerConfig = tMerConfigService.getTMerConfig("ynTrust", "123456");
		Assert.assertNotNull(tMerConfig);
	}
	
}
