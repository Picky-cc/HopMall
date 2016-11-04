package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 银行限额信息
 * @author zhanghongbing
 *
 */
public class BankLimitAmountInfo {

	//银行编码
	private String bankCode;
	
	//银行名称
	private String bankName;
	
	//单笔限额
	private BigDecimal singleLimitAmount;
	
	//单日限额
	private BigDecimal oneDayLimitAmount;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getSingleLimitAmount() {
		return singleLimitAmount;
	}

	public void setSingleLimitAmount(BigDecimal singleLimitAmount) {
		this.singleLimitAmount = singleLimitAmount;
	}

	public BigDecimal getOneDayLimitAmount() {
		return oneDayLimitAmount;
	}
	
	public BigDecimal getOneDayLimitAmount(BigDecimal plannedAmount) {
		if(this.oneDayLimitAmount == null) {
			return plannedAmount;
		}
		return this.oneDayLimitAmount;
	}

	public void setOneDayLimitAmount(BigDecimal oneDayLimitAmount) {
		this.oneDayLimitAmount = oneDayLimitAmount;
	}
	
	public BankLimitAmountInfo() {
		super();
	}

	public BankLimitAmountInfo(String bankCode, String bankName,
			BigDecimal singleLimitAmount, BigDecimal oneDayLimitAmount) {
		super();
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.singleLimitAmount = singleLimitAmount;
		this.oneDayLimitAmount = oneDayLimitAmount;
	}
	
	/**
	 * 根据日限额，计算所需执行天数
	 * @param plannedAmount 计划金额
	 * @return 预计执行天数
	 */
	public int getExecutionDays(BigDecimal plannedAmount) {
		if(this.oneDayLimitAmount == null) {
			return 1;
		}
		return plannedAmount.divide(this.oneDayLimitAmount, RoundingMode.CEILING).intValue();
	}
	
	/**
	 * 取业务单笔限额与通道单日限额最小值
	 * @param businessRemittanceLimitAmount 业务放款限额
	 * @return 放款拆分基础金额
	 */
	public BigDecimal getRemittanceSplitBasicAmount(BigDecimal businessRemittanceLimitAmount) {
		if(businessRemittanceLimitAmount == null ) {
			return this.oneDayLimitAmount;
		}
		if(this.oneDayLimitAmount == null) {
			return businessRemittanceLimitAmount;
		}
		if(this.oneDayLimitAmount.compareTo(businessRemittanceLimitAmount) > 0) {
			return businessRemittanceLimitAmount;
		}else {
			return this.oneDayLimitAmount;
		}
	}
	
}