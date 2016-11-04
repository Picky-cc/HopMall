package com.zufangbao.sun.entity.icbc.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.ledgerbook.AccountSide;

@Entity
@Table(name = "cash_flow")
public class CashFlow {

	@Id
	@GeneratedValue
	private Long id;

	private String cashFlowUuid;// UUID
	
	private CashFlowChannelType cashFlowChannelType;// 现金流类型
	
	private String companyUuid;// company uuid

	private String hostAccountUuid;// 发生账户UUID

	private String hostAccountNo;// 发生账号

	private String hostAccountName;// 发生账户名

	private String counterAccountNo;// 对方账号

	private String counterAccountName;// 对方账户名

	@Column(columnDefinition = "text")
	private String counterAccountAppendix;//

	@Column(columnDefinition = "text")
	private String counterBankInfo;//

	@Enumerated(EnumType.ORDINAL)
	private AccountSide accountSide;// 借贷标志

	private Date transactionTime;// 交易时间

	private BigDecimal transactionAmount;// 发生额（交易金额）

	private BigDecimal balance;// 余额

	private String transactionVoucherNo;// 业务凭证号/企业流水号

	private String bankSequenceNo;// 银行流水号

	private String remark;// 备注

	private String otherRemark;// 其他备注

	@Enumerated(EnumType.ORDINAL)
	private StrikeBalanceStatus strikeBalanceStatus;// 冲账标志
	
	private String tradeUuid;

	/** 已确认金额 **/
	private BigDecimal issuedAmount;

	@Enumerated(EnumType.ORDINAL)
	private AuditStatus auditStatus;

	// 预留字段
	private Date dateFieldOne;

	private Date dateFieldTwo;

	private Date dateFieldThree;

	private Long longFieldOne;

	private Long longFieldTwo;

	private Long longFieldThree;

	private String stringFieldOne;

	private String stringFieldTwo;

	private String stringFieldThree;

	private BigDecimal decimalFieldOne;

	private BigDecimal decimalFieldTwo;

