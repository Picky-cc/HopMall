package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum PaymentWay {
	/** 银联代扣 */
	UNIONDEDUCT("enum.payment-way.union-deduct"),

	/** 线下打款 */

	OUTLINEDEDUCT("enum.payment-way.outline-deduct");
	private String key;

	/**
	 * @param key
	 */
	private PaymentWay(String key) {
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

	public static PaymentWay fromValue(int value) {

		for (PaymentWay item : PaymentWay.values()) {
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
