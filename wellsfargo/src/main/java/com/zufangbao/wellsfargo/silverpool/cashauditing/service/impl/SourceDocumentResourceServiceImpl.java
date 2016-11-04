package com.zufangbao.wellsfargo.silverpool.cashauditing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentResource;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentResourceService;

@Service("sourceDocumentResourceService")
public class SourceDocumentResourceServiceImpl extends GenericServiceImpl<SourceDocumentResource> implements SourceDocumentResourceService {

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceDocumentResource> get(String sourceDocumentUuid, String firstNo) {
		String sql = "FROM SourceDocumentResource WHERE sourceDocumentUuid = :sourceDocumentUuid OR batchNo = :batchNo AND status = :status";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sourceDocumentUuid", sourceDocumentUuid);
		parms.put("batchNo", firstNo);
		parms.put("status", true);
		return genericDaoSupport.searchForList(sql, parms);
	}

	@Override
	public void updateResourceConnectionRelation(List<String> resourceUuids, String sourceDocumentUuid, String requestNo) {
		if(CollectionUtils.isEmpty(resourceUuids)) {
			return;
		}
		String hql = "update SourceDocumentResource set sourceDocumentUuid =:sourceDocumentUuid, batchNo = :batchNo, status = :status where uuid in :uuids";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.putIfAbsent("sourceDocumentUuid", sourceDocumentUuid);
		parameters.putIfAbsent("batchNo", requestNo);
		parameters.putIfAbsent("uuids", resourceUuids);
		parameters.putIfAbsent("status", true);
		genericDaoSupport.executeHQL(hql, parameters);
		
	}

}

