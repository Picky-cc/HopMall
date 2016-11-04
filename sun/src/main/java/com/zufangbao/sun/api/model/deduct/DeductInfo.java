package com.zufangbao.sun.api.model.deduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;


public class DeductInfo {

	
	//扣款编号
	private  String deductNo;
	
	private List<String>  repaymentCodes;
	
	private String      businessResultMessage;
	
	private BigDecimal  planAmount;
	
	private BigDecimal  successAmount;
	
	
	//0 已创建 1处理中2 成功 3 失败 4 异常
	private int         executionStatus;
	
	private static HashMap<ExecutingDeductStatus,Integer> executionStatusMapping=new HashMap<ExecutingDeductStatus,Integer>()
			{{
				put(ExecutingDeductStatus.CREATE,0);
				put(ExecutingDeductStatus.DOING,1);
				put(ExecutingDeductStatus.SUCCESS,2);
				put(ExecutingDeductStatus.FAIL,3);
				put(ExecutingDeductStatus.TIME_OUT,4);
				
			}};
			
	public void setExecutionStatus(ExecutingDeductStatus status)
	{
		Integer iStatus=executionStatusMapping.get(status);
		executionStatus=iStatus!=0?iStatus:4;
	}
	
	private static HashMap<DeductApplicationExecutionStatus,Integer> apiExecutionStatusMapping=new HashMap<DeductApplicationExecutionStatus,Integer>()
	{{
				put(DeductApplicationExecutionStatus.CREATE,0);
				put(DeductApplicationExecutionStatus.PROCESSING,1);
				put(DeductApplicationExecutionStatus.SUCCESS,2);
				put(DeductApplicationExecutionStatus.FAIL,3);
				put(DeductApplicationExecutionStatus.ABNORMAL,4);
				put(DeductApplicationExecutionStatus.ABANDON,5);
				
	}};
			
    public void setExecutionStatus(DeductApplicationExecutionStatus status)
	{
				Integer iStatus=apiExecutionStatusMapping.get(status);
				executionStatus=iStatus!=0?iStatus:4;
	}
			 
	public DeductInfo(String deductId, DeductApplication deductApplication,
			BigDecimal successTotalAmount,
			DeductApplicationExecutionStatus deductApplicationExecutionStatus, String businessMessage) {
	    this.deductNo        = deductId;
	    this.repaymentCodes  = deductApplication.getRepaymentPlanCodeListJsonString();
	    this.planAmount      = deductApplication.getPlannedDeductTotalAmount();
	    this.businessResultMessage = businessMessage;
	    this.successAmount   = successTotalAmount;
	    setExecutionStatus(deductApplicationExecutionStatus);
	}
	
	public DeductInfo(){
		
	}

	public DeductInfo(String deductId, TransferApplication transferApplication,
			BigDecimal successTotalAmount,
			ExecutingDeductStatus executingDeductStatus) {
		List<String> repayments = new ArrayList<String>();
		repayments.add(transferApplication.getOrder().getAssetSet().getSingleLoanContractNo());
		this.deductNo        = deductId;
		this.repaymentCodes  = repayments;
		this.planAmount      = transferApplication.getAmount();
		this.successAmount   = successTotalAmount;
		setExecutionStatus(executingDeductStatus);
	}

	public String getDeductNo() {
		return deductNo;
	}

	public void setDeductNo(String deductNo) {
		this.deductNo = deductNo;
	}

	public List<String> getRepaymentCodes() {
		return repaymentCodes;
	}

	public void setRepaymentCodes(List<String> repaymentCodes) {
		this.repaymentCodes = repaymentCodes;
	}

	public BigDecimal getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	public int getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(int executionStatus) {
		this.executionStatus = executionStatus;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public String getBusinessResultMessage() {
		return businessResultMessage;
	}

	public void setBusinessResultMessage(String businessResultMessage) {
		this.businessResultMessage = businessResultMessage;
	}
	

	
	
	
}
