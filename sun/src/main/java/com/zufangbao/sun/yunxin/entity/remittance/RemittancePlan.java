package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.sun.constant.CodeNameMapper;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.utils.EnumUtil;

/**
 * 放款计划表
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_remittance_plan")
public class RemittancePlan {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 放款计划uuid
	 */
	private String remittancePlanUuid;
	
	/**
	 * 放款申请uuid
	 */
	private String remittanceApplicationUuid;
	
	/**
	 * 放款申请明细uuid
	 */
	private String remittanceApplicationDetailUuid;

	/**
	 * 信托合同Uuid
	 */
	private String financialContractUuid;

	/**
	 * 信托合同Id
	 */
	private Long financialContractId;
	
	/**
	 * 业务记录号
	 */
	private String businessRecordNo;
	
	/**
	 * 贷款合同唯一编号
	 */
	private String contractUniqueId;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;

	/**
	 * 支付网关0:广州银联，1:超级网银，2:银企直连
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentInstitutionName paymentGateway;

	/**
	 * 支付通道uuid
	 */
	private String paymentChannelUuid;
	
	/**
	 * 支付通道名称
	 */
	private String paymentChannelName;
	
	/**
	 * 支付网关账户名
	 */
	private String pgAccountName;
	
	/**
	 * 支付网关帐户号
	 */
	private String pgAccountNo;
	
	/**
	 * 支付网关清算帐户
	 */
	private String pgClearingAccount;

	/**
	 * 交易类型0:贷，1:借
	 */
	@Enumerated(EnumType.ORDINAL)
	private AccountSide transactionType;
	
	/**
	 * 交易备注
	 */
	private String transactionRemark;

	/**
	 * 优先级
	 */
	private int priorityLevel;
	
	/**
	 * 恒生银行编码
	 */
	private String cpBankCode;
	
	/**
	 * 交易对手方银行卡号
	 */
	private String cpBankCardNo;
	
	/**
	 * 交易对手方银行卡持有人
	 */
	private String cpBankAccountHolder;

	/**
	 * 交易对手方证件类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private CertificateType cpIdType;

	/**
	 * 交易对手方证件号
	 */
	private String cpIdNumber;

	/**
	 * 交易对手方开户行所在省
	 */
	private String cpBankProvince;

	/**
	 * 交易对手方开户行所在市
	 */
	private String cpBankCity;
	
	/**
	 * 交易对手方开户行名称
	 */
	private String cpBankName;

	/**
	 * 计划支付时间
	 */
	private Date plannedPaymentDate;
	
	/**
	 * 实际支付完成时间
	 */
	private Date completePaymentDate;

	/**
	 * 计划交易总金额
	 */
	private BigDecimal plannedTotalAmount;
	
	/**
	 * 实际交易总金额
	 */
	private BigDecimal actualTotalAmount;
	
	/**
	 * 执行前置条件
	 */
	private String executionPrecond;

	/**
	 * 执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatus executionStatus;

	/**
	 * 执行备注
	 */
	private String executionRemark;

	private Date createTime;

	private String creatorName;

	private Date lastModifiedTime;
	
	/**
	 * 交易流水号
	 */
	private String transactionSerialNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemittancePlanUuid() {
		return remittancePlanUuid;
	}

	public void setRemittancePlanUuid(String remittancePlanUuid) {
		this.remittancePlanUuid = remittancePlanUuid;
	}

	public String getRemittanceApplicationUuid() {
		return remittanceApplicationUuid;
	}

	public void setRemittanceApplicationUuid(String remittanceApplicationUuid) {
		this.remittanceApplicationUuid = remittanceApplicationUuid;
	}

	public String getRemittanceApplicationDetailUuid() {
		return remittanceApplicationDetailUuid;
	}

	public void setRemittanceApplicationDetailUuid(
			String remittanceApplicationDetailUuid) {
		this.remittanceApplicationDetailUuid = remittanceApplicationDetailUuid;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}
	
	public Long getFinancialContractId() {
		return financialContractId;
	}

	public void setFinancialContractId(Long financialContractId) {
		this.financialContractId = financialContractId;
	}
	
	public String getBusinessRecordNo() {
		return businessRecordNo;
	}

	public void setBusinessRecordNo(String businessRecordNo) {
		this.businessRecordNo = businessRecordNo;
	}

	public String getContractUniqueId() {
		return contractUniqueId;
	}

	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public PaymentInstitutionName getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(PaymentInstitutionName paymentGateWay) {
		this.paymentGateway = paymentGateWay;
	}

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}
	
	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public String getPgAccountName() {
		return pgAccountName;
	}

	public void setPgAccountName(String pgAccount) {
		this.pgAccountName = pgAccount;
	}
	
	public String getPgAccountNo() {
		return pgAccountNo;
	}

	public void setPgAccountNo(String paAccountNo) {
		this.pgAccountNo = paAccountNo;
	}

	public String getPgClearingAccount() {
		return pgClearingAccount;
	}

	public void setPgClearingAccount(String pgClearingAccount) {
		this.pgClearingAccount = pgClearingAccount;
	}

	public AccountSide getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(AccountSide transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionRemark() {
		return transactionRemark;
	}

	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}
	
	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getCpBankCode() {
		return cpBankCode;
	}

	public void setCpBankCode(String cpBankCode) {
		this.cpBankCode = cpBankCode;
	}

	public String getCpBankCardNo() {
		return cpBankCardNo;
	}

	public void setCpBankCardNo(String cpBankCardNo) {
		this.cpBankCardNo = cpBankCardNo;
	}

	public String getCpBankAccountHolder() {
		return cpBankAccountHolder;
	}

	public void setCpBankAccountHolder(String cpBankAccountHolder) {
		this.cpBankAccountHolder = cpBankAccountHolder;
	}

	public CertificateType getCpIdType() {
		return cpIdType;
	}

	public void setCpIdType(CertificateType cpIdType) {
		this.cpIdType = cpIdType;
	}

	public String getCpIdNumber() {
		return cpIdNumber;
	}

	public void setCpIdNumber(String cpIdNumber) {
		this.cpIdNumber = cpIdNumber;
	}

	public String getCpBankProvince() {
		return cpBankProvince;
	}

	public void setCpBankProvince(String cpBankProvince) {
		this.cpBankProvince = cpBankProvince;
	}

	public String getCpBankCity() {
		return cpBankCity;
	}

	public void setCpBankCity(String cpBankCity) {
		this.cpBankCity = cpBankCity;
	}

	public String getCpBankName() {
		return cpBankName;
	}

	public void setCpBankName(String cpBankName) {
		this.cpBankName = cpBankName;
	}

	public Date getPlannedPaymentDate() {
		return plannedPaymentDate;
	}

	public void setPlannedPaymentDate(Date plannedPaymentDate) {
		this.plannedPaymentDate = plannedPaymentDate;
	}

	public Date getCompletePaymentDate() {
		return completePaymentDate;
	}

	public void setCompletePaymentDate(Date completePaymentDate) {
		this.completePaymentDate = completePaymentDate;
	}

	public BigDecimal getPlannedTotalAmount() {
		return plannedTotalAmount;
	}

	public void setPlannedTotalAmount(BigDecimal plannedTotalAmount) {
		this.plannedTotalAmount = plannedTotalAmount;
	}

	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount == null ? BigDecimal.ZERO : actualTotalAmount;
	}

	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	public String getExecutionPrecond() {
		return executionPrecond;
	}

	public void setExecutionPrecond(String executionPrecond) {
		this.executionPrecond = executionPrecond;
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}
	
	public String getExecutionStatusMsg() {
		return executionStatus == null ? "" : executionStatus.getChineseMessage();
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	public String getExecutionRemark() {
		return executionRemark == null ? "" : executionRemark;
	}

	public void setExecutionRemark(String executionRemark) {
		this.executionRemark = executionRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	public String getTransactionSerialNo() {
		return transactionSerialNo;
	}

	public void setTransactionSerialNo(String transactionSerialNo) {
		this.transactionSerialNo = transactionSerialNo;
	}

	public RemittancePlan() {
		super();
	}

	public RemittancePlan(String remittancePlanUuid,
			String remittanceApplicationUuid,
			String remittanceApplicationDetailUuid,
			String financialContractUuid, Long financialContractId,
			String businessRecordNo, String contractUniqueId,
			String contractNo, PaymentInstitutionName paymentGateWay,
			String paymentChannelUuid, String paymentChannelName, String pgAccount,
			String pgClearingAccount, int transactionType,
			String transactionRemark, int priorityLevel, String cpBankCode,
			String cpBankCardNo, String cpBankAccountHolder,
			CertificateType cpIdType, String cpIdNumber, String cpBankProvince,
			String cpBankCity, String cpBankName, Date plannedPaymentDate,
			BigDecimal plannedTotalAmount, String executionPrecond,
			String creatorName) {
		super();
		this.remittancePlanUuid = remittancePlanUuid;
		this.remittanceApplicationUuid = remittanceApplicationUuid;
		this.remittanceApplicationDetailUuid = remittanceApplicationDetailUuid;
		this.financialContractUuid = financialContractUuid;
		this.financialContractId = financialContractId;
		this.businessRecordNo = businessRecordNo;
		this.contractUniqueId = contractUniqueId;
		this.contractNo = contractNo;
		this.paymentGateway = paymentGateWay;
		this.paymentChannelUuid = paymentChannelUuid;
		this.paymentChannelName = paymentChannelName;
		this.pgAccountName = pgAccount;
		this.pgClearingAccount = pgClearingAccount;
		this.transactionType = EnumUtil.fromOrdinal(AccountSide.class, transactionType);
		this.transactionRemark = transactionRemark;
		this.priorityLevel = priorityLevel;
		this.cpBankCode = cpBankCode;
		this.cpBankCardNo = cpBankCardNo;
		this.cpBankAccountHolder = cpBankAccountHolder;
		this.cpIdType = cpIdType;
		this.cpIdNumber = cpIdNumber;
		this.cpBankProvince = cpBankProvince;
		this.cpBankCity = cpBankCity;
		this.cpBankName = cpBankName;
		this.plannedPaymentDate = plannedPaymentDate;
		this.plannedTotalAmount = plannedTotalAmount;
		this.actualTotalAmount = BigDecimal.ZERO;
		this.executionPrecond = executionPrecond;
		this.executionStatus = ExecutionStatus.PROCESSING;
		this.createTime = new Date();
		this.creatorName = creatorName;
		this.lastModifiedTime = new Date();
	}
	
	@JSONField(serialize = false)
	public boolean isCreditSuccess() {
		if(this.transactionType == AccountSide.CREDIT && this.executionStatus == ExecutionStatus.SUCCESS) {
			return true;
		}
		return false;
	}
	
	@JSONField(serialize = false)
	public boolean isCredit() {
		if(this.transactionType == AccountSide.CREDIT) {
			return true;
		}
		return false;
	}
	
	@JSONField(serialize = false)
	public Date getMaxCompletePaymentDate(Date date) {
		if(this.completePaymentDate != null && date != null) {
			if(this.completePaymentDate.before(date)) {
				return date;
			}else {
				return this.completePaymentDate;
			}
		}else {
			return date == null ? this.completePaymentDate : date;
		}
	}
	
	public AccountInfoModel getPgAccountInfo(){ // 付款方
		if(this.transactionType == AccountSide.CREDIT){
			return genPgAccountInfo();
		}else if(this.transactionType == AccountSide.DEBIT){
			return genCpAccountInfo();
		}else{
			return new AccountInfoModel();
		}
	}
	
	public AccountInfoModel getCpAccountInfo(){ // 收款方
		if(this.transactionType == AccountSide.CREDIT){
			return genCpAccountInfo();
		}else if(this.transactionType == AccountSide.DEBIT){
			return genPgAccountInfo();
		}else{
			return new AccountInfoModel();
		}
	}
	
	public AccountInfoModel genPgAccountInfo(){
		return new AccountInfoModel(this.pgAccountName, this.pgAccountNo, "", "", "", "", "");
	}
	
	public AccountInfoModel genCpAccountInfo(){
		return new AccountInfoModel(this.cpBankAccountHolder,
				this.cpBankCardNo, this.cpBankName,
				CodeNameMapper.provinceMapper.get(this.cpBankProvince),
				CodeNameMapper.cityMapper.get(this.cpBankCity), this.cpIdNumber, this.cpBankCode);
	}
	
	public String getUuid(){
		return this.remittancePlanUuid;
	}
	
}