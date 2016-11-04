package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;

public interface SmsQueneService extends GenericService<SmsQuene>{
	public List<SmsQuene> getAllNeedSendSmsQuene();
	
	public List<SmsQuene> querySmsQuene(int allowedSendStatus,
			String clientId, int smsTemplateEnum, Page page, String startDate, String endDate);

	public void sendSuccSms();
	
	public void deleteNotSuccSms();

}
