package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.zufangbao.sun.utils.GeneratorUtils;

/**
 * 线下支付单
 * @author zhanghongbing
 *
 */
@Entity
public class OfflineBill {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String offlineBillUuid;
	/**
	 * 线下支付单编号
	 */
	private String offlineBillNo;
	/**
	 * 开户行名称
	 */
	private String bankShowName;
	/**
	 * 付款人姓名
	 */
	private String payerAccountName;
	/**
	 * 交易流水号
	 */
	private String serialNo;
	/**
	 * 付款人账号
	 */
	private String payerAccountNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 线下支付单状态 0:失败,1:成功 
	 */
	@Enumerated(EnumType.ORDINAL)
	private OfflineBillStatus offlineBillStatus;
	/**
	 * 备注
	 */
	private String comment;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 交易时间
	 */
	private Date tradeTime;
	/**
	 * 状态修改时间
	 */
	private Date statusModifiedTime;
	/**
	 * 最后修改时间
	 */
	private Date lastModifiedTime;
	/**
	 * 是否删除
	 */
	private boolean isDelete;
	
	@Transient
	private OfflineBillConnectionState offlineBillConnectionState;//关联状态
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOfflineBillUuid() {
		return offlineBillUuid;
	}
	public void setOfflineBillUuid(String offlineBillUuid) {
		this.offlineBillUuid = offlineBillUuid;
	}
	public String getOfflineBillNo() {
		return offlineBillNo;
	}
	public void setOfflineBillNo(String offlineBillNo) {
		this.offlineBillNo = offlineBillNo;
	}
	public String getBankShowName() {
		return bankShowName;
	}
	public void setBankShowName(String bankShowName) {
		this.bankShowName = bankShowName;
	}
	public String getPayerAccountName() {
		return payerAccountName;
	}
	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}
	public String getPayerAccountNo() {
		return payerAccountNo;
	}
	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public OfflineBillStatus getOfflineBillStatus() {
		return offlineBillStatus;
	}
	
	public String getOfflineBillStatusMsg() {
		return offlineBillStatus.getChineseMessage();
	}
	public void setOfflineBillStatus(OfflineBillStatus offlineBillStatus) {
		this.offlineBillStatus = offlineBillStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getStatusModifiedTime() {
		return statusModifiedTime;
	}
	public void setStatusModifiedTime(Date statusModifiedTime) {
		this.statusModifiedTime = statusModifiedTime;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public String getOfflineBillConnectionStateMsg() {
		if(offlineBillConnectionState != null) {
			offlineBillConnectionState.getChineseMessage();
		}
		return "";
	}
	
	public OfflineBillConnectionState getOfflineBillConnectionState() {
		return offlineBillConnectionState;
	}
	
	public void setOfflineBillConnectionState(OfflineBillConnectionState offlineBillConnectionState) {
		this.offlineBillConnectionState = offlineBillConnectionState;
	}
	
	public OfflineBill(){

	}
	
	public OfflineBill(String bankShowName, String payerAccountName, String serialNo,
			String payerAccountNo, BigDecimal amount,
			OfflineBillStatus offlineBillStatus, String comment,
			Date createTime, Date tradeTime, Date statusModifiedTime, Date lastModifiedTime,
			boolean isDelete) {
		super();
		this.offlineBillUuid = UUID.randomUUID().toString().replace("-", "");
		this.offlineBillNo = GeneratorUtils.generateOfflineBillNo();
		this.bankShowName = bankShowName;
		this.payerAccountName = payerAccountName;
		this.serialNo = serialNo;
		this.payerAccountNo = payerAccountNo;
		this.amount = amount;
		this.offlineBillStatus = offlineBillStatus;
		this.comment = comment;
		this.createTime = createTime;
		this.tradeTime = tradeTime;
		this.statusModifiedTime = statusModifiedTime;
		this.lastModifiedTime = lastModifiedTime;
		this.isDelete = isDelete;
	}
	
	public OfflineBill(String bankShowName, String payerAccountName, String serialNo, String payerAccountNo, BigDecimal amount, Date tradeTime, String comment){
		this(bankShowName,payerAccountName, serialNo, payerAccountNo, amount,
				OfflineBillStatus.PAID, comment,new Date(), tradeTime, new Date(), new Date(), false);
	}
	public String getUuid(){
		return this.offlineBillUuid;
	}
	
}
