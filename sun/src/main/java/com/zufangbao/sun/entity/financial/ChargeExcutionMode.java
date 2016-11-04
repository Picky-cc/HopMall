package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 费用模式
 * @author Lux
 */
public enum ChargeExcutionMode {
	/**
	 * 向前收费
	 */
	FORWARD("enum.charge-excution-mode.FORWARD"),
	/**
	 * 向后收费
	 */
	BACKWARD("enum.charge-excution-mode.BACKWARD");
	
	private String key;

	private ChargeExcutionMode(String key) {
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
	
	public static ChargeExcutionMode formValue(int value){
		for(ChargeExcutionMode item:ChargeExcutionMode.values()){
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
