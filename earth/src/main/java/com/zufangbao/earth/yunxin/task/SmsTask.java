package com.zufangbao.earth.yunxin.task;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.Dictionary;
import com.zufangbao.sun.yunxin.entity.DictionaryCode;
import com.zufangbao.sun.yunxin.exception.DictionaryNotExsitException;
import com.zufangbao.sun.yunxin.exception.SmsTemplateNotExsitException;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.handler.SmsQueneHandler;
import com.zufangbao.sun.yunxin.service.DictionaryService;

/**
 * 发送短信Task
 * 
 * @author louguanyang
 *
 */
@Component
public class SmsTask {
	@Autowired
	private SmsQueneHandler smsQueneHandler;
	@Autowired
	private DictionaryService dictionaryService; 
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	private static Log logger = LogFactory.getLog(SmsTask.class);
	
	public void sendSmsQuene() {
		logger.info("sendSmsQuene task start! " + DateUtils.getCurrentTimeMillis());
		try {
			List<Serializable> ids = smsQueneHandler.getAllNeedSendSmsQueneIds();
			Dictionary smsDictionary = dictionaryService.getDictionaryByCode(DictionaryCode.SMS_URL.getCode());
			for (Serializable id : ids) {
				smsQueneHandler.sendSmsQuene(smsDictionary.getContent(), id);
			}
		} catch (DictionaryNotExsitException e) {
			e.printStackTrace();
			logger.error("短信发送接口,请求网关地址未配置!", e);
		} 
		logger.info("sendSmsQuene task end! " + DateUtils.getCurrentTimeMillis());
	}
	
	public void createRemindAndOverDueSmsQuene() {
		logger.info("createRemindAndOverDueSmsQuene task start! " + DateUtils.getCurrentTimeMillis());
		boolean allowedSendStatus = false;
		try {
			Dictionary remindDayDictionary = dictionaryService.getDictionaryByCode(DictionaryCode.REMIND_DAY.getCode());
			int remindDay = new BigDecimal(remindDayDictionary.getContent()).intValue();
			allowedSendStatus = dictionaryService.getSmsAllowSendFlag();
			List<Serializable> ids = repaymentPlanHandler.get_all_needs_remind_assetSet_id_list(remindDay);
			for (Serializable id : ids) {
				smsQueneHandler.saveRemindSmsQuene(id, allowedSendStatus);
			}
		} catch (DictionaryNotExsitException e) {
			e.printStackTrace();
			logger.error("缺少提前N天提醒字典!", e);
		} catch (SmsTemplateNotExsitException e) {
			e.printStackTrace();
			logger.error("缺少提前N天提醒短信模版!", e);
		}
		
		/**
		 * 逾期，短信提醒暂不发送
		 */
//		try {
//			List<Serializable> ids = assetSetHandler.get_overDue_assetSet_id_list();
//			for (Serializable id : ids) {
//				smsQueneHandler.saveOverDueSmsQuene(id, allowedSendStatus);
//			}
//		} catch (SmsTemplateNotExsitException e) {
//			e.printStackTrace();
//			logger.error("逾期提醒短信模版不存在!", e);
//		}
		logger.info("createRemindAndOverDueSmsQuene task end! " + DateUtils.getCurrentTimeMillis());
	}
}