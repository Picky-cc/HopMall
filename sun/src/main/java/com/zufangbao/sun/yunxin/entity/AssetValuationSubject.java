package com.zufangbao.sun.yunxin.entity;

public enum AssetValuationSubject {
	/** 还款额 */
	REPAYMENT_AMOUNT("enum.asset-valuation-subject.repaymentAmount"),
	
	/** 罚息 */
	PENALTY_INTEREST("enum.asset-valuation-subject.penaltyInterest"),
	
	/** 金额调整 */
	AMOUNT_ADJUSTMENT("enum.asset-valuation-subject.amountAdjustment");
	
	
	
	private String key;
	
	/**
	 * @param key
	 */
	private AssetValuationSubject(String key) {
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
	
	
	public static AssetValuationSubject fromValue(int value){
	    
	    for(AssetValuationSubject item : AssetValuationSubject.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
}
