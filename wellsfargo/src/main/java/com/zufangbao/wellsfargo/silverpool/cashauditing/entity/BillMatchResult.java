package com.zufangbao.wellsfargo.silverpool.cashauditing.entity;

import static com.zufangbao.sun.Constant.MatchScore.MATCH_BY_JOURNAL_VOUCHER;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;

public class BillMatchResult implements Serializable {
	
	public static final String ACCOUNT_AND_NAME_MAP = "accountAndNameMap";

	public static final String CONTRACT_NO = "contractNo";

	public static final String BILLING_PLAN_NAME = "billingPlanName";

	public static final String DUE_DATE = "dueDate";

	public static final String BILLING_PLAN_TYPE = "billingPlanType";

	public static final String BILLING_PLAN_NUMBER = "billingPlanNumber";
	public static final String SUBJECT_MATTER_SOURCE_NO = "subjectMatterSourceNo";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719147828695147863L;

	public static BillMatchResult createBillMatchResult(JournalVoucher journalVoucher,BusinessVoucher businessVoucher) {
		return new BillMatchResult(journalVoucher.getCashFlowUuid(), journalVoucher.getAccountSide(),
				journalVoucher.getBillingPlanUuid(), journalVoucher.getJournalVoucherUuid(), journalVoucher.getStatus(), journalVoucher.getBookingAmount(), businessVoucher.getBusinessVoucherUuid(),
				businessVoucher.getBusinessVoucherStatus(), businessVoucher.getSettlementAmount(), businessVoucher.getReceivableAmount(), MATCH_BY_JOURNAL_VOUCHER);
	}

	private String cashFlowUuid;
	
	private AccountSide accountSide;
	
	private Map<String,Object> showData = new HashMap<String,Object>();
	
	private String billingPlanUuid;
	
	private String journalVoucherUuid;
	private JournalVoucherStatus journalVoucherStatus;
	private BigDecimal bookingAmount;
	
	private String businessVoucherUuid;
	private BusinessVoucherStatus businessVoucherStatus;
	private BigDecimal settlementAmount;
	private BigDecimal receivableAmount;
	private BigDecimal currentSpecificAmount;
	
	private int score;
	
	public BillMatchResult(){
		
	}
	
	public BillMatchResult(String cashFlowUuid, AccountSide accountSide, String billingPlanUuid,
			String journalVoucherUuid,
			JournalVoucherStatus journalVoucherStatus, BigDecimal bookingAmount,
			String businessVoucherUuid,
			BusinessVoucherStatus businessVoucherStatus,
			BigDecimal settlementAmount, BigDecimal receivableAmount, int score) {
		super();
		this.accountSide = accountSide;
		this.billingPlanUuid = billingPlanUuid;
		this.journalVoucherUuid = journalVoucherUuid;
		this.journalVoucherStatus = journalVoucherStatus;
		this.bookingAmount = bookingAmount;
		this.businessVoucherUuid = businessVoucherUuid;
		this.businessVoucherStatus = businessVoucherStatus;
		this.settlementAmount = settlementAmount;
		this.receivableAmount = receivableAmount;
		this.currentSpecificAmount = getCurrentSpecificAmountFrom(this.bookingAmount, this.settlementAmount);
		this.score = score;
	}

	public BillMatchResult(String cashFlowUuid, AccountSide accountSide,String billingPlanUuid){
		this.cashFlowUuid = cashFlowUuid;
		this.accountSide = accountSide;
		this.billingPlanUuid = billingPlanUuid;
	}
	
