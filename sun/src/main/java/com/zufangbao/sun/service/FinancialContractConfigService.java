package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelSwitchResultListModel;
import com.zufangbao.sun.yunxin.entity.model.StrategyResultSaveModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public interface FinancialContractConfigService extends GenericService<FinancialContractConfig> {
	
	public FinancialContractConfig getFinancialContractConfigBy(String financialContractUuid, BusinessType businessType);
	
	public boolean updateFinancialContractConfig(PaymentChannelInformation paymentChannelInformation);
	
	public List<PaymentChannelSwitchResultListModel> queryBy(int debitStrategyMode, int creditStrategyMode, String keyWord, Page page);
	
	public int queryCount(int debitStrategyMode, int creditStrategyMode, String keyWord);
	
	public boolean updateCreditAndDebitStrategy(StrategyResultSaveModel strategyResultSaveModel, int businessType);

	public String getPaymentChannelInformationUuids(String financialContractUuid, BusinessType businessType, AccountSide accountSide);
}
