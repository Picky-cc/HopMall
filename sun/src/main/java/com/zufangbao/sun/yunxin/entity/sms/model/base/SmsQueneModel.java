package com.zufangbao.sun.yunxin.entity.sms.model.base;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;

public abstract class SmsQueneModel implements ISmsQueneBaseModel {

	private AssetSet assetSet;
	private ContractAccount contractAccount;
	private SmsTemplate smsTemplate;
	
	public SmsQueneModel(AssetSet assetSet, ContractAccount contractAccount, SmsTemplate smsTemplate) {
		this.assetSet = assetSet;
		this.contractAccount = contractAccount;
		this.smsTemplate = smsTemplate;
	}

	public AssetSet getAssetSet() {
		return assetSet;
	}

	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}

	public ContractAccount getContractAccount() {
		return contractAccount;
	}

	public void setContractAccount(ContractAccount contractAccount) {
		this.contractAccount = contractAccount;
	}

	@Override
	public String getClientId() {
		return assetSet.getContract().getCustomer().getSource();
	}

	public SmsTemplate getSmsTemplate() {
		return smsTemplate;
	}

	@Override
	public String getPlatformCode() {
		return assetSet.getContract().getApp().getAppId();
	}

	/**
	 * 默认短信内容：共5个参数
	 * 1.客户姓名，2.还款编号，3.金额（当前资产公允值），4.资产应还日期，5.扣款卡后4位
	 */
	@Override
	public String getSmsContent() {
		String templateContent = getSmsTemplate().getTemplate();
		AssetSet assetSet = getAssetSet();
		String customerName = assetSet.getContract().getCustomer().getName();
		String singleLoanContractNo = assetSet.getSingleLoanContractNo();
		BigDecimal assetFairValue = assetSet.getAssetFairValue();
		Date date = assetSet.getAssetRecycleDate();
		String assetRecycleDate = DateUtils.format(date, DateUtils.DATE_FORMAT_CHINESE);
		String lastFourAccountNo = getContractAccount().getLastFourAccountNo();
		return String.format(templateContent, customerName, singleLoanContractNo, assetFairValue, assetRecycleDate, lastFourAccountNo);
	}

}
