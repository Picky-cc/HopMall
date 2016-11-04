package com.zufangbao.sun.yunxin.entity.api;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 凭证类型
 * @author louguanyang
 * 不要更改此枚举的key,sourceDocumentDetail中的secondType用此枚举的key
 */
public enum VoucherType {
	/********************商户付款凭证类型，代偿，商户担保，回购，差额划拨********************/
	/** 代偿 **/
	PAY("enum.voucher-type.pay"),
	/** 商户担保 **/
	GUARANTEE("enum.voucher-type.guarantee"),
	/** 回购 **/
	REPURCHASE("enum.voucher-type.repurchase"),
	/** 差额划拨 **/
	MERCHANT_REFUND("enum.voucher-type.merchant-refund"),
	/** 第三方发起到扣款到凭证 **/
	THIRD_PARTY_DEDUCTION_VOUCHER("enum.voucher-type.third_party_deduction_voucher"),
	
	/********************主动付款凭证类型, 主动付款，他人代偿********************/
	/** 主动付款 **/
	ACTIVE_PAY("enum.voucher-type.active_pay"),
	/** 他人代偿 **/
	OTHER_PAY("enum.voucher-type.other_pay");
	
	private String key;

	public String getKey() {
		return key;
	}

	private VoucherType(String key) {
		this.key = key;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

	public static VoucherType fromKey(String key) {
		for (VoucherType item : VoucherType.values()) {
			if (StringUtils.equals(item.getKey(), key)) {
				return item;
			}
		}
		return null;
	}
	
	/********************商户付款凭证类型，代偿，商户担保，回购，差额划拨********************/
	public static VoucherType[] business_payment_voucher_type = { VoucherType.PAY, VoucherType.GUARANTEE,
			VoucherType.REPURCHASE, VoucherType.MERCHANT_REFUND };
	
	public static VoucherType business_payment_voucher_type_from_value(int value) {
		return fromValue(value, business_payment_voucher_type);
	}
	
	/********************第三方发起到扣款到凭证********************/
	public static VoucherType[] third_party_deduction_voucher_type = { VoucherType.THIRD_PARTY_DEDUCTION_VOUCHER};
	
	public static VoucherType third_party_deduction_voucher_type_from_value(int value) {
		return fromValue(value, third_party_deduction_voucher_type);
	}
	
	/********************主动付款凭证类型, 主动付款，他人代偿********************/
	public static VoucherType[] active_payment_voucher_type = { VoucherType.ACTIVE_PAY, VoucherType.OTHER_PAY };
	
	public static VoucherType active_payment_voucher_type_from_value(int value) {
		return fromValue(value, active_payment_voucher_type);
	}
	
	public static VoucherType fromValue(int value, VoucherType[] voucherTypes) {
		for (VoucherType item : voucherTypes) {
			if (value == item.ordinal()) {
				return item;
			}
		}
		return null;
	}

}
