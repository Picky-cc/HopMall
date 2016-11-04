package com.zufangbao.sun.yunxin.entity.api;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_api_config")
public class TApiConfig {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String apiUrl; //接口地址
	
	private String fnCode; //功能代码
	
	private String description; //接口说明
	
	@Enumerated(EnumType.ORDINAL)
	private ApiStatus apiStatus; //接口状态0 : 关闭，1 : 启用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getFnCode() {
		return fnCode;
	}

	public void setFnCode(String fnCode) {
		this.fnCode = fnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApiStatus getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

	public TApiConfig() {
		super();
	}
	
}
