package com.zufangbao.earth.yunxin.unionpay.model.basic;

import com.zufangbao.sun.entity.financial.PaymentChannel;


/**
 * 广州银联基础信息模型
 * @author zhanghongbing
 *
 */
public class GZUnionPayBasicInfoModel implements IGZUnionPayApiParams {
	
	private String reqNo; //交易流水号
	
	private String userName; //用户名
	
	private String userPwd; //用户密码
	
	private String merchantId; //商户代码
	
	private String apiUrl; //银联接口地址
	
	private String cerFilePath; //公钥文件地址
		
	private String pfxFilePath; //私钥文件地址
		
	private String pfxFileKey; //私钥key
	
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

	@Override
	public String getApiUrl() {
		return apiUrl;
	}

	@Override
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	@Override
	public String getCerFilePath() {
		return cerFilePath;
	}

	@Override
	public void setCerFilePath(String cerFilePath) {
		this.cerFilePath = cerFilePath;
	}

	@Override
	public String getPfxFilePath() {
		return pfxFilePath;
	}

	@Override
	public void setPfxFilePath(String pfxFilePath) {
		this.pfxFilePath = pfxFilePath;
	}

	@Override
	public String getPfxFileKey() {
		return pfxFileKey;
	}

	@Override
	public void setPfxFileKey(String pfxFileKey) {
		this.pfxFileKey = pfxFileKey;
	}

	public GZUnionPayBasicInfoModel(String userName, String userPwd,
			String merchantId, String apiUrl, String cerFilePath,
			String pfxFilePath, String pfxFileKey) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
		this.merchantId = merchantId;
		this.apiUrl = apiUrl;
		this.cerFilePath = cerFilePath;
		this.pfxFilePath = pfxFilePath;
		this.pfxFileKey = pfxFileKey;
	}
	
	public GZUnionPayBasicInfoModel(PaymentChannel channel) {
		this.userName = channel.getUserName();
		this.userPwd = channel.getUserPassword();
		this.merchantId = channel.getMerchantId();
		this.apiUrl = channel.getApiUrl();
		this.cerFilePath = channel.getCerFilePath();
		this.pfxFilePath = channel.getPfxFilePath();
		this.pfxFileKey = channel.getPfxFileKey();
	}

	public GZUnionPayBasicInfoModel() {
		super();
	}
	
}
