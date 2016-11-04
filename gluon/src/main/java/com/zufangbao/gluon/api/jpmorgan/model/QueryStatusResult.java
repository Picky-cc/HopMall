package com.zufangbao.gluon.api.jpmorgan.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易结果状态
 * @author zhanghongbing
 *
 */
public class QueryStatusResult {

	/** 上送交易号 **/
	private String sourceMessageUuid;

	/** 交易planUuid **/
	private String transactionUuid;
	
	/** 对端交易唯一编号 **/
	private String tradeUuid;

	/** 通道账户名 **/
	private String channelAccountName;

	/** 通道账户号 **/
	private String channelAccountNo;

	private String destinationAccountName;

	private String destinationAccountNo;

	private BigDecimal transactionAmount;

	/** 交易状态：0: 队列中，1:处理中，2:对端处理中，3:成功，4:失败，5:撤销**/
	private BusinessStatus businessStatus;

	/** 支付成功通道uuid **/
	private String paymentChannelUuid;

	/** 交易失败原因 **/
	private String businessResultMsg;

	/** 交易成功时间 **/
	private Date businessSuccessTime;

	/** 交易流水号 **/
	private String channelSequenceNo;

	public QueryStatusResult() {
		super();
	}

	public String getSourceMessageUuid() {
		return sourceMessageUuid;
	}

	public void setSourceMessageUuid(String sourceMessageUuid) {
		this.sourceMessageUuid = sourceMessageUuid;
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}
	
	public String getTradeUuid() {
		return tradeUuid;
	}

	public void setTradeUuid(String tradeUuid) {
		this.tradeUuid = tradeUuid;
	}

	public String getChannelAccountName() {
		return channelAccountName;
	}

	public void setChannelAccountName(String channelAccountName) {
		this.channelAccountName = channelAccountName;
	}

	public String getChannelAccountNo() {
		return channelAccountNo;
	}

	public void setChannelAccountNo(String channelAccountNo) {
		this.channelAccountNo = channelAccountNo;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public String getDestinationAccountNo() {
		return destinationAccountNo;
	}

	public void setDestinationAccountNo(String destinationAccountNo) {
		this.destinationAccountNo = destinationAccountNo;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BusinessStatus getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(BusinessStatus businessStatus) {
		this.businessStatus = businessStatus;
	}
	
	public boolean isFinish() {
		if(this.businessStatus == BusinessStatus.Success || this.businessStatus == BusinessStatus.Failed || this.businessStatus == BusinessStatus.Abandon) {
			return true;
		}
		return false;
	}
	
	@JSONField(serialize = false)
	public Integer convertToRemittanceExecutionStatus() {
		//0: 队列中，1:处理中，2:对端处理中 --> 1: 处理中
		if(this.businessStatus == BusinessStatus.Inqueue || this.businessStatus == BusinessStatus.Processing ||this.businessStatus == BusinessStatus.OppositeProcessing) {
			return 1;
		}
		//，3: 成功 --> 2: 成功
		if(this.businessStatus == BusinessStatus.Success ) {
			return 2;
		}
		//，4:失败 --> 3:失败
		if(this.businessStatus == BusinessStatus.Failed ) {
			return 3;
		}
		//，5:撤销 --> 5:撤销
		if(this.businessStatus == BusinessStatus.Abandon ) {
			return 5;
		}
		return null;
	}
	
	@JSONField(serialize = false)
	public Integer convertToDeductAppliationExecutionStatus() {
		//0: 队列中，1:处理中，2:对端处理中 --> 1: 处理中
		if(this.businessStatus == BusinessStatus.Inqueue || this.businessStatus == BusinessStatus.Processing ||this.businessStatus == BusinessStatus.OppositeProcessing) {
			return 1;
		}
		//，3: 成功 --> 2: 成功
		if(this.businessStatus == BusinessStatus.Success ) {
			return 2;
		}
		//，4:失败 --> 3:失败
		if(this.businessStatus == BusinessStatus.Failed ) {
			return 3;
		}
		//，5:撤销 --> 5:撤销
		if(this.businessStatus == BusinessStatus.Abandon ) {
			return 5;
		}
		return null;
	}
	

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String successPaymentChannel) {
		this.paymentChannelUuid = successPaymentChannel;
	}

	public String getBusinessResultMsg() {
		return businessResultMsg;
	}

	public void setBusinessResultMsg(String businessFailedReason) {
		this.businessResultMsg = businessFailedReason;
	}

	public Date getBusinessSuccessTime() {
		return businessSuccessTime;
	}

	public void setBusinessSuccessTime(Date businessSuccessTime) {
		this.businessSuccessTime = businessSuccessTime;
	}

	public String getChannelSequenceNo() {
		return channelSequenceNo;
	}

	public void setChannelSequenceNo(String channelSequenceNo) {
		this.channelSequenceNo = channelSequenceNo;
	}


}
