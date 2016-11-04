package com.zufangbao.sun.entity.account;

import com.zufangbao.sun.BasicEnum;

/**
 * 银行账号类型
 * @author zjm
 *
 */
public enum VirtualAccountTransactionType implements BasicEnum{

	/**充值*/
	DEPOSIT("enum.virtual-account-transaction-type.deposit"),
	
	/**支付*/
	INTER_ACCOUNT_REMITTANCE("enum.virtual-account-transaction-type.inter-account-remittance"),
	
	/**退款*/
	INTER_ACCOUNT_BENEFICIARY("enum.virtual-account-transaction-type.inter-account-beneficiary"),
	
	/**提现*/
	WITHDRAW_DEPOSIT("enum.virtual-account-transaction-type.withdraw-deposit"),
	
	/**充值撤销*/
	DEPOSIT_CANCEL("enum.virtual-account-transaction-type.deposit-cancel");
	
	private String key;
	
	/**
	 * 
	 * @param key
	 */
	private VirtualAccountTransactionType(String key){
		this.key = key;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get alias of PaymentInstrument
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	
	public int getOrdinal(){
		return ordinal();
	}
	
}
