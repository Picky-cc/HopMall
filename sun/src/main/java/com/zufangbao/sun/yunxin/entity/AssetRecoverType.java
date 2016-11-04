package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.BasicEnum;

/**
 * 收款单类型
 * @author louguanyang
 *
 */
public enum AssetRecoverType implements BasicEnum {
	/** 应收还款计划 */
	LOAN_ASSET("enum.asset-recover-type.loan-asset"),
	/** 担保资产 */
	GUARANTEE_ASSET("enum.asset-recover-type.guarantee-asset");
	
	private String key;

	private AssetRecoverType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public static AssetRecoverType convertFromOrderType(OrderType orderType){
		if(orderType==null){
			return null;
		}
		if(orderType==OrderType.NORMAL){
			return AssetRecoverType.LOAN_ASSET;
		} else if(orderType==OrderType.GUARANTEE){
			return AssetRecoverType.GUARANTEE_ASSET;
		}
		return null;
	}
	
}
