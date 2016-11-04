package com.zufangbao.earth.yunxin.handler;

import java.util.List;
import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.yunxin.entity.model.StrategySwitchResultSubmitModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public interface FinancialContractConfigHandler {
	
	public Map<String, Object> queryBy(int debitStrategyMode, int creditStrategyMode, String keyWord, Page page);
	
	public Map<String, Object> getSwitchDetail(String financialContractUuid);
	
	public List<Map<String, Object>> getDebitStrategyList(String financialContractUuid, int businessType);
	
	public List<Map<String, Object>> getCreditStrategyList(String financialContractUuid, int businessType);
	
	public Map<String, Object> getActiveCreditAndDebitStrategy(String financialContractUuid, BusinessType businessType);
	
	public Map<String, Object> getSwitchDetailInfo(String financialContractUuid);
	
	public List<Map<String, Object>> extractAvailableBriefPaymentChannelInfo(AccountSide accountSide, FinancialContractConfig financialContractConfig);
	
	public List<Map<String, Object>> previewPaymentChannelOrderForBanks(String financialContractUuid, BusinessType businessType, AccountSide accountSide);
	
	public boolean updateConfig(StrategySwitchResultSubmitModel submitModel);
}
