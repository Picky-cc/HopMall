package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

/**
 * 资产评估明细表
 * 
 * @author louguanyang
 *
 */

@Entity
@Table(name = "ledger_book_shelf")
public class LedgerItem {
	
	/**
	 * 资产评估明细ID
	 */
	@Id
	@GeneratedValue
	private long id;
	//book sector
	private String  ledgerBookOwnerId;
	
	private String ledgerBookNo;
	
	private String ledgerUuid;
	//balance sector
	
	private BigDecimal debitBalance;
	
	private BigDecimal creditBalance;
	//account sector
	
	private String firstAccountName;
	
	private String firstAccountUuid;
	
	private String secondAccountName;
	
	private String secondAccountUuid;
	
	private String thirdAccountName;

	private String thirdAccountUuid;
		
	private String firstPartyId;
	
	private String secondPartyId;
	
	private String thirdPartyId;
	
	private int accountSide;
	//voucher sector
	private String journalVoucherUuid;
	
	private String businessVoucherUuid;
	
	private  String  sourceDocumentUuid;
	//reference sector
	
	
	private String backwardLedgerUuid;
	
	private String forwardLedgerUuid;
	
	private String batchSerialUuid;
	//status sector
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookInDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date carriedOverDate;
	@Enumerated(EnumType.ORDINAL)
	private LedgerLifeCycle lifeCycle;
	
	//contract sector
	private Long contractId;
	private String contractUuid;
	//@Column(name="related_lv1_asset_uuid")
	private String relatedLv1AssetUuid;
	//@Column(name="related_lv1_asset_outer_idenity")
	private String relatedLv1AssetOuterIdenity;
	
	//@Column(name="related_lv2_asset_uuid")
	private String relatedLv2AssetUuid;
	
	//@Column(name="related_lv2_asset_outer_idenity")
	private String relatedLv2AssetOuterIdenity;
	
	//@Column(name="related_lv3_asset_uuid")
	private String relatedLv3AssetUuid;
	
	//@Column(name="related_lv3_asset_outer_idenity")
	private String relatedLv3AssetOuterIdenity;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date defaultDate;
	
	/**
	 * 摊销日
	 */
	@Type(type = "date")
	private Date amortizedDate;


	

	
	public int getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(int accountSide) {
		this.accountSide = accountSide;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getContractUuid() {
		return contractUuid;
	}

	public void setContractUuid(String contractUuid) {
		this.contractUuid = contractUuid;
	}

	public String getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}
	public LedgerItem() {
		super();
		this.setLedgerUuid(UUID.randomUUID().toString());
		this.setBatchSerialUuid(UUID.randomUUID().toString());
	}

	public BigDecimal getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getFirstAccountName() {
		return firstAccountName;
	}

	public void setFirstAccountName(String firstAccountName) {
		this.firstAccountName = firstAccountName;
	}

	
	public String getSecondAccountName() {
		return secondAccountName;
	}

	public void setSecondAccountName(String secondAccountName) {
		this.secondAccountName = secondAccountName;
	}

	

	public String getThirdAccountName() {
		return thirdAccountName;
	}

	public void setThirdAccountName(String thirdAccountName) {
		this.thirdAccountName = thirdAccountName;
	}

	

	public String getFirstPartyId() {
		return firstPartyId;
	}

