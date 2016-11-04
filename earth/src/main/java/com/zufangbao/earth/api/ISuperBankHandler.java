package com.zufangbao.earth.api;

import java.util.List;

import com.zufangbao.earth.api.exception.UnknownUkeyException;

/**
 * 
 * @author zjm
 *
 */
public interface ISuperBankHandler {

	/**
	 * 批量贷记
	 * @param creditedDetailList
	 * @return
	 * @throws ResponseParseException 
	 * @throws UnknownUkeyException 
	 */
	public List<CreditedResult> batchCredited(List<CreditedModel> creditedDetailList) throws ResponseParseException, UnknownUkeyException;
	
	/**
	 * 查询交易
	 * @param queryTransactionModel
	 * @return
	 * @throws ResponseParseException 
	 * @throws UnknownUkeyException 
	 */
	public List<QueryTransactionResult> queryTransaction(QueryTransactionModel queryTransactionModel) throws ResponseParseException, UnknownUkeyException;
}
