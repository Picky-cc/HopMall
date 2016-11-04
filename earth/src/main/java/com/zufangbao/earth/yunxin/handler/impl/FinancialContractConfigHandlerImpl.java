package com.zufangbao.earth.yunxin.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.web.controller.reapymentInfo.BankCoreCodeMapSpec;
import com.zufangbao.earth.yunxin.handler.FinancialContractConfigHandler;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentStrategyMode;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelSwitchResultListModel;
import com.zufangbao.sun.yunxin.entity.model.StrategySwitchResultSubmitModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;


@Component("financialContractConfigHandler")
public class FinancialContractConfigHandlerImpl implements  FinancialContractConfigHandler {
	
	@Autowired
	private FinancialContractConfigService financialContractConfigService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	@Override
	public Map<String, Object> queryBy(int debitStrategyMode, int creditStrategyMode, String keyWord, Page page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<PaymentChannelSwitchResultListModel> resultList = financialContractConfigService.queryBy(debitStrategyMode, creditStrategyMode, keyWord, page);
		int allSize = financialContractConfigService.queryCount(debitStrategyMode, creditStrategyMode, keyWord);
		resultMap.put("list", resultList);
		resultMap.put("size", allSize);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getSwitchDetail(String financialContractUuid) {
		Map<String, Object> resultDataMap = new HashMap<>();
		FinancialContract fc = financialContractService.getFinancialContractBy(financialContractUuid);
		if(fc == null){
			return resultDataMap;
		}
		
		resultDataMap.put("financialContractUuid", financialContractUuid);
		resultDataMap.put("contractNo", fc.getContractNo());
		resultDataMap.put("contractName", fc.getContractName());
		Account account = fc.getCapitalAccount();
		resultDataMap.put("bankNameUnionAccountNo", account.getBankName() + "(" + account.getMarkedAccountNo() + ")" );
		
		// 自有
		FinancialContractConfig financialContractConfig = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, BusinessType.SELF);
		if(financialContractConfig != null){
			PaymentStrategyMode creditPaymentChannelMode = financialContractConfig.getCreditPaymentChannelMode();
			resultDataMap.put("creditPaymentChannelMode", creditPaymentChannelMode==null ? "" : creditPaymentChannelMode.getChineseMessage());
			resultDataMap.put("creditPaymentChannelModeOrdinal", creditPaymentChannelMode==null ? -1 : creditPaymentChannelMode.ordinal());
			
			PaymentStrategyMode debitPaymentChannelMode = financialContractConfig.getDebitPaymentChannelMode();
			resultDataMap.put("debitPaymentChannelMode", debitPaymentChannelMode == null ? "" : debitPaymentChannelMode.getChineseMessage());
			resultDataMap.put("debitPaymentChannelModeOrdinal", debitPaymentChannelMode == null ? -1 : debitPaymentChannelMode.ordinal());
			
			resultDataMap.put("creditTransaction", paymentChannelInformationService.getPaymentChannelCreditDetailForSwitchDetail(financialContractConfig.getPaymentChannelRouterForCredit()));
			resultDataMap.put("debitTransaction", paymentChannelInformationService.getPaymentChannelDebitDetailForSwitchDetail(financialContractConfig.getPaymentChannelRouterForDebit()));
		}
	
		// 委托
		FinancialContractConfig acfinancialContractConfig = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, BusinessType.ENTRUST);
		if(acfinancialContractConfig != null){
			PaymentStrategyMode accreditPaymentChannelMode = acfinancialContractConfig.getCreditPaymentChannelMode();
			resultDataMap.put("accreditPaymentChannelMode", accreditPaymentChannelMode==null ? "" : accreditPaymentChannelMode.getChineseMessage());
			resultDataMap.put("accreditPaymentChannelModeOrdinal", accreditPaymentChannelMode==null ? -1 : accreditPaymentChannelMode.ordinal());
			
			PaymentStrategyMode acdebitPaymentChannelMode = acfinancialContractConfig.getDebitPaymentChannelMode();
			resultDataMap.put("acdebitPaymentChannelMode", acdebitPaymentChannelMode == null ? "" : acdebitPaymentChannelMode.getChineseMessage());
			resultDataMap.put("acdebitPaymentChannelModeOrdinal", acdebitPaymentChannelMode == null ? -1 : acdebitPaymentChannelMode.ordinal());
			
			resultDataMap.put("accreditTransaction", paymentChannelInformationService.getPaymentChannelCreditDetailForSwitchDetail(acfinancialContractConfig.getPaymentChannelRouterForCredit()));
			resultDataMap.put("acdebitTransaction", paymentChannelInformationService.getPaymentChannelDebitDetailForSwitchDetail(acfinancialContractConfig.getPaymentChannelRouterForDebit()));
			
		}
		return resultDataMap;
	}
	
	@Override
	public List<Map<String, Object>> getDebitStrategyList(String financialContractUuid, int businessType) {
		FinancialContractConfig fcc = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, BusinessType.fromValue(businessType));
		if(fcc == null){
			return null;
		}
		List<String> paymentChannelUuidList = fcc.getPaymentChannelUuidListForDebit();
		return paymentChannelInformationService.getDebitStrategyList(paymentChannelUuidList);
	}
	
	@Override
	public List<Map<String, Object>> getCreditStrategyList(String financialContractUuid, int businessType) {
		FinancialContractConfig fc = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, BusinessType.fromValue(businessType));
		if(fc == null){
			return null;
		}
		List<String> paymentChannelUuidList = fc.getPaymentChannelUuidListForCredit();
		return paymentChannelInformationService.getCreditStrategyList(paymentChannelUuidList);
	}
	
	@Override
	public Map<String, Object> getActiveCreditAndDebitStrategy(String financialContractUuid, BusinessType businessType){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		FinancialContractConfig fcc = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, businessType);
		if(fcc == null){
			return rtnMap;
		}
		// Credit
		String creditPaymentChannelUuid = fcc.getPaymentChannelRouterForCredit();
		Map<String, Object> creditStrategyData = new HashMap<String, Object>();
		if( StringUtils.isNotEmpty(creditPaymentChannelUuid) ){
			List<String> creditPaymentChannelUuidList = new ArrayList<String>();
			creditPaymentChannelUuidList.add(creditPaymentChannelUuid);
			List<Map<String, Object>> creditStrategyList = paymentChannelInformationService.getCreditStrategyList(creditPaymentChannelUuidList);
			if(CollectionUtils.isNotEmpty(creditStrategyList)){
				creditStrategyData = creditStrategyList.get(0);
			}
		}
		
		// Debit
		String debitPaymentChannelUuid = fcc.getPaymentChannelRouterForDebit();
		Map<String, Object> debitStrategyData = new HashMap<String, Object>();
		if( StringUtils.isNotEmpty(debitPaymentChannelUuid) ){
			List<String> debitPaymentChannelUuidList = new ArrayList<String>();
			debitPaymentChannelUuidList.add(debitPaymentChannelUuid);
			List<Map<String, Object>> debitStrategyList = paymentChannelInformationService.getDebitStrategyList(debitPaymentChannelUuidList);
			if(CollectionUtils.isNotEmpty(debitStrategyList)){
				debitStrategyData = debitStrategyList.get(0);
			}
		}
		
		if(businessType == BusinessType.SELF){ //自有
			rtnMap.put("creditStrategyData", creditStrategyData);
			rtnMap.put("debitStrategyData", debitStrategyData);
			rtnMap.put("creditPaymentChannelMode", fcc.getCreditPaymentChannelMode()==null?null:fcc.getCreditPaymentChannelMode().ordinal());
			rtnMap.put("debitPaymentChannelMode", fcc.getDebitPaymentChannelMode()==null?null:fcc.getDebitPaymentChannelMode().ordinal());
		}else if(businessType == BusinessType.ENTRUST){ //委托
			rtnMap.put("accreditStrategyData", creditStrategyData);
			rtnMap.put("acdebitStrategyData", debitStrategyData);
			rtnMap.put("accreditPaymentChannelMode", fcc.getCreditPaymentChannelMode()==null?null:fcc.getCreditPaymentChannelMode().ordinal());
			rtnMap.put("acdebitPaymentChannelMode", fcc.getDebitPaymentChannelMode()==null?null:fcc.getDebitPaymentChannelMode().ordinal());
		}
		
		return rtnMap;
	}

	@Override
	public Map<String, Object> getSwitchDetailInfo(String financialContractUuid) {
		
		Map<String, Object> resultDataMap = new HashMap<String, Object>();
		
		// 自有
		FinancialContractConfig financialContractConfig = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, BusinessType.SELF);
		if (financialContractConfig != null) {
			PaymentStrategyMode creditPaymentChannelMode = financialContractConfig.getCreditPaymentChannelMode();
			resultDataMap.put("creditPaymentChannelMode",creditPaymentChannelMode);
			resultDataMap.put("creditPaymentChannelData", genData(AccountSide.CREDIT, financialContractConfig));
			
			PaymentStrategyMode debitPaymentChannelMode = financialContractConfig.getDebitPaymentChannelMode();
			resultDataMap.put("debitPaymentChannelMode",debitPaymentChannelMode);
			resultDataMap.put("debitPaymentChannelModeData",genData(AccountSide.DEBIT, financialContractConfig));
		}

		// 委托
		FinancialContractConfig acFinancialContractConfig = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid,BusinessType.ENTRUST);
		if (acFinancialContractConfig != null) {
			PaymentStrategyMode acCreditPaymentChannelMode = acFinancialContractConfig.getCreditPaymentChannelMode();
			resultDataMap.put("acCreditPaymentChannelMode", acCreditPaymentChannelMode);
			resultDataMap.put("acCreditPaymentChannelModeData", genData(AccountSide.CREDIT, acFinancialContractConfig));

			PaymentStrategyMode acDebitPaymentChannelMode = acFinancialContractConfig.getDebitPaymentChannelMode();
			resultDataMap.put("acDebitPaymentChannelMode", acDebitPaymentChannelMode);
			resultDataMap.put("acDebitPaymentChannelModeData", genData(AccountSide.DEBIT, acFinancialContractConfig));
		}
		return resultDataMap;
	}
	
	
	public List<Map<String, Object>> genData(AccountSide accountSide, FinancialContractConfig financialContractConfig){
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		if(accountSide == AccountSide.CREDIT){
			if(financialContractConfig.getCreditPaymentChannelMode() == PaymentStrategyMode.SINGLECHANNELMODE){// 单一通道模式
				String paymentChannelUuid = financialContractConfig.getPaymentChannelRouterForCredit();
				PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
				if(paymentChannelInformation == null){
					return data;
				}
				data.add(paymentChannelInformationService.extractBriefCreditInfo(paymentChannelInformation));
			}else if(financialContractConfig.getCreditPaymentChannelMode() == PaymentStrategyMode.ISSUINGBANKFIRST){// 发卡行优先
				List<String> paymentChannelUuids = financialContractConfig.getPaymentChannelUuidListForCredit();
				for (String paymentChannelUuid : paymentChannelUuids) {
					PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
					if(paymentChannelInformation == null){
						continue;
					}
					data.add(paymentChannelInformationService.extractBriefCreditInfo(paymentChannelInformation));
				}
			}
		}else if(accountSide == AccountSide.DEBIT){
			if(financialContractConfig.getDebitPaymentChannelMode() == PaymentStrategyMode.SINGLECHANNELMODE){// 单一通道模式
				String paymentChannelUuid = financialContractConfig.getPaymentChannelRouterForDebit();
				PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
				if(paymentChannelInformation == null){
					return data;
				}
				data.add(paymentChannelInformationService.extractBriefDebitInfo(paymentChannelInformation));
			}else if(financialContractConfig.getDebitPaymentChannelMode() == PaymentStrategyMode.ISSUINGBANKFIRST){// 发卡行优先
				List<String> paymentChannelUuids = financialContractConfig.getPaymentChannelUuidListForDebit();
				for (String paymentChannelUuid : paymentChannelUuids) {
					PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
					if(paymentChannelInformation == null){
						continue;
					}
					data.add(paymentChannelInformationService.extractBriefDebitInfo(paymentChannelInformation));
				}
			}
		}
		
		return data;
	}

	@Override
	public List<Map<String, Object>> extractAvailableBriefPaymentChannelInfo(AccountSide accountSide,FinancialContractConfig financialContractConfig) {
		if(accountSide == null || financialContractConfig == null){
			return Collections.emptyList();
		}
		List<Map<String, Object>> data = new ArrayList<>();
		List<String> paymentChannelUuids = null;
		
		if(accountSide == AccountSide.CREDIT){
			paymentChannelUuids = financialContractConfig.getPaymentChannelUuidListForCredit();
		}else if(accountSide == AccountSide.DEBIT){
			paymentChannelUuids = financialContractConfig.getPaymentChannelUuidListForDebit();
		}
		if(CollectionUtils.isEmpty(paymentChannelUuids)){
			return Collections.emptyList();
		}
		for (String paymentChannelUuid : paymentChannelUuids) {
			PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
			if(paymentChannelInformation == null){
				continue;
			}
			if(accountSide == AccountSide.CREDIT){
				data.add(paymentChannelInformationService.extractBriefCreditInfo(paymentChannelInformation));
			}else if(accountSide == AccountSide.DEBIT){
				data.add(paymentChannelInformationService.extractBriefDebitInfo(paymentChannelInformation));
			}
		}
		return data;
	}

	@Override
	public List<Map<String, Object>> previewPaymentChannelOrderForBanks(String financialContractUuid, BusinessType businessType, AccountSide accountSide) {
		if(StringUtils.isBlank(financialContractUuid) || businessType == null){
			return Collections.emptyList();
		}
		FinancialContractConfig fcc = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, businessType);
		if(fcc == null){
			return Collections.emptyList();
		}
		Map<String, String> bankPaymentChannel = null;
		List<String> paymentChannelUuids = null;
		
		if(accountSide == AccountSide.CREDIT){
			bankPaymentChannel = fcc.getPaymentChannelConfigMapForCredit();
			paymentChannelUuids = fcc.getPaymentChannelUuidListForCredit();
		}else if(accountSide == AccountSide.DEBIT){
			bankPaymentChannel = fcc.getPaymentChannelConfigMapForDebit();
			paymentChannelUuids = fcc.getPaymentChannelUuidListForDebit();
		}
		if(bankPaymentChannel == null){
			return Collections.emptyList();
		}
		List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
		for(Map.Entry<String, String> mapEntry : bankPaymentChannel.entrySet()){
			Map<String, Object> partyMap = new HashMap<String, Object>();
			partyMap.put("bankCode", mapEntry.getKey());
			partyMap.put("bankName", BankCoreCodeMapSpec.coreBankMap.get(mapEntry.getKey()));
			partyMap.put("paymentChannelList", genPaymentChannelList(mapEntry.getValue(), paymentChannelUuids));
			rtnList.add(partyMap);
		}
		return rtnList;
	}
	
	private List<Map<String, String>> genPaymentChannelList(String paymentChannelUuid, List<String> paymentChannelUuids){
		
		List<Map<String, String>> rtnList = new ArrayList<Map<String,String>>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		PaymentChannelInformation pci = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		tmpMap.put("paymentChannelUuid", pci.getPaymentChannelUuid());
		tmpMap.put("paymentChannelName", pci.getPaymentChannelName());
		rtnList.add(tmpMap);
		for (String paymentChannelUuidOrder : paymentChannelUuids) {
			if(paymentChannelUuidOrder.equals(paymentChannelUuid)){
				continue;
			}
			Map<String, String> tmptMap = new HashMap<String, String>();
			pci = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
			tmptMap.put("paymentChannelUuid", pci.getPaymentChannelUuid());
			tmptMap.put("paymentChannelName", pci.getPaymentChannelName());
			rtnList.add(tmptMap);
		}
		return rtnList;
	}

	@Override
	public boolean updateConfig(StrategySwitchResultSubmitModel submitModel) {
		if(!submitModel.isValidParameters()){
			return false;
		}
		FinancialContractConfig fcc = financialContractConfigService.getFinancialContractConfigBy(submitModel.getFinancialContractUuid(),submitModel.getBusinessTypeEnum());
		if(fcc == null){
			return false;
		}
		
		PaymentStrategyMode mode = submitModel.getPaymentStrategyModeEnum();
		if(submitModel.getAccountSideEnum() == AccountSide.CREDIT){
			fcc.setCreditPaymentChannelMode(mode);
			if(mode == PaymentStrategyMode.SINGLECHANNELMODE){
				fcc.setPaymentChannelRouterForCredit(submitModel.getPaymentChannelUuidList().get(0));
			}else if(mode == PaymentStrategyMode.ISSUINGBANKFIRST){
				fcc.setPaymentChannelUuidsForCredit(submitModel.getPaymentChannelUuids());
				fcc.setPaymentChannelConfigForCredit(JSON.toJSONString(submitModel.getPaymentChannelOrderForBanks(), SerializerFeature.DisableCircularReferenceDetect));
			}
		}else if(submitModel.getAccountSideEnum() == AccountSide.DEBIT){
			fcc.setDebitPaymentChannelMode(mode);
			if(mode == PaymentStrategyMode.SINGLECHANNELMODE){
				fcc.setPaymentChannelRouterForDebit(submitModel.getPaymentChannelUuidList().get(0));
			}else if(mode == PaymentStrategyMode.ISSUINGBANKFIRST){
				fcc.setPaymentChannelUuidsForDebit(submitModel.getPaymentChannelUuids());
				fcc.setPaymentChannelConfigForDebit(JSON.toJSONString(submitModel.getPaymentChannelOrderForBanks(), SerializerFeature.DisableCircularReferenceDetect));
			}
		}
		return true;
	}
	
}
