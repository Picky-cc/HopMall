package com.zufangbao.sun.yunxin.entity;

public enum DeductStatus {
	/** 未扣款成功 */
	UNCLEAR("enum.deduct-status.unclear"),
	/** 已扣款成功 */
	CLEAR("enum.deduct-status.clear");

	private String key;

	/**
	 * @param key
	 */
	private DeductStatus(String key) {
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

	public int getOrdinal() {
		return ordinal();
	}

	public static DeductStatus fromValue(int value) {

		for (DeductStatus item : DeductStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
}
