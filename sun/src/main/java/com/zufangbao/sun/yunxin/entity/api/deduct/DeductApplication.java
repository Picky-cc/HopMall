package com.zufangbao.sun.yunxin.entity.api.deduct;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.api.model.deduct.DeductCommandRequestModel;
import com.zufangbao.sun.api.model.deduct.IsAvailable;
import com.zufangbao.sun.api.model.deduct.RecordStatus;
import com.zufangbao.sun.api.model.deduct.RepaymentType;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;
import com.zufangbao.sun.yunxin.entity.remittance.TransactionRecipient;


@Entity
@Table(name ="t_deduct_application")
public class DeductApplication {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String deductApplicationUuid;
	
	
	private Date   apiCalledTime; 
	
	private String requestNo;
	
	private String deductId;
	
	private String financialContractUuid;
	
    private String financialProductCode;
    
    private String contractUniqueId;
    
    private String contractNo;
    
    private String customerName;
    
    private String repaymentPlanCodeList;
	//计划扣款金额
    private BigDecimal  plannedDeductTotalAmount;
    
    //实际扣款金额
    private BigDecimal  actualDeductTotalAmount;
    
    private String  notifyUrl;
    
    //接口交易类型
    @Enumerated(EnumType.ORDINAL)
    private AccountSide transcationType;
    
    //还款类型
    @Enumerated(EnumType.ORDINAL)
    private RepaymentType repaymentType;
    
    private TransactionRecipient  transactionRecipient;
    
    //扣款执行状态
    @Enumerated(EnumType.ORDINAL)
    private DeductApplicationExecutionStatus executionStatus;
    
    @Enumerated(EnumType.ORDINAL)
    private RecordStatus recordStatus; 
    
    @Enumerated(EnumType.ORDINAL)
    private IsAvailable  isAvailable; 
    
    
    private String executionRemark;
    
    //扣款申请创建时间
    private Date createTime;

    //创建人
    private String creatorName;
	//ip地址
    private String ip;
	/** 成功后设置其为扣款成功时间 */
    private Date  lastModifiedTime;

	public DeductApplication(DeductCommandRequestModel commandModel, Contract contract,FinancialContract financialContract, String merchantId, String ipAddress, List<String> repaymentCodes) {
		
		this.deductApplicationUuid = commandModel.getDeductApplicationUuid();
		this.apiCalledTime = DateUtils.asDay(commandModel.getApiCalledTime());
		this.requestNo     = commandModel.getRequestNo();
		this.deductId      = commandModel.getDeductId();
		this.financialContractUuid = financialContract.getFinancialContractUuid();
		this.financialProductCode = financialContract.getContractNo();
		this.contractUniqueId = contract.getUniqueId();
		this.contractNo = contract.getContractNo();
		this.customerName = contract.getCustomer().getName();
		this.plannedDeductTotalAmount  = new BigDecimal(commandModel.getAmount());
		this.actualDeductTotalAmount=  BigDecimal.ZERO;
		this.notifyUrl = "";
		this.transcationType = AccountSide.DEBIT;
		this.repaymentType = RepaymentType.fromValue(commandModel.getRepaymentType());
		this.executionStatus = DeductApplicationExecutionStatus.PROCESSING;
		this.recordStatus    = RecordStatus.UNRECORDED;
		this.isAvailable     = IsAvailable.AVAILABLE;  
		this.transactionRecipient  = TransactionRecipient.LOCAL;
		this.executionRemark= "";
		this.createTime = new Date();
		this.creatorName =  merchantId;
		this.ip =  ipAddress;
		this.lastModifiedTime = new Date();
		setRepaymentPlanCodeListJsonString(repaymentCodes);
	}
	
	

	public DeductApplication(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeductApplicationUuid() {
		return deductApplicationUuid;
	}

	public void setDeductApplicationUuid(String deductApplicationUuid) {
		this.deductApplicationUuid = deductApplicationUuid;
	}

	public Date getApiCalledTime() {
		return apiCalledTime;
	}

	public void setApiCalledTime(Date apiCalledTime) {
		this.apiCalledTime = apiCalledTime;
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


	public BigDecimal getPlannedDeductTotalAmount() {
		return plannedDeductTotalAmount;
	}

	public void setPlannedDeductTotalAmount(BigDecimal plannedDeductTotalAmount) {
		this.plannedDeductTotalAmount = plannedDeductTotalAmount;
	}

	public BigDecimal getActualDeductTotalAmount() {
		return actualDeductTotalAmount;
	}

	public void setActualDeductTotalAmount(BigDecimal actualDeductTotalAmount) {
		this.actualDeductTotalAmount = actualDeductTotalAmount;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}


	public RepaymentType getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}


	public String getRemark() {
		return executionRemark;
	}

	public void setRemark(String remark) {
		this.executionRemark = remark;
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

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
    
    public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDeductId() {
		return deductId;
	}

	public void setDeductId(String deductId) {
		this.deductId = deductId;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public DeductApplicationExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(DeductApplicationExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public IsAvailable getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(IsAvailable isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getExecutionRemark() {
		return executionRemark;
	}

	public void setExecutionRemark(String executionRemark) {
		this.executionRemark = executionRemark;
	}

	public AccountSide getTranscationType() {
		return transcationType;
	}

	public void setTranscationType(AccountSide transcationType) {
		this.transcationType = transcationType;
	}

	public String getFinancialProductCode() {
		return financialProductCode;
	}

	public void setFinancialProductCode(String financialProductCode) {
		this.financialProductCode = financialProductCode;
	}

	public TransactionRecipient getTransactionRecipient() {
		return transactionRecipient;
	}

	public void setTransactionRecipient(TransactionRecipient transactionRecipient) {
		this.transactionRecipient = transactionRecipient;
	}

	public List<String> getRepaymentPlanCodeListJsonString() {
		if(this.repaymentPlanCodeList == null){
			return Collections.EMPTY_LIST;
		}
		return JsonUtils.parseArray(repaymentPlanCodeList, String.class);
	}

	public void setRepaymentPlanCodeListJsonString(List<String> repaymentPlanCodeList) {
		
		if(CollectionUtils.isEmpty(repaymentPlanCodeList)){
			this.repaymentPlanCodeList = null;
		}
		this.repaymentPlanCodeList = JsonUtils.toJsonString(repaymentPlanCodeList);
	}

	public String getRepaymentPlanCodeList() {
		return repaymentPlanCodeList;
	}

	public void setRepaymentPlanCodeList(String repaymentPlanCodeList) {
		this.repaymentPlanCodeList = repaymentPlanCodeList;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

}
