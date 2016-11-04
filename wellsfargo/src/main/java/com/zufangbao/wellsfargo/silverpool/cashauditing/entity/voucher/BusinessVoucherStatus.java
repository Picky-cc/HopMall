package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum BusinessVoucherStatus {
	

	VOUCHER_PENDING("enum.business-voucher-status.voucher_pending"),
	VOUCHER_ISSUING("enum.business-voucher-status.voucher_issuing"),
	VOUCHER_ISSUED("enum.business-voucher-status.voucher_issued"),
	VOUCHER_LAPSE("enum.business-voucher-status.voucher_lapse");

	
	private String key;
	

	/**
	 * @param key
	 */
	private BusinessVoucherStatus(String key) {
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
