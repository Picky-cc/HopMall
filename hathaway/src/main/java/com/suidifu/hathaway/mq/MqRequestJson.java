package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;
import java.util.LinkedList;

import com.demo2do.core.utils.JsonUtils;

public class MqRequestJson extends MqRequest{

	protected LinkedList<MqParameter> arguments = new LinkedList<MqParameter>();
	/**
	 * 
	 */
	private static final long serialVersionUID = -10051708245496293L;

	public MqRequestJson() {
		super("","", "", true);
	}
	public MqRequestJson(String refrenceUuid,String beanName, String methodName, boolean sync) {
		super(refrenceUuid,beanName,methodName, sync);
	}
	

	public LinkedList<MqParameter> getArguments() {
		return arguments;
	}

	public void setArguments(LinkedList<MqParameter> arguments) {
		this.arguments = arguments;
	}

	@Override
	public void pushAllArguments(Object[] args) {
		
		for (Object object : args) {

			pushArument(object);
		}
		
	}
	@Override
	public Object[] extractArguments(Type[] types) {

		int size = this.arguments.size();

		Object[] args = new Object[size];

		for (int i = 0; i < size; i++) {

			args[i] = this.arguments.get(i).parseParamValue(types[i]);
		}

		return args;

	}

	@Override
	public void pushArument(Object value)  {
		arguments.add(new MqParameter(value));
	}
	@Override
	public Object deserializeFromString(String serializedString) {
		MqRequest mqRequest = JsonUtils.parse(serializedString, MqRequestJson.class);
		return mqRequest;
	}
	@Override
	public String serializeToStrig() {
		return JsonUtils.toJsonString(this);
	}
}
