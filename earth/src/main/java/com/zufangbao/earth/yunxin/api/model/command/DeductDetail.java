package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 扣款明细
 * @author zhanghongbing
 *
 */
public class DeductDetail {
	
	/**
	 * 还款计划编号
	 */
	private String repaymentPlanNo;
	
	/**
	 * 科目
	 */
	private String subject;
	
	/**
	 * 扣款金额
	 */
	private String amount;

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JSONField(serialize = false)
	public boolean isValid() {
		if(StringUtils.isEmpty(this.repaymentPlanNo)) {
			return false;
		}
		if(StringUtils.isEmpty(this.subject)) {
			return false;
		}
		try {
			new BigDecimal(this.amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
