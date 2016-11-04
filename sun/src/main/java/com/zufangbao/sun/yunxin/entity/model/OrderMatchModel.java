package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;

/**
 * 收款单关联匹配
 * @author louguanyang
 *
 */
public class OrderMatchModel {
	/**
	 * 来源单号
	 */
	private String orderNo;
	/**
	 * 还款期号
	 * 
	 */
	private String singleLoanContractNo;
	/**
	 * 应还日期
	 */
	private Date assetRecycleDate;
	/**
	 * 结算日期
	 */
	private Date settlementDate;
	/**
	 * 客户编号
	 */
	private String customerNo;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 应还本金
	 */
	private BigDecimal assetPrincipalValue;
	/**
	 * 应还利息
	 */
	private BigDecimal assetInterestAmount;
	/**
	 * 逾期罚息
	 */
	private BigDecimal penaltyInterestAmount;
	/**
	 * 逾期天数
	 */
	private int overDueDays;
	/**
	 * 发生时间
	 */
	private Date modifyTime;
	/**
	 * 应还金额
	 */
	private BigDecimal amount;
	/**
	 * 已付金额
	 */
	private BigDecimal paidAmount;
	/**
	 * 状态
	 */
	private OfflineBillConnectionState status;
	/**
	 * 备注
	 */
	private String comment;

	public OrderMatchModel() {
		super();
	}
	public OrderMatchModel(Order order, BigDecimal paidAmount) {
		this.orderNo = order.getOrderNo();
		this.singleLoanContractNo = order.getSingleLoanContractNo();
		this.assetRecycleDate = order.getAssetRecycleDate();
		this.settlementDate = order.getDueDate();
		this.customerNo = order.getCustomerNo();
		Customer customer = order.getCustomer();
		if(customer!=null){
			this.customerName = customer.getName();
		}
		this.assetPrincipalValue = order.getAssetSet().getAssetPrincipalValue();
		this.assetInterestAmount = order.getAssetSet().getAssetInitialValue().subtract(assetPrincipalValue);
		this.penaltyInterestAmount = order.getPenaltyAmount();
		if(order.isNormalOrder()) {
			this.overDueDays = order.getNumbersOfOverdueDays();
		}
		if(order.isGuaranteeOrder()) {
			this.overDueDays = order.getNumbersOfGuranteeDueDays();
		}
		this.modifyTime = order.getModifyTime();
		this.amount = order.getTotalRent();
		this.paidAmount = paidAmount;
		if(BigDecimal.ZERO.compareTo(paidAmount) == 0) {
			this.status = OfflineBillConnectionState.NONE;
		}
		if(BigDecimal.ZERO.compareTo(paidAmount) == -1) {
			this.status = OfflineBillConnectionState.ALL;
			if(paidAmount.compareTo(amount) == -1) {
				this.status = OfflineBillConnectionState.PART;
			}
		}
		//FIXME:补充备注
		this.comment = "";		
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}
	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}
	public Date getAssetRecycleDate() {
		return assetRecycleDate;
	}
	public void setAssetRecycleDate(Date assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}
	public BigDecimal getAssetPrincipalValue() {
		return assetPrincipalValue;
	}
	public void setAssetPrincipalValue(BigDecimal assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}
	public BigDecimal getAssetInterestAmount() {
		return assetInterestAmount;
	}
	public void setAssetInterestAmount(BigDecimal assetInterestAmount) {
		this.assetInterestAmount = assetInterestAmount;
	}
	public BigDecimal getPenaltyInterestAmount() {
		return penaltyInterestAmount;
	}
	public void setPenaltyInterestAmount(BigDecimal penaltyInterestAmount) {
		this.penaltyInterestAmount = penaltyInterestAmount;
	}
	public int getOverDueDays() {
		return overDueDays;
	}
	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public OfflineBillConnectionState getStatus() {
		return status;
	}
	public void setStatus(OfflineBillConnectionState status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
