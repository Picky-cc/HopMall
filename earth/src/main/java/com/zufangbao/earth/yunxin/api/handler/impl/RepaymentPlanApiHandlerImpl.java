package com.zufangbao.earth.yunxin.api.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.handler.ContractApiHandler;
import com.zufangbao.earth.yunxin.api.handler.RepaymentPlanApiHandler;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanQueryModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("RepaymentApiHandler")
public class RepaymentPlanApiHandlerImpl implements RepaymentPlanApiHandler {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private ContractApiHandler contractApiHandler;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	@Override
	public List<RepaymentPlanDetail> queryRepaymentPlanDetail(RepaymentPlanQueryModel queryModel) {
		
		Contract contract = contractApiHandler.getContractBy(queryModel.getUniqueId(), queryModel.getContractNo());
		
		List<AssetSet> assetSetList = repaymentPlanHandler.getEffectiveRepaymentPlansByContract(contract);
		
		List<RepaymentPlanDetail> repaymentPlanDetails = new ArrayList<RepaymentPlanDetail>();
		for(AssetSet repaymentPlan :assetSetList){
			Map<String,BigDecimal> amountMap = repaymentPlanExtraChargeService.getAssetSetExtraChargeModels(repaymentPlan.getAssetUuid());
			BigDecimal totalOverDueFee       = repaymentPlanExtraChargeService.getTotalOverDueFee(repaymentPlan.getAssetUuid());
			repaymentPlanDetails.add(new RepaymentPlanDetail(repaymentPlan,amountMap, totalOverDueFee));
		}
		

		return repaymentPlanDetails;
	}
}
