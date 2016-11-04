package com.suidifu.hathaway.mq;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.springframework.util.StringUtils;

import com.suidifu.hathaway.mq.exceptions.InvalidRemoteServiceContainerConfigException;

public abstract class VirtualContainer {

	public void prepareWorkingContext()
			throws InvalidRemoteServiceContainerConfigException,
			IllegalArgumentException, IllegalAccessException, IOException {

		String serviceName = serviceName();
		HashMap<String, Field> fields = extractWorkingParams();
		for (String paramName : fields.keySet()) {
			String fullParamPath = serviceName + "." + paramName;
			String paramValue = evaluateWorkingContext(fullParamPath);
			if (StringUtils.isEmpty(paramValue))
				throw new InvalidRemoteServiceContainerConfigException();
			Field field = fields.get(paramName);
			field.setAccessible(true);
			field.set(this, paramValue);
		}

	}

	private HashMap<String, Field> extractWorkingParams() {
		Field[] fields = this.getClass().getDeclaredFields();
		HashMap<String, Field> workingParams = new HashMap<String, Field>();
		for (Field field : fields) {
			RemoteServiceParam remoteParamAnnotation = field
					.getAnnotation(RemoteServiceParam.class);
			if (remoteParamAnnotation == null)
				continue;
			workingParams.put(field.getName(), field);
		}
		return workingParams;
	}

	public abstract String serviceName();

	public abstract String evaluateWorkingContext(String fullConfigPath)
			throws IOException;

	public abstract <T> T connection(Class<T> clazz);

}
