package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;

@Entity
public class BusinessVoucher {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String businessVoucherUuid;

	private String businessVoucherTypeUuid;
	
	/**
	 * 代收单类型
	 */
	private String billingPlanTypeUuid;
	/**
	 * 代收单uid
	 */
	private String billingPlanUuid;
	
	@Column(columnDefinition = "text")
	private String billingPlanBreif;
	/**
	 * 发票
	 */
	private String taxInvoiceUuid;
	/**
	 * 制证状态: 0:待制证,1:制证中,2:已制证,3:已作废
	 */
	@Enumerated(EnumType.ORDINAL)
	private BusinessVoucherStatus businessVoucherStatus;
	/**
	 * 应收金额
	 */
	private BigDecimal receivableAmount;
	
	/**
	 * 结算金额
	 */
	private BigDecimal settlementAmount;
	
	/**
	 * 0:贷方1:借方
	 */
	@Enumerated(EnumType.ORDINAL)
	private AccountSide accountSide;
	
	private Long companyId;
	
	private Long accountId;
	
	public BusinessVoucher() {
		super();
	}

	public BusinessVoucher(String businessVoucherTypeUuid,
			String billingPlanUuid,
			String billingPlanBreif,
			BusinessVoucherStatus businessVoucherStatus, BigDecimal recievableAmount,
			BigDecimal settlementAmount, AccountSide accountSide, long companyId, long accountId, String billingPlanTypeUuid) {
		super();
		this.businessVoucherTypeUuid = businessVoucherTypeUuid;
		this.billingPlanUuid = billingPlanUuid;
		this.billingPlanBreif = billingPlanBreif;
		this.businessVoucherStatus = businessVoucherStatus;
		this.receivableAmount = recievableAmount;
		this.settlementAmount = settlementAmount;
		this.accountSide = accountSide;
		this.companyId = companyId;
		this.accountId = accountId;
		this.billingPlanTypeUuid = billingPlanTypeUuid;
		this.businessVoucherUuid = UUID.randomUUID().toString();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessVoucherUuid() {
		return businessVoucherUuid;
	}

	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}

	public String getBusinessVoucherTypeUuid() {
		return businessVoucherTypeUuid;
	}

	public void setBusinessVoucherTypeUuid(String businessVoucherTypeUuid) {
		this.businessVoucherTypeUuid = businessVoucherTypeUuid;
	}

	public String getSourceDocumentTypeUuid() {
		return billingPlanTypeUuid;
	}

	public void setSourceDocumentTypeUuid(String sourceDocumentTypeUuid) {
		this.billingPlanTypeUuid = sourceDocumentTypeUuid;
	}

	public String getSourceDocumentUuid() {
		return billingPlanUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.billingPlanUuid = sourceDocumentUuid;
	}

	public String getSourceDocumentBreif() {
		return billingPlanBreif;
	}

	public void setSourceDocumentBreif(String sourceDocumentBreif) {
		this.billingPlanBreif = sourceDocumentBreif;
	}

	public String getTaxInvoiceUuid() {
		return taxInvoiceUuid;
	}

	public void setTaxInvoiceUuid(String taxInvoiceUuid) {
		this.taxInvoiceUuid = taxInvoiceUuid;
	}

	public BusinessVoucherStatus getBusinessVoucherStatus() {
		return businessVoucherStatus;
	}

	public void setBusinessVoucherStatus(BusinessVoucherStatus businessVoucherStatus) {
		this.businessVoucherStatus = businessVoucherStatus;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getBillingPlanTypeUuid() {
		return billingPlanTypeUuid;
	}

	public void setBillingPlanTypeUuid(String billingPlanTypeUuid) {
		this.billingPlanTypeUuid = billingPlanTypeUuid;
	}

	public String getBillingPlanUuid() {
		return billingPlanUuid;
	}

	public void setBillingPlanUuid(String billingPlanUuid) {
		this.billingPlanUuid = billingPlanUuid;
	}

	public String getBillingPlanBreif() {
		return billingPlanBreif;
	}

	public void setBillingPlanBreif(String billingPlanBreif) {
		this.billingPlanBreif = billingPlanBreif;
	}
	
	public void updateStatus(BigDecimal issuedAmount) {
		settlementAmount = issuedAmount;
		if(settlementAmount.compareTo(receivableAmount) == 0) {
			businessVoucherStatus = BusinessVoucherStatus.VOUCHER_ISSUED;
		} else {
			businessVoucherStatus = BusinessVoucherStatus.VOUCHER_ISSUING;
		}
	}

	public void addSettlementAmountAndBusinessVoucherStatus(BigDecimal bookingAmount) throws IllegalInputAmountException {
		if(bookingAmount == null) {
			bookingAmount = BigDecimal.ZERO;
		}
		BigDecimal issuedAmount = bookingAmount.add(settlementAmount);
		if(issuedAmount.compareTo(receivableAmount) == 1) {
			throw new IllegalInputAmountException();
		}
		updateStatus(issuedAmount);
	}
	
	public boolean isBusinessVoucherIssued() {
		return businessVoucherStatus == BusinessVoucherStatus.VOUCHER_ISSUED;
	}
	
	public boolean isBusinessVoucherNotIssued() {
		return businessVoucherStatus != BusinessVoucherStatus.VOUCHER_ISSUED;
	}
}
