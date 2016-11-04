package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import com.zufangbao.sun.BasicEnum;


/**
 * 
 * @author wukai
 * 支付方式类型
 */
public enum SettlementModes implements BasicEnum{

	/** 信用证 */
	LETTER_OF_CREDIT("enum.settlement-modes.letter_of_credit"),
	/** 保函  */
	LETTER_OF_GUARANTEE("enum.settlement-modes.letter_of_guarantee"),
	/** 托收  */
	COLLECTION("enum.settlement-modes.collection"),
	/** 实缴  */
	REMITTANCE("enum.settlement-modes.remittance"),
	/** POS  */
	POINT_OF_SALE("enum.settlement-modes.point_of_sale"),
	/** 电子支付  */
	ELECTRONIC_PAYMENT("enum.settlement-modes.electronic_payment"),
	/**承兑汇票*/
	ACCEPTANCE("enum.settlement-modes.acceptance"),
	/**支票*/
	CHEQUE("enum.settlement-modes.cheque"),
	/**本票*/
	PROMISSORY_NOTE("enum.settlement-modes.promissory_note"),
	/**表外*/
	OFF_SHEET ("enum.settlement-modes.off-sheet"),
	/**预收款*/
	DEPOSITE_RECEIVED("enum.settlement-modes.deposite-received")
	
	;
	
	private String key;
	
	/**
	 * @param key
	 */
	private SettlementModes(String key) {
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
	public String getName(){
		
		return this.name();
	}
	public static SettlementModes fromValue(int value){
		
		for(SettlementModes item : SettlementModes.values()){
			
			if(value == item.ordinal()){
				
				return item;
			}
		}
		return null;
	}

	public int getOrdinal() {
		return this.ordinal();
	}
 
}
