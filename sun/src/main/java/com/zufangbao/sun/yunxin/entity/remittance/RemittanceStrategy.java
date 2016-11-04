package com.zufangbao.sun.yunxin.entity.remittance;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 放款策略
 * 
 * @author zhanghongbing
 *
 */
public enum RemittanceStrategy {

	/** 多目标放款策略 */
	MULTIOBJECT("enum.remittance-strategy.multiobject"),

	/** 先放后扣放款策略 */
	DEDUCT_AFTER_REMITTANCE("enum.remittance-strategy.deduct_after_remittance");

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private RemittanceStrategy(String key) {
		this.key = key;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	

}
