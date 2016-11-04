package com.zufangbao.sun.yunxin.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.ModifyOverdueFeeLog;

public interface ModifyOverDueFeeLogService extends GenericService<ModifyOverdueFeeLog>{

	public ModifyOverdueFeeLog getLogByRequestNo(String requestNo);

	
}
