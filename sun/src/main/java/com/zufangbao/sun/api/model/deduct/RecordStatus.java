package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

	
public enum RecordStatus {
	
	//未挂帐
	UNRECORDED("enum.record-status.unrecorded"),
	//已入账
	RECORDED("enum.record-status.recorded"),
	//已核销
	WRITE_OFF("enum.record-status.write-off");
	
	
   private String key;
	
	/**
	 * @param key
	 */
	private RecordStatus(String key) {
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
	

	
	public int getOrdinal(){
		
		return this.ordinal();
	}
	public static RecordStatus fromOrdinal(int ordinal){
		
		for(RecordStatus item : RecordStatus.values()){
			
			if(ordinal == item.getOrdinal()){
				
				return item;
			}
		}
		return null;
	}
	
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

}
