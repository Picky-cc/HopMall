package com.zufangbao.sun.api.model.deduct;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.sun.utils.CheckFormatUtils;
import com.zufangbao.sun.utils.ValidatorFieldType;

public class RepaymentDetail {

	private String repaymentDetailUuid = UUID.randomUUID().toString();

	private String repaymentPlanNo;
    	
	
	@ValidatorFieldType("BigDecimal")
	private BigDecimal repaymentAmount;

	@ValidatorFieldType("BigDecimal")
	private BigDecimal repaymentPrincipal;

	@ValidatorFieldType("BigDecimal")
	private BigDecimal repaymentInterest;

	@ValidatorFieldType("BigDecimal")
	private BigDecimal loanFee;

	@ValidatorFieldType("BigDecimal")
	private BigDecimal techFee;

	@ValidatorFieldType("BigDecimal")
	private BigDecimal otherFee;
	// 逾期费用合计
	@ValidatorFieldType("BigDecimal")
	private BigDecimal totalOverdueFee ;

	@JSONField(serialize = false)
	public String getRepaymentDetailUuid() {
		return this.repaymentDetailUuid;
	}

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
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

	public BigDecimal getTotalOverdueFee() {
		return totalOverdueFee;
	}

	public void setTotalOverdueFee(BigDecimal totalOverduFee) {
		this.totalOverdueFee = totalOverduFee;
	}

	public boolean isValid() {

		try {
			Map<String, BigDecimal> allAmounts = CheckFormatUtils
					.getAllAmountsFields(this);
			for (String field : allAmounts.keySet()) {
				{
					BigDecimal value=allAmounts.get(field);
				if (CheckFormatUtils
						.checkRMBCurrencyBigDecimal(value) == false)
					return false;
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			return false;
		}

		return true;

	}

	public BigDecimal calcRepaymentDetailTotalAmount() {

		return repaymentPrincipal.add(this.repaymentInterest).add(this.techFee).add(this.totalOverdueFee).add(this.loanFee).add(this.otherFee);

	}
}
