package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;

public interface BusinessPaymentVoucherSession {
	public void handler_compensatory_loan_asset_recover();

	public boolean compensatory_recover_loan_assets(List<Long> sourceDocumentDetailIds,
			Long sourceDocumentId);
	
	public boolean sourceDocumentDetailValidator(List<Long> detailIds, String financialContractNo, String ledgerBookNo);

	public void single_compensatory_recover_loan_asset(List<Long> sourceDocumentDetailIds, SourceDocument sourceDocument);
}
