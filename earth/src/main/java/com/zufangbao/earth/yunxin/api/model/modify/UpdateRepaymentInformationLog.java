package com.zufangbao.earth.yunxin.api.model.modify;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.sun.entity.contract.Contract;


@Entity
@Table(name = "t_interface_repayment_information_log")
public class UpdateRepaymentInformationLog {
	
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 贷款合同
	 */
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private Contract contract;
	/**
	 * 请求编号
	 */
	private String requestNo;
	/**
	 * 请求数据
	 */
	private String requestData;
	/**
	 * 创建时间
	 */
	private Date  createTime;
	/**
	 * 请求IP地址
	 */
	private String ip;
	
	public UpdateRepaymentInformationLog(
			RepaymentInformationModifyModel modifyModel,
			HttpServletRequest request, Contract contract) {
		this.contract = contract;
		this.requestNo = modifyModel.getRequestNo();
		this.requestData = JsonUtils.toJsonString(modifyModel);
		this.createTime = new Date();
		this.ip =  IpUtil.getIpAddress(request);
		
	}
	public UpdateRepaymentInformationLog(){
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
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
