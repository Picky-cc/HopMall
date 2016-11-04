package com.suidifu.coffer.entity;

public class CreditResult extends ResultBaseModel {

	private String transactionVoucherNo;

	private String bankSequenceNo;

	private String transactionAmount;

	private BusinessRequestStatus requestStatus;

	private BusinessProcessStatus processStatus;

	public CreditResult() {
		super();
	}
	
	public CreditResult(String errMsg) {
		super(errMsg);
	}

	public CreditResult(String errCode, String errMsg) {
		super(errCode, errMsg);
	}
	
	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getBankSequenceNo() {
		return bankSequenceNo;
	}

	public void setBankSequenceNo(String bankSequenceNo) {
		this.bankSequenceNo = bankSequenceNo;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BusinessRequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(BusinessRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public BusinessProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(BusinessProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

}
