package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 还款状态
 * 
 * @author louguanyang
 *
 */
public enum PaymentStatus {
	/** 未到期 */
	DEFAULT("enum.payment-statu.default"),
	/** 还款处理中 */
	PROCESSING("enum.payment-statu.processing"),
	/** 还款异常 */
	UNUSUAL("enum.payment-statu.unusual"),
	/** 还款成功 */
	SUCCESS("enum.payment-statu.success");
	private String key;

	private PaymentStatus(String key) {
		this.key = key;
	}

	public static PaymentStatus fromValue(int value) {
		for (PaymentStatus item : PaymentStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
