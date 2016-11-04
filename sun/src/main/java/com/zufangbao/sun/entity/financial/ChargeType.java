package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 交易类型
 * @author Lux
 */
public enum ChargeType{

	/**
	 * 批量
	 */
	BATCH("enum.charge-type.BATCH"),
	/**
	 * 单笔
	 */
	SINGLE("enum.charge-type.SINGLE"),
	/**
	 * 都支持
	 */
	BOTH("enum.charge-type.BOTH");
	
	private String key;

	private ChargeType(String key) {
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
	
	public static ChargeType formValue(int value){
		for(ChargeType item:ChargeType.values()){
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
