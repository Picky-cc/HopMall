package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.suidifu.hathaway.interfaces.MqSerialzableAndDeserialzable;
import com.suidifu.hathaway.mq.exceptions.ErrorRpcException;
import com.suidifu.hathaway.util.KryoUtils;

public class MqResponse implements MqSerialzableAndDeserialzable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2217932736825327148L;

	private String responseUuid;
	
	private String exceptionObject;
	
	private String resultJson;

	private boolean exception_fired;

	public String getResponseUuid() {
		return responseUuid;
	}

	public void setResponseUuid(String responseUuid) {
		this.responseUuid = responseUuid;
	}

	public String getExceptionObject() {
		return exceptionObject;
	}
	public Exception findExceptionObject(Class<?>[] exceptionTypes) {
		
		for (Class<?> clazz : exceptionTypes) {
			
			Object obj = KryoUtils.readClassAndObject(this.getExceptionObject(), clazz);
			
			if(obj != null){
				
				return (Exception) obj;
			}
		}
		return (Exception) KryoUtils.readClassAndObject(this.getExceptionObject(),ErrorRpcException.class);
	}

	public void setExceptionObject(String exceptionObject) {
		this.exceptionObject = exceptionObject;
	}
	public boolean isException_fired() {
		return exception_fired;
	}

	public void setException_fired(boolean exception_fired) {
		this.exception_fired = exception_fired;
	}

	public String getResultJson() {
		return resultJson;
	}
	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}

	public Object parseResultObject(Type type){
		
		if(StringUtils.isEmpty(this.resultJson)){
			return null;
		}
		return KryoUtils.readClassAndObject(resultJson, type);
	}

	public MqResponse() {
	}
	
	public MqResponse(String responseUuid, Object resultObject,
			Object exceptionObject) {
		super();
		this.responseUuid = responseUuid;
		this.exception_fired = (exceptionObject != null);
		this.resultJson = this.exception_fired ?  StringUtils.EMPTY : KryoUtils.writeClassAndObject(resultObject);
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Throwable.class, "stackTrace"); 
		this.exceptionObject = this.exception_fired ? KryoUtils.writeClassAndObject(exceptionObject) : StringUtils.EMPTY;
		
	}

	@Override
	public Object deserializeFromString(String serializedString) {
		return KryoUtils.readClassAndObject(serializedString,MqResponse.class);
	}

	@Override
	public String serializeToStrig() {
		return KryoUtils.writeClassAndObject(this);
	}
}
