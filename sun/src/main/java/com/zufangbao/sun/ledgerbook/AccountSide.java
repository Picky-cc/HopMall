package com.zufangbao.sun.ledgerbook;

import com.zufangbao.sun.BasicEnum;
import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum AccountSide implements BasicEnum{
	/** 贷 */
	CREDIT("enum.account-side.credit"),

	/** 借 */
	DEBIT("enum.account-side.debit"),
	/**借贷*/
	BOTH("enum.account-side.debit"),;
	

	private String key;

	/**
	 * @param key
	 */
	private AccountSide(String key) {
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
	public int getOrdinal(){
		return this.ordinal();
	}
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

	public static AccountSide fromValue(int value) {

		for (AccountSide item : AccountSide.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public AccountSide counterSide()
	{
		if(this.equals(CREDIT)) return this.DEBIT;
		if(this.equals(DEBIT)) return this.CREDIT;
		 return this.BOTH;
	}

}
