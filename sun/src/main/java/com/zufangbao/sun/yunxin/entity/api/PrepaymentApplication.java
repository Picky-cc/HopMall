package com.zufangbao.sun.yunxin.entity.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.model.api.PrepaymentModifyModel;

/**
 * 提前还款请求记录
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "t_prepayment_application")
public class PrepaymentApplication {
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 贷款合同Id
	 */
	private Long contractId;
	/**
	 * 还款计划Id
	 */
	private Long assetSetId;
	/**
	 * 还款计划Uuid
	 */
	private String assetSetUuid;
	/**
	 * 贷款合同唯一标识
	 */
	private String uniqueId;
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	/**
	 * 请求编号
	 */
	private String requestNo;
	/**
	 * 计划提前还款时间
	 */
	private Date assetRecycleDate;
	/**
	 * 计划提前还款金额
	 */
	private String assetInitialValue;
	/**
	 * 提前还款类型 0:全部提前还款
	 */
	@Enumerated(EnumType.ORDINAL)
	private PrepaymentType type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 完成时间
	 */
	private Date completedTime;
	/**
	 * 请求IP地址
	 */
	private String ip;
	
	/**
	 * 提前还款状态 0:未还款，1：还款成功，2:还款失败
	 */
	@Enumerated(EnumType.ORDINAL)
	private PrepaymentStatus prepaymentStatus = PrepaymentStatus.UNFINISHED;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getAssetSetId() {
		return assetSetId;
	}

	public void setAssetSetId(Long assetSetId) {
		this.assetSetId = assetSetId;
	}

	public String getAssetSetUuid() {
		return assetSetUuid;
	}

	public void setAssetSetUuid(String assetSetUuid) {
		this.assetSetUuid = assetSetUuid;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Date getAssetRecycleDate() {
		return assetRecycleDate;
	}

	public void setAssetRecycleDate(Date assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}

	public String getAssetInitialValue() {
		return assetInitialValue;
	}

	public void setAssetInitialValue(String assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}

	public PrepaymentType getType() {
		return type;
	}

	public void setType(PrepaymentType type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public PrepaymentStatus getPrepaymentStatus() {
		return prepaymentStatus;
	}

	public void setPrepaymentStatus(PrepaymentStatus prepaymentStatus) {
		this.prepaymentStatus = prepaymentStatus;
	}

	public void setAssetSetId(Long assetSetId, String assetSetUuid) {
		this.assetSetId = assetSetId;
		this.assetSetUuid = assetSetUuid;
	}
	

	public PrepaymentApplication() {
		super();
	}

	public PrepaymentApplication(Long contractId, PrepaymentModifyModel model, String ip) {
		super();
		this.contractId = contractId;
		this.uniqueId = model.getUniqueId();
		this.contractNo = model.getContractNo();
		this.requestNo = model.getRequestNo();
		this.assetRecycleDate = model.getAssetRecycleDateValue();
		this.assetInitialValue = model.getAssetInitialValue();
		this.type = EnumUtil.fromOrdinal(PrepaymentType.class, model.getType());
		this.createTime = new Date();
		this.ip = ip;
	}

}
