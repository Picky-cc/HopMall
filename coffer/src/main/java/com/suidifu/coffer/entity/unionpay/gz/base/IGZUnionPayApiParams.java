package com.suidifu.coffer.entity.unionpay.gz.base;

/**
 * 广州银联接口参数
 * @author zhanghongbing
 *
 */
public interface IGZUnionPayApiParams {

	public abstract String getPfxFileKey();

	public abstract String getPfxFilePath();

	public abstract String getCerFilePath();

	public abstract String getApiUrl();

	public abstract void setPfxFileKey(String pfxFileKey);

	public abstract void setPfxFilePath(String pfxFilePath);

	public abstract void setCerFilePath(String cerFilePath);

	public abstract void setApiUrl(String apiUrl);

}
