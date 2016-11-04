package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum JournalVoucherStatus {
	

	/** 已建 */
	VOUCHER_CREATED("enum.journal-voucher-status.voucher_created"),
	/** 已制证  */
	VOUCHER_ISSUED("enum.journal-voucher-status.voucher_issued"),
	/** 凭证作废  */
	VOUCHER_LAPSE("enum.journal-voucher-status.voucher_lapse");

	
	private String key;
	

	
	/**
	 * @param key
	 */
	private JournalVoucherStatus(String key) {
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
	
	public static JournalVoucherStatus fromOrdinal(int ordinal){
		
		for(JournalVoucherStatus item : JournalVoucherStatus.values()){
			
			if(ordinal == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}
	
}
