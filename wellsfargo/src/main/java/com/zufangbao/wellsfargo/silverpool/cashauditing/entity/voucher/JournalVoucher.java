package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.GeneratorUtils;

@Entity
public class JournalVoucher {

	@Id
	@GeneratedValue
	private Long id;
	
	private String journalVoucherUuid;
	/**
	 * 日记帐凭证完整性 0:现金流条目流缺失,1:交易通知条目缺失,2:交易通知歧义,3:条目完整
	 */
	@Enumerated(EnumType.ORDINAL)
	private JournalVoucherCompleteness completeness;
	/**
	 * 日记账凭证检查等级 0:自动制证,1:人工二次确认
	 */
	@Enumerated(EnumType.ORDINAL)
	private JournalVoucherCheckingLevel checkingLevel;
	
	private String counterPartyAccount;
	
	private String counterPartyName;
	
	@Enumerated(EnumType.ORDINAL)
	private SettlementModes settlementModes;
	
	private String batchUuid;	
	
	private Long companyId;
	
	//By create Service
	@Enumerated(EnumType.ORDINAL)
	private AccountSide  accountSide;
	
	/********by Accounter********************/
	private BigDecimal bookingAmount;
	/**
	 * 日记账凭证状态 0:已建,1:已制证,2:凭证作废
	 */
	@Enumerated(EnumType.ORDINAL)
	private JournalVoucherStatus status;
	
	/********by Accounter********************/
	
	/************create when filling souceDocument task will fill*******/
	private String businessVoucherUuid;
	
	private String businessVoucherTypeUuid;
	
	/************create when filling souceDocument task will fill*******/
	
	/*************cash flow task*************/
	private String cashFlowUuid;
	@Enumerated(EnumType.ORDINAL)
	
	private CashFlowChannelType cashFlowChannelType;
	@Type(type = "timestamp")
	private Date tradeTime;
	/** 支付平台流水号  */
	private String cashFlowSerialNo;
	/** 上传给支付平台的自定义信息 */
	@Column(columnDefinition = "text")
	private String notificationMemo;
	
	/** 上传给支付平台的通知标识 */
	private String notificationIdentity;
	
	private String bankIdentity;
	
	private BigDecimal cashFlowAmount;
	
	@Column(columnDefinition = "text")
	private String cashFlowBreif;
	/*************cash flow task*************/
	/**
	 * 通知变更时间
	 */
	private String notificationRecordUuid;
	
	private String sourceDocumentUuid;
	
	@Type(type = "timestamp")
	private Date notifiedDate;
	
	/** 支付平台的通知标识 */
	private String sourceDocumentIdentity;
	/** 支付平台流水号 */
	private String sourceDocumentCashFlowSerialNo;
	
	private BigDecimal sourceDocumentAmount;
	
	@Column(columnDefinition = "text")
	private String sourceDocumentBreif;
	
	private String sourceDocumentCounterPartyUuid;
	
	private String sourceDocumentCounterPartyAccount;
	
	private String sourceDocumentCounterPartyName;
	
	//unique_bill_id
	private String billingPlanUuid;
	
	/*************new fields start*************/
	private JournalVoucherType journalVoucherType;
	//financialContractUuid
	private String relatedBillContractInfoLv1;
	//contractUuid
	private String relatedBillContractInfoLv2;
	//assetSetUuid
	private String relatedBillContractInfoLv3;
	
	//信托项目名称
	private String relatedBillContractNoLv1;
	//合同编号
	private String relatedBillContractNoLv2;
	//还款计划编号
	private String relatedBillContractNoLv3;
	//订单编号（orderNo）
	private String relatedBillContractNoLv4;
	//凭证编号
	private String sourceDocumentNo;

	private String cashFlowAccountInfo;
	
	//orderNo of order
	@Enumerated(EnumType.ORDINAL)
	private CounterAccountType counterAccountType;
	/*************new fields end*************/
	
	/**
	 * 创建时间
	 */
	@Type(type = "timestamp")
	private Date createdDate;
	
	private Date issuedTime;
	
	private String journalVoucherNo;
	
