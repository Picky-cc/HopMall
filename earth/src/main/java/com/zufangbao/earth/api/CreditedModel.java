package com.zufangbao.earth.api;

/**
 * 贷记model
 * @author zjm
 *
 */
public class CreditedModel {

	private String payerAccountName;

	private String payerAccountNo;

	private String payingBankNo;

	private String payeeAccountName;

	private String payeeAccountNo;

	private String dueBankNo;

	private String transactionAmount;

	private String currencyCode;

	private String postscript;

	private String transactionUuid;

	public String getPayerAccountName() {
		return payerAccountName;
	}

	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getPayingBankNo() {
		return payingBankNo;
	}

	public void setPayingBankNo(String payingBankNo) {
		this.payingBankNo = payingBankNo;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	public String getDueBankNo() {
		return dueBankNo;
	}

	public void setDueBankNo(String dueBankNo) {
		this.dueBankNo = dueBankNo;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}

	public CreditedModel(String payerAccountName, String payerAccountNo,
			String payingBankNo, String payeeAccountName,
			String payeeAccountNo, String dueBankNo, String transactionAmount,
			String currencyCode, String postscript, String transactionUuid) {
		super();
		this.payerAccountName = payerAccountName;
		this.payerAccountNo = payerAccountNo;
		this.payingBankNo = payingBankNo;
		this.payeeAccountName = payeeAccountName;
		this.payeeAccountNo = payeeAccountNo;
		this.dueBankNo = dueBankNo;
		this.transactionAmount = transactionAmount;
		this.currencyCode = currencyCode;
		this.postscript = postscript;
		this.transactionUuid = transactionUuid;
	}

	public CreditedModel() {
		super();
	}

}
