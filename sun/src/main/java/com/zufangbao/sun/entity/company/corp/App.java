/**
 * 
 */
package com.zufangbao.sun.entity.company.corp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.Company;

/**
 * @author lute
 *
 */

@Entity
@Table(name = "app")
public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1706437731251099523L;

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 合作商户名称
	 */
	private String name;
	/**
	 * 合作商户编号
	 */
	private String appId;
	/**
	 * 合作商户唯一标识
	 */
	private String appSecret;

	@Column(name = "is_disabled")
	private boolean disabled;
	/**
	 * 合作商户官网
	 */
	private String host;
	/**
	 * 合作商户公司
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Company company;

	private String addressee;
	
	/**
	 * default constructor
	 */
	public App() {

	}

	/**
	 * Initialize app secret
	 * 
	 * @param appSecret
	 * @return
	 */
	public App initialize(String appSecret, Company company) {
		this.appSecret = appSecret;
		this.company = company;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @param appSecret
	 *            the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

}
