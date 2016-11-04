package com.zufangbao.sun.yunxin.entity;


/**
 * 还款计划
 * @author zhanghongbing
 *
 */
public enum AssetSetActiveStatus{
	
	/** 开启 **/
	OPEN("enum.asset-set-active-status.open"),
	
	/** 作废 **/
	INVALID("enum.asset-set-active-status.invalid"),
	
	/** 提前还款待处理 **/
	PREPAYMENT_WAIT_FOR_PROCESSING("enum.asset-set-active-status.prepayment_wait_for_processing");
	
	private String key;
	
	private AssetSetActiveStatus(String key){
		this.key = key;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public static AssetSetActiveStatus fromValue(int value) {
		for (AssetSetActiveStatus item : AssetSetActiveStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

	public Integer getOrdinal() {
		return Integer.valueOf(this.ordinal());
	}
	
}
