package com.zufangbao.sun.yunxin.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;

public interface SmsTemplateService extends GenericService<SmsTemplate> {
	SmsTemplate getSmsTemplateByCode(String code);
}
