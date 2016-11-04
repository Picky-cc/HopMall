package com.zufangbao.sun.yunxin.entity.remittance;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 冲账状态
 * @author Administrator
 *
 */
public enum ReverseStatus {
	
	/**
	 * 未发生
	 */
	UNOCCUR("enum.reverse-status.unOccur"),

	/**
	 * 未冲账
	 */
	NOTREVERSE("enum.reverse-status.notReverse"),
	
	/**
	 * 已冲账
	 */
	REVERSED("enum.reverse-status.reversed");

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private ReverseStatus(String key) {
		this.key = key;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.key);
	}

}
