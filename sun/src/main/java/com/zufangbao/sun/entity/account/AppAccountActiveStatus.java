package com.zufangbao.sun.entity.account;

public enum AppAccountActiveStatus {
	
	/** 有效 */
	VALID("enum.app-account-active-status.valid"),
	
	/** 无效 */
	INVALID("enum.app-account-active-status.invalid");
	
	private String key;
	
	/**
	 * @param key
	 */
	private AppAccountActiveStatus(String key) {
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
}
