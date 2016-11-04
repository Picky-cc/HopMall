  package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class MqParameter implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2789372508768739132L;
	
	private String lazyParamValue;

	public void setParamValue(Object paramValue) {
		this.lazyParamValue = JSON.toJSONString(paramValue);
	}

	public MqParameter() {
	}

	public MqParameter(Object paramValue) {
		super();
		this.setParamValue(paramValue);
	}

	public Object parseParamValue(Type type) {
		Object realValue = JSON.parseObject(lazyParamValue, type,
				
				new Feature[0]);

		return realValue;

	}

	public String getLazyParamValue() {
		return lazyParamValue;
	}

	public void setLazyParamValue(String lazyParamValue) {
		this.lazyParamValue = lazyParamValue;
	}
}