package com.zufangbao.sun.yunxin.handler.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetExtraCharge;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanExtraChargeHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("RepaymentPlanExtraChargeHandler")
public class RepaymentPlanExtraChargeHandlerImpl implements RepaymentPlanExtraChargeHandler{

	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	@Override
	public BigDecimal modifyFeeInAssetSetExtraCharge(AssetSet repaymentPlan, BigDecimal modifyFee,String charString) {
		   AssetSetExtraCharge assetSetExtraCharge = repaymentPlanExtraChargeService.getAssetSetExtraChargeByAssetSetUuidAndCharString( repaymentPlan.getAssetUuid(), charString);
		   if(assetSetExtraCharge == null){
			    repaymentPlanExtraChargeService.createAssetSetExtraCharge(repaymentPlan, modifyFee, charString);
			    return modifyFee;
		   }
		   assetSetExtraCharge.setAccountAmount(modifyFee);
		   repaymentPlanExtraChargeService.update(assetSetExtraCharge);
		   return modifyFee;
	}
	@Override
	public Map<String, BigDecimal> getLoanFeeBy(String assetSetUuid) {
		List<String> loanFeeAccounts = Arrays.asList(ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE,
				ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
		Map<String, BigDecimal> allExtraChargeModels = repaymentPlanExtraChargeService.getAssetSetExtraChargeModels(assetSetUuid);
		Map<String,BigDecimal> loanFeeMap = new HashMap<String,BigDecimal>();
		for(String loanFeeAccount: loanFeeAccounts){
			if(allExtraChargeModels.containsKey(loanFeeAccount)){
				loanFeeMap.put(loanFeeAccount, allExtraChargeModels.get(loanFeeAccount));
			}
		}
		return loanFeeMap;
	}

}
