
package com.zufangbao.sun.entity.financial;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 支付通道
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "payment_channel")
public class PaymentChannel {
	
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 支付通道名称
	 */
	private String channelName;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String userPassword;
	/**
	 * 商户号
	 */
	private String merchantId;
	/**
	 * 公钥文件地址
	 */
	private String cerFilePath;
	/**
	 * 私钥文件地址
	 */
	private String pfxFilePath;
	/**
	 * 私钥key
	 */
	private String pfxFileKey;
	/**
	 * 通道类型 0:广银联通道
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentChannelType paymentChannelType;
	/**
	 * 接口地址
	 */
	private String apiUrl;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Type(type = "date")
	private Date fromDate;
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Type(type = "date")
	private Date thruDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCerFilePath() {
		return cerFilePath;
	}
	public void setCerFilePath(String cerFilePath) {
		this.cerFilePath = cerFilePath;
	}
	public String getPfxFilePath() {
		return pfxFilePath;
	}
	public void setPfxFilePath(String pfxFilePath) {
		this.pfxFilePath = pfxFilePath;
	}
	public String getPfxFileKey() {
		return pfxFileKey;
	}
	public void setPfxFileKey(String pfxFileKey) {
		this.pfxFileKey = pfxFileKey;
	}
	public PaymentChannelType getPaymentChannelType() {
		return paymentChannelType;
	}
	public void setPaymentChannelType(PaymentChannelType paymentChannelType) {
		this.paymentChannelType = paymentChannelType;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date formDate) {
		this.fromDate = formDate;
	}
	public Date getThruDate() {
		return thruDate;
	}
	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}
}
