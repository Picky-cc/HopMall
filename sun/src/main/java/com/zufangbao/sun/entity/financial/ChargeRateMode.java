package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 通道工作状态
 * @author Lux
 */
public enum ChargeRateMode {
	
	/**
	 * 单笔固定
	 */
	SINGLEFIXED("enum.charge-rate-mode.SINGLEFIXED"),
	/**
	 * 单笔比例
	 */
	SINGLERATA("enum.charge-rate-mode.SINGLERATA");
	
	private String key;

	private ChargeRateMode(String key) {
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
	
	public static ChargeRateMode formValue(int value){
		for(ChargeRateMode item:ChargeRateMode.values()){
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
