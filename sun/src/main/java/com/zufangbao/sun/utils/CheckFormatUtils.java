package com.zufangbao.sun.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CheckFormatUtils {

	public static boolean checkRMBCurrency(String content) {

		if(StringUtils.isEmpty(content)){
			return false;
		}
		try{
		BigDecimal currency= new BigDecimal(content);
		if(currency!=null&&currency.compareTo(BigDecimal.ZERO)>=0) return true;
		else
			return false;
		}catch(Exception e)
		{
			return false;
		}
		
	}
	
	public  static Map<String,BigDecimal> getAllAmountsFields(Object obj)
	{
		HashMap<String,BigDecimal> amountList=new HashMap();
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
		for(Field field:fields)
		{
			field.setAccessible(true);
			Object value=field.get(obj);
			
			
			ValidatorFieldType annotation = field.getAnnotation(ValidatorFieldType.class);
			if(annotation!=null)
			{
				if(annotation.value().equals("BigDecimal")==true)
				{
					if(value==null)
						amountList.put(field.getName(),null);
					else if(value instanceof String )
						amountList.put(field.getName(),new BigDecimal((String)value));
					else if(value instanceof BigDecimal )
						amountList.put(field.getName(),(BigDecimal) value);
				}
			}
		}
		return amountList;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Collections.EMPTY_MAP;
		}
	}
	
	public static boolean checkRMBCurrencyBigDecimal(BigDecimal currency) {

		if(currency!=null&& currency.compareTo(BigDecimal.ZERO) >= 0) return true;
		else
			return false;
		
	}

	private static boolean match(String content, String regex) {
		if (!content.matches(regex)) {
			return false;
		}
		return true;
	}

	public static boolean checkChineseName(String content) {
		if(StringUtils.isEmpty(content)){
			return false;
		}
		String regex = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
		return match(content, regex);
	}

	public  static  boolean checkIDCard(String content)
	{
		if(StringUtils.isEmpty(content)){
			return false;
		}
		String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		return match(content, regex);
	}
	public static  boolean checkPercent(String content){
		if(StringUtils.isEmpty(content)){
			return false;
		}
		String regex = "^\\d+\\.?\\d*\\%?$";
		return match(content, regex);
	}

}
