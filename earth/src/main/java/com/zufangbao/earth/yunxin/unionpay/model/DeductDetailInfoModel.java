package com.zufangbao.earth.yunxin.unionpay.model;

import java.math.BigDecimal;

import com.zufangbao.earth.model.ManualDeductWebModel;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

public class DeductDetailInfoModel {

	private String sn; // 记录序号

	private String bankCode; // 银行代码

	private String accountNo; // 银行卡号

	private String accountName; // 持卡人姓名

	private BigDecimal amount; // 金额

	private String remark; // 备注
	
	private String idCardNum; //身份证号
	
	private String province; //开户行所在省
	
	private String city; //开户行所在市

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public DeductDetailInfoModel(TransferApplication transferApplication) {
		this.setAmount(transferApplication.getAmount());
		this.setSn(transferApplication.getTransferApplicationNo());
		this.setRemark("");
		
		ContractAccount account = transferApplication.getContractAccount();
		this.setBankCode(account.getBankCode());
		this.setAccountNo(account.getPayAcNo());
		this.setAccountName(account.getPayerName());
		this.setIdCardNum(account.getIdCardNum());
		this.setProvince(account.getProvince());
		this.setCity(account.getCity());
	}
	
	public DeductDetailInfoModel(ManualDeductWebModel manualBatchDeductWebModel, String sn){
		this.setAmount(manualBatchDeductWebModel.getAmount());
		this.setSn(sn);
		this.setRemark(manualBatchDeductWebModel.getRemark());
		this.setBankCode(manualBatchDeductWebModel.getBankCode());
		this.setAccountNo(manualBatchDeductWebModel.getAccountNo());
		this.setAccountName(manualBatchDeductWebModel.getAccountName());
		this.setIdCardNum(manualBatchDeductWebModel.getIdCardNum());
		this.setProvince(manualBatchDeductWebModel.getProvince());
		this.setCity(manualBatchDeductWebModel.getCity());
	}

	public DeductDetailInfoModel(BigDecimal amount, String sn, ContractAccount contractAccount) {
		this.setAmount(amount);
		this.setSn(sn);
		this.setRemark("");
		
		this.setBankCode(contractAccount.getBankCode());
		this.setAccountNo(contractAccount.getPayAcNo());
		this.setAccountName(contractAccount.getPayerName());
		this.setIdCardNum(contractAccount.getIdCardNum());
		this.setProvince(contractAccount.getProvince());
		this.setCity(contractAccount.getCity());
	}
	
	public DeductDetailInfoModel() {
		super();
	}
	
}
