package com.zufangbao.wellsfargo.greypool.geography.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.wellsfargo.greypool.geography.entity.District;

/**
 * @author Xiepf
 * 2015年12月9日
 */
public interface DistrictService extends GenericService<District>{
	/** 根据城市编号 查询地区列表*/
	public List<District> getDistrictListByCityCode(String CityCode);
	
	public District getDistrictListByDistrictCode(String districtCode);
	
	public String getCompleteDistrictName(String provinceCode, String cityCode, String districtCode);
}
