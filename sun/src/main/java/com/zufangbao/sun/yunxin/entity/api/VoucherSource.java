package com.zufangbao.sun.yunxin.entity.api;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 凭证来源类型
 * @author louguanyang
 *
 * 不要更改此枚举的key,sourceDocumentDetail中的firstType用此枚举的key
 */
public enum VoucherSource {
	
	/** 商户付款凭证 **/
	BUSINESS_PAYMENT_VOUCHER("enum.voucher-source.business-payment-voucher"),
	
	/** 第三方发起扣款凭证 **/
	THIRD_PARTY_DEDUCTION_VOUCHER("enum.voucher-source.third-party-deduction-voucher"),
	
	/** 主动付款凭证 **/
	ACTIVE_PAYMENT_VOUCHER("enum.voucher-source.active_payment_voucher"); 
	
	private String key;

	public String getKey() {
		return key;
	}

	private VoucherSource(String key) {
		this.key = key;
	}
	
	public static VoucherSource fromValue(int value) {

		for (VoucherSource item : VoucherSource.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

	public static VoucherSource fromKey(String key) {
		for (VoucherSource item : VoucherSource.values()) {
			if (StringUtils.equals(item.getKey(), key)) {
				return item;
			}
		}
		return null;
	}
}
