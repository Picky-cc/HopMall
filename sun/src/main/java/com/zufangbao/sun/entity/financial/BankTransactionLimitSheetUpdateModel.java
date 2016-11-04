package com.zufangbao.sun.entity.financial;

import java.math.BigDecimal;

public class BankTransactionLimitSheetUpdateModel {
	/**
	 * 网关名称
	 */
	private String bankTransactionLimitSheetUuid;
	
	/**
	 * 单笔限额(元)
	 */
	private BigDecimal transactionLimitPerTranscation;
	
	/**
	 * 单日限额（元）
	 */
	private BigDecimal transcationLimitPerDay;
	
	/**
	 * 单月限额（元）
	 */
	private BigDecimal transactionLimitPerMonth;

	public BigDecimal getTransactionLimitPerTranscation() {
		return transactionLimitPerTranscation;
	}

	public void setTransactionLimitPerTranscation(
			BigDecimal transactionLimitPerTranscation) {
		this.transactionLimitPerTranscation = transactionLimitPerTranscation;
	}

	public BigDecimal getTranscationLimitPerDay() {
		return transcationLimitPerDay;
	}

	public void setTranscationLimitPerDay(BigDecimal transcationLimitPerDay) {
		this.transcationLimitPerDay = transcationLimitPerDay;
	}

	public BigDecimal getTransactionLimitPerMonth() {
		return transactionLimitPerMonth;
	}

	public void setTransactionLimitPerMonth(BigDecimal transactionLimitPerMonth) {
		this.transactionLimitPerMonth = transactionLimitPerMonth;
	}

	public String getBankTransactionLimitSheetUuid() {
		return bankTransactionLimitSheetUuid;
	}
	
	public void setBankTransactionLimitSheetUuid(
			String bankTransactionLimitSheetUuid) {
		this.bankTransactionLimitSheetUuid = bankTransactionLimitSheetUuid;
	}
	
	public BankTransactionLimitSheetUpdateModel() {
	}
}
