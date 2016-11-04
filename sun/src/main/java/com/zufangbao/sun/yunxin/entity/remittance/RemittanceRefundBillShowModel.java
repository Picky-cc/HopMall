package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

public class RemittanceRefundBillShowModel {

	/**
	 * 撤销单号
	 */
	private String remittanceRefundBillUuid;
	
	/**
	 * 通道流水号
	 */
	private String channelCashFlowNo;
	
	/**
	 * 通道名称
	 */
	private String paymentChannelName;
	
	/**
	 * 发生时间
	 */
	private Date createTime;
	
	/**
	 * 退回账户
	 */
	private String hostAccountNo;
	
	/**
	 * 交易类型
	 */
	private String transactionType;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;

	public String getRemittanceRefundBillUuid() {
		return remittanceRefundBillUuid;
	}

	public void setRemittanceRefundBillUuid(String remittanceRefundBillUuid) {
		this.remittanceRefundBillUuid = remittanceRefundBillUuid;
	}

	public String getChannelCashFlowNo() {
		return channelCashFlowNo;
	}

	public void setChannelCashFlowNo(String channelCashFlowNo) {
		this.channelCashFlowNo = channelCashFlowNo;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHostAccountNo() {
		return hostAccountNo;
	}

	public void setHostAccountNo(String hostAccountNo) {
		this.hostAccountNo = hostAccountNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public RemittanceRefundBillShowModel() {
		super();
	}

	public RemittanceRefundBillShowModel(RemittanceRefundBill rrb) {
		super();
		this.remittanceRefundBillUuid = rrb.getRemittanceRefundBillUuid();
		this.channelCashFlowNo = rrb.getChannelCashFlowNo();
		this.paymentChannelName = rrb.getPaymentChannelName();
		this.createTime = rrb.getCreateTime();
		this.hostAccountNo = rrb.getHostAccountNo();
		this.transactionType = rrb.getTransactionType() == null ? "" : rrb.getTransactionType().getChineseMessage();
		this.amount = rrb.getAmount();
	}
	
}
