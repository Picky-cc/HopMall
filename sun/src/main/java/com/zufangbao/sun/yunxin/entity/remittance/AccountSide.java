package com.zufangbao.sun.yunxin.entity.remittance;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum AccountSide {
	
	/** 贷方 */
	CREDIT("enum.account-side.credit"),

	/** 借方 */
	DEBIT("enum.account-side.debit");

	private String key;

	private AccountSide(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public int getOrdinal(){
		return ordinal();
	}
	
	public static AccountSide fromValue(int ordinal){
		
		for(AccountSide item : AccountSide.values()){
			
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
