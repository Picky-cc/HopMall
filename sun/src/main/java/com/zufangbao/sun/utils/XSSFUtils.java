package com.zufangbao.sun.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.demo2do.core.utils.DateUtils;

public class XSSFUtils {
	/**
	 * read file via fileName
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook readFile(String fileName)
			throws InvalidFormatException, IOException {
		return WorkbookFactory.create(new FileInputStream(fileName));
	}

	public static String getCellContent(Cell cell) {

		String value = "";
		if (null == cell)
			return value;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = DateUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
			} else {
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		default: value = "";
		}
		return value;
	}

	private static String getFormatDateStr(Cell cell) {

		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			return DateUtils.format(date, "yyyy-MM-dd");
		}
		return null;
	}
}