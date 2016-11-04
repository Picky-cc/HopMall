package com.zufangbao.sun.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PoiUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCastCellValue() throws IOException {
		
		Resource resource = new ClassPathResource("test/quantum/YLTemplate 2015.11.172fix.xls");
		
		InputStream inputStream = resource.getInputStream();
		
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		HSSFRow row = sheet.getRow(2);
		
		assertEquals("13",PoiUtil.castCellValue(row.getCell(0)));//+
		assertEquals("11.25",PoiUtil.castCellValue(row.getCell(1)));//average
		assertEquals("4.01",PoiUtil.castCellValue(row.getCell(2)));//+.
		assertEquals("2",PoiUtil.castCellValue(row.getCell(3)));//-
		assertEquals("24.33",PoiUtil.castCellValue(row.getCell(4)));
		
	}
	
	@Test
	public void testJSON() {
		String counterBankInfo = "{\"bankName\":\"123\"}";
		JSONObject jsonObject = JSON.parseObject(counterBankInfo );
		String bankName = jsonObject.getString("bankName");
		assertEquals("123", bankName);
	}
}
