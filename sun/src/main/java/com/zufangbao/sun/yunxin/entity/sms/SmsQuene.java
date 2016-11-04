package com.zufangbao.sun.yunxin.entity.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zufangbao.sun.yunxin.entity.SmsSendStatus;

/**
 * 短信发送队列
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "sms_quene")
public class SmsQuene {
	@Id
	@GeneratedValue
	private Long id;
	/** 客户编号 **/
	private String clientId;
	/** 模版编号 **/
	private String templateCode;
	/** 请求参数 **/
	private String params;
	/** 短信内容 **/
	private String content;
	/** 合作商户号 **/
	private String platformCode;
	/** 创建时间 **/
	private Date createTime;
	/** 请求时间 **/
	private Date requestTime;
	/** 响应时间 **/
	private Date responseTime;
	/** 响应结果 **/
	private String responseTxt;
	/** 是否允许自动发送 **/
	private boolean allowedSendStatus;
	/**
	 * 短信发送状态 0:待发送,1:成功,2:失败
	 */
	@Enumerated(EnumType.ORDINAL)
	private SmsSendStatus smsSendStatus;
	public SmsQuene() {
		super();
	}
	
	public SmsQuene(String clientId, String templateCode, String content,
			String platformCode, boolean allowedSendStatus) {
		super();
		this.clientId = clientId;
		this.templateCode = templateCode;
		this.content = content;
		this.platformCode = platformCode;
		this.allowedSendStatus = allowedSendStatus;
		this.createTime = new Date();
		this.smsSendStatus = SmsSendStatus.WAITING_SEND;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseTxt() {
		return responseTxt;
	}

	public void setResponseTxt(String responseTxt) {
		this.responseTxt = responseTxt;
	}

	
	public boolean isAllowedSendStatus() {
		return allowedSendStatus;
	}

	public void setAllowedSendStatus(boolean allowedSendStatus) {
		this.allowedSendStatus = allowedSendStatus;
	}

	public boolean isAllowedSend() {
		return allowedSendStatus == true && smsSendStatus == SmsSendStatus.WAITING_SEND;
	}

	public Map<String, String> getRequestParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ClientId", this.clientId);
		params.put("Content", this.content);
		params.put("PlatformCode", this.platformCode);
		return params;
	}

	public void updateStatus(String result, boolean sendSmsStatus) {
		this.responseTxt = result;
		this.requestTime = new Date();
		this.responseTime = new Date();
		if(sendSmsStatus) {
			this.smsSendStatus = SmsSendStatus.SUCCESS;
		}else {
			this.smsSendStatus = SmsSendStatus.FAILURE;
		}
	}

	public SmsSendStatus getSmsSendStatus() {
		return smsSendStatus;
	}

	public void setSmsSendStatus(SmsSendStatus smsSendStatus) {
		this.smsSendStatus = smsSendStatus;
	}
	
}
