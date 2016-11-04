package com.zufangbao.earth.yunxin.excel;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.utils.HSSFUtils;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml", })
public class SettlementOrderExcelTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SettlementOrderForExportExcel settlementOrderForExportExcel;

	@Test
	@Sql("classpath:test/testQuerySettlementOrderExcel.sql")
	public void testQuerySettlementOrderExcel() {

		MockHttpServletResponse response = new MockHttpServletResponse();
		SettlementOrderQueryModel settlementOrderQueryModel = new SettlementOrderQueryModel("[1]",
				0, "qs123456", "DKHD-001-01", "anmeitu");
		HSSFWorkbook workBook = settlementOrderForExportExcel
				.createExcel(settlementOrderQueryModel);
		
		
		HSSFSheet sheet = workBook.getSheetAt(0);
		HSSFRow  row1 = sheet.getRow(1);
		Assert.assertEquals(new String("qs123456"), HSSFUtils.getCellContent(row1.getCell(0)));
		Assert.assertEquals(new String("DKHD-001-01"), HSSFUtils.getCellContent(row1.getCell(1)));
		Assert.assertEquals(new String("2016-04-08"), HSSFUtils.getCellContent(row1.getCell(2)));
		Assert.assertEquals(new String("2016-04-18"), HSSFUtils.getCellContent(row1.getCell(3)));
		Assert.assertEquals(new String("anmeitu"), HSSFUtils.getCellContent(row1.getCell(4)));
		Assert.assertEquals(new String("15976.15"), HSSFUtils.getCellContent(row1.getCell(5)));
		Assert.assertEquals(new String("0"), HSSFUtils.getCellContent(row1.getCell(6)));
		Assert.assertEquals(new String("0.00"), HSSFUtils.getCellContent(row1.getCell(7)));
		Assert.assertEquals(new String("2016-04-13 16:45:00"), HSSFUtils.getCellContent(row1.getCell(8)));
		Assert.assertEquals(new String("0.00"), HSSFUtils.getCellContent(row1.getCell(9)));
		Assert.assertEquals(new String("未发生"), HSSFUtils.getCellContent(row1.getCell(10)));
		Assert.assertEquals(new String(""), HSSFUtils.getCellContent(row1.getCell(11)));
		
		
		

	}

}
