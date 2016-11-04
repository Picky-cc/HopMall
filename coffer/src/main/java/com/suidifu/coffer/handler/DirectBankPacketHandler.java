package com.suidifu.coffer.handler;

import java.util.List;
import java.util.Map;

import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.CreditModel;
import com.suidifu.coffer.entity.CreditResult;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.entity.QueryCreditModel;
import com.suidifu.coffer.entity.QueryCreditResult;
import com.suidifu.coffer.exception.RequestDataException;
import com.suidifu.coffer.exception.ResponseParseException;

public interface DirectBankPacketHandler {

	/**
	 * 生成单条贷记报文
	 * @param creditModel
	 * @param workParms
	 * @return
	 * @throws RequestDataException 
	 */
	String generateSingleCreditPacket(CreditModel creditModel, Map<String, String> workParms) throws RequestDataException;
	/**
	 * 生成批量贷记报文
	 * @param creditModelList
	 * @param workParms
	 * @return
	 */
	String generateBatchCreditPacket(List<CreditModel> creditModelList, Map<String, String> workParms);
	/**
	 * 解析单条贷记报文
	 * @param responseResult
	 * @return
	 * @throws ResponseParseException
	 */
	CreditResult parseSingleCreditPacket(String responsePacket, Map<String, String> workParms) throws ResponseParseException;
	/**
	 * 解析批量贷记报文
	 * @param responseResult
	 * @return
	 * @throws ResponseParseException
	 */
	List<CreditResult> parseBatchCreditPacket(String responsePacket) throws ResponseParseException;
	/**
	 * 生成贷记状态查询报文
	 * @param queryCreditModel
	 * @return
	 * @throws RequestDataException 
	 */
	String generateQueryCreditPacket(QueryCreditModel queryCreditModel, Map<String, String> workParms) throws RequestDataException;
	/**
	 * 解析贷记状态查询报文
	 * @param responseResult
	 * @return
	 * @throws ResponseParseException
	 */
	QueryCreditResult parseQueryCreditPacket(String responsePacket, Map<String, String> workParms) throws ResponseParseException;
	/**
	 * 生成查询当日明细报文
	 * @param queryCashFlowModel
	 * @param workParms
	 * @return
	 * @throws RequestDataException
	 */
	String generateQueryIntradayCashFlowPacket(QueryCashFlowModel queryCashFlowModel, Map<String, String> workParms) throws RequestDataException;
	/**
	 * 解析查询当日明细报文
	 * @param responsePacket
	 * @param workParms
	 * @return
	 * @throws ResponseParseException
	 */
	CashFlowResultModel parseQueryIntradayCashFlowPacket(String responsePacket, Map<String, String> workParms) throws ResponseParseException;
}
