package com.zufangbao.earth.api;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.api.exception.RequestDataException;
import com.zufangbao.earth.api.exception.UnknownUkeyException;
import com.zufangbao.sun.entity.directbank.USBKey;

/**
 * 
 * @author zjm
 *
 */
@Component("CMBSuperBankHandler")
public class CMBSuperBankHandlerImpl extends GenericSuperBankHandler implements CMBSuperBankHandler {
	
	@Autowired
	private USBKeyHandler usbKeyHandler;
	@Autowired
	private CMBSuperBankPacketHandler cmbSuperBankPacketHandler;

	@Override
	public List<CreditedResult> batchCredited(List<CreditedModel> creditedDetailList) throws ResponseParseException, UnknownUkeyException {
		
		if(CollectionUtils.isEmpty(creditedDetailList)) {
			throw new RequestDataException(BankCorpMsgSpec.MSG_CREDIT_EMPTY_ERROR);
		}
		
		return sendBatchCreditedRequest(creditedDetailList);
	}
	
	
	private List<CreditedResult> sendBatchCreditedRequest(List<CreditedModel> creditedDetailList) throws ResponseParseException, UnknownUkeyException {
		
		USBKey usbKey = usbKeyHandler.getUSBKeyByPayerAccount(creditedDetailList.get(0).getPayerAccountNo(), GatewayType.SuperBank);
		
		if(null == usbKey) {
			throw new UnknownUkeyException(BankCorpMsgSpec.MSG_UNKNOWN_USBKEY_ERROR);
		}
		
		Map<String, String> config = usbKey.getConfig();
		
		String requestPacketData = cmbSuperBankPacketHandler.generateCreditedPacket(creditedDetailList, config);
		//System.out.println("send:" + requestPacketData);
		String responseResult = sendHttpRequest(requestPacketData, config);
		//String responseResult = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CMBSDKPGK><INFO><DATTYP>2</DATTYP><ERRMSG></ERRMSG><FUNNAM>NTIBCOPR</FUNNAM><LGNNAM>张建明suidifu</LGNNAM><RETCOD>0</RETCOD></INFO><NTOPRDRTZ><RTNTIM>006</RTNTIM></NTOPRDRTZ><NTOPRDRTZ><RTNTIM>006</RTNTIM></NTOPRDRTZ><NTOPRRTNZ><ERRCOD>SUC0000</ERRCOD><REQNBR>0582821687</REQNBR><REQSTS>BNK</REQSTS><SQRNBR>0000000001</SQRNBR></NTOPRRTNZ><NTOPRRTNZ><ERRCOD>SUC0000</ERRCOD><REQNBR>0582821689</REQNBR><REQSTS>BNK</REQSTS><SQRNBR>0000000002</SQRNBR></NTOPRRTNZ></CMBSDKPGK>";
		return cmbSuperBankPacketHandler.parseCreditedPacket(responseResult);
	}


	@Override
	public List<QueryTransactionResult> queryTransaction(QueryTransactionModel queryTransactionModel) throws ResponseParseException, UnknownUkeyException {
		if(null == queryTransactionModel) {
			throw new RequestDataException(BankCorpMsgSpec.MSG_NULL_REQUEST_ERROR);
		}
		
		USBKey usbKey = usbKeyHandler.getUSBKeyByPayerAccount(queryTransactionModel.getAccountNo(), GatewayType.SuperBank);
		
		if(null == usbKey) {
			throw new UnknownUkeyException(BankCorpMsgSpec.MSG_UNKNOWN_USBKEY_ERROR);
		}
		
		Map<String, String> config = usbKey.getConfig();
		
		String requestPacketData = cmbSuperBankPacketHandler.generateQueryTransactionPacket(queryTransactionModel, config);
		
		String responseResult = sendHttpRequest(requestPacketData, config);
		
		return cmbSuperBankPacketHandler.parseQueryTransactionPacket(responseResult);
	}
	
	
}
