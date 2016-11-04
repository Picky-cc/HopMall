package com.zufangbao.sun.yunxin.entity;

/**
 * 差异状态
 * @author louguanyang
 *
 */
public enum OverDueStatus {
	/** 未差异 */
	NOT_OVERDUE("enum.over-due-status.not-overdue"),
	/** 已差异 */
	OVERDUE("enum.over-due-status.overdue");
	private String key;

	/**
	 * @param key
	 */
	private OverDueStatus(String key) {
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
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static OverDueStatus fromValue(int value) {

		for (OverDueStatus item : OverDueStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
}
