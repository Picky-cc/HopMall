package com.zufangbao.sun.entity.financial;

import java.math.BigDecimal;

import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public class BankTransactionLimitSheetListModel {
	
	/**
	 * uuid
	 */
	private String bankTransactionLimitSheetUuid;
	
	/**
	 * 网关名称
	 */
	private String paymentInstitutionName;
	
	/**
	 * 网关下标
	 */
	private int paymentInstitutionOrdinal=-1;;
	
	/**
	 * 商户号
	 */
	private String outlierChannelName;
	
	/**
	 * 收付类型
	 */
	private String accountSide;
	
	/**
	 * 收付类型  下标
	 */
	private int accountSideOrdinal=-1;

	/**
	 * 银行编号
	 */
	private String bankCode;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
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
	
	public String getBankTransactionLimitSheetUuid() {
		return bankTransactionLimitSheetUuid;
	}

	public void setBankTransactionLimitSheetUuid(
			String bankTransactionLimitSheetUuid) {
		this.bankTransactionLimitSheetUuid = bankTransactionLimitSheetUuid;
	}

	public String getPaymentInstitutionName() {
		return paymentInstitutionName;
	}

	public void setPaymentInstitutionName(String paymentInstitutionName) {
		this.paymentInstitutionName = paymentInstitutionName;
	}
	
	public int getPaymentInstitutionOrdinal() {
		return paymentInstitutionOrdinal;
	}

	public void setPaymentInstitutionOrdinal(int paymentInstitutionOrdinal) {
		this.paymentInstitutionOrdinal = paymentInstitutionOrdinal;
	}

	public int getAccountSideOrdinal() {
		return accountSideOrdinal;
	}

	public void setAccountSideOrdinal(int accountSideOrdinal) {
		this.accountSideOrdinal = accountSideOrdinal;
	}

	public String getOutlierChannelName() {
		return outlierChannelName;
	}

	public void setOutlierChannelName(String outlierChannelName) {
		this.outlierChannelName = outlierChannelName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

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

	public BankTransactionLimitSheetListModel() {
	}
	
	public BankTransactionLimitSheetListModel(BankTransactionLimitSheet sheet) {
		this.bankTransactionLimitSheetUuid = sheet.getBankTransactionLimitSheetUuid();
		this.paymentInstitutionName = sheet.getPaymentInstitutionName()==null?"":sheet.getPaymentInstitutionName().getChineseMessage();
		this.paymentInstitutionOrdinal = sheet.getPaymentInstitutionName()==null?-1:sheet.getPaymentInstitutionName().ordinal();
		this.outlierChannelName = sheet.getOutlierChannelName();
		if(sheet.getAccountSide() == AccountSide.CREDIT){
			this.accountSide = "代付";
			this.accountSideOrdinal = AccountSide.CREDIT.ordinal();
		}else if(sheet.getAccountSide() == AccountSide.DEBIT){
			this.accountSide = "代收";
			this.accountSideOrdinal = AccountSide.DEBIT.ordinal();
		}else{
			this.accountSide = "";
			this.accountSideOrdinal = -1;
		}
		this.bankCode = sheet.getBankCode();
		this.bankName = sheet.getBankName();
		this.transactionLimitPerTranscation = sheet.getTransactionLimitPerTranscation();
		this.transcationLimitPerDay = sheet.getTranscationLimitPerDay();
		this.transactionLimitPerMonth = sheet.getTransactionLimitPerMonth();
	}

}
