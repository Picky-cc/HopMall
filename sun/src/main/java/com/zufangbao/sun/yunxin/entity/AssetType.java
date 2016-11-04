package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum AssetType {
	
	ANMEITUCAR("enum.asset-type.car"),
	
	NONGFENQISEED("enum.asset-type.seed");
	private String key;
	
	private AssetType(String key){
		this.key = key;
	}
	
	
	public String getKey(){
		return this.key;
	}
	
	public static AssetType formValue(int value){
		for(AssetType item:AssetType.values()){
			if( value == item.ordinal()){
				return item;
			}
		}
		return null;
	}
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	

}
