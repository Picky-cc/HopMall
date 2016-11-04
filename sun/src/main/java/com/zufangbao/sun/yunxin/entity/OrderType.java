package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.BasicEnum;

/**
 * 收款单类型
 * @author louguanyang
 *
 */
public enum OrderType implements BasicEnum {
	/** 结算单 */
	NORMAL("enum.order-type.normal"),
	/** 担保补足单 */
	GUARANTEE("enum.order-type.guarantee");
	
	private String key;

	private OrderType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public static OrderType fromValue(int value) {
		for (OrderType item : OrderType.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
}
