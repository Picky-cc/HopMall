package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;

public class RepaymentInfo {

	
	private String repaymentCode;
	
	private BigDecimal repaymentAmount;

	public String getRepaymentCode() {
		return repaymentCode;
	}

	public void setRepaymentCode(String repaymentCode) {
		this.repaymentCode = repaymentCode;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	
	
	
}
