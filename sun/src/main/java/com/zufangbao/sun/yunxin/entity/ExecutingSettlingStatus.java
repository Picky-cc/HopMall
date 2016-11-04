package com.zufangbao.sun.yunxin.entity;

public enum ExecutingSettlingStatus {
	/** 已创建 */
	CREATE("enum.executing-settling-status.create"),
	/** 处理中 */
	DOING("enum.executing-settling-status.doing"),
	/** 扣款成功 */
	SUCCESS("enum.executing-settling-status.success"),
	/** 扣款失败 */
	FAIL("enum.executing-settling-status.fail");
	
	private String key;
	
	/**
	 * @param key
	 */
	private ExecutingSettlingStatus(String key) {
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
	
	
	public static ExecutingSettlingStatus fromValue(int value){
	    
	    for(ExecutingSettlingStatus item : ExecutingSettlingStatus.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
}
