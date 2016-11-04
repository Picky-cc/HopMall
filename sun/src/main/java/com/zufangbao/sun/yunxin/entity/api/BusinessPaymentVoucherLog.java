package com.zufangbao.sun.yunxin.entity.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商户付款凭证接口
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "t_interface_voucher_log")
public class BusinessPaymentVoucherLog {
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
//	/**
//	 * 凭证编号（必填）
//	 */
//	private String businessVoucherNo;
	/**
	 * 凭证类型(0:代偿，1:担保补足，2:回购，3:差额划拨)（必填）
	 */
	private Integer voucherType;
	/**
	 * 凭证金额（必填）
	 */
	private BigDecimal voucherAmount = BigDecimal.ZERO;
	/**
	 * 信托产品代码（必填）
	 */
	private String financialContractNo;
	/**
	 * 收款账户号（选填）
	 */
	private String receivableAccountNo;
	/**
	 * 付款账户号（必填）
	 */
	private String paymentAccountNo;
	/**
	 * 付款银行帐户名称（必填）
	 */
	private String paymentName;
	/**
	 * 付款银行名称（必填）
	 */
	private String paymentBank;
	/**
	 * 银行流水号（必填）
	 */
	private String bankTransactionNo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 请求IP地址
	 */
	private String ip;

	public BusinessPaymentVoucherLog() {
		super();
	}

	public BusinessPaymentVoucherLog(String requestNo, Integer transactionType, Integer voucherType,
			BigDecimal voucherAmount, String financialContractNo, String receivableAccountNo, String paymentAccountNo,
			String paymentName, String paymentBank, String bankTransactionNo, String ip) {
		super();
		this.requestNo = requestNo;
		this.transactionType = transactionType;
		this.voucherType = voucherType;
		this.voucherAmount = voucherAmount;
		this.financialContractNo = financialContractNo;
		this.receivableAccountNo = receivableAccountNo;
		this.paymentAccountNo = paymentAccountNo;
		this.paymentName = paymentName;
		this.paymentBank = paymentBank;
		this.bankTransactionNo = bankTransactionNo;
		this.createTime = new Date();
		this.ip = ip;
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

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
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
