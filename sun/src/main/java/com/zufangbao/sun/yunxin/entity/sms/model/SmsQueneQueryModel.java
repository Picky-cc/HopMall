package com.zufangbao.sun.yunxin.entity.sms.model;

import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;

public class SmsQueneQueryModel {
	private SmsQuene smsQuene;
	private SmsTemplate smsTemplate;
	
	public SmsQueneQueryModel() {
		super();
	}

	public SmsQueneQueryModel(SmsQuene smsQuene, SmsTemplate smsTemplate) {
		super();
		this.smsQuene = smsQuene;
		this.smsTemplate = smsTemplate;
	}

	public SmsQuene getSmsQuene() {
		return smsQuene;
	}

	public void setSmsQuene(SmsQuene smsQuene) {
		this.smsQuene = smsQuene;
	}

	public SmsTemplate getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(SmsTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

}
