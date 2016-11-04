package com.zufangbao.earth.yunxin.handler;

import java.util.List;
import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.model.CreateFinancialContractModel;
import com.zufangbao.sun.yunxin.entity.model.financialcontract.FinancialContractQueryModel;

public interface FinancialContractHandler {
	public FinancialContract editFinancialContract(
			CreateFinancialContractModel createFinancialContractModel,
			Long financialContractId);
	
	public FinancialContract createFinancialContract(CreateFinancialContractModel createFinancialContractModel );

	public Map<String, Object> query(FinancialContractQueryModel financialContractQueryModel, Page page);

	public List<FinancialContract> getFinancialContractList(Long financialContractId);
	
}
