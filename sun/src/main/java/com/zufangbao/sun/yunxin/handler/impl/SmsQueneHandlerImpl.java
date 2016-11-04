package com.zufangbao.sun.yunxin.handler.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.utils.HttpUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.SmsSendStatus;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.SmsResult;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplateEnum;
import com.zufangbao.sun.yunxin.entity.sms.model.FailSmsQueneModel;
import com.zufangbao.sun.yunxin.entity.sms.model.OverDueSmsQueneModel;
import com.zufangbao.sun.yunxin.entity.sms.model.RemindSmsQueneModel;
import com.zufangbao.sun.yunxin.entity.sms.model.SmsQueneQueryModel;
import com.zufangbao.sun.yunxin.entity.sms.model.SuccessSmsQueneModel;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;
import com.zufangbao.sun.yunxin.exception.SmsTemplateNotExsitException;
import com.zufangbao.sun.yunxin.handler.SmsQueneHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SmsQueneService;
import com.zufangbao.sun.yunxin.service.SmsTemplateService;

@Component("smsQueneHandler")
public class SmsQueneHandlerImpl implements SmsQueneHandler {

	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsQueneService smsQueneService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private ContractAccountService contractAccountService;
	private static Log logger = LogFactory.getLog(SmsQueneHandlerImpl.class);

	@Override
	public void saveSmsQuene(SmsQueneModel smsQueneModel, boolean allowedSendStatus) {
		if (smsQueneModel == null) {
			return;
		}
		String clientId = smsQueneModel.getClientId();
		String templateCode = smsQueneModel.getSmsTemplate().getCode();
		String content = smsQueneModel.getSmsContent();
		String platformCode = smsQueneModel.getPlatformCode();
		SmsQuene smsQuene = new SmsQuene(clientId, templateCode, content, platformCode, allowedSendStatus);
		smsQueneService.saveOrUpdate(smsQuene);
	}

	@Override
	public void sendSmsQuene(String url, Serializable id) {
		SmsQuene smsQuene = smsQueneService.load(SmsQuene.class, id);
		if (smsQuene == null || !smsQuene.isAllowedSend()) {
			return;
		}
		try {
			String result = HttpUtils.post(url, smsQuene.getRequestParams(), HttpUtils.ENCODE);
			SmsResult smsResult = JsonUtils.parse(result, SmsResult.class);
			if (smsResult == null) {
				logger.error("短信接口异常, parse Json Error!");
				updateSmsQuene(smsQuene, false, "短信接口异常, parse Json Error!");
				return;
			}
			if (!smsResult.isSuccess()) {
				logger.error("短信发送结果失败, 短信队列 id:" + id + "错误消息:" + smsResult.getMessage());
			}
			updateSmsQuene(smsQuene, smsResult.isSuccess(), result);
		} catch (IOException e) {
			logger.error("短信接口异常," + e.getMessage(), e);
			updateSmsQuene(smsQuene, false, "短信接口异常," + e.getMessage());
		}
	}

	private void updateSmsQuene(SmsQuene smsQuene, boolean sendStatus, String message) {
		smsQuene.updateStatus(message, sendStatus);
		smsQueneService.saveOrUpdate(smsQuene);
	}

