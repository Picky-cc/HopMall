package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class BankTransactionConfigure {
	/**
	 * 银行编号
	 */
	@ExcelVoAttribute(name = "银行编号", column = "A")
	private String bankCode;
	/**
	 * 银行名称
	 */
	@ExcelVoAttribute(name = "银行名称", column = "B")
	private String bankName;
	/**
	 * 单笔限额
	 */
	@ExcelVoAttribute(name = "单笔限额(元)", column = "C")
	private String transactionLimitPerTranscation;
	
	/**
	 * 单日限额
	 */
	@ExcelVoAttribute(name = "单日限额(元)", column = "D")
	private String transcationLimitPerDay;
	/**
	 * 单月限额
	 */
	@ExcelVoAttribute(name = "单月限额(元)", column = "E")
	private String transactionLimitPerMonth;
	/**
	 * 工作模式
	 */
	@ExcelVoAttribute(name = "工作模式", column = "F")
	private String workingMode; // 批量 or 单笔

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTransactionLimitPerTranscation() {
		return transactionLimitPerTranscation;
	}

	public void setTransactionLimitPerTranscation(
			String transactionLimitPerTranscation) {
		this.transactionLimitPerTranscation = transactionLimitPerTranscation;
	}

	public String getTransactionLimitPerMonth() {
		return transactionLimitPerMonth;
	}

	public void setTransactionLimitPerMonth(String transactionLimitPerMonth) {
		this.transactionLimitPerMonth = transactionLimitPerMonth;
	}

	public String getTranscationLimitPerDay() {
		return transcationLimitPerDay;
	}

	public void setTranscationLimitPerDay(String transcationLimitPerDay) {
		this.transcationLimitPerDay = transcationLimitPerDay;
	}

	public String getWorkingMode() {
		return workingMode;
	}

	public void setWorkingMode(String workingMode) {
		this.workingMode = workingMode;
	}

	public BankTransactionConfigure() {
		super();
	}
}