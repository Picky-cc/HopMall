package com.zufangbao.wellsfargo.yunxin.handler.vouchertask;

import java.util.List;

import com.suidifu.hathaway.voucher.VoucherParameter;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.ledgerbook.LedgerBook;

public interface BusinessPaymentVoucherTaskHandler {

	//task
	boolean compensatory_recover_loan_asset_detail(Long sourceDocumentDetailId, String sourceDocumentNo, LedgerBook book, VirtualAccount companyVirtualAccount);

	//task
	boolean sourceDocumentDetailValidatorSingle(Long detailId, String financialContractNo, String ledgerBookNo);
	
	boolean sourceDocumentDetailValidatorList(List<VoucherParameter> voucherParamters);

}