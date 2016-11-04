package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;

public interface SystemOperateLogService  extends GenericService<SystemOperateLog>{
	
	public List<SystemOperateLog> getSystemOperateLogBy(String uuid, Page page);

	public List<SystemOperateLog> getLogsByUuid(String uuid);
	
	public long countSystemOperaterLogsBy(String uuid);
	
}
