package com.zufangbao.sun.entity.account;
/**
 * 银行账号类型
 * @author zjm
 *
 */
public enum AccountType {

	/**租房宝账号*/
	ZFB("enum.account-type.ZFB"),
	
	/**风险准备金*/
	CompanyRiskReserve("enum.account-type.CompanyRiskReserve"),
	
	/**正常回款*/
	CompanyPayment("enum.account-type.CompanyPayment"),
	
	/**收租账号*/
	AppCollectRents("enum.account-type.AppCollectRents"),
	
	/**回拨账号*/
	AppPayment("enum.account-type.AppPayment");
	
	private String key;
	
	/**
	 * 
	 * @param key
	 */
	private AccountType(String key){
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
}
