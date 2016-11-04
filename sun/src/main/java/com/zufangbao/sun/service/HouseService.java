package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.house.House;

public interface HouseService extends GenericService<House> {

	
	public House getHouseByAddress(String address);
}
