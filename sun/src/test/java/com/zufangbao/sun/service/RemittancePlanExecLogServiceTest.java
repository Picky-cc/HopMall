package com.zufangbao.sun.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml", "classpath:/local/applicationContext-*.xml"})
public class RemittancePlanExecLogServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private IRemittancePlanExecLogService remittancePlanExecLogService;
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittancePlanExecLogService.sql")
	public void testQueryRemittancePlanExecLog_get_RemittancePlanExecLog_Ids_ForReverse(){
		List<Long> list = remittancePlanExecLogService.getRemittancePlanExecLogIdsForReverse();
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(new Long(1), list.get(0)); 
		Assert.assertEquals(new Long(3), list.get(1)); 
	}
}	
