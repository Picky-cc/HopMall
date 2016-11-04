package com.zufangbao.sun.yunxin.entity.deduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.api.model.deduct.RepaymentType;
import com.zufangbao.sun.utils.EnumUtil;

public class DeductApplicationQeuryModel {

	
	/**
	 * 信托合同ids
	 */
	private String financialContractIds;

	/**
	 * 还款类型
	 */
	private String repaymentType;
	
	/**
	 * 扣款状态
	 */
	private String executionStatus;
	
	/**
	 * 贷款合同编号
	 */
	private String loanContractNo;
	
	
	private String customerName;
	
	/**
	 * 受理日期起始
	 */
	private String receiveStartDate;
	
	/**
	 * 受理日期终止
	 */
	private String receiveEndDate;
	
	

	public String getFinancialContractIds() {
		return financialContractIds;
	}
	
	public void setFinancialContractIds(String financialContractUuids) {
		this.financialContractIds = financialContractUuids;
	}
	
	public List<String> getFinancialContractIdList(){
		List<String> financialContractIdList = JsonUtils.parseArray(this.financialContractIds, String.class);
		if(financialContractIdList==null){
			return new ArrayList<String>();
		}
		return financialContractIdList;
	}
	public Date getStartDateValue(){
		return receiveStartDate == null?null:DateUtils.asDay(receiveStartDate);
	}
	
	public Date getEndDateValue(){
		return receiveEndDate == null?null:DateUtils.asDay(receiveEndDate);
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getReceiveStartDate() {
		return receiveStartDate;
	}

	public void setReceiveStartDate(String receiveStartDate) {
		this.receiveStartDate = receiveStartDate;
	}

	public String getReceiveEndDate() {
		return receiveEndDate;
	}

	public void setReceiveEndDate(String receiveEndDate) {
		this.receiveEndDate = receiveEndDate;
	}

	public List<DeductApplicationExecutionStatus> getExecutionStatusEnumList() {
		List<Integer> ordinals = JsonUtils.parseArray(this.getExecutionStatus(), Integer.class);
		if (ordinals == null) {
			ordinals = new ArrayList<Integer>();
		}
		List<DeductApplicationExecutionStatus> executionStatusEnumList = new ArrayList<DeductApplicationExecutionStatus>();
		for(Integer ordinal : ordinals) {
			if (ordinal == null) continue;
			DeductApplicationExecutionStatus executionStatus = EnumUtil.fromOrdinal(DeductApplicationExecutionStatus.class, ordinal);
			if (executionStatus == null) continue;
			executionStatusEnumList.add(executionStatus);
		}
		return executionStatusEnumList;
	}
	
	public List<RepaymentType> getRepaymentTypeList() {
		List<Integer> ordinals = JsonUtils.parseArray(this.getRepaymentType(), Integer.class);
		if (ordinals == null) {
			ordinals = new ArrayList<Integer>();
		}
		List<RepaymentType> repaymentTypeList = new ArrayList<RepaymentType>();
		for(Integer ordinal : ordinals) {
			if (ordinal == null) continue;
			RepaymentType repaymentType = EnumUtil.fromOrdinal(RepaymentType.class, ordinal);
			if (repaymentType == null) continue;
			repaymentTypeList.add(repaymentType);
		}
		return repaymentTypeList;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	
	
}
