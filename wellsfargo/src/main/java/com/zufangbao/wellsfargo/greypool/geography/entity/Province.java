/**
 * 
 */
package com.zufangbao.wellsfargo.greypool.geography.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author wukai
 *
 */
@Entity
public class Province {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String code;
	
	private String name;
	
	private boolean isDeleted;
	
	public Province() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
