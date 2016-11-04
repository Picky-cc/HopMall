package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum CounterAccountType {
	

	/** 银行账户 */
	BankAccount("enum.counter-account-type.bank-account"),
	/** 虚拟账户 */
	VirtualAccount("enum.counter-account-type.virtual-account"),
	/** 账单 */
	Bill("enum.counter-account-type.bill");
	
	private String key;
	

	
	/**
	 * @param key
	 */
	private CounterAccountType(String key) {
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
	
}
