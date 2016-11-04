package com.zufangbao.gluon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapperImpl;

/**
 * Bean包装工具类
 * @author zhanghongbing
 *
 */
public class BeanWrapperUtil {

	/**
	 * 重新组装Bean数组，只取propertyNames中定义的字段
	 * @param list Bean数组
	 * @param propertyNames 字段名数组
	 * @return 只保留指定字段的数据
	 */
	public static List<Map<String,Object>> wrapperList(List<?> list, String... propertyNames) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Object bean : list) {
			Map<String, Object> result = wrapperMap(bean, propertyNames);
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 重新组装Bean，只取propertyNames中定义的字段
	 * @param bean 实体类
	 * @param propertyNames 字段名数组
	 * @return 只保留指定字段的数据
	 */
	public static Map<String, Object> wrapperMap(Object bean, String... propertyNames) {
		BeanWrapperImpl wrapper = new BeanWrapperImpl(bean);
		Map<String, Object> map = new HashMap<String, Object>();
		for (String propertyName : propertyNames) {
			Object propertyValue = wrapper.getPropertyValue(propertyName);
			map.put(propertyName, propertyValue);
		}
		return map;
	}
}
