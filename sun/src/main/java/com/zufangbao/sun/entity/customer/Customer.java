/**
 * 
 */
package com.zufangbao.sun.entity.customer;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.yunxin.entity.NFQLoanInformation;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;

/**
 * @author lute
 *
 */

@Entity
@Table(name = "customer")
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String customerUuid;
	/**
	 * 客户姓名
	 */
	private String name;
	
	private String mobile;
	/**
	 * 客户身份证号
	 */
	private String account;
	/**
	 * 客户编号
	 */
	private String source;
	/**
	 * 所属商户
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;
	
	/**
	 * 客户类型:0. 个人 ;1. 公司 
	 */
	private CustomerType customerType;
	
	public Customer initial(App app) {
		this.customerUuid = UUID.randomUUID().toString();
		this.source = app.getName();
		this.app = app;
		this.customerType = CustomerType.PERSON;
		return this;
	}

	public Customer() {
		
	}	
	
	public Customer(NFQLoanInformation nfqLoanInformation, App app) {
		super();
		this.name = nfqLoanInformation.getCustomerName();
		this.account = nfqLoanInformation.getCustomerIDNo();
		//TODO source to code
		this.source = nfqLoanInformation.getCustomerCode();
		this.app = app;
		this.customerUuid = UUID.randomUUID().toString();
		this.customerType = CustomerType.PERSON;
	}
	

	/**
	 * Constructor for mock, can be removed  
	 * 
	 * @param id
	 */
	public Customer(Long id) {

	}


	public Customer(ContractDetail contractDetail, App app) {
		super();
		this.name = contractDetail.getLoanCustomerName();
		this.account = contractDetail.getIDCardNo();
		this.source = contractDetail.getLoanCustomerNo();
		this.app = app;
		this.customerUuid = UUID.randomUUID().toString();
		this.customerType = CustomerType.PERSON;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @return the app
	 */
	public App getApp() {
		return app;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @param app the app to set
	 */
	public void setApp(App app) {
		this.app = app;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
}
