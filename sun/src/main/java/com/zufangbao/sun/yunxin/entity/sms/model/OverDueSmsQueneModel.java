package com.zufangbao.sun.yunxin.entity.sms.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;

public class OverDueSmsQueneModel extends SmsQueneModel {

	public OverDueSmsQueneModel(AssetSet assetSet, ContractAccount contractAccount, SmsTemplate smsTemplate) {
		super(assetSet, contractAccount, smsTemplate);
	}

	@Override
	public String getSmsContent() {
		String templateContent = getSmsTemplate().getTemplate();
		AssetSet assetSet = getAssetSet();
		String customerName = assetSet.getContract().getCustomer().getName();
		String singleLoanContractNo = assetSet.getSingleLoanContractNo();
		BigDecimal penaltyInterest = assetSet.getContract().getPenaltyInterest();
		Date date = new Date();
		String actualRecycleDate = DateUtils.format(date, DateUtils.DATE_FORMAT_CHINESE);
		String lastFourAccountNo = getContractAccount().getLastFourAccountNo();
		return String.format(templateContent, customerName, singleLoanContractNo, penaltyInterest, actualRecycleDate, lastFourAccountNo);
	}
	
}
