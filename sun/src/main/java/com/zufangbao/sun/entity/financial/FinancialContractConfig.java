package com.zufangbao.sun.entity.financial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.utils.JsonUtils;


@Entity
@Table(name = "financial_contract_config")
public class FinancialContractConfig {
	
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 信托合同Uuid
	 */
	private String financialContractUuid;
	
	/**
	 * 业务类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private BusinessType businessType;// 0：自有   1：委托
	
	/**
	 * 付/放款 通道列表
	 */
	private String paymentChannelUuidsForCredit;
	
	/**
	 * 收款通道列表
	 */
	private String paymentChannelUuidsForDebit;
	
	/**
	 * 付款通道模式
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentStrategyMode creditPaymentChannelMode;
	
	/**
	 * 收款通道模式
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentStrategyMode debitPaymentChannelMode;
	
	/**
	 * 付款通道配置信息
	 */
	private String paymentChannelRouterForCredit;
	
	/**
	 * 收款通道配置信息
	 */
	private String paymentChannelRouterForDebit;
	
	/**
	 * 付款通道配置信息
	 */
	private String paymentChannelConfigForCredit;
	
	/**
	 * 收款通道配置信息
	 */
	private String paymentChannelConfigForDebit;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getPaymentChannelUuidsForCredit() {
		return paymentChannelUuidsForCredit;
	}
	
	public String getPaymentChannelConfigForCredit() {
		return paymentChannelConfigForCredit;
	}
	
	public Map<String, String> getPaymentChannelConfigMapForCredit() {
		return JSON.parseObject(this.paymentChannelConfigForCredit, new TypeReference<Map<String,String>>(){});  
	}

	public void setPaymentChannelConfigForCredit(
			String paymentChannelConfigForCredit) {
		this.paymentChannelConfigForCredit = paymentChannelConfigForCredit;
	}

	public String getPaymentChannelConfigForDebit() {
		return paymentChannelConfigForDebit;
	}
	
	public Map<String, String> getPaymentChannelConfigMapForDebit() {
		return JSON.parseObject(this.paymentChannelConfigForDebit, new TypeReference<Map<String,String>>(){});  
	}

	public void setPaymentChannelConfigForDebit(String paymentChannelConfigForDebit) {
		this.paymentChannelConfigForDebit = paymentChannelConfigForDebit;
	}

	public void addPaymentChannelUuidsForCredit(String paymentChannelUuid){
		List<String> list = JsonUtils.parseArray(this.paymentChannelUuidsForCredit,String.class);
		if(list == null){
			list = new ArrayList<String>();
		}
		list.add(paymentChannelUuid);
		this.paymentChannelUuidsForCredit = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
	}

	public void setPaymentChannelUuidsForCredit(String paymentChannelUuidsForCredit) {
		this.paymentChannelUuidsForCredit = paymentChannelUuidsForCredit;
	}

	public String getPaymentChannelUuidsForDebit() {
		return paymentChannelUuidsForDebit;
	}
	
	public void addPaymentChannelUuidsForDebit(String paymentChannelUuid){
		List<String> list = JsonUtils.parseArray(this.paymentChannelUuidsForDebit,String.class);
		if(list == null){
			list = new ArrayList<String>();
		}
		list.add(paymentChannelUuid);
		this.paymentChannelUuidsForDebit = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
	}

	public void setPaymentChannelUuidsForDebit(String paymentChannelUuidsForDebit) {
		this.paymentChannelUuidsForDebit = paymentChannelUuidsForDebit;
	}

	public PaymentStrategyMode getCreditPaymentChannelMode() {
		return creditPaymentChannelMode;
	}

	public void setCreditPaymentChannelMode(
			PaymentStrategyMode creditPaymentChannelMode) {
		this.creditPaymentChannelMode = creditPaymentChannelMode;
	}

	public PaymentStrategyMode getDebitPaymentChannelMode() {
		return debitPaymentChannelMode;
	}

	public void setDebitPaymentChannelMode(
			PaymentStrategyMode debitPaymentChannelMode) {
		this.debitPaymentChannelMode = debitPaymentChannelMode;
	}

	public String getPaymentChannelRouterForCredit() {
		return paymentChannelRouterForCredit;
	}

	public void setPaymentChannelRouterForCredit(
			String paymentChannelRouterForCredit) {
		this.paymentChannelRouterForCredit = paymentChannelRouterForCredit;
	}

	public String getPaymentChannelRouterForDebit() {
		return paymentChannelRouterForDebit;
	}

	public void setPaymentChannelRouterForDebit(String paymentChannelRouterForDebit) {
		this.paymentChannelRouterForDebit = paymentChannelRouterForDebit;
	}
	
	public List<String> getPaymentChannelUuidListForCredit(){
		return JsonUtils.parseArray(this.paymentChannelUuidsForCredit,String.class);
		
	}
	
	public List<String> getPaymentChannelUuidListForDebit(){
		return JsonUtils.parseArray(this.paymentChannelUuidsForDebit,String.class);
//		return Arrays.asList(this.paymentChannelUuidsForDebit.split(","));
	}

	public FinancialContractConfig() {
		super();
	}
	
	public FinancialContractConfig(PaymentChannelInformation paymentChannelInformation){
		super();
		String financialContractUuid =paymentChannelInformation.getRelatedFinancialContractUuid();
		String paymentChannelUuid = paymentChannelInformation.getPaymentChannelUuid();
		if(StringUtils.isEmpty(financialContractUuid) || StringUtils.isEmpty(paymentChannelUuid)){
			return;
		}
		this.setBusinessType(paymentChannelInformation.getBusinessType());
		this.setFinancialContractUuid(financialContractUuid);
		if(paymentChannelInformation.getCreditChannelWorkingStatus() != ChannelWorkingStatus.NOTLINK){
			this.addPaymentChannelUuidsForCredit(paymentChannelUuid);
		}
		if(paymentChannelInformation.getDebitChannelWorkingStatus() != ChannelWorkingStatus.NOTLINK){
			this.addPaymentChannelUuidsForDebit(paymentChannelUuid);
		}
	}

	public FinancialContractConfig(String financialContractUuid,
			BusinessType businessType, String paymentChannelUuidsForCredit,
			String paymentChannelUuidsForDebit,
			PaymentStrategyMode creditPaymentChannelMode,
			PaymentStrategyMode debitPaymentChannelMode,
			String paymentChannelRouterForCredit,
			String paymentChannelRouterForDebit,
			String paymentChannelConfigForCredit,
			String paymentChannelConfigForDebit) {
		super();
		this.financialContractUuid = financialContractUuid;
		this.businessType = businessType;
		this.paymentChannelUuidsForCredit = paymentChannelUuidsForCredit;
		this.paymentChannelUuidsForDebit = paymentChannelUuidsForDebit;
		this.creditPaymentChannelMode = creditPaymentChannelMode;
		this.debitPaymentChannelMode = debitPaymentChannelMode;
		this.paymentChannelRouterForCredit = paymentChannelRouterForCredit;
		this.paymentChannelRouterForDebit = paymentChannelRouterForDebit;
		this.paymentChannelConfigForCredit = paymentChannelConfigForCredit;
		this.paymentChannelConfigForDebit = paymentChannelConfigForDebit;
	}
}
