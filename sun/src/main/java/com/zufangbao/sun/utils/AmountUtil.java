package com.zufangbao.sun.utils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 金额相关工具类
 * @author zhanghongbing
 *
 */
public class AmountUtil {

	/**
	 * 比较两个BigDecimal类型的数据是否相等
	 */
	public static boolean equals(BigDecimal a, BigDecimal b) {
		if (a == null) {
			return b == null;
		}
		if (b == null) {
			return a == null;
		}
		return a.compareTo(b) == 0;
	}
	
	public  static  BigDecimal  getAmountFromMap( Map<String, BigDecimal> amountMap,String chartString){
		if(amountMap.get(chartString) != null){
			return amountMap.get(chartString);
		}
		return BigDecimal.ZERO; 
	}
	
}
