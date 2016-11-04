package com.zufangbao.earth.api.xml;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailNode;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;
import com.zufangbao.sun.utils.DateUtils;

@XStreamAlias("NTEBPINFZ")
public class TransactionDetail {
	
	@XStreamAlias("YURREF")
	private String reqNo; //原交易编号，也就是上送给银联的请求号
	
	@XStreamAlias("RPYACC")
	private String account; //账户
	
	@XStreamAlias("RPYNAM")
	private String accountName; //账户名
	
	@XStreamAlias("AMTCDR")
	private String accountSide;//借贷标记.C:贷,银联表示代付;D:借，银联表示代收
	
	@XStreamAlias("TRSAMT")
	private String amount; //金额
	
	@XStreamAlias("ETYTIM")
	private String completeTime; //完成时间
	
	@XStreamAlias("NARYUR")
	private String remark; //备注
	
	@XStreamAlias("RECACC")
	private String reckonAccount; //清算账号
	
	@XStreamAlias("REFNBR")
	private String serialNo;//交易流水号，银联不提供
	
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

	public String getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}

	public String getAmount() {
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
	
	public static String convertToCompleteTime(Date date){
		if(date==null){
			return "";
		} else{
			return DateUtils.format(date,DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS);
		}
		
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReckonAccount() {
		return reckonAccount;
	}

	public void setReckonAccount(String reckonAccount) {
		this.reckonAccount = reckonAccount;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public TransactionDetail(){
		
	}

	public TransactionDetail(String reqNo, String account, String accountName,
			String accountSide, String amount, String completeTime,
			String remark, String reckonAccount, String serialNo) {
		super();
		this.reqNo = reqNo;
		this.account = account;
		this.accountName = accountName;
		this.accountSide = accountSide;
		this.amount = amount;
		this.completeTime = completeTime;
		this.remark = remark;
		this.reckonAccount = reckonAccount;
		this.serialNo = serialNo;
	}
	
	public static TransactionDetail createUnionTransactionDetail(TransactionDetailNode transactionDetailNode){
		
		return new TransactionDetail(StringUtils.defaultString(transactionDetailNode.getReqNo()), StringUtils.defaultString(transactionDetailNode.getAccount()), StringUtils.defaultString(transactionDetailNode.getAccountName()),
				StringUtils.defaultString(AccountSide.convertToApiAccountType(transactionDetailNode.getAccountSide())), transactionDetailNode.getAmount()+"", StringUtils.defaultString(transactionDetailNode.getCompleteTime()),
				StringUtils.defaultString(transactionDetailNode.getRemark()), StringUtils.defaultString(transactionDetailNode.getReckonAccount()), null);
	}
	
	public static TransactionDetail createDirectBankTransactionDetail(FlowRecord flowRecord){
		AccountSide accountSide = AccountSide.reverse(BankSide.fromValue(flowRecord.getDrcrf()));
		return new TransactionDetail(StringUtils.defaultString(flowRecord.getReqNo()), StringUtils.defaultString(flowRecord.getRecipAccNo()), StringUtils.defaultString(flowRecord.getRecipName()),
				StringUtils.defaultString(AccountSide.convertToApiAccountType(accountSide)), flowRecord.getTransAmount()+"", StringUtils.defaultString(convertToCompleteTime(flowRecord.getTime())),
				StringUtils.defaultString(flowRecord.getSummary()), null, StringUtils.defaultString(flowRecord.getSerialNo()));
	}
	
	
}
