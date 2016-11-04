package com.zufangbao.earth.yunxin.handler.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.cache.FinancialContractVsPrincipleCacheSpec;
import com.zufangbao.earth.yunxin.handler.FinancialContracCacheHandler;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;

@Component("financialContracCacheHandler")
public class FinancialContracCacheHandlerImpl implements
		FinancialContracCacheHandler {

	private static Log log = LogFactory.getLog(FinancialContracCacheHandlerImpl.class);

	@Autowired
	private FinancialContractService financialContractService;
	
	@Override
	@Cacheable(value=FinancialContractVsPrincipleCacheSpec.CACHE_KEY,key="#princialId")
	public List<FinancialContract> getAvailableFinancialContractList(
			Long princialId) {
		return financialContractService.getAvailableFinancialContractForPincipal(princialId);
	}

	@Override
	@CacheEvict(value=FinancialContractVsPrincipleCacheSpec.CACHE_KEY,key="#princialId")
	public void cacheEvict(Long princialId) {
		log.info("cacheEvict FinancialContracCache by key[principalId:"+princialId+"]");
	}

	@Override
	@CacheEvict(value=FinancialContractVsPrincipleCacheSpec.CACHE_KEY,allEntries=true)
	public void allCacheEvict() {
		log.info("cacheEvict FinancialContracCache.");
	}


}
