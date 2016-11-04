package com.suidifu.coffer.entity;

import java.util.Date;

/**
 * 
 * @author zjm
 *
 */
public class QueryCreditResult extends ResultBaseModel {

	private String sourceAccountNo;

	private String sourceAccountName;

	private String destinationAccountNo;

	private String destinationAccountName;

	private String transactionAmount;

	private Date acceptanceTime;

	private String transactionVoucherNo;
	
	private String channelSequenceNo;

	private BusinessRequestStatus requestStatus;

	private BusinessProcessStatus processStatus;
	
	private String businessResultMsg;
	
	private Date businessSuccessTime;

	public QueryCreditResult() {
		super();
		requestStatus = BusinessRequestStatus.NOTRECEIVE;
	}
	
	public QueryCreditResult(String errMsg) {
		super(errMsg);
	}

	public String getSourceAccountNo() {
		return sourceAccountNo;
	}

	public void setSourceAccountNo(String sourceAccountNo) {
		this.sourceAccountNo = sourceAccountNo;
	}

	public String getSourceAccountName() {
		return sourceAccountName;
	}

	public void setSourceAccountName(String sourceAccountName) {
		this.sourceAccountName = sourceAccountName;
	}

	public String getDestinationAccountNo() {
		return destinationAccountNo;
	}

	public void setDestinationAccountNo(String destinationAccountNo) {
		this.destinationAccountNo = destinationAccountNo;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Date acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public Date getBusinessSuccessTime() {
		return businessSuccessTime;
	}

	public void setBusinessSuccessTime(Date businessSuccessTime) {
		this.businessSuccessTime = businessSuccessTime;
	}

	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getChannelSequenceNo() {
		return channelSequenceNo;
	}

	public void setChannelSequenceNo(String channelSequenceNo) {
		this.channelSequenceNo = channelSequenceNo;
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

	public String getBusinessResultMsg() {
		return businessResultMsg;
	}

	public void setBusinessResultMsg(String businessResultMsg) {
		this.businessResultMsg = businessResultMsg;
	}

	public boolean isBusinessSuccess() {
		return BusinessRequestStatus.FINISH.equals(requestStatus) && BusinessProcessStatus.SUCCESS.equals(processStatus);
	}
	
	public boolean isBusinessFailed() {
		return BusinessRequestStatus.FINISH.equals(requestStatus) && BusinessProcessStatus.FAIL.equals(processStatus);
	}

}
