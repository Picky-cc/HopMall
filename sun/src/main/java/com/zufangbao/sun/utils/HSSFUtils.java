/**
 * 
 */
package com.zufangbao.sun.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.demo2do.core.utils.DateUtils;

/**
 * @author wk
 *
 */
public class HSSFUtils {

	/**
	 * read file via fileName
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static HSSFWorkbook readFile(String fileName) throws FileNotFoundException, IOException{
		return new HSSFWorkbook(new FileInputStream(fileName));
	}
	public static String getCellContent(HSSFCell cell){
		
		String value = "";
		if(null == cell)
			return value;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			double result = cell.getNumericCellValue();
			if(getFormatDateStr(cell) != null){
				return getFormatDateStr(cell);
			}
			value = String.valueOf(result);
			return value;
		}
		if(cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
			value = cell.getBooleanCellValue() ? "是" :"否";
			return value;
		}
		if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
			value = cell.getCellFormula();
			Date date = new Date(Long.valueOf(value));
			return DateUtils.format(date, "yyyy-MM-dd");
		}
		value = cell.getStringCellValue();
		return value;
	}
	private static String getFormatDateStr(HSSFCell cell){
		
		if(HSSFDateUtil.isCellDateFormatted(cell)){
			Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			return DateUtils.format(date, "yyyy-MM-dd");
			}
		return null;
	}
}
