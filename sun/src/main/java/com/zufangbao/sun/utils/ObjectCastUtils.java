/**
 * 
 */
package com.zufangbao.sun.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang.StringUtils;


/**
 * @author wukai
 *
 */
public class ObjectCastUtils {
	
	public static final Map<String,Object> DEFAULT_CAST_VALUE_MAP = new HashMap<String,Object>(){{
		
		put(String.class.getName(),StringUtils.EMPTY);
		put(Integer.class.getName(),0);
		put(Double.class.getName(),0D);
		
	}};
	public static final HashMap<String, BiFunction<Object,Object,Object>> PARSE_FUNCTION_MAP= new HashMap<String, BiFunction<Object,Object,Object>>()
	{{
		put(Integer.class.getName(),(Object waitingCastObj,Object ignoreObj )->new Integer(Integer.parseInt(waitingCastObj.toString())));
		put(Double.class.getName(),(Object waitingCastObj,Object ignoreObj )->new Double(Double.parseDouble(waitingCastObj.toString())));
		put(String.class.getName(),(Object waitingCastObj,Object ignoreObj )->waitingCastObj.toString());
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
	@SuppressWarnings("finally")
	public static <T> T cast(Object obj,Class<T> clazz){
			
		BiFunction<Object, Object, Object> parseFunction = PARSE_FUNCTION_MAP.get(clazz.getName());
		
		Object rtnObject = parseFunction.apply(obj,null);
		
		return (T) rtnObject;
	}
	@SuppressWarnings("finally")
	private static <T> T cast(Object obj,Class<T> clazz,Object defaultValue){

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
