package com.zufangbao.earth.yunxin.handler;

import java.util.List;
import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.yunxin.entity.model.TransactionLimitQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public interface BankTransactionLimitSheetHandler {
	
	public List<Map<String, Object>> getAllBanks(String financialContractUuid, BusinessType businessType, AccountSide accountSide);

	Map<String, Object> searchTransactionLimitBy(TransactionLimitQueryModel queryModel, Page page);
	
	public List<Map<String, Object>> getBankLimitPreview(PaymentInstitutionName paymentInstitutionName, AccountSide accountSide, String outlierChannelName, Page page);
	
	public Map<String, Object> searchBankTransactionLimitBy(TransactionLimitQueryModel queryModel, Page page);
}
