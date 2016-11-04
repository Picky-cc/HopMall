package com.zufangbao.sun.utils;

import java.util.ArrayList;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;


/**
 * 枚举类工具类
 * @author zhanghongbing
 *
 */
public class EnumUtil {

	/**
	 * 通过序数，返回指定枚举类的枚举值
	 * @param clazz 枚举类
	 * @param ordinal 序数
	 * @return 枚举值
	 */
	public static <T> T fromOrdinal(Class<T> clazz, Integer ordinal) {
		// 非枚举类，返回空
		if (!clazz.isEnum()) {
			return null;
		}
		T[] values = clazz.getEnumConstants();
		if (ordinal==null || ordinal < 0 || ordinal >= values.length) {
			return null;
		}
		return values[ordinal];
	}

	/**
	 * 通过序数的数组，返回指定枚举类的值集合
	 * @param clazz 枚举类
	 * @param ordinals 数组的字符串 eg：["1", "2", "3"]
	 * @return 枚举值集合
	 */
	public static <T> List<T> fromOrdinals(Class<T> clazz, String ordinals){
		List<Integer> ordinalList = JsonUtils.parseArray(ordinals, Integer.class);
		if (ordinals == null) {
			return null;
		}
		List<T> returnEnumList = new ArrayList<T>();
		for (Integer ordinal : ordinalList) {
			if(ordinal == null) continue;
			T item = fromOrdinal(clazz, ordinal);
			if(item == null) continue;
			returnEnumList.add(item);
		}
		return returnEnumList;
	}
	
}
