package com.zufangbao.yunxin.entity.api.syncdata.model;


/**
 * 
 * @author zhenghangbo
 * 
 * 数据同步状态 ： 数据同步只记录已还款的数据
 * 0  未同步
 * 1  已同步
 */

public enum DataSyncStatus {
	
	
	//未同步
	NO_SYNC("enum.data_sync_status.no_sync"),
	//已同步
	HAS_SYNCED("enum.data_sync_status.has_synced");
	
	private  String key;
	
	
	private DataSyncStatus(String key ){
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}

	

}
