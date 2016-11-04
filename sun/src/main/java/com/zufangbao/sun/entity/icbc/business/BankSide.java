/**
 * 
 */
package com.zufangbao.sun.entity.icbc.business;

import org.apache.commons.lang.StringUtils;

/**
 * @author wukai
 * 银行借贷标记
 */
public enum BankSide {
	
	/**
	 * 借
	 */
	Debit("1"),

	/**
	 * 贷
	 */
	Credit("2"),
	
	;
	
	private String value;
	
	private BankSide(String value){
		
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static BankSide fromValue(String value){
		
		if(StringUtils.equals(value,BankSide.Credit.getValue())){
			
			return Credit;
		}
		return Debit;
	}
	
	public static BankSide fromName(String accountSide){
		if(StringUtils.isEmpty(accountSide)){
			return null;
		}
		return "debit".equalsIgnoreCase(accountSide) ? Credit : Debit;
	}
	
	public String getDescription(){
		
		return this == Credit ? "贷":"借";
	}
}
