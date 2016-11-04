package com.zufangbao.sun.yunxin.log;

import com.demo2do.core.utils.DateUtils;

public class SystemOperateLogVO {


	private String recordContent;

	private String occurTime;

	private String operateName;

	public SystemOperateLogVO() {

	}

	public SystemOperateLogVO(String recordContent, String occurTime, String operateName) {
		super();
		this.recordContent = recordContent;
		this.occurTime = occurTime;
		this.operateName = operateName;
	}

	public SystemOperateLogVO(SystemOperateLog systemOperateLog , String name) {
		this.occurTime = DateUtils.format(systemOperateLog.getOccurTime(), "yyyy-MM-dd HH:mm:ss");
        this.operateName = name;
	}


	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

}
