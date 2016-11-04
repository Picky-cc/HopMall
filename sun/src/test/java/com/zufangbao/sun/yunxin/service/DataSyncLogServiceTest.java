package com.zufangbao.sun.yunxin.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class DataSyncLogServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private DataSyncLogService dataSyncLogService;
	
	@Test
	@Sql("classpath:test/yunxin/dataSync/test4DataSyncService.sql")
	public void testCountSuccessAssetSet(){
		int cnt = dataSyncLogService.countSuccessAssetSet("assetSetUuid1", RepayType.NORMAL);
		Assert.assertEquals(1, cnt);
	}
	
	
	
}
