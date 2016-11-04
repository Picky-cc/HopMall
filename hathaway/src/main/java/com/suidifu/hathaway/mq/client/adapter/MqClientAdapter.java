package com.suidifu.hathaway.mq.client.adapter;

import java.lang.reflect.Method;

import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.amqp.AsycOccurReminderException;
import com.suidifu.hathaway.mq.MqRequest;
import com.suidifu.hathaway.mq.MqResponse;
import com.suidifu.hathaway.mq.RemoteServiceAccessor;
import com.suidifu.hathaway.mq.RemoteServiceContainer;
import com.suidifu.hathaway.mq.exceptions.InvalidRemoteServiceContainerConfigException;
import com.suidifu.hathaway.mq.exceptions.InvalidRemoteServiceContainerException;
import com.suidifu.hathaway.mq.exceptions.NoResponseException;
import com.suidifu.hathaway.util.KryoUtils;
import com.suidifu.hathaway.util.SpringContextUtil;

@Component
public class MqClientAdapter {

	public Object publishSyncRPCMessage(MqRequest mqRequest, String RemoteServiceContainerName, Method method) throws Throwable {
		RemoteServiceAccessor accessor = selectRemoteServiceAccessor(RemoteServiceContainerName, mqRequest.getRefrenceUuid());
		Object responseMessage = accessor.sendAndReceive(mqRequest.serializeToStrig());
		if (null == responseMessage)
			throw new NoResponseException();
		MqResponse mqResponse = (MqResponse) KryoUtils.readClassAndObject(responseMessage.toString(), MqResponse.class);
		if (mqResponse.isException_fired())
			throw mqResponse.findExceptionObject(method.getExceptionTypes());
		return mqResponse.parseResultObject(method.getGenericReturnType());
	}

	private RemoteServiceAccessor selectRemoteServiceAccessor(String RemoteServiceContainerName, String refrenceUuid)
			throws InvalidRemoteServiceContainerConfigException, InvalidRemoteServiceContainerException {
		return selectRemoteServiceAccessor(findRemoteServiceContainer(RemoteServiceContainerName), refrenceUuid);
	}

	private RemoteServiceAccessor selectRemoteServiceAccessor(RemoteServiceContainer remoteServiceContainer, String refrenceUuid)
			throws InvalidRemoteServiceContainerConfigException {
		RemoteServiceAccessor accessor = remoteServiceContainer.accessor(refrenceUuid);
		if (accessor == null)
			throw new InvalidRemoteServiceContainerConfigException();
		return accessor;
	}

	public RemoteServiceContainer findRemoteServiceContainer(String RemoteServiceContainerName) throws InvalidRemoteServiceContainerException {
		RemoteServiceContainer remoteServiceContainer = SpringContextUtil.getBean(RemoteServiceContainerName);
		if (remoteServiceContainer == null)
			throw new InvalidRemoteServiceContainerException();
		if (remoteServiceContainer.accessorSize() <= 0)
			throw new InvalidRemoteServiceContainerException();
		return remoteServiceContainer;
	}

	public Object publishASyncRPCMessage(String beanName, String methodName, Object[] args, RemoteServiceContainer remoteServiceContainer, String referenceUuid)
			throws Throwable {
		MqRequest mqRequest = MqRequest.createRequest(referenceUuid, beanName, methodName, false);
		mqRequest.pushAllArguments(args);
		return publishASyncRPCMessage(mqRequest, remoteServiceContainer);
	}

	public Object publishASyncRPCMessage(MqRequest mqRequest, String RemoteServiceContainerName) throws Throwable {
		return publishASyncRPCMessage(mqRequest, findRemoteServiceContainer(RemoteServiceContainerName));
	}

	public Object publishASyncRPCMessage(MqRequest mqRequest, RemoteServiceContainer remoteServiceContainer) throws Throwable {
		return publishASyncRPCMessage(mqRequest, selectRemoteServiceAccessor(remoteServiceContainer, mqRequest.getRefrenceUuid()));
	}

	private Object publishASyncRPCMessage(MqRequest mqRequest, RemoteServiceAccessor accessor) throws AsycOccurReminderException {
		return accessor.send(mqRequest.serializeToStrig(), new CorrelationData(mqRequest.getRequestUuid()));
	}
}
