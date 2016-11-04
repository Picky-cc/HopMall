package com.zufangbao.sun.entity.financial;

import java.math.BigDecimal;
import java.util.Map;

public class TransactionChannelConfigure {
	/**
	 * 通道状态
	 */
	private ChannelWorkingStatus channelStatus;
	
	/**
	 * 费用模式
	 */
	private ChargeExcutionMode chargeExcutionMode;
	
	/**
	 * 交易类型
	 */
	private ChargeType chargeType;
	
	/**
	 * 通道扣率方式
	 */
	private ChargeRateMode chargeRateMode;
	
	/**
	 * 单笔费用
	 */
	private BigDecimal  chargePerTranscation;
	/**
	 * 单笔扣率
	 */
	private BigDecimal  chargeRatePerTranscation;
	/**
	 * 单笔最高收取费用
	 */
	private BigDecimal  upperChargeLimitPerTransaction;
	/**
	 * 单笔最低收取费用
	 */
	private BigDecimal  lowerestChargeLimitPerTransaction;
	/**
	 * 通道单笔限额
	 */
	private BigDecimal  trasncationLimitPerTransaction;
	/**
	 * 清算周期
	 */
	private int clearingInterval;
	
	/**
	 * 银行限额列表文件路径
	 */
	private String bankLimitationFileKey;
	
	private String bankLimitationFileName;
	
	/**
	 * 限额列表文件内容
	 */
	private Map<String,BankTransactionConfigure> bankLimitations;

	public ChannelWorkingStatus getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(ChannelWorkingStatus channelStatus) {
		this.channelStatus = channelStatus;
	}

	public ChargeExcutionMode getChargeExcutionMode() {
		return chargeExcutionMode;
	}

	public void setChargeExcutionMode(ChargeExcutionMode chargeExcutionMode) {
		this.chargeExcutionMode = chargeExcutionMode;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getChargePerTranscation() {
		return chargePerTranscation;
	}

	public void setChargePerTranscation(BigDecimal chargePerTranscation) {
		this.chargePerTranscation = chargePerTranscation;
	}

	public BigDecimal getChargeRatePerTranscation() {
		return chargeRatePerTranscation;
	}

	public void setChargeRatePerTranscation(BigDecimal chargeRatePerTranscation) {
		this.chargeRatePerTranscation = chargeRatePerTranscation;
	}

	public BigDecimal getUpperChargeLimitPerTransaction() {
		return upperChargeLimitPerTransaction;
	}

	public void setUpperChargeLimitPerTransaction(
			BigDecimal upperChargeLimitPerTransaction) {
		this.upperChargeLimitPerTransaction = upperChargeLimitPerTransaction;
	}

	public BigDecimal getLowerestChargeLimitPerTransaction() {
		return lowerestChargeLimitPerTransaction;
	}

	public void setLowerestChargeLimitPerTransaction(
			BigDecimal lowerestChargeLimitPerTransaction) {
		this.lowerestChargeLimitPerTransaction = lowerestChargeLimitPerTransaction;
	}

	public BigDecimal getTrasncationLimitPerTransaction() {
		return trasncationLimitPerTransaction;
	}

	public void setTrasncationLimitPerTransaction(
			BigDecimal trasncationLimitPerTransaction) {
		this.trasncationLimitPerTransaction = trasncationLimitPerTransaction;
	}

	public int getClearingInterval() {
		return clearingInterval;
	}

	public void setClearingInterval(int clearingInterval) {
		this.clearingInterval = clearingInterval;
	}

	public Map<String, BankTransactionConfigure> getBankLimitations() {
		return bankLimitations;
	}

	public void setBankLimitations(
			Map<String, BankTransactionConfigure> bankLimitations) {
		this.bankLimitations = bankLimitations;
	}

	public TransactionChannelConfigure() {
		super();
	}

	public ChargeRateMode getChargeRateMode() {
		return chargeRateMode;
	}

	public void setChargeRateMode(ChargeRateMode chargeRateMode) {
		this.chargeRateMode = chargeRateMode;
	}

	public String getBankLimitationFileName() {
		return bankLimitationFileName;
	}

	public void setBankLimitationFileName(String bankLimitationFileName) {
		this.bankLimitationFileName = bankLimitationFileName;
	}
	
	public String getBankLimitationFileKey() {
		return bankLimitationFileKey;
	}

	public void setBankLimitationFileKey(String bankLimitationFileKey) {
		this.bankLimitationFileKey = bankLimitationFileKey;
	}
	
	public boolean isValidBigDecimal(BigDecimal bigDecimal){
		return bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public boolean isValid(){
		if(chargeRateMode == ChargeRateMode.SINGLEFIXED){
//			if(!isValidBigDecimal(chargePerTranscation)) {
//				return false;
//			}
		}else if(chargeRateMode == ChargeRateMode.SINGLERATA){
			if(!isValidBigDecimal(chargeRatePerTranscation)){
				return false;
			}
			if(upperChargeLimitPerTransaction!= null && lowerestChargeLimitPerTransaction!=null){
				return upperChargeLimitPerTransaction.compareTo(lowerestChargeLimitPerTransaction) >= 0;
			}
		}
		if(clearingInterval<0){
			return false;
		}
		return true;
	}

}
