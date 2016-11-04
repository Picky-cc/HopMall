package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;

import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

/**
 * 农分期资产包 - 还款计划
 * @author louguanyang
 *
 */
public class NFQRepaymentPlan {
	@ExcelVoAttribute(name = "贷款合同编号", column = "A")
	private String contractNo;
	@ExcelVoAttribute(name = "还款日期", column = "B")
	private String assetRecycleDate;
	@ExcelVoAttribute(name = "还款利息", column = "C")
	private String assetInterestValue;
	@ExcelVoAttribute(name = "还款本金", column = "D")
	private String assetPrincipalValue;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getAssetRecycleDate() {
		return assetRecycleDate;
	}
	public void setAssetRecycleDate(String assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}
	public String getAssetInterestValue() {
		return assetInterestValue;
	}
	public void setAssetInterestValue(String assetInterestValue) {
		this.assetInterestValue = assetInterestValue;
	}
	public String getAssetPrincipalValue() {
		return assetPrincipalValue;
	}
	public void setAssetPrincipalValue(String assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}
	public BigDecimal getAssetPrincipalValueBigDecimal() {
		try {
			if(StringUtils.isEmpty(assetPrincipalValue)) {
				return BigDecimal.ZERO;
			}
			return new BigDecimal(assetPrincipalValue);
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}
	public BigDecimal getAssetInterestValueBigDecimal() {
		try {
			if(StringUtils.isEmpty(assetInterestValue)) {
				return BigDecimal.ZERO;
			}
			return new BigDecimal(assetInterestValue);
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}
	
}
