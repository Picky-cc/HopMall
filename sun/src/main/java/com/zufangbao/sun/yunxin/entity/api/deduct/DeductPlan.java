package com.zufangbao.sun.yunxin.entity.api.deduct;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.utils.BusinessUtils;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.remittance.CertificateType;

/**
 * 放款计划表
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_deduct_plan")
public class DeductPlan {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 扣款计划uuid
	 */
	private String deductPlanUuid;
	
	/**
	 * 扣款申请uuid
	 */
	private String deductApplicationUuid;
	
	/**
	 *扣款申请明细uuid
	 */
	private String deductApplicationDetailUuid;

	/**
	 * 信托合约Id
	 */
	private String  financialContractUuid;
	
	private String  contractUniqueId;
	
	private String  contractNo;
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
	 * 支付网关账户（或商户号）
	 */
	private String pgAccount;
	
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

	
	 private String executionRemark;
	/**
	 * 执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	@Enumerated(EnumType.ORDINAL)
	private DeductApplicationExecutionStatus executionStatus;

	private Date createTime;

	private String creatorName;

	private Date lastModifiedTime;
	
	/**
	 * 交易流水号
	 */
	private String transactionSerialNo;

	
	
	public DeductPlan(String deductPlanUuid,
			String deductApplicationUuid,
			String deductApplicationDetailUuid, String financialContractUuid,
			String contractUniqueId, String contractNo,
			PaymentInstitutionName paymentGateWay, String paymentChannelUuid,
			String pgAccount, String pgClearingAccount, int transactionType,
			String transactionRemark, String cpBankCode, String cpBankCardNo,
			String cpBankAccountHolder, CertificateType cpIdType,
			String cpIdNumber, String cpBankProvince, String cpBankCity,
			String cpBankName, Date plannedPaymentDate,
			BigDecimal plannedTotalAmount, String executionPrecond,
			String creatorName) {
		super();
		this.deductPlanUuid = deductPlanUuid;
		this.deductApplicationUuid = deductApplicationUuid;
		this.deductApplicationDetailUuid = deductApplicationDetailUuid;
		this.financialContractUuid = financialContractUuid;
		this.contractUniqueId = contractUniqueId;
		this.contractNo = contractNo;
		this.paymentGateway = paymentGateWay;
		this.paymentChannelUuid = paymentChannelUuid;
		this.pgAccount = pgAccount;
		this.pgClearingAccount = pgClearingAccount;
		this.transactionType = EnumUtil.fromOrdinal(AccountSide.class, transactionType);
		this.transactionRemark = transactionRemark;
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
		this.executionStatus = DeductApplicationExecutionStatus.PROCESSING;
		this.createTime = new Date();
		this.creatorName = creatorName;
		this.lastModifiedTime = new Date();
	}


	public DeductPlan(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeductPlanUuid() {
		return deductPlanUuid;
	}

	public void setDeductPlanUuid(String deductPlanUuid) {
		this.deductPlanUuid = deductPlanUuid;
	}

	public String getDeductApplicationUuid() {
		return deductApplicationUuid;
	}



	public void setDeductApplicationUuid(String deductApplicationUuid) {
		this.deductApplicationUuid = deductApplicationUuid;
	}



	public String getDeductApplicationDetailUuid() {
		return deductApplicationDetailUuid;
	}



	public void setDeductApplicationDetailUuid(String deductApplicationDetailUuid) {
		this.deductApplicationDetailUuid = deductApplicationDetailUuid;
	}



	public String getFinancialContractUuid() {
		return financialContractUuid;
	}



	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
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



	public void setPaymentGateway(PaymentInstitutionName paymentGateway) {
		this.paymentGateway = paymentGateway;
	}



	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}



	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}



	public String getPgAccount() {
		return pgAccount;
	}



	public void setPgAccount(String pgAccount) {
		this.pgAccount = pgAccount;
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
		return actualTotalAmount;
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



	public String getExecutionRemark() {
		return executionRemark;
	}



	public void setExecutionRemark(String executionRemark) {
		this.executionRemark = executionRemark;
	}




	public DeductApplicationExecutionStatus getExecutionStatus() {
		return executionStatus;
	}



	public void setExecutionStatus(DeductApplicationExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
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
	
	public String getMerId_ClearingNo(){
		return BusinessUtils.get_merId_clearingNo(pgAccount, pgClearingAccount);
	}

	
	
}

