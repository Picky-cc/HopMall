package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum PaymentChannelType {

	GuangdongUnionPay("enum.channel-type.guangdong-unionpay");

	private String key;

	private PaymentChannelType(String key) {
		this.key = key;
	}
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public String getKey(){
		return key;
	}
	public int getordinal() {
		return this.ordinal();
	}
	public static PaymentChannelType formValue(int value) {
		for (PaymentChannelType item : PaymentChannelType.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
}
