package com.zufangbao.sun.entity.account;



/**
 * 银行账号类型
 * @author zjm
 *
 */
public enum DepositeAccountType {

	/** 银联 */
	UINON_PAY("enum.deposite_account_type.uinon_pay"),
	
	/** 银行 */
	BANK("enum.deposite_account_type.bank"),
	
	/** 虚拟账户 */
	VIRTUAL_ACCOUNT("enum.deposite_account_type.VIRTUAL_ACCOUNT");
	
	
	
	private String key;
	
	
	/**
	 * 
	 * @param key
	 */
	private DepositeAccountType(String key){
		this.key = key;
		}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get alias of PaymentInstrument
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	
	public static DepositeAccountType fromValue(int value){
	    
	    for(DepositeAccountType item : DepositeAccountType.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
	
}
