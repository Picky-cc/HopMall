/**
 * 
 */
package com.suidifu.hathaway.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;


/**
 * @author wukai
 *
 */
public class ObjectCastUtils {
	
	public static final Map<String,Object> DEFAULT_CAST_VALUE_MAP = new HashMap<String,Object>(){{
		
		put(String.class.getName(),StringUtils.EMPTY);
		put(Integer.class.getName(),0);
		put(Double.class.getName(),0D);
		put(BigDecimal.class.getName(),new BigDecimal("-1"));
		
	}};
	public static final HashMap<String, BiFunction<Object,Object,Object>> PARSE_FUNCTION_MAP= new HashMap<String, BiFunction<Object,Object,Object>>()
	{{
		put(Integer.class.getName(),(Object waitingCastObj,Object ignoreObj )->new Integer(Integer.parseInt(waitingCastObj.toString())));
		put(Double.class.getName(),(Object waitingCastObj,Object ignoreObj )->new Double(Double.parseDouble(waitingCastObj.toString())));
		put(String.class.getName(),(Object waitingCastObj,Object ignoreObj )->waitingCastObj.toString());
		put(BigDecimal.class.getName(),(Object waitingCastObj,Object ignoreObj )-> new BigDecimal(waitingCastObj.toString()));
		put(Date.class.getName(),(Object waitingCastObj,Object ignoreObj )->waitingCastObj== null ? null:DateUtils.parseDate(waitingCastObj.toString(),"yyyy-MM-dd"));
	}};
	
	public static String castToString(Object obj){
		
		return cast(obj, String.class,null);
	}
	public static String castToString(Object obj,String defaultValue){
		
		return cast(obj, String.class,defaultValue);
	}
	public static int castToInteger(Object obj){
		
		return cast(obj, Integer.class,null);
	}
	public static int castToInteger(Object obj,Integer defaultValue){
		return cast(obj, Integer.class,defaultValue);
	}
	public static double castToDouble(Object obj){
		return cast(obj, Double.class,null);
	}
	public static double castToDouble(Object obj,Double defalutValue){
		return cast(obj, Double.class,defalutValue);
	}
	public static BigDecimal castToBigDecimal(Object obj){
		return cast(obj,BigDecimal.class);
	}
	@SuppressWarnings("finally")
	public static <T> T cast(Object obj,Class<T> clazz){
		
		BiFunction<Object, Object, Object> parseFunction = PARSE_FUNCTION_MAP.get(clazz.getName());
		
		Object rtnObject = parseFunction.apply(obj,null);
		
		return (T) rtnObject;
	}
	@SuppressWarnings("finally")
	public static <T> T cast(Object obj,Class<T> clazz,Object defaultValue){

		try{
			
			return cast(obj, clazz);
			
		}catch(Exception e){
			
			if(null == defaultValue){
				
				return (T)DEFAULT_CAST_VALUE_MAP.get(clazz.getName());
			}
			
			try{
				
				return cast(defaultValue, clazz);
				
			}catch(Exception ee){
				
				return (T)DEFAULT_CAST_VALUE_MAP.get(clazz.getName());
			}
		}

	}
}
