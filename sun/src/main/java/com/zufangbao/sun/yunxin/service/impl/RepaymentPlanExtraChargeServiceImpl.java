package com.zufangbao.sun.yunxin.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.utils.AmountUtil;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetExtraCharge;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;

@Service("RepaymentPlanExtraChargeService")
public class RepaymentPlanExtraChargeServiceImpl extends GenericServiceImpl<AssetSetExtraCharge> implements RepaymentPlanExtraChargeService{
	@Override
	public List<AssetSetExtraCharge> getExtraChargeList(String assetSetUuid) {
		Filter filter = new Filter();
		filter.addEquals("assetSetUuid",assetSetUuid);
		return this.list(AssetSetExtraCharge.class,filter);
	}
	
	
	@Override
	public AssetSetExtraCharge getAssetSetExtraChargeByAssetSetUuidAndCharString(String assetSetUuid, String charString) {

		Filter  filter = new Filter();
		filter.addEquals("assetSetUuid", assetSetUuid);
		List<AssetSetExtraCharge> extraCharges =  this.list(AssetSetExtraCharge.class, filter);
		if(CollectionUtils.isEmpty(extraCharges)){
			return null;
		}
		for(AssetSetExtraCharge extraCharge:extraCharges){
			if(charString.equals(extraCharge.lastAccountName())){
				return extraCharge;
			}
		}
		return null;
	}
	@Override
	public void createAssetSetExtraCharge(AssetSet assetSet,BigDecimal amount, String chartString) {
		if(AmountUtil.equals(amount, BigDecimal.ZERO)) {
			return;
		}
		AssetSetExtraCharge assetSetExtraCharge = new AssetSetExtraCharge(assetSet.getAssetUuid(),amount,chartString);
		this.save(assetSetExtraCharge);
	}

	@Override
	public Map<String, BigDecimal> getAssetSetExtraChargeModels(String assetSetUuid){
		
		List<AssetSetExtraCharge> assetSetExtraChargeAlls =getExtraChargeList(assetSetUuid);
		Map<String , BigDecimal> subjectDetail = new HashMap<String ,BigDecimal>();
		
		if(CollectionUtils.isEmpty(assetSetExtraChargeAlls)){
			return  subjectDetail;
		}
		for(AssetSetExtraCharge assetSetExtraCharge :assetSetExtraChargeAlls ){
			String subjectUuid =  assetSetExtraCharge.lastAccountName();
			subjectDetail.put(subjectUuid,assetSetExtraCharge.getAccountAmount());
	    }
		
		return subjectDetail;
	}
	
	
	@Override
	public BigDecimal getTotalOverDueFee(String assetSetUuid){
		 Map<String, BigDecimal>  amountMap = getAssetSetExtraChargeModels(assetSetUuid);
		 
		return getDetailOverDueFee(amountMap, ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY).
		        add(getDetailOverDueFee(amountMap, ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION)).
		        add(getDetailOverDueFee(amountMap, ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE)).
		        add(getDetailOverDueFee(amountMap, ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE));
	}
	
	@Override
	public BigDecimal getTotalAmount(String assetSetUuid){
		Map<String, BigDecimal>  amountMap = getAssetSetExtraChargeModels(assetSetUuid);
		
		BigDecimal totalAmount =  BigDecimal.ZERO;
		for(String key:amountMap.keySet()){
			totalAmount = totalAmount.add(amountMap.get(key));
		}
		return totalAmount;
	}
	
	
	
	private BigDecimal getDetailOverDueFee(Map<String, BigDecimal> amountMap,
			String charString) {
		if(amountMap.get( charString) != null){
			 return amountMap.get(charString);
		 }
		 return BigDecimal.ZERO;
	}
}
	
