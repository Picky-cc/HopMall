package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncStatus;

@Entity
@Table(name = "asset_set")
public class AssetSet{

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * UUID
	 */
	private String assetUuid;
	/**
	 * 贷款合同
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Contract contract;
	/**
	 * 单笔还款编号
	 */
	private String singleLoanContractNo;
	/**
	 * 资产初始价值
	 */
	private BigDecimal assetInitialValue = BigDecimal.ZERO;

	/**
	 * 本期资产本金
	 */
	private BigDecimal assetPrincipalValue = BigDecimal.ZERO;

	/**
	 * 本期资产利息
	 */
	private BigDecimal assetInterestValue = BigDecimal.ZERO;

	/**
	 * 资产公允值
	 */
	private BigDecimal assetFairValue = BigDecimal.ZERO;
	/**
	 * 资金状态: 0:未转结,1:已转结
	 */
	@Enumerated(EnumType.ORDINAL)
	private AssetClearStatus assetStatus;
	/**
	 * 挂账状态: 0:未挂账,1:已挂账,2:已核销
	 */
	@Enumerated(EnumType.ORDINAL)
	private OnAccountStatus onAccountStatus;
	/**
	 * 结转类型（0:正常,1:提前还款）
	 */
	@Enumerated(EnumType.ORDINAL)
	private RepaymentPlanType repaymentPlanType = RepaymentPlanType.NORMAL;

	/**
	 * 担保补足状态: 0:未发生,1:待补足,2:已补足,3:担保作废
	 */
	@Enumerated(EnumType.ORDINAL)
	private GuaranteeStatus guaranteeStatus;

	/**
	 * 担保结清状态: 0:未发生,1:申请清算,2:清算处理中,3:已清算
	 */
	@Enumerated(EnumType.ORDINAL)
	private SettlementStatus settlementStatus = SettlementStatus.NOT_OCCURRED;

	/**
	 * 最后评估时间
	 */
	private Date lastValuationTime;
	/**
	 * 计划资产回收日期
	 */
	@Type(type = "date")
	private Date assetRecycleDate;
	/**
	 * 资产实际回收时间
	 */
	private Date actualRecycleDate;
	/**
	 * 人工确认到账日期
	 */
	@Type(type = "date")
	private Date confirmRecycleDate;
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount = BigDecimal.ZERO;
	/**
	 * 当前期数
	 */
	private int currentPeriod;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	private Date lastModifiedTime;
	/**
	 * 备注
	 */
	@Column(columnDefinition = "text")
	private String Comment;

	/**
	 * 逾期状态（0:正常，1:待确认，2:已逾期）
	 */
	@Enumerated(EnumType.ORDINAL)
	private AuditOverdueStatus overdueStatus = AuditOverdueStatus.NORMAL;
	
	/**
	 * 逾期日期
	 */
	@Type(type = "date")
	private Date overdueDate;
	
	/**
	 * 还款计划版本号
	 */
	private Integer versionNo;
	
	/**
	 *  0 未同步 1已同步
	 */
	@Enumerated(EnumType.ORDINAL)
	private DataSyncStatus syncStatus = DataSyncStatus.NO_SYNC;
	
	/**
	 * 有效状态 0:开启,1:作废
	 */
	@Enumerated(EnumType.ORDINAL)
	private AssetSetActiveStatus activeStatus;
	
	

	
	
	
	public AuditOverdueStatus getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(AuditOverdueStatus overdueStatus) {
		this.overdueStatus = overdueStatus;
	}

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public AssetSet() {
		this.assetUuid = UUID.randomUUID().toString();
	}
	
	public AssetSet(Contract contract, NFQRepaymentPlan repaymentPlan, int currentPeriod) {
		this(contract, currentPeriod, DateUtils.asDay(repaymentPlan.getAssetRecycleDate()), repaymentPlan.getAssetPrincipalValueBigDecimal(), repaymentPlan.getAssetInterestValueBigDecimal(),BigDecimal.ZERO);
	}

