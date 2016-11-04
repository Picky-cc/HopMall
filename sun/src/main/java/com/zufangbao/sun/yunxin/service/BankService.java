package com.zufangbao.sun.yunxin.service;

import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.bank.Bank;

public interface BankService extends GenericService<Bank> {

	/**
	 * bank表数据（带缓存）
	 */
	public Map<String, Bank> getCachedBanks();

	/**
	 * 清除bank表数据缓存
	 */
	public void evictCachedBanks();
}
