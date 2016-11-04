/**
 * 
 */
package com.zufangbao.sun.entity.icbc.business;

import org.apache.commons.lang.StringUtils;

/**
 * @author wukai
 * 业务类型
 */
public enum TransactionType {
	
	/**
	 * 转账
	 */
	Transfer("转账"),
	
	/**
	 * 收费
	 */
	Charge("收费"),
	
	/**
	 * 充值
	 */
	Recharge("充值"),

	/**
	 * 提现
	 */
	Withdrawal("提现"),
	
	/**
	 * 退票
	 */
	Dishonour("退票"),
	
	/**
	 * 在线支付
	 */
	OnlinePay("在线支付")
	
	;
	
	private String value;
	
	private TransactionType(String value){
		
		this.value = value;
	}
	
	public String getAlias() {
		return this.value.substring(this.value.lastIndexOf(".") + 1);
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}

	public String getValue() {
		return value;
	}
	
	public static TransactionType  fromValue(String value){
		
		for(TransactionType item : TransactionType.values()){
			
			if(StringUtils.equals(value, item.getValue())){
				
				return item;
			}
		}
		return null;
	}
}
