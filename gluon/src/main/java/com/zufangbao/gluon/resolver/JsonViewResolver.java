package com.zufangbao.gluon.resolver;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;

@Component
public class JsonViewResolver {

	public String sucJsonResult() {
		return jsonResult(GlobalCodeSpec.CODE_SUC, GlobalMsgSpec.MSG_SUC, null, null);
	}

	public String sucJsonResult(String key, Object data) {
		return jsonResult(GlobalCodeSpec.CODE_SUC, GlobalMsgSpec.MSG_SUC, key, data);
	}

	public String sucJsonResult(Map<String, Object> data) {
		Result result = new Result(String.valueOf(GlobalCodeSpec.CODE_SUC));
		result.setData(data);
		return JsonUtils.toJsonString(result);
	}
	
	public String sucJsonResult(String message, String key, Object data) {
		return jsonResult(GlobalCodeSpec.CODE_SUC, message, key, data);
	}
	
	public String sucJsonResult(String key, Object data, SerializerFeature... features) {
		return JSON.toJSONString(result(GlobalCodeSpec.CODE_SUC, GlobalMsgSpec.MSG_SUC, key, data), features);
	}
	
	public String sucJsonResult(Map<String, Object> data, SerializerFeature... features) {
		Result result = new Result(String.valueOf(GlobalCodeSpec.CODE_SUC));
		result.setData(data);
		return JSON.toJSONString(result, features);
	}
	
	public String errorJsonResult(int code) {
		return errorJsonResult(code, "");
	}
	
	public String errorJsonResult(int retCode, String message) {
		return jsonResult(retCode, message, null, null);
	}
	
	public String errorJsonResult(String message) {
		return errorJsonResult(GlobalCodeSpec.CODE_FAILURE, message);
	}

	public String errorJsonResult(Map<String, Object> data) {
		Result result = new Result(String.valueOf(GlobalCodeSpec.CODE_FAILURE));
		result.setData(data);
		return JsonUtils.toJsonString(result);
	}

	public Result errorResult(int retCode, String message) {
		return result(retCode, message,null,null);
	}
	
	public String jsonResult(String message) {
		return jsonResult(GlobalCodeSpec.CODE_SUC, message, null, null);
	}

	public String jsonResult(int code) {
		return jsonResult(code, StringUtils.EMPTY, null, null);
	}

	public String jsonResult(boolean isTrue) {
		return jsonResult(isTrue ? GlobalCodeSpec.CODE_SUC : GlobalCodeSpec.CODE_FAILURE,isTrue ? GlobalMsgSpec.MSG_SUC : GlobalMsgSpec.MSG_FAILURE,null,null);
	}

	public String jsonResult(int code, String message) {
		return jsonResult(code, message, null, null);
	}
	
	public String jsonResult(int code,String message,String key,Object data){
		return JsonUtils.toJsonString(result(code, message, key, data));
	}

	public String jsonResult(int code, String message, Map<String, Object> data) {
		Result result = new Result(String.valueOf(code));
		result.setMessage(message);
		result.setData(data);
		return JsonUtils.toJsonString(result);
	}
	
	public Result noAuthorityResult() {
		return result(GlobalCodeSpec.GeneralErrorCode.ERROR_NO_AUTHORITY,GlobalMsgSpec.GeneralErrorMsg.MSG_NO_AUTHORITY,null,null);
	}
	
	private Result result(int code,String message,String key,Object data){
		Result result = new Result(String.valueOf(code));
		if(StringUtils.isNotEmpty(message)){
			result.setMessage(message);
		}
		if(StringUtils.isNotEmpty(key) && ObjectUtils.notEqual(data, ObjectUtils.NULL)){
			result.data(key, data);
		}
		return result;
	}

}
