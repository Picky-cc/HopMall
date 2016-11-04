package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum RepaymentType {

	
	//提前
	ADVANCE("enum.repayment-type.advance"),
	//正常
	NORMAL("enum.repayment-type.normal"),
	//逾期
	OVERDUE("enum.repayment-type.overdue");
	private String key;

	/**
	 * @param key
	 */
	private RepaymentType(String key) {
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

	public static RepaymentType fromValue(int value) {

		for (RepaymentType item : RepaymentType.values()) {
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
