package com.zufangbao.sun.yunxin.entity;

public enum OrderClearStatus {
	/** 未结清 */
	UNCLEAR("enum.clearing-status.unclear"),
	
	/** 已结清 */
	CLEAR("enum.clearing-status.clear");
	
	private String key;
	
	
	/**
	 * @param key
	 */
	private OrderClearStatus(String key) {
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
	
	
	public static OrderClearStatus fromValue(int value){
	    
	    for(OrderClearStatus item : OrderClearStatus.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
}
