package com.suidifu.coffer.handler.pab.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suidifu.coffer.GlobalSpec.ErrorMsg;
import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.CreditModel;
import com.suidifu.coffer.entity.CreditResult;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.entity.QueryCreditModel;
import com.suidifu.coffer.entity.QueryCreditResult;
import com.suidifu.coffer.exception.RequestDataException;
import com.suidifu.coffer.exception.ResponseParseException;
import com.suidifu.coffer.handler.GenericHandler;
import com.suidifu.coffer.handler.pab.PABDirectBankHandler;
import com.suidifu.coffer.handler.pab.PABDirectBankPacketHandler;
@Component("pabDirectBankHandler")
public class PABDirectBankHandlerImpl extends GenericHandler implements PABDirectBankHandler {

	@Autowired
	private PABDirectBankPacketHandler pabDirectBankPacketHandler;

	@Override
	public CreditResult singleCredit(CreditModel creditModel,
			Map<String, String> workParms) {
		try {
			String requestPacket = pabDirectBankPacketHandler.generateSingleCreditPacket(creditModel, workParms);
			System.out.println("requestPacket:" + requestPacket);
			String responsePacket = sendSocketRequest(requestPacket, workParms);
			System.out.println("responsePacket:" + responsePacket);
			return pabDirectBankPacketHandler.parseSingleCreditPacket(responsePacket, workParms);
		} catch (RequestDataException e) {
			e.printStackTrace();
			return new CreditResult(e.getMessage());
		} catch (ResponseParseException e) {
			e.printStackTrace();
			return new CreditResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new CreditResult(ErrorMsg.ERR_SYSTEM_EXCEPTION);
		}
	}

	@Override
	public List<CreditResult> batchCredit(List<CreditModel> creditModelList,
			Map<String, String> workParms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryCreditResult queryCredit(QueryCreditModel queryCreditModel,
			Map<String, String> workParms) {
		try {
			String requestPacket = pabDirectBankPacketHandler.generateQueryCreditPacket(queryCreditModel, workParms);
			String responsePacket = sendSocketRequest(requestPacket, workParms);

			return pabDirectBankPacketHandler.parseQueryCreditPacket(responsePacket, workParms);
		} catch (RequestDataException e) {
			e.printStackTrace();
			return new QueryCreditResult(e.getMessage());
		} catch (ResponseParseException e) {
			e.printStackTrace();
			return new QueryCreditResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new QueryCreditResult(ErrorMsg.ERR_SYSTEM_EXCEPTION);
		}
	}

	@Override
	public CashFlowResultModel queryIntradayCashFlow(
			QueryCashFlowModel queryCashFlowModel, Map<String, String> workParms) {
		try {
			//CashFlowResultModel cashFlowResultModel = CashFlowResultModel
			
			String requestPacket = pabDirectBankPacketHandler.generateQueryIntradayCashFlowPacket(queryCashFlowModel, workParms);
			String responsePacket = sendSocketRequest(requestPacket, workParms);
			
			//CashFlowResultModel cashFlowResultModel = pabDirectBankPacketHandler.parseQueryIntradayCashFlowPacket(responsePacket, workParms);

//			if(cashFlowResultModel.isHasNextPage()) {
//				
//			}
			return pabDirectBankPacketHandler.parseQueryIntradayCashFlowPacket(responsePacket, workParms);
		} catch (RequestDataException e) {
			e.printStackTrace();
			return new CashFlowResultModel(e.getMessage());
		} catch (ResponseParseException e) {
			e.printStackTrace();
			return new CashFlowResultModel(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new CashFlowResultModel(ErrorMsg.ERR_SYSTEM_EXCEPTION);
		}
	}
	
}
