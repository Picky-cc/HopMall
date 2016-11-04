package com.zufangbao.sun.yunxin.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@Service("systemOperateLogService")
public class SystemOperateLogServiceImpl extends
		GenericServiceImpl<SystemOperateLog> implements SystemOperateLogService {

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemOperateLog> getSystemOperateLogBy(String uuid, Page page) {

		if(StringUtils.isEmpty(uuid)) {
			return Collections.emptyList();
		}
		
		String querySentence = getSystemOperateLogQuerySentence();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uuid", uuid);
		params.put("logOperateType", LogOperateType.EXPORT);
		params.put("likeUuid", "%" + uuid + "%");
		
		return this.genericDaoSupport.searchForList(querySentence, params, page.getBeginIndex(), page.getEveryPage());
	}

	private String getSystemOperateLogQuerySentence() {
		String querySentence = "FROM SystemOperateLog WHERE objectUuid =:uuid "
				+ " OR (logOperateType =:logOperateType AND recordContentDetail LIKE :likeUuid) "
				+ " ORDER BY occurTime desc";
		return querySentence;
	}
	
	private String getCountSystemOperateLogSentence() {
		String querySentence = getSystemOperateLogQuerySentence();
		StringBuffer countSentenceBuffer = new StringBuffer("SELECT count(id) ");
		countSentenceBuffer.append(querySentence);
		return countSentenceBuffer.toString();
	}

	@Override
	public List<SystemOperateLog> getLogsByUuid(String uuid) {
		if(StringUtils.isEmpty(uuid)) {
			return Collections.emptyList();
		}
		Filter filter = new Filter();
		filter.addEquals("objectUuid", uuid);
		Order order = new Order("occurTime", "desc");
		order.add("id", "desc");
		return this.list(SystemOperateLog.class, filter, order);
	}

	@Override
	public long countSystemOperaterLogsBy(String uuid) {
		if(StringUtils.isEmpty(uuid)) {
			return 0;
		}
		
		String countSentence = getCountSystemOperateLogSentence();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uuid", uuid);
		params.put("logOperateType", LogOperateType.EXPORT);
		params.put("likeUuid", "%" + uuid + "%");
		List result = this.genericDaoSupport.searchForList(countSentence, params);
		return ((Long) result.get(0)).longValue();
	}


}
