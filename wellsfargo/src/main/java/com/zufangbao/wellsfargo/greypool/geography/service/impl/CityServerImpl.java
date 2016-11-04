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
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;

/**
 * @author Xiepf
 * 2015年12月9日
 */
@Service("cityService")
public class CityServerImpl extends GenericServiceImpl<City> implements CityService{
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	private static Log logger = LogFactory.getLog(CityServerImpl.class);
	
	
	
	@Override
	@Cacheable("citys")
	public Map<String, City>  getCacheCitysMap(){
		logger.info("beign cache citys");
		List<City> citys = this.loadAll(City.class);
		Map<String,City> cityCodesMap = new HashMap<String,City>();
		for(City city:citys){
			cityCodesMap.put(city.getCode(), city);
		}
		return cityCodesMap;
		
	}
	
	@Override
	public List<City> getCityListByProvinceCode(String ProvinceCode) {
		return genericDaoSupport.searchForList("from City city where city.provinceCode = :provinceCode", "provinceCode", ProvinceCode); 
	}

	@Override
	public City getCityByCityCode(String cityCode) {

		if(StringUtils.isEmpty(cityCode)){
			return null;
		}
		List<City> cityList = genericDaoSupport.searchForList("From City where code=:code","code",cityCode);
		if(CollectionUtils.isEmpty(cityList)){
			return null;
		}
		return cityList.get(0);
	}

	@Override
	public City getCityByCityName(String cityName) {
		
		if(StringUtils.isEmpty(cityName)){
			return null;
		}
		List<City> cityList = genericDaoSupport.searchForList("From City where name=:name","name",cityName);
		if(CollectionUtils.isEmpty(cityList)){
			return null;
		}
		return cityList.get(0);
	}

	
	@Override
	@CacheEvict(value = "citys", allEntries = true)
	public void evictCachedCitys() {
		logger.info("evict cache for citys");
	}
}
