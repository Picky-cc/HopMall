package com.zufangbao.sun.entity.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

/**
 * 银行限额表
 * @author jx
 */

@Entity
@Table(name = "bank_transaction_limit_sheet")
public class BankTransactionLimitSheet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String bankTransactionLimitSheetUuid;
	
	/**
	 * 网关
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentInstitutionName paymentInstitutionName;
	
	/**
	 * 商户号
	 */
	private String outlierChannelName;
	
	/**
	 * 通道Uuid
	 */
	private String paymentChannelInformationUuid;
	
	/**
	 * 通道付、收款Uuid
	 */
	private String paymentChannelInformationServiceUuid; // ???
	
	/**
	 * 借贷标记
	 */
	private AccountSide accountSide;
	
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
	
	/**
	 * 工作模式
	 */
	@Enumerated(EnumType.ORDINAL)
	private ChargeType workingMode; // 批量 or 单笔
	
	/**
	 * 创建时间
	 */
	private Date creatTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifiedTime;
	
	/**
	 * 作废时间
	 */
	private Date invalidTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankTransactionLimitSheetUuid() {
		return bankTransactionLimitSheetUuid;
	}

	public void setBankTransactionLimitSheetUuid(
			String bankTransactionLimitSheetUuid) {
		this.bankTransactionLimitSheetUuid = bankTransactionLimitSheetUuid;
	}

	public PaymentInstitutionName getPaymentInstitutionName() {
		return paymentInstitutionName;
	}

	public void setPaymentInstitutionName(
			PaymentInstitutionName paymentInstitutionName) {
		this.paymentInstitutionName = paymentInstitutionName;
	}

	public String getOutlierChannelName() {
		return outlierChannelName;
	}

	public void setOutlierChannelName(String outlierChannelName) {
		this.outlierChannelName = outlierChannelName;
	}

	public String getPaymentChannelInformationUuid() {
		return paymentChannelInformationUuid;
	}

	public void setPaymentChannelInformationUuid(
			String paymentChannelInformationUuid) {
		this.paymentChannelInformationUuid = paymentChannelInformationUuid;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public String getPaymentChannelInformationServiceUuid() {
		return paymentChannelInformationServiceUuid;
	}

	public void setPaymentChannelInformationServiceUuid(
			String paymentChannelInformationServiceUuid) {
		this.paymentChannelInformationServiceUuid = paymentChannelInformationServiceUuid;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public ChargeType getWorkingMode() {
		return workingMode;
	}

	public void setWorkingMode(ChargeType workingMode) {
		this.workingMode = workingMode;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public BankTransactionLimitSheet() {
		super();
		Date now = new Date();
		this.creatTime = now;
		this.lastModifiedTime = now;
		this.bankTransactionLimitSheetUuid = UUID.randomUUID().toString();
	}
	
	public void initBy(BankTransactionConfigure btc){
		this.bankCode = btc.getBankCode();
		this.bankName = btc.getBankName();
		this.transactionLimitPerMonth = new BigDecimal(btc.getTransactionLimitPerMonth());
		this.transactionLimitPerTranscation = new BigDecimal(btc.getTransactionLimitPerTranscation());
		this.transcationLimitPerDay = new BigDecimal(btc.getTranscationLimitPerDay());
	}
	
}