	public AssetSet(Contract contract, int currentPeriod, Date assetRecycleDate, BigDecimal assetPrincipalValue, BigDecimal assetInterestValue, BigDecimal theAdditionalFee) {
		this.assetUuid = UUID.randomUUID().toString();
		this.singleLoanContractNo = GeneratorUtils.generateAssetSetNo();
		this.contract = contract;
		this.assetStatus = AssetClearStatus.UNCLEAR;
		this.onAccountStatus = OnAccountStatus.ON_ACCOUNT;
		this.guaranteeStatus = GuaranteeStatus.NOT_OCCURRED;
		this.settlementStatus = SettlementStatus.NOT_OCCURRED;
		this.assetRecycleDate = assetRecycleDate;
		this.currentPeriod = currentPeriod;
		this.assetPrincipalValue =assetPrincipalValue;
		this.assetInterestValue = assetInterestValue;
		BigDecimal assetInitialValue = assetPrincipalValue.add(assetInterestValue);
		this.assetInitialValue = assetInitialValue;
		this.assetFairValue = assetInitialValue.add(theAdditionalFee);
		this.createTime = new Date();
		this.lastModifiedTime = new Date();
		this.activeStatus = AssetSetActiveStatus.OPEN;
		this.syncStatus = DataSyncStatus.NO_SYNC;
		this.versionNo = contract.getActiveVersionNo();
	}



	public SettlementStatus getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(SettlementStatus settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetUuid() {
		return assetUuid;
	}

	public void setAssetUuid(String assetUuid) {
		this.assetUuid = assetUuid;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}

	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}

	public BigDecimal getAssetFairValue() {
		return assetFairValue;
	}

	public void setAssetFairValue(BigDecimal assetFairValue) {
		this.assetFairValue = assetFairValue;
	}

	public AssetClearStatus getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(AssetClearStatus assetStatus) {
		this.assetStatus = assetStatus;
	}

	public Date getAssetRecycleDate() {
		return assetRecycleDate;
	}

