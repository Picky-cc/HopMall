package com.zufangbao.earth.api;

import java.util.List;

import com.demo2do.core.service.GenericService;

public interface RequestRecordService extends GenericService<RequestRecord>{
	
	boolean isReqNoExist(String reqNo);

	List<RequestRecord> getRequestRecordsBy(String reqNo);
}
