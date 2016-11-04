package com.zufangbao.earth.yunxin.api.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 响应结果类
 * @author zhanghongbing
 *
 */
public class ApiResult {

	public int code;
	
	public String message;
	
	public Map<String, Object> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public ApiResult data(String key, Object value) {
		if(this.data == null) {
			this.data = new HashMap<String, Object>();
		}
		this.data.put(key, value);
		return this;
	}
	
	@JSONField(serialize = false)
	public boolean isValid() {
		return this.code == 0;
	}
	
	public ApiResult() {
		super();
	}

	public ApiResult(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
}
