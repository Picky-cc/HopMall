package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.icbc.business.CashFlow;

/**
 * 代付撤销单
 * @author jx
 */

@Entity
@Table(name = "t_remittance_refund_bill")
public class RemittanceRefundBill {
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 撤销单号
	 */
	private String remittanceRefundBillUuid;
	
	
	/**
	 * 代付单号
	 */
	private String relatedExecReqNo;
	
	
	/**
	 * 信托合同uuid
	 */
	private String financialContractUuid;

	/**
	 * 信托合同Id
	 */
	private Long financialContractId;
	
	/**
	 * 通道流水号
	 */
	private String channelCashFlowNo;
	
	/**
	 * 通道名称
	 */
	private String paymentChannelName;
	
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
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 交易时间
	 */
	private Date transactionTime;

	/**
	 * 发生账户UUID
	 */
	private String hostAccountUuid;

	/**
	 * 发生账号
	 */
	private String hostAccountNo;

	/**
	 * 发生账户名
	 */
	private String hostAccountName;

	/**
	 * 对方账号
	 */
	private String counterAccountNo;

	/**
	 * 对方账户名
	 */
	private String counterAccountName;

	/**
	 * 交易类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private AccountSide transactionType;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemittanceRefundBillUuid() {
		return remittanceRefundBillUuid;
	}

	public void setRemittanceRefundBillUuid(String remittanceRefundBillUuid) {
		this.remittanceRefundBillUuid = remittanceRefundBillUuid;
	}
	
	public String getRelatedExecReqNo() {
		return relatedExecReqNo;
	}

	public void setRelatedExecReqNo(String relatedExecReqNo) {
		this.relatedExecReqNo = relatedExecReqNo;
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

	public String getChannelCashFlowNo() {
		return channelCashFlowNo;
	}

	public void setChannelCashFlowNo(String channelFlowUuid) {
		this.channelCashFlowNo = channelFlowUuid;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
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

	public AccountSide getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(AccountSide transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public RemittanceRefundBill() {
		super();
	}
	
	public RemittanceRefundBill(CashFlow cf, RemittancePlanExecLog execLog){
		Date now = new Date();
		this.createTime = now;
		this.lastModifiedTime = now;
		this.remittanceRefundBillUuid = UUID.randomUUID().toString();
		this.financialContractUuid = execLog.getFinancialContractUuid();
		this.relatedExecReqNo = execLog.getExecReqNo();
		this.financialContractId = execLog.getFinancialContractId();
		this.channelCashFlowNo = cf.getBankSequenceNo();
		this.paymentChannelName = execLog.getPaymentChannelName();
		this.paymentGateway = execLog.getPaymentGateway();
		this.paymentChannelUuid = execLog.getPaymentChannelUuid();
		this.transactionTime = cf.getTransactionTime();
		this.hostAccountUuid = cf.getHostAccountUuid();
		this.hostAccountNo = cf.getHostAccountNo();
		this.hostAccountName = cf.getHostAccountName();
		this.counterAccountNo = cf.getCounterAccountNo();
		this.counterAccountName = cf.getCounterAccountName();
		this.transactionType = execLog.getTransactionType();
		this.amount = cf.getTransactionAmount();
	}

}
