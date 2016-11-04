package com.zufangbao.sun.entity.account;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum AuditStatus {
	
	/** 待对账 */
	CREATE("enum.audit-status.create"),
	
	/** 异常 */
	ISSUING("enum.audit-status.issuing"),
	
	/** 成功 */
	ISSUED("enum.audit-status.issued"),
	
	/** 失败 */
	CLOSE("enum.audit-status.close");
	
	private String key;
	
	/**
	 * @param key
	 */
	private AuditStatus(String key) {
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
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
	public static AuditStatus fromOrdinal(int ordinal){
		
		for(AuditStatus item : AuditStatus.values()){
			
			if(ordinal == item.getOrdinal()){
				
				return item;
			}
		}
		return null;
	}
}
