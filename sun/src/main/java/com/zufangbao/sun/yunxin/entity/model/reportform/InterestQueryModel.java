package com.zufangbao.sun.yunxin.entity.model.reportform;

import java.util.Date;

import com.zufangbao.sun.utils.DateUtils;

/**
 * 应收利息管理报表 查询Model
 * 
 * @author louguanyang
 *
 */
public class InterestQueryModel {
	private Long financialContractId = -1l;
	private String startDateString;
	private String endDateString;

	public Long getFinancialContractId() {
		return financialContractId;
	}

	public void setFinancialContractId(Long financialContractId) {
		this.financialContractId = financialContractId;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public Date getEndDate() {
		try {
			return DateUtils.asDay(endDateString);
		} catch (Exception e) {
			return DateUtils.asDay(new Date());
		}
	}
	
}
