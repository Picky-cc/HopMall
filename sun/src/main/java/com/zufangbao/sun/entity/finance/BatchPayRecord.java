package com.zufangbao.sun.entity.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.utils.DateUtils;

/**
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "batch_pay_record")
public class BatchPayRecord {

	@Id
	@GeneratedValue
	private Long id;
	
	private String batchPayRecordUuid;
	/** 批量总金额 */
	private BigDecimal amount;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/** 银联唯一标示此次交易的queryId */
	private String serialNo;
	/** 上传第三方的请求编号 */
	private String requestNo;
	/** 扣款时请求的requestData */
	private String requestData;
	/** 扣款时返回的responseData */
	private String responseData;
	/** 主动查询时返回的responseData */
	private String queryResponseData;
	/** 上传给银联的交易时间 */
	private String transDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatchPayRecordUuid() {
		return batchPayRecordUuid;
	}

	public void setBatchPayRecordUuid(String batchPayRecordUuid) {
		this.batchPayRecordUuid = batchPayRecordUuid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getQueryResponseData() {
		return queryResponseData;
	}

	public void setQueryResponseData(String queryResponseData) {
		this.queryResponseData = queryResponseData;
	}

	public String getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}

	public BatchPayRecord() {
		super();
	}

	public BatchPayRecord(Long id, BigDecimal amount, Account receiveAccount,
			Date payTime, Date applyTime, Date modifyTime, Date createTime,String serialNo,
			String requestNo, String requestData,
			String responseData) {
		super();
		this.id = id;
		this.batchPayRecordUuid = UUID.randomUUID().toString().replace("-","");
		this.amount = amount;
		this.payTime = payTime;
		this.modifyTime = modifyTime;
		this.createTime = createTime;
		this.serialNo = serialNo;
		this.requestNo = requestNo;
		this.requestData = requestData;
		this.responseData = responseData;
	}
	public BatchPayRecord(BigDecimal amount, String requestNo){
		this(null, amount, null, null, null, null,new Date(), StringUtils.EMPTY, requestNo, StringUtils.EMPTY, StringUtils.EMPTY);
	}
	
	public void updateTranDateTimeAndResponseData(String transDateTime, String requestData, String responseData, String queryId) {
		this.transDateTime = transDateTime;
		this.requestData = requestData;
		this.responseData = responseData;
		this.serialNo = queryId;
		this.modifyTime = new Date();
	}
	public void updateAfterQueryUnion(String queryResponseData){
		this.queryResponseData =queryResponseData;
		this.modifyTime = new Date();
	}
	
	public Date getTransDateTimeDateValue() {
		return DateUtils.parseFullDateTimeToDate(transDateTime);
	}
	
}
