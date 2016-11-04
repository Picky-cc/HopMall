package com.zufangbao.sun.yunxin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.bank.Bank;
import com.zufangbao.sun.yunxin.service.BankService;


@Service("BankService")
public class BankServiceImpl extends GenericServiceImpl<Bank> implements BankService {

	private static Log log = LogFactory.getLog(BankServiceImpl.class);
	
	@Override
	@Cacheable("banks")
	public Map<String, Bank> getCachedBanks() {
		log.info("start cache banks");
		List<Bank> banks = this.loadAll(Bank.class);
		Map<String, Bank> bankMap = new HashMap<String, Bank>();
		for (Bank bank : banks) {
			String bankCode = bank.getBankCode();
			bankMap.put(bankCode, bank);
		}
		return bankMap;
	}

	@Override
	@CacheEvict(value = "banks", allEntries = true)
	public void evictCachedBanks() {
		log.info("evict cache for banks");
	}

}
