package com.zufangbao.wellsfargo.greypool.geography.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.entity.District;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;
import com.zufangbao.wellsfargo.greypool.geography.service.DistrictService;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;

	/**
	 * @author Xiepf
	 * 2015年12月9日
	 */
	@Service("districtService")
	public class DistrictServiceImpl extends GenericServiceImpl<District> implements DistrictService{
		@Autowired
		private GenericDaoSupport genericDaoSupport;
		
		@Autowired
		private ProvinceService provinceService;
		
		@Autowired
		private CityService cityService;
		
		@Override
		public List<District> getDistrictListByCityCode(String CityCode) {
				return genericDaoSupport.searchForList("from District district where district.cityCode = :cityCode","cityCode", CityCode);
		}

		@Override
		public District getDistrictListByDistrictCode(String districtCode) {
			
			if(StringUtils.isEmpty(districtCode)){
				return null;
			}
			List<District> districtList = genericDaoSupport.searchForList("From District where code=:code","code",districtCode);
			if(CollectionUtils.isEmpty(districtList)){
				return null;
			}
			return districtList.get(0);
		}

		@Override
		public String getCompleteDistrictName(String provinceCode, String cityCode, String districtCode) {
			Province province = provinceService.getProvinceByCode(provinceCode);
			City city = cityService.getCityByCityCode(cityCode);
			District district = getDistrictListByDistrictCode(districtCode);
			return 		(province==null ? StringUtils.EMPTY:province.getName())
				   + 	(city    ==null ? StringUtils.EMPTY:city.getName())
				   + 	(district==null ? StringUtils.EMPTY:district.getName());
		}
	}
