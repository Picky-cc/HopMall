package com.suidifu.coffer.entity.unionpay.gz.base;

import java.util.Map;

import com.suidifu.coffer.entity.DebitModel;

public abstract class GZUnionPayBaseModel extends DebitModel implements IGZUnionPayApiParams {
	
	/** 交易流水号 **/
	private String reqNo;
	
	/** 用户名 **/
	private String userName;
	
	/** 用户密码 **/
	private String userPwd;
	
	/** 商户代码 **/
	private String merchantId;
	
	/** 银联接口地址 **/
	private String apiUrl;
	
	/** 公钥文件地址 **/
	private String cerFilePath;
		
	/** 私钥文件地址 **/
	private String pfxFilePath;
		
	/** 私钥key **/
	private String pfxFileKey;
	
	/** 请求报文 **/
	private String xmlPacket;
	
	public void initBaseParams(Map<String, String> workParms) {
		//setReqNo(workParms.getOrDefault("reqNo", ""));
		setUserName(workParms.getOrDefault("userName", ""));
		setUserPwd(workParms.getOrDefault("userPwd", ""));
		setMerchantId(workParms.getOrDefault("merchantId", ""));
		setApiUrl(workParms.getOrDefault("apiUrl", ""));
		setCerFilePath(workParms.getOrDefault("cerFilePath", ""));
		setPfxFilePath(workParms.getOrDefault("pfxFilePath", ""));
		setPfxFileKey(workParms.getOrDefault("pfxFileKey", ""));
	}
	
	public String getXmlPacket() {
		return xmlPacket;
	}

	public void setXmlPacket(String xmlPacket) {
		this.xmlPacket = xmlPacket;
	}

	@Override
	public String getPfxFileKey() {
		return pfxFileKey;
	}

	@Override
	public String getPfxFilePath() {
		return pfxFilePath;
	}

	@Override
	public String getCerFilePath() {
		return cerFilePath;
	}

	@Override
	public String getApiUrl() {
		return apiUrl;
	}

	@Override
	public void setPfxFileKey(String pfxFileKey) {
		this.pfxFileKey = pfxFileKey;
	}

	@Override
	public void setPfxFilePath(String pfxFilePath) {
		this.pfxFilePath = pfxFilePath;
	}

	@Override
	public void setCerFilePath(String cerFilePath) {
		this.cerFilePath = cerFilePath;
	}

	@Override
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
