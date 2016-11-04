package com.zufangbao.sun.entity.financial;

public enum GateWay {
	SuperBank("enum.gate-way.super-bank","0001"),
	UnionPay("enum.gate-way.union-pay","0002"),
	DirectBank("enum.gate-way.direct-bank","0003"),
	PABDirectBank("enum.gate-way.pab-direct-bank","0004");

	private String key;
	private String code;
	private GateWay(String key,String code) {
		this.key = key;
		this.code = code;
	}
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public String getKey(){
		return key;
	}
	public String getCode(){
		return code;
	}
	public int getordinal() {
		return this.ordinal();
	}
	public static GateWay fromValue(int value) {
		for (GateWay item : GateWay.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public static GateWay fromCode(String code) {
		for (GateWay item : GateWay.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	
}
