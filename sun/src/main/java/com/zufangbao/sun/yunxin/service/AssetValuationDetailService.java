package com.zufangbao.sun.yunxin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;

public interface AssetValuationDetailService extends GenericService<AssetValuationDetail>{

	public List<AssetValuationDetail> getAssetValuationDetailListByAsset(AssetSet assetSet, Date valuation_date_time);
	
	AssetValuationDetail get_today_asset_valuation_detail_by_asset_set_and_suject(AssetSet assetSet, AssetValuationSubject suject);

	/**
	 * 根据 资产，评估明细，资本化日 查询 出相应的 评估明细
	 * @param assetSet 资产
	 * @param subject	评估明细
	 * @param date	资本化日
	 * @return
	 */
	AssetValuationDetail get_asset_valuation_detail_by_asset_set_subject_and_date(AssetSet assetSet, AssetValuationSubject subject, Date date);

	AssetValuationDetail insert_asset_valuation_detail(AssetSet assetSet, Date valuationDate,
			BigDecimal amount, AssetValuationSubject assetValuationSubject) ;

}