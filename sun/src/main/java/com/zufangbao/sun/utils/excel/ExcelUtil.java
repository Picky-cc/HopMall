package com.zufangbao.sun.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.utils.XSSFUtils;

/*
 * ExcelUtil工具类实现功能:
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 */
public class ExcelUtil<T> {
	Class<T> clazz;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> importExcel(int sheetIndex, InputStream input) {
		int maxCol = 0;
		List<T> list = new ArrayList<T>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(input);
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			/*
			 * if (!sheetIndex.trim().equals("")) { sheet =
			 * workbook.getSheet(sheetIndex);// 如果指定sheet名,则取指定sheet中的内容. }
			 */
			if (sheet == null) {
				sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
			}
			int rows = sheet.getPhysicalNumberOfRows();

			if (rows > 0) {// 有数据时才处理
				// Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
				List<Field> allFields = getMappedFiled(clazz, null);

				Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
				for (Field field : allFields) {
					// 将有注解的field存放到map中.
					if (field.isAnnotationPresent(ExcelVoAttribute.class)) {
						ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
						int col = getExcelCol(attr.column());// 获得列号
						maxCol = Math.max(col, maxCol);
						// System.out.println(col + "====" + field.getName());
						field.setAccessible(true);// 设置类的私有字段属性可访问.
						fieldsMap.put(col, field);
					}
				}
				for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.
					HSSFRow row = sheet.getRow(i);
					// int cellNum = row.getPhysicalNumberOfCells();
					// int cellNum = row.getLastCellNum();
					int cellNum = maxCol;
					T entity = null;
					for (int j = 0; j <= cellNum; j++) {
						HSSFCell cell = row.getCell(j);
						if (cell == null) {
							continue;
						}
						// int cellType = cell.getCellType();
						String c = "";
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							c = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								c = DateUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
							} else {
								c = String.valueOf(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							c = String.valueOf(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							c = cell.getCellFormula();
							break;
						}
						if (c == null || c.equals("")) {
							continue;
						}
						entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
						Field field = fieldsMap.get(j);// 从map中得到对应列的field.
						if (field == null) {
							continue;
						}
						// 取得类型,并根据对象类型设置值.
						Class<?> fieldType = field.getType();
						if (String.class == fieldType) {
							field.set(entity, String.valueOf(c));
						} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
							field.set(entity, Integer.parseInt(c));
						} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
							field.set(entity, Long.valueOf(c));
						} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
							field.set(entity, Float.valueOf(c));
						} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
							field.set(entity, Short.valueOf(c));
						} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
							field.set(entity, Double.valueOf(c));
						} else if (Character.TYPE == fieldType) {
							if ((c != null) && (c.length() > 0)) {
								field.set(entity, Character.valueOf(c.charAt(0)));
							}
						}

					}
					if (entity != null) {
						list.add(entity);
					}
				}
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<T> importExcelHighVersion(int sheetIndex, Workbook workbook) {
		try {
			List<T> list = new ArrayList<T>();
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null || sheet.getPhysicalNumberOfRows() <= 0) {
				return Collections.emptyList();
			}
			Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
			int maxCol = 0;
			List<Field> allFields = getMappedFiled(clazz, null);
			Row firstRow = sheet.getRow(0);
			for (Field field : allFields) {
				if (field.isAnnotationPresent(ExcelVoAttribute.class)) {
					ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
					int col = getExcelCol(attr.column());// 获得列号
					Cell cell = firstRow.getCell(col);
					String cellValue = XSSFUtils.getCellContent(cell);
					if (!cellValue.equals(attr.name())) {
						throw new Exception();
					}
					maxCol = Math.max(col, maxCol);
					field.setAccessible(true);// 设置类的私有字段属性可访问.
					fieldsMap.put(col, field);
				}
			}
			int rows = sheet.getPhysicalNumberOfRows();
			for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.
				Row row = sheet.getRow(i);
				int cellNum = maxCol;
				T entity = null;
				for (int j = 0; j <= cellNum; j++) {
					Cell cell = row.getCell(j);
					String c = XSSFUtils.getCellContent(cell);
					if (StringUtils.isEmpty(c)) {
						continue;
					}
					entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
					Field field = fieldsMap.get(j);// 从map中得到对应列的field.
					if (field == null) {
						continue;
					}
					// 取得类型,并根据对象类型设置值.
					Class<?> fieldType = field.getType();
					if (String.class == fieldType) {
						field.set(entity, String.valueOf(c));
					} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
						field.set(entity, Integer.parseInt(c));
					} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
						field.set(entity, Long.valueOf(c));
					} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
						field.set(entity, Float.valueOf(c));
					} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
						field.set(entity, Short.valueOf(c));
					} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
						field.set(entity, Double.valueOf(c));
					} else if (Character.TYPE == fieldType) {
						if ((c != null) && (c.length() > 0)) {
							field.set(entity, Character.valueOf(c.charAt(0)));
						}
					}
				}
				if (entity != null) {
					list.add(entity);
				}
			}
			return list;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * 对list数据源将其里面的数据导入到excel表单
	 * 
	 * @param sheetName
	 *            工作表的名称
	 * @param output
	 *            java输出流
	 */
	public boolean exportExcel(List<T> lists[], String sheetNames[], OutputStream output) {
		if (lists.length != sheetNames.length) {
			System.out.println("数组长度不一致");
			return false;
		}

		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象

		for (int ii = 0; ii < lists.length; ii++) {
			List<T> list = lists[ii];
			String sheetName = sheetNames[ii];

			List<Field> fields = getMappedFiled(clazz, null);

			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象

			workbook.setSheetName(ii, sheetName);

			HSSFRow row;
			HSSFCell cell;// 产生单元格
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
				int col = getExcelCol(attr.column());// 获得列号
				cell = row.createCell(col);// 创建列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
				cell.setCellValue(attr.name());// 写入列名

				// 如果设置了提示信息则鼠标放上去提示.
				if (!attr.prompt().trim().equals("")) {
					setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
				}
				// 如果设置了combo属性则本列只能选择不能输入
				if (attr.combo().length > 0) {
					setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
				}
				cell.setCellStyle(style);
			}

			int startNo = 0;
			int endNo = list.size();
			// 写入各条记录,每条记录对应excel表中的一行
			for (int i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				T vo = (T) list.get(i); // 得到导出对象.
				for (int j = 0; j < fields.size(); j++) {
					Field field = fields.get(j);// 获得field.
					field.setAccessible(true);// 设置实体类私有属性可访问
					ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
					try {
						// 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
						if (attr.isExport()) {
							cell = row.createCell(getExcelCol(attr.column()));// 创建cell
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			output.flush();
			workbook.write(output);
			output.close();
			workbook.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed ");
			return false;
		}

	}

	public HSSFWorkbook exportDatasToHSSFWorkbook(List<T> lists[], String sheetNames[]) {
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		return exportDatasToHSSFWorkbook(workbook, lists, sheetNames);
	}
	
	
	public String exportOneRowDatasToOneRowCSV(T data)
	{
		List<Field> fields = getMappedFiled(clazz, null);
		
		String OneRowCSV = "";
		for (int j = 0; j < fields.size(); j++) {
			Field field = fields.get(j);// 获得field.
			field.setAccessible(true);// 设置实体类私有属性可访问
			ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
			try {
				if (attr.isExport()) {
					String fieldStr = field.get(data) == null ? "" : String.valueOf(field.get(data));
					OneRowCSV += fieldStr + ",";
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		OneRowCSV = OneRowCSV.substring(0, OneRowCSV.length());
		return OneRowCSV;
		
	}
	public String exportDatasToCSVHeader()
	{
		List<Field> fields = getMappedFiled(clazz, null);
		String ColumnStr = "";
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
			String name = attr.name();
			ColumnStr += name + ",";
		}
		ColumnStr = ColumnStr.substring(0, ColumnStr.length());
		return ColumnStr;
		
	}
	
	public List<String> exportDatasToCSV(List<T> list) {
		List<String> CSVdata = new ArrayList<String>();
		if(CollectionUtils.isEmpty(list)) {
			Collections.emptyList();
		}
		String ColumnStr = exportDatasToCSVHeader();
		
		CSVdata.add(ColumnStr);
		// 写入各条记录,每条记录对应excel表中的一行
		for (T vo : list) {
			String OneRowCSV = exportOneRowDatasToOneRowCSV(vo);
			CSVdata.add(OneRowCSV);
		}
		return CSVdata;
	}


	public HSSFWorkbook exportDatasToHSSFWorkbook(HSSFWorkbook workbook, List<T> lists[], String sheetNames[]) {
		if (lists.length != sheetNames.length) {
			System.out.println("数组长度不一致");
			return null;
		}

		for (int ii = 0; ii < lists.length; ii++) {
			List<T> list = lists[ii];
			if (list.size() == 0) {
				continue;
			}
			String sheetName = sheetNames[ii];

			List<Field> fields = getMappedFiled(clazz, null);

			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象

			workbook.setSheetName(ii, sheetName);

			HSSFRow row;
			HSSFCell cell;// 产生单元格
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFCellStyle fontstyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
				int col = getExcelCol(attr.column());// 获得列号
				cell = row.createCell(col);// 创建列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
				cell.setCellValue(attr.name());// 写入列名
				cell.setCellStyle(style);
				// 如果设置了提示信息则鼠标放上去提示.
				if (!attr.prompt().trim().equals("")) {
					setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
				}
				// 如果设置了combo属性则本列只能选择不能输入
				if (attr.combo().length > 0) {
					setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
				}
				if (attr.color().equals(Constant.COLUMN_COLOR_RED)) {
					cell = row.createCell(col);// 创建列
					font.setColor(HSSFColor.RED.index);// HSSFColor.VIOLET.index
														// //字体颜色
					fontstyle.setFont(font);
					cell = row.createCell(getExcelCol(attr.column()));// 创建cell
					cell.setCellStyle(fontstyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(attr.name());
				}
				if (attr.color().equals(Constant.COlUMN_COLOR_GREEN)) {
					cell = row.createCell(getExcelCol(attr.column()));// 创建cell
					font.setColor(HSSFColor.GREEN.index);// HSSFColor.VIOLET.index
															// //字体颜色
					fontstyle.setFont(font);
					cell.setCellStyle(fontstyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(attr.name());
				}
				sheet.autoSizeColumn(i);
			}
			int startNo = 0;

			int endNo = list.size();
			// 写入各条记录,每条记录对应excel表中的一行
			for (int i = 0; i < endNo; i++) {
				startNo = i + 1;
				row = sheet.createRow(startNo);
				T vo = (T) list.get(i); // 得到导出对象.
				for (int j = 0; j < fields.size(); j++) {
					Field field = fields.get(j);// 获得field.
					field.setAccessible(true);// 设置实体类私有属性可访问
					ExcelVoAttribute attr = field.getAnnotation(ExcelVoAttribute.class);
					try {
						// 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
						if (attr.isExport()) {
							cell = row.createCell(getExcelCol(attr.column()));// 创建cell
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				for (int j = 0; j < fields.size(); j++) {
					sheet.autoSizeColumn(i);
				}
			}
		}
		return workbook;
	}

	/**
	 * 对list数据源将其里面的数据导入到excel表单
	 * 
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个sheet中数据的行数,此数值必须小于65536
	 * @param output
	 *            java输出流
	 */
	public boolean exportExcel(List<T> list, String sheetName, OutputStream output) {
		@SuppressWarnings("unchecked")
		List<T>[] lists = new ArrayList[1];
		lists[0] = list;

		String[] sheetNames = new String[1];
		sheetNames[0] = sheetName;

		return exportExcel(lists, sheetNames, output);
	}

	/**
	 * 生成单个sheet excel文件 dongjigong
	 * 
	 * @param list
	 * @param sheetName
	 * @return
	 */
	public HSSFWorkbook exportDataToHSSFWork(List<T> list, String sheetName) {
		@SuppressWarnings("unchecked")
		List<T>[] lists = new ArrayList[1];
		lists[0] = list;

		String[] sheetNames = new String[1];
		sheetNames[0] = sheetName;

		return exportDatasToHSSFWorkbook(lists, sheetNames);
	}

	/**
	 * 基于已有的excel生成
	 * 
	 * @param workBook
	 * @param list
	 * @param sheetName
	 * @return
	 */
	public HSSFWorkbook exportDataToHSSFWork(HSSFWorkbook workBook, List<T> list, String sheetName) {
		@SuppressWarnings("unchecked")
		List<T>[] lists = new ArrayList[2];
		lists[1] = list;
		lists[0] = new ArrayList<T>();

		String[] sheetNames = new String[2];
		sheetNames[1] = sheetName;

		return exportDatasToHSSFWorkbook(workBook, lists, sheetNames);
	}

	/**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 * 
	 * @param col
	 */
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**
	 * 设置单元格上提示
	 * 
	 * @param sheet
	 *            要设置的sheet.
	 * @param promptTitle
	 *            标题
	 * @param promptContent
	 *            内容
	 * @param firstRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param endCol
	 *            结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
			int endRow, int firstCol, int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * 
	 * @param sheet
	 *            要设置的sheet.
	 * @param textlist
	 *            下拉框显示的内容
	 * @param firstRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param endCol
	 *            结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
			int firstCol, int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/**
	 * 得到实体类所有通过注解映射了数据表的字段
	 * 
	 * @param map
	 * @return
	 */
	private List<Field> getMappedFiled(Class<? super T> class1, List<Field> fields) {
		if (fields == null) {
			fields = new ArrayList<Field>();
		}

		Field[] allFields = class1.getDeclaredFields();// 得到所有定义字段
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelVoAttribute.class)) {
				fields.add(field);
			}
		}
		if (class1.getSuperclass() != null && !class1.getSuperclass().equals(Object.class)) {
			getMappedFiled(class1.getSuperclass(), fields);
		}

		return fields;
	}

}