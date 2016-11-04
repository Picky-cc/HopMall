package com.zufangbao.sun.yunxin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.api.ModifyOverdueFeeLog;
import com.zufangbao.sun.yunxin.service.ModifyOverDueFeeLogService;

@Service("ModifyOverDueFeeLogService")
public class ModifyOverDueFeeLogServiceImpl extends GenericServiceImpl<ModifyOverdueFeeLog>
implements ModifyOverDueFeeLogService {

	@Override
	public ModifyOverdueFeeLog getLogByRequestNo(String requestNo) {

		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<ModifyOverdueFeeLog> result = this.list(ModifyOverdueFeeLog.class, filter);
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		return result.get(0);
	}
	
}
