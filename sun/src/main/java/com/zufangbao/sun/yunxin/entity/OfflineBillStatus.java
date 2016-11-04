package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 资产的资金状态
 * 
 * @author louguanyang
 *
 */
public enum OfflineBillStatus {

	/** 失败 */
	UNPAID("enum.offline-bill-status.unpaid"),
	
	/** 成功 */
	PAID("enum.offline-bill-status.paid");
	
	private String key;

	/**
	 * @param key
	 */
	private OfflineBillStatus(String key) {
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

	public static OfflineBillStatus fromValue(int value) {

		for (OfflineBillStatus item : OfflineBillStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

}
