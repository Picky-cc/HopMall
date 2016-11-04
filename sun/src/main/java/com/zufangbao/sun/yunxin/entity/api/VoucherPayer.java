package com.zufangbao.sun.yunxin.entity.api;

/**
 * 凭证付款人
 * @author louguanyang
 *
 */
public enum VoucherPayer {
	/** 贷款人 **/
	LOANER("enum.voucher-payer.loaner"),
	/** 商户 **/
	BUSINESS("enum.voucher-payer.business");
	
	private String key;

	public String getKey() {
		return key;
	}

	private VoucherPayer(String key) {
		this.key = key;
	}
	
	public static VoucherPayer fromValue(int value) {
		for (VoucherPayer item : VoucherPayer.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
}
