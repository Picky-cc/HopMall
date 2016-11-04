package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 短信发送状态
 * @author louguanyang
 *
 */
public enum SmsSendStatus {
	/** 待发送 */
	WAITING_SEND("enum.sms-send-status.waiting-send"),
	/** 成功 */
	SUCCESS("enum.sms-send-status.success"),
	/** 失败 */
	FAILURE("enum.sms-send-status.failure");

	private String key;

	private SmsSendStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getOrdinal() {
		return this.ordinal();
	}

	@Override
	public String toString() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
