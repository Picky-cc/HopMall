package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.service.HouseService;

@Service("houseService")
public class HouseServiceImpl extends GenericServiceImpl<House> implements HouseService {

	@Override
	public House getHouseByAddress(String address) {
		
		Filter filter = new Filter();
		filter.addEquals("address", address);
		List<House> houseList = this.list(House.class, filter);
		if(CollectionUtils.isEmpty(houseList)){
			return null;
		}
		return  houseList.get(0);
	}

}
