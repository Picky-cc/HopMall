package com.suidifu.coffer.entity;

import java.util.Date;

/**
 * 查询贷记状态model
 * 
 * @author zjm
 *
 */
public class QueryCreditModel {

	private String accountNo;

	private String beginDate;

	private String endDate;

	private String minAmount;

	private String maxAmount;

	private String transactionVoucherNo;
	
	private Date requestDate;

	public QueryCreditModel() {
		super();
	}

	public QueryCreditModel(String transactionVoucherNo, Date requestDate) {
		super();
		this.transactionVoucherNo = transactionVoucherNo;
		this.requestDate = requestDate;
	}

	public QueryCreditModel(String beginDate, String endDate,
			String transactionVoucherNo) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

}
