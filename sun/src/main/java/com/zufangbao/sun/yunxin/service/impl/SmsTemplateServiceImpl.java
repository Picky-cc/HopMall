package com.zufangbao.sun.yunxin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.service.SmsTemplateService;

@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends GenericServiceImpl<SmsTemplate> implements SmsTemplateService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Override
	public SmsTemplate getSmsTemplateByCode(String code) {
		String queryString = "From SmsTemplate where code =:code";
		List<SmsTemplate> result = this.genericDaoSupport.searchForList(queryString, "code", code);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}



}
