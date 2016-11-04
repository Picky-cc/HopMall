/**
 * 
 */
package com.suidifu.hathaway.voucher;

/**
 * @author wukai
 *
 */
public class VoucherParameter {

	private String sourceDocumentUuid;
	
	private Long sourceDocumentId;
	
	private Long sourceDocumentDetailId;
	
	private String sourceDocumentDetailUuid;
	
	private String financialContractNo;
	
	private String ledgerBookNo;
	
	private String contractUuid;
	
	public VoucherParameter() {
		super();
	}

	public VoucherParameter(String sourceDocumentUuid, Long sourceDocumentId,
			Long sourceDocumentDetailId, String sourceDocumentDetailUuid,
			String financialContractNo, String ledgerBookNo, String contractUuid) {
		super();
		this.sourceDocumentUuid = sourceDocumentUuid;
		this.sourceDocumentId = sourceDocumentId;
		this.sourceDocumentDetailId = sourceDocumentDetailId;
		this.sourceDocumentDetailUuid = sourceDocumentDetailUuid;
		this.financialContractNo = financialContractNo;
		this.ledgerBookNo = ledgerBookNo;
		this.contractUuid = contractUuid;
	}

	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}

	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}

	public Long getSourceDocumentId() {
		return sourceDocumentId;
	}

	public void setSourceDocumentId(Long sourceDocumentId) {
		this.sourceDocumentId = sourceDocumentId;
	}

	public Long getSourceDocumentDetailId() {
		return sourceDocumentDetailId;
	}

	public void setSourceDocumentDetailId(Long sourceDocumentDetailId) {
		this.sourceDocumentDetailId = sourceDocumentDetailId;
	}

	public String getSourceDocumentDetailUuid() {
		return sourceDocumentDetailUuid;
	}

	public void setSourceDocumentDetailUuid(String sourceDocumentDetailUuid) {
		this.sourceDocumentDetailUuid = sourceDocumentDetailUuid;
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}

	public String getContractUuid() {
		return contractUuid;
	}

	public void setContractUuid(String contractUuid) {
		this.contractUuid = contractUuid;
	}
}
