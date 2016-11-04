package com.zufangbao.earth.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zufangbao.sun.entity.financial.GateWay;

@Entity
@Table(name="request_record")
public class RequestRecord {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	private GateWay gateway; // 网关
	
	private String functionName; // 函数名
	
	private String reqNo; //唯一交易号（接受）
	
	private String transactionUuid;//唯一交易号（发送）
	
	@Column(columnDefinition = "text")
	private String originalReqPackage; //原始请求报文
	
	private String merchantId; //商户号
	
	private Date reqDate; // 请求时间
	
	private long longFieldOne;

	private long longFieldTwo;

	private long longFieldThree;

	private String stringFieldOne;

	private String stringFieldTwo;

	private String stringFieldThree;

	private BigDecimal decimalFieldOne;

	private BigDecimal decimalFieldTwo;

	private BigDecimal decimalFieldThree;

	public GateWay getGateway() {
		return gateway;
	}

	public void setGateway(GateWay gateway) {
		this.gateway = gateway;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getOriginalReqPackage() {
		return originalReqPackage;
	}

	public void setOriginalReqPackage(String originalReqPackage) {
		this.originalReqPackage = originalReqPackage;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public RequestRecord() {
		super();
	}

	public RequestRecord(GateWay gateway, String functionName, String reqNo, String transactionUuid,
			String originalReqPackage, String merchantId) {
		super();
		this.gateway = gateway;
		this.functionName = functionName;
		this.reqNo = reqNo;
		this.transactionUuid = transactionUuid;
		this.originalReqPackage = originalReqPackage;
		this.merchantId = merchantId;
	}
	
	public boolean isUnionpay(){
		return this.gateway.equals(GateWay.UnionPay);
	}
	
	public boolean isSuperBank(){
		return this.gateway.equals(GateWay.SuperBank);
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getLongFieldOne() {
		return longFieldOne;
	}

	public void setLongFieldOne(long longFieldOne) {
		this.longFieldOne = longFieldOne;
	}

	public long getLongFieldTwo() {
		return longFieldTwo;
	}

	public void setLongFieldTwo(long longFieldTwo) {
		this.longFieldTwo = longFieldTwo;
	}

	public long getLongFieldThree() {
		return longFieldThree;
	}

	public void setLongFieldThree(long longFieldThree) {
		this.longFieldThree = longFieldThree;
	}

	public String getStringFieldOne() {
		return stringFieldOne;
	}

	public void setStringFieldOne(String stringFieldOne) {
		this.stringFieldOne = stringFieldOne;
	}

	public String getStringFieldTwo() {
		return stringFieldTwo;
	}

	public void setStringFieldTwo(String stringFieldTwo) {
		this.stringFieldTwo = stringFieldTwo;
	}

	public String getStringFieldThree() {
		return stringFieldThree;
	}

	public void setStringFieldThree(String stringFieldThree) {
		this.stringFieldThree = stringFieldThree;
	}

	public BigDecimal getDecimalFieldOne() {
		return decimalFieldOne;
	}

	public void setDecimalFieldOne(BigDecimal decimalFieldOne) {
		this.decimalFieldOne = decimalFieldOne;
	}

	public BigDecimal getDecimalFieldTwo() {
		return decimalFieldTwo;
	}

	public void setDecimalFieldTwo(BigDecimal decimalFieldTwo) {
		this.decimalFieldTwo = decimalFieldTwo;
	}

	public BigDecimal getDecimalFieldThree() {
		return decimalFieldThree;
	}

	public void setDecimalFieldThree(BigDecimal decimalFieldThree) {
		this.decimalFieldThree = decimalFieldThree;
	}
	
}
