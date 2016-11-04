package com.zufangbao.wellsfargo.silverpool.cashauditing.webform;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;

public class CashFlowDetail {
	/** 银行流水号 */
	private String cashFlowSeriralNo;
	/** 付款方账号*/
	private String payerAccount;
	/** 银行账户信息 */
	private String bankShortName;
	/** 收款方账户 */
	private String receiveAcNo;
	/** 流水发生时间 */
	private Date time;
	/** 付款方名称 */
	private String payerName;
	/** 发生金额 */
	private BigDecimal amount;
	/** 付款方开户行 */
	private String bankName;
	/** 银行备注 */
	private String remark;
	/** 对账状态 */
	private AuditStatus auditStatus;
	/** 匹配房源编号 */
	private String relatedHouseSourceNo;
	/** 匹配账单编号 */
	private String relatedBillNo;
	/** 匹配租约编号 */
	private String relatedContractNo;
	/** 收款方式 */
	private String paymentWay;
	public String getCashFlowSeriralNo() {
		return cashFlowSeriralNo;
	}
	public void setCashFlowSeriralNo(String cashFlowSeriralNo) {
		this.cashFlowSeriralNo = cashFlowSeriralNo;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRelatedHouseSourceNo() {
		return relatedHouseSourceNo;
	}
	public void setRelatedHouseSourceNo(String relatedHouseSourceNo) {
		this.relatedHouseSourceNo = relatedHouseSourceNo;
	}
	public String getRelatedBillNo() {
		return relatedBillNo;
	}
	public void setRelatedBillNo(String relatedBillNo) {
		this.relatedBillNo = relatedBillNo;
	}
	public String getRelatedContractNo() {
		return relatedContractNo;
	}
	public void setRelatedContractNo(String relatedContractNo) {
		this.relatedContractNo = relatedContractNo;
	}
	public String getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}
	public AuditStatus getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getReceiveAcNo() {
		return receiveAcNo;
	}
	public void setReceiveAcNo(String receiveAcNo) {
		this.receiveAcNo = receiveAcNo;
	}
	public CashFlowDetail(){
		
	}
	public CashFlowDetail(String cashFlowSeriralNo, String payerAccount,
			String bankShortName, Date time, String payerName,
			BigDecimal amount, String bankName, String remark,
			AuditStatus auditStatus,
			String relatedHouseSourceNo, String relatedBillNo,
			String relatedContractNo, String paymentWay) {
		super();
		this.cashFlowSeriralNo = cashFlowSeriralNo;
		this.payerAccount = payerAccount;
		this.bankShortName = bankShortName;
		this.time = time;
		this.payerName = payerName;
		this.amount = amount;
		this.bankName = bankName;
		this.remark = remark;
		this.auditStatus = auditStatus;
		this.relatedHouseSourceNo = relatedHouseSourceNo;
		this.relatedBillNo = relatedBillNo;
		this.relatedContractNo = relatedContractNo;
		this.paymentWay = paymentWay;
	}
	public CashFlowDetail(AppArriveRecord appArriveRecord, Account account){
		this.cashFlowSeriralNo = appArriveRecord.getSerialNo();
		this.payerAccount = appArriveRecord.getPayAcNo();
		if(account!=null){
			this.bankShortName = account.getMarkedAccountNo();
		}
		this.receiveAcNo = appArriveRecord.getReceiveAcNo();
		this.time = appArriveRecord.getTime();
		this.payerName = appArriveRecord.getPayName();
		this.amount = appArriveRecord.getAmount();
		this.remark = appArriveRecord.getRemark();
		this.auditStatus = appArriveRecord.getAuditStatus();
	}
	public void addRelatedNo(List<BillMatchResult> billMatchResultList) {
		if(CollectionUtils.isEmpty(billMatchResultList)){
			return;
		}
		Map<AuditStatus,JournalVoucherStatus> auditAndVoucherStatusMap = getAuditAndVoucherStatusMap();
		
		if(this.auditStatus == AuditStatus.CLOSE){
			this.relatedBillNo=StringUtils.EMPTY;
			this.relatedContractNo = StringUtils.EMPTY;
			this.relatedHouseSourceNo = StringUtils.EMPTY;
		} else {
			this.relatedBillNo = join(billMatchResultList,BillMatchResult.BILLING_PLAN_NUMBER);
			this.relatedContractNo = join(billMatchResultList,BillMatchResult.CONTRACT_NO);
			this.relatedHouseSourceNo = join(billMatchResultList,BillMatchResult.SUBJECT_MATTER_SOURCE_NO);
		}
		
	}
	
	private String join(List<BillMatchResult> billMatchResultList, String key){
		Set<String>  noSet = extractNo(billMatchResultList, key);
		return StringUtils.join(noSet, ",");
	}
	
	private Set<String> extractNo(List<BillMatchResult> billMatchResultList,String key) {
		Set<String> noSet = new HashSet<String>();
		if(CollectionUtils.isEmpty(billMatchResultList)){
			return noSet;
		}
		for (BillMatchResult billMatchResult : billMatchResultList) {
			if(billMatchResult==null) continue;
			String no = (String)billMatchResult.getShowData().get(key);
			if(StringUtils.isEmpty(no))	continue;
			JournalVoucherStatus status = getAuditAndVoucherStatusMap().get(this.auditStatus);
			if(status!=null && status==billMatchResult.getJournalVoucherStatus() ){
				noSet.add(no);
			} else if (status==null){
				noSet.add(no);
			}
		}
		return noSet;
	}
	private Map<AuditStatus,JournalVoucherStatus> getAuditAndVoucherStatusMap(){
		Map<AuditStatus,JournalVoucherStatus> auditAndVoucherStatusMap = new HashMap<AuditStatus,JournalVoucherStatus>();
		auditAndVoucherStatusMap.put(AuditStatus.CREATE, null);
		auditAndVoucherStatusMap.put(AuditStatus.ISSUED, JournalVoucherStatus.VOUCHER_ISSUED);
		auditAndVoucherStatusMap.put(AuditStatus.ISSUING, JournalVoucherStatus.VOUCHER_ISSUED);
		return auditAndVoucherStatusMap;
	}
		
}
