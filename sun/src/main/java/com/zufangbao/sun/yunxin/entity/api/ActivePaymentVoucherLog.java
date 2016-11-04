package com.zufangbao.sun.yunxin.entity.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_interface_active_voucher_log")
public class ActivePaymentVoucherLog {
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 请求唯一标识（必填）
	 */
	private String requestNo;
	/**
	 * 交易类型(0:提交，1:撤销)（必填）
	 */
	private Integer transactionType;
	/**
	 * 凭证类型(5:主动付款，6:他人代付)（必填）
	 */
	private Integer voucherType;
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	/**
	 * 还款计划编号（必填）
	 */
	private String repaymentPlanNo;
	/**
	 * 收款账户号（选填）
	 */
	private String receivableAccountNo;
	/**
	 * 付款银行名称（必填）
	 */
	private String paymentBank;
	/**
	 * 银行流水号（必填）
	 */
	private String bankTransactionNo;
	/**
	 * 凭证金额（必填）
	 */
	private BigDecimal voucherAmount = BigDecimal.ZERO;
	/**
	 * 付款银行帐户名称（必填）
	 */
	private String paymentName;
	/**
	 * 付款账户号（必填）
	 */
	private String paymentAccountNo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 请求IP地址
	 */
	private String ip;
	
	public ActivePaymentVoucherLog() {
		super();
	}
	
	public ActivePaymentVoucherLog(String requestNo, Integer transactionType, Integer voucherType, String uniqueId,
			String contractNo, String repaymentPlanNo, String receivableAccountNo, String paymentBank,
			String bankTransactionNo, BigDecimal voucherAmount, String paymentName, String paymentAccountNo,
			String ip) {
		super();
		this.requestNo = requestNo;
		this.transactionType = transactionType;
		this.voucherType = voucherType;
		this.uniqueId = uniqueId;
		this.contractNo = contractNo;
		this.repaymentPlanNo = repaymentPlanNo;
		this.receivableAccountNo = receivableAccountNo;
		this.paymentBank = paymentBank;
		this.bankTransactionNo = bankTransactionNo;
		this.voucherAmount = voucherAmount;
		this.paymentName = paymentName;
		this.paymentAccountNo = paymentAccountNo;
		this.ip = ip;
		this.createTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public String getBankTransactionNo() {
		return bankTransactionNo;
	}

	public void setBankTransactionNo(String bankTransactionNo) {
		this.bankTransactionNo = bankTransactionNo;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
