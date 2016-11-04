package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 逾期状态
 * @author louguanyang
 *
 */
public enum AuditOverdueStatus {
	/** 正常 **/
	NORMAL("enum.audit-overdue-status.normal"),
	/** 待确认 **/
	UNCONFIRMED("enum.audit-overdue-status.unconfirmed"),
	/** 已逾期 **/
	OVERDUE("enum.audit-overdue-status.overdue");
	private String key;

	private AuditOverdueStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public static AuditOverdueStatus fromValue(int value) {
		for (AuditOverdueStatus item : AuditOverdueStatus.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public int getOrdinal() {
		return ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
