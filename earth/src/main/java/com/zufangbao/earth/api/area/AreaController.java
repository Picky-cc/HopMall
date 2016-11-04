package com.zufangbao.earth.api.area;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;

/** 
* @author 作者 zhenghangbo
* @version 创建时间：Oct 8, 2016 11:35:39 AM 
* 类说明 
*/


@Controller
@RequestMapping("/area")
public class AreaController {

	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService; 
	
	@RequestMapping("/getProvinceList")
	public @ResponseBody String getProvinceCodesMap(){
		
		Result result = new Result();
		try {
			List<Province>  provinceList  = provinceService.getCacheProvinces().values().stream().collect(Collectors.toList());
			return JsonUtils.toJsonString(result.success().data("provinceList", provinceList));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  JsonUtils.toJsonString(result.fail());
		}
		
	}
	
	@RequestMapping("/getCityList")
	public @ResponseBody String getCityCodesMap(@RequestParam("provinceCode") String provinceCode){

		Result result  = new Result();
		try {
			List<City> cityList= cityService.getCityListByProvinceCode(provinceCode);
			return JsonUtils.toJsonString(result.success().data("cityList", cityList));
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtils.toJsonString(result.fail());
		}
		
	}
	
	
}
