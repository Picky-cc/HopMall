/**
 * 
 */
package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.PaymentWay;

public class TransferApplicationQueryModel{
	private static final int DEFAULT = -1;
	private int paymentWay=DEFAULT;
	private int executingDeductStatus=DEFAULT;
	private String accountName;
	private String paymentNo;
	private String repaymentNo;
	private String billingNo;
	private String payAcNo;
	private String startDateString;
	private String endDateString;
	private String bank;
	private String comment;
	public int getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(int paymentWay) {
		this.paymentWay = paymentWay;
	}
	public PaymentWay getPaymentWayEnum(){
		return PaymentWay.fromValue(paymentWay);
	}
	public int getExecutingDeductStatus() {
		return executingDeductStatus;
	}
	public void setExecutingDeductStatus(int executingDeductStatus) {
		this.executingDeductStatus = executingDeductStatus;
	}
	public ExecutingDeductStatus getExecutingDeductStatusEnum(){
		return ExecutingDeductStatus.fromValue(executingDeductStatus);
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getRepaymentNo() {
		return repaymentNo;
	}
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}
	public String getBillingNo() {
		return billingNo;
	}
	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}
	public String getPayAcNo() {
		return payAcNo;
	}
	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public Date getStartDate(){
		return startDateString==null?null:DateUtils.asDay(startDateString);
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public Date getEndDate(){
		return endDateString==null?null:DateUtils.asDay(endDateString);
	}

	public TransferApplicationQueryModel(){
	}
	public TransferApplicationQueryModel(int paymentWay,
			int executingDeductStatus, String accountName, String paymentNo,
			String repaymentNo, String billingNo, String payAcNo,
			String startDateString, String endDateString) {
		super();
		this.paymentWay = paymentWay;
		this.executingDeductStatus = executingDeductStatus;
		this.accountName = accountName;
		this.paymentNo = paymentNo;
		this.repaymentNo = repaymentNo;
		this.billingNo = billingNo;
		this.payAcNo = payAcNo;
		this.startDateString = startDateString;
		this.endDateString = endDateString;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
