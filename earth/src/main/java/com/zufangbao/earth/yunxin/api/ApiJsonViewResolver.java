package com.zufangbao.earth.yunxin.api;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.model.ApiResult;
import com.zufangbao.earth.yunxin.api.util.ApiMessageUtil;

@Component
public class ApiJsonViewResolver {
	
	public String sucJsonResult() {
		return jsonResult(ApiResponseCode.SUCCESS, null, null, null);
	}
	
	public String sucJsonResult(String message) {
		return jsonResult(ApiResponseCode.SUCCESS, message, null, null);
	}
	
	public String sucJsonResult(Map<String, Object> data) {
		return jsonResult(ApiResponseCode.SUCCESS, null, data);
	}
	
	public String sucJsonResult(String message, Map<String, Object> data) {
		return jsonResult(ApiResponseCode.SUCCESS, message, data);
	}
	
	public String sucJsonResult(String key, Object data) {
		return jsonResult(ApiResponseCode.SUCCESS, null, key, data);
	}
	
	public String sucJsonResult(String message, String key, Object data) {
		return jsonResult(ApiResponseCode.SUCCESS, message, key, data);
	}
	
	public String errorJsonResult(int code) {
		return jsonResult(code, null, null, null);
	}
	
	public String errorJsonResult(Exception e) {
		if(e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			return jsonResult(apiException.getCode(), apiException.getMsg(), null, null);
		}
		return jsonResult(ApiResponseCode.SYSTEM_ERROR, null, null, null);
	}
	
	public String errorJsonResult(int code, String message) {
		return jsonResult(code, message, null, null);
	}
	
	public String errorJsonResult(int code, Map<String, Object> data) {
		return jsonResult(code, null, data);
	}
	
	public String errorJsonResult(int code, String message, Map<String, Object> data) {
		return jsonResult(code, message, data);
	}
	
	public String errorJsonResult(int code, String key, Object data) {
		return jsonResult(code, null, key, data);
	}
	
	public String errorJsonResult(int code, String message, String key, Object data) {
		return jsonResult(code, message, key, data);
	}
	
	private ApiResult result(int code, String message, Map<String, Object> data) {
		ApiResult result = ApiMessageUtil.getResponseResult(code, message);
		result.setData(data);
		return result;
	}
	
	private String jsonResult(int code, String message, Map<String, Object> data) {
		return JSON.toJSONString(result(code, message, data));
	}
	
	private ApiResult result(int code, String message, String key, Object data){
		ApiResult result = ApiMessageUtil.getResponseResult(code, message);
		if(StringUtils.isNotEmpty(key) && ObjectUtils.notEqual(data, ObjectUtils.NULL)){
			result.data(key, data);
		}
		return result;
	}
	
	private String jsonResult(int code, String message, String key, Object data) {
		return JSON.toJSONString(result(code, message, key, data));
	}
	
}
