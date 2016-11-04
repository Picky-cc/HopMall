package com.zufangbao.sun.yunxin.entity.model;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.financial.PaymentStrategyMode;
import com.zufangbao.sun.utils.EnumUtil;


public class StrategyResultSaveModel {

	/**
	 * 通道Uuid
	 */
	private String financialContractUuid;
	
	/**
	 * 自有付款策略
	 */
	private int creditStrategyMode=-1;
	
	/**
	 * 自有收款策略
	 */
	private int debitStrategyMode=-1;
	
	/**
	 * 自有收款通道Uuid
	 */
	private String creditChannelUuid;
	
	/**
	 * 自有付款通道Uuid
	 */
	private String debitChannelUuid;
	
	/**
	 * 委托付款策略
	 */
	private int accreditStrategyMode=-1;
	
	/**
	 * 委托收款策略
	 */
	private int acdebitStrategyMode=-1;
	
	/**
	 * 委托收款通道Uuid
	 */
	private String accreditChannelUuid;
	
	/**
	 * 委托付款通道Uuid
	 */
	private String acdebitChannelUuid;

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public int getCreditStrategyMode() {
		return creditStrategyMode;
	}
	
	public PaymentStrategyMode getCreditStrategyModeEnum() {
		return EnumUtil.fromOrdinal(PaymentStrategyMode.class, this.creditStrategyMode);
	}

	public void setCreditStrategyMode(int creditStrategyMode) {
		this.creditStrategyMode = creditStrategyMode;
	}

	public int getDebitStrategyMode() {
		return debitStrategyMode;
	}
	
	public PaymentStrategyMode getDebitStrategyModeEnum() {
		return EnumUtil.fromOrdinal(PaymentStrategyMode.class, this.debitStrategyMode);
	}

	public void setDebitStrategyMode(int debitStrategyMode) {
		this.debitStrategyMode = debitStrategyMode;
	}

	public String getCreditChannelUuid() {
		return creditChannelUuid;
	}

	public void setCreditChannelUuid(String creditChannelUuid) {
		this.creditChannelUuid = creditChannelUuid;
	}

	public String getDebitChannelUuid() {
		return debitChannelUuid;
	}

	public void setDebitChannelUuid(String debitChannelUuid) {
		this.debitChannelUuid = debitChannelUuid;
	}

	public int getAccreditStrategyMode() {
		return accreditStrategyMode;
	}
	
	public PaymentStrategyMode getAccreditStrategyModeEnum() {
		return EnumUtil.fromOrdinal(PaymentStrategyMode.class, this.accreditStrategyMode);
	}

	public void setAccreditStrategyMode(int accreditStrategyMode) {
		this.accreditStrategyMode = accreditStrategyMode;
	}

	public int getAcdebitStrategyMode() {
		return acdebitStrategyMode;
	}
	
	public PaymentStrategyMode getAcdebitStrategyModeEnum() {
		return EnumUtil.fromOrdinal(PaymentStrategyMode.class, this.acdebitStrategyMode);
	}

	public void setAcdebitStrategyMode(int acdebitStrategyMode) {
		this.acdebitStrategyMode = acdebitStrategyMode;
	}

	public String getAccreditChannelUuid() {
		return accreditChannelUuid;
	}

	public void setAccreditChannelUuid(String accreditChannelUuid) {
		this.accreditChannelUuid = accreditChannelUuid;
	}

	public String getAcdebitChannelUuid() {
		return acdebitChannelUuid;
	}

	public void setAcdebitChannelUuid(String acdebitChannelUuid) {
		this.acdebitChannelUuid = acdebitChannelUuid;
	}

	public StrategyResultSaveModel() {
		super();
	}
	
	public boolean isValid(){
		return StringUtils.isNotBlank(this.financialContractUuid);
	}

	public StrategyResultSaveModel(String financialContractUuid,
			int creditStrategyMode, int debitStrategyMode,
			String creditChannelUuid, String debitChannelUuid,
			int accreditStrategyMode, int acdebitStrategyMode,
			String accreditChannelUuid, String acdebitChannelUuid) {
		super();
		this.financialContractUuid = financialContractUuid;
		this.creditStrategyMode = creditStrategyMode;
		this.debitStrategyMode = debitStrategyMode;
		this.creditChannelUuid = creditChannelUuid;
		this.debitChannelUuid = debitChannelUuid;
		this.accreditStrategyMode = accreditStrategyMode;
		this.acdebitStrategyMode = acdebitStrategyMode;
		this.accreditChannelUuid = accreditChannelUuid;
		this.acdebitChannelUuid = acdebitChannelUuid;
	}
	
}
