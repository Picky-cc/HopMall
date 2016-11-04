package com.zufangbao.sun.yunxin.handler;

import java.math.BigDecimal;
import java.util.Map;

import com.zufangbao.sun.yunxin.entity.AssetSet;

public interface RepaymentPlanExtraChargeHandler {


	public  BigDecimal modifyFeeInAssetSetExtraCharge(AssetSet repaymentPlan,
			BigDecimal modifyFee, String charString);
	
	public Map<String,BigDecimal> getLoanFeeBy(String assetSetUuid);
}
