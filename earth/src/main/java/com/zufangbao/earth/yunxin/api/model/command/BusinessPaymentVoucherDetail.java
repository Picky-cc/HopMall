package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.sun.utils.StringUtils;

/**
 * 商户还款凭证明细
 * @author louguanyang
 *
 */
public class BusinessPaymentVoucherDetail {
	/**
	 * 贷款合同唯一标识（必填）
	 */
	private String uniqueId;
	/**
	 * 还款计划编号（必填）
	 */
	private String repaymentPlanNo;
	/**
	 * 还款金额（必填）
	 */
	private BigDecimal amount = BigDecimal.ZERO;
	/**
	 * 付款人（0:贷款人，1:商户垫付）
	 */
	private Integer payer=0;
	
	public Integer getPayer() {
		return payer;
	}
	public void setPayer(Integer payer) {
		this.payer = payer;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}
	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@JSONField(serialize = false)
	public boolean isValid() {
		if(StringUtils.isEmpty(this.getUniqueId())){
			return false;
		}
		if(StringUtils.isEmpty(this.getRepaymentPlanNo())){
			return false;
		}
		if(this.getAmount().compareTo(BigDecimal.ZERO) <= 0){
			return false;
		}
		return true;
	}
}
