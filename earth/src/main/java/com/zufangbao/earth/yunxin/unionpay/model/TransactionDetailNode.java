package com.zufangbao.earth.yunxin.unionpay.model;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.utils.FinanceUtils;

/**
 * 交易明细节点数据
 * @author zhanghongbing
 *
 */
@XStreamAlias("RET_DETAIL")
public class TransactionDetailNode {

	@XStreamAlias("REQ_SN")
	private String reqNo; //交易流水号
	
	@XStreamAlias("SN")
	private String sn; //记录序号
	
	@XStreamAlias("SF_TYPE")
	private String sfType; //收付类型
	
	@XStreamAlias("TRX_CODE")
	private String taxCode; //交易代码
	
	@XStreamAlias("BUSINESS_CODE")
	private String businessCode; //业务代码
	
	@XStreamAlias("ACCOUNT")
	private String account; //账户
	
	@XStreamAlias("ACCOUNT_NAME")
	private String accountName; //账户名
	
	@XStreamAlias("AMOUNT")
	private String amount; //金额
	
	@XStreamAlias("COMPLETE_TIME")
	private String completeTime; //完成时间
	
	@XStreamAlias("SETT_DATE")
	private String settDate; //清算日期
	
	@XStreamAlias("RECKON_ACCOUNT")
	private String reckonAccount; //清算账号
	
	@XStreamAlias("REMARK")
	private String remark; //备注
	
	@XStreamAlias("RET_CODE")
	private String retCode; //返回吗
	
	@XStreamAlias("ERR_MSG")
	private String errMsg; //信息

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSfType() {
		return sfType;
	}

	public void setSfType(String sfType) {
		this.sfType = sfType;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAmount() {
		BigDecimal bdAmount = new BigDecimal(amount);
		return FinanceUtils.convert_cent_to_yuan(bdAmount);
	}
	public String getAmountString(){
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getSettDate() {
		return settDate;
	}

	public void setSettDate(String settDate) {
		this.settDate = settDate;
	}

	public String getReckonAccount() {
		return reckonAccount;
	}

	public void setReckonAccount(String reckonAccount) {
		this.reckonAccount = reckonAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public AccountSide getAccountSide(){
		if("f".equalsIgnoreCase(sfType)){
			return AccountSide.CREDIT;
		}else if("s".equalsIgnoreCase(sfType)) {
			return AccountSide.DEBIT;
		} else {
			return null;
		}
	}
	
}