	public JournalVoucher(){
		this.createdDate = new Date();
	}
	@Deprecated
	public JournalVoucher(AccountSide acountSide){
		this();
		this.accountSide = acountSide;
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.journalVoucherUuid = UUID.randomUUID().toString().replace("-", "");
		this.checkingLevel = getDefaultCheckingLevel();
	}
	@Deprecated
	public JournalVoucher(String notificationRecordUuid, Date notifiedDate,
			String notifiedIdentity, String notifiedCashFlowSerialNo,
			BigDecimal notifiedAmount, Long companyId,
			String virtualAccountUuid) {
		this();
		this.notificationRecordUuid = notificationRecordUuid;
		this.notifiedDate = notifiedDate;
		this.sourceDocumentIdentity = notifiedIdentity;
		this.sourceDocumentCashFlowSerialNo = notifiedCashFlowSerialNo;
		this.sourceDocumentAmount = notifiedAmount;
		this.companyId = companyId;
		this.batchUuid = UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * 利用现金流的信息创建journal_voucher
	 * @param appArriveRecord
	 * @return
	 */
	@Deprecated
	public JournalVoucher createFromCashFlow(AppArriveRecord appArriveRecord){
		
		this.cashFlowUuid = appArriveRecord.getCashFlowUid();
		this.counterAccountType=CounterAccountType.BankAccount;
		this.counterPartyAccount = appArriveRecord.getPayAcNo();
		this.counterPartyName = appArriveRecord.getPayName();
		this.tradeTime = appArriveRecord.getTime();
		this.cashFlowSerialNo = appArriveRecord.getVouhNo();
		this.notificationMemo = appArriveRecord.getSummary();
		this.notificationIdentity = appArriveRecord.getSerialNo();
		this.bankIdentity = appArriveRecord.getPartnerId();
		this.cashFlowAmount = appArriveRecord.getAmount();
		this.cashFlowChannelType = appArriveRecord.getCashFlowChannelType();
		this.cashFlowBreif = StringUtils.EMPTY;
		this.journalVoucherUuid = UUID.randomUUID().toString();
		App app = appArriveRecord.getApp();
		if (app == null){
			this.companyId = null;
		} else {
			Company company = app.getCompany();
			if (company == null){
				this.companyId = null;
			}
			this.companyId = company.getId();
		}
		this.checkingLevel = getDefaultCheckingLevel();
		this.accountSide = AccountSide.reverse(BankSide.fromValue(appArriveRecord.getDrcrf()));
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.createdDate = new Date();
		
		return this;
	}
	/** directbank cashflow */
	public JournalVoucher createFromCashFlow(CashFlow cashFlow,Long companyId){
		
		this.cashFlowUuid = cashFlow.getCashFlowUuid();
		this.counterAccountType=CounterAccountType.BankAccount;
		this.counterPartyAccount = cashFlow.getCounterAccountNo();
		this.counterPartyName = cashFlow.getCounterAccountName();
		this.tradeTime = cashFlow.getTransactionTime();
		this.cashFlowSerialNo = cashFlow.getBankSequenceNo();
		this.notificationMemo = cashFlow.getRemark();
		this.notificationIdentity = cashFlow.getBankSequenceNo();
		this.bankIdentity = cashFlow.getCounterAccountNo();
		this.cashFlowAmount = cashFlow.getTransactionAmount();
		this.cashFlowChannelType = cashFlow.getCashFlowChannelType();
		this.cashFlowBreif = cashFlow.getRemark();
		this.journalVoucherUuid = UUID.randomUUID().toString();
		this.companyId = companyId;
		this.checkingLevel = getDefaultCheckingLevel();
		this.accountSide = AccountSide.fromLedgerBookAccountSide(cashFlow.getAccountSide());
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.createdDate = new Date();
		
		return this;
	}
	
	
	/**
	 * 
	 * @param sourceDocument
	 * @return
	 */
	public JournalVoucher copyFromSourceDocument(SourceDocument sourceDocument){
		this.sourceDocumentUuid = sourceDocument.getSourceDocumentUuid();
		this.sourceDocumentNo = sourceDocument.getSourceDocumentNo();
		this.sourceDocumentIdentity = sourceDocument.getOutlierDocumentUuid();
		this.sourceDocumentCashFlowSerialNo = sourceDocument.getOutlierSerialGlobalIdentity();
		this.sourceDocumentAmount = sourceDocument.getOutlierAmount();
		this.sourceDocumentBreif = sourceDocument.getOutlierBreif();
		this.sourceDocumentCounterPartyAccount = sourceDocument.getOutlierCounterPartyAccount();
		this.sourceDocumentCounterPartyName = sourceDocument.getOutlierCounterPartyName();
		this.notifiedDate = sourceDocument.getOutlierTradeTime();
		this.tradeTime = sourceDocument.getOutlierTradeTime();
		this.companyId = sourceDocument.getCompanyId();
		this.checkingLevel = getDefaultCheckingLevel();
		this.accountSide = sourceDocument.getSourceAccountSide();
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.createdDate = new Date();
		this.journalVoucherUuid = UUID.randomUUID().toString();
		this.settlementModes = sourceDocument.getOutlierSettlementModes();
		return this;
	}
	
	public JournalVoucher createFromComposentoryVoucher(SourceDocumentDetail sourceDocumentDetail,String sourceDocumentNo,AccountSide accountSide, Company company, ContractAccount contractAccount, VirtualAccount virtualAccount){
		this.sourceDocumentUuid = sourceDocumentDetail.getUuid();
		this.sourceDocumentIdentity = sourceDocumentDetail.getSourceDocumentUuid();
		this.counterAccountType = CounterAccountType.VirtualAccount;
		if(virtualAccount!=null){
			this.counterPartyAccount = virtualAccount.getVirtualAccountNo();
			this.counterPartyName = virtualAccount.getOwnerName();
		}
		if(company!=null){
			this.companyId = company.getId();
			this.cashFlowAccountInfo = company.getUuid();
		}
		this.sourceDocumentAmount = sourceDocumentDetail.getAmount();
		if(contractAccount!=null){
			this.sourceDocumentCounterPartyAccount = contractAccount.getPayAcNo();
			this.sourceDocumentCounterPartyName = contractAccount.getPayerName();
		}
				
		this.checkingLevel = getDefaultCheckingLevel();
		this.accountSide = accountSide;
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.createdDate = new Date();
		this.journalVoucherUuid = UUID.randomUUID().toString();
		this.sourceDocumentNo = sourceDocumentNo;
		return this;
	}
	public JournalVoucher createFromThirdPartyDeductionVoucher(SourceDocumentDetail sourceDocumentDetail,AccountSide accountSide, Company company, Account receiveaccount, String sourceDocumentNo){
		this.sourceDocumentUuid = sourceDocumentDetail.getUuid();
		this.sourceDocumentIdentity = sourceDocumentDetail.getSourceDocumentUuid();
		this.counterAccountType = CounterAccountType.BankAccount;
		this.counterPartyAccount = sourceDocumentDetail.getPaymentAccountNo();
		this.counterPartyName = sourceDocumentDetail.getPaymentName();
		if(company!=null){
			this.companyId = company.getId();
			this.cashFlowAccountInfo = company.getUuid();
		}
		this.sourceDocumentAmount = sourceDocumentDetail.getAmount();
		if(receiveaccount!=null){
			this.sourceDocumentCounterPartyAccount = receiveaccount.getAccountNo();
			this.sourceDocumentCounterPartyName = receiveaccount.getAccountName();
		}
				
		this.checkingLevel = getDefaultCheckingLevel();
		this.accountSide = accountSide;
		this.status = JournalVoucherStatus.VOUCHER_CREATED;
		this.createdDate = new Date();
		this.journalVoucherUuid = UUID.randomUUID().toString();
		this.sourceDocumentNo = sourceDocumentNo;
		return this;
	}
	/**
	 * copy现金流的数据到journal_voucher
	 * @param appArriveRecord
	 * @return
	 */
	public JournalVoucher copyFromCashFlow(CashFlow cashFlow,Long companyId){
		
		this.cashFlowUuid = cashFlow.getCashFlowUuid();
		this.counterAccountType=CounterAccountType.BankAccount;
		this.counterPartyAccount = cashFlow.getCounterAccountNo();
		this.counterPartyName = cashFlow.getCounterAccountName();
		this.tradeTime = cashFlow.getTransactionTime();
		this.cashFlowSerialNo = cashFlow.getBankSequenceNo();
		this.notificationMemo = cashFlow.getRemark();
		this.notificationIdentity = cashFlow.getBankSequenceNo();
		this.bankIdentity = cashFlow.getCounterAccountNo();
		this.cashFlowAmount = cashFlow.getTransactionAmount();
		this.cashFlowChannelType = CashFlowChannelType.DirectBank;
		this.cashFlowBreif = cashFlow.getRemark();
		this.journalVoucherUuid = UUID.randomUUID().toString();
		this.companyId = companyId;
		this.accountSide = AccountSide.fromLedgerBookAccountSide(cashFlow.getAccountSide());
		
		return this;
	}
	@Deprecated
	public JournalVoucher copyDataFromCashFlow(AppArriveRecord appArriveRecord){
		
		this.cashFlowUuid = appArriveRecord.getCashFlowUid();
		this.counterAccountType=CounterAccountType.BankAccount;
		this.counterPartyAccount = appArriveRecord.getPayAcNo();
		this.counterPartyName = appArriveRecord.getPayName();
		this.tradeTime = appArriveRecord.getTime();
		this.cashFlowSerialNo = appArriveRecord.getVouhNo();
		this.notificationMemo = appArriveRecord.getSummary();
		this.notificationIdentity = appArriveRecord.getSerialNo();
		this.bankIdentity = appArriveRecord.getPartnerId();
		this.cashFlowAmount = appArriveRecord.getAmount();
		this.cashFlowBreif = StringUtils.EMPTY;
		this.accountSide = AccountSide.reverse(BankSide.fromValue(appArriveRecord.getDrcrf()));
		this.cashFlowChannelType = appArriveRecord.getCashFlowChannelType();
		
		return this;
	}
	
	public JournalVoucher fill_voucher_and_booking_amount(String billingPlanUuid, String businessVoucherTypeUuid,
			String businessVoucherUuid, BigDecimal bookingAmount, JournalVoucherStatus status,
			JournalVoucherCheckingLevel journalVoucherCheckingLevel, JournalVoucherType journalVoucherType){
		this.billingPlanUuid = billingPlanUuid;
		this.businessVoucherTypeUuid = businessVoucherTypeUuid;
		this.businessVoucherUuid = businessVoucherUuid;
		this.bookingAmount = bookingAmount;
		this.status = status;
		this.checkingLevel = journalVoucherCheckingLevel;
		this.journalVoucherType = journalVoucherType;
		if(journalVoucherType != null ){
			if(journalVoucherType.equals(JournalVoucherType.VIRTUAL_ACCOUNT_TRANSFER) || journalVoucherType.equals(JournalVoucherType.THIRD_PARTY_DEDUCT_VOUCHER) ||
				journalVoucherType.equals(JournalVoucherType.TRANSFER_BILL_BY_VOUCHER)){
				this.journalVoucherNo = GeneratorUtils.generatePaymentNo();
			}else{
				this.journalVoucherNo = UUID.randomUUID().toString();
			}
		}
		return this;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(BigDecimal bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public AccountSide getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}
	public String getJournalVoucherUuid() {
		return journalVoucherUuid;
	}
	public void setJournalVoucherUuid(String journalVoucherUuid) {
		this.journalVoucherUuid = journalVoucherUuid;
	}
	public JournalVoucherCompleteness getCompleteness() {
		return completeness;
	}
	public void setCompleteness(JournalVoucherCompleteness completeness) {
		this.completeness = completeness;
	}
	public JournalVoucherStatus getStatus() {
		return status;
	}
	public void setStatus(JournalVoucherStatus status) {
		this.status = status;
	}
	public JournalVoucherCheckingLevel getCheckingLevel() {
		return checkingLevel;
	}
	public void setCheckingLevel(JournalVoucherCheckingLevel checkingLevel) {
		this.checkingLevel = checkingLevel;
	}
	public String getCashFlowUuid() {
		return cashFlowUuid;
	}
	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getCashFlowSerialNo() {
		return cashFlowSerialNo;
	}
	public void setCashFlowSerialNo(String cashFlowSerialNo) {
		this.cashFlowSerialNo = cashFlowSerialNo;
	}
	public String getNotificationMemo() {
		return notificationMemo;
	}
	public void setNotificationMemo(String notificationMemo) {
		this.notificationMemo = notificationMemo;
	}
	public String getNotificationIdentity() {
		return notificationIdentity;
	}
	public void setNotificationIdentity(String notificationIdentity) {
		this.notificationIdentity = notificationIdentity;
	}
	public String getBankIdentity() {
		return bankIdentity;
	}
	public void setBankIdentity(String bankIdentity) {
		this.bankIdentity = bankIdentity;
	}
	public BigDecimal getCashFlowAmount() {
		return cashFlowAmount;
	}
	public void setCashFlowAmount(BigDecimal cashFlowAmount) {
		this.cashFlowAmount = cashFlowAmount;
	}
	public String getCashFlowBreif() {
		return cashFlowBreif;
	}
	public void setCashFlowBreif(String cashFlowBreif) {
		this.cashFlowBreif = cashFlowBreif;
	}
	public String getNotificationRecordUuid() {
		return notificationRecordUuid;
	}
	public void setNotificationRecordUuid(String notificationRecordUuid) {
		this.notificationRecordUuid = notificationRecordUuid;
	}
	public Date getNotifiedDate() {
		return notifiedDate;
	}
	public void setNotifiedDate(Date notifiedDate) {
		this.notifiedDate = notifiedDate;
	}
	public String getSourceDocumentIdentity() {
		return sourceDocumentIdentity;
	}
	public void setSourceDocumentIdentity(String notifiedIdentity) {
		this.sourceDocumentIdentity = notifiedIdentity;
	}
	public String getSourceDocumentCashFlowSerialNo() {
		return sourceDocumentCashFlowSerialNo;
	}
	public void setSourceDocumentCashFlowSerialNo(String notifiedCashFlowSerialNo) {
		this.sourceDocumentCashFlowSerialNo = notifiedCashFlowSerialNo;
	}
	public BigDecimal getSourceDocumentAmount() {
		return sourceDocumentAmount;
	}
	public void setSourceDocumentAmount(BigDecimal notifiedAmount) {
		this.sourceDocumentAmount = notifiedAmount;
	}
	public Long getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getSourceDocumentBreif() {
		return sourceDocumentBreif;
	}
	public void setSourceDocumentBreif(String notificationBreif) {
		this.sourceDocumentBreif = notificationBreif;
	}
	public String getBatchUuid() {
		return batchUuid;
	}
	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}
	public String getBusinessVoucherUuid() {
		return businessVoucherUuid;
	}
	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}
	public String getBillingPlanUuid() {
		return billingPlanUuid;
	}
	public void setBillingPlanUuid(String billingPlanUuid) {
		this.billingPlanUuid = billingPlanUuid;
	}

