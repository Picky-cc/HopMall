package com.zufangbao.sun.yunxin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.TMerConfig;
import com.zufangbao.sun.yunxin.service.TMerConfigService;

@Service("tMerConfigService")
public class TMerConfigSerciveImpl extends GenericServiceImpl<TMerConfig> implements TMerConfigService{

	@Override
	@SuppressWarnings("unchecked")
	public TMerConfig getTMerConfig(String merId, String secret) {
		if(StringUtils.isEmpty(merId) || StringUtils.isEmpty(secret)) {
			return null;
		}
		String hql = "FROM TMerConfig WHERE merId =:merId AND secret = :secret";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("merId", merId);
		parameters.put("secret", secret);
		List<TMerConfig> result = genericDaoSupport.searchForCacheableList(hql, parameters);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}

}