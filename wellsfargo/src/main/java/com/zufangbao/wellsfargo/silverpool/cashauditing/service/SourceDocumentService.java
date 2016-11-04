package com.zufangbao.wellsfargo.silverpool.cashauditing.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.model.AccountsPrepaidModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;

public interface SourceDocumentService extends GenericService<SourceDocument> {

	SourceDocument getSourceDocumentBy(String sourceDocumentUuid);
	
	SourceDocument getIssuedSourceDocumentBy(Long financeCompanyId, Long outlierCompanyId, String outlierDocumentUuid);
	
	SourceDocument getSourceDocumentBy(Long financeCompanyId, Long outlierCompanyId, String outlierDocumentUuid);
	
	SourceDocument getSourceDocumentBy(Long companyId, String outlierDocumentUuid);
	
	void signSourceDocument(SourceDocument sourceDocument, BigDecimal issuedAmount);
	
	public boolean existsBatchPayRecordUuid(String batchPayRecordUuid, AccountSide sourceAccountSide, Long companyId);
	public List<SourceDocument> getSourceDocuments(SourceDocumentType sourceDocumentType, AccountSide sourceAccountSide, Long companyId, SourceDocumentStatus sourceDocumentStatus, String firstOutlier);
	public SourceDocument getOneSourceDocuments(SourceDocumentType sourceDocumentType, AccountSide sourceAccountSide, SourceDocumentStatus sourceDocumentStatus, String firstOutlierType, String outlierDocumentUuid);
	public void createSourceDocumentBy(Long companyId, OfflineBill offlineBill);
	
	List<SourceDocument> getSourceDocumentByOfflineBillUuid(String offlineBillUuid);
	List<SourceDocument> getSourceDocumentListBy(Collection<String> sourceDocumentUuids, SourceDocumentStatus sourceDocumentStatus);
	Set<String> extractOutlierDocumentUuid(List<SourceDocument> sourceDocumentList, String firstOutlierDocType);
	SourceDocument getBusinessPaymentSourceDocument(Long financeCompanyId, Long outlierCompanyId, String outlierSerialGlobalIdentity);
	
	public List<SourceDocument> getDepositReceipt(String cashFlowUuid);
	public List<SourceDocument> getSourceDocumentList(AccountsPrepaidModel accountsPrepaidModel,Page page);
	public int count(AccountsPrepaidModel accountsPrepaidModel);
	
	public List<Long> get_deposit_source_document_connected_by(SourceDocumentExcuteResult excuteResult,SourceDocumentExcuteStatus excuteStatus);

	void cancelSourceDocumentDetailAttach(String sourceDocumentUuid, String bankTransactionNo);
	
	public SourceDocument getSourceDocumentBy(Long id);
	
	public void update_after_inter_account_transfer(Long id, BigDecimal totalAmount);
	
}