	public void setAssetRecycleDate(Date assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
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

	public Date getActualRecycleDate() {
		return actualRecycleDate;
	}

	public void setActualRecycleDate(Date actualRecycleDate) {
		this.actualRecycleDate = actualRecycleDate;
	}

	public OnAccountStatus getOnAccountStatus() {
		return onAccountStatus;
	}

	public void setOnAccountStatus(OnAccountStatus onAccountStatus) {
		this.onAccountStatus = onAccountStatus;
	}

	public RepaymentPlanType getRepaymentPlanType() {
		return repaymentPlanType;
	}

	public void setRepaymentPlanType(RepaymentPlanType repaymentPlanType) {
		this.repaymentPlanType = repaymentPlanType;
	}

	public BigDecimal getAssetInitialValue() {
		return assetInitialValue;
	}

	public void setAssetInitialValue(BigDecimal assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}

	public BigDecimal getAssetInterestValue() {
		return assetInterestValue == null ? BigDecimal.ZERO : assetInterestValue;
	}

	public void setAssetInterestValue(BigDecimal assetInterestValue) {
		this.assetInterestValue = assetInterestValue;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public int getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Date getLastValuationTime() {
		return lastValuationTime;
	}

	public void setLastValuationTime(Date lastValuationTime) {
		this.lastValuationTime = lastValuationTime;
	}

	public Date getConfirmRecycleDate() {
		return confirmRecycleDate;
	}

	public void setConfirmRecycleDate(Date confirmRecycleDate) {
		this.confirmRecycleDate = confirmRecycleDate;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}
	
	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public AssetSetActiveStatus getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(AssetSetActiveStatus activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * 当前资产是否为当日应收
	 * 
	 * @param valuation_date
	 * @return
	 */
	public boolean isAssetRecycleDate(Date valuation_date) {
		return assetStatus.ordinal() == AssetClearStatus.UNCLEAR.ordinal()
				&& DateUtils.isSameDay(assetRecycleDate, valuation_date);
	}

	/**
	 * 当前资产是否逾期未收
	 * 
	 * @param valuation_date_time
	 * @return
	 */
	public boolean isOverdueDate(Date valuation_date_time) {
		Date valuation_date = DateUtils.asDay(valuation_date_time);
		return assetStatus.ordinal() == AssetClearStatus.UNCLEAR.ordinal()
				&& assetRecycleDate.before(valuation_date);
	}

	public boolean isClearAssetSet() {
		return assetStatus.ordinal() == AssetClearStatus.CLEAR.ordinal();
	}

	public void update_asset_fair_value_and_valution_time(BigDecimal amount) {
		this.setAssetFairValue(amount);
		this.setLastModifiedTime(new Date());
		this.setLastValuationTime(new Date());
	}

	public void updateStatus(AssetClearStatus assetStatus,
			OnAccountStatus onAccountStatus, Date acturalRecycleTime) {
		this.assetStatus = assetStatus;
		this.onAccountStatus = onAccountStatus;
		this.actualRecycleDate = acturalRecycleTime;
		if(this.confirmRecycleDate ==null){
			this.confirmRecycleDate = acturalRecycleTime;
		}
		this.setLastModifiedTime(new Date());
	}

	public void updateClearStatus(Date acturalRecycleTime, boolean clearResult) {
		if (clearResult) {
			updateStatus(AssetClearStatus.CLEAR, OnAccountStatus.WRITE_OFF, acturalRecycleTime);
		} else {
			updateStatus(AssetClearStatus.UNCLEAR, OnAccountStatus.ON_ACCOUNT, null);
		}
	}

	public String getContractNo() {
		return getContract().getContractNo();
	}

	public String getAssetNo() {
		return getContract().getHouse().getAddress();
	}

	public String getCustomerNo() {
		return getContract().getCustomer().getSource();
	}

	public int getOverDueDays() {
		Date end = getActualRecycleDate();
		if (getActualRecycleDate() == null) {
			end = new Date();
		}
		int overDueDay = DateUtils.compareTwoDatesOnDay(end, assetRecycleDate);
		return overDueDay > 0 ? overDueDay : 0;
	}

	public boolean getOverDueDayStatus() {
		return getOverDueDays() > 0;
	}
	
	public BigDecimal getAmount() {
		return getAssetFairValue();
	}

	public BigDecimal getPenaltyInterestAmount() {
		BigDecimal penaltyInterestAmount = getAmount().subtract(
				getAssetInitialValue());
		if (penaltyInterestAmount.compareTo(BigDecimal.ZERO) == 1) {
			return penaltyInterestAmount;
		}
		return BigDecimal.ZERO;
	}

	public GuaranteeStatus getGuaranteeStatus() {
		return guaranteeStatus;
	}
	
	public String getGuaranteeStatusMsg() {
		return guaranteeStatus==null?"":guaranteeStatus.getChineseMessage();
	}
	
	public String getSettlementStatusMsg() {
		return settlementStatus==null?"":settlementStatus.getChineseMessage();
	}

	public void setGuaranteeStatus(GuaranteeStatus guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}
	
	public String getGuaranteeStatusString(){
		switch (guaranteeStatus) {
		case NOT_OCCURRED:
			return "未发生";
			
		case WAITING_GUARANTEE:
			return "待补足";
		case HAS_GUARANTEE:
			return "已补足";
		case LAPSE_GUARANTEE:
			return "担保作废";
		default:
			return "";
		}
	}
	

	public void updateGuaranteeStatus() {
		this.setGuaranteeStatus(GuaranteeStatus.WAITING_GUARANTEE);
		this.setLastModifiedTime(new Date());
	}
	
	public void updateGuranteeStatus(boolean hasGuranteed){
		if(hasGuranteed){
			this.setGuaranteeStatus(GuaranteeStatus.HAS_GUARANTEE);
		}else {
			this.setGuaranteeStatus(GuaranteeStatus.WAITING_GUARANTEE);
		}
	}
	
	public boolean guaranteeStatusCanBeLapsed(){
		return this.guaranteeStatus==GuaranteeStatus.WAITING_GUARANTEE;
	}
	
	public boolean isGuaranteeStatusLapsed(){
		return this.guaranteeStatus==GuaranteeStatus.LAPSE_GUARANTEE;
	}

	public BigDecimal getAssetPrincipalValue() {
		return assetPrincipalValue == null ? BigDecimal.ZERO : assetPrincipalValue;
	}

	public void setAssetPrincipalValue(BigDecimal assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}
	
	public boolean isUnavailable() {
		return contract == null || contract.getCustomer() == null || contract.getApp() == null;
	}

	public BigDecimal calc_daily_penalty_interest_amount(Date valuationDate, int loanOverdueStartDay) {
		if(is_in_Interest_free_period(valuationDate, assetRecycleDate, loanOverdueStartDay)) {
			return BigDecimal.ZERO;
		}
		return calc_penalty_interest();
	}

	private BigDecimal calc_penalty_interest() {
		//FIMME:罚息计算
		BigDecimal asset_init_value = getAssetInitialValue();
		BigDecimal penaltyInterest = getContract().getPenaltyInterest();
		BigDecimal amount = asset_init_value.multiply(penaltyInterest);
		return amount;
	}

	public boolean is_in_Interest_free_period(Date valuationDate, Date assetRecycleDate, int loanOverdueStartDay) {
		return DateUtils.distanceDaysBetween(assetRecycleDate, valuationDate) < loanOverdueStartDay;
	}
	
	public PaymentStatus getPaymentStatus() {
		if(this.isClearAssetSet()) {
			return PaymentStatus.SUCCESS;
		}else {
			int todayCompareToAssetRecyleDate = DateUtils.compareTwoDatesOnDay(new Date(), this.assetRecycleDate);
			if (todayCompareToAssetRecyleDate < 0) {
				return PaymentStatus.DEFAULT;
			}
			if (todayCompareToAssetRecyleDate == 0) {
				return PaymentStatus.PROCESSING;
			}
			return PaymentStatus.UNUSUAL;
		}
	}

	public int getAuditOverdueDays() {
		if (getOverdueStatus() == AuditOverdueStatus.OVERDUE) {
			Date endDate = DateUtils.getToday();
			if (isClearAssetSet()) {
				endDate = getActualRecycleDate();
			}
			return DateUtils.distanceDaysBetween(getOverdueDate(), endDate) + 1;
		}
		return 0;
	}
	
	//在开启状态下的判断
	public boolean isPaidOff() {
		return this.getAssetStatus() == AssetClearStatus.CLEAR&& this.getOnAccountStatus() == OnAccountStatus.WRITE_OFF;
	}
	
	/**
	 * 获取物理逾期起始日
	 * @param financialContract 信托合同
	 * @return
	 */
	public Date getOverdueStartDate(FinancialContract financialContract) {
		Date assetRecycleDate = getAssetRecycleDate();
		int loanOverdueStartDay = financialContract.getLoanOverdueStartDay();
		return DateUtils.addDays(assetRecycleDate, loanOverdueStartDay);
		
	}


	
	public BigDecimal calcRepaymentDetailTotalAmount(BigDecimal assetSetTotalExtraCharge){
		
		BigDecimal amountPart1 = this.assetPrincipalValue.add(this.assetInterestValue);
		BigDecimal amoountPart2 = assetSetTotalExtraCharge;
		return amountPart1.add(amoountPart2);
	}

	public DataSyncStatus getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(DataSyncStatus syncStatus) {
		this.syncStatus = syncStatus;
	}
	
	
	
}
