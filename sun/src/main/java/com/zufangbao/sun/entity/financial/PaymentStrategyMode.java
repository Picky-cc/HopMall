package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 通道策略
 * @author Lux
 */
public enum PaymentStrategyMode {
	/**
	 * 单一通道模式
	 */
	SINGLECHANNELMODE("enum.payment-strategy-model.SINGLECHANNELMODE"),
	
	/**
	 * 发卡行优先模式
	 **/
	ISSUINGBANKFIRST("enum.payment-strategy-model.ISSUINGBANKFIRST");
	
	
	private String key;

	private PaymentStrategyMode(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public int getOrdinal() {
		return ordinal();
	}
	
	public static PaymentStrategyMode fromValue(int value){
		for(PaymentStrategyMode item:PaymentStrategyMode.values()){
			if(item.ordinal() == value){ 
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
