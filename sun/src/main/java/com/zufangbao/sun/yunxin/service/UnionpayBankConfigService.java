package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.unionpay.UnionpayBankConfig;

public interface UnionpayBankConfigService extends GenericService<UnionpayBankConfig>{

	public void cacheEvictUnionpayBankConfig();

	public List<UnionpayBankConfig> getUnionpayBankConfigs();

	public boolean isUseBatchDeduct(String bankCode, String standardBankCode);

}
