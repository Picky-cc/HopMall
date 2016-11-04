package com.zufangbao.sun.entity.contract;

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

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.ContractState;
import com.zufangbao.sun.yunxin.entity.AssetType;
import com.zufangbao.sun.yunxin.entity.RepaymentWay;

/**
 * 贷款合同
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "contract")
public class Contract {
	
	public static final int INITIAL_VERSION_NO = 1;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 信托合同uuid
	 */
	private String financialContractUuid;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	/**
	 * 贷款人
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Customer customer;
	/**
	 * 抵押物
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private House house;
	/**
	 * 所属商户
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;
	/**
	 * 贷款开始日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Type(type = "date")
	private Date beginDate;
	/**
	 * 贷款结束日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Type(type = "date")
	private Date endDate;
	/**
	 * 月还款额(等额本息)
	 */
	private BigDecimal monthFee = BigDecimal.ZERO;
	/**
	 * 资产类型 0:二手车,1:种子贷
	 */
	@Enumerated(EnumType.ORDINAL)
	private  AssetType assetType;
	/**
	 * 实际结束日
	 */
	private Date actualEndDate;
	/**
	 * 贷款总额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;
	/**
	 * 罚息利率
	 */
	private BigDecimal penaltyInterest = BigDecimal.ZERO;
	/**
	 * 贷款利率
	 */
	private BigDecimal interestRate = BigDecimal.ZERO;
	/**
	 * 回款方式: 0:等额本息,1:等额本金,2,锯齿型
	 */
	@Enumerated(EnumType.ORDINAL)
	private RepaymentWay repaymentWay;
	/**
	 * 贷款期数
	 */
	private int periods;
	/**
	 * 还款频率(月)
	 */
	private int paymentFrequency;
	/**
	 * 还款日
	 */
	private int paymentDayInMonth;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	/**
	 * 有效的还款计划版本号
	 */
	private Integer activeVersionNo;
	
	/**
	 * 还款计划操作日志JsonArray
	 */
	private String repaymentPlanOperateLogs;
	
	private String uuid;
	
	@Enumerated(EnumType.ORDINAL)
	private ContractState state;
	
	public String getStateMsg() {
		return getState() == null? "" : getState().getChineseMessage();
	}
	
	public ContractState getState() {
		return state;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getMonthFee() {
		return monthFee;
	}

	public void setMonthFee(BigDecimal monthFee) {
		this.monthFee = monthFee;
	}

	public AssetType getAssetType() {
		return assetType;
	}
	
	public String getAssetTypeMsg() {
		return getAssetType()==null?"":getAssetType().getChineseMessage();
	}


	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPenaltyInterest() {
		return penaltyInterest;
	}

	public void setPenaltyInterest(BigDecimal penaltyInterest) {
		this.penaltyInterest = penaltyInterest;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public RepaymentWay getRepaymentWay() {
		return repaymentWay;
	}
	
	public String getRepaymentWayMsg() {
		return getRepaymentWay().getChineseMessage();
	}

	public void setRepaymentWay(RepaymentWay repaymentWay) {
		this.repaymentWay = repaymentWay;
	}

	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}

	public int getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(int paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public int getPaymentDayInMonth() {
		return paymentDayInMonth;
	}

	public void setPaymentDayInMonth(int paymentDayInMonth) {
		this.paymentDayInMonth = paymentDayInMonth;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getActiveVersionNo() {
		return activeVersionNo;
	}

	public void setActiveVersionNo(int activeVersionNo) {
		this.activeVersionNo = activeVersionNo;
	}
	
	public String getRepaymentPlanOperateLogs() {
		return repaymentPlanOperateLogs;
	}

	public void setRepaymentPlanOperateLogs(String repaymentPlanOperateLogs) {
		this.repaymentPlanOperateLogs = repaymentPlanOperateLogs;
	}

	public Contract(){
		this.setUuid(UUID.randomUUID().toString());
		this.setState(ContractState.EFFECTIVE);
	}

	public boolean isAverageCapitalPlusInterest() {
		return this.getRepaymentWay() == RepaymentWay.AVERAGE_CAPITAL_PLUS_INTEREST;
	}
	
	public BigDecimal calcAverageCapitalPlusMonthFee() {

		BigDecimal calc = this.getInterestRate().add(BigDecimal.ONE)
				.pow(this.getPeriods());
		BigDecimal dividend = calc.multiply(this.getInterestRate())
				.multiply(this.getTotalAmount());
		BigDecimal divisor = calc.subtract(BigDecimal.ONE);
		return dividend.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
	}

	public void endContract() {
		Date today = DateUtils.asDay(new Date());
		this.setEndDate(today);
		this.actualEndDate = today;
		this.setState(ContractState.INVALIDATE);
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}
	
}
