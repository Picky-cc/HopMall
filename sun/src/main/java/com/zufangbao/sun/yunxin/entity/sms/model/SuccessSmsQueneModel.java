package com.zufangbao.sun.yunxin.entity.sms.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;

public class SuccessSmsQueneModel extends SmsQueneModel {

	public SuccessSmsQueneModel(AssetSet assetSet, ContractAccount contractAccount, SmsTemplate smsTemplate) {
		super(assetSet, contractAccount, smsTemplate);
	}

	/**
	 * 扣款成功短信内容：共5个参数 1.客户姓名，2.还款编号，3.金额（当前资产公允值），4.扣款成功日期，5.扣款卡后4位
	 */
	@Override
	public String getSmsContent() {
		String templateContent = getSmsTemplate().getTemplate();
		AssetSet assetSet = getAssetSet();
		String customerName = assetSet.getContract().getCustomer().getName();
		String singleLoanContractNo = assetSet.getSingleLoanContractNo();
		BigDecimal assetFairValue = assetSet.getAssetFairValue();
		Date date = assetSet.getActualRecycleDate();
		String actualRecycleDate = DateUtils.format(date, DateUtils.DATE_FORMAT_CHINESE);
		String lastFourAccountNo = getContractAccount().getLastFourAccountNo();
		return String.format(templateContent, customerName, singleLoanContractNo, assetFairValue, actualRecycleDate, lastFourAccountNo);
	}

}
