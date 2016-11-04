package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

	
public enum DeductApplicationExecutionStatus {
	
	
	
	/** 已创建 */
	CREATE("enum.deduct-execution-status.create"),
	
	/** 处理中 */
	PROCESSING("enum.deduct-execution-status.processing"),

	/** 成功 */
	SUCCESS("enum.deduct-execution-status.success"),

	/** 失败 */
	FAIL("enum.deduct-execution-status.fail"),
	
	/** 异常 */
	ABNORMAL("enum.deduct-execution-status.abnormal"),
	
	/** 撤销 */
	ABANDON("enum.deduct-execution-status.abandon");
	
	
	
private String key;
	
	/**
	 * @param key
	 */
	private DeductApplicationExecutionStatus(String key) {
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
	public static DeductApplicationExecutionStatus fromOrdinal(int ordinal){
		
		for(DeductApplicationExecutionStatus item : DeductApplicationExecutionStatus.values()){
			
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
