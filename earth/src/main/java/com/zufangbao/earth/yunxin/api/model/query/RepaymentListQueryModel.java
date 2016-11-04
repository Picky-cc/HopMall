package com.zufangbao.earth.yunxin.api.model.query;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;

public class RepaymentListQueryModel {

	private String requestNo;

	private String financialContractNo;

	private String queryStartDate;

	private String queryEndDate;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public boolean isVaild() {
		if (requestNo.isEmpty()||!isDateVaild(queryStartDate) || !isDateVaild(queryEndDate)) {
			return false;
		}
		Date beginDate = DateUtils.asDay(queryStartDate);
		Date endDate = DateUtils.asDay(queryEndDate);
		
		return !beginDate.after(endDate);
	}

	public boolean isQueryDateBetweenThreeMonths() {
		Date beginDate = DateUtils.asDay(queryStartDate);
		Date endDate = DateUtils.asDay(queryEndDate);
		Date deadLineDate = DateUtils.addMonths(beginDate, 3);
		return !endDate.after(deadLineDate);
	}

	private boolean isDateVaild(String queryDate) {
		return (!StringUtils.isEmpty(queryDate))
				&& (DateUtils.asDay(queryDate) != null);
	}

}
