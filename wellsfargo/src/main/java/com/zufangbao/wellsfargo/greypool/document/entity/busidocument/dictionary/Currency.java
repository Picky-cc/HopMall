package com.zufangbao.wellsfargo.greypool.document.entity.busidocument.dictionary;

import org.apache.commons.lang.StringUtils;

/**
 * @author wukai
 *
 */
public enum Currency {
	
	/**
	 * 人民币
	 */
	CNY("enum.currency.cny"),
	
	/**
	 * 美元
	 */
	USD("enum.currency.usd"),
	

	;

	private String key;
	
	private Currency(String key){
		
		this.key = key;
	}
	
	public String getValue(){
		
		return this.getKey().substring(this.getKey().lastIndexOf(".") + 1);
	}

	public String getKey() {
		return key;
	}
	public static Currency fromName(String name){
		
		for(Currency item : Currency.values()){
			
			if(StringUtils.equalsIgnoreCase(name, item.name())){
				
				return item;
			}
		}
		return null;
	}
	public String getName(){
		return this.name();
	}
	public static Currency fromValue(int value){
		
		for(Currency item : Currency.values()){
			
			if(value == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}
	
}