	public String getCounterPartyAccount() {
		return counterPartyAccount;
	}

	public void setCounterPartyAccount(String counterPartyAccount) {
		this.counterPartyAccount = counterPartyAccount;
	}

	public String getCounterPartyName() {
		return counterPartyName;
	}

	public void setCounterPartyName(String counterPartyName) {
		this.counterPartyName = counterPartyName;
	}

	public SettlementModes getSettlementModes() {
		return settlementModes;
	}

	public void setSettlementModes(SettlementModes settlementModes) {
		this.settlementModes = settlementModes;
	}

	public String getBusinessVoucherTypeUuid() {
		return businessVoucherTypeUuid;
	}

	public void setBusinessVoucherTypeUuid(String businessVoucherTypeUuid) {
		this.businessVoucherTypeUuid = businessVoucherTypeUuid;
	}

	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	
	public String getSourceDocumentCounterPartyUuid() {
		return sourceDocumentCounterPartyUuid;
	}

	public void setSourceDocumentCounterPartyUuid(
			String sourceDocumentCounterPartyUuid) {
		this.sourceDocumentCounterPartyUuid = sourceDocumentCounterPartyUuid;
	}
	
	public String getSourceDocumentCounterPartyAccount() {
		return sourceDocumentCounterPartyAccount;
	}

