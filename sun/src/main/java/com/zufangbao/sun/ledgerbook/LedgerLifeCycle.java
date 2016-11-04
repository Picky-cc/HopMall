package com.zufangbao.sun.ledgerbook;

public enum LedgerLifeCycle {
	
	/** 结转 */
	CARRY_OVER("enum.asset-status.carry_over"),

	/** 挂帐 */
	BOOKED("enum.asset-status.booked"),
	
	/** 核销 */
	WRITTEN_OFF ("enum.asset-status.written_off");
	

	private String key;

	/**
	 * @param key
	 */
	private LedgerLifeCycle(String key) {
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

	public static LedgerLifeCycle fromValue(int value) {

		for (LedgerLifeCycle item : LedgerLifeCycle.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

}
