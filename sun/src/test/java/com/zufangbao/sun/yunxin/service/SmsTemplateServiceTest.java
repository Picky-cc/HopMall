package com.zufangbao.sun.yunxin.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class SmsTemplateServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@Test
	@Sql("classpath:test/yunxin/sms/GetSmsTemplateByCode.sql")
	public void testGetSmsTemplateByCode() {
		SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateByCode("YX-NFQ-SUCC");
		Assert.assertNotNull(smsTemplate);
		Assert.assertEquals("YX-NFQ-SUCC", smsTemplate.getCode());
	}
	
	@Test
	@Sql("classpath:test/yunxin/sms/GetSmsTemplateByCode.sql")
	public void testGetSmsTemplateByCode_Null() {
		SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateByCode("Null");
		Assert.assertNull(smsTemplate);
	}
}
