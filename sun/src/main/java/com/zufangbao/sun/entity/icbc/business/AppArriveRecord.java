package com.zufangbao.sun.entity.icbc.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.company.corp.App;

/**
 * 服务商账户到账记录
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "app_arrive_record")
public class AppArriveRecord {

	// 主键id
	@Id
	@GeneratedValue
	private Long id;

	//到账流水UUID
	private String cashFlowUid;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;

	// 流水号
	private String serialNo;

	// 借贷标志
	private String drcrf;

	// 付款帐号
	private String payAcNo;

	// 付款户名
	private String payName;

	// 收款账号
	private String receiveAcNo;
	
//	// 收款户名
	private String receiveName;

	// 发生额（交易金额）
	private BigDecimal amount;

	// 入账时间
	private Date time;

	// 是否已被扣款
	@Enumerated(EnumType.ORDINAL)
	private ArriveRecordStatus arriveRecordStatus;

	// 附言
	private String remark;

	// 凭证号
	private String vouhNo;

	// 摘要
	private String summary;

	//操作备注
	@Column(columnDefinition = "text")
	private String operateRemark;

	/**
	 * 流水通道类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private CashFlowChannelType cashFlowChannelType;
	
	/**
	 * 服务类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private TransactionType transactionType;
	
	/**
	 * 商户号
	 */
	private String partnerId;
	
	//报文数据
	@Column(columnDefinition = "text")
	private String detailData;
	
	/** 已确认金额 **/
	private BigDecimal issuedAmount;
	
	@Enumerated(EnumType.ORDINAL)
	private AuditStatus auditStatus;
	
	private String sourceDocumentUuid;
	
	
	private String firstAccountName;
	
	private String firstAccountCode;
	
	private String firstAccountAlias; 
	
	private String secondAccountName;
	
	private String secondAccountCode;
	private String secondAccountAlias; 
	
	
	private String thirdAccountName;

	private String thirdAccountCode;
	private String thirdAccountAlias; 
	
	private String fourthAccountName;

	private String fourthAccountCode;
	private String fourthAccountAlias;
	
	private String auxiliaryAccounting;
	
	private String journal;
	
	private Date journalTime;
	
	
	
	
	/**
	 * 贷
	 */
	public static final String CREDIT = BankSide.Credit.getValue();
	/**
	 * 借
	 */
	public static final String DEBIT = BankSide.Debit.getValue();

	/**
	 * default constructor
	 */
	public AppArriveRecord() {

	}

	/**
	 * @param app
	 * @param serialNo
	 * @param drcrf
	 * @param payAcNo
	 * @param payName
	 * @param receiveAcNo
	 * @param amount
	 * @param time
	 * @param arriveRecordStatus
	 * @param remark
	 * @param vouhNo
	 * @param summary
	 */
	public AppArriveRecord(App app, String serialNo, String drcrf,
			String payAcNo, String payName, String receiveAcNo, String receiveName,
			BigDecimal amount, Date time,
			ArriveRecordStatus arriveRecordStatus, String remark,
			String vouhNo, String summary) {
		
		this(app, serialNo, drcrf,payAcNo,payName, receiveAcNo,receiveName,amount,time,arriveRecordStatus, remark,vouhNo, summary,StringUtils.EMPTY,CashFlowChannelType.DirectBank, StringUtils.EMPTY, null);
	}
	/**
	 * @param app
	 * @param serialNo
	 * @param drcrf
	 * @param payAcNo
	 * @param payName
	 * @param receiveAcNo
	 * @param amount
	 * @param time
	 * @param arriveRecordStatus
	 * @param remark
	 * @param vouhNo
	 * @param summary
	 * @param cashFlowChannelType 
	 * @param partnerId 
	 * @param transactionType 
	 */
	public AppArriveRecord(App app, String serialNo, String drcrf,
			String payAcNo, String payName, String receiveAcNo, String receiveName,
			BigDecimal amount, Date time,
			ArriveRecordStatus arriveRecordStatus, String remark,
			String vouhNo, String summary,String detailData, CashFlowChannelType cashFlowChannelType, String partnerId, TransactionType transactionType) {
		super();
		this.app = app;
		this.serialNo = serialNo;
		this.drcrf = drcrf;
		this.payAcNo = payAcNo;
		this.payName = payName;
		this.receiveAcNo = receiveAcNo;
		this.receiveName = receiveName;
		this.amount = amount;
		this.time = time;
		this.arriveRecordStatus = arriveRecordStatus;
		this.remark = remark;
		this.vouhNo = vouhNo;
		this.summary = summary;
		this.cashFlowUid = UUID.randomUUID().toString();
		this.detailData = detailData;
		this.cashFlowChannelType = cashFlowChannelType;
		this.partnerId = partnerId;
		this.transactionType = transactionType;
		this.auditStatus = AuditStatus.CREATE;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the payAcNo
	 */
	public String getPayAcNo() {
		return payAcNo;
	}

	/**
	 * @param payAcNo
	 *            the payAcNo to set
	 */
	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}

	/**
	 * @return the receiveAcNo
	 */
	public String getReceiveAcNo() {
		return receiveAcNo;
	}

	/**
	 * @param receiveAcNo
	 *            the receiveAcNo to set
	 */
	public void setReceiveAcNo(String receiveAcNo) {
		this.receiveAcNo = receiveAcNo;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the arriveRecordStatus
	 */
	public ArriveRecordStatus getArriveRecordStatus() {
		return arriveRecordStatus;
	}

	/**
	 * @param arriveRecordStatus
	 *            the arriveRecordStatus to set
	 */
	public void setArriveRecordStatus(ArriveRecordStatus arriveRecordStatus) {
		this.arriveRecordStatus = arriveRecordStatus;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the app
	 */
	public App getApp() {
		return app;
	}

	/**
	 * @param app
	 *            the app to set
	 */
	public void setApp(App app) {
		this.app = app;
	}

	/**
	 * @return the drcrf
	 */
	public String getDrcrf() {
		return drcrf;
	}

	/**
	 * @param drcrf
	 *            the drcrf to set
	 */
	public void setDrcrf(String drcrf) {
		this.drcrf = drcrf;
	}
	public void injectDrcrf(BankSide bankSide){
		this.setDrcrf(bankSide.getValue());
	}

	/**
	 * @return the payName
	 */
	public String getPayName() {
		return payName;
	}

	/**
	 * @param payName
	 *            the payName to set
	 */
	public void setPayName(String payName) {
		this.payName = payName;
	}

	/**
	 * @return the vouhNo
	 */
	public String getVouhNo() {
		return vouhNo;
	}

	/**
	 * @param vouhNo
	 *            the vouhNo to set
	 */
	public void setVouhNo(String vouhNo) {
		this.vouhNo = vouhNo;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	

	public String getCashFlowUid() {
		return cashFlowUid;
	}

	public void setCashFlowUid(String cashFlowUid) {
		this.cashFlowUid = cashFlowUid;
	}

	public boolean isBufferDeductFlow() {
		return BigDecimal.ZERO.compareTo(getAmount()) == 0;
	}

	public CashFlowChannelType getCashFlowChannelType() {
		return cashFlowChannelType;
	}

	public void setCashFlowChannelType(CashFlowChannelType cashFlowChannelType) {
		this.cashFlowChannelType = cashFlowChannelType;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getDetailData() {
		return detailData;
	}

	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}

	/**
	 * @return the operateRemark
	 */
	public List<OperateRemark> getOperateRemark() {
		if(operateRemark == null || operateRemark.isEmpty()){
			return new ArrayList<OperateRemark>();
		}
		List<OperateRemark> history_remarks = JsonUtils.parseArray(
				operateRemark, OperateRemark.class);

		return history_remarks;

	}

	/**
	 * @param operateRemark
	 *            the operateRemark to set
	 */
	public void setOperateRemark(String remark, Long operatorId) {

		List<OperateRemark> history_remarks = getOperateRemark();

		OperateRemark operateRemark = new OperateRemark(
				AppArriveRecordOperateType.VetoAlone.ordinal(), remark,
				DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), operatorId);

		history_remarks.add(operateRemark);

		this.operateRemark = JsonUtils.toJsonString(history_remarks);
	}
	
	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}

	@Override
	public String toString(){
		
		return "[流水号(serialNo):" +serialNo+"],[交易时间(time):"+time+"],[交易金额(amount):"+amount+"],[借贷(drcrf)"+BankSide.fromValue(drcrf).getDescription() +"],[付款人账号(payAcNo):"+payAcNo+"],[付款人姓名(payName):"+payName+"],[收款人账号(receiveAcNo):"+receiveAcNo+"],[备注(remark):"+remark+"],[摘要(summary):"+summary+"],[支付平台凭证号(vouhNo):"+vouhNo+"]";
	}
	
	public AuditStatus changeIssuedAmountAndAuditStatus(BigDecimal issuedAmount){
		if (issuedAmount == null || amount == null){
			return null;
		}
		this.issuedAmount = issuedAmount;
		if(issuedAmount.compareTo(BigDecimal.ZERO) == 0){
			this.auditStatus = AuditStatus.CREATE;
		} else if(issuedAmount.compareTo(amount)<0){
			this.auditStatus = AuditStatus.ISSUING;
		} else if (issuedAmount.compareTo(amount)==0){
			this.auditStatus = AuditStatus.ISSUED;
		} else {
			this.auditStatus = AuditStatus.ISSUING;
		}
		return this.auditStatus;
	}

	public String getFirstAccountName() {
		return firstAccountName;
	}

	public void setFirstAccountName(String firstAccountName) {
		this.firstAccountName = firstAccountName;
	}

	public String getFirstAccountCode() {
		return firstAccountCode;
	}

	public void setFirstAccountCode(String firstAccountId) {
		this.firstAccountCode = firstAccountId;
	}

	public String getFirstAccountAlias() {
		return firstAccountAlias;
	}

	public void setFirstAccountAlias(String firstAccountAlias) {
		this.firstAccountAlias = firstAccountAlias;
	}

	public String getSecondAccountName() {
		return secondAccountName;
	}

	public void setSecondAccountName(String secondAccountName) {
		this.secondAccountName = secondAccountName;
	}

	public String getSecondAccountCode() {
		return secondAccountCode;
	}

	public void setSecondAccountId(String secondAccountId) {
		this.secondAccountCode = secondAccountId;
	}

	public String getSecondAccountAlias() {
		return secondAccountAlias;
	}

	public void setSecondAccountAlias(String secondAccountAlias) {
		this.secondAccountAlias = secondAccountAlias;
	}

	public String getThirdAccountName() {
		return thirdAccountName;
	}

	public void setThirdAccountName(String thirdAccountName) {
		this.thirdAccountName = thirdAccountName;
	}

	public String getThirdAccountCode() {
		return thirdAccountCode;
	}

	public void setThirdAccountId(String thirdAccountId) {
		this.thirdAccountCode = thirdAccountId;
	}

	public String getThirdAccountAlias() {
		return thirdAccountAlias;
	}

	public void setThirdAccountAlias(String thirdAccountAlias) {
		this.thirdAccountAlias = thirdAccountAlias;
	}

	public String getFourthAccountName() {
		return fourthAccountName;
	}

	public void setFourthAccountName(String fourthAccountName) {
		this.fourthAccountName = fourthAccountName;
	}

	public String getFourthAccountCode() {
		return fourthAccountCode;
	}

	public void setFourthAccountCode(String fourthAccountId) {
		this.fourthAccountCode = fourthAccountId;
	}

	public String getFourthAccountAlias() {
		return fourthAccountAlias;
	}

	public void setFourthAccountAlias(String fourthAccountAlias) {
		this.fourthAccountAlias = fourthAccountAlias;
	}

	public String getAuxiliaryAccounting() {
		return auxiliaryAccounting;
	}

	public void setAuxiliaryAccounting(String auxiliaryAccounting) {
		this.auxiliaryAccounting = auxiliaryAccounting;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public Date getJournalTime() {
		return journalTime;
	}

	public void setJournalTime(Date journalTime) {
		this.journalTime = journalTime;
	}
	
	public boolean hasJournal()
	{
		return !StringUtils.isEmpty(this.getFirstAccountCode());
	}
	
	public void clearJournalInfoSet()
	{
		this.setFirstAccountAlias("");
		this.setFirstAccountCode("");
		this.setFirstAccountName("");
		this.setSecondAccountAlias("");
		this.setSecondAccountId("");
		this.setSecondAccountName("");
		this.setThirdAccountAlias("");
		this.setThirdAccountId("");
		this.setThirdAccountName("");
		this.setJournal("");
		this.setAuxiliaryAccounting("");
		this.setJournalTime(new Date());
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	
	public String getUuid(){
		return this.cashFlowUid;
	}
}
