package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 通道业务类型
 * @author Lux
 */
public enum BusinessType {
	
	/**
	 * 自有
	 */
	SELF("enum.business-type.SELF"),
	
	/**
	 * 委托
	 */
	ENTRUST("enum.business-type.ENTRUST");
	
	private String key;

	private BusinessType(String key) {
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
	
	public static BusinessType fromValue(int value){
		for(BusinessType item:BusinessType.values()){
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
