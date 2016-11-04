package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;
import java.util.UUID;

import com.suidifu.hathaway.interfaces.MqSerialzableAndDeserialzable;

public abstract class MqRequest implements MqSerialzableAndDeserialzable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6500517364802902995L;

	public abstract Object[] extractArguments(Type[] types);

	public abstract void pushAllArguments(Object[] args);
	
	public static String SERIALIZER= 
//			MqRequestJson.class.getName();
			MqRequestKryo.class.getName();
	
	public  static MqRequest createfromString(String reqData) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Class<?> clazz=Class.forName(SERIALIZER);
		MqRequest request=(MqRequest) clazz.newInstance();
		return (MqRequest) request.deserializeFromString(reqData);
	}
	
	public static MqRequest createRequest(String refrenceUuid,String beanName, String methodName, boolean sync) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Class<?> clazz=Class.forName(SERIALIZER);
		MqRequest request=(MqRequest) clazz.newInstance();
		request.requestUuid = UUID.randomUUID().toString();
		request.refrenceUuid = refrenceUuid;
		request.beanName = beanName;
		request.methodName = methodName;
		request.sync = sync;
		return request;
		
	}
	
	
	public MqRequest(String refrenceUuid,String beanName, String methodName, boolean sync) {
		super();
		this.requestUuid = UUID.randomUUID().toString();
		this.refrenceUuid = refrenceUuid;
		this.beanName = beanName;
		this.methodName = methodName;
		this.sync = sync;
	}

	protected String requestUuid;
	protected String refrenceUuid;
	protected boolean sync;
	protected String beanName;
	protected String methodName;
	

	public String getRequestUuid() {
		return requestUuid;
	}

	public void setRequestUuid(String uuid) {
		this.requestUuid = uuid;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public abstract void pushArument(Object value);

	public String getRefrenceUuid() {
		return refrenceUuid;
	}

	public void setRefrenceUuid(String refrenceUuid) {
		this.refrenceUuid = refrenceUuid;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

}
