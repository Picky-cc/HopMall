package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;

public class TransferApplicationQueryModel4TransactionDetail {
	
	private static final int DEFAULT = -1;
	
//	private int chargeType = DEFAULT; // Debit or Credit
	
	private int transactionType  = DEFAULT;// Debit or Credit
	
	private String startDateString;
	
	private String endDateString;
	
	private String transferApplicationNo;
	
	private String paymentChannelUuid;
	
	private String isAsc;


	public String getStartDateString() {
		return startDateString;
	}

	public Date getStartDate(){
		return startDateString==null?null:DateUtils.asDay(startDateString);
	}
	
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}
	
	public Date getEndDate(){
		return endDateString==null?null:DateUtils.asDay(endDateString);
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getTransferApplicationNo() {
		return transferApplicationNo;
	}

	public void setTransferApplicationNo(String transferApplicationNo) {
		this.transferApplicationNo = transferApplicationNo;
	}

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}
	
	public TransferApplicationQueryModel4TransactionDetail() {
		super();
	}

	public TransferApplicationQueryModel4TransactionDetail(int transactionType,
			String startDateString, String endDateString,
			String transferApplicationNo, String paymentChannelUuid, String isAsc) {
		super();
		this.transactionType = transactionType;
		this.startDateString = startDateString;
		this.endDateString = endDateString;
		this.transferApplicationNo = transferApplicationNo;
		this.paymentChannelUuid = paymentChannelUuid;
		this.isAsc = isAsc;
	}

	public boolean isValid(){
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return false;
		}
		return true;
	}

	public String getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}


	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

}
