
package com.zufangbao.sun.yunxin.entity.excel;

import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class RepaymentPlanExcelVO {

	
	@ExcelVoAttribute(name = "还款日期", column = "A")
	private String repaymentDate;
	@ExcelVoAttribute(name = "返还收益", column = "B")
	private String repatriatedEarnings;
	@ExcelVoAttribute(name = "计划本金/计算本金", column = "C")
	private String principal;
	@ExcelVoAttribute(name = "返还本金", column = "D")
	private String repatriatedPrincipal;
	@ExcelVoAttribute(name = "返还种类", column = "E")
	private String repatriatedType;
	@ExcelVoAttribute(name = "收益类别", column = "F")
	private String earningsType;
	@ExcelVoAttribute(name = "还款方式", column = "G")
	private String repaymentWay;
	@ExcelVoAttribute(name = "开始日期", column = "H")
	private String startDate;
	@ExcelVoAttribute(name = "结束日期", column = "I")
	private String endDate;
	@ExcelVoAttribute(name = "利率", column = "J")
	private String interestRate;
	@ExcelVoAttribute(name = "计息天数", column = "K")
	private String interestDays;
	@ExcelVoAttribute(name = "公式算法", column = "L")
	private String formula;
	@ExcelVoAttribute(name = "备注信息", column = "M")
	private String comment;
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getRepatriatedEarnings() {
		return repatriatedEarnings;
	}
	public void setRepatriatedEarnings(String repatriatedEarnings) {
		this.repatriatedEarnings = repatriatedEarnings;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getRepatriatedPrincipal() {
		return repatriatedPrincipal;
	}
	public void setRepatriatedPrincipal(String repatriatedPrincipal) {
		this.repatriatedPrincipal = repatriatedPrincipal;
	}
	public String getRepatriatedType() {
		return repatriatedType;
	}
	public void setRepatriatedType(String repatriatedType) {
		this.repatriatedType = repatriatedType;
	}
	public String getEarningsType() {
		return earningsType;
	}
	public void setEarningsType(String earningsType) {
		this.earningsType = earningsType;
	}
	public String getRepaymentWay() {
		return repaymentWay;
	}
	public void setRepaymentWay(String repaymentWay) {
		this.repaymentWay = repaymentWay;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getInterestDays() {
		return interestDays;
	}
	public void setInterestDays(String interestDays) {
		this.interestDays = interestDays;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
