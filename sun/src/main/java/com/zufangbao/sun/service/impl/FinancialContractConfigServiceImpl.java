package com.zufangbao.sun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentStrategyMode;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelSwitchResultListModel;
import com.zufangbao.sun.yunxin.entity.model.StrategyResultSaveModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

@Service("financialContractConfigService")
public class FinancialContractConfigServiceImpl extends GenericServiceImpl<FinancialContractConfig> implements FinancialContractConfigService {

	@Override
	public FinancialContractConfig getFinancialContractConfigBy(String financialContractUuid, BusinessType businessType) {
		if(StringUtils.isEmpty(financialContractUuid)){
			return null;
		}
		
		Filter filter = new Filter();
		filter.addEquals("financialContractUuid", financialContractUuid);
		if(businessType != null){
			filter.addEquals("businessType", businessType);
		}
		List<FinancialContractConfig> financialContractConfigs = this.list(FinancialContractConfig.class, filter);
		if(CollectionUtils.isNotEmpty(financialContractConfigs)){
			return financialContractConfigs.get(0);
		}
		return null;
	}

	@Override
	public boolean updateFinancialContractConfig(PaymentChannelInformation paymentChannelInformation) {
		String financialContractUuid = paymentChannelInformation.getRelatedFinancialContractUuid();
		String paymentChannelUuid = paymentChannelInformation.getPaymentChannelUuid();
		if(StringUtils.isEmpty(financialContractUuid)){
			return false;
		}
		FinancialContractConfig financialContractConfig = this.getFinancialContractConfigBy(financialContractUuid, paymentChannelInformation.getBusinessType());
		if(financialContractConfig == null){ // 未查到结果，需SAVE
			financialContractConfig = new FinancialContractConfig(paymentChannelInformation);
			this.save(financialContractConfig);
		}else{ // 查到结果，需UPDATE
			if(paymentChannelInformation.getCreditChannelWorkingStatus() != ChannelWorkingStatus.NOTLINK ){
				List<String> creditPaymentChannelList = financialContractConfig.getPaymentChannelUuidListForCredit();
				if(creditPaymentChannelList == null ||  !creditPaymentChannelList.contains(paymentChannelUuid)){
					financialContractConfig.addPaymentChannelUuidsForCredit(paymentChannelUuid);
				}
			}
			if(paymentChannelInformation.getDebitChannelWorkingStatus() != ChannelWorkingStatus.NOTLINK ){
				List<String> debitPaymentChannelList = financialContractConfig.getPaymentChannelUuidListForDebit();
				if(debitPaymentChannelList == null || !debitPaymentChannelList.contains(paymentChannelUuid)){
					financialContractConfig.addPaymentChannelUuidsForDebit(paymentChannelUuid);
				}
			}
			this.update(financialContractConfig);
		}
		return true;
	}

