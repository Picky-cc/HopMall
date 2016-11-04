package com.zufangbao.sun.yunxin.entity.model.voucher;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class VoucherDetailBusinessModel {
	private Long sourceDocumentDetailId;
	private String singleLoanContractNo;
	private String contractNo;
	private String financialContractName;
	private Date assetRecycleDate;
	private Date actualRecycleDate;
	private String customerName;
	private BigDecimal assetFairValue;
	private BigDecimal payedAmount;
	private BigDecimal detailAmount;
	private String paymentStatus;
	private String detailStatus;
	private String verifyStatus;//校验状态
	private String comment;//校验错误信息
	private String voucherType;//凭证类型
	
	public VoucherDetailBusinessModel() {
		super();
	}

	public VoucherDetailBusinessModel(Long sourceDocumentDetailId, AssetSet repaymentPlan, BigDecimal receivable_amount,
			String financialContractName, BigDecimal detailAmount, String detailStatus, String verifyStatus, String comment) {
		this.sourceDocumentDetailId = sourceDocumentDetailId;
		if(repaymentPlan != null) {
			this.singleLoanContractNo = repaymentPlan.getSingleLoanContractNo();
			this.contractNo = repaymentPlan.getContractNo();
			this.assetRecycleDate = repaymentPlan.getAssetRecycleDate();
			this.actualRecycleDate = repaymentPlan.getActualRecycleDate();
			this.assetFairValue = repaymentPlan.getAssetFairValue();
			if(repaymentPlan.isClearAssetSet() || repaymentPlan.isAssetRecycleDate(DateUtils.getToday()) || repaymentPlan.isOverdueDate(DateUtils.getToday())) {
				this.payedAmount = assetFairValue.subtract(receivable_amount);
			}else {
				this.payedAmount = BigDecimal.ZERO;
			}
			this.paymentStatus = repaymentPlan.getPaymentStatus().getChineseMessage();
			this.customerName = repaymentPlan.getContract().getCustomer().getName();
		}
		this.financialContractName = financialContractName;
		this.detailAmount = detailAmount;
		this.detailStatus = detailStatus;
		this.verifyStatus = verifyStatus;
		this.comment = comment;
	}

	public Long getSourceDocumentDetailId() {
		return sourceDocumentDetailId;
	}

	public void setSourceDocumentDetailId(Long sourceDocumentDetailId) {
		this.sourceDocumentDetailId = sourceDocumentDetailId;
	}

	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}

	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getFinancialContractName() {
		return financialContractName;
	}

	public void setFinancialContractName(String financialContractName) {
		this.financialContractName = financialContractName;
	}

	public Date getAssetRecycleDate() {
		return assetRecycleDate;
	}

	public void setAssetRecycleDate(Date assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}

	public Date getActualRecycleDate() {
		return actualRecycleDate;
	}

	public void setActualRecycleDate(Date actualRecycleDate) {
		this.actualRecycleDate = actualRecycleDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAssetFairValue() {
		return assetFairValue;
	}

	public void setAssetFairValue(BigDecimal assetFairValue) {
		this.assetFairValue = assetFairValue;
	}

	public BigDecimal getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(BigDecimal payedAmount) {
		this.payedAmount = payedAmount;
	}

	public BigDecimal getDetailAmount() {
		return detailAmount;
	}

	public void setDetailAmount(BigDecimal detailAmount) {
		this.detailAmount = detailAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

}
