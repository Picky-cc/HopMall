package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.ChargeRateMode;
import com.zufangbao.sun.entity.financial.ChargeType;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.financial.TransactionChannelConfigure;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelQueryModel;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel;


@Service("paymentChannelInformationService")
public class PaymentChannelInformationServiceImpl extends GenericServiceImpl<PaymentChannelInformation>
	implements PaymentChannelInformationService {
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private FinancialContractConfigService financialContractConfigService;

	@Override
	public void updateChannelData(PaymentChannelInformation paymentChannelInformation) {
		this.update(paymentChannelInformation);
	}

	@Override
	public List<PaymentChannelInformation> query(PaymentChannelQueryModel queryModel, Page page) {
		StringBuffer querySb = new StringBuffer("SELECT pci FROM PaymentChannelInformation pci, FinancialContract fc where pci.relatedFinancialContractUuid = fc.financialContractUuid");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(queryModel, querySb, parameters);
		if(page == null){
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		}else{
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	@Override
	public int queryCount(PaymentChannelQueryModel queryModel) {
		StringBuffer querySb = new StringBuffer("SELECT pci FROM  PaymentChannelInformation pci, FinancialContract fc where pci.relatedFinancialContractUuid = fc.financialContractUuid");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(queryModel, querySb, parameters);
		return this.genericDaoSupport.count(querySb.toString(), parameters);
	}
	
	private void genQuerySentence(PaymentChannelQueryModel queryModel, StringBuffer querySb, Map<String, Object> parameters){
		
		PaymentInstitutionName paymentInstitutionName = queryModel.getGatewayEnum();
		if(queryModel.isValidParameter(paymentInstitutionName)){
			querySb.append( " AND pci.paymentInstitutionName=:paymentInstitutionName");
			parameters.put("paymentInstitutionName", paymentInstitutionName);
		}
		
		ChannelWorkingStatus creditChannelStatus = queryModel.getCreditStatusEnum();
		if(queryModel.isValidParameter(creditChannelStatus)){
			querySb.append( " AND pci.creditChannelWorkingStatus=:creditChannelStatus");
			parameters.put("creditChannelStatus", creditChannelStatus);
		}
		
		ChannelWorkingStatus debitChannelStatus = queryModel.getDebitStatusEnum();
		if(queryModel.isValidParameter(debitChannelStatus)){
			querySb.append( " AND pci.debitChannelWorkingStatus=:debitChannelStatus");
			parameters.put("debitChannelStatus", debitChannelStatus);
		}
		
		BusinessType businessTypeEnum = queryModel.getBusinessTypeEnum();
		if(queryModel.isValidParameter(businessTypeEnum)){
			querySb.append( " AND pci.businessType=:businessTypeEnum");
			parameters.put("businessTypeEnum", businessTypeEnum);
		}
		
		String keyWord = queryModel.getKeyWord();
		if(queryModel.isValidParameter(keyWord)){
			querySb.append( " AND (pci.outlierChannelName LIKE :keyWord OR fc.contractName LIKE :keyWord OR pci.paymentChannelName LIKE :keyWord)");
			parameters.put("keyWord", "%"+keyWord+"%");
		}
		
		querySb.append(" ORDER BY pci.createTime DESC");
	}

	@Override
	public List<PaymentChannelInformation> getPaymentChannelBy(String financialContractUuid) {
		Filter filter = new Filter();
		filter.addEquals("relatedFinancialContractUuid", financialContractUuid);
		return this.list(PaymentChannelInformation.class, filter);
	}

	@Override
	public PaymentChannelInformation getPaymentChannelInformationBy(String paymentChannelUuid) {
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("paymentChannelUuid", paymentChannelUuid);
		List<PaymentChannelInformation> paymentChannelInformations = this.list(PaymentChannelInformation.class, filter);
		if(CollectionUtils.isNotEmpty(paymentChannelInformations)){
			return paymentChannelInformations.get(0);
		}
		return null;
	}

	@Override
	public boolean isUnique(String paymentChannelName, String paymentChannelUuid) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paymentChannelUuid", paymentChannelUuid);
		String queryStr = " SELECT paymentChannelName FROM PaymentChannelInformation WHERE paymentChannelUuid !=:paymentChannelUuid";
		List<String> paymentChannelNames = this.genericDaoSupport.searchForList(queryStr, parameters);
		return CollectionUtils.isEmpty(paymentChannelNames)? true : (!paymentChannelNames.contains(paymentChannelName));
	}

	@Override
	public Map<String, Object> getPaymentChannelCreditDetailForSwitchDetail(String paymentChannelUuid) {
		Map<String, Object> creditDetail = new HashMap<>();
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return creditDetail;
		}
		PaymentChannelInformation paymentChannelInformation = getPaymentChannelInformationBy(paymentChannelUuid);
		PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();//单一通道模式
		String paymentChannelName  = paymentChannelInformation.getPaymentChannelName();
		TransactionChannelConfigure creditCofigure = channelConfigure.getCreditChannelConfigure();
		creditDetail.put("paymentChannelName", paymentChannelName);
		ChargeType chargeType = creditCofigure.getChargeType();
		creditDetail.put("chargeType", chargeType == null ? "" : chargeType.getChineseMessage());
		creditDetail.put("channelStatus", creditCofigure.getChannelStatus());
		return creditDetail;
	}

	@Override
	public PaymentChannelConfigure getPaymentChannelConfigureBy(String paymentChannelUuid) {
		Filter filter = new Filter();
		filter.addEquals("paymentChannelUuid", paymentChannelUuid);
		List<PaymentChannelInformation> paymentChannelInformations = this.list(PaymentChannelInformation.class, filter);
		if(CollectionUtils.isNotEmpty(paymentChannelInformations)){
			return JsonUtils.parse(paymentChannelInformations.get(0).getPaymentConfigureData(), PaymentChannelConfigure.class);
		}
		return null;
	}

	@Override
	public Map<String, Object> getPaymentChannelDebitDetailForSwitchDetail(String paymentChannelUuid) {
		Map<String, Object> debitDetail = new HashMap<>();
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return debitDetail;
		}
		PaymentChannelInformation paymentChannelInformation = getPaymentChannelInformationBy(paymentChannelUuid);
		PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();//单一通道模式
		String paymentChannelName  = paymentChannelInformation.getPaymentChannelName();
		TransactionChannelConfigure debitCofigure = channelConfigure.getDebitChannelConfigure();
		debitDetail.put("paymentChannelName", paymentChannelName);
		ChargeType chargeType = debitCofigure.getChargeType();
		debitDetail.put("chargeType", chargeType==null?"":chargeType.getChineseMessage());
		debitDetail.put("channelStatus", debitCofigure.getChannelStatus());
		return debitDetail;
	}

	@Override
	public List<Map<String, Object>> getDebitStrategyList(List<String> paymentChannelUuidList) {
		if(paymentChannelUuidList == null){
			return Collections.emptyList();
		}
		List<Map<String, Object>> debitStrategyList = new ArrayList<>();
		for (String paymentChannelUuid : paymentChannelUuidList) {
			if(StringUtils.isEmpty(paymentChannelUuid) || "null".equals(paymentChannelUuid)){
				continue;
			}
			PaymentChannelInformation paymentChannelInformation = getPaymentChannelInformationBy(paymentChannelUuid);
			PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();
			TransactionChannelConfigure debitConfigure = channelConfigure.getDebitChannelConfigure();
			if(debitConfigure.getChannelStatus()==null || debitConfigure.getChannelStatus()==ChannelWorkingStatus.NOTLINK){ // 未对接
				continue;
			}
			Map<String, Object> debitStrategy = new HashMap<>();
			debitStrategy.put("paymentChannelUuid", paymentChannelUuid);
			debitStrategy.put("paymentChannelName", paymentChannelInformation.getPaymentChannelName());
			genStrategyData(debitStrategy, debitConfigure);
			debitStrategyList.add(debitStrategy);
		}
		return debitStrategyList;
	}
	
	private void genStrategyData(Map<String, Object> strategy, TransactionChannelConfigure configure){
		ChargeType chargeType = configure.getChargeType();
		ChargeRateMode chargeRateMode = configure.getChargeRateMode();
		strategy.put("chargeType", chargeType == null? "" : chargeType.getChineseMessage());
		if(chargeRateMode == ChargeRateMode.SINGLEFIXED){
			String feeAmount = configure.getChargePerTranscation() == null ? "" : configure.getChargePerTranscation().toString();
			strategy.put("fee", feeAmount+"元/笔");
		}else if(chargeRateMode == ChargeRateMode.SINGLERATA ){
		    BigDecimal chargeRate = configure.getChargeRatePerTranscation();
		    strategy.put("fee",  chargeRate==null?"":chargeRate.toString()+"%");
		}else{
			strategy.put("fee", "0.00");
		}
		strategy.put("channelStatus", configure.getChannelStatus());
	}

	@Override
	public List<Map<String, Object>> getCreditStrategyList(List<String> paymentChannelUuidList) {
		List<Map<String, Object>> creditStrategyList = new ArrayList<>();
		for (String paymentChannelUuid : paymentChannelUuidList) {
			if(StringUtils.isEmpty(paymentChannelUuid) || "null".equals(paymentChannelUuid)){
				continue;
			}
			PaymentChannelInformation paymentChannelInformation = getPaymentChannelInformationBy(paymentChannelUuid);
			PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();
			TransactionChannelConfigure creditCofigure = channelConfigure.getCreditChannelConfigure();
			if(creditCofigure.getChannelStatus()==null || creditCofigure.getChannelStatus()==ChannelWorkingStatus.NOTLINK){ // 未对接
				continue;
			}
			Map<String, Object> creditStrategy = new HashMap<>();
			creditStrategy.put("paymentChannelUuid", paymentChannelUuid);
			creditStrategy.put("paymentChannelName", paymentChannelInformation.getPaymentChannelName());
			genStrategyData(creditStrategy, creditCofigure);
			creditStrategyList.add(creditStrategy);
		}
		return creditStrategyList;
	}

	@Override
	public List<TransferApplicationStatisticsModel> getTransferApplicationStatistics() {
		String queryString = "SELECT new com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel(paymentChannelUuid, paymentChannelName) FROM PaymentChannelInformation";
		return this.genericDaoSupport.searchForList(queryString);
	}

	@Override
	public Map<String, Object> extractBriefDebitInfo(PaymentChannelInformation paymentChannelInformation) {
		if(paymentChannelInformation == null || paymentChannelInformation.getDebitChannelWorkingStatus() == ChannelWorkingStatus.NOTLINK){
			return Collections.emptyMap();
		}
		Map<String, Object> debitInfo = new HashMap<>();
		debitInfo.put("paymentChannelUuid", paymentChannelInformation.getPaymentChannelUuid());
		debitInfo.put("paymentChannelName", paymentChannelInformation.getPaymentChannelName());
		PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();
		TransactionChannelConfigure debitConfigure = channelConfigure.getDebitChannelConfigure();
		genBriefInfo(debitInfo, debitConfigure);
		return debitInfo;
	}
	
	private void genBriefInfo(Map<String, Object> infoMap, TransactionChannelConfigure configure){
		ChannelWorkingStatus channelStatus = configure.getChannelStatus();
		infoMap.put("channelStatusMsg", channelStatus == null ? "" : channelStatus.getChineseMessage() );
		infoMap.put("channelStatus",channelStatus);
		if(configure.getChannelStatus()==null || configure.getChannelStatus()==ChannelWorkingStatus.NOTLINK){ // 未对接
			return ;
		}
		ChargeRateMode chargeRateMode = configure.getChargeRateMode();
		if(chargeRateMode == ChargeRateMode.SINGLEFIXED){
			String feeAmount = configure.getChargePerTranscation() == null ? "" : configure.getChargePerTranscation().toString();
			infoMap.put("fee", feeAmount+"元/笔");
		}else if(chargeRateMode == ChargeRateMode.SINGLERATA ){
		    BigDecimal chargeRate = configure.getChargeRatePerTranscation();
		    infoMap.put("fee",  chargeRate==null?"":chargeRate.toString()+"%");
		}else{
			infoMap.put("fee", "0.00");
		}
	}
	
	@Override
	public Map<String, Object> extractBriefCreditInfo(PaymentChannelInformation paymentChannelInformation) {
		if(paymentChannelInformation == null || paymentChannelInformation.getCreditChannelWorkingStatus() == ChannelWorkingStatus.NOTLINK){
			return Collections.emptyMap();
		}
		Map<String, Object> creditInfo = new HashMap<>();
		creditInfo.put("paymentChannelUuid", paymentChannelInformation.getPaymentChannelUuid());
		creditInfo.put("paymentChannelName", paymentChannelInformation.getPaymentChannelName());
		PaymentChannelConfigure channelConfigure = paymentChannelInformation.extractConfigData();
		TransactionChannelConfigure creditConfigure = channelConfigure.getCreditChannelConfigure();
		genBriefInfo(creditInfo, creditConfigure);
		return creditInfo;
	}

	@Override
	public List<String> getAllOutlierChannelNames() {
		String queryStr = "SELECT DISTINCT outlierChannelName FROM PaymentChannelInformation";
		return this.genericDaoSupport.searchForList(queryStr);
	}

	@Override
	public PaymentChannelInformation getPaymentChannelInformationBy(
			PaymentInstitutionName paymentInstitutionName,
			String outlierChannelName) {
		String queryStr = "FROM PaymentChannelInformation WHERE outlierChannelName = :outlierChannelName AND paymentInstitutionName =:paymentInstitutionName";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("outlierChannelName", outlierChannelName);
		param.put("paymentInstitutionName", paymentInstitutionName);
		List<PaymentChannelInformation> paymentChannelInformations = this.genericDaoSupport.searchForList(queryStr, param);
		if(CollectionUtils.isNotEmpty(paymentChannelInformations) && paymentChannelInformations.size() == 1){
			return paymentChannelInformations.get(0);
		}
		return null;
	}

}
