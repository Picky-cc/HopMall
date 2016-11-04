package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.EnumUtil;

/**
 * 
 * @author dcc
 *
 */
public class BankReconciliationQueryModel {
	
	private String bankName;
	
	private String hostAccountNo;
	
	private int accountSide;
	
	private int auditStatus;
	
	private String key;
	
	private String tradeStartTime;
	
	private String tradeEndTime;
	
	private String checkErrorMsg;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getKey() {
		return key;
	}

	public BigDecimal getAmountFromKey() {
		try {
			return new BigDecimal(key);
		} catch(Exception e){
		}
		return null;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getHostAccountNo() {
		return hostAccountNo;
	}

	public void setHostAccountNo(String hostAccountNo) {
		this.hostAccountNo = hostAccountNo;
	}

	public int getAccountSide() {
		return accountSide;
	}

	public String getTradeStartTime() {
		return tradeStartTime;
	}
	
	public Date parseTradeStartTime(){
		return tradeStartTime==null?null:DateUtils.parseDate(tradeStartTime,DateUtils.LONG_DATE_FORMAT);
	}

	public void setTradeStartTime(String tradeStartTime) {
		this.tradeStartTime = tradeStartTime;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}
	
	public Date parseTradeEndTime(){
		return tradeEndTime==null?null:DateUtils.parseDate(tradeEndTime,DateUtils.LONG_DATE_FORMAT);
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	public AccountSide getAccountSideEnum(){
		return EnumUtil.fromOrdinal(AccountSide.class,accountSide);
	}
	
	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public AuditStatus getAuditStatusEnum(){
		return EnumUtil.fromOrdinal(AuditStatus.class, auditStatus);
	}
	
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getCheckErrorMsg() {
		return checkErrorMsg;
	}

	public void setCheckErrorMsg(String checkErrorMsg) {
		this.checkErrorMsg = checkErrorMsg;
	}

	public List<AuditStatus> getAuditStatusEnumList(){
		List<Integer> ordinals = getauditStatusOrdinalList();
		List<AuditStatus> auditStatusEnumList = new ArrayList<AuditStatus>();
		for (Integer ordinal : ordinals) {
			if(ordinal==null) continue;
			AuditStatus auditStatus = AuditStatus.fromOrdinal(ordinal);
			if(auditStatus==null) continue;
			auditStatusEnumList.add(auditStatus);
		}
		return auditStatusEnumList;
	}
	public List<Integer> getauditStatusOrdinalList(){
		List<Integer> ordinals = JsonUtils.parseArray(auditStatus+"",Integer.class);
		return ordinals;
	}
	
	public boolean valid_4export_csv(){
		Date startTime = parseTradeStartTime();
		if(startTime==null){
			this.checkErrorMsg = "请输入起始时间";
			return false;
		}
		Date endTime = parseTradeEndTime();
		if(endTime==null){
			this.checkErrorMsg = "请输入终止时间";
			return false;
		}
		if(DateUtils.addDays(startTime, 3).before(endTime)){
			this.checkErrorMsg = "时间跨度不能超过3天";
			return false;
		}
		return true;
	}
	
}
