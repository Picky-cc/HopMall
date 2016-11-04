package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

public enum JournalVoucherType {
	

	/** 线上扣款后台制证 */
	ONLINE_DEDUCT_BACK_ISSUE("enum.journal-voucher-type.online-deduct-back-issue"),
	/** 线下支付对账单 */
	OFFLINE_BILL_ISSUE("enum.journal-voucher-type.offline-bill-issue"),
	/** 银行充值凭证 */
	BANK_CASHFLOW_DEPOSIT("enum.journal-voucher-type.bank-cashflow-deposit"),
	/** 余额冲销账单 */
	VIRTUAL_ACCOUNT_TRANSFER("enum.journal-voucher-type.virtual-account-transfer"),
	/** 余额支付退款 */
	VIRTUAL_ACCOUNT_TRANSFER_ROLL_BACK("enum.journal-voucher-type.virtual-account-transfer-roll-back"),
	/** 商户付款凭证冲销 */
	TRANSFER_BILL_BY_VOUCHER("enum.journal-voucher-type.voucher-write-off-bill"),
	/** 余额提现 */
	VIRTUAL_ACCOUNT_WITHDRAW_DEPOSIT("enum.journal-voucher-type.virtual-account-withdraw-deposit"),
	/** 第三方扣款凭证 */
	THIRD_PARTY_DEDUCT_VOUCHER("enum.journal-voucher-type.third_party_deduct_voucher");
	
	private String key;
	

	
	/**
	 * @param key
	 */
	private JournalVoucherType(String key) {
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
