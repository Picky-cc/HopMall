package com.suidifu.hathaway.handler.impl;

import static org.junit.Assert.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.suidifu.hathaway.CacheKey;
import com.suidifu.hathaway.handler.MqStatusInspectorHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" 
})
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class MqStatusInspectorHandlerImplTest {
	
	@Autowired
	private MqStatusInspectorHandler mqStatusInspectorHandler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSetStatus() {
		
		String key = UUID.randomUUID().toString();
		
		int timeout = 12;
		
		System.out.println("key="+key);
		
		CacheKey cacheKey = new CacheKey(key, "value", timeout, TimeUnit.HOURS);
		
		mqStatusInspectorHandler.setStatus(cacheKey);
		
		assertFalse(mqStatusInspectorHandler.needRetrySendRequest(cacheKey.getKey()));
	}

	@Test
	public void testNeedRetrySendRequest() throws InterruptedException {
		
		String key = UUID.randomUUID().toString();
		
		int timeout = 12;
		
		System.out.println("key="+key);
		
		CacheKey cacheKey = new CacheKey(key, "value", timeout, TimeUnit.HOURS);
		
		mqStatusInspectorHandler.setStatus(cacheKey);
		
		assertFalse(mqStatusInspectorHandler.needRetrySendRequest(cacheKey.getKey()));
		
		key = UUID.randomUUID().toString();
		
		timeout = 1;
		
		System.out.println("key="+key);
		
		cacheKey = new CacheKey(key, "value", timeout, TimeUnit.SECONDS);
		
		mqStatusInspectorHandler.setStatus(cacheKey);
		
		Thread.sleep(2000);
		
		assertTrue(mqStatusInspectorHandler.needRetrySendRequest(cacheKey.getKey()));
	}

}