	private BigDecimal decimalFieldThree;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCashFlowUuid() {
		return cashFlowUuid;
	}

	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}

	public CashFlowChannelType getCashFlowChannelType() {
		return cashFlowChannelType;
	}

	public void setCashFlowChannelType(CashFlowChannelType cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public String getCompanyUuid() {
		return companyUuid;
	}

	public void setCompanyUuid(String companyUuid) {
		this.companyUuid = companyUuid;
	}

	public String getHostAccountUuid() {
		return hostAccountUuid;
	}

	public void setHostAccountUuid(String hostAccountUuid) {
		this.hostAccountUuid = hostAccountUuid;
	}

	public String getHostAccountNo() {
		return hostAccountNo;
	}

	public void setHostAccountNo(String hostAccountNo) {
		this.hostAccountNo = hostAccountNo;
	}

	public String getHostAccountName() {
		return hostAccountName;
	}

	public void setHostAccountName(String hostAccountName) {
		this.hostAccountName = hostAccountName;
	}

	public String getCounterAccountNo() {
		return counterAccountNo;
	}

	public void setCounterAccountNo(String counterAccountNo) {
		this.counterAccountNo = counterAccountNo;
	}

	public String getCounterAccountName() {
		return counterAccountName;
	}

	public void setCounterAccountName(String counterAccountName) {
		this.counterAccountName = counterAccountName;
	}

	public String getCounterAccountAppendix() {
		return counterAccountAppendix;
	}

	public void setCounterAccountAppendix(String counterAccountAppendix) {
		this.counterAccountAppendix = counterAccountAppendix;
	}

	public String getCounterBankInfo() {
		return counterBankInfo;
	}

	public void setCounterBankInfo(String counterBankInfo) {
		this.counterBankInfo = counterBankInfo;
	}
	
	private static final String BANK_NAME = "bankName";
	
	public String getCounterBankName() {
		try {
			JSONObject jsonObject = JSON.parseObject(counterBankInfo);
			return jsonObject.getString(BANK_NAME);
		} catch (Exception e) {
			return counterBankInfo;
		}
	}

	private static final String BANK_CODE = "bankCode";
	
	public String getCounterBankCode() {
		try {
			JSONObject jsonObject = JSON.parseObject(counterBankInfo);
			return jsonObject.getString(BANK_CODE);
		} catch (Exception e) {
			return counterBankInfo;
		}
	}
	
	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOtherRemark() {
		return otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}

	public StrikeBalanceStatus getStrikeBalanceStatus() {
		return strikeBalanceStatus;
	}

	public void setStrikeBalanceStatus(StrikeBalanceStatus strikeBalanceStatus) {
		this.strikeBalanceStatus = strikeBalanceStatus;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getTradeUuid() {
		return tradeUuid;
	}

	public void setTradeUuid(String tradeUuid) {
		this.tradeUuid = tradeUuid;
	}
	
	

	public Date getDateFieldOne() {
		return dateFieldOne;
	}

	public void setDateFieldOne(Date dateFieldOne) {
		this.dateFieldOne = dateFieldOne;
	}

	public Date getDateFieldTwo() {
		return dateFieldTwo;
	}

	public void setDateFieldTwo(Date dateFieldTwo) {
		this.dateFieldTwo = dateFieldTwo;
	}

	public Date getDateFieldThree() {
		return dateFieldThree;
	}

	public void setDateFieldThree(Date dateFieldThree) {
		this.dateFieldThree = dateFieldThree;
	}

	public Long getLongFieldOne() {
		return longFieldOne;
	}

	public void setLongFieldOne(Long longFieldOne) {
		this.longFieldOne = longFieldOne;
	}

	public Long getLongFieldTwo() {
		return longFieldTwo;
	}

	public void setLongFieldTwo(Long longFieldTwo) {
		this.longFieldTwo = longFieldTwo;
	}

	public Long getLongFieldThree() {
		return longFieldThree;
	}

	public void setLongFieldThree(Long longFieldThree) {
		this.longFieldThree = longFieldThree;
	}

	public String getStringFieldOne() {
		return stringFieldOne;
	}

	public void setStringFieldOne(String stringFieldOne) {
		this.stringFieldOne = stringFieldOne;
	}

	public String getStringFieldTwo() {
		return stringFieldTwo;
	}

	public void setStringFieldTwo(String stringFieldTwo) {
		this.stringFieldTwo = stringFieldTwo;
	}

	public String getStringFieldThree() {
		return stringFieldThree;
	}

	public void setStringFieldThree(String stringFieldThree) {
		this.stringFieldThree = stringFieldThree;
	}

	public BigDecimal getDecimalFieldOne() {
		return decimalFieldOne;
	}

	public void setDecimalFieldOne(BigDecimal decimalFieldOne) {
		this.decimalFieldOne = decimalFieldOne;
	}

	public BigDecimal getDecimalFieldTwo() {
		return decimalFieldTwo;
	}

	public void setDecimalFieldTwo(BigDecimal decimalFieldTwo) {
		this.decimalFieldTwo = decimalFieldTwo;
	}

	public BigDecimal getDecimalFieldThree() {
		return decimalFieldThree;
	}

	public void setDecimalFieldThree(BigDecimal decimalFieldThree) {
		this.decimalFieldThree = decimalFieldThree;
	}

	public String getAuditStatusMsg() {
		return auditStatus == null ? "" : auditStatus.getChineseMessage();
	}
	
	public String getAccountSideMsg() {
		return accountSide == null ? "" : accountSide.getChineseMessage();
	}
	

	public CashFlow() {
		super();
	}

	public CashFlow(String cashFlowUuid,
			CashFlowChannelType cashFlowChannelType, String companyUuid,
			String hostAccountUuid, String hostAccountNo,
			String hostAccountName, String counterAccountNo,
			String counterAccountName, String counterAccountAppendix,
			String counterBankInfo, AccountSide accountSide,
			Date transactionTime, BigDecimal transactionAmount,
			BigDecimal balance, String transactionVoucherNo,
			String bankSequenceNo, String remark, String otherRemark,
			StrikeBalanceStatus strikeBalanceStatus, String tradeUuid) {
		super();
		this.cashFlowUuid = cashFlowUuid;
		this.cashFlowChannelType = cashFlowChannelType;
		this.companyUuid = companyUuid;
		this.hostAccountUuid = hostAccountUuid;
		this.hostAccountNo = hostAccountNo;
		this.hostAccountName = hostAccountName;
		this.counterAccountNo = counterAccountNo;
		this.counterAccountName = counterAccountName;
		this.counterAccountAppendix = counterAccountAppendix;
		this.counterBankInfo = counterBankInfo;
		this.accountSide = accountSide;
		this.transactionTime = transactionTime;
		this.transactionAmount = transactionAmount;
		this.balance = balance;
		this.transactionVoucherNo = transactionVoucherNo;
		this.bankSequenceNo = bankSequenceNo;
		this.remark = remark;
		this.otherRemark = otherRemark;
		this.strikeBalanceStatus = strikeBalanceStatus;
		this.tradeUuid = tradeUuid;
		this.auditStatus = AuditStatus.CREATE;
	}

	public AuditStatus changeIssuedAmountAndAuditStatus(BigDecimal issuedAmount) {
		if (issuedAmount == null || transactionAmount == null) {
			return null;
		}
		this.issuedAmount = issuedAmount;
		if (issuedAmount.compareTo(BigDecimal.ZERO) == 0) {
			this.auditStatus = AuditStatus.CREATE;
		} else if (issuedAmount.compareTo(transactionAmount) < 0) {
			this.auditStatus = AuditStatus.ISSUING;
		} else if (issuedAmount.compareTo(transactionAmount) == 0) {
			this.auditStatus = AuditStatus.ISSUED;
		} else {
			this.auditStatus = AuditStatus.ISSUING;
		}
		return this.auditStatus;
	}

}
