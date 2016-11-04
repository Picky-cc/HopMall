package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum IsTotal {

	

    //合计
	TOTAL("enum.is-total.total"),
	//明细
	DETAIL("enum.is-total.detail");
	 
	
	
	
	
   private String key;
	
	/**
	 * @param key
	 */
	private IsTotal(String key) {
		this.key = key;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get alias of order status
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	

	
	public int getOrdinal(){
		
		return this.ordinal();
	}
	public static IsTotal fromOrdinal(int ordinal){
		
		for(IsTotal item : IsTotal.values()){
			
			if(ordinal == item.getOrdinal()){
				
				return item;
			}
		}
		return null;
	}
	
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
