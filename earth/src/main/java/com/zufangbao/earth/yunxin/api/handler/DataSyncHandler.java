package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;
import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;

public interface DataSyncHandler {

	public List<String> getPendingRepaymentPlanList();

	public RepayType      castRepayType(AssetSet assetSet);

	public DataSyncLog    generateSyncLog(String repaymentPlanUuid);
	
	public void           execSingleNotifyForDataSync(List<DataSyncLog> dataSyncLogList, int sendRule);
}
