package com.suidifu.coffer.handler;

import java.util.Map;

import com.suidifu.coffer.entity.DebitModel;
import com.suidifu.coffer.entity.DebitResult;

public interface UnionpayHandler {

	/**
	 * 批量代收
	 * @param debitModel
	 * @return
	 */
	public DebitResult batchDebit(DebitModel batchDebitModel, Map<String, String> workParms);
	
	/**
	 * 实时代收
	 * @param debitModel
	 * @return
	 */
	public DebitResult realTimeDebit(DebitModel realTimeDebitModel, Map<String, String> workParms);
	
	/**
	 * 批量代收查询
	 * @param batchDebitModel
	 * @return
	 */
	public DebitResult debitQuery(DebitModel debitQueryModel, Map<String, String> workParms);
	
	/**
	 * 交易详情查询
	 * @param transactionDetailQueryModel
	 * @return
	 */
	public DebitResult transactionDetailQuery(DebitModel transactionDetailQueryModel, Map<String, String> workParms);
	
	/**
	 * 实时放款
	 * @param reanTimePaymentModel
	 * @return
	 */
	public DebitResult realTimePayment(DebitModel reanTimePaymentModel, Map<String, String> workParms);
}
