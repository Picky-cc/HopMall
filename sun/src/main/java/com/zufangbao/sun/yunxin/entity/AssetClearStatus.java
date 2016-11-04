package com.zufangbao.sun.yunxin.entity;

/**
 * 资产的资金状态
 * 
 * @author louguanyang
 *
 */
public enum AssetClearStatus {

	/** 未结转 */
	UNCLEAR("enum.asset-status.unclear"),

	/** 已结转 */
	CLEAR("enum.asset-status.clear");

	private String key;

	/**
	 * @param key
	 */
	private AssetClearStatus(String key) {
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
	public int getOrdinal(){
		return this.ordinal();
	}
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static AssetClearStatus fromValue(int value) {

		for (AssetClearStatus item : AssetClearStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

}
