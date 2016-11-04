package com.zufangbao.earth.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.demo2do.core.utils.StringUtils;

/**
 * 
 * @author zjm
 *
 */
@Component("CMBSuperBankPacketHandler")
public class CMBSuperBankPacketHandlerImpl implements CMBSuperBankPacketHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String generateCreditedPacket(List<CreditedModel> creditedDetailList, Map<String, String> config) {
		
		CMBXmlPacket xmlPkt = new CMBXmlPacket(config.getOrDefault("CreditedCode", "NTIBCOPR").toString(), config.getOrDefault("LGNNAM", "").toString());
		
		Map mpPodInfo = new Properties();
		mpPodInfo.put("BUSMOD", config.getOrDefault("BUSMOD", "00001"));
		xmlPkt.putProperty("NTOPRMODX", mpPodInfo);
		
		for(int i = 0; i < creditedDetailList.size(); i ++) {
			CreditedModel creditedDetail = creditedDetailList.get(i);
			Map mpPayInfo = new Properties();
			mpPayInfo.put("SQRNBR", createSequenceNo(i + 1));
			mpPayInfo.put("BBKNBR", creditedDetail.getPayingBankNo());
			mpPayInfo.put("ACCNBR", creditedDetail.getPayerAccountNo());
			mpPayInfo.put("YURREF", creditedDetail.getTransactionUuid());
			mpPayInfo.put("CCYNBR", creditedDetail.getCurrencyCode());
			mpPayInfo.put("TRSAMT", creditedDetail.getTransactionAmount());
			mpPayInfo.put("CDTNAM", creditedDetail.getPayeeAccountName());
			mpPayInfo.put("CDTEAC", creditedDetail.getPayeeAccountNo());
			mpPayInfo.put("CDTBRD", creditedDetail.getDueBankNo());
			mpPayInfo.put("RMKTXT", creditedDetail.getPostscript());
			
			mpPayInfo.put("CNVNBR", config.get("ProtocolNo"));
			mpPayInfo.put("TRSTYP", config.getOrDefault("BusinessType", "C210"));
			mpPayInfo.put("TRSCAT", config.getOrDefault("BusinessClass", "09001"));
			
			xmlPkt.putProperty("NTIBCOPRX", mpPayInfo);
		}
		
		return xmlPkt.toXmlString();
	}
	
	
	private String createSequenceNo(int i) {
		
		return String.format("%010d", i);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CreditedResult> parseCreditedPacket(String responseResult) throws ResponseParseException {
		
		if(StringUtils.isEmpty(responseResult)) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_PACKET_PARSE_ERROR);
		}
		
		CMBXmlPacket pktRsp = CMBXmlPacket.valueOf(responseResult);
		
		if(null == pktRsp) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_PACKET_PARSE_ERROR);
		}
		
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_TRANSACTION_ERROR + pktRsp.getERRMSG());
		}
		
		List<CreditedResult> resultList = new ArrayList<CreditedResult>();
		int resultSize = pktRsp.getSectionSize("NTOPRRTNZ");
		for (int i = 0; i < resultSize; i++) {
			Map resultDetail = pktRsp.getProperty("NTOPRRTNZ", i);
			CreditedResult creditedResult = new CreditedResult();
			creditedResult.setResultCode(resultDetail.getOrDefault("ERRCOD", "").toString());
			creditedResult.setErrorMsg(resultDetail.getOrDefault("ERRTXT", "").toString());
			creditedResult.setRequestStatus(resultDetail.getOrDefault("REQSTS", "").toString());
			resultList.add(creditedResult);
		}
		
		return resultList;
	}


	private CMBXmlPacket parsePacket(String responseResult) throws ResponseParseException {
		if(StringUtils.isEmpty(responseResult)) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_PACKET_PARSE_ERROR);
		}
		
		CMBXmlPacket pktRsp = CMBXmlPacket.valueOf(responseResult);
		
		if(null == pktRsp) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_PACKET_PARSE_ERROR);
		}
		return pktRsp;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String generateQueryTransactionPacket(QueryTransactionModel queryTransactionModel, Map<String, String> config) {
		
		CMBXmlPacket xmlPkt = new CMBXmlPacket(config.getOrDefault("QueryTransactionCode", "NTQRYEBP").toString(), config.getOrDefault("LGNNAM", "").toString());

		Map mpQueryInfo = new Properties();
		mpQueryInfo.put("BGNDAT", queryTransactionModel.getBeginDate());
		mpQueryInfo.put("ENDDAT", queryTransactionModel.getEndDate());
		mpQueryInfo.put("YURREF", queryTransactionModel.getTransactionUuid());
		
		xmlPkt.putProperty("NTWAUEBPY", mpQueryInfo);
		
		return xmlPkt.toXmlString();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<QueryTransactionResult> parseQueryTransactionPacket(String responseResult) throws ResponseParseException {
		
		CMBXmlPacket pktRsp = parsePacket(responseResult);
		
		if(pktRsp.isError()) {
			throw new ResponseParseException(BankCorpMsgSpec.MSG_QUERY_ERROR + pktRsp.getRETCOD() + ":" + pktRsp.getERRMSG());
		}
		
		List<QueryTransactionResult> resultRecordList = new ArrayList<QueryTransactionResult>(); 
		int recordSize = pktRsp.getSectionSize("NTWAUEBPZ");
		for (int i = 0; i < recordSize; i++) {
			Map recordDetail = pktRsp.getProperty("NTWAUEBPZ", i);
			QueryTransactionResult resultRecord = new QueryTransactionResult();
			resultRecord.setTransactionUuid(recordDetail.get("YURREF").toString());
			resultRecord.setAccountNo(recordDetail.get("ACCNBR").toString());
			resultRecord.setCurrencyCode(recordDetail.get("CCYNBR").toString());
			resultRecord.setTransactionAmount(recordDetail.get("TRSAMT").toString());
			resultRecord.setOperateDate(recordDetail.get("OPRDAT").toString());
			resultRecord.setRequestStatus(recordDetail.get("REQSTS").toString());
			resultRecord.setProcessResult(recordDetail.get("RTNFLG").toString());
			resultRecordList.add(resultRecord);
		}
		
		return resultRecordList;
	}


}
