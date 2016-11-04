package com.zufangbao.sun.yunxin.entity.model.vo.welcom;

import java.util.Date;

/**
 * 放款数据统计
 * 
 * @author zhanghongbing
 *
 */
public class RemittanceDataStatistic {

	private Date countDate;

	/**
	 * 处理中放款计划数
	 */
	private int processingRemittanceApplicationNums;

	/**
	 * 异常放款计划数
	 */
	private int abnormalRemittanceApplicationNums;

	/**
	 * 失败放款计划数
	 */
	private int failedRemittanceApplicationNums;
	
	public int getTotalNums() {
		return processingRemittanceApplicationNums
				+ abnormalRemittanceApplicationNums
				+ failedRemittanceApplicationNums;
	}

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public int getProcessingRemittanceApplicationNums() {
		return processingRemittanceApplicationNums;
	}

	public void setProcessingRemittanceApplicationNums(
			int processingRemittanceApplicationNums) {
		this.processingRemittanceApplicationNums = processingRemittanceApplicationNums;
	}

	public int getAbnormalRemittanceApplicationNums() {
		return abnormalRemittanceApplicationNums;
	}

	public void setAbnormalRemittanceApplicationNums(
			int abnormalRemittanceApplicationNums) {
		this.abnormalRemittanceApplicationNums = abnormalRemittanceApplicationNums;
	}

	public int getFailedRemittanceApplicationNums() {
		return failedRemittanceApplicationNums;
	}

	public void setFailedRemittanceApplicationNums(
			int failedRemittanceApplicationNums) {
		this.failedRemittanceApplicationNums = failedRemittanceApplicationNums;
	}

	public RemittanceDataStatistic(int processingRemittanceApplicationNums,
			int abnormalRemittanceApplicationNums,
			int failedRemittanceApplicationNums) {
		super();
		this.processingRemittanceApplicationNums = processingRemittanceApplicationNums;
		this.abnormalRemittanceApplicationNums = abnormalRemittanceApplicationNums;
		this.failedRemittanceApplicationNums = failedRemittanceApplicationNums;
		this.countDate = new Date();
	}

	public RemittanceDataStatistic() {
		super();
		this.countDate = new Date();
	}
	
}
