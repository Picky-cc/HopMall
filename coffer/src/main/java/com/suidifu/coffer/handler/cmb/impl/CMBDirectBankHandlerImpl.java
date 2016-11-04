package com.suidifu.coffer.handler.cmb.impl;

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
import com.suidifu.coffer.handler.cmb.CMBDirectBankHandler;
import com.suidifu.coffer.handler.cmb.CMBDirectBankPacketHandler;

@Component("cmbDirectBankHandler")
public class CMBDirectBankHandlerImpl extends GenericHandler implements
		CMBDirectBankHandler {

	@Autowired
	private CMBDirectBankPacketHandler cmbDirectBankPacketHandler;

	@Override
	public CreditResult singleCredit(CreditModel creditModel,
			Map<String, String> workParms) {
		try {
			String requestPacket = cmbDirectBankPacketHandler.generateSingleCreditPacket(creditModel, workParms);
			String responsePacket = sendHttpRequest(requestPacket, workParms);
			return cmbDirectBankPacketHandler.parseSingleCreditPacket(responsePacket, workParms);
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
			String requestPacket = cmbDirectBankPacketHandler.generateQueryCreditPacket(queryCreditModel, workParms);
			String responsePacket = sendHttpRequest(requestPacket, workParms);

			return cmbDirectBankPacketHandler.parseQueryCreditPacket(responsePacket, workParms);
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
		// TODO Auto-generated method stub
		return null;
	}
}
