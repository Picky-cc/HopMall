package com.zufangbao.sun.yunxin.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;

@Service("assetValuationDetailService")
public class AssetValuationDetailServiceImpl extends GenericServiceImpl<AssetValuationDetail>
		implements AssetValuationDetailService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Override
	public List<AssetValuationDetail> getAssetValuationDetailListByAsset(AssetSet assetSet, Date valuation_date_time) {
		if (assetSet == null) {
			return Collections.emptyList();
		}
		Date assetRecycleDate = assetSet.getAssetRecycleDate();
		String queryString = "from AssetValuationDetail where assetSet =:assetSet AND createdDate >=:assetRecycleDate AND createdDate <=:valuationDay";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetSet", assetSet);
		parameters.put("assetRecycleDate", assetRecycleDate);
		parameters.put("valuationDay", DateUtils.asDay(valuation_date_time));
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@Override
	public AssetValuationDetail get_today_asset_valuation_detail_by_asset_set_and_suject(AssetSet assetSet,
			AssetValuationSubject suject) {
		return get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet, suject, DateUtils.asDay(new Date()));
	}

	@Override
	public AssetValuationDetail get_asset_valuation_detail_by_asset_set_subject_and_date(AssetSet assetSet,
			AssetValuationSubject subject, Date date) {
		String queryString = "from AssetValuationDetail where assetSet =:assetSet AND subject =:subject";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetSet", assetSet);
		parameters.put("subject", subject);
		if (date != null) {
			queryString += " AND createdDate = :createdDate";
			parameters.put("createdDate", date);
		}
		List<AssetValuationDetail> result = this.genericDaoSupport.searchForList(queryString, parameters);
		if (CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}

	
	@Override
	public AssetValuationDetail insert_asset_valuation_detail(AssetSet assetSet, Date valuationDate,
			BigDecimal amount, AssetValuationSubject assetValuationSubject) {
		if (assetSet == null || assetSet.getContract() == null) {
			return null;
		}
		AssetValuationDetail assetValuationDetail = new AssetValuationDetail(assetSet, assetValuationSubject, amount, valuationDate);
		this.save(assetValuationDetail);
		return assetValuationDetail;
	}

}
