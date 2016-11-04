package com.zufangbao.sun.yunxin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.api.TApiConfig;
import com.zufangbao.sun.yunxin.service.TApiConfigService;

@Service("TApiConfigService")
public class TApiConfigServiceImpl extends GenericServiceImpl<TApiConfig> implements TApiConfigService{

	@SuppressWarnings("unchecked")
	@Override
	public List<TApiConfig> getApiConfigListBy(String apiUrl) {
		String hql = "FROM TApiConfig WHERE apiUrl =:apiUrl";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("apiUrl", apiUrl);
		return genericDaoSupport.searchForList(hql, parameters);
	}

}
