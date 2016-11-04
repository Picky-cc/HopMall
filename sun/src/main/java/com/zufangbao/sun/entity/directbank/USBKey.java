package com.zufangbao.sun.entity.directbank;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 银企直联 usbkey
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "usb_key")
public class USBKey {

	@Id
	@GeneratedValue
	private Long id;

	private String uuid;

	@Enumerated(EnumType.ORDINAL)
	private KeyType keyType;

	private String bankCode;

	@Column(columnDefinition = "text")
	private String config;

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

	public KeyType getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Map<String, String> getConfig() {
		if(StringUtils.isEmpty(this.config)) {
			return new HashMap<String, String>();
		}
		
		return JSON.parseObject(this.config, Map.class);
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean is_incorrectly_config() {
		return null == this || null == getBankCode();
	}

}
