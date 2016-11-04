/**
 * 
 */
package com.zufangbao.sun.entity.icbc.business;


/**
 * @author wukai
 * 流水通道类型
 */
public enum CashFlowChannelType {

	/**
	 * 银企直连的流水
	 */
	DirectBank("enum.cash-flow-type.direct-bank"),
	
	/**
	 * 支付宝的流水
	 */
	Alipay("enum.cash-flow-type.alipay"),
	
	/**
	 * 银联的流水
	 */
	Unionpay("enum.cash-flow-type.unionpay"),
	
	/**
	 * 虚拟账户的流水
	 */
	VirtualAccount("enum.cash-flow-type.virtual-account")
	
	;
	private String key;
	
	private CashFlowChannelType(String key){
		
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public static CashFlowChannelType fromValue(int value) {
		for (CashFlowChannelType item : CashFlowChannelType.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
}
