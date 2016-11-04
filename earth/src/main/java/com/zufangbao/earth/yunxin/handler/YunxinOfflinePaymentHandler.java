package com.zufangbao.earth.yunxin.handler;

import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillQueryModel;

public interface YunxinOfflinePaymentHandler {

	
	Map<String, Object> queryAllOfflineBillCorrespondingSourceDocumentByAuditStatus(OfflineBillQueryModel offlineBillQueryModel, Page page);
}
