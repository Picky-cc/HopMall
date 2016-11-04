package com.zufangbao.sun.ledgerbook;

public class VoucherSet {

	private String journalVoucherUuid;
	private String businessVoucherUuid;
	private String sourceDocumentUuid;
	public VoucherSet(String journalVoucherUuid, String businessVoucherUuid,
			String sourceDocumentUuid) {
		super();
		this.journalVoucherUuid = journalVoucherUuid;
		this.businessVoucherUuid = businessVoucherUuid;
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	public String getJournalVoucherUuid() {
		return journalVoucherUuid;
	}
	public void setJournalVoucherUuid(String journalVoucherUuid) {
		this.journalVoucherUuid = journalVoucherUuid;
	}
	public String getBusinessVoucherUuid() {
		return this.businessVoucherUuid;
	}
	public void setBusinessVoucherUuid(String businessVoucherUuid) {
		this.businessVoucherUuid = businessVoucherUuid;
	}
	public String getSourceDocumentUuid() {
		return this.sourceDocumentUuid;
	}
	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	
	
	
}
