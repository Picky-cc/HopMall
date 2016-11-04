package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchQueryModel;

public interface LoanBatchService extends GenericService<LoanBatch> {

	public LoanBatch getLoanBatchBycode(String code);

	public LoanBatch createAndSaveLoanBatch(FinancialContract financialContract, boolean activeStatus);

	public void activationLoanBatch(LoanBatch loanBatch);
	
	public LoanBatch getLoanBatchByUUid(String Uuid);

	public List<LoanBatch> queryLoanBatchIdsByQueryModel(LoanBatchQueryModel loanBatchQueryModel, Page page);

	public int countLoanBatchList(LoanBatchQueryModel loanBatchQueryModel);

}