	public void setSourceDocumentCounterPartyAccount(
			String sourceDocumentCounterPartyAccount) {
		this.sourceDocumentCounterPartyAccount = sourceDocumentCounterPartyAccount;
	}

	public String getSourceDocumentCounterPartyName() {
		return sourceDocumentCounterPartyName;
	}

	public void setSourceDocumentCounterPartyName(
			String sourceDocumentCounterPartyName) {
		this.sourceDocumentCounterPartyName = sourceDocumentCounterPartyName;
	}

	public CashFlowChannelType getCashFlowChannelType() {
		return cashFlowChannelType;
	}

	public void setCashFlowChannelType(CashFlowChannelType cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}
	
	public JournalVoucherType getJournalVoucherType() {
		return journalVoucherType;
	}

	public void setJournalVoucherType(JournalVoucherType journalVoucherType) {
		this.journalVoucherType = journalVoucherType;
	}

	public String getRelatedBillContractInfoLv1() {
		return relatedBillContractInfoLv1;
	}

	public void setRelatedBillContractInfoLv1(String relatedBillContractInfoLv1) {
		this.relatedBillContractInfoLv1 = relatedBillContractInfoLv1;
	}

	public String getRelatedBillContractInfoLv2() {
		return relatedBillContractInfoLv2;
	}

