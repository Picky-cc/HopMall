package com.zufangbao.earth.yunxin.handler;

import java.util.List;

import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RemittanceDataStatistic;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;

public interface DataStatisticsCacheHandler  {
	
	public RepaymentDataStatistic getRepaymentDataFromOrRefreshCache(List<Long> financialContractIds);
	
	public RemittanceDataStatistic getRemittanceDataFromOrRefreshCache(List<Long> financialContractIds);
	
	//financialContractCache
	public void cacheEvict(List<Long> financialContractIds);
	
}
