package com.zufangbao.wellsfargo.greypool.document.entity.busidocument.bill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.FinanceUtils;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.utils.ObjectCastUtils;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.dictionary.Currency;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SettlementModes;

public class BillingPlan {
	// removed some fields
	private String _id;

	private String billingPlanUuid;

	private String billingPlanName;

	private String billingPlanNumber;

	private Long companyId;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 货币代码
	 */
	private int currency;

	/**
	 * 交易对手
	 */
	private String tradePartyUuid;

	/**
	 * 费用类型      注意两者差别
	 */
	private String billingPlanType;
	

	/**
	 * 账单类型
	 */
	private String entryType;

	/**
	 * 借贷标记
	 */
	private int accountSide;


	/**
	 * 订单的组的UUID
	 */
	private String billEnvelopeUuid;

	/**
	 * 执行的paymentClause的UUID
	 */
	private String applyingPaymentClauseUuid;

	/**
	 * 签发时间
	 */
	private Date issueTime;

	/**
	 * 生效时间
	 */
	private Date effectiveTime;
	/**
	 * 结束时间
	 */
	private Date maturityTime;

	private Date writtenOffTime;

	/**
	 * 交易开始点
	 */
	private Date tradeStartPoint;

	/**
	 * 应收单的生命周期
	 */
	private int lifeCycle;

	/**
	 * 服务通知的状态
	 */
	private int billServiceNoticeStatus;

	/**
	 * 冻结状态
	 */
	private int billFrozenStatus;

	/**
	 * 客户支付方式
	 */
	private int settlementModes;

	/**
	 * 业务凭证uid
	 */
	private String businessVoucherUuid;

	/**
	 * 链接资产uid
	 */
	private String linkedUnderlyingAssetUuid;

	/**
	 * 链接租约的uid
	 */
	private String linkedContractUuid;

	private Map<String,Object> appendix = new HashMap<String,Object>();

	/**
	 * 链接mysql订单号
	 */
	private String linkedOuterBusinessNo;

	/**
	 * 链接mysql订单的结算状态
	 */
	private int settlingStatus;

	/**
	 * 记录生成时间
	 */
	private Date createTime;

	/**
	 * 记录修改时间
	 */
	private Date modifiedTime;

	/**
	 * 逻辑删除的标志位
	 */
	private boolean isDelete;

	public BillingPlan() {
		super();
	}

	public String get_id() {
		return _id;
	}

	public String getBillingPlanUuid() {
		return billingPlanUuid;
	}

	public void setBillingPlanUuid(String billingPlanUuid) {
		this.billingPlanUuid = billingPlanUuid;
	}

	public BigDecimal getAmount() {
		return FinanceUtils.convertToBigDecimal(this.amount).setScale(2, RoundingMode.HALF_UP);  
	}
	public Double getAmountInDouble(){
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = FinanceUtils.convertToDouble(amount);
	}
	
	public BigDecimal getCarryingAmount() {
		Double carryingAmount = ObjectCastUtils.castToDouble(getAppendixBy("carryingAmount"));
		return carryingAmount==null?BigDecimal.ZERO:FinanceUtils.convertToBigDecimal(carryingAmount).setScale(2, RoundingMode.HALF_UP);  
	}

	public Currency getCurrency() {
		return Currency.fromValue(currency);
	}

	public void setCurrency(Currency currency) {
		this.currency = currency.ordinal();
	}

	public String getTradePartyUuid() {
		return tradePartyUuid;
	}

	public void setTradePartyUuid(String tradePartyUuid) {
		this.tradePartyUuid = tradePartyUuid;
	}

	public String getBillingPlanType() {
		return billingPlanType;
	}

