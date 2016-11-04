package com.zufangbao.earth.web.controller.channel;

import java.util.Date;

import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.ConfigureProgress;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;

public class PaymentChannelInformationListModel {
	
	private String relatedFinancialContractUuid;
	
	private String relatedFinancialContractName;
	
	/**
	 * 支付通道uuid
	 */
	private String paymentChannelUuid;
	
	/**
	 * 通道名称:{网关名称}{000}
	 */
	private String paymentChannelName;
	
	/**
	 * 网关名称
	 */
	private String paymentInstitutionName;
	
	/**
	 * 商户号
	 */
	private String outlierChannelName;
	
	/**
	 * 业务类型
	 */
	private BusinessType businessType;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 付款通道工作状态
	 */
	private ChannelWorkingStatus creditChannelWorkingStatus;
	
	/**
	 * 收款通道工作状态
	 */
	private ChannelWorkingStatus debitChannelWorkingStatus;
	
	/**
	 * 配置进程
	 */
	private ConfigureProgress configureProgress;

	public String getRelatedFinancialContractUuid() {
		return relatedFinancialContractUuid;
	}

	public void setRelatedFinancialContractUuid(String relatedFinancialContractUuid) {
		this.relatedFinancialContractUuid = relatedFinancialContractUuid;
	}

	public String getRelatedFinancialContractName() {
		return relatedFinancialContractName;
	}

	public void setRelatedFinancialContractName(String relatedFinancialContractName) {
		this.relatedFinancialContractName = relatedFinancialContractName;
	}

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public String getPaymentInstitutionName() {
		return paymentInstitutionName;
	}

	public void setPaymentInstitutionName(
			String paymentInstitutionName) {
		this.paymentInstitutionName = paymentInstitutionName;
	}
	public void setPaymentInstitutionName(
			PaymentInstitutionName paymentInstitutionName) {
		this.paymentInstitutionName = paymentInstitutionName==null?"":paymentInstitutionName.getChineseMessage();
	}

	public String getOutlierChannelName() {
		return outlierChannelName;
	}

	public void setOutlierChannelName(String outlierChannelName) {
		this.outlierChannelName = outlierChannelName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ChannelWorkingStatus getCreditChannelWorkingStatus() {
		return creditChannelWorkingStatus;
	}

	public void setCreditChannelWorkingStatus(
			ChannelWorkingStatus creditChannelWorkingStatus) {
		this.creditChannelWorkingStatus = creditChannelWorkingStatus;
	}

	public ChannelWorkingStatus getDebitChannelWorkingStatus() {
		return debitChannelWorkingStatus;
	}

	public void setDebitChannelWorkingStatus(
			ChannelWorkingStatus debitChannelWorkingStatus) {
		this.debitChannelWorkingStatus = debitChannelWorkingStatus;
	}

	public ConfigureProgress getConfigureProgress() {
		return configureProgress;
	}

	public void setConfigureProgress(ConfigureProgress configureProgress) {
		this.configureProgress = configureProgress;
	}
	
	public BusinessType getBusinessType() {
		return businessType;
	}
	
	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public PaymentChannelInformationListModel() {
		super();
	}

	public PaymentChannelInformationListModel(
			String relatedFinancialContractUuid,
			String relatedFinancialContractName, String paymentChannelUuid,
			String paymentChannelName,
			String paymentInstitutionName,
			String outlierChannelName, Date createTime,
			ChannelWorkingStatus creditChannelWorkingStatus,
			ChannelWorkingStatus debitChannelWorkingStatus,
			ConfigureProgress configureProgress) {
		super();
		this.relatedFinancialContractUuid = relatedFinancialContractUuid;
		this.relatedFinancialContractName = relatedFinancialContractName;
		this.paymentChannelUuid = paymentChannelUuid;
		this.paymentChannelName = paymentChannelName;
		this.paymentInstitutionName = paymentInstitutionName;
		this.outlierChannelName = outlierChannelName;
		this.createTime = createTime;
		this.creditChannelWorkingStatus = creditChannelWorkingStatus;
		this.debitChannelWorkingStatus = debitChannelWorkingStatus;
		this.configureProgress = configureProgress;
	}
	
	public PaymentChannelInformationListModel(PaymentChannelInformation paymentChannelInformation){
		this.relatedFinancialContractUuid = paymentChannelInformation.getRelatedFinancialContractUuid();
		this.relatedFinancialContractName = paymentChannelInformation.getRelatedFinancialContractName();
		this.paymentChannelUuid = paymentChannelInformation.getPaymentChannelUuid();
		this.paymentChannelName = paymentChannelInformation.getPaymentChannelName();
		PaymentInstitutionName paymentInstitutionName = paymentChannelInformation.getPaymentInstitutionName();
		this.setPaymentInstitutionName(paymentInstitutionName);
		this.outlierChannelName = paymentChannelInformation.getOutlierChannelName();
		this.createTime = paymentChannelInformation.getCreateTime();
		this.creditChannelWorkingStatus = paymentChannelInformation.getCreditChannelWorkingStatus();
		this.debitChannelWorkingStatus = paymentChannelInformation.getDebitChannelWorkingStatus();
		this.configureProgress = paymentChannelInformation.getConfigureProgress();
		this.businessType = paymentChannelInformation.getBusinessType();
	}
	
}
