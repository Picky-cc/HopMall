package com.zufangbao.sun.yunxin.entity;

/**
 * 还款计划类型
 *
 */
public enum RepaymentPlanType {
	
	/** 正常还款 */
	NORMAL("enum.repayment-plan-type.normal"),
	/** 提前还款 */
	PREPAYMENT("enum.repayment-plan-type.prepayment");

	private String key;

	/**
	 * @param key
	 */
	private RepaymentPlanType(String key) {
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

	public static RepaymentPlanType fromValue(int value) {

		for (RepaymentPlanType item : RepaymentPlanType.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
}
