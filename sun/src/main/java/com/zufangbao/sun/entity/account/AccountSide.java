package com.zufangbao.sun.entity.account;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.BasicEnum;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum AccountSide implements BasicEnum{
	
	/** 贷方 */
	CREDIT("enum.account-side.credit","enum.account-side.outgoings"),
	/** 借方  */
	DEBIT("enum.account-side.debit","enum.account-side.incoming");

	
	private String key;
	private String incomeType;
	/**
	 * @param key
	 */
	private AccountSide(String key,String incomeType) {
		this.key = key;
		this.incomeType = incomeType;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	/**
	 * Get alias 
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	public static AccountSide fromName(String name){
		
		for(AccountSide item : AccountSide.values()){
			
			if(StringUtils.equalsIgnoreCase(name, item.name())){
				
				return item;
			}
			
		}
		return null;
	}
	public static AccountSide reverse(BankSide bankSide){
		
		if(StringUtils.equalsIgnoreCase(bankSide.name(), CREDIT.name())){
			
			return DEBIT;
		}
		return CREDIT;
	}
	public static AccountSide transform(BankSide bankSide){
		
		if(StringUtils.equalsIgnoreCase(bankSide.name(), CREDIT.name())){
			
			return CREDIT;
		}
		return DEBIT;
	}
	public String getName(){
		return this.name();
	}
	public static AccountSide fromValue(int value){
		
		for(AccountSide item : AccountSide.values()){
			
			if(value == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}
	
	public static AccountSide fromLedgerBookAccountSide(com.zufangbao.sun.ledgerbook.AccountSide accountSide){
		if(accountSide==null){
			return null;
		}
		for(AccountSide item : AccountSide.values()){
			
			if(accountSide.ordinal() == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}
	
	public static AccountSide fromAccountSide(com.zufangbao.sun.yunxin.entity.remittance.AccountSide accountSide){
		if(accountSide==null){
			return null;
		}
		for(AccountSide item : AccountSide.values()){
			
			if(accountSide.ordinal() == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}
	
	public static String convertToApiAccountType(AccountSide accountSide){
		if(accountSide==AccountSide.DEBIT){
			return "D";
		}else if(accountSide==AccountSide.CREDIT){
			return "C";
		}
		return "";
	}

	public int getOrdinal() {
		return this.ordinal();
	}
	
	public String getIncomeTypeMsg(){
		return ApplicationMessageUtils.getChineseMessage(getIncomeType());
	}
	
}
