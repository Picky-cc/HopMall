package com.zufangbao.sun.entity.account;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.corp.App;

/**
 * 
 * @author zjm
 * 商户付款账户
 */
@Entity
@Table(name = "app_account")
public class AppAccount {

	@Id
	@GeneratedValue
	private Long id;
	
	private String uuid;

	private String bankName;

	private String accountName;

	private String accountNo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;
	
	private AppAccountActiveStatus appAccountActiveStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public AppAccountActiveStatus getAppAccountActiveStatus() {
		return appAccountActiveStatus;
	}

	public void setAppAccountActiveStatus(
			AppAccountActiveStatus appAccountActiveStatus) {
		this.appAccountActiveStatus = appAccountActiveStatus;
	}
	

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public AppAccount() {
		super();
	}
	
	
}
