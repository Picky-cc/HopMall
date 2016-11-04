package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.yunxin.entity.api.VoucherPayer;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
/**
 * 原始凭证明细
 * @author louguanyang
 *
 */
@Entity
@Table(name = "source_document_detail")
public class SourceDocumentDetail {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String uuid;
	
	private String sourceDocumentUuid;
	
	private String contractUniqueId;
	
	private String repaymentPlanNo;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.ORDINAL)
	private SourceDocumentDetailStatus status;
	
	/**
	 *  凭证来源
	 *  {@link VoucherSource}.key
	 */
	private String firstType;
	private String firstNo;//商户付款凭证：接口请求编号; 第三方扣款凭证：deductDetailUuid;
	/**
	 * 凭证类型
	 *  {@link VoucherType}.key
	 */
	private String secondType;
	private String secondNo;//商户付款凭证：外部打款流水号;第三方扣款凭证:deductDetailApplicationUuid;
	
	@Enumerated(EnumType.ORDINAL)
	private VoucherPayer payer;
	
	/**
	 * 收款账户号
	 */
	private String receivableAccountNo;
	/**
	 * 付款账户号
	 */
	private String paymentAccountNo;
	/**
	 * 付款银行帐户名称
	 */
	private String paymentName;
	/**
	 * 付款银行名称
	 */
	private String paymentBank;
	
	/**
	 * 校验状态
	 */
	@Enumerated(EnumType.ORDINAL)
	private SourceDocumentDetailCheckState checkState = SourceDocumentDetailCheckState.UNCHECKED;
	
	/**
	 * 备注（校验失败错误信息）
	 */
	private String comment;

	public SourceDocumentDetail() {
		super();
	}
	
	public SourceDocumentDetail(String sourceDocumentUuid, String contractUniqueId, String repaymentPlanNo,
			BigDecimal amount, SourceDocumentDetailStatus status, String firstType, String firstNo, String secondType,
			String secondNo, VoucherPayer payer, String receivableAccountNo, String paymentAccountNo,
			String paymentName, String paymentBank) {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.sourceDocumentUuid = sourceDocumentUuid;
		this.contractUniqueId = contractUniqueId;
		this.repaymentPlanNo = repaymentPlanNo;
		this.amount = amount;
		this.status = status;
		this.firstType = firstType;
		this.firstNo = firstNo;
		this.secondType = secondType;
		this.secondNo = secondNo;
		this.payer = payer;
		this.receivableAccountNo = receivableAccountNo;
		this.paymentAccountNo = paymentAccountNo;
		this.paymentName = paymentName;
		this.paymentBank = paymentBank;
		this.checkState = SourceDocumentDetailCheckState.UNCHECKED;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public VoucherPayer getPayer() {
		return payer;
	}

	public void setPayer(VoucherPayer payer) {
		this.payer = payer;
	}

	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

	public String getFirstNo() {
		return firstNo;
	}

	public void setFirstNo(String firstNo) {
		this.firstNo = firstNo;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getSecondNo() {
		return secondNo;
	}

	public void setSecondNo(String secondNo) {
		this.secondNo = secondNo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	
	public String getContractUniqueId() {
		return contractUniqueId;
	}

	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public SourceDocumentDetailStatus getStatus() {
		return status;
	}

	public void setStatus(SourceDocumentDetailStatus status) {
		this.status = status;
	}

	public SourceDocumentDetailCheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(SourceDocumentDetailCheckState checkState) {
		this.checkState = checkState;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void appendComment(String msg) {
		StringBuffer buffer = new StringBuffer(comment);
		buffer.append(",");
		buffer.append(msg);
		this.comment = buffer.toString();
	}

	public boolean isUncheck() {
		return getCheckState() == SourceDocumentDetailCheckState.UNCHECKED;
	}

	public boolean is_business_payment_voucher() {
		return StringUtils.equals(VoucherSource.BUSINESS_PAYMENT_VOUCHER.getKey(), firstType);
	}

	public boolean is_third_party_deduction_voucher() {
		return StringUtils.equals(VoucherSource.THIRD_PARTY_DEDUCTION_VOUCHER.getKey(), firstType);
	}
	
	public boolean is_active_payment_voucher() {
		return StringUtils.equals(VoucherSource.ACTIVE_PAYMENT_VOUCHER.getKey(), firstType);
	}

	public boolean isInvalid() {
		return this.getStatus() == SourceDocumentDetailStatus.INVALID;
	}
	
}
