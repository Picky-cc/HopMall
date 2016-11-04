/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.stereotype.Component;

/**
 *基本入参信息
 * @author wk
 *
 */
@Component
public class BasicParams {

	/**集团CIS编号*/
	private String groupCIS;	
	/**归属银行编号*/
	private String bankCode;
	/**证书ID*/
	private String certificateId;
	
	/**
	 * 
	 */
	public BasicParams() {
		super();
	}
	/**
	 * @return the groupCIS
	 */
	public String getGroupCIS() {
		return groupCIS;
	}
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * @return the certificateId
	 */
	public String getCertificateId() {
		return certificateId;
	}
	/**
	 * @param groupCIS the groupCIS to set
	 */
	public void setGroupCIS(String groupCIS) {
		this.groupCIS = groupCIS;
	}
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 * @param certificateId the certificateId to set
	 */
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	
}
