package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

public class BusinessDocumentOffSetParam {
	public String ledgerBookNo;
	public String firstPartyUuid;
	public String businessDocument;
	public String businessDocumentType;
	public String sourceDocumentUuid;
	public String sourceDocumentDetailUuid;

	public BusinessDocumentOffSetParam(String ledgerBookNo,
			String firstPartyUuid, String businessDocument,
			String businessDocumentType, String sourceDocumentUuid,
			String sourceDocumentDetailUuid) {
		this.ledgerBookNo = ledgerBookNo;
		this.firstPartyUuid = firstPartyUuid;
		this.businessDocument = businessDocument;
		this.businessDocumentType = businessDocumentType;
		this.sourceDocumentUuid = sourceDocumentUuid;
		this.sourceDocumentDetailUuid = sourceDocumentDetailUuid;
	}
}