package com.zufangbao.sun.yunxin.api.deduct.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationLogService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplicationLog;

@Service("deductApplicationLogService")
public class DeductApplicationLogServiceImpl  extends GenericServiceImpl<DeductApplicationLog> implements DeductApplicationLogService {

	@Override
	public DeductApplicationLog getDeductApplicationLogByReuqestNo(String requestNo) {
		
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<DeductApplicationLog> logs = this.list(DeductApplicationLog.class, filter);
		if(CollectionUtils.isEmpty(logs)){
			return null;
		}
		return logs.get(0);
	}

}
