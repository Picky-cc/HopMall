package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.sun.service.VirtualAccountFlowService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration
public class VirtualAccountFlowServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private VirtualAccountFlowService virtualAccountFlowService;
	
	@Test
	public void test(){
		assertTrue(true);
	}
}
