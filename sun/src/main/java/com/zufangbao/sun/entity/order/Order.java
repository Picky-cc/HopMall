/**
 * 
 */
package com.zufangbao.sun.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import org.hibernate.annotations.Type;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;

/**
 * @author lute
 *
 */

@Entity
@Table(name = "rent_order")
public class Order {

	@Id
	@GeneratedValue
	private Long id;

	/** 收款编号 */
	private String orderNo;

	/** 总金额 */
	private BigDecimal totalRent;

	/** 收款时间 */
	private Date payoutTime;
	/** 修改时间 */
	private Date modifyTime;
	
	/** 资产评估明细Id列表 */
	private String userUploadParam;
	/** 贷款人 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Customer customer;
	
	/** 结算日 */
	@Type(type = "date")
	private Date dueDate;
	
	/** 创建时间 */
	private Date createTime;
	
	/**
	 * 信托计划id
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private FinancialContract financialContract;
	/** 应收资产 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AssetSet assetSet;
	/** 
	 * 结清状态 0:未结清,1:已结清
	 */
	@Enumerated(EnumType.ORDINAL)
	private OrderClearStatus clearingStatus;
	/** 
	 * 扣款执行状态 0:已创建,1:处理中,2:扣款成功,3:扣款失败
	 */
	@Enumerated(EnumType.ORDINAL)
	private ExecutingSettlingStatus executingSettlingStatus;
	/**
	 * 收款单类型 0:结算单,1:商户担保单
	 */
	@Enumerated(EnumType.ORDINAL)
	private OrderType orderType;
	
	/** 回款唯一编号*/
	private String repaymentBillId;
	/**
	 * 备注
	 */
	public static Map<String,String>  filedNameCorrespondStringName;
	static{
		filedNameCorrespondStringName = new HashMap<String, String>();
		filedNameCorrespondStringName.put("totalRent", "总金额");
		filedNameCorrespondStringName.put("comment", "备注");
		filedNameCorrespondStringName.put("userUploadParam", "资产评估明细Id列表");
	}
	private String comment;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getTotalRent() {
		return totalRent;
	}

	public void setTotalRent(BigDecimal totalRent) {
		this.totalRent = totalRent;
	}

	public Date getPayoutTime() {
		return payoutTime;
	}

