package com.zufangbao.yunxin.entity.api.syncdata.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;

public class DataSyncRequestModel {
	
	
    private String productId;
	
	private String contractNo;
	
	private Date endDate;
	
	private String uniqueId;
	
	private String repayScheduleNo;
	
	private String  contractFlag;
	
	private String repayKind;
	
	private Date preOccurDate;
	
	private Date occurDate;
	
    private BigDecimal totalAmount;
	
	private BigDecimal preRepayPrincipal;
	
	private BigDecimal repayPrincipal;
	
	private BigDecimal preRepayProfit;
	
	private BigDecimal repayProfit;
	
	private BigDecimal preRepayPennalty;
	
	private BigDecimal repayPennalty;
	
	private BigDecimal techMaintenanceFee;
	
	private BigDecimal loanServiceFee;
	
	private BigDecimal latePenalty;
	
	private BigDecimal lateServiceFee;
	
	private BigDecimal lateOtherCosts;
	
	private BigDecimal loanCallCost;
	
	private BigDecimal otherFee;

	public DataSyncRequestModel(DataSyncLog dataSyncLog) {
		this.productId = dataSyncLog.getProductId();
		this.contractNo = dataSyncLog.getContractNo();
		this.endDate    = dataSyncLog.getContractEndDate();
		this.uniqueId   = dataSyncLog.getContractUniqueId();
		this.repayScheduleNo= dataSyncLog.getRepaymentPlanNo();
		this.contractFlag   = new String("0"+dataSyncLog.getContractFlag());
		this.repayKind    = new String("0"+dataSyncLog.getRepayType().getOrdinal());
		this.preOccurDate = dataSyncLog.getPlanRepayDate();
		this.occurDate    = dataSyncLog.getActualRepayDate();
		this.totalAmount  = dataSyncLog.getDataSyncBigDecimalDetailsJson().getRepayTotalAmount();
		this.preRepayPrincipal =dataSyncLog.getDataSyncBigDecimalDetailsJson().getPreRepayPrincipal();
		this.repayPrincipal    = dataSyncLog.getDataSyncBigDecimalDetailsJson().getRepayPrincipal();
		this.preRepayProfit    = dataSyncLog.getDataSyncBigDecimalDetailsJson().getPreRepayInterest();
		this.repayProfit       = dataSyncLog.getDataSyncBigDecimalDetailsJson().getRepayInterest();
		this.preRepayPennalty  = dataSyncLog.getDataSyncBigDecimalDetailsJson().getPreRepayPennalty();
		this.repayPennalty     = dataSyncLog.getDataSyncBigDecimalDetailsJson().getRepayPennalty();
		this.techMaintenanceFee = dataSyncLog.getDataSyncBigDecimalDetailsJson().getTechMaintenanceFee();
		this.loanServiceFee    =  dataSyncLog.getDataSyncBigDecimalDetailsJson().getLoanServiceFee();
		this.latePenalty       = dataSyncLog.getDataSyncBigDecimalDetailsJson().getLateServiceFee();
		this.lateServiceFee    = dataSyncLog.getDataSyncBigDecimalDetailsJson().getLateServiceFee();
		this.lateOtherCosts    = dataSyncLog.getDataSyncBigDecimalDetailsJson().getLateOtherCosts();
		this.loanCallCost      = dataSyncLog.getDataSyncBigDecimalDetailsJson().getLoanCallCost();
		this.otherFee          = dataSyncLog.getDataSyncBigDecimalDetailsJson().getOtherFee();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getRepayScheduleNo() {
		return repayScheduleNo;
	}

	public void setRepayScheduleNo(String repayScheduleNo) {
		this.repayScheduleNo = repayScheduleNo;
	}

	public String getContractFlag() {
		return contractFlag;
	}

	public void setContractFlag(String contractFlag) {
		this.contractFlag = contractFlag;
	}


	public String getRepayKind() {
		return repayKind;
	}

	public void setRepayKind(String repayKind) {
		this.repayKind = repayKind;
	}

	public Date getPreOccurDate() {
		return preOccurDate;
	}

	public void setPreOccurDate(Date preOccurDate) {
		this.preOccurDate = preOccurDate;
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPreRepayPrincipal() {
		return preRepayPrincipal;
	}

	public void setPreRepayPrincipal(BigDecimal preRepayPrincipal) {
		this.preRepayPrincipal = preRepayPrincipal;
	}

	public BigDecimal getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(BigDecimal repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}

	public BigDecimal getPreRepayProfit() {
		return preRepayProfit;
	}

	public void setPreRepayProfit(BigDecimal preRepayProfit) {
		this.preRepayProfit = preRepayProfit;
	}

	public BigDecimal getRepayProfit() {
		return repayProfit;
	}

	public void setRepayProfit(BigDecimal repayProfit) {
		this.repayProfit = repayProfit;
	}

	public BigDecimal getPreRepayPennalty() {
		return preRepayPennalty;
	}

	public void setPreRepayPennalty(BigDecimal preRepayPennalty) {
		this.preRepayPennalty = preRepayPennalty;
	}

	public BigDecimal getRepayPennalty() {
		return repayPennalty;
	}

	public void setRepayPennalty(BigDecimal repayPennalty) {
		this.repayPennalty = repayPennalty;
	}

	public BigDecimal getTechMaintenanceFee() {
		return techMaintenanceFee;
	}

	public void setTechMaintenanceFee(BigDecimal techMaintenanceFee) {
		this.techMaintenanceFee = techMaintenanceFee;
	}

	public BigDecimal getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(BigDecimal loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public BigDecimal getLatePenalty() {
		return latePenalty;
	}

	public void setLatePenalty(BigDecimal latePenalty) {
		this.latePenalty = latePenalty;
	}

	public BigDecimal getLateServiceFee() {
		return lateServiceFee;
	}

	public void setLateServiceFee(BigDecimal lateServiceFee) {
		this.lateServiceFee = lateServiceFee;
	}

	public BigDecimal getLateOtherCosts() {
		return lateOtherCosts;
	}

	public void setLateOtherCosts(BigDecimal lateOtherCosts) {
		this.lateOtherCosts = lateOtherCosts;
	}

	public BigDecimal getLoanCallCost() {
		return loanCallCost;
	}

	public void setLoanCallCost(BigDecimal loanCallCost) {
		this.loanCallCost = loanCallCost;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	
	

}
