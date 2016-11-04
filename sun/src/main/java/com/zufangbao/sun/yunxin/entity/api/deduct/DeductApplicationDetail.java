package com.zufangbao.sun.yunxin.entity.api.deduct;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.api.model.deduct.IsTotal;
import com.zufangbao.sun.api.model.deduct.RepaymentDetail;
import com.zufangbao.sun.api.model.deduct.RepaymentType;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;



@Entity
@Table(name ="t_deduct_application_detail")
public class DeductApplicationDetail {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String deductApplicationDetailUuid;
	
	private String deductApplicationUuid;
	
	private String requestNo;
	
	private String financialContractUuid;
	
	private String contractUniqueId;
	
	private String repaymentPlanCode;
	
	private String assetSetUuid;
	
	@Enumerated(EnumType.ORDINAL)
	private RepaymentType repaymentType;
	
	/**
	 * 交易类型0:贷，1:借 
	 */
	@Enumerated(EnumType.ORDINAL)
	private AccountSide transactionType;
	
	
	private Date        createTime;
	
	private String      creatorName;
	
	private Date        lastModifyTime;
	
    private String      executionRemark;
    
    // 0 合计金额  1 明细金额
    @Enumerated(EnumType.ORDINAL)
    private IsTotal         isTotal;
    
    private BigDecimal   accountAmount;
    
    private String firstAccountName;
	
	private String firstAccountUuid;
	
	private String secondAccountName;
	
	private String secondAccountUuid;
	
	private String thirdAccountName;

	private String thirdAccountUuid;
	
	

	
	public DeductApplicationDetail(){
		
	}
	
	public DeductApplicationDetail(DeductApplication deductApplication, RepaymentDetail repaymentDetail, IsTotal isTotal, BigDecimal amount, String assetSetUuid) {
		this.deductApplicationDetailUuid = repaymentDetail.getRepaymentDetailUuid();
		this.deductApplicationUuid = deductApplication.getDeductApplicationUuid();
		this.requestNo = deductApplication.getRequestNo();
		this.financialContractUuid = deductApplication.getFinancialContractUuid();
		this.contractUniqueId = deductApplication.getContractUniqueId();
		this.repaymentPlanCode  = repaymentDetail.getRepaymentPlanNo();
		this.assetSetUuid       = assetSetUuid;
		this.repaymentType = deductApplication.getRepaymentType();
		this.transactionType = deductApplication.getTranscationType();
		this.createTime = new Date();
		this.creatorName = deductApplication.getCreatorName();
		this.lastModifyTime = new Date();
		this.executionRemark = "";
		this.isTotal = isTotal;
		this.accountAmount = amount;
	}

	public Long getId() {
		return id;
		
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public AccountSide getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(AccountSide transactionType) {
		this.transactionType = transactionType;
	}

	public String getFirstAccountName() {
		return firstAccountName;
	}

	public void setFirstAccountName(String firstAccountName) {
		this.firstAccountName = firstAccountName;
	}

	public String getFirstAccountUuid() {
		return firstAccountUuid;
	}

	public void setFirstAccountUuid(String firstAccountUuid) {
		this.firstAccountUuid = firstAccountUuid;
	}

	public String getSecondAccountName() {
		return secondAccountName;
	}

	public void setSecondAccountName(String secondAccountName) {
		this.secondAccountName = secondAccountName;
	}

	public String getSecondAccountUuid() {
		return secondAccountUuid;
	}

	public void setSecondAccountUuid(String secondAccountUuid) {
		this.secondAccountUuid = secondAccountUuid;
	}

	public String getThirdAccountName() {
		return thirdAccountName;
	}

	public void setThirdAccountName(String thirdAccountName) {
		this.thirdAccountName = thirdAccountName;
	}

	public String getThirdAccountUuid() {
		return thirdAccountUuid;
	}

	public void setThirdAccountUuid(String thirdAccountUuid) {
		this.thirdAccountUuid = thirdAccountUuid;
	}
	
	public String lastAccountName()
	{
		if(StringUtils.isEmpty(getThirdAccountName())==false) return getThirdAccountName();
		if(StringUtils.isEmpty(getSecondAccountName())==false) return getSecondAccountName();
		if(StringUtils.isEmpty(getFirstAccountName())==false) return getFirstAccountName();
		
		return "";
		
	}
	
	public void copyTAccount(TAccountInfo account)
	{
		if(account==null) return;
		if(account.getFirstLevelAccount()==null||StringUtils.isEmpty(account.getFirstLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getFirstLevelAccount().getAccountCode()))return;
		this.firstAccountName = account.getFirstLevelAccount().getAccountName();
		this.firstAccountUuid = account.getFirstLevelAccount().getAccountCode();

		if(account.getSecondLevelAccount()==null||StringUtils.isEmpty(account.getSecondLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getSecondLevelAccount().getAccountCode()))return;
		this.secondAccountName = account.getSecondLevelAccount().getAccountName();
		this.secondAccountUuid = account.getSecondLevelAccount().getAccountCode();
		if(account.getThirdLevelAccount()==null||StringUtils.isEmpty(account.getThirdLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getThirdLevelAccount().getAccountCode()))return;
		this.thirdAccountName = account.getThirdLevelAccount().getAccountName();
		this.thirdAccountUuid = account.getThirdLevelAccount().getAccountCode();
	
	}

	public IsTotal getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(IsTotal isTotal) {
		this.isTotal = isTotal;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getAssetSetUuid() {
		return assetSetUuid;
	}

	public void setAssetSetUuid(String assetSetUuid) {
		this.assetSetUuid = assetSetUuid;
	}
	
	
	

	
}
