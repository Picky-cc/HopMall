package com.zufangbao.sun.yunxin.api.deduct.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductPlan;

public interface DeductPlanService  extends GenericService<DeductPlan>{

	
	public DeductPlan  getDeductPlanByUUid(String deductPlanUuid);


	public void updateDecuctPlan(String deductPlanUuid, int  businessStatus, BigDecimal transactionAmount, Date businessSuccessTime);


	public List<DeductPlan> getDeductPlanByDeductApplicationUuidAndInprocessing(String deductApplicationUuid);


	public List<DeductPlan> getDeductPlanByDeductApplicationUuid(
			String deductApplicationUuid);
	
	public DeductPlan getDeductPlanByDeductApplicationUuid(String deductApplicationUuid,DeductApplicationExecutionStatus exeStatus);
}
