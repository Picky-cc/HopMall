package com.zufangbao.sun.yunxin.entity.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.PaymentStrategyMode;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

/**
 * @author reluxer
 */
public class StrategySwitchResultSubmitModel {

	/**
	 * 信托合同Uuid
	 */
	private String financialContractUuid;
	
	private int businessType=-1;
	
	private int accountSide=-1;
	
	private int paymentStrategyMode=-1;
	
	private String paymentChannelUuid;
	
	private String paymentChannelUuids;
	
	private Map<String, String> paymentChannelOrderForBanks;

//	private Map<String, List<String>> paymentChannelOrderForBanks;

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	
	public BusinessType getBusinessTypeEnum(){
		return EnumUtil.fromOrdinal(BusinessType.class, this.businessType);
	}

	public int getAccountSide() {
		return accountSide;
	}
	
	public AccountSide getAccountSideEnum() {
		return EnumUtil.fromOrdinal(AccountSide.class, this.accountSide);
	}

	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}
	
	public int getPaymentStrategyMode() {
		return paymentStrategyMode;
	}
	
	public PaymentStrategyMode getPaymentStrategyModeEnum() {
		return EnumUtil.fromOrdinal(PaymentStrategyMode.class, this.paymentStrategyMode);
	}

	public void setPaymentStrategyMode(int paymentStrategyMode) {
		this.paymentStrategyMode = paymentStrategyMode;
	}

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}

	public String getPaymentChannelUuids() {
		return paymentChannelUuids;
	}
	
	public List<String> getPaymentChannelUuidList(){
		return JSON.parseArray(this.paymentChannelUuids, String.class);
	}

	public void setPaymentChannelUuids(String paymentChannelUuids) {
		this.paymentChannelUuids = paymentChannelUuids;
	}

	public Map<String, String> getPaymentChannelOrderForBanks() {
		return paymentChannelOrderForBanks;
	}

	public void setPaymentChannelOrderForBanks(Map<String, String> paymentChannelOrderForBanks) {
		this.paymentChannelOrderForBanks = paymentChannelOrderForBanks;
	}

	public StrategySwitchResultSubmitModel() {
		super();
	}
	
	public boolean isValidParameters(){
		if(StringUtils.isBlank(financialContractUuid)){
			return false;
		}
		if(this.getBusinessTypeEnum() == null){
			return false;
		}
		if(this.getAccountSideEnum() == null){
			return false;
		}
		List<String> uuidList = this.getPaymentChannelUuidList();
		if(CollectionUtils.isEmpty(uuidList)){
			return false;
		}
		if(this.getPaymentStrategyModeEnum() == PaymentStrategyMode.SINGLECHANNELMODE){ // 单一通道模式
			if(uuidList.size() != 1){
				return false;
			}
		}else if(this.getPaymentStrategyModeEnum() == PaymentStrategyMode.ISSUINGBANKFIRST){ // 发卡行优先模式
			if(MapUtils.isEmpty(this.getPaymentChannelOrderForBanks())){
				return false;
			}
		}else if(this.getPaymentStrategyModeEnum() == null){
			return false;
		}
		return true;
	}
	
}
