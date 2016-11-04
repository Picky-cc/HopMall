package com.zufangbao.sun.service;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.entity.directbank.KeyType;
import com.zufangbao.sun.entity.directbank.USBKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
public class USBKeyServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private USBKeyService usbKeyService;

	@Test
	@Sql("classpath:test/yunxin/directbank/testGetUSBKeyByBankCode.sql")
	public void testGetUSBKeyByBankCode() {

		USBKey USBKey = usbKeyService.getUSBKeyByBankCode("CMB");

		Assert.assertEquals("CMB", USBKey.getBankCode());
		Assert.assertEquals("44bcf65b-c138-48f4-afd5-929bbdd979ee",
				USBKey.getUuid());
		Assert.assertEquals(KeyType.Entity, USBKey.getKeyType());

		Map<String, String> config = USBKey.getConfig();

		Assert.assertEquals("管知时suidifu", config.get("LGNNAM").toString());
		Assert.assertEquals("http://cmb.zufangbao.cn:25662", config.get("URL")
				.toString());
		Assert.assertEquals("GetTransInfo", config.get("GetTransInfo_Code")
				.toString());
		Assert.assertEquals("GetAccInfo", config.get("GetAccInfo_Code")
				.toString());
		Assert.assertEquals("GetPaymentInfo", config.get("GetPaymentInfo_Code")
				.toString());
		Assert.assertEquals("DCPAYMNT", config.get("DCPAYMNT_Code").toString());
		Assert.assertEquals("N02031", config.get("BUSCOD").toString());
		Assert.assertEquals("00002", config.get("BUSMOD").toString());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/directbank/testGetUSBKeyByBankCode.sql")
	public void testGetUSBKeyByUUID() {

		USBKey USBKey = usbKeyService.getUSBKeyByUUID("44bcf65b-c138-48f4-afd5-929bbdd979ee");

		Assert.assertEquals("CMB", USBKey.getBankCode());
		Assert.assertEquals("44bcf65b-c138-48f4-afd5-929bbdd979ee",
				USBKey.getUuid());
		Assert.assertEquals(KeyType.Entity, USBKey.getKeyType());

		Map<String, String> config = USBKey.getConfig();

		Assert.assertEquals("管知时suidifu", config.get("LGNNAM").toString());
		Assert.assertEquals("http://cmb.zufangbao.cn:25662", config.get("URL")
				.toString());
		Assert.assertEquals("GetTransInfo", config.get("GetTransInfo_Code")
				.toString());
		Assert.assertEquals("GetAccInfo", config.get("GetAccInfo_Code")
				.toString());
		Assert.assertEquals("GetPaymentInfo", config.get("GetPaymentInfo_Code")
				.toString());
		Assert.assertEquals("DCPAYMNT", config.get("DCPAYMNT_Code").toString());
		Assert.assertEquals("N02031", config.get("BUSCOD").toString());
		Assert.assertEquals("00002", config.get("BUSMOD").toString());
	}
	

	// @Test
	// public void testGetUUID() {
	// System.out.println(UUID.randomUUID());
	// }
}
