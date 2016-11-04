package com.zufangbao.sun.entity.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.entity.DeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.PaymentWay;

/**
 * 转账申请
 * 
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "transfer_application")
public class TransferApplication{

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 扣款金额
	 */
	private BigDecimal amount;
	
	/**
	 * 转账申请单号
	 */
	private String transferApplicationNo;
	/**
	 * 转账申请创建人
	 */
	private Long creatorId;

	/**
	 * 转账申请备注
	 */
	private String comment;

	/**
	 * 批支付记录
	 */
	private Long batchPayRecordId;
	
	/**
	 * 支付方式 0:银联代扣,1:线下支付
	 */
	@Enumerated(EnumType.ORDINAL)
	private PaymentWay paymentWay;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后状态更新时间
	 */
	private Date lastModifiedTime;
	
	/** 上传给银联的唯一的订单号 */
	private String unionPayOrderNo;
	
	/** 扣款发起时间 */
	private Date deductSendTime;
	
	/** 扣款结果时间 */
	private Date deductTime;
	
	/** 
	 * 扣款结果状态 0:未扣款成功,1:已扣款成功
	 */
	@Enumerated(EnumType.ORDINAL)
	private DeductStatus deductStatus;
	
	/**
	 * 执行状态 0:已创建,1:扣款中:2,扣款成功,3扣款失败,4扣款超时
	 */
	@Enumerated(EnumType.ORDINAL)
	private ExecutingDeductStatus executingDeductStatus;
	
	/** 扣款账户 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ContractAccount contractAccount;
	/**
	 * 还款单
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Order order;
	
	private String transferApplicationUuid;
	
	/**
	 * 支付通道Uuid
	 */
	private String paymentChannelUuid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransferApplicationNo() {
		return transferApplicationNo;
	}

	public void setTransferApplicationNo(String transferApplicationNo) {
		this.transferApplicationNo = transferApplicationNo;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBatchPayRecordId() {
		return batchPayRecordId;
	}

	public void setBatchPayRecordId(Long batchPayRecordId) {
		this.batchPayRecordId = batchPayRecordId;
	}
	
	public String getPaymentWayMsg() {
		return this.paymentWay.getChineseMessage();
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeMonthStr(){
		return DateUtils.format(DateUtils.asDay(this.createTime), "yyyy-MM");
	}
	
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getUnionPayOrderNo() {
		return unionPayOrderNo;
	}

	public void setUnionPayOrderNo(String unionPayOrderNo) {
		this.unionPayOrderNo = unionPayOrderNo;
	}
	
	public Date getDeductSendTime() {
		return deductSendTime;
	}

	public void setDeductSendTime(Date deductSendTime) {
		this.deductSendTime = deductSendTime;
	}

	public Date getDeductTime() {
		return deductTime;
	}
	
	public String getDeductTimeAsDay() {
		return DateUtils.format(deductTime);
	}

	public void setDeductTime(Date deductTime) {
		this.deductTime = deductTime;
	}

	public DeductStatus getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(DeductStatus deductStatus) {
		this.deductStatus = deductStatus;
	}
	
	public String getExecutingDeductStatusMsg() {
		return this.executingDeductStatus.getChineseMessage();
	}

	public ExecutingDeductStatus getExecutingDeductStatus() {
		return executingDeductStatus;
	}

	public void setExecutingDeductStatus(ExecutingDeductStatus executingDeductStatus) {
		this.executingDeductStatus = executingDeductStatus;
	}

	public ContractAccount getContractAccount() {
		return contractAccount;
	}

	public void setContractAccount(ContractAccount contractAccount) {
		this.contractAccount = contractAccount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getTransferApplicationUuid() {
		return transferApplicationUuid;
	}

	public void setTransferApplicationUuid(String transferApplicationUuid) {
		this.transferApplicationUuid = transferApplicationUuid;
	}

	public TransferApplication(Long id, BigDecimal amount, Long creatorId,
			String comment, Long batchPayRecordId, Date createTime,
			Date lastModifiedTime, 
			Date deductTime, DeductStatus deductStatus,
			ExecutingDeductStatus executingDeductStatus,
			ContractAccount contractAccount, Order order,PaymentWay paymentWay) {
		super();
		this.id = id;
		this.amount = amount;
		this.creatorId = creatorId;
		this.comment = comment;
		this.batchPayRecordId = batchPayRecordId;
		this.createTime = createTime;
		this.lastModifiedTime = lastModifiedTime;
		this.deductTime = deductTime;
		this.deductStatus = deductStatus;
		this.executingDeductStatus = executingDeductStatus;
		this.contractAccount = contractAccount;
		this.order = order;
		this.paymentWay = paymentWay;
		this.transferApplicationNo = GeneratorUtils.generateUnionPayNo();
		this.unionPayOrderNo = transferApplicationNo;
		this.transferApplicationUuid = UUID.randomUUID().toString().replace("-", "");
	}
	
	public TransferApplication(BigDecimal amount, ContractAccount contractAccount, Order order, PaymentWay paymentWay){
		this(null, amount, null,
				StringUtils.EMPTY, null, new Date(),
				new Date(), 
				null, DeductStatus.UNCLEAR,
				ExecutingDeductStatus.CREATE,
				contractAccount, order,paymentWay);
	}
	
	public void updateStatus(DeductStatus deductStatus,ExecutingDeductStatus executingDeductStatus, String comment, Date deductTime){
		this.deductStatus = deductStatus;
		this.executingDeductStatus = executingDeductStatus;
		this.deductTime = deductTime;
		this.lastModifiedTime = new Date();
		this.comment= comment;
	}
	
	public void updateDeductStatus(boolean deductResult, String comment, Date deductTime){
		if(deductResult){
			updateStatus(DeductStatus.CLEAR, ExecutingDeductStatus.SUCCESS, comment, deductTime);
		} else{
			updateStatus(DeductStatus.UNCLEAR, ExecutingDeductStatus.FAIL, comment, deductTime);
		}
	}

	public TransferApplication() {
		super();
	}
	public String getUuid(){
		return this.transferApplicationUuid;
	}

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}
	
}



