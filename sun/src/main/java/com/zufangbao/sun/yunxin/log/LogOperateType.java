package com.zufangbao.sun.yunxin.log;

import org.apache.commons.lang.StringUtils;

public enum LogOperateType {

	LOGIN("enum.log-operate-type.login"),
	ADD("enum.log-operate-type.add"),
	UPDATE("enum.log-operate-type.update"),
	DELETE("enum.log-operate-type.delete"),
	IMPORT("enum.log-operate-type.import"),
	EXPORT("enum.log-operate-type.export"),
	ACTIVE("enum.log-operate-type.active"),
	ASSOCIATE("enum.log-operate-type.associate"),
	LAPSE("enum.log-operate-type.lapse"),
	AUDIT("enum.log-operate-type.audit"),
	INVALIDATE("enum.log-operate-type.invalidate"),
	RESEND("enum.log-operate-type.resend");
	

	private String value;

	private LogOperateType(String value) {

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public int getOrdinal() {
		return this.ordinal();
	}

	public static LogOperateType fromValue(String value) {

		for (LogOperateType item : LogOperateType.values()) {

			if (StringUtils.equals(value, item.getValue())) {

				return item;
			}
		}
		return null;
	}
}
