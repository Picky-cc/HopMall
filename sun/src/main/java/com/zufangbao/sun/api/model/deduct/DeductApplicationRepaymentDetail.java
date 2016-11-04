package com.zufangbao.sun.api.model.deduct;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplicationDetail;
import com.zufangbao.sun.yunxin.entity.model.api.ExtraChargeSpec;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public class DeductApplicationRepaymentDetail {

	
    private String deductApplicationDetailUuid;
	
	private String deductApplicationUuid;
	
	private String requestNo;
	
	private String financialContractUuid;
	
	private String contractUniqueId;
	
	private String assetSetUuid;
	
	private String repaymentPlanCode;
	
	private RepaymentType repaymentType;
	
	/**
	 * 交易类型0:贷，1:借 2 借贷方
	 */
	private AccountSide transactionType;
	
	
	private Date        createTime;
	
	private String      creatorName;
	
	private Date        lastModifyTime;
	
    private String      executionRemark;
    
	private BigDecimal  repaymentPrincipal;
	 
	private BigDecimal  repaymentInterest;
	
	private BigDecimal  loanFee;
	
	private BigDecimal  techFee;
	
	private BigDecimal  otherFee;
	//逾期费用合计
	private BigDecimal  totalOverduFee;
	//扣款当时资产应收总金额()
	private BigDecimal  accountReceivableAmount;
	
	//TODO refacto
	//不包括当时应收总金额
	public BigDecimal getTotalAmount(){
		BigDecimal  repaymentPrincipal = this.repaymentPrincipal==null?BigDecimal.ZERO:this.repaymentPrincipal;
		BigDecimal  repaymentInterest = this.repaymentInterest==null?BigDecimal.ZERO:this.repaymentInterest;
		BigDecimal  loanFee = this.loanFee==null?BigDecimal.ZERO:this.loanFee;
		BigDecimal  techFee = this.techFee==null?BigDecimal.ZERO:this.techFee;
		BigDecimal  otherFee = this.otherFee==null?BigDecimal.ZERO:this.otherFee;
		BigDecimal  totalOverduFee = this.totalOverduFee==null?BigDecimal.ZERO:this.totalOverduFee;
		return repaymentPrincipal.add(repaymentInterest).add(loanFee).add(techFee).add(otherFee).add(totalOverduFee);
	}
	
	public DeductApplicationRepaymentDetail(Map<String, BigDecimal> subjectDetail,
			DeductApplicationDetail deductApplicationDetailTotalType) {
		this.deductApplicationDetailUuid = deductApplicationDetailTotalType.getDeductApplicationDetailUuid();
		this.deductApplicationUuid       = deductApplicationDetailTotalType.getDeductApplicationUuid();
		this.requestNo                   = deductApplicationDetailTotalType.getRequestNo();
		this.financialContractUuid       = deductApplicationDetailTotalType.getFinancialContractUuid();
		this.contractUniqueId            = deductApplicationDetailTotalType.getContractUniqueId();
		this.repaymentPlanCode           = deductApplicationDetailTotalType.getRepaymentPlanCode();
		this.repaymentType               = deductApplicationDetailTotalType.getRepaymentType();
		this.transactionType             = deductApplicationDetailTotalType.getTransactionType();
		this.createTime                  = deductApplicationDetailTotalType.getCreateTime();
		this.creatorName                 = deductApplicationDetailTotalType.getCreatorName();
		this.lastModifyTime     		 = deductApplicationDetailTotalType.getLastModifyTime();
		this.executionRemark		     = deductApplicationDetailTotalType.getExecutionRemark();
		this.assetSetUuid				 = deductApplicationDetailTotalType.getAssetSetUuid();
		this.repaymentPrincipal          = subjectDetail.get(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE);
		this.repaymentInterest           = subjectDetail.get(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_INTEREST);
		this.loanFee                     = subjectDetail.get(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE);
		this.techFee                     = subjectDetail.get(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE); 
		this.otherFee                    = subjectDetail.get(ChartOfAccount.TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE);
		this.totalOverduFee              = subjectDetail.get(ExtraChargeSpec.TOTAL_OVERDUE_FEE);
		this.accountReceivableAmount     = subjectDetail.get(ExtraChargeSpec.TOTAL_RECEIVABEL_AMOUNT);
		
		
	}
	public DeductApplicationRepaymentDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getDeductApplicationDetailUuid() {
		return deductApplicationDetailUuid;
	}
	public void setDeductApplicationDetailUuid(String deductApplicationDetailUuid) {
		this.deductApplicationDetailUuid = deductApplicationDetailUuid;
	}
	public String getDeductApplicationUuid() {
		return deductApplicationUuid;
	}
	public void setDeductApplicationUuid(String deductApplicationUuid) {
		this.deductApplicationUuid = deductApplicationUuid;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getFinancialContractUuid() {
		return financialContractUuid;
	}
	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}
	public String getContractUniqueId() {
		return contractUniqueId;
	}
	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}
	public String getRepaymentPlanCode() {
		return repaymentPlanCode;
	}
	public void setRepaymentPlanCode(String repaymentPlanCode) {
		this.repaymentPlanCode = repaymentPlanCode;
	}
	public RepaymentType getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}
	public AccountSide getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(AccountSide transactionType) {
		this.transactionType = transactionType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getExecutionRemark() {
		return executionRemark;
	}
	public void setExecutionRemark(String executionRemark) {
		this.executionRemark = executionRemark;
	}
	public BigDecimal getRepaymentPrincipal() {
		return repaymentPrincipal;
	}
	public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}
	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}
	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}
	public BigDecimal getLoanFee() {
		return loanFee;
	}
	public void setLoanFee(BigDecimal loanFee) {
		this.loanFee = loanFee;
	}
	public BigDecimal getTechFee() {
		return techFee;
	}
	public void setTechFee(BigDecimal techFee) {
		this.techFee = techFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public BigDecimal getTotalOverduFee() {
		return totalOverduFee;
	}
	public void setTotalOverduFee(BigDecimal totalOverduFee) {
		this.totalOverduFee = totalOverduFee;
	}
	public String getAssetSetUuid() {
		return assetSetUuid;
	}
	public void setAssetSetUuid(String assetSetUuid) {
		this.assetSetUuid = assetSetUuid;
	}

	public BigDecimal getAccountReceivableAmount() {
		return accountReceivableAmount;
	}

	public void setAccountReceivableAmount(BigDecimal accountReceivableAmount) {
		this.accountReceivableAmount = accountReceivableAmount;
	}
	
	
}