	public void setRelatedBillContractInfoLv2(String relatedBillContractInfoLv2) {
		this.relatedBillContractInfoLv2 = relatedBillContractInfoLv2;
	}

	public String getRelatedBillContractInfoLv3() {
		return relatedBillContractInfoLv3;
	}

	public void setRelatedBillContractInfoLv3(String relatedBillContractInfoLv3) {
		this.relatedBillContractInfoLv3 = relatedBillContractInfoLv3;
	}

	public String getCashFlowAccountInfo() {
		return cashFlowAccountInfo;
	}

	public void setCashFlowAccountInfo(String cashFlowAccountInfo) {
		this.cashFlowAccountInfo = cashFlowAccountInfo;
	}
	
	public CounterAccountType getCounterAccountType() {
		return counterAccountType;
	}

	public void setCounterAccountType(CounterAccountType counterAccountType) {
		this.counterAccountType = counterAccountType;
	}
	
	public String getRelatedBillContractNoLv1() {
		return relatedBillContractNoLv1;
	}
	public void setRelatedBillContractNoLv1(String relatedBillContractNoLv1) {
		this.relatedBillContractNoLv1 = relatedBillContractNoLv1;
	}
	public String getRelatedBillContractNoLv2() {
		return relatedBillContractNoLv2;
	}
	public void setRelatedBillContractNoLv2(String relatedBillContractNoLv2) {
		this.relatedBillContractNoLv2 = relatedBillContractNoLv2;
	}
	public String getRelatedBillContractNoLv3() {
		return relatedBillContractNoLv3;
	}
	public void setRelatedBillContractNoLv3(String relatedBillContractNoLv3) {
		this.relatedBillContractNoLv3 = relatedBillContractNoLv3;
	}
	public String getRelatedBillContractNoLv4() {
		return relatedBillContractNoLv4;
	}
	public void setRelatedBillContractNoLv4(String relatedBillContractNoLv4) {
		this.relatedBillContractNoLv4 = relatedBillContractNoLv4;
	}
	public String getSourceDocumentNo() {
		return sourceDocumentNo;
	}
	public void setSourceDocumentNo(String sourceDocumentNo) {
		this.sourceDocumentNo = sourceDocumentNo;
	}
	public static JournalVoucherCheckingLevel getDefaultCheckingLevel(){
		
		return JournalVoucherCheckingLevel.DOUBLE_CHECKING;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}
	
	public String getJournalVoucherNo() {
		return journalVoucherNo;
	}
	public void setJournalVoucherNo(String journalVoucherNo) {
		this.journalVoucherNo = journalVoucherNo;
	}
	public void fillBillContractInfo(String relatedBillContractInfoLv1,String relatedBillContractInfoLv2,String relatedBillContractInfoLv3, String relatedBillContractNoLv1, String relatedBillContractNoLv2, String relatedBillContractNoLv3, String relatedBillContractNoLv4){
		this.relatedBillContractInfoLv1 = relatedBillContractInfoLv1;
		this.relatedBillContractInfoLv2 = relatedBillContractInfoLv2;
		this.relatedBillContractInfoLv3 = relatedBillContractInfoLv3;
		this.relatedBillContractNoLv1 = relatedBillContractNoLv1;
		this.relatedBillContractNoLv2 = relatedBillContractNoLv2;
		this.relatedBillContractNoLv3 = relatedBillContractNoLv3;
		this.relatedBillContractNoLv4 = relatedBillContractNoLv4;
	}
	public void fillCashFlowAccountInfo(String cashFlowAccountInfo){
		this.cashFlowAccountInfo = cashFlowAccountInfo;
	}

}
