package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

/**
 * 线上代付单导出模型
 * @author Administrator
 */
public class RemittancePlanExecLogExportModel {
	/**
	 * 交易上送请求号 -- 代付单号
	 */
	@ExcelVoAttribute(name = "代付单号", column = "A")
	private String execReqNo;
	
	@ExcelVoAttribute(name = "通道请求号", column = "B")
	private String execRspNo;
	
	/**
	 * 交易类型
	 */
//	@ExcelVoAttribute(name = "交易类型", column = "C")
	private String transactionType;

	/**
	 * 计划支付金额--放款金额
	 */
	@ExcelVoAttribute(name = "发生金额", column = "C")
	private BigDecimal plannedAmount;
	/**
	 * 状态变更时间
	 */
	@ExcelVoAttribute(name = "状态变更时间", column = "D")
	private String lastModifiedTime;
	/**
	 * 代付状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	@ExcelVoAttribute(name = "代付状态", column = "E")
	private String executionStatus;
	/**
	 * 冲账状态
	 */
	@ExcelVoAttribute(name = "冲账状态", column = "F")
	private String reverseStatus = "";
	/**
	 * 交易对手方银行卡号
	 */
	@ExcelVoAttribute(name = "收款方账号", column = "G")
	private String cpBankCardNo;
	/**
	 * 交易对手方证件号
	 */
	private String cpIdNumber;
	
	public String getExecReqNo() {
		return execReqNo;
	}
	public void setExecReqNo(String execReqNo) {
		this.execReqNo = execReqNo;
	}
	public String getExecRspNo() {
		return execRspNo;
	}
	public void setExecRspNo(String execRspNo) {
		this.execRspNo = execRspNo;
	}
	public BigDecimal getPlannedAmount() {
		return plannedAmount;
	}
	public void setPlannedAmount(BigDecimal plannedAmount) {
		this.plannedAmount = plannedAmount;
	}
	public String getCpBankCardNo() {
		return cpBankCardNo;
	}
	public void setCpBankCardNo(String cpBankCardNo) {
		this.cpBankCardNo = cpBankCardNo;
	}
	public String getCpIdNumber() {
		return cpIdNumber;
	}
	public void setCpIdNumber(String cpIdNumber) {
		this.cpIdNumber = cpIdNumber;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	public void setExecutionStatusUseEnum(ExecutionStatus executionStatus){
		this.executionStatus = executionStatus == null ? null : executionStatus.getChineseMessage();
	}
	public void setReverseStatusUseEnum(ReverseStatus reverseStatus){
		this.reverseStatus = reverseStatus == null ? null : reverseStatus.getChineseMessage();
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getReverseStatus() {
		return reverseStatus;
	}
	public void setReverseStatus(String reverseStatus) {
		this.reverseStatus = reverseStatus;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransactionType(AccountSide transactionType) {
		this.transactionType = transactionType == null ? "" : transactionType.getChineseMessage();
	}
	public RemittancePlanExecLogExportModel() {
		super();
	}
	public RemittancePlanExecLogExportModel(RemittancePlanExecLog execLog) {
		super();
		this.execReqNo = execLog.getExecReqNo();
		this.execRspNo = execLog.getExecRspNo();
		this.plannedAmount = execLog.getPlannedAmount();
		this.lastModifiedTime = execLog.getLastModifiedTime() == null ? StringUtils.EMPTY : DateUtils.format(execLog.getLastModifiedTime(), DateUtils.LONG_DATE_FORMAT);
		this.setReverseStatusUseEnum(execLog.getReverseStatus());
		this.cpBankCardNo = "\t"+execLog.getCpBankCardNo();
		this.cpIdNumber = execLog.getCpIdNumber();
		// this.setTransactionType(execLog.getTransactionType());
		this.setExecutionStatusUseEnum(execLog.getExecutionStatus());
	}

}