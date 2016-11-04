package com.zufangbao.sun.utils;

import java.math.BigDecimal;

public class StringUtils extends com.demo2do.core.utils.StringUtils {
	private final static String DECIMAL_POINT = ".";

	/**
	 * 去除小数点及其之后的内容
	 * 
	 * @param str
	 *            待处理的数据
	 * @return if str is empty return str;if str has decimal point, return
	 *         content before decimal point, else return str;
	 */
	public static String removeDecimal(String str) {
		if (isEmpty(str)) {
			return str;
		}
		if (hasDecimalPoint(str)) {
			return str.substring(0, str.indexOf(DECIMAL_POINT));
		}
		return str;
	}

	private static boolean hasDecimalPoint(String str) {
		return (str.indexOf(DECIMAL_POINT) != -1);
	}

	public static String defaultString(BigDecimal bigDecimal){
		if(bigDecimal==null){
			return "0.00";
		}
		return bigDecimal.toString();
	}


}
