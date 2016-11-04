package com.zufangbao.sun.entity.house;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;

@Entity
@Table(name = "house")
public class House {
	
	@Id
	@GeneratedValue
	private Long id;

	/** 车编号 */
	private String address;
	/**
	 * 所属商户
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public House(){
		
	}
	
	public House initial(App app){
		this.app=app;
		return this;
	}
	
	public House(App app) {
		this.app=app;
	}

	public House(ContractDetail contractDetail, App app) {
		this.app=app;
		if(!StringUtils.isEmpty(contractDetail.getSubjectMatterassetNo())){
		this.address = contractDetail.getSubjectMatterassetNo();
		}
	}
}
