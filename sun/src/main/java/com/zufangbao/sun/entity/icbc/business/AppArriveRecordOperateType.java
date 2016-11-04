package com.zufangbao.sun.entity.icbc.business;
/**
 * 
 * @author zjm
 *
 */
public enum AppArriveRecordOperateType {

	/**单条否决*/
	VetoAlone("enum.arriveRecord-operateType.vetoAlone"),
	
	/**与订单否决*/
	VetoWithOrder("enum.arriveRecord-operateType.vetoWithOrder"),
	
	/**与租约否决*/
	VetoWithContract("enum.arriveRecord-operateType.vetoWithContract");
	
	private String key;
	
	/**
	 * 
	 * @param key
	 */
	private AppArriveRecordOperateType(String key){
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
