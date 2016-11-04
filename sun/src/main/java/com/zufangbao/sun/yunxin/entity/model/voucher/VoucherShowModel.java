package com.zufangbao.sun.yunxin.entity.model.voucher;

import java.math.BigDecimal;

/**
 * 凭证显示Model
 * 
 * @author louguanyang
 *
 */
public class VoucherShowModel {
	private Long detailId;
	/**
	 * voucher number
	 */
	private String sourceDocumentNo;
	/**
	 * receivable account number
	 */
	private String receivableAccountNo;
	/**
	 * payment account number
	 */
	private String paymentAccountNo;
	/**
	 * payer name
	 */
	private String paymentName;
	/**
	 * payment bank name
	 */
	private String paymentBank;

	/**
	 * voucher amount
	 */
	private BigDecimal amount;

	/**
	 * voucher type
	 */
	private String voucherType;
	/**
	 * voucher source
	 */
	private String voucherSource;
	/**
	 * voucher status
	 */
	private String voucherStatus;

	public VoucherShowModel() {
		super();
	}

	public VoucherShowModel(Long detailId, String sourceDocumentNo, String receivableAccountNo, String paymentAccountNo,
			String paymentName, String paymentBank, BigDecimal amount, String voucherType, String voucherSource,
			String voucherStatus) {
		super();
		this.detailId = detailId;
		this.sourceDocumentNo = sourceDocumentNo;
		this.receivableAccountNo = receivableAccountNo;
		this.paymentAccountNo = paymentAccountNo;
		this.paymentName = paymentName;
		this.paymentBank = paymentBank;
		this.amount = amount;
		this.voucherType = voucherType;
		this.voucherSource = voucherSource;
		this.voucherStatus = voucherStatus;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getSourceDocumentNo() {
		return sourceDocumentNo;
	}

	public void setSourceDocumentNo(String sourceDocumentNo) {
		this.sourceDocumentNo = sourceDocumentNo;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherSource() {
		return voucherSource;
	}

	public void setVoucherSource(String voucherSource) {
		this.voucherSource = voucherSource;
	}

	public String getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

}
