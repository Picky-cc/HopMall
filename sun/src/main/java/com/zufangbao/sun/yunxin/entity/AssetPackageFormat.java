package com.zufangbao.sun.yunxin.entity;

public enum AssetPackageFormat {

	AVERAGE_CAPITAL_PLUS_INTEREST("enum.repayment-way.averageCapitalPlusInterest"),
	SAW_TOOTH("enum.repayment-way.sawtooth");
	
	private String key;
	
	private AssetPackageFormat(String code){
		this.key = code;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public static AssetPackageFormat formValue(int value){
		for(AssetPackageFormat item:AssetPackageFormat.values()){
			if( value == item.ordinal()){
				return item;
			}
		}
		return null;
	}
	public int getOrdinal(){
		return this.ordinal();
	}
}
