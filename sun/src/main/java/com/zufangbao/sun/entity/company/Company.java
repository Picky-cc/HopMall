
package com.zufangbao.sun.entity.company;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "company")
public class Company implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 628154941109342437L;

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 公司简称
	 */
	private String shortName;
	/**
	 * 公司全名
	 */
	private String fullName;
	/**
	 * 公司地址
	 */
	private String address;
	
	
	private String uuid;
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	public Company(String shortName, String fullName) {
		super();
		this.shortName = shortName;
		this.fullName = fullName;
	}

	public Company() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
}
