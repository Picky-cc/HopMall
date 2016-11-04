package com.zufangbao.earth.yunxin.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.earth.yunxin.api.dataSync.task.DataSyncTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
@TransactionConfiguration(defaultRollback = false)
public class CommandDataSyncTest {

	
	@Autowired
	private  DataSyncTask dataSyncTask;
	
	
	@Test
	@Sql("classpath:test/yunxin/api/testCommandSyncTest.sql")
	public void testCommandSyncTest(){
		
		dataSyncTask.commandDataSync();
	}
	
	@Test
	@Sql("")
	public  void testCommandSyncTestFor(){
		
	}
	
}
