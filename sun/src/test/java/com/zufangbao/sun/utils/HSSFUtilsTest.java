/**
 * 
 */
package com.zufangbao.sun.utils;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

/**
 * @author wk
 *
 */
public class HSSFUtilsTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testExcel(){
		String excelFileName = "F:\\baiduyunpan\\git\\zufangbao.projects\\小寓\\测试.xls";
		try {
			HSSFWorkbook workBook = HSSFUtils.readFile(excelFileName);
			for(int i  = 0; i < workBook.getNumberOfSheets(); i++){
				HSSFSheet sheet = workBook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();
				for(int j = 1; j < rows; j++){
					HSSFRow row = sheet.getRow(j);
					int cells = row.getPhysicalNumberOfCells();
						for(int k = 0 ; k <cells; k ++){
							HSSFCell cell = row.getCell(k);
							if(k == 19){
								//HSSFCell.CELL_TYPE_NUMERIC
//								if(HSSFDateUtil.isCellDateFormatted(cell)){
//									Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
//									System.out.println("date:"+ DateUtils.format(date, "yyyy-MM-dd"));
//								}
								String value = HSSFUtils.getCellContent(cell);
								
								double dValue = Double.valueOf(value);
								int ii = (int) dValue;
								System.out.println(value);
								System.out.println("ii=" + ii);
							}
						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCellType(){
		System.out.println(HSSFCell.CELL_TYPE_NUMERIC);
	}

}
