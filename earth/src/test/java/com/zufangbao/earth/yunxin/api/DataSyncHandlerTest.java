package com.zufangbao.earth.yunxin.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.earth.yunxin.api.handler.DataSyncHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
public class DataSyncHandlerTest {
	
	@Autowired
	private DataSyncHandler dataSyncHandler;
	
	@Test
	@Sql("classpath:test/yunxin/api/test4DataSyncHandler.sql")
	public void getPendingRepaymentPlanListTest(){
//		List<AssetSet> assetSets = dataSyncHandler.getPendingRepaymentPlanList();
//		Assert.assertEquals(1, assetSets.size());
	}

}
