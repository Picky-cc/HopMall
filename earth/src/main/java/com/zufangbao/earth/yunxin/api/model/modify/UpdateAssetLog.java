package com.zufangbao.earth.yunxin.api.model.modify;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.contract.Contract;

@Entity
@Table(name = "update_asset_log")
public class UpdateAssetLog {
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
	 * 请求原因
	 */
	private String requestReason;
	/**
	 * 请求变更数据
	 */
	private String requestData;
	/**
	 * 变更原始数据备份
	 */
	private String originData;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 请求IP地址
	 */
	private String ip;
	
	public UpdateAssetLog() {
		super();
	}
	
	public UpdateAssetLog(Contract contract, RepaymentPlanModifyModel modifyModel,
			String originData, String ip) {
		super();
		this.contract = contract;
		this.requestNo = modifyModel.getRequestNo();
		this.requestReason = modifyModel.getRequestReason();
		this.requestData = modifyModel.getRequestData();
		this.originData = originData;
		this.ip = ip;
		this.createTime = new Date();
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

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getOriginData() {
		return originData;
	}

	public void setOriginData(String originData) {
		this.originData = originData;
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
