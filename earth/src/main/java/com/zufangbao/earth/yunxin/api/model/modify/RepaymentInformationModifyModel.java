package com.zufangbao.earth.yunxin.api.model.modify;

import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.sun.utils.StringUtils;


public class RepaymentInformationModifyModel {

	private String requestNo;
	
	private String uniqueId;
	
	private String contractNo;
	
	private String bankCode;
	
	private String bankAccount;
	
	private String bankName;
	
	private String bankProvince;
	
	private String bankCity;
	
	private String repaymentChannel;
	
	/**
	 * 校验失败信息
	 */
	private String checkFailedMsg;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getRepaymentChannel() {
		return repaymentChannel;
	}

	public void setRepaymentChannel(String repaymentChannel) {
		this.repaymentChannel = repaymentChannel;
	}
	
	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}

	@JSONField(serialize = false)
	public boolean isValid() {
		if(StringUtils.isEmpty(this.bankCode) || StringUtils.isEmpty(this.bankAccount) || StringUtils.isEmpty(this.bankCity)||StringUtils.isEmpty(this.bankProvince)||StringUtils.isEmpty(this.bankName)){
			this.checkFailedMsg = "请必填其中编号［bankCode，bankAccount，bankCity，bankProvince，bankName］！";
			return false;
		}
		
		if(!(StringUtils.isEmpty(this.uniqueId) ^ StringUtils.isEmpty(this.contractNo))) {
			this.checkFailedMsg = "请选填其中一种编号［uniqueId，contractNo］！";
			return false;
		}
		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		return true;
	}

}
