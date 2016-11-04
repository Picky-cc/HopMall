package com.zufangbao.sun.yunxin.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;
import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;

public interface DataSyncLogService extends GenericService<DataSyncLog> {

	public int countSuccessAssetSet(String assetSetUuid, RepayType repayType);
	
}
