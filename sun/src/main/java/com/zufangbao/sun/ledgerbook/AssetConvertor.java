package com.zufangbao.sun.ledgerbook;

import java.util.Date;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;

public class AssetConvertor {
	
	public static AssetCategory convertAnMeiTuLoanAssetToAssetCategory(AssetSet set)
	{
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setFstLvLAssetUuid(set.getAssetUuid());
		assetCategory.setFstLvLAssetOuterIdentity(set.getSingleLoanContractNo());
		assetCategory.setDefaultDate(set.getAssetRecycleDate());
		assetCategory.setAmortizedDate(set.getAssetRecycleDate());
		assetCategory.setRelatedContractId(set.getContract().getId());
		assetCategory.setRelatedContractUuid(set.getContract().getUuid());
		return assetCategory;
	}
	public static AssetCategory convertAnMeiTupPenaltyAssetToAssetCategory(AssetSet set,AssetValuationDetail assetValuationDetail)
	{
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setFstLvLAssetUuid(set.getAssetUuid());
		assetCategory.setFstLvLAssetOuterIdentity(set.getSingleLoanContractNo());
		assetCategory.setSndLvlAssetUuid(assetValuationDetail.getAssetValuationDetailUuid());
		
		assetCategory.setDefaultDate(assetValuationDetail.getAssetValueDate());
		assetCategory.setAmortizedDate(assetValuationDetail.getAssetValueDate());
		assetCategory.setRelatedContractId(set.getContract().getId());
		assetCategory.setRelatedContractUuid(set.getContract().getUuid());
		return assetCategory;
	}
	
	public static AssetCategory convertAnMeiTuAssetCategory(AssetSet set,String sndLv1AssetUuid, Date amortizedDate){
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setFstLvLAssetUuid(set.getAssetUuid());
		assetCategory.setFstLvLAssetOuterIdentity(set.getSingleLoanContractNo());
		assetCategory.setSndLvlAssetUuid(sndLv1AssetUuid);
		
		assetCategory.setDefaultDate(amortizedDate);
		assetCategory.setAmortizedDate(amortizedDate);
		assetCategory.setRelatedContractId(set.getContract().getId());
		assetCategory.setRelatedContractUuid(set.getContract().getUuid());
		return assetCategory;
	}
	/** 贷款人充值 */
	public static AssetCategory convertDepositAssetCategory(Contract contract){
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setRelatedContractId(contract.getId());
		assetCategory.setRelatedContractUuid(contract.getUuid());
		return assetCategory;
	}
	/** 商户充值 */
	public static AssetCategory convertAppDepositAssetCategory(){
		AssetCategory assetCategory = new AssetCategory();
		return assetCategory;
	}
	/** 只对应到contract时 */
	public static AssetCategory convertAssetCategory(Contract contract){
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setRelatedContractId(contract.getId());
		assetCategory.setRelatedContractUuid(contract.getUuid());
		return assetCategory;
	}
	public static AssetCategory convertEmptyAssetCategory(){
		AssetCategory assetCategory = new AssetCategory();
		return assetCategory;
	}
}
