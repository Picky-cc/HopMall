package com.zufangbao.sun.yunxin.entity.model.loanbatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;

/**
 * 贷款批次查询条件
 * 
 * @author louguanyang
 *
 */
public class LoanBatchQueryModel {
	
	private String financialContractUuids;
	
	private String startDate;
	
	private String endDate;
	
	private String loanBatchCode;
	
	public String getFinancialContractUuids() {
		return financialContractUuids;
	}

	public void setFinancialContractUuids(String financialContractUuids) {
		this.financialContractUuids = financialContractUuids;
	}
	
	public List<String> getFinancialContractUuidList(){
		List<String> financialContractList = JsonUtils.parseArray(this.financialContractUuids,String.class);
		if(financialContractList==null){
			return new ArrayList<String>();
		}
		return financialContractList;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public Date getStartDateValue() {
		return startDate==null?null:DateUtils.asDay(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public Date getEndDateValue() {
		return endDate==null?null:DateUtils.asDay(endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getLoanBatchCode() {
		return loanBatchCode;
	}
	
	public void setLoanBatchCode(String loanBatchCode) {
		this.loanBatchCode = loanBatchCode;
	}

	public LoanBatchQueryModel() {
		super();
	}

	public LoanBatchQueryModel(String financialContractUuids, String startDate,
			String endDate, String loanBatchCode) {
		super();
		this.financialContractUuids = financialContractUuids;
		this.startDate = startDate;
		this.endDate = endDate;
		this.loanBatchCode = loanBatchCode;
	}
	
}
