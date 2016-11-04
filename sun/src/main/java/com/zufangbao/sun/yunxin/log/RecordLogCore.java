package com.zufangbao.sun.yunxin.log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@Component
public class RecordLogCore {

	@Autowired
	private SystemOperateLogService systemOperateLogService;

	public SystemOperateLog insertNormalRecordLog(Long userId, String ip,
			LogFunctionType logFunctionType, LogOperateType logOperateType,
			Object object) throws Exception {

		return generateRecordLog(userId, ip, logFunctionType, logOperateType,
				object);
	}


	private SystemOperateLog generateRecordLog(Long userId, String ip,
			LogFunctionType logFunctionType, LogOperateType logOperateType,
			Object object) throws Exception {
		SystemOperateLog log = new SystemOperateLog();
		try {
			Method method1 = (Method) object.getClass().getMethod("getUuid");
			Object uuid = method1.invoke(object);
			if (uuid != null) {
				log.setObjectUuid(uuid.toString());
			}
			String recordContextDetail = JsonUtils.toJsonString(object);
			log.setRecordContentDetail(recordContextDetail);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		log.setIp(ip);
		log.setLogFunctionType(logFunctionType);
		log.setLogOperateType(logOperateType);
		log.setOccurTime(new Date());
		log.setUserId(userId);
		return log;

	}


	public <T> SystemOperateLog generateUpdateRecordLog(Long userId, String ip,
			LogFunctionType logFunctionType, LogOperateType logOperateType,
			Object oldObject, Object newObject, Class<T> persistentClass) throws Exception {
		List<UpdateContentDetail> updateDetails = new ArrayList<UpdateContentDetail>();
		SystemOperateLog log = new SystemOperateLog();
		domainEquals(oldObject, newObject, updateDetails,
				persistentClass);
		try {
			Method method1 = (Method) newObject.getClass().getMethod("getUuid");
			Object uuid = method1.invoke(newObject);
			if (uuid != null) {
				log.setObjectUuid(uuid.toString());
			}
			String recordContextDetail = JsonUtils.toJsonString(newObject);
			log.setRecordContentDetail(recordContextDetail);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		log.setIp(ip);
		log.setLogFunctionType(logFunctionType);
		log.setLogOperateType(logOperateType);
		log.setOccurTime(new Date());
		log.setUserId(userId);
		log.setUpdateContentDetail(updateDetails);
		return log;
	}

	private <T> boolean domainEquals(Object source, Object target,
			List<UpdateContentDetail> updateDetails, Class<T> persistentClass) {
		if (source == null || target == null) {
			return false;
		}
		boolean rv = true;
		rv = classOfSrc(source, target, rv, updateDetails, persistentClass);
		return rv;
	}

	/**
	 * 源目标为非MAP类型时
	 * 
	 * @param source
	 * @param target
	 * @param rv
	 * @return
	 */
	private <T> boolean classOfSrc(Object source, Object target, boolean rv,
			List<UpdateContentDetail> updateDetails, Class<T> persistentClass) {
		Field[] fields = persistentClass.getDeclaredFields();
		for (Field field : fields) {
			String nameKey = field.getName();
			String srcValue = getClassValue(source, nameKey) == null ? ""
					: getClassValue(source, nameKey).toString();
			String tarValue = getClassValue(target, nameKey) == null ? ""
					: getClassValue(target, nameKey).toString();
			if (!srcValue.equals(tarValue)) {
				UpdateContentDetail updateContext = new UpdateContentDetail();
				updateContext.setFiledName(nameKey);
				updateContext.setOldValue(srcValue);
				updateContext.setNewValue(tarValue);
				updateDetails.add(updateContext);
				rv = false;
			}
		}

		return rv;
	}

	/**
	 * 根据字段名称取值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public Object getClassValue(Object obj, String fieldName) {
		if (obj == null) {
			return null;
		}
		try {
			Class beanClass = obj.getClass();
			Method[] ms = beanClass.getMethods();
			for (int i = 0; i < ms.length; i++) {
				// 非get方法不取
				if (!ms[i].getName().startsWith("get")) {
					continue;
				}
				Object objValue = null;
				try {
					objValue = ms[i].invoke(obj, new Object[] {});
				} catch (Exception e) {
					continue;
				}
				if (objValue == null) {
					continue;
				}
				if (ms[i].getName().toUpperCase()
						.equals(fieldName.toUpperCase())
						|| ms[i].getName().substring(3).toUpperCase()
								.equals(fieldName.toUpperCase())) {
					return objValue;
				} else if (fieldName.toUpperCase().equals("SID")
						&& (ms[i].getName().toUpperCase().equals("ID") || ms[i]
								.getName().substring(3).toUpperCase()
								.equals("ID"))) {
					return objValue;
				}
			}
		} catch (Exception e) {
			// logger.info("取方法出错！" + e.toString());
		}
		return null;
	}

}
