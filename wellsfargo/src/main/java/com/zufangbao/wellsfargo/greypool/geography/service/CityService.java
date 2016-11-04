
package com.zufangbao.wellsfargo.greypool.geography.service;

import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
/**
 * @author Xiepf
 * 2015年12月9日
 */
public interface CityService extends GenericService<City>{
	/** 根据省份编号 查询市列表*/
	public List<City> getCityListByProvinceCode(String ProvinceCode);
	
	public City getCityByCityCode(String cityCode);
	
	public City getCityByCityName(String cityName);

	public  void evictCachedCitys();

	public  Map<String, City> getCacheCitysMap();
}
