package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.SettlementStatus;

/**
 * 结清单
 * 
 * @author louguanyang
 *
 */
@Entity
@Table(name = "settlement_order")
public class SettlementOrder {

	@Id
	@GeneratedValue
	private Long id;
	/** 资产 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AssetSet assetSet;
	/** 清算单号 **/
	private String settleOrderNo;
	/** 担保单号 **/
	private String guaranteeOrderNo;
	/** 清算截止日 **/
	private Date dueDate;
	/** 逾期天数 **/
	private Integer overdueDays;
	/** 逾期罚息 **/
	private BigDecimal overduePenalty;
	/** 创建时间 **/
	private Date createTime;
	/** 最后修改时间 **/
	private Date lastModifyTime;
	/** 结清金额 **/
	private BigDecimal settlementAmount;

	private String settlementOrderUuid;

	/** 备注 **/
	private String comment;

	public SettlementOrder() {
		super();
	}

	public SettlementOrder(Order order) {
		super();
		AssetSet assetSet = order.getAssetSet();
		this.assetSet = assetSet;
		this.settleOrderNo = GeneratorUtils.generateSettlementOrderNo();
		this.guaranteeOrderNo = order.getOrderNo();
		Date assetRecycleDate = order.getAssetSet().getAssetRecycleDate();
		this.dueDate = DateUtils.addMonths(assetRecycleDate, 1);
		overdueDays = assetSet.getOverDueDays();
		overduePenalty = assetSet.getPenaltyInterestAmount();
		createTime = new Date();
		lastModifyTime = new Date();
		settlementAmount = order.getAssetSet().getAssetFairValue();
		settlementOrderUuid = UUID.randomUUID().toString().replace(Constant.ORDERNO_SPLIT_CHAR, "");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssetSet getAssetSet() {
		return assetSet;
	}

	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}

	public String getSettleOrderNo() {
		return settleOrderNo;
	}

	public void setSettleOrderNo(String settleOrderNo) {
		this.settleOrderNo = settleOrderNo;
	}

	public String getGuaranteeOrderNo() {
		return guaranteeOrderNo;
	}

	public void setGuaranteeOrderNo(String guaranteeOrderNo) {
		this.guaranteeOrderNo = guaranteeOrderNo;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BigDecimal getOverduePenalty() {
		return overduePenalty;
	}

	public void setOverduePenalty(BigDecimal overduePenalty) {
		this.overduePenalty = overduePenalty;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSettlementOrderUuid() {
		return settlementOrderUuid;
	}

	public void setSettlementOrderUuid(String settlementOrderUuid) {
		this.settlementOrderUuid = settlementOrderUuid;
	}

	public boolean isCreateSettlementOrder() {
		return getAssetSet().getSettlementStatus() == SettlementStatus.CREATE;
	}

	public boolean isWaitingSettlementOrder() {
		return getAssetSet().getSettlementStatus() == SettlementStatus.WAITING;
	}

}