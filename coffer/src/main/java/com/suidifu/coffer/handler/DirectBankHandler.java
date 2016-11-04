package com.suidifu.coffer.handler;

import java.util.List;
import java.util.Map;

import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.CreditModel;
import com.suidifu.coffer.entity.CreditResult;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.entity.QueryCreditModel;
import com.suidifu.coffer.entity.QueryCreditResult;

public interface DirectBankHandler {

	/**
	 * 单条贷记（打款）
	 * @param creditModel
	 * @param workParms
	 * @return
	 */
	public CreditResult singleCredit(CreditModel creditModel, Map<String, String> workParms);
	/**
	 * 批量贷记（打款）
	 * @param creditModelList
	 * @return
	 */
	public List<CreditResult> batchCredit(List<CreditModel> creditModelList, Map<String, String> workParms);
	/**
	 * 贷记状态查询接口
	 * @param queryCreditModel
	 * @param workParms
	 * @return
	 */
	public QueryCreditResult queryCredit(QueryCreditModel queryCreditModel, Map<String, String> workParms);
	/**
	 * 查询当日明细
	 * @param queryCashFlowModel
	 * @return
	 */
	public CashFlowResultModel queryIntradayCashFlow(QueryCashFlowModel queryCashFlowModel, Map<String, String> workParms);
}
