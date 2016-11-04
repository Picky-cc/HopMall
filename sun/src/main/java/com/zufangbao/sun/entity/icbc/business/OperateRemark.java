package com.zufangbao.sun.entity.icbc.business;


/**
 * 
 * @author zjm
 *
 */
public class OperateRemark {

	private Integer operateType;

	private String operateRemark;

	private String operateTime;

	private Long operatorId;

	/**
	 * @param operateType
	 * @param operateRemark
	 * @param operateTime
	 * @param operatorId
	 */
	public OperateRemark(Integer operateType, String operateRemark,
			String operateTime, Long operatorId) {
		super();
		this.operateType = operateType;
		this.operateRemark = operateRemark;
		this.operateTime = operateTime;
		this.operatorId = operatorId;
	}
	
	

	/**
	 * 
	 */
	public OperateRemark() {
		super();
	}



	/**
	 * @return the operateType
	 */
	public Integer getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType
	 *            the operateType to set
	 */
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the operateRemark
	 */
	public String getOperateRemark() {
		return operateRemark;
	}

	/**
	 * @param operateRemark
	 *            the operateRemark to set
	 */
	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

	/**
	 * @return the operateTime
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

}
