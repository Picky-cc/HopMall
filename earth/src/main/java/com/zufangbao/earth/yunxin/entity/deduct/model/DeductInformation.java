package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.math.BigDecimal;
import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;

public class DeductInformation {
	
	private String  deductApplicationCode;
	
	//扣款创建日期
	private Date  deductCreateDate;
	
	private String deductStatus;
	
	private String repaymentType;
	
	private String remark;
	
	private BigDecimal planDeductAmount;
	
	private BigDecimal actualDeductAmount;
	//扣款受理时间
	private Date  deductReceiveTime;
	//扣款发生日期
	private Date  deductHappenTime;

	public DeductInformation(DeductApplication deductApplication) {
		this.deductApplicationCode = deductApplication.getDeductId();
		this.deductCreateDate = DateUtils.asDay(deductApplication.getApiCalledTime());
		this.deductStatus = deductApplication.getExecutionStatus().getChineseMessage();
		this.repaymentType = deductApplication.getRepaymentType().getChineseMessage();
		this.remark = deductApplication.getRemark();
		this.planDeductAmount = deductApplication.getPlannedDeductTotalAmount();
		this.actualDeductAmount = deductApplication.getActualDeductTotalAmount();
		this.deductReceiveTime = deductApplication.getCreateTime();
		this.deductHappenTime =  deductApplication.getLastModifiedTime();
	}

	public String getDeductApplicationCode() {
		return deductApplicationCode;
	}

	public void setDeductApplicationCode(String deductApplicationCode) {
		this.deductApplicationCode = deductApplicationCode;
	}



	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		deductStatus = deductStatus;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPlanDeductAmount() {
		return planDeductAmount;
	}

	public void setPlanDeductAmount(BigDecimal planDeductAmount) {
		this.planDeductAmount = planDeductAmount;
	}

	public BigDecimal getActualDeductAmount() {
		return actualDeductAmount;
	}

	public void setActualDeductAmount(BigDecimal actualDeductAmount) {
		this.actualDeductAmount = actualDeductAmount;
	}


	public Date getDeductHappenTime() {
		return deductHappenTime;
	}

	public void setDeductHappenTime(Date deductHappenTime) {
		this.deductHappenTime = deductHappenTime;
	}

	public Date getDeductCreateDate() {
		return deductCreateDate;
	}

	public void setDeductCreateDate(Date deductCreateDate) {
		this.deductCreateDate = deductCreateDate;
	}

	public Date getDeductReceiveTime() {
		return deductReceiveTime;
	}

	public void setDeductReceiveTime(Date deductReceiveTime) {
		this.deductReceiveTime = deductReceiveTime;
	}
	
	
	
	
}
