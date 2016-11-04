package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_remittance_application_detail")
public class RemittanceApplicationDetail {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 放款申请明细uuid
	 */
	private String remittanceApplicationDetailUuid;
	/**
	 * 放款申请uuid
	 */
	private String remittanceApplicationUuid;

	/**
	 * 信托合同uuid
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemittanceApplicationDetailUuid() {
		return remittanceApplicationDetailUuid;
	}

	public void setRemittanceApplicationDetailUuid(
			String remittanceApplicationDetailUuid) {
		this.remittanceApplicationDetailUuid = remittanceApplicationDetailUuid;
	}

	public String getRemittanceApplicationUuid() {
		return remittanceApplicationUuid;
	}

	public void setRemittanceApplicationUuid(String remittanceApplicationUuid) {
		this.remittanceApplicationUuid = remittanceApplicationUuid;
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
		return actualTotalAmount;
	}

	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	public String getExecutionRemark() {
		return executionRemark;
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

	public CertificateType getCpIdType() {
		return cpIdType;
	}

	public void setCpIdType(CertificateType cpIdType) {
		this.cpIdType = cpIdType;
	}

	public RemittanceApplicationDetail(String remittanceApplicationDetailUuid,
			String remittanceApplicationUuid, String financialContractUuid,
			Long financialContractId, String businessRecordNo,
			int priorityLevel, String cpBankCode, String cpBankCardNo,
			String cpBankAccountHolder, CertificateType cpIdType,
			String cpIdNumber, String cpBankProvince, String cpBankCity,
			String cpBankName, Date plannedPaymentDate,
			BigDecimal plannedTotalAmount, String creatorName) {
		super();
		this.remittanceApplicationDetailUuid = remittanceApplicationDetailUuid;
		this.remittanceApplicationUuid = remittanceApplicationUuid;
		this.financialContractUuid = financialContractUuid;
		this.financialContractId = financialContractId;
		this.businessRecordNo = businessRecordNo;
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
		this.executionStatus = ExecutionStatus.PROCESSING;
		this.createTime = new Date();
		this.creatorName = creatorName;
		this.lastModifiedTime = new Date();
	}

	public RemittanceApplicationDetail() {
		super();
	}
	
}
