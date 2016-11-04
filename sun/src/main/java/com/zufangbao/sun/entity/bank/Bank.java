package com.zufangbao.sun.entity.bank;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bank")
public class Bank  implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9195147932239987248L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String bankCode;
	
	private String bankName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
