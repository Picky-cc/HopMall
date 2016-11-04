package com.zufangbao.earth.yunxin.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.YunxinOfflinePaymentHandler;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillModel;

@Component("yunxinOfflinePaymentHandler")
public class YunxinOfflinePaymentHandlerImpl implements
		YunxinOfflinePaymentHandler {

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Override
	public Map<String, Object> queryAllOfflineBillCorrespondingSourceDocumentByAuditStatus(
			OfflineBillQueryModel offlineBillQueryModel, Page page) {

		StringBuffer querySentence = new StringBuffer(
				"SELECT new com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillModel(a,b) FROM OfflineBill as a, SourceDocument as b WHERE a.offlineBillUuid = b.outlierDocumentUuid");

		if (!StringUtils.isEmpty(offlineBillQueryModel.getPayAcNo())) {
			querySentence.append(" AND a.payerAccountNo LIKE :payerAccountNo");
		}
		if (!StringUtils.isEmpty(offlineBillQueryModel.getAccountName())) {
			querySentence.append(" AND a.payerAccountName LIKE :payerAccountName");
		}
		querySentence.append(" order by a.statusModifiedTime desc");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("payerAccountName", "%"+offlineBillQueryModel.getAccountName()+"%");
		parameters.put("payerAccountNo", "%"+offlineBillQueryModel.getPayAcNo()+"%");
		
		List<OfflineBillModel> result = genericDaoSupport.searchForList(querySentence.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		
		List<OfflineBillModel> all = genericDaoSupport.searchForList(querySentence.toString(), parameters);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("size", all.size());
		resultMap.put("list", result);
		return resultMap;
	}
}