	@Override
	public void saveRemindSmsQuene(Serializable id, boolean allowedSendStatus) throws SmsTemplateNotExsitException {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, id);
		ContractAccount contractAccount = getContractAccountByAssetSet(assetSet);
		if (contractAccount == null) {
			return;
		}
		SmsTemplate smsTemplate = getSmsTemplateByCode(SmsTemplateEnum.LOAN_REPAY_REMINDER.getCode());
		RemindSmsQueneModel smsQueneModel = new RemindSmsQueneModel(assetSet, contractAccount, smsTemplate);
		saveSmsQuene(smsQueneModel, allowedSendStatus);
	}

	private SmsTemplate getSmsTemplateByCode(String code) throws SmsTemplateNotExsitException {
		SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateByCode(code);
		if (smsTemplate == null) {
			throw new SmsTemplateNotExsitException();
		}
		return smsTemplate;
	}

	private ContractAccount getContractAccountByAssetSet(AssetSet assetSet) {
		if (assetSet == null || assetSet.isUnavailable()) {
			return null;
		}	
		Contract contract = assetSet.getContract();
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		return contractAccount;
	}

	@Override
	public void saveSuccessSmsQuene(AssetSet assetSet, boolean allowedSendStatus) throws SmsTemplateNotExsitException {
		ContractAccount contractAccount = getContractAccountByAssetSet(assetSet);
		if (contractAccount == null) {
			return;
		}
		SmsTemplate smsTemplate = getSmsTemplateByCode(SmsTemplateEnum.LOAN_REPAY_SUCC.getCode());
		SuccessSmsQueneModel smsQueneModel = new SuccessSmsQueneModel(assetSet, contractAccount, smsTemplate);
		saveSmsQuene(smsQueneModel, allowedSendStatus);
	}

	@Override
	public void saveFailSmsQuene(AssetSet assetSet, boolean allowedSendStatus) throws SmsTemplateNotExsitException {
		ContractAccount contractAccount = getContractAccountByAssetSet(assetSet);
		if (contractAccount == null) {
			return;
		}
		SmsTemplate smsTemplate = getSmsTemplateByCode(SmsTemplateEnum.LOAN_REPAY_FAIL.getCode());
		FailSmsQueneModel smsQueneModel = new FailSmsQueneModel(assetSet, contractAccount, smsTemplate);
		saveSmsQuene(smsQueneModel, allowedSendStatus);
	}

	@Override
	public void activateSmsQuene(SmsQuene smsQuene) {
		smsQuene.setAllowedSendStatus(true);
		smsQueneService.update(smsQuene);
	}

	@Override
	public List<SmsQueneQueryModel> querySmsQueneQueryModelList(int allowedSendStatus, String clientId, int smsTemplateEnum,
			Page page, String startDate, String endDate) {
		List<SmsQueneQueryModel> smsQueneQueryModelList = new ArrayList<>();
		List<SmsQuene> smsQueneList = smsQueneService.querySmsQuene(allowedSendStatus, clientId, smsTemplateEnum, page,
				startDate, endDate);
		for (SmsQuene smsQuene : smsQueneList) {
			SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateByCode(smsQuene.getTemplateCode());
			if (smsTemplate == null) {
				continue;
			}
			SmsQueneQueryModel smsQueneQueryModel = new SmsQueneQueryModel(smsQuene, smsTemplate);
			smsQueneQueryModelList.add(smsQueneQueryModel);
		}

		return smsQueneQueryModelList;
	}

	@Override
	public void saveOverDueSmsQuene(Serializable id, boolean allowedSendStatus) throws SmsTemplateNotExsitException {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, id);
		ContractAccount contractAccount = getContractAccountByAssetSet(assetSet);
		if (contractAccount == null) {
			return;
		}
		SmsTemplate smsTemplate = getSmsTemplateByCode(SmsTemplateEnum.LOAN_REPAY_OVERDUE.getCode());
		OverDueSmsQueneModel smsQueneModel = new OverDueSmsQueneModel(assetSet, contractAccount, smsTemplate);
		saveSmsQuene(smsQueneModel, allowedSendStatus);
	}

	@Override
	public void reSendMessage(SmsQuene smsQueue) {
		smsQueue.setSmsSendStatus(SmsSendStatus.WAITING_SEND);
		smsQueneService.saveOrUpdate(smsQueue);
	}

	@Override
	public List<Serializable> getAllNeedSendSmsQueneIds() {
		List<SmsQuene> smsQueneList = smsQueneService.getAllNeedSendSmsQuene();
		return getIdsBy(smsQueneList);
	}

	private List<Serializable> getIdsBy(List<SmsQuene> smsQueneList) {
		List<Serializable> ids = new ArrayList<>();
		for (SmsQuene smsQuene : smsQueneList) {
			ids.add(smsQuene.getId());
		}
		return ids;
	}

	/**
	 * 一键发送所有未发送的成功扣款短信
	 */
	@Override
	public void sendSuccSms() {
		smsQueneService.sendSuccSms();
	}
	
	@Override
	public void deleteNotSuccSms() {
		smsQueneService.deleteNotSuccSms();
	}
	
}
