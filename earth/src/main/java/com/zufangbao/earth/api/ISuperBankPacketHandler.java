package com.zufangbao.earth.api;

import java.util.List;
import java.util.Map;

/**
 * 超级网银报文处理handler
 * 
 * @author zjm
 *
 */
public interface ISuperBankPacketHandler {

	/**
	 * 生成批量贷记发送报文
	 * @param creditedDetailList
	 * @param config
	 * @return
	 */
	String generateCreditedPacket(List<CreditedModel> creditedDetailList, Map<String, String> config);
	
	/**
	 * 解析批量贷记响应报文
	 * @param responseResult
	 * @return
	 * @throws ResponseParseException 
	 */
	List<CreditedResult> parseCreditedPacket(String responseResult) throws ResponseParseException;

	/**
	 * 生成查询支付状态报文
	 * @param queryPaymentStatusModel
	 * @return
	 */
	String generateQueryTransactionPacket(QueryTransactionModel queryTransactionModel, Map<String, String> config);
	
	/**
	 * 解析查询支付状态报文
	 * @param responseResult
	 * @return
	 * @throws ResponseParseException 
	 */
	List<QueryTransactionResult> parseQueryTransactionPacket(String responseResult) throws ResponseParseException;
}
