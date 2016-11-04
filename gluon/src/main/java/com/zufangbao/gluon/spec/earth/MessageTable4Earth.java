package com.zufangbao.gluon.spec.earth;

import java.util.Hashtable;

import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4AutorisedBillSketch;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4Contract;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4ContractPartical;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4Order;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4OrderVirutalAccount;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4ParticalSystem;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4Receivables;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4RentNotify;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorCode4WellsFargo;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4AutorisedBillSketch;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4Contract;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4ContractPartical;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4Order;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4OrderVirutalAccount;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4ParticalSystem;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4Receivables;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4RentNotify;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4WellsFargo;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.gluon.spec.global.MessageTable;

public class MessageTable4Earth {

	private static Hashtable<Integer, String> table = init();
	
	private static Hashtable<Integer, String> init(){
		
		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		table.putAll(MessageTable.getTable());
		
		table.put(GlobalSpec4Earth.ERROR_EMPTY_APPID, GlobalSpec4Earth.MSG_EMPTY_APPID);
		table.put(GlobalSpec4Earth.ERROR_NOT_EXIST_APP, GlobalSpec4Earth.MSG_NOT_EXIST_APP);
		table.put(GlobalSpec4Earth.ERROR_ILLEGAL_PARAMS, GlobalSpec4Earth.MSG_ILLEGAL_PARAMS);
	
		table.put(GlobalSpec4Earth.ERROR_PARTICAL_SYSTEM, ErrorMsg4ParticalSystem.MSG_PARTICAL_SYSTEM);
		table.put(ErrorCode4ParticalSystem.ERROR_TRY_TO_CREATE_DUPLICATE_PARTICALS, ErrorMsg4ParticalSystem.MSG_TRY_TO_CREATE_DUPLICATE_PARTICALS);
		table.put(ErrorCode4ParticalSystem.ERROR_TRY_TO_CREATE_PARTICALS_WITH_INVALID_DATA, ErrorMsg4ParticalSystem.MSG_TRY_TO_CREATE_PARTICALS_WITH_INVALID_DATA);
		table.put(ErrorCode4ParticalSystem.ERROR_PARTICAL_NOT_FOUND, ErrorMsg4ParticalSystem.MSG_PARTICAL_NOT_FOUND);
		table.put(ErrorCode4ParticalSystem.ERROR_PARTICALMODEL_NOT_FOUND, ErrorMsg4ParticalSystem.MSG_PARTICALMODEL_NOT_FOUND);
		
		table.put(ErrorCode4ContractPartical.ERROR_EXIST_CONTRACT_PARTICAL, ErrorMsg4ContractPartical.MSG_EXIST_CONTRACT_PARTICAL);
		table.put(ErrorCode4ContractPartical.ERROR_NOTEXIST_CONTRACT_PARTICAL, ErrorMsg4ContractPartical.MSG_NOTEXIST_CONTRACT_PARTICAL);
		
		table.put(ErrorCode4AutorisedBillSketch.ERROR_GENERATE_BILL_UNIQUEID, ErrorMsg4AutorisedBillSketch.MSG_GENERATE_BILL_UNIQUEID);
		table.put(ErrorCode4AutorisedBillSketch.ERROR_EMPTY_AUTHORISED_BILLSKETCH, ErrorMsg4AutorisedBillSketch.MSG_EMPTY_AUTHORISED_BILLSKETCH);
		table.put(ErrorCode4AutorisedBillSketch.ERROR_EMPTY_UNIQUE_BILLIDS, ErrorMsg4AutorisedBillSketch.MSG_EMPTY_UNIQUE_BILLIDS);
		table.put(ErrorCode4AutorisedBillSketch.ERROR_NOT_EXIST_ORDER, ErrorMsg4AutorisedBillSketch.MSG_NOT_EXIST_ORDER);
		table.put(ErrorCode4AutorisedBillSketch.ERROR_EMPTY_HOT_BILL_SKETCHS, ErrorMsg4AutorisedBillSketch.MSG_EMPTY_HOT_BILL_SKETCHS);
		table.put(ErrorCode4AutorisedBillSketch.ERROR_BEGIN_OR_MAX_INVALID, ErrorMsg4AutorisedBillSketch.MSG_BEGIN_OR_MAX_INVALID);
		
		table.put(ErrorCode4Receivables.ERROR_CONFIRM_TRANSACTION_RECORD_FAIL, ErrorMsg4Receivables.MSG_CONFIRM_TRANSACTION_RECORD_FAIL);
		
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_ACCOUNT_UNIQUE_ID_EMPTY, ErrorMsg4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_ACCOUNT_UNIQUE_ID_EMPTY);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_PARTICAL_UNIQUE_ID_EMPTY, ErrorMsg4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_PARTICAL_UNIQUE_ID_EMPTY);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_OREDER_ID_INVALID, ErrorMsg4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_OREDER_ID_INVALID);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_OREDER_ID_LIST_EMPTY, ErrorMsg4OrderVirutalAccount.ERROR_ILLEGAL_PARAM_OREDER_ID_LIST_EMPTY);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_ALREADY_BINDED, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_ALREADY_BINDED);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_ALREADY_UNBINDED, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_ALREADY_UNBINDED);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_NOT_EXIST, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_NOT_EXIST);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_PARSE_ORDER_ID_LIST_IN_JSON, ErrorMsg4OrderVirutalAccount.ERROR_PARSE_ORDER_ID_LIST_IN_JSON);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_VIRTUAL_ACCOUNT_NOT_EXIST, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_VIRTUAL_ACCOUNT_NOT_EXIST);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_BIND_STATUS_EXCEPTION, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_BIND_STATUS);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_ORDER_UNBIND_STATUS_EXCEPTION, ErrorMsg4OrderVirutalAccount.ERROR_ORDER_UNBIND_STATUS);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_VIRTUALACCOUNT_NOT_IN_THE_SAME_PARTICAL, ErrorMsg4OrderVirutalAccount.ERROR_VIRTUALACCOUNT_NOT_IN_THE_SAME_PARTICAL);
		table.put(ErrorCode4OrderVirutalAccount.ERROR_VIRTUALACCOUNT_REMOTE_TRANSFER, ErrorMsg4OrderVirutalAccount.ERROR_VIRTUALACCOUNT_REMOTE_TRANSFER);
		
		table.put(ErrorCode4Contract.ERROR_NO_SUCH_CONTRACT, ErrorMsg4Contract.ERROR_NO_SUCH_CONTRACT);
		table.put(ErrorCode4Contract.ERROR_CONTRACT_ALREADY_CLOSED, ErrorMsg4Contract.ERROR_CONTRACT_ALREADY_CLOSED);
		table.put(ErrorCode4Contract.ERROR_EXIST_NOT_CLOSED_ORDER, ErrorMsg4Contract.ERROR_EXIST_NOT_CLOSED_ORDER);
		
		table.put(ErrorCode4Order.ERROR_EMPTY_ORDER_NO, ErrorMsg4Order.ERROR_EMPTY_ORDER_NO);
		table.put(ErrorCode4Order.ERROR_NO_SUCH_OREDER, ErrorMsg4Order.ERROR_NO_SUCH_OREDER);
		table.put(ErrorCode4Order.ERROR_ORDER_ALREADY_CLOSED, ErrorMsg4Order.ERROR_ORDER_ALREADY_CLOSED);
		
		table.put(ErrorCode4RentNotify.ERROR_NO_SMS_SERVICE_SETTING, ErrorMsg4RentNotify.ERROR_NO_SMS_SERVICE_SETTING);
		
		table.put(ErrorCode4WellsFargo.ERROR_NO_SUBJECT_MATTER, ErrorMsg4WellsFargo.ERROR_NO_SUBJECT_MATTER);
		table.put(ErrorCode4WellsFargo.ERROR_NOT_EXIST_BUSINESS_CONTRACT, ErrorMsg4WellsFargo.ERROR_NOT_EXIST_BUSINESS_CONTRACT);
		table.put(ErrorCode4WellsFargo.ERROR_NOT_EXIST_UNDERLYING_ASSET, ErrorMsg4WellsFargo.ERROR_NOT_EXIST_UNDERLYING_ASSET);
		table.put(ErrorCode4WellsFargo.ERROR_PUSH_DOCUMENT_SLICE_FAIL, ErrorMsg4WellsFargo.ERROR_PUSH_DOCUMENT_SLICE_FAIL);
		table.put(ErrorCode4WellsFargo.ERROR_REMOVE_ASSET_FAIL, ErrorMsg4WellsFargo.ERROR_REMOVE_ASSET_FAIL);
		table.put(ErrorCode4WellsFargo.ERROR_ADD_ASSET_FAIL, ErrorMsg4WellsFargo.ERROR_ADD_ASSET_FAIL);
		table.put(ErrorCode4WellsFargo.ERROR_AUDIT_ASSET_NOT_EXIST_ASSET, ErrorMsg4WellsFargo.ERROR_AUDIT_ASSET_NOT_EXIST_ASSET);
		table.put(ErrorCode4WellsFargo.ERROR_AUDIT_ASSET_NO_PERMISSIONS, ErrorMsg4WellsFargo.ERROR_AUDIT_ASSET_NO_PERMISSIONS);
		table.put(ErrorCode4WellsFargo.ERROR_AUDIT_ASSET_DATA_OUT_OF_DATE, ErrorMsg4WellsFargo.ERROR_AUDIT_ASSET_DATA_OUT_OF_DATE);
		
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_BUSINESS_CONTRACT , ErrorMsg4WellsFargo.ERROR_CREATE_BUSINESS_CONTRACT);
		table.put(ErrorCode4WellsFargo.ERROR_LEASING_BUSINESS_CONTRACT_FORM , ErrorMsg4WellsFargo.ERROR_LEASING_BUSINESS_CONTRACT_FORM);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_CONTRACT_WRONG_PARTY_CONCERNED , ErrorMsg4WellsFargo.ERROR_CREATE_CONTRACT_WRONG_PARTY_CONCERNED);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_CONTRACT_WRONG_TRADE_PARTY , ErrorMsg4WellsFargo.ERROR_CREATE_CONTRACT_WRONG_TRADE_PARTY);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_CONTRACT_IN_CREATE_PAYMENTTERM , ErrorMsg4WellsFargo.ERROR_CREATE_CONTRACT_IN_CREATE_PAYMENTTERM);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_BUSINESS_CONTRACT_DETAIL,ErrorMsg4WellsFargo.ERROR_CREATE_BUSINESS_CONTRACT_DETAIL);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_BUSINESS_CREATING_BILLINGPLAN,ErrorMsg4WellsFargo.ERROR_CREATE_BUSINESS_CREATING_BILLINGPLAN);
		table.put(ErrorCode4WellsFargo.ERROR_CREATE_BUSINESS_DUPLICATE_CREATATION,ErrorMsg4WellsFargo.ERROR_CREATE_BUSINESS_DUPLICATE_CREATATION);
		table.put(ErrorCode4WellsFargo.ERROR_END_BUSINESS,ErrorMsg4WellsFargo.ERROR_END_BUSINESS);
		
		table.put(ErrorCode4WellsFargo.ERROR_NO_UNDERLYING_ASSET , ErrorMsg4WellsFargo.ERROR_NO_UNDERLYING_ASSET);
		
		table.put(ErrorCode4WellsFargo.ERROR_NO_PARTY_CONCERNED , ErrorMsg4WellsFargo.ERROR_NO_PARTY_CONCERNED);

		table.put(ErrorCode4WellsFargo.ERROR_NO_TRADE_PARTY ,ErrorMsg4WellsFargo.ERROR_NO_TRADE_PARTY);
		
		table.put(ErrorCode4WellsFargo.ERROR_NO_EXIST_SUBJECT_MATTER ,ErrorMsg4WellsFargo.ERROR_NO_EXIST_SUBJECT_MATTER);
		
		return table;
	}
	
	public static String getMessage(int key){
		
		return table.getOrDefault(key, GlobalMsgSpec.MSG_FAILURE);
	}
	
	public static String getMessage(int key, Exception e) {
		if(e instanceof CommonException) {
			return ((CommonException)e).getErrorMsg();
		}else{
			return MessageTable4Earth.getMessage(key);
		}
	}
}
