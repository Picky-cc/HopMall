package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;

public class OfflineBillCreateModel{
	private String bankShowName;
	private String payerAccountName;
	private String serialNo;
	private String payerAccountNo;
	private String guaranteeRepaymentUuids;
	private String comment;
	private String amount;
	private String tradeTimeString;
	
	private String checkFailedMsg;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBankShowName() {
		return bankShowName;
	}
	public void setBankShowName(String bankShowName) {
		this.bankShowName = bankShowName;
	}
	public String getPayerAccountName() {
		return payerAccountName;
	}
	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPayerAccountNo() {
		return payerAccountNo;
	}
	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}
	public String getGuaranteeRepaymentUuids() {
		return guaranteeRepaymentUuids;
	}
	public void setGuaranteeRepaymentUuids(String guaranteeRepaymentUuids) {
		this.guaranteeRepaymentUuids = guaranteeRepaymentUuids;
	}
	public List<String> parseGuaranteeRepaymentUuidList(){
		List<String> guaranteeReapaymentUuidList =  JsonUtils.parseArray(guaranteeRepaymentUuids,String.class);
		return guaranteeReapaymentUuidList==null?new ArrayList<String>():guaranteeReapaymentUuidList;
		
	}
	
	public BigDecimal getAmount() {
		return new BigDecimal(this.amount);
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTradeTimeString() {
		return tradeTimeString;
	}
	public Date getTradeTime() {
		return tradeTimeString==null?null:DateUtils.parseDate(tradeTimeString, "yyyy-MM-dd HH:mm:ss");
	}
	public void setTradeTimeString(String tradeTimeString) {
		this.tradeTimeString = tradeTimeString;
	}
	
	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}
	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}
	public OfflineBillCreateModel(){
		
	}
	public OfflineBillCreateModel(String bankShowName, String payerAccountName,
			String serialNo, String payerAccountNo, String guaranteeRepaymentUuids,
			String comment) {
		super();
		this.bankShowName = bankShowName;
		this.payerAccountName = payerAccountName;
		this.serialNo = serialNo;
		this.payerAccountNo = payerAccountNo;
		this.guaranteeRepaymentUuids = guaranteeRepaymentUuids;
		this.comment = comment;
	}
	
	public OfflineBillCreateModel(String bankShowName, String payerAccountName,
			String serialNo, String payerAccountNo, String comment,
			String amount, String tradeTimeString) {
		super();
		this.bankShowName = bankShowName;
		this.payerAccountName = payerAccountName;
		this.serialNo = serialNo;
		this.payerAccountNo = payerAccountNo;
		this.comment = comment;
		this.amount = amount;
		this.tradeTimeString = tradeTimeString;
	}
	
	public boolean isValid() {
		if(StringUtils.isEmpty(this.payerAccountName)) {
			this.checkFailedMsg = "还款方名称，不能为空！";
			return false;
		}
		if(StringUtils.isEmpty(this.bankShowName)) {
			this.checkFailedMsg = "还款方开户行，不能为空！";
			return false;
		}
		if(StringUtils.isEmpty(this.payerAccountNo)) {
			this.checkFailedMsg = "还款方账户，不能为空！";
			return false;
		}
		try {
			BigDecimal amountBD = getAmount();
			if(amountBD.compareTo(BigDecimal.ZERO)<=0) {
				this.checkFailedMsg = "金额需大于0！";
				return false;
			}
		} catch (Exception e) {
			this.checkFailedMsg = "金额格式错误！";
			return false;
		}
		if(StringUtils.isEmpty(this.serialNo)) {
			this.checkFailedMsg = "支付机构流水号，不能为空！";
			return false;
		}
		return true;
	}
	
}
