package com.zufangbao.earth.api;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;

@Service("requestRecordService")
public class RequestRecordServiceImpl extends GenericServiceImpl<RequestRecord> implements RequestRecordService{

	@SuppressWarnings("unchecked")
	@Override
	public List<RequestRecord> getRequestRecordsBy(String reqNo) {
		if(StringUtils.isEmpty(reqNo)) {
			return Collections.EMPTY_LIST;
		}
		return genericDaoSupport.searchForList("FROM RequestRecord WHERE reqNo =:reqNo", "reqNo", reqNo);
	}

	@Override
	public boolean isReqNoExist(String reqNo) {
		if(StringUtils.isEmpty(reqNo)) {
			return false;
		}
		List<RequestRecord> recordList = genericDaoSupport.searchForList("FROM RequestRecord WHERE reqNo =:reqNo", "reqNo", reqNo);
		return !CollectionUtils.isEmpty(recordList);
	}

}

