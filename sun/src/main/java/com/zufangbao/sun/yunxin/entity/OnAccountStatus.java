package com.zufangbao.sun.yunxin.entity;

/**
 * 挂账状态 默认已挂账
 * 
 * @author louguanyang
 *
 */
public enum OnAccountStatus {

	/** 未挂账 */
	OFF_ACCOUNT("enum.on-account-status.offAccount"),
	/** 已挂账 */
	ON_ACCOUNT("enum.on-account-status.onAccount"),
	/** 已核销 */
	WRITE_OFF("enum.on-account-status.writeOff");

	private String key;

	/**
	 * @param key
	 */
	private OnAccountStatus(String key) {
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

	public static OnAccountStatus fromValue(int value) {

		for (OnAccountStatus item : OnAccountStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

}
