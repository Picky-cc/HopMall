package com.zufangbao.sun.yunxin.handler;

import java.io.Serializable;
import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.model.SmsQueneQueryModel;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;
import com.zufangbao.sun.yunxin.exception.SmsTemplateNotExsitException;

public interface SmsQueneHandler {
	public void saveSmsQuene(SmsQueneModel smsQueneModel, boolean allowedSendStatus);
	
	public void sendSmsQuene(String url, Serializable id);
	
	public void saveRemindSmsQuene(Serializable id, boolean allowedSendStatus) throws SmsTemplateNotExsitException;
	
	public void saveSuccessSmsQuene(AssetSet assetSet, boolean allowedSendStatus) throws SmsTemplateNotExsitException;
	
	public void saveFailSmsQuene(AssetSet assetSet, boolean allowedSendStatus) throws SmsTemplateNotExsitException;
	
	public void activateSmsQuene(SmsQuene smsQuene);
	
	public List<SmsQueneQueryModel> querySmsQueneQueryModelList(int allowedSendStatus ,String clientId, int smsTemplateEnum, Page page, String startDate, String endDate);
	
	public void saveOverDueSmsQuene(Serializable id, boolean allowedSendStatus) throws SmsTemplateNotExsitException;

	public void reSendMessage(SmsQuene smsQueue);
	
	public List<Serializable> getAllNeedSendSmsQueneIds();
	
	public void sendSuccSms();

	public void deleteNotSuccSms();
	
}
