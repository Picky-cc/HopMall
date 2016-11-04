package com.zufangbao.sun.yunxin.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplateEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class SmsQueneServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private SmsQueneService smsQueneService;
	
	@Test
	@Sql("classpath:test/yunxin/sms/GetAllNeedSendSmsQuene.sql")
	public void testGetAllNeedSendSmsQuene() {
		List<SmsQuene> allNeedSendSmsQuene = smsQueneService.getAllNeedSendSmsQuene();
		Assert.assertEquals(1, allNeedSendSmsQuene.size());
		
		SmsQuene smsQuene = allNeedSendSmsQuene.get(0);
		Assert.assertEquals(1, smsQuene.getId().longValue());
		Assert.assertNull(smsQuene.getRequestTime());
		Assert.assertNull(smsQuene.getResponseTime());
		Assert.assertNull(smsQuene.getResponseTxt());
	}
	
	@Test
	@Sql("classpath:test/yunxin/sms/GetAllNeedSendSmsQuene_No_Result.sql")
	public void testGetAllNeedSendSmsQuene_No_Result() {
		List<SmsQuene> allNeedSendSmsQuene = smsQueneService.getAllNeedSendSmsQuene();
		Assert.assertTrue(CollectionUtils.isEmpty(allNeedSendSmsQuene));
	}
	
	@Test
	@Sql("classpath:test/yunxin/sms/SendSuccSms.sql")
	public void testSendSuccSms() {
		smsQueneService.sendSuccSms();
		List<SmsQuene> all = smsQueneService.list(SmsQuene.class, new Filter());
		
		SmsQuene fail_smsQuene = all.get(0);
		Assert.assertFalse(fail_smsQuene.isAllowedSend());
		Assert.assertEquals(SmsTemplateEnum.LOAN_REPAY_FAIL.getCode(), fail_smsQuene.getTemplateCode());
		SmsQuene succ_smsQuene = all.get(1);
		Assert.assertTrue(succ_smsQuene.isAllowedSend());
		Assert.assertEquals(SmsTemplateEnum.LOAN_REPAY_SUCC.getCode(), succ_smsQuene.getTemplateCode());
	}

	@Test
	@Sql("classpath:test/yunxin/sms/DeleteNotSuccSms.sql")
	public void testDeleteNotSuccSms() {
		smsQueneService.deleteNotSuccSms();
		List<SmsQuene> all = smsQueneService.loadAll(SmsQuene.class);
		Assert.assertEquals(1, all.size());
		
		SmsQuene succ_smsQuene = all.get(0);
		Assert.assertEquals(SmsTemplateEnum.LOAN_REPAY_SUCC.getCode(), succ_smsQuene.getTemplateCode());
		Assert.assertEquals(3, succ_smsQuene.getId().intValue());
	}
	
}
