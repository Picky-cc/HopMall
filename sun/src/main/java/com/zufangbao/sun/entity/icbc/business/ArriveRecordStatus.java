package com.zufangbao.sun.entity.icbc.business;

public enum ArriveRecordStatus {

	/**未扣款*/
	NotDeducted("enum.arriveRecord-status.notDeducted"),
	
	/**待扣款*/
	ToBeDeducted("enum.arriveRecord-status.toBeDeducted"),
	
	/**已扣款*/
	Deducted("enum.arriveRecord-status.deducted"),
	
	/**否决*/
	Vetoed("enum.arriveRecord-status.vetoed");
	
	private String key;
	
	/**
	 * 
	 * @param key
	 */
	private ArriveRecordStatus(String key){
		this.key = key;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get alias of PaymentInstrument
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
}
