package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheet;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetListModel;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetUpdateModel;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.yunxin.entity.model.TransactionLimitQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

public interface BankTransactionLimitSheetService extends GenericService<BankTransactionLimitSheet>{
	
	boolean modifyinvalidTime(PaymentInstitutionName paymentInstitutionName,String outlierChannelName, AccountSide accountSide);
	
	public List<String> getAllBanks(List<String> paymentChannelUuids);

	public int queryCount(TransactionLimitQueryModel queryModel);

	public List<BankTransactionLimitSheet> query(TransactionLimitQueryModel queryModel, Page page);

	boolean modifyTransactionLimit(BankTransactionLimitSheetUpdateModel bankTransactionLimitSheetUpdateModel);
	
	public List<BankTransactionLimitSheet> getBankTransactionLimitSheetBy(PaymentInstitutionName paymentInstitutionName, AccountSide accountSide, String outlierChannelName, Page page);

	public int getBankTransactionLimitSheetCountBy(String paymentChannelUuid,AccountSide accountSide);
}
