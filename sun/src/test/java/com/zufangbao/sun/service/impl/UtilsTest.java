package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.FinanceUtils;

public class UtilsTest {

	@Test
	public void testConvert_yuan_to_cent_null() {
		Assert.assertEquals("0", FinanceUtils.convert_yuan_to_cent(null));
	}

	@Test
	public void testConvert_yuan_to_cent() {
		Assert.assertEquals("100",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("1.00")));
	}

	@Test
	public void testConvert_yuan_to_cent2() {
		Assert.assertEquals("100",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("1")));
	}

	@Test
	public void testConvert_yuan_to_cent3() {
		Assert.assertEquals("10.1",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("0.101")));
	}

	@Test
	public void testConvert_yuan_to_cent4() {
		Assert.assertEquals("0",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("0")));
	}

	@Test
	public void testConvert_yuan_to_cent5() {
		Assert.assertEquals("1",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("0.01")));
	}

	@Test
	public void testConvert_yuan_to_cent6() {
		Assert.assertEquals("101",
				FinanceUtils.convert_yuan_to_cent(new BigDecimal("1.01")));
	}

	@Test
	public void testConvert_cent_to_yuan1() {
		Assert.assertEquals(new BigDecimal("0.01"),
				FinanceUtils.convert_cent_to_yuan(new BigDecimal("1")));
	}

	@Test
	public void testConvert_cent_to_yuan2() {
		Assert.assertEquals(new BigDecimal("1.00"),
				FinanceUtils.convert_cent_to_yuan(new BigDecimal("100")));
	}

	@Test
	public void testConvert_cent_to_yuan_null() {
		Assert.assertEquals(new BigDecimal("0.00"),
				FinanceUtils.convert_cent_to_yuan(null));
	}

	@Test
	public void testGetDay() {
		System.out.println(DateUtils.parseDate(
				DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"));
	}
	
	@Test
	public void test(){
		System.out.print(String.format("%-6s", "4014"));
		System.out.println(DateUtils.format(new Date(), "HHmmss"));
	}
	
	@Test
	public void test1(){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("is_path_locked", true);
		config.put("name", "wuxie");

		System.out.println(JsonUtils.toJsonString(config));
		
		String aaa = "{\"is_path_locked\":true,\"name\":\"wuxie\"}";
		
		Map<String, Object> bbb = JsonUtils.parse(aaa);
		System.out.println(bbb.get("is_path_locked").toString());
	}
	
	@Test
	public void testUUID(){
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
	}

}
