package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.TApiConfig;

public interface TApiConfigService extends GenericService<TApiConfig>{

	public List<TApiConfig> getApiConfigListBy(String apiUrl);
	
}
