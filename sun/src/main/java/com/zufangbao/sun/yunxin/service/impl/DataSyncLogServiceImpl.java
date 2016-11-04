package com.zufangbao.sun.yunxin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;
import com.zufangbao.sun.yunxin.service.DataSyncLogService;
import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;


@Service("dataSyncLogService")
public class DataSyncLogServiceImpl extends GenericServiceImpl<DataSyncLog> implements DataSyncLogService {

	@Override
	public int countSuccessAssetSet(String assetSetUuid, RepayType repayType) {
		if(StringUtils.isBlank(assetSetUuid) || repayType == null){
			return -1;
		}
		String queryStr = "SELECT COUNT(id) FROM DataSyncLog WHERE assetSetUuid = :uuid AND repayType = :type AND isSuccess = :suc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uuid", assetSetUuid);
		params.put("type", repayType);
		params.put("suc", true);
		return this.genericDaoSupport.searchForInt(queryStr, params);
	}
	
}
