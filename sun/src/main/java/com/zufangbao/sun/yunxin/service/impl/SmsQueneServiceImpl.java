package com.zufangbao.sun.yunxin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.SmsSendStatus;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplateEnum;
import com.zufangbao.sun.yunxin.service.SmsQueneService;

@Service("smsQueneService")
public class SmsQueneServiceImpl extends GenericServiceImpl<SmsQuene> implements
		SmsQueneService {

	private static final int HAS_BEEN_ALLOWED = 1;
	private static final int DEFAULT_ALL_STATUS = -1;
	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Override
	public List<SmsQuene> getAllNeedSendSmsQuene() {
		String queryString = "From SmsQuene where smsSendStatus = :smsSendStatus and allowedSendStatus = :allowedSendStatus";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("smsSendStatus", SmsSendStatus.WAITING_SEND);
		params.put("allowedSendStatus", true);
		return this.genericDaoSupport.searchForList(queryString, params);
	}

	@Override
	public List<SmsQuene> querySmsQuene(int allowedSendStatus, String clientId,
			int smsTemplateEnum, Page page, String startDate, String endDate) {
		StringBuffer queryString = new StringBuffer("From SmsQuene where 1=1");
		Map<String, Object> params = new HashMap<String, Object>();
		if (allowedSendStatus != DEFAULT_ALL_STATUS) {
			queryString.append(" And allowedSendStatus =:allowedSendStatus");
			params.put("allowedSendStatus", (allowedSendStatus == HAS_BEEN_ALLOWED));
		}
		if (smsTemplateEnum != DEFAULT_ALL_STATUS) {
			SmsTemplateEnum templateEnum = SmsTemplateEnum.fromValue(smsTemplateEnum);
			if(templateEnum != null) {
				queryString.append(" And templateCode =:templateCode");
				params.put("templateCode", templateEnum.getCode());
			}
		}
		if (is_where_condition(startDate)) {
			queryString.append(" And Date(createTime) >= :startDate");
			params.put("startDate", DateUtils.asDay(startDate));
		}
		if (is_where_condition(endDate)) {
			queryString.append(" And Date(createTime) <= :endDate");
			params.put("endDate", DateUtils.asDay(endDate));
		}
		if (is_where_condition(clientId)) {
			queryString.append(" And clientId LIKE :clientId");
			params.put("clientId", "%" + clientId + "%");
		}
		queryString.append(" order by id desc");
		List<SmsQuene> results = genericDaoSupport.searchForList(
				queryString.toString(), params, page.getBeginIndex(),
				page.getMaxResultRecords());
		page.calculate(results.size());
		return results.size() > page.getEveryPage() ? results.subList(0,
				page.getEveryPage()) : results;
	}

	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}
	
	@Override
	public void sendSuccSms() {
		String queryString = "Update sms_quene set allowed_send_status = :allowed_send_status where template_code = :template_code and allowed_send_status = :status";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("allowed_send_status", true);
		params.put("template_code", SmsTemplateEnum.LOAN_REPAY_SUCC.getCode());
		params.put("status", false);
		this.genericDaoSupport.executeSQL(queryString, params);
	}

	@Override
	public void deleteNotSuccSms() {
		String queryString = "delete from sms_quene where template_code <> :template_code and allowed_send_status = :allowed_send_status";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("template_code", SmsTemplateEnum.LOAN_REPAY_SUCC.getCode());
		params.put("allowed_send_status", false);
		this.genericDaoSupport.executeSQL(queryString, params);
	}

}