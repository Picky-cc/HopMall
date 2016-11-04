package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum JournalVoucherCheckingLevel {
	

	/** 自动制证  */
	AUTO_BOOKING("enum.journal-voucher-checking-level.auto_booking"),
	/** 人工二次确认  */
	DOUBLE_CHECKING("enum.journal-voucher-checking-level.double_checking");
	

	
	private String key;
	

	
	/**
	 * @param key
	 */
	private JournalVoucherCheckingLevel(String key) {
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
	
	public JournalVoucherCheckingLevel getDefaultCheckingLevel(){
		return JournalVoucherCheckingLevel.DOUBLE_CHECKING;
	}
	
}
