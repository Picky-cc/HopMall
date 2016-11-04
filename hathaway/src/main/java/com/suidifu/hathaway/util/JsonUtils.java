/**
 * 
 */
package com.suidifu.hathaway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author wukai
 *
 */
public class JsonUtils extends com.demo2do.core.utils.JsonUtils{

	public static String toJsonString(Object object) {
		
		JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
		
		return JSON.toJSONString(object,SerializerFeature.WriteDateUseDateFormat);
	}
	
}
