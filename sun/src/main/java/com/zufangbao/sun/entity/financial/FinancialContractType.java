package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 信托合约类型
 * @author zhanghongbing
 *
 */
public enum FinancialContractType {

	ConsumerLoan("enum.financial-contract-type-consumerLoan"),
	SmallBusinessMicroCredit("enum.financial-contract-smallBusinessMicroCredit");

	private String key;

	private FinancialContractType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public int getOrdinal() {
		return ordinal();
	}
	
	public static FinancialContractType formValue(int value){
		for(FinancialContractType item:FinancialContractType.values()){
			if(item.ordinal() == value){ 
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
}
