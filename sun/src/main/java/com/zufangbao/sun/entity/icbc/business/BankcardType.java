package com.zufangbao.sun.entity.icbc.business;

public enum BankcardType {

	/**借记卡*/
	DebitCard("enum.bankcard-type.debitCard"),
	
	/**信用卡*/
	CreditCard("enum.bankcard-type.creditCard");
	
	private String key;
	
	/**
	 * 
	 * @param key
	 */
	private BankcardType(String key){
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
}
