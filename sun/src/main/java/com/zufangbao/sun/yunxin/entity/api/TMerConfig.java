package com.zufangbao.sun.yunxin.entity.api;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.Company;

/**
 * api请求商户配置表
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "t_merchant_config")
public class TMerConfig {
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 商户编号
	 */
	private String merId;
	/**
	 * 商户唯一标识
	 */
	private String secret;

	/**
	 * 商户公司
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Company company;

	/**
	 * 商户公钥地址
	 */
	private String pubKeyPath;

	/**
	 * 商户公钥
	 */
	private String pubKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getPubKeyPath() {
		return pubKeyPath;
	}

	public void setPubKeyPath(String pubKeyPath) {
		this.pubKeyPath = pubKeyPath;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

}
