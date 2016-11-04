package com.zufangbao.wellsfargo.silverpool.cashauditing.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentResource;

public interface SourceDocumentResourceService extends GenericService<SourceDocumentResource> {

	List<SourceDocumentResource> get(String sourceDocumentUuid, String firstNo);

	void updateResourceConnectionRelation(List<String> resourceUuids, String sourceDocumentUuid, String requestNo);

}
