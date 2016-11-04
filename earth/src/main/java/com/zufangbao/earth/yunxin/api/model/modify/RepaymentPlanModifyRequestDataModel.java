package com.zufangbao.earth.yunxin.api.model.modify;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.utils.DateUtils;

/**
 * 
 * @author louguanyang
 *
 */
public class RepaymentPlanModifyRequestDataModel {
	/**
	 * 计划还款日期 yyyy-MM-dd
	 */
	private String assetRecycleDate;
	/**
	 * 计划本金
	 */
	private BigDecimal assetPrincipal = BigDecimal.ZERO;
	/**
	 * 计划利息
	 */
	private BigDecimal assetInterest = BigDecimal.ZERO;
	/**
	 * 服务费
	 */
	private BigDecimal serviceCharge = BigDecimal.ZERO;
	/**
	 * 维护费
	 */
	private BigDecimal maintenanceCharge = BigDecimal.ZERO;
	/**
	 * 其它费用
	 */
	private BigDecimal otherCharge = BigDecimal.ZERO;
	
	private Integer assetType = null;

	public RepaymentPlanModifyRequestDataModel() {
		super();
	}

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	public Date getDate() {
		try {
			return DateUtils.asDay(assetRecycleDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getAssetRecycleDate() {
		return assetRecycleDate;
	}

	public void setAssetRecycleDate(String assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}

	public BigDecimal getAssetPrincipal() {
		return assetPrincipal;
	}

	public void setAssetPrincipal(BigDecimal assetPrincipal) {
		this.assetPrincipal = assetPrincipal;
	}

	public BigDecimal getAssetInterest() {
		return assetInterest;
	}

	public void setAssetInterest(BigDecimal assetInterest) {
		this.assetInterest = assetInterest;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getMaintenanceCharge() {
		return maintenanceCharge;
	}

	public void setMaintenanceCharge(BigDecimal maintenanceCharge) {
		this.maintenanceCharge = maintenanceCharge;
	}

	public BigDecimal getOtherCharge() {
		return otherCharge;
	}

	public void setOtherCharge(BigDecimal otherCharge) {
		this.otherCharge = otherCharge;
	}
	
	public BigDecimal getTheAdditionalFee(){
		return this.serviceCharge.add(maintenanceCharge).add(otherCharge);
	}
}
