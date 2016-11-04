package com.zufangbao.earth.yunxin.service;

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

import com.zufangbao.sun.yunxin.entity.Dictionary;
import com.zufangbao.sun.yunxin.entity.DictionaryCode;
import com.zufangbao.sun.yunxin.exception.DictionaryNotExsitException;
import com.zufangbao.sun.yunxin.service.DictionaryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class DictionaryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DictionaryService dictionaryService;
	
	@Test
	@Sql("classpath:test/yunxin/dictionary/GetDictionaryByCode.sql")
	public void testGetDictionaryByCode() {
		String content = "http://192.168.24.52:86/SendSMSFor5D/Send";
		String code = DictionaryCode.SMS_URL.getCode();
		Dictionary dictionary = null;
		try {
			dictionary = dictionaryService.getDictionaryByCode(code);
		} catch (DictionaryNotExsitException e) {
			Assert.fail();
		}
		Assert.assertNotNull(dictionary);
		Assert.assertEquals(code, dictionary.getCode());
		Assert.assertEquals(content, dictionary.getContent());
	}
	
	@Test
	@Sql("classpath:test/yunxin/dictionary/GetDictionaryByCode_Null.sql")
	public void testGetDictionaryByCode_Null() {
		List<Dictionary> all = dictionaryService.loadAll(Dictionary.class);
		Assert.assertTrue(CollectionUtils.isEmpty(all));
		Dictionary dictionary = null;
		try {
			dictionary = dictionaryService.getDictionaryByCode("");
			Assert.fail();
		} catch (DictionaryNotExsitException e) {
			Assert.assertNull(dictionary);
		}
	}
}