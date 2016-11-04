package com.zufangbao.sun.entity.account;

import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.JsonUtils;

/**
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	private String uuid;

	private String bankName;

	private String accountName;

	private String accountNo;

	private String alias;

	private String attr;
	
	private boolean scanCashFlowSwitch;
	private boolean usbKeyConfigured;
	
	private String bankCode;

	private final static String BANK_BRANCH_NO_KEY = "bankBranchNo";	// 分行号
	private final static String USB_UUID_KEY = "usbUuid";
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Map<String, Object> getAttr() {
		return JsonUtils.parse(this.attr);
	}

	public String getAttrBankBranchNo(){
		Map<String,Object> attr = getAttr();
		if(attr!=null){
			return (String)attr.getOrDefault(BANK_BRANCH_NO_KEY, "");
		}
		return "";
	}
	public String getUsbUuid(){
		Map<String,Object> attr = getAttr();
		if(attr!=null){
			return (String)attr.getOrDefault(USB_UUID_KEY,"");
		}
		return "";
	}
	public boolean isScanCashFlowSwitch() {
		return scanCashFlowSwitch;
	}

	public void setScanCashFlowSwitch(boolean scanCashFlowSwitch) {
		this.scanCashFlowSwitch = scanCashFlowSwitch;
	}

	public boolean isUsbKeyConfigured() {
		return usbKeyConfigured;
	}

	public void setUsbKeyConfigured(boolean usbKeyConfigured) {
		this.usbKeyConfigured = usbKeyConfigured;
	}
	
	public String getMarkedAccountNo(){
		if(StringUtils.isEmpty(accountNo)){
			return "";
		}
		return StringUtils.left(accountNo, 5)+"***"+StringUtils.right(accountNo, 4);
	}

	public Account() {
		super();
	}

	public Account(String bankName, String accountName, String accountNo) {
		super();
		this.bankName = bankName;
		this.accountName = accountName;
		this.accountNo = accountNo;
		this.uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
