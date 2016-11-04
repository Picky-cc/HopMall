package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum JournalVoucherCompleteness {
	/** 现金流条目流缺失  */
	CASHFLOW_MISSING("enum.journal-voucher-status.cashflow_missing"),
	/** 交易通知条目缺失  */
	NOTIFICATION_MISSING("enum.journal-voucher-status.notification_missing"),
	/** 交易通知歧义  */
	NOTIFICATION_AMBIGUOUS("enum.journal-voucher-status.notification_ambiguous"),
	/** 条目完整  */
	COMPLETE("enum.journal-voucher-status.complete"),;
	
	private String key;
	

	
	/**
	 * @param key
	 */
	private JournalVoucherCompleteness(String key) {
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
