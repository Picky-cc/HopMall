package com.zufangbao.sun.yunxin.log;

import java.util.List;

public class SystemOperateLogRequestParam {

	//操作人
	Long principalId;
	//ip地址
	String ipAddress;
    //操作对象主要编号
	String keyContent;
	//操作功能类型
	LogFunctionType logFunctionType;
    //操作类型
	LogOperateType logOperateType;
	//操作类Class
	Class persistentClass;
	//操作对象
	Object object;
	//修改后对象（除update外为null）
	Object editObject;
	/* 导出时 需要传入被导出对象UUid */
	List<String> uuidList;
	
	

	public SystemOperateLogRequestParam(Long principalId, String ipAddress2,
			String keyContent, LogFunctionType logFunctionType,LogOperateType logOperateType,
			Class class1,Object oldObject,
			Object editObject,List<String> uuidList ) {
		this.principalId =  principalId;
		this.ipAddress = ipAddress2;
		this.keyContent = keyContent;
		this.logFunctionType = logFunctionType;
		this.logOperateType = logOperateType;
		this.persistentClass = class1;
		this.object = oldObject;
		this.editObject  =editObject;
		this.uuidList = uuidList;
		
	}

	public Long getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getKeyContent() {
		return keyContent;
	}

	public void setKeyContent(String keyContent) {
		this.keyContent = keyContent;
	}

	public LogFunctionType getLogFunctionType() {
		return logFunctionType;
	}

	public void setLogFunctionType(LogFunctionType logFunctionType) {
		this.logFunctionType = logFunctionType;
	}

	public LogOperateType getLogOperateType() {
		return logOperateType;
	}

	public void setLogOperateType(LogOperateType logOperateType) {
		this.logOperateType = logOperateType;
	}

	public Class getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(Class persistentClass) {
		this.persistentClass = persistentClass;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getEditObject() {
		return editObject;
	}

	public void setEditObject(Object editObject) {
		this.editObject = editObject;
	}

	public List<String> getUuidList() {
		return uuidList;
	}

	public void setUuidList(List<String> uuidList) {
		this.uuidList = uuidList;
	}

}
