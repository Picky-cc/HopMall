package com.zufangbao.sun.yunxin.entity.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_interface_modfify_overdue_fee_log")
public class ModifyOverdueFeeLog {

	@Id
	@GeneratedValue
	private  Long id;
	
	private  String  requestNo;
	
	private  String  overDueFeeData;
	
	private  Date    createTime;
	
	private  String  ipAddress;
	
	
	public ModifyOverdueFeeLog(){
		
	}

	public ModifyOverdueFeeLog(String requestNo2,
			String modifyOverDueFeeDetails, String ipAddress2) {

	this.requestNo = requestNo2;
	this.overDueFeeData = modifyOverDueFeeDetails;
	this.createTime = new Date();
	this.ipAddress = ipAddress2;
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getOverDueFeeData() {
		return overDueFeeData;
	}

	public void setOverDueFeeData(String overDueFeeData) {
		this.overDueFeeData = overDueFeeData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