	public void setPayoutTime(Date payoutTime) {
		this.payoutTime = payoutTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserUploadParam() {
		return userUploadParam;
	}

	public void setUserUploadParam(String userUploadParam) {
		this.userUploadParam = userUploadParam;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public FinancialContract getFinancialContract() {
		return financialContract;
	}

	public void setFinancialContract(FinancialContract financialContract) {
		this.financialContract = financialContract;
	}

	public AssetSet getAssetSet() {
		return assetSet;
	}

	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}

	public OrderClearStatus getClearingStatus() {
		return clearingStatus;
	}

	public void setClearingStatus(OrderClearStatus clearingStatus) {
		this.clearingStatus = clearingStatus;
	}

	public ExecutingSettlingStatus getExecutingSettlingStatus() {
		return executingSettlingStatus;
	}

	public void setExecutingSettlingStatus(
			ExecutingSettlingStatus executingSettlingStatus) {
		this.executingSettlingStatus = executingSettlingStatus;
	}

	public String getRepaymentBillId() {
		return repaymentBillId;
	}

	public void setRepaymentBillId(String repaymentBillId) {
		this.repaymentBillId = repaymentBillId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Order() {
		this.createTime = new Date();
	}

	public Order(BigDecimal totalRent, String userUploadParam, AssetSet assetSet,Customer customer, FinancialContract financialContract, Date executeDate) {
		super();
		this.orderNo = GeneratorUtils.generateJSOrderNo();
		this.totalRent = totalRent;
		this.userUploadParam = userUploadParam;
		this.dueDate = DateUtils.asDay(executeDate);
		this.assetSet = assetSet;
		this.createTime = new Date();
		this.modifyTime = new Date();
		this.clearingStatus = OrderClearStatus.UNCLEAR;
		this.executingSettlingStatus = ExecutingSettlingStatus.CREATE;
		this.customer = customer;
		this.financialContract = financialContract;
		this.repaymentBillId = UUID.randomUUID().toString().replace("-", "");
		this.orderType = OrderType.NORMAL;
	}
	
	public void updateStatus(OrderClearStatus clearingStatus, ExecutingSettlingStatus executingSettlingStatus,Date clearTime){
		this.clearingStatus = clearingStatus;
		this.executingSettlingStatus = executingSettlingStatus;
		this.payoutTime = clearTime;
		this.modifyTime = new Date();
	}
	
	public void updateClearStatus(Date clearTime, boolean clearResult){
		if(clearResult){
			updateStatus(OrderClearStatus.CLEAR, ExecutingSettlingStatus.SUCCESS, clearTime);
		} else{
			updateStatus(OrderClearStatus.UNCLEAR, ExecutingSettlingStatus.FAIL, null);
		}
	}
	
	/* jsp方法 start  */
	public String getSingleLoanContractNo(){
		if(assetSet==null){
			return StringUtils.EMPTY;
		}
		return assetSet.getSingleLoanContractNo();
	}
	public Date getAssetRecycleDate(){
		if(assetSet==null){
			return null;
		}
		return assetSet.getAssetRecycleDate();
	}
	public String getCustomerNo(){
		if(assetSet==null){
			return StringUtils.EMPTY;
		}
		Contract contract = assetSet.getContract();
		if(contract==null){
			return StringUtils.EMPTY;
		}
		Customer customer = contract.getCustomer();
		if(customer==null){
			return StringUtils.EMPTY;
		}
		return customer.getSource();
	}
	public BigDecimal getAssetInitialValue(){
		if(assetSet==null){
			return BigDecimal.ZERO;
		}
		return assetSet.getAssetInitialValue();
	}
	public BigDecimal getPenaltyAmount(){
		BigDecimal assetInitialValue = getAssetInitialValue();
		BigDecimal totalAmount = getTotalRent();
		if(totalAmount==null){
			totalAmount= BigDecimal.ZERO;
		}
		if(assetInitialValue==null){
			assetInitialValue = BigDecimal.ZERO;
		}
		return totalAmount.subtract(assetInitialValue);
	}
	public Integer getNumbersOfOverdueDays(){
		if(assetSet==null){
			return null;
		}
		Date assetRecycleDate = assetSet.getAssetRecycleDate();
		if(assetRecycleDate==null || dueDate==null){
			return null;
		}
		Integer days = DateUtils.compareTwoDatesOnDay(dueDate,assetRecycleDate);
		return days>=0?days:0;
	}
	public Integer getNumbersOfGuranteeDueDays(){
		if(assetSet==null){
			return null;
		}
		if(dueDate==null){
			return null;
		}
		Integer days = null;
		if(assetSet.getGuaranteeStatus()==GuaranteeStatus.HAS_GUARANTEE){
			if(payoutTime==null) return null;
			days = DateUtils.compareTwoDatesOnDay(payoutTime,dueDate);
		} else {
			days = DateUtils.compareTwoDatesOnDay(new Date(),dueDate);
		}
		return days>=0?days:0;
		
	}
	/* jsp方法 end  */

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	private Order(AssetSet assetSet, Customer customer, FinancialContract financialContract) {
		super();
		this.orderNo = GeneratorUtils.generateGuaranteeOrderNo();
		this.totalRent = assetSet.getAssetInitialValue();
		this.userUploadParam = "";
		this.dueDate = getGuaranteeOrderDueDay(new Date(), financialContract);
		this.assetSet = assetSet;
		this.createTime = new Date();
		this.modifyTime = new Date();
		this.clearingStatus = OrderClearStatus.UNCLEAR;
		this.executingSettlingStatus = ExecutingSettlingStatus.CREATE;
		this.customer = customer;
		this.financialContract = financialContract;
		this.repaymentBillId = UUID.randomUUID().toString().replace("-", "");
		this.orderType = OrderType.GUARANTEE;
	}
	
	public Order(Order order) {
		this.repaymentBillId = order.repaymentBillId;
		this.orderNo = order.getOrderNo();
		this.totalRent = order.totalRent;
		this.comment = order.comment;
	}

	private Date getGuaranteeOrderDueDay(Date overdueDay, FinancialContract financialContract) {
		return DateUtils.addDaysNotIncludingWeekend(overdueDay, financialContract.getAdvaMatuterm());
	}

	public boolean isNormalOrder() {
		return orderType == OrderType.NORMAL;
	}

	public boolean isGuaranteeOrder() {
		return orderType == OrderType.GUARANTEE;
	}
	
	public boolean isEditable(){
		return getExecutingSettlingStatus()==ExecutingSettlingStatus.CREATE && DateUtils.isSameDay(new Date(), getDueDate());
	}

	public static Order createGuaranteeOrder(AssetSet assetSet, Customer customer, FinancialContract financialContract) {
		return new Order(assetSet, customer, financialContract);
	}
	public String getUuid(){
		return this.repaymentBillId;
	}
	
}