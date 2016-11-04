package com.zufangbao.earth.yunxin.api.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.zufangbao.earth.yunxin.api.model.ApiResult;
import com.zufangbao.sun.utils.StringUtils;

/**
 * 接口响应信息工具类
 * @author zhanghongbing
 *
 */
public class ApiMessageUtil {
	
	private static Properties props = new Properties();

	static{
		try {
			props = PropertiesLoaderUtils.loadAllProperties("ApiMessage.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getMessage(int code) {
		return props.getProperty(String.valueOf(code), "");
	}
	
	public static ApiResult getResponseResult(int code){
		return getResponseResult(code, null);
	}
	
	public static ApiResult getResponseResult(int code, String message) {
		ApiResult result = new ApiResult();
		result.setCode(code);
		message = StringUtils.isEmpty(message) ? getMessage(code) : message;
		result.setMessage(message);
		return result;
	}
	
}
