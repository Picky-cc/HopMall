package com.zufangbao.sun.yunxin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetExtraCharge;

/**
 * @author zhenghangbo
 *
 */
public interface RepaymentPlanExtraChargeService extends GenericService<AssetSetExtraCharge>{
	public List<AssetSetExtraCharge> getExtraChargeList(String assetSetUuid);
	
	public AssetSetExtraCharge getAssetSetExtraChargeByAssetSetUuidAndCharString(String assetUuid, String charString);

	public void createAssetSetExtraCharge(AssetSet assetSet,BigDecimal amount, String chartString);

	public Map<String, BigDecimal> getAssetSetExtraChargeModels(String assetSetUuid);

	public BigDecimal getTotalOverDueFee(String assetSetUuid);

	public BigDecimal getTotalAmount(String assetSetUuid);
}
