package com.zufangbao.wellsfargo.greypool.geography.service;

import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;

/**
 * @author Xiepf
 * 2015年12月9日
 */
public interface ProvinceService extends GenericService<Province>{
	
	public Province getProvinceByCode(String code);
	
	public Province getProvinceByProvinceName(String provinceName);

	public Map<String, Province> getCacheProvinces();

	public void evictCachedProvinces();
}