	public void fillBillingPlanData(Order order, Map<String, String> accountAndNameMaps){
		if(order != null){
			this.showData.put(BILLING_PLAN_NAME,order.getSingleLoanContractNo());
			this.showData.put(BILLING_PLAN_TYPE,order.getOrderType().getChineseName());
			this.showData.put(DUE_DATE,order.getDueDate());
			this.showData.put(BILLING_PLAN_NUMBER, order.getOrderNo());
			this.showData.put(SUBJECT_MATTER_SOURCE_NO, order.getAssetSet().getContractNo());
			//Map<String,Object> appendix = billingPlan.getAppendix();
			/*if (appendix !=null){
				this.showData.put(SUBJECT_MATTER_SOURCE_NO, appendix.get(GeneralLeasingDocumentTypeDictionary.BILL_APPENDIX_SUBJECT_MATTER_PROPERTY_SOURCE_NO));
			}*/
			
			this.receivableAmount = order.getTotalRent();
		}
		this.showData.put(CONTRACT_NO, order.getAssetSet().getContractNo());
		/*Map<String,String> accountAndNameMaps = new HashMap<String,String>();
		if(tradeParty!=null){
			List<FinancialAccount> financialAccountList = tradeParty.getFinancialAccountBook();
			if(!CollectionUtils.isEmpty(financialAccountList)){
				for(FinancialAccount financialAccount:financialAccountList){
					if(financialAccount!=null){
						accountAndNameMaps.put(financialAccount.getAccount(), financialAccount.getAccountOwnerName());
					}
				}
			}
			
		}*/
		
		this.showData.put(ACCOUNT_AND_NAME_MAP,accountAndNameMaps);
	}

	public String getCashFlowUuid() {
		return cashFlowUuid;
	}

	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public Map<String, Object> getShowData() {
		return showData;
	}

	public void setShowData(Map<String, Object> showData) {
		this.showData = showData;
	}

	public String getBillingPlanUuid() {
		return billingPlanUuid;
	}

	public void setBillingPlanUuid(String billingPlanUuid) {
		this.billingPlanUuid = billingPlanUuid;
	}

	public String getJournalVoucherUuid() {
		return journalVoucherUuid;
	}

	public void setJournalVoucherUuid(String journalVoucherUuid) {
		this.journalVoucherUuid = journalVoucherUuid;
	}

	public JournalVoucherStatus getJournalVoucherStatus() {
		return journalVoucherStatus;
	}

	public void setJournalVoucherStatus(JournalVoucherStatus journalVoucherStatus) {
		this.journalVoucherStatus = journalVoucherStatus;
	}

	public BigDecimal getBookingAmount() {
		return bookingAmount;
	}

	public void setBookingAmount(BigDecimal bookingAmount) {
		this.bookingAmount = bookingAmount;
	}

	public String getBusinessVoucherUuid() {
		return businessVoucherUuid;
	}

	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}

	public BusinessVoucherStatus getBusinessVoucherStatus() {
		return businessVoucherStatus;
	}

	public void setBusinessVoucherStatus(BusinessVoucherStatus businessVoucherStatus) {
		this.businessVoucherStatus = businessVoucherStatus;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void fillVoucherPart(BillMatchResult resultInMysql) {
		if (resultInMysql == null){
			return;
		}
		this.journalVoucherUuid = resultInMysql.getJournalVoucherUuid();
		this.journalVoucherStatus = resultInMysql.getJournalVoucherStatus();
		this.businessVoucherUuid = resultInMysql.getBusinessVoucherUuid();
		this.businessVoucherStatus = resultInMysql.getBusinessVoucherStatus();
		this.receivableAmount = resultInMysql.getReceivableAmount();
		this.settlementAmount = resultInMysql.getSettlementAmount();
		this.bookingAmount = resultInMysql.getBookingAmount();
		this.currentSpecificAmount = getCurrentSpecificAmountFrom(this.bookingAmount, this.settlementAmount);
	}
	
	public void fillVoucherPart(BigDecimal issuedAmount) {
		this.settlementAmount = issuedAmount;
		this.currentSpecificAmount = getCurrentSpecificAmountFrom(this.bookingAmount, this.settlementAmount);
	}
	
	public BigDecimal getCurrentSpecificAmount() {
		return currentSpecificAmount;
	}

	public void setCurrentSpecificAmount(BigDecimal currentSpecificAmount) {
		this.currentSpecificAmount = currentSpecificAmount;
	}
	
	public BigDecimal getCurrentSpecificAmountFrom(BigDecimal bookingAmount, BigDecimal settlingAmount){
		if (bookingAmount == null){
			bookingAmount = BigDecimal.ZERO;
		}
		if (settlingAmount == null){
			settlingAmount = BigDecimal.ZERO;
		}
		return settlingAmount.subtract(bookingAmount);
	}
	
}
