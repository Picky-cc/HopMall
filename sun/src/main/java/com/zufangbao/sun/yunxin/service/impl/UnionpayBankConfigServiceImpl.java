package com.zufangbao.sun.yunxin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.unionpay.UnionpayBankConfig;
import com.zufangbao.sun.yunxin.service.UnionpayBankConfigService;

@Service("UnionpayBankConfigServiceImpl")
public class UnionpayBankConfigServiceImpl extends GenericServiceImpl<UnionpayBankConfig> implements UnionpayBankConfigService{

	private static Log log = LogFactory.getLog(UnionpayBankConfigServiceImpl.class);
	
	@Override
	@Cacheable("unionpayBankConfigs")
	public List<UnionpayBankConfig> getUnionpayBankConfigs() {
		log.info("start cache unionpayBankConfigs");
		return this.loadAll(UnionpayBankConfig.class);
	}

	@Override
	@CacheEvict(value = "unionpayBankConfigs", allEntries = true)
	public void cacheEvictUnionpayBankConfig() {
		log.info("evict cache for unionpayBankConfigs");
	}
	
	@Override
	public  boolean isUseBatchDeduct(String bankCode,String standardBankCode) {
		
		if( StringUtils.isEmpty(bankCode) && StringUtils.isEmpty(standardBankCode)){
			return false;
		}
		List<UnionpayBankConfig> unionpayBankConfigs = getUnionpayBankConfigs();
		boolean useBatchDeduct = unionpayBankConfigs
				.stream()
				.filter(ubc -> ubc.isUseBatchDeduct()
						&& (StringUtils.equals(bankCode, ubc.getBankCode()) || StringUtils.equals(standardBankCode, ubc.getStandardBankCode()) ))
				.count() > 0;
		return useBatchDeduct;
	}

	
}
