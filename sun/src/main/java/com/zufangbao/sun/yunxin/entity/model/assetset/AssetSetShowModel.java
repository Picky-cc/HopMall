package com.zufangbao.sun.yunxin.entity.model.assetset;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class AssetSetShowModel {
	private Long assetSetId;
	private Long contractId;
	private String singleLoanContractNo;
	private String contractNo;
	private String financialContractNo;
	private String financialProjectName;
	private String assetNo;
	private String customerName;
	private Date assetRecycleDate;
	private int currentPeriod;
	private int allPeriods;
	private BigDecimal assetPrincipalValue;
	private BigDecimal assetInterestValue;
	private BigDecimal assetInitialValue;

	/** 差异天数 **/
	private int overDueDays;
	/** 逾期天数 **/
	private int auditOverdueDays;
	/** 差异罚息金额 **/
	private BigDecimal penaltyInterestAmount;
	/** 应还款金额 **/
	private BigDecimal amount;
	/** 实际还款金额 **/
	private BigDecimal actualAmount;
	/** 实际还款日期 **/
	private Date actualRecycleDate;
	/** 退款金额 **/
	private BigDecimal refundAmount;
	/** 还款状态 **/
	private String paymentStatus;
	/** 担保状态 **/
	private String guaranteeStatus;
	/** 备注 **/
	private String comment;

	public AssetSetShowModel(AssetSet assetSet, AssetSetQueryModel queryModel) {
		super();
		String financialContractUuid = assetSet.getContract().getFinancialContractUuid();
		FinancialContract financialContract = queryModel.getFinancialContractByUuid(financialContractUuid);
		this.assetSetId = assetSet.getId();
		this.singleLoanContractNo = assetSet.getSingleLoanContractNo();
		this.contractNo = assetSet.getContractNo();
		this.financialContractNo = financialContract.getContractNo();
		this.financialProjectName = financialContract.getContractName();
		this.assetNo = assetSet.getContract().getHouse().getAddress();
		this.currentPeriod = assetSet.getCurrentPeriod();
		this.allPeriods = assetSet.getContract().getPeriods();
		this.assetPrincipalValue = assetSet.getAssetPrincipalValue();
		this.assetInterestValue = assetSet.getAssetInterestValue();
		this.actualAmount = assetSet.isPaidOff()?assetSet.getAmount():BigDecimal.ZERO;
		this.contractId = assetSet.getContract().getId();
		this.customerName = assetSet.getContract().getCustomer().getName();
		this.assetRecycleDate = assetSet.getAssetRecycleDate();
		this.actualRecycleDate = assetSet.getActualRecycleDate();
		this.assetInitialValue = assetSet.getAssetInitialValue();
		this.overDueDays = assetSet.getOverDueDays();
		this.auditOverdueDays = assetSet.getAuditOverdueDays();
		this.penaltyInterestAmount = assetSet.getPenaltyInterestAmount();
		this.amount = assetSet.getAmount();
		this.refundAmount = assetSet.getRefundAmount();
		this.paymentStatus = assetSet.getPaymentStatus().getChineseMessage();
		this.guaranteeStatus = assetSet.getGuaranteeStatus()
				.getChineseMessage();
		this.comment = assetSet.getComment();
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getFinancialProjectName() {
		return financialProjectName;
	}

	public void setFinancialProjectName(String financialProjectName) {
		this.financialProjectName = financialProjectName;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public int getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public int getAllPeriods() {
		return allPeriods;
	}

	public void setAllPeriods(int allPeriods) {
		this.allPeriods = allPeriods;
	}

	public BigDecimal getAssetPrincipalValue() {
		return assetPrincipalValue;
	}

	public void setAssetPrincipalValue(BigDecimal assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}

	public BigDecimal getAssetInterestValue() {
		return assetInterestValue;
	}

	public void setAssetInterestValue(BigDecimal assetInterestValue) {
		this.assetInterestValue = assetInterestValue;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public AssetSetShowModel() {
		super();
	}

	public Long getAssetSetId() {
		return assetSetId;
	}

	public void setAssetSetId(Long assetSetId) {
		this.assetSetId = assetSetId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public BigDecimal getAssetInitialValue() {
		return assetInitialValue;
	}

	public void setAssetInitialValue(BigDecimal assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}

	public int getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}

	public int getAuditOverdueDays() {
		return auditOverdueDays;
	}

	public void setAuditOverdueDays(int auditOverdueDays) {
		this.auditOverdueDays = auditOverdueDays;
	}

	public BigDecimal getPenaltyInterestAmount() {
		return penaltyInterestAmount;
	}

	public void setPenaltyInterestAmount(BigDecimal penaltyInterestAmount) {
		this.penaltyInterestAmount = penaltyInterestAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

}
