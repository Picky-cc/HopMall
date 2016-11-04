package com.zufangbao.sun.yunxin.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.TMerConfig;

public interface TMerConfigService extends GenericService<TMerConfig>{
	TMerConfig getTMerConfig(String merId, String secret);
}
