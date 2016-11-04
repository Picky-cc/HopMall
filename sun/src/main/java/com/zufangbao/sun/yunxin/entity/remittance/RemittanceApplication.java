package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 放款申请表
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_remittance_application")
public class RemittanceApplication {

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 放款申请uuid
	 */
	private String remittanceApplicationUuid;
	
	/**
	 * 放款请求编号
	 */
	private String requestNo;
	
	/**
	 * 信托合同uuid
	 */
	private String financialContractUuid;
	
	/**
	 * 信托合同Id
	 */
	private Long financialContractId;
	
	/**
	 * 信托产品代码
	 */
	private String financialProductCode;
	
	/**
	 * 贷款合同唯一编号
	 */
	private String contractUniqueId;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	
	/**
	 * 计划放款总金额
	 */
	private BigDecimal plannedTotalAmount;
	
	/**
	 * 实际放款总金额
	 */
	private BigDecimal actualTotalAmount;
	
	/**
	 * 审核人
	 */
	private String auditorName;
	
	/**
	 * 审核日期
	 */
	private Date auditTime;
	
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	
	/**
	 * 计划通知次数
	 */
	private int planNotifyNumber;
	
	/**
	 * 实际通知次数
	 */
	private int actualNotifyNumber;
	
	/**
	 * 放款策略 0:多目标放款策略, 1:先放后扣放款策略 
	 */
	@Enumerated(EnumType.ORDINAL)
	private RemittanceStrategy remittanceStrategy;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatus executionStatus;
	
	/**
	 * 交易接收方, 0:本端，1:对端
	 */
	private TransactionRecipient transactionRecipient;
	
	/**
	 * 执行备注
	 */
	private String executionRemark;
	
	/**
	 * 受理日期
	 */
	private Date createTime;
	
	/**
	 * 创建人
	 */
	private String creatorName;
	
	/**
	 * ip地址
	 */
	private String ip;
	
	/**
	 * 最后更新时间
	 */
	private Date lastModifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemittanceApplicationUuid() {
		return remittanceApplicationUuid;
	}

	public void setRemittanceApplicationUuid(String remittanceApplicationUuid) {
		this.remittanceApplicationUuid = remittanceApplicationUuid;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractId) {
		this.financialContractUuid = financialContractId;
	}
	
	public Long getFinancialContractId() {
		return financialContractId;
	}

	public void setFinancialContractId(Long financialContractId) {
		this.financialContractId = financialContractId;
	}

	public String getFinancialProductCode() {
		return financialProductCode;
	}

	public void setFinancialProductCode(String financialProductCode) {
		this.financialProductCode = financialProductCode;
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

	public BigDecimal getPlannedTotalAmount() {
		return plannedTotalAmount == null ? BigDecimal.ZERO : plannedTotalAmount;
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

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public int getPlanNotifyNumber() {
		return planNotifyNumber;
	}

	public void setPlanNotifyNumber(int planNotifyNumber) {
		this.planNotifyNumber = planNotifyNumber;
	}

	public int getActualNotifyNumber() {
		return actualNotifyNumber;
	}

	public void setActualNotifyNumber(int actualNotifyNumber) {
		this.actualNotifyNumber = actualNotifyNumber;
	}

	public RemittanceStrategy getRemittanceStrategy() {
		return remittanceStrategy;
	}

	public void setRemittanceStrategy(RemittanceStrategy remittanceStrategy) {
		this.remittanceStrategy = remittanceStrategy;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	public TransactionRecipient getTransactionRecipient() {
		return transactionRecipient;
	}

	public void setTransactionRecipient(TransactionRecipient transactionRecipient) {
		this.transactionRecipient = transactionRecipient;
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public RemittanceApplication(String remittanceApplicationUuid,
			String requestNo, String financialContractUuid,
			Long financialContractId, String financialProductCode,
			String contractUniqueId, String contractNo,
			BigDecimal plannedTotalAmount, String auditorName, Date auditTime,
			String notifyUrl, RemittanceStrategy remittanceStrategy,
			String remark, String creatorName, String ip) {
		super();
		this.remittanceApplicationUuid = remittanceApplicationUuid;
		this.requestNo = requestNo;
		this.financialContractUuid = financialContractUuid;
		this.financialContractId = financialContractId;
		this.financialProductCode = financialProductCode;
		this.contractUniqueId = contractUniqueId;
		this.contractNo = contractNo;
		this.plannedTotalAmount = plannedTotalAmount;
		this.auditorName = auditorName;
		this.auditTime = auditTime;
		this.notifyUrl = notifyUrl;
		this.remittanceStrategy = remittanceStrategy;
		this.remark = remark;
		this.executionStatus = ExecutionStatus.PROCESSING;
		this.transactionRecipient = TransactionRecipient.LOCAL;
		this.createTime = new Date();
		this.creatorName = creatorName;
		this.ip = ip;
		this.lastModifiedTime = new Date();
	}

	public RemittanceApplication() {
		super();
	}
	
}
