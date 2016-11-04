package com.zufangbao.earth.yunxin.handler;

import java.util.List;

import com.zufangbao.sun.entity.financial.FinancialContract;

public interface FinancialContracCacheHandler {
	public List<FinancialContract> getAvailableFinancialContractList(Long princialId);

	public void cacheEvict(Long princialId);
	
	public void allCacheEvict();
}
