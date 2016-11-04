package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 扣款执行状态
 * 
 */
public enum ExecutingDeductStatus {
	/** 已创建 */
	CREATE("enum.executing-deduct-status.create"),
	/** 扣款中 */
	DOING("enum.executing-deduct-status.doing"),
	/** 扣款成功 */
	SUCCESS("enum.executing-deduct-status.success"),
	/** 扣款失败 */
	FAIL("enum.executing-deduct-status.fail"),
	/** 扣款超时 */
	TIME_OUT("enum.executing-deduct-status.timeOut");

	private String key;

	/**
	 * @param key
	 */
	private ExecutingDeductStatus(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get alias of order status
	 * 
	 * @return
	 */
	public int getOrdinal() {
		return this.ordinal();
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static ExecutingDeductStatus fromValue(int value) {

		for (ExecutingDeductStatus item : ExecutingDeductStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
}
