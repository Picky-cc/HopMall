package com.zufangbao.sun.entity.financial;


public class PaymentChannelConfigure {
	/**
	 * 清算号
	 */
	private String clearingNo;
	
	private TransactionChannelConfigure creditChannelConfigure;
	
	private TransactionChannelConfigure debitChannelConfigure;
	
	public TransactionChannelConfigure getCreditChannelConfigure() {
		return creditChannelConfigure;
	}

	public void setCreditChannelConfigure(
			TransactionChannelConfigure creditChannelConfigure) {
		this.creditChannelConfigure = creditChannelConfigure;
	}

	public TransactionChannelConfigure getDebitChannelConfigure() {
		return debitChannelConfigure;
	}

	public void setDebitChannelConfigure(
			TransactionChannelConfigure debitChannelConfigure) {
		this.debitChannelConfigure = debitChannelConfigure;
	}

	public PaymentChannelConfigure() {
		super();
	}
	
	public boolean isComplete(){
		return creditChannelConfigure.isValid() && debitChannelConfigure.isValid();
	}

	public String getClearingNo() {
		return clearingNo;
	}

	public void setClearingNo(String clearingNo) {
		this.clearingNo = clearingNo;
	}
	
}
