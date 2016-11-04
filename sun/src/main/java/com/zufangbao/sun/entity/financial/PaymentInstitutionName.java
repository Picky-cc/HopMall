package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 网关
 * @author Lux
 */
public enum PaymentInstitutionName {
	
	/**
	 * 银联广州
	 */
	UNIONPAYGZ("enum.payment-institution-name.UNIONPAYGZ"),
	/**
	 * 超级网银
	 */
	SUPERBANK("enum.payment-institution-name.SUPERBANK"),
	/**
	 * 银企直联
	 */
	DIRECTBANK("enum.payment-institution-name.DIRECTBANK");
	
	private String key;

	private PaymentInstitutionName(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public int getOrdinal() {
		return ordinal();
	}
	
	public static PaymentInstitutionName fromValue(int value){
		for(PaymentInstitutionName item:PaymentInstitutionName.values()){
			if(item.ordinal() == value){ 
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}