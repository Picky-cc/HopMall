package com.zufangbao.sun.yunxin.entity;

public enum ApplicationStatus {
	/** 未申请 */
	CREATE("enum.application-status.create"),
	/** 已申请 */
	APPLIED("enum.application-status.applied"),
	/** 申请成功 */
	SUCCESS("enum.application-status.success"),
	/** 申请失败 */
	FAIL("enum.application-status.fail"),
	/** 申请异常 */
	EXCEPTION("enum.application-status.exception");
	private String key;
	
	/**
	 * @param key
	 */
	private ApplicationStatus(String key) {
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
	
	
	public static ApplicationStatus fromValue(int value){
	    
	    for(ApplicationStatus item : ApplicationStatus.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
}
