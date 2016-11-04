package com.zufangbao.sun.entity.financial;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.BusinessUtils;

@Entity
@Table(name = "payment_channel_information")
public class PaymentChannelInformation {
	
	@Id
	@GeneratedValue
	public long id;
	
	/**
	 * 关联的信托合同uuid
	 */
	private String relatedFinancialContractUuid;
	
	/**
	 * 关联的信托合同名称
	 */
	private String relatedFinancialContractName;
	
	/**
	 * 支付通道uuid
	 */
	private String paymentChannelUuid;
	
	/**
	 * 通道名称
	 */
	private String paymentChannelName;
	
	/**
	 * 支付机构名称 网关
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentInstitutionName paymentInstitutionName;
	
	/**
	 * 清算号
	 */
	private String clearingNo;
	
	/**
	 * 商户号
	 */
	private String outlierChannelName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 付款服务uuid
	 */
	private String creditPaymentChannelServiceUuid;
	
	/**
	 * 收款服务uuid
	 */
	private String debitPaymentChannelServiceUuid;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	
	/**
	 * 付款通道工作状态
	 */
	@Enumerated(EnumType.ORDINAL)
	private ChannelWorkingStatus  creditChannelWorkingStatus;
	
	/**
	 * 收款通道工作状态
	 */
	@Enumerated(EnumType.ORDINAL)
	private ChannelWorkingStatus  debitChannelWorkingStatus;
	
	/**
	 * 配置进程
	 */
	@Enumerated(EnumType.ORDINAL)
	private ConfigureProgress configureProgress;
	
	/**
	 * 业务类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private BusinessType businessType;

	/**
	 * 通道配置数据
	 */
	private String paymentConfigureData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRelatedFinancialContractUuid() {
		return relatedFinancialContractUuid;
	}

	public void setRelatedFinancialContractUuid(String relatedFinancialContractUuid) {
		this.relatedFinancialContractUuid = relatedFinancialContractUuid;
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

	public PaymentInstitutionName getPaymentInstitutionName() {
		return paymentInstitutionName;
	}
	
	public Integer getPaymentGateway() {
		return paymentInstitutionName == null ? null : paymentInstitutionName.ordinal();
	}
	
	public String getPaymentInstitutionNameMsg(){
		return this.paymentInstitutionName == null? "" : this.paymentInstitutionName.getChineseMessage();
	}

	public void setPaymentInstitutionName(PaymentInstitutionName paymentInstitutionName) {
		this.paymentInstitutionName = paymentInstitutionName;
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

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
	
	public void initConfigureProgress(PaymentChannelConfigure paymentChannelConfigure) {
		if (paymentChannelConfigure.isComplete()) {
			this.configureProgress = ConfigureProgress.DONE;
		} else {
			this.configureProgress = ConfigureProgress.WAITING;
		}
	}

	public String getPaymentConfigureData() {
		return paymentConfigureData;
	}

	public void setPaymentConfigureData(String paymentConfigureData) {
		this.paymentConfigureData = paymentConfigureData;
	}
	
	public PaymentChannelConfigure extractConfigData(){
		return JsonUtils.parse(this.paymentConfigureData, PaymentChannelConfigure.class);
	}
	
	public String getRelatedFinancialContractName() {
		return relatedFinancialContractName;
	}
	
	public void setRelatedFinancialContractName(
			String relatedFinancialContractName) {
		this.relatedFinancialContractName = relatedFinancialContractName;
	}
	
	public String getClearingNo() {
		return clearingNo;
	}
	
	public String getMerId_ClearingNo(){
		return BusinessUtils.get_merId_clearingNo(outlierChannelName, clearingNo);
	}
	
	public void setClearingNo(String clearingNo) {
		this.clearingNo = clearingNo;
	}

	public PaymentChannelInformation() {
		super();
		this.paymentChannelUuid = UUID.randomUUID().toString();
		Date currentDate = new Date();
		this.createTime = currentDate;
		this.lastModifyTime= currentDate;
	}
	
	public void initBy(PaymentChannelConfigure paymentChannelConfigure){
		
		ChannelWorkingStatus creditStatus = paymentChannelConfigure.getCreditChannelConfigure().getChannelStatus();;
		this.setCreditChannelWorkingStatus(creditStatus);
		
		ChannelWorkingStatus debitStatus = paymentChannelConfigure.getDebitChannelConfigure().getChannelStatus();
		this.setDebitChannelWorkingStatus(debitStatus);
		
		String paymentConfigData = JSON.toJSONString(paymentChannelConfigure,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		this.setPaymentConfigureData(paymentConfigData);
		
		this.initConfigureProgress(paymentChannelConfigure);
	}
	
	public void setBy(PaymentChannelConfigure paymentChannelConfigure){
		ChannelWorkingStatus creditStatus = paymentChannelConfigure.getCreditChannelConfigure().getChannelStatus();;
		this.setCreditChannelWorkingStatus(creditStatus);
		
		ChannelWorkingStatus debitStatus = paymentChannelConfigure.getDebitChannelConfigure().getChannelStatus();
		this.setDebitChannelWorkingStatus(debitStatus);
		
		this.initConfigureProgress(paymentChannelConfigure);
		String paymentConfigData = JSON.toJSONString(paymentChannelConfigure,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		this.setPaymentConfigureData(paymentConfigData);
		this.setLastModifyTime(new Date());
	}

	public String getCreditPaymentChannelServiceUuid() {
		return creditPaymentChannelServiceUuid;
	}

	public void setCreditPaymentChannelServiceUuid(
			String creditPaymentChannelServiceUuid) {
		this.creditPaymentChannelServiceUuid = creditPaymentChannelServiceUuid;
	}

	public String getDebitPaymentChannelServiceUuid() {
		return debitPaymentChannelServiceUuid;
	}

	public void setDebitPaymentChannelServiceUuid(
			String debitPaymentChannelServiceUuid) {
		this.debitPaymentChannelServiceUuid = debitPaymentChannelServiceUuid;
	}
	
}
