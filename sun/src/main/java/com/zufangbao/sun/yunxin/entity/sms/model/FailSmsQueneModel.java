package com.zufangbao.sun.yunxin.entity.sms.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;

public class FailSmsQueneModel extends SmsQueneModel {

	public FailSmsQueneModel(AssetSet assetSet, ContractAccount contractAccount, SmsTemplate smsTemplate) {
		super(assetSet, contractAccount, smsTemplate);
	}

	/**
	 * 扣款失败短信内容：共5个参数 1.客户姓名，2.还款编号，3.应还日，4.正常本息合计金额值（不含罚息），5.当前日期，6.扣款卡后4位
	 */
	@Override
	public String getSmsContent() {
		String templateContent = getSmsTemplate().getTemplate();
		AssetSet assetSet = getAssetSet();
		String customerName = assetSet.getContract().getCustomer().getName();
		String singleLoanContractNo = assetSet.getSingleLoanContractNo();
		BigDecimal amount = assetSet.getAssetInitialValue();
		String assetRecycleDate = DateUtils.format(assetSet.getAssetRecycleDate(), DateUtils.DATE_FORMAT_CHINESE);
		String actualRecycleDate = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_CHINESE);
		String lastFourAccountNo = getContractAccount().getLastFourAccountNo();
		return String.format(templateContent, customerName, singleLoanContractNo, assetRecycleDate, amount, actualRecycleDate, lastFourAccountNo);
	}

}
