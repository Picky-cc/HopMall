package com.zufangbao.sun.yunxin.entity.api.deduct;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="t_interface_deduct_application_log")
public class DeductApplicationLog {

	@Id
	@GeneratedValue
	private Long id;
	
	private String deductId;
	
	private String requestNo;
	
	private String contractUniqueId;
	
	private String contractNo;
	
	private Date   createTime; 
	
	private String  ip;
	


	public DeductApplicationLog(String deductId2, String contractNo2, String requestNo2, String uniqueId, String ip) {
		this.deductId = deductId2;
		this.requestNo = requestNo2;
		this.contractNo = contractNo2;
		this.contractUniqueId = uniqueId;
		this.ip = ip;
		this.createTime = new Date();
	}
	
	public DeductApplicationLog(){
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeductId() {
		return deductId;
	}

	public void setDeductId(String deductId) {
		this.deductId = deductId;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getContractUniqueId() {
		return contractUniqueId;
	}

	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
