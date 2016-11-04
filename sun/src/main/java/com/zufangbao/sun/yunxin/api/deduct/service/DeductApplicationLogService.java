package com.zufangbao.sun.yunxin.api.deduct.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplicationLog;

public interface DeductApplicationLogService extends GenericService<DeductApplicationLog>{

	public DeductApplicationLog getDeductApplicationLogByReuqestNo(String requestNo);
}
