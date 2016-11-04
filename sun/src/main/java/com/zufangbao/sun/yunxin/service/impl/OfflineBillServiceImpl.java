package com.zufangbao.sun.yunxin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.service.OfflineBillService;

@Service("offlineBillService")
public class OfflineBillServiceImpl extends GenericServiceImpl<OfflineBill> implements
		OfflineBillService {

	@Override
	public OfflineBill getOfflineBillBy(String offlineBillUuid) {
		Filter filter = new Filter();
		filter.addEquals("offlineBillUuid", offlineBillUuid);
		List<OfflineBill> result = this.list(OfflineBill.class, filter);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public List<OfflineBill> getOfflineBillListBy(Collection<String> offlineBillUuids) {
		if(CollectionUtils.isEmpty(offlineBillUuids)){
			return Collections.emptyList();
		}
		String querySentence = "From OfflineBill where offlineBillUuid IN (:offlineBillUuids) ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("offlineBillUuids", offlineBillUuids);
		return genericDaoSupport.searchForList(querySentence, params);
	}

	@Override
	public List<String> extractOfflineBillNo(List<OfflineBill> offlineBills) {
		List<String> offlineBillNoList = new ArrayList<String>();
		for (OfflineBill offlineBill : offlineBills) {
			if(offlineBill==null) {
				continue;
			}
			offlineBillNoList.add(offlineBill.getOfflineBillNo());
		}
		return offlineBillNoList;
	}

	@Override
	public List<String> extractSerialNo(List<OfflineBill> offlineBills) {
		List<String> serialNoList = new ArrayList<String>();
		for (OfflineBill offlineBill : offlineBills) {
			if(offlineBill==null) {
				continue;
			}
			serialNoList.add(offlineBill.getSerialNo());
		}
		return serialNoList;
	}

	@Override
	public List<OfflineBill> queryOfflineBillListBy(
			String queryBeginDate, String queryEndDate) {
		Date beginDate = DateUtils.asDay(queryBeginDate);
		Date endDate   = DateUtils.asDay(queryEndDate);
		Map<String ,Object> params = new HashMap<String , Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		String querySentence = "from OfflineBill WHERE  Date(tradeTime) >=:beginDate and Date(createTime) <=:endDate";
		return this.genericDaoSupport.searchForList(querySentence,params);
	}

	@Override
	public OfflineBill getOfflineBillById(Long offlineBillId) {
		return this.genericDaoSupport.get(OfflineBill.class, offlineBillId);
	}

	
}