	public void setFirstPartyId(String firstPartyId) {
		this.firstPartyId = firstPartyId;
	}

	

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}


	public LedgerLifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(LedgerLifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}




	public Date getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getLedgerUuid() {
		return ledgerUuid;
	}

	public void setLedgerUuid(String detailUUid) {
		this.ledgerUuid = detailUUid;
	}
	
	public String getBatchSerialUuid() {
		return this.batchSerialUuid;
	}

	public void setBatchSerialUuid(String batchSerial) {
		this.batchSerialUuid = batchSerial;
	}
	
	


	public Date getCarriedOverDate() {
		return carriedOverDate;
	}

	public void setCarriedOverDate(Date carriedOverDate) {
		this.carriedOverDate = carriedOverDate;
	}
	

	public String getLedgerBookOwnerId() {
		return ledgerBookOwnerId;
	}

	public void setLedgerBookOwnerId(String ledgerBookOwnerId) {
		this.ledgerBookOwnerId = ledgerBookOwnerId;
	}

	public String getFirstAccountUuid() {
		return firstAccountUuid;
	}

	public void setFirstAccountUuid(String firstAccountUuId) {
		this.firstAccountUuid = firstAccountUuId;
	}

	public String getSecondAccountUuid() {
		return secondAccountUuid;
	}

	public void setSecondAccountUuid(String secondAccountUuId) {
		this.secondAccountUuid = secondAccountUuId;
	}

	public String getThirdAccountUuid() {
		return thirdAccountUuid;
	}

	public void setThirdAccountUuid(String thirdAccountUuId) {
		this.thirdAccountUuid = thirdAccountUuId;
	}

	public String getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(String secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getJournalVoucherUuid() {
		return journalVoucherUuid;
	}

	public void setJournalVoucherUuid(String journalVoucherUuid) {
		this.journalVoucherUuid = journalVoucherUuid;
	}

	public String getBusinessVoucherUuid() {
		return businessVoucherUuid;
	}

	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}

	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}

	public String getBackwardLedgerUuid() {
		return backwardLedgerUuid;
	}

	public void setBackwardLedgerUuid(String backwardLedgerUuid) {
		this.backwardLedgerUuid = backwardLedgerUuid;
	}

	public String getForwardLedgerUuid() {
		return forwardLedgerUuid;
	}

	public void setForwardLedgerUuid(String forwardLedgerUuid) {
		this.forwardLedgerUuid = forwardLedgerUuid;
	}

	public Date getBookInDate() {
		return bookInDate;
	}

	public void setBookInDate(Date bookInDate) {
		this.bookInDate = bookInDate;
	}

	public Date getAmortizedDate() {
		return amortizedDate;
	}

	public void setAmortizedDate(Date amortizatedDate) {
		this.amortizedDate = amortizatedDate;
	}

	public String getRelatedLv1AssetUuid() {
		return relatedLv1AssetUuid;
	}

	public void setRelatedLv1AssetUuid(String relatedLv1AssetUuid) {
		this.relatedLv1AssetUuid = relatedLv1AssetUuid;
	}

	public String getRelatedLv1AssetOuterIdenity() {
		return relatedLv1AssetOuterIdenity;
	}

	public void setRelatedLv1AssetOuterIdenity(String relatedLv1AssetOuterIdenity) {
		this.relatedLv1AssetOuterIdenity = relatedLv1AssetOuterIdenity;
	}

	public String getRelatedLv2AssetUuid() {
		return relatedLv2AssetUuid;
	}

	public void setRelatedLv2AssetUuid(String relatedLv2AssetUuid) {
		this.relatedLv2AssetUuid = relatedLv2AssetUuid;
	}

	public String getRelatedLv2AssetOuterIdenity() {
		return relatedLv2AssetOuterIdenity;
	}

	public void setRelatedLv2AssetOuterIdenity(String relatedLv2AssetOuterIdenity) {
		this.relatedLv2AssetOuterIdenity = relatedLv2AssetOuterIdenity;
	}

	public String getRelatedLv3AssetUuid() {
		return relatedLv3AssetUuid;
	}

	public void setRelatedLv3AssetUuid(String relatedLv3AssetUuid) {
		this.relatedLv3AssetUuid = relatedLv3AssetUuid;
	}

	public String getRelatedLv3AssetOuterIdenity() {
		return relatedLv3AssetOuterIdenity;
	}

	public void setRelatedLv3AssetOuterIdenity(String relatedLv3AssetOuterIdenity) {
		this.relatedLv3AssetOuterIdenity = relatedLv3AssetOuterIdenity;
	}
	
	public void fillVouchers(String jvUuid, String bvUuid, String sourceDocumentUuid){
		this.journalVoucherUuid = jvUuid;
		this.businessVoucherUuid = bvUuid;
		this.sourceDocumentUuid = sourceDocumentUuid;
	}

	public String lastAccountName()
	{
		if(StringUtils.isEmpty(getThirdAccountName())==false) return getThirdAccountName();
		if(StringUtils.isEmpty(getSecondAccountName())==false) return getSecondAccountName();
		if(StringUtils.isEmpty(getFirstAccountName())==false) return getFirstAccountName();
		
		return "";
		
	}
	public boolean InTAccount(TAccountInfo account)
	{
		if(account==null) return false;
		if(this.getFirstAccountName()==null) return false;
		
		if(account.getFirstLevelAccount()==null) return false;
		if(!this.getFirstAccountName().equals(account.getFirstLevelAccount().getAccountName())) return false;
		if(!this.getFirstAccountUuid().equals(account.getFirstLevelAccount().getAccountCode())) return false;
		
		if(account.getSecondLevelAccount()!=null) 
		{
			if(!this.getSecondAccountName().equals(account.getSecondLevelAccount().getAccountName())) return false;
			if(!this.getSecondAccountUuid().equals(account.getSecondLevelAccount().getAccountCode())) return false;
		
			if(account.getThirdLevelAccount()!=null){
				if(!this.getThirdAccountName().equals(account.getThirdLevelAccount().getAccountName())) return false;
				if(!this.getThirdAccountUuid().equals(account.getThirdLevelAccount().getAccountCode())) return false;
			}
		}
		return true;
	}
	public AccountSide balanceSide()
	{
		if(this.getCreditBalance().compareTo(this.getDebitBalance())>0)
			return AccountSide.CREDIT;
		else if (this.getCreditBalance().compareTo(this.getDebitBalance())<0)
			return AccountSide.DEBIT;
		else
			return AccountSide.BOTH;
	}
	public void balanceAccount()
	{
		if(balanceSide()==AccountSide.CREDIT)
			this.setDebitBalance(this.getCreditBalance());
		else  if(balanceSide()==AccountSide.DEBIT)
			this.setCreditBalance(this.getDebitBalance());
	}
	
	public void balanceAccount_part(BigDecimal amount) throws OverFlowCarryOverException {
		BigDecimal current_balance=BigDecimal.ZERO;
		if(balanceSide()==AccountSide.CREDIT)
		{
			current_balance=this.getDebitBalance();
			current_balance=current_balance.add(amount);
			if(current_balance.compareTo(this.getCreditBalance())>0)
				throw new OverFlowCarryOverException();
			this.setDebitBalance(amount);
		}
		else  if(balanceSide()==AccountSide.DEBIT)
		{
			current_balance=this.getCreditBalance();
			current_balance=current_balance.add(amount);
			if(current_balance.compareTo(this.getDebitBalance())>0)
				throw new OverFlowCarryOverException();
			this.setCreditBalance(amount);
		}
	}
	
	public LedgerItem generateCounterLedgerWithSameAccount(AccountSide accountSide, BigDecimal amount, String journalVoucherUuid, String businessVoucherUuid, String sourceDocumentUuid ){
		LedgerItem newLedger = new LedgerItem();
		BeanUtils.copyProperties(this, newLedger, "id","creditBalance","debitBalance","accountSide",
				"ledgerUuid","bookInDate","journalVoucherUuid","businessVoucherUuid","sourceDocumentUuid","batchSerialUuid","backwardLedgerUuid","forwardLedgerUuid");
		newLedger.setLifeCycle(LedgerLifeCycle.BOOKED);
		newLedger.setBookInDate(new Date());	
	
		newLedger.setAccountSide(accountSide.ordinal());
		if(accountSide==AccountSide.DEBIT){
			newLedger.setCreditBalance(amount);
			newLedger.setDebitBalance(BigDecimal.ZERO);
		} else {
			newLedger.setCreditBalance(BigDecimal.ZERO);
			newLedger.setDebitBalance(amount);
		}
		newLedger.setJournalVoucherUuid(journalVoucherUuid);
		newLedger.setBusinessVoucherUuid(businessVoucherUuid);
		newLedger.setSourceDocumentUuid(sourceDocumentUuid);
		return newLedger;
	}

	BigDecimal getAbsoluteBalance() {
		return getDebitBalance().subtract(getCreditBalance()).abs();
	}
	

}
