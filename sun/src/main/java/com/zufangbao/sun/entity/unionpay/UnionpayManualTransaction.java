package com.zufangbao.sun.entity.unionpay;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 银联手动交易（测试用表）
 * 
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "unionpay_manual_transaction")
public class UnionpayManualTransaction {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 银行卡所有人姓名
	 */
	private String bankCardOwner;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 扣款卡开户行所在省
	 */
	private String province;
	/**
	 * 扣款卡开户行所在市
	 */
	private String city;
	/**
	 * 扣款金额
	 */
	private BigDecimal amount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 请求银联交易编号
	 */
	private String unionpayTransactionNo;
	/**
	 * 扣款方式 0:实时代收,1:批量代收
	 */
	private int transactionType;
	/**
	 * 支付通道
	 */
	private Long paymentChannelId;
	/**
	 * 请求报文
	 */
	private String requestPacket;
	/**
	 * 响应报文
	 */
	private String responsePacket;
	/**
	 * 查询响应报文
	 */
	private String queryResponsePacket;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardOwner() {
		return bankCardOwner;
	}

	public void setBankCardOwner(String bankCardOwner) {
		this.bankCardOwner = bankCardOwner;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnionpayTransactionNo() {
		return unionpayTransactionNo;
	}

	public void setUnionpayTransactionNo(String unionpayTransactionNo) {
		this.unionpayTransactionNo = unionpayTransactionNo;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public Long getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(Long paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

	public String getRequestPacket() {
		return requestPacket;
	}

	public void setRequestPacket(String requestPacket) {
		this.requestPacket = requestPacket;
	}

	public String getResponsePacket() {
		return responsePacket;
	}

	public void setResponsePacket(String responsePacket) {
		this.responsePacket = responsePacket;
	}

	public String getQueryResponsePacket() {
		return queryResponsePacket;
	}

	public void setQueryResponsePacket(String queryResponsePacket) {
		this.queryResponsePacket = queryResponsePacket;
	}

	public UnionpayManualTransaction(String bankCode, String bankCardNo, String bankCardOwner, String idCardNo,
			String province, String city, BigDecimal amount, String remark, String unionpayTransactionNo,
			int transactionType, Long paymentChannelId, String requestPacket, String responsePacket,
			String queryResponsePacket) {
		super();
		this.bankCode = bankCode;
		this.bankCardNo = bankCardNo;
		this.bankCardOwner = bankCardOwner;
		this.idCardNo = idCardNo;
		this.province = province;
		this.city = city;
		this.amount = amount;
		this.remark = remark;
		this.unionpayTransactionNo = unionpayTransactionNo;
		this.transactionType = transactionType;
		this.paymentChannelId = paymentChannelId;
		this.requestPacket = requestPacket;
		this.responsePacket = responsePacket;
		this.queryResponsePacket = queryResponsePacket;
	}

	public UnionpayManualTransaction() {
		super();
	}

}