	@Override
	public List<PaymentChannelSwitchResultListModel> queryBy(int debitStrategyMode, int creditStrategyMode, String keyWord, Page page) {
		StringBuffer querySb = new StringBuffer("SELECT new com.zufangbao.sun.yunxin.entity.model.PaymentChannelSwitchResultListModel(fcc, fc) FROM FinancialContractConfig fcc, FinancialContract fc WHERE fcc.financialContractUuid=fc.financialContractUuid");//JOIN?
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(debitStrategyMode, creditStrategyMode, keyWord, querySb, parameters);		
		if(page == null){
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		}
		return this.genericDaoSupport.searchForList(querySb.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int queryCount(int debitStrategyMode, int creditStrategyMode, String keyWord){
		StringBuffer querySb = new StringBuffer("SELECT count(*) FROM FinancialContractConfig fcc, FinancialContract fc WHERE fcc.financialContractUuid=fc.financialContractUuid");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(debitStrategyMode, creditStrategyMode, keyWord, querySb, parameters);
		List result = this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		if(CollectionUtils.isNotEmpty(result)) {
			return result.size();
		}
		return 0;
	}
	
	private void genQuerySentence(int debitStrategyMode, int creditStrategyMode, String keyWord, StringBuffer querySb, Map<String, Object> parameters){
		PaymentStrategyMode debitStrategyModeEnum = PaymentStrategyMode.fromValue(debitStrategyMode);
		
		if(debitStrategyModeEnum != null){
			querySb.append(" AND fcc.debitPaymentChannelMode =:debitStrategyModeEnum");
			parameters.put("debitStrategyModeEnum", debitStrategyModeEnum);
		}
		
		PaymentStrategyMode creditStrategyModeEnum = PaymentStrategyMode.fromValue(creditStrategyMode);
		if(creditStrategyModeEnum != null){
			querySb.append(" AND fcc.creditPaymentChannelMode =:creditStrategyModeEnum ");
			parameters.put("creditStrategyModeEnum", creditStrategyModeEnum);
		}
		
		if( !StringUtils.isEmpty(keyWord) ){
			querySb.append(" AND (fc.contractName LIKE :keyWord OR fc.contractNo LIKE :keyWord) ");
			parameters.put("keyWord", "%"+keyWord+"%");
		}
		querySb.append(" GROUP BY fcc.financialContractUuid");
	}
	
	@Override
	public boolean updateCreditAndDebitStrategy(StrategyResultSaveModel strategyResultSaveModel, int businessType) {
		if(! strategyResultSaveModel.isValid()){
			return false;
		}
		BusinessType businessTypeEnum = EnumUtil.fromOrdinal(BusinessType.class, businessType);
		FinancialContractConfig financialContractConfig = getFinancialContractConfigBy(strategyResultSaveModel.getFinancialContractUuid(), businessTypeEnum);
		if(financialContractConfig == null){
			return true;
		}
		if(businessTypeEnum == BusinessType.SELF){ // 自有
			financialContractConfig.setCreditPaymentChannelMode(strategyResultSaveModel.getCreditStrategyModeEnum());
			financialContractConfig.setDebitPaymentChannelMode(strategyResultSaveModel.getDebitStrategyModeEnum());
			financialContractConfig.setPaymentChannelRouterForCredit(strategyResultSaveModel.getCreditChannelUuid());
			financialContractConfig.setPaymentChannelRouterForDebit(strategyResultSaveModel.getDebitChannelUuid());
		}else if(businessTypeEnum == BusinessType.ENTRUST){ // 委托
			financialContractConfig.setCreditPaymentChannelMode(strategyResultSaveModel.getAccreditStrategyModeEnum());
			financialContractConfig.setDebitPaymentChannelMode(strategyResultSaveModel.getAcdebitStrategyModeEnum());
			financialContractConfig.setPaymentChannelRouterForCredit(strategyResultSaveModel.getAccreditChannelUuid());
			financialContractConfig.setPaymentChannelRouterForDebit(strategyResultSaveModel.getAcdebitChannelUuid());
		}
		this.update(financialContractConfig);
		return true;
	}

	@Override
	public String getPaymentChannelInformationUuids(
			String financialContractUuid, BusinessType businessType, AccountSide accountSide) {
		if(StringUtils.isEmpty(financialContractUuid) || businessType==null){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("financialContractUuid", financialContractUuid);
		filter.addEquals("businessType", businessType);
		List<FinancialContractConfig> financialContractConfigs = this.list(FinancialContractConfig.class, filter);
		if(CollectionUtils.isEmpty(financialContractConfigs) || financialContractConfigs.size() !=1){
			return null;
		}
		FinancialContractConfig financialContractConfig = financialContractConfigs.get(0);
		if(accountSide == AccountSide.CREDIT){
			return financialContractConfig.getPaymentChannelRouterForCredit();
		}else if(accountSide == AccountSide.DEBIT){
			return financialContractConfig.getPaymentChannelRouterForDebit();
		}else{
			return null;
		}
	}
	
}
