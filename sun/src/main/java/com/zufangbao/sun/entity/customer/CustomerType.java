package com.zufangbao.sun.entity.customer;

import com.zufangbao.sun.BasicEnum;

/**
 * 收款单类型
 * @author louguanyang
 *
 */
public enum CustomerType implements BasicEnum {
	/** 个人 */
	PERSON("enum.customer-type.person"),
	/** 公司 */
	COMPANY("enum.customer-type.company");
	
	private String key;

	private CustomerType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		
		return this.ordinal();
	}
	
	public static CustomerType fromOrdinal(int ordinal){
		
		for(CustomerType item : CustomerType.values()){
			
			if(ordinal == item.getOrdinal()){
				
				return item;
			}
		}
		return null;
	}
	
}
