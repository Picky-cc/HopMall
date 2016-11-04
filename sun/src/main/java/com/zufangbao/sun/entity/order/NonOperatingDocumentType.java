package com.zufangbao.sun.entity.order;

import com.zufangbao.sun.BasicEnum;

/**
 * 收款单类型
 * @author louguanyang
 *
 */
public enum NonOperatingDocumentType implements BasicEnum {
	/** 充值单 */
	CHARGE_ORDER("enum.non-operating-document-type.charge-order"),
	/** 退款单 */
	REFUND_ORDER("enum.order-type.refund-order");
	
	private String key;

	private NonOperatingDocumentType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
}
