package com.zufangbao.sun.yunxin.api.deduct.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductPlanService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductPlan;


@Service("deductPlanService")
public class DeductPlanServiceImpl  extends GenericServiceImpl<DeductPlan> implements DeductPlanService {

	
	//Buss
	private static final int ABANDON = 5;

	private static final int FAIL = 4;

	private static final int SUCCESS = 3;

	private static final int DOING = 2;


	@Override
	public DeductPlan getDeductPlanByUUid(String deductPlanUuid) {
		
		if(StringUtils.isEmpty(deductPlanUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("deductPlanUuid", deductPlanUuid);
		List<DeductPlan> deductPlans = this.list(DeductPlan.class, filter);
		if(CollectionUtils.isEmpty(deductPlans)){
			return null;
		}
		return deductPlans.get(0);
	}

	@Override
	public void updateDecuctPlan(String deductPlanUuid, int businessStatus, BigDecimal transactionAmount, Date businessSuccessTime) {
		
		DeductApplicationExecutionStatus excutionStatus = DeductApplicationExecutionStatus.PROCESSING ;
		switch (businessStatus) {
		case 0:
		case 1:
		case DOING:
			excutionStatus = DeductApplicationExecutionStatus.PROCESSING;
			break;
		case SUCCESS:
			excutionStatus = DeductApplicationExecutionStatus.SUCCESS;
			break;
		case FAIL:
			excutionStatus = DeductApplicationExecutionStatus.FAIL;
			break;
		case ABANDON:
			excutionStatus = DeductApplicationExecutionStatus.ABANDON;
			break;
		default:
			break;
		}
		BigDecimal successAmount = BigDecimal.ZERO;
		if(businessStatus == SUCCESS){
		    successAmount = transactionAmount;
		}
		Date completePaymentDate  =null;
		if(businessSuccessTime != null){
		 completePaymentDate = businessSuccessTime;
		}
		
		String queryString  = " UPDATE DeductPlan set executionStatus =: executionStatus,actualTotalAmount =:successAmount, lastModifiedTime =:lastModifiedTime,completePaymentDate =:completePaymentDate where deductPlanUuid =: deductPlanUuid ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("executionStatus", excutionStatus.ordinal());
		params.put("successAmount", successAmount);
		params.put("lastModifiedTime", new Date());
		params.put("deductPlanUuid",deductPlanUuid);
		params.put("completePaymentDate",completePaymentDate);
		this.genericDaoSupport.executeHQL(queryString, params);
	}
	
	@Override
	public List<DeductPlan> getDeductPlanByDeductApplicationUuidAndInprocessing(String deductApplicationUuid) {
		Filter filter = new Filter();
		filter.addEquals("deductApplicationUuid", deductApplicationUuid);
		filter.addEquals("executionStatus", DeductApplicationExecutionStatus.PROCESSING);
		return this.list(DeductPlan.class, filter);
	}
	
	@Override
	public List<DeductPlan> getDeductPlanByDeductApplicationUuid(
			String deductApplicationUuid) {
		String  queryString = "from DeductPlan  where  deductApplicationUuid =:deductApplicationUuid";
		return this.genericDaoSupport.searchForList(queryString,"deductApplicationUuid",deductApplicationUuid);
	}

	@Override
	public DeductPlan getDeductPlanByDeductApplicationUuid(String deductApplicationUuid,
			DeductApplicationExecutionStatus exeStatus) {
		String  queryString = "from DeductPlan  where  deductApplicationUuid =:deductApplicationUuid AND executionStatus=:executionStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deductApplicationUuid",deductApplicationUuid);
		params.put("executionStatus", exeStatus);
		List<DeductPlan> deductPlanList =  this.genericDaoSupport.searchForList(queryString,params);
		if(CollectionUtils.isEmpty(deductPlanList)){
			return null;
		}
		return deductPlanList.get(0);
	}

}
