package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.constant.CodeNameMapper;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.utils.AmountUtil;

/**
 * 放款计划执行日志
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_remittance_plan_exec_log")
public class RemittancePlanExecLog {

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 放款申请uuid
	 */
	private String remittanceApplicationUuid;

	/**
	 * 放款计划uuid
	 */
	private String remittancePlanUuid;

	/**
	 * 信托合同Uuid
	 */
	private String financialContractUuid;

	/**
	 * 信托合同Id
	 */
	private Long financialContractId;
	
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
	 * 计划支付金额
	 */
	private BigDecimal plannedAmount;
	
	/**
	 * 实际交易总金额
	 */
	private BigDecimal actualTotalAmount;
	
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
	 * 交易类型0:贷，1:借
	 */
	@Enumerated(EnumType.ORDINAL)
	private AccountSide transactionType;
	/**
	 * 交易备注
	 */
	private String transactionRemark;
	
	/**
	 * 交易上送请求号
	 */
	private String execReqNo;
	
	/**
	 * 交易下达响应号
	 */
	private String execRspNo;

	/**
	 * 执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatus executionStatus;
	
	/**
	 * 执行备注
	 */
	private String executionRemark;

	/**
	 * 交易完成时间
	 */
	private Date completePaymentDate;

	private Date createTime;

	private Date lastModifiedTime;
	
	/**
	 * 交易流水号
	 */
	private String transactionSerialNo;
	
	/**
	 * 计划贷记流水检查次数
	 */
	private int planCreditCashFlowCheckNumber;

	/**
	 * 实际贷记流水检查次数
	 */
	private int actualCreditCashFlowCheckNumber;
	
	/**
	 * 冲账状态
	 */
	@Enumerated(EnumType.ORDINAL)
	private ReverseStatus reverseStatus = ReverseStatus.UNOCCUR;
	
	private String creditCashFlowUuid;
	
	private String debitCashFlowUuid;

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

	public String getRemittancePlanUuid() {
		return remittancePlanUuid;
	}

	public void setRemittancePlanUuid(String remittancePlanUuid) {
		this.remittancePlanUuid = remittancePlanUuid;
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

	public BigDecimal getPlannedAmount() {
		return plannedAmount;
	}

	public void setPlannedAmount(BigDecimal plannedAmount) {
		this.plannedAmount = plannedAmount;
	}
	
	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount;
	}

	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
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

	public String getExecReqNo() {
		return execReqNo;
	}

	public void setExecReqNo(String execReqNo) {
		this.execReqNo = execReqNo;
	}

	public String getExecRspNo() {
		return execRspNo;
	}

	public void setExecRspNo(String execRspNo) {
		this.execRspNo = execRspNo;
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

	public Date getCompletePaymentDate() {
		return completePaymentDate;
	}

	public void setCompletePaymentDate(Date completePaymentDate) {
		this.completePaymentDate = completePaymentDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public RemittancePlanExecLog() {
		super();
	}

	public RemittancePlanExecLog(RemittancePlan remittancePlan, String execReqNo) {
		this.remittanceApplicationUuid = remittancePlan.getRemittanceApplicationUuid();
		this.remittancePlanUuid = remittancePlan.getRemittancePlanUuid();
		this.financialContractUuid = remittancePlan.getFinancialContractUuid();
		this.financialContractId = remittancePlan.getFinancialContractId();
		this.paymentGateway = remittancePlan.getPaymentGateway();
		this.paymentChannelUuid = remittancePlan.getPaymentChannelUuid();
		this.paymentChannelName = remittancePlan.getPaymentChannelName();
		this.pgClearingAccount = remittancePlan.getPgClearingAccount();
		this.plannedAmount = remittancePlan.getPlannedTotalAmount();
		this.cpBankCode = remittancePlan.getCpBankCode();
		this.cpBankCardNo = remittancePlan.getCpBankCardNo();
		this.cpBankAccountHolder = remittancePlan.getCpBankAccountHolder();
		this.cpIdType = remittancePlan.getCpIdType();
		this.cpIdNumber = remittancePlan.getCpIdNumber();
		this.cpBankProvince = remittancePlan.getCpBankProvince();
		this.cpBankCity = remittancePlan.getCpBankCity();
		this.cpBankName = remittancePlan.getCpBankName();
		this.transactionType = remittancePlan.getTransactionType();
		this.transactionRemark = remittancePlan.getTransactionRemark();
		this.execReqNo = execReqNo;
		this.executionStatus = ExecutionStatus.PROCESSING;
		this.createTime = new Date();
		this.lastModifiedTime = new Date();
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
				this.cpBankCardNo, this.cpBankName, CodeNameMapper.provinceMapper.get(this.cpBankProvince),
				CodeNameMapper.cityMapper.get(this.cpBankCity), this.cpIdNumber, this.cpBankCode);
	}
	
	public boolean isFinish() {
		if (this.executionStatus == ExecutionStatus.SUCCESS
				|| this.executionStatus == ExecutionStatus.FAIL
				|| this.executionStatus == ExecutionStatus.ABNORMAL
				|| this.executionStatus == ExecutionStatus.ABANDON) {
			return true;
		}
		return false;
	}
	
	public String getExecutedTime() {
		Date tmpDate = this.completePaymentDate == null ? this.lastModifiedTime : this.completePaymentDate;
		Date endDate = this.isFinish() ? tmpDate : new Date();
		if(endDate == null || this.createTime == null) {
			return "";
		}
		
		long diff = endDate.getTime() - this.createTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        return diffHours + "时" + diffMinutes + "分" + diffSeconds + "秒";
	}
	
	public int getPlanCreditCashFlowCheckNumber() {
		return planCreditCashFlowCheckNumber;
	}

	public void setPlanCreditCashFlowCheckNumber(int planCreditCashFlowCheckNumber) {
		this.planCreditCashFlowCheckNumber = planCreditCashFlowCheckNumber;
	}
	
	public int getActualCreditCashFlowCheckNumber() {
		return actualCreditCashFlowCheckNumber;
	}

	public void setActualCreditCashFlowCheckNumber(
			int actualCreditCashFlowCheckNumber) {
		this.actualCreditCashFlowCheckNumber = actualCreditCashFlowCheckNumber;
	}

	public ReverseStatus getReverseStatus() {
		return reverseStatus;
	}

	public void setReverseStatus(ReverseStatus reverseStatus) {
		this.reverseStatus = reverseStatus;
	}

	public String getCreditCashFlowUuid() {
		return creditCashFlowUuid;
	}

	public void setCreditCashFlowUuid(String creditCashFlowUuid) {
		this.creditCashFlowUuid = creditCashFlowUuid;
	}

	public String getDebitCashFlowUuid() {
		return debitCashFlowUuid;
	}

	public void setDebitCashFlowUuid(String debitCashFlowUuid) {
		this.debitCashFlowUuid = debitCashFlowUuid;
	}
	
	public boolean matchInfoWithCreditCashFlow(CashFlow cf) {
		if(cf == null) {
			return false;
		}
		if(this.getCpBankCardNo() == null || this.getPlannedAmount() == null || this.getPgAccountNo() == null) {
			return false;
		}
		if(!StringUtils.equals(cf.getCounterAccountNo(), this.getCpBankCardNo())) {
			return false;
		}
		if(!AmountUtil.equals(this.getPlannedAmount(), cf.getTransactionAmount())) {
			return false;
		}
		if(!StringUtils.equals(cf.getHostAccountNo(), this.getPgAccountNo())) {
			return false;
		}
		return true;
	}
	
}