package com.zufangbao.wellsfargo.greypool.geography.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;

/**
 * @author zhenghangbo
 * 2015年12月9日
 */
@Service("provinceService")
public class ProvinceServiceImpl extends GenericServiceImpl<Province> implements ProvinceService{
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	private static Log log = LogFactory.getLog(ProvinceServiceImpl.class);
	
	
	@Override
	@Cacheable("provinces")
	public Map<String, Province> getCacheProvinces(){
		log.info("beign cache provinces");
		List<Province> provinces = this.loadAll(Province.class);
 		Map<String,Province> provinceCodeMap = new HashMap<String,Province>();
 		for(Province province:provinces){
 			provinceCodeMap.put(province.getCode(), province);
 		}
 		return provinceCodeMap;
 	}

	@Override
	public Province getProvinceByCode(String code) {
		if(StringUtils.isEmpty(code)){
			return null;
		}
		List<Province> provinceList = genericDaoSupport.searchForList("From Province where code=:code","code",code);
		if(CollectionUtils.isEmpty(provinceList)){
			return null;
		}
		return provinceList.get(0);
	}

	@Override
	public Province getProvinceByProvinceName(String provinceName) {
		
		
		if(StringUtils.isEmpty(provinceName)){
			return null;
		}
		
		List<Province> provinceList = genericDaoSupport.searchForList("From Province where name=:name","name",provinceName);
		if(CollectionUtils.isEmpty(provinceList)){
			return null;
		}
		return provinceList.get(0);
	}
	@Override
	@CacheEvict(value = "provinces", allEntries = true)
	public void evictCachedProvinces() {
		log.info("evict cache for provinces");
	}

}