	public void setBillingPlanType(String billingPlanType) {
		this.billingPlanType = billingPlanType;
	}
	
	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String billType) {
		this.entryType = billType;
	}


	public AccountSide getAccountSide() {
		return AccountSide.fromValue(this.accountSide);
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide.ordinal();
	}

	public String getBillEnvelopeUuid() {
		return billEnvelopeUuid;
	}

	public void setBillEnvelopeUuid(String billEnvelopeUuid) {
		this.billEnvelopeUuid = billEnvelopeUuid;
	}

	public String getApplyingPaymentClauseUuid() {
		return applyingPaymentClauseUuid;
	}

	public void setApplyingPaymentClauseUuid(String applyingPaymentClauseUuid) {
		this.applyingPaymentClauseUuid = applyingPaymentClauseUuid;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getMaturityTime() {
		return maturityTime;
	}

	public void setMaturityTime(Date maturityTime) {
		this.maturityTime = maturityTime;
	}

	public Date getWrittenOffTime() {
		return writtenOffTime;
	}

	public void setWrittenOffTime(Date writtenOffTime) {
		this.writtenOffTime = writtenOffTime;
	}

	public Date getTradeStartPoint() {
		return tradeStartPoint;
	}
	public String formatTradeStartPoint(){
		return DateUtils.format(this.getTradeStartPoint(), "yyyy_MM");
	}

	public void setTradeStartPoint(Date tradeStartPoint) {
		this.tradeStartPoint = tradeStartPoint;
	}

	public SettlementModes getSettlementModes() {
		return SettlementModes.fromValue(settlementModes);
	}

	public void setSettlementModes(SettlementModes settlementModes) {
		this.settlementModes = settlementModes.ordinal();
	}

	public String getBusinessVoucherUuid() {
		return businessVoucherUuid;
	}

	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}

	public String getLinkedUnderlyingAssetUuid() {
		return linkedUnderlyingAssetUuid;
	}

	public void setLinkedUnderlyingAssetUuid(String linkedUnderlyingAssetUuid) {
		this.linkedUnderlyingAssetUuid = linkedUnderlyingAssetUuid;
	}

	public String getLinkedContractUuid() {
		return linkedContractUuid;
	}

	public void setLinkedContractUuid(String linkedContractUuid) {
		this.linkedContractUuid = linkedContractUuid;
	}

	public String getLinkedOuterBusinessNo() {
		return linkedOuterBusinessNo;
	}

	public void setLinkedOuterBusinessNo(String linkedOuterBusinessNo) {
		this.linkedOuterBusinessNo = linkedOuterBusinessNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getBillingPlanName() {
		return billingPlanName;
	}

	public void setBillingPlanName(String billingPlanName) {
		this.billingPlanName = billingPlanName;
	}

	public String getBillingPlanNumber() {
		return billingPlanNumber;
	}

	public void setBillingPlanNumber(String billingPlanNumber) {
		this.billingPlanNumber = billingPlanNumber;
	}

	public int getDistanceFromTodayToDueDay(){
		
		return DateUtils.compareTwoDatesOnDay(new Date(), this.tradeStartPoint);
	}
	
	public Map<String, Object> getAppendix() {
		return appendix;
	}
	public Object getAppendixBy(String key){
		return StringUtils.isEmpty(key)?null:appendix.get(key);
	}

	public void setAppendix(Map<String, Object> appendix) {
		this.appendix = appendix;
	}
	public void putAppendix(String key,Object value) {
		this.appendix.put(key,value);
	}
	
	public String getAppendixStr(String key) {
		if(appendix == null && key == null)return StringUtils.EMPTY;
		Object value = appendix.get(key);
		if(value==null || (value instanceof String)==false) return StringUtils.EMPTY;
		return (String) value;
	}
	
	
	public Boolean getAppendixBoolean(String key) {
		if(appendix == null && key == null)return null;
		Object value = appendix.get(key);
		if(value == null || (value instanceof Boolean)==false)return false;
		return castData(new TypeReference<Boolean >(){},value);
	}
	
	public static  <T> T castData(TypeReference<T> type,Object value) {
		if (value instanceof Map || value instanceof Collection) {
			String objstr = JsonUtils.toJsonString(value);
			T data = JSON.parseObject(objstr, type);
			return data;
		} else {
			return (T) value;
		}
	}
	public static  <T> T castData(Class<T> clazz,Object value) {
		if (value instanceof Map || value instanceof Collection) {
			String objstr = JsonUtils.toJsonString(value);
			T data = JsonUtils.parse(objstr, clazz);
			return data;
		} else {
			return (T) value;
		}
	}
	public static String generateBillingPlanNumber(){
		return GeneratorUtils.generateID();
	}
	public static String generateBillingPlanName(Date effectiveDate, Date maturityDate,String billingPlanTypeAlias,String subjectMatterName,String propertyNo) {

		String time = DateUtils.formatByYearMonth(effectiveDate) + "-"+DateUtils.formatByYearMonth(maturityDate);

		return subjectMatterName + "(" + time + "月)" + billingPlanTypeAlias + propertyNo;
	}

	
	
	public BillingPlan(Order order){
		this.billingPlanUuid = order.getRepaymentBillId();
		this.billingPlanName = order.getOrderNo();
		this.amount = FinanceUtils.convertToDouble(order.getTotalRent());
		this.issueTime = order.getCreateTime();
		this.effectiveTime = order.getDueDate();
		this.maturityTime = order.getDueDate();
		this.tradeStartPoint = order.getDueDate();
		this.createTime = order.getCreateTime();
		this.modifiedTime = order.getModifyTime();
	}

	
}
