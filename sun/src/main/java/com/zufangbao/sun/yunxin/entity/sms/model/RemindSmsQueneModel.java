package com.zufangbao.sun.yunxin.entity.sms.model;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplate;
import com.zufangbao.sun.yunxin.entity.sms.model.base.SmsQueneModel;

public class RemindSmsQueneModel extends SmsQueneModel{

	public RemindSmsQueneModel(AssetSet assetSet, ContractAccount contractAccount, SmsTemplate smsTemplate) {
		super(assetSet, contractAccount, smsTemplate);
	}

}
