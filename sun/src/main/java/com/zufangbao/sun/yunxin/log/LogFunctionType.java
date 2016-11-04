package com.zufangbao.sun.yunxin.log;

import org.apache.commons.lang.StringUtils;

public enum LogFunctionType {
	
	
	
	LOGIN("enum.log-function-type.login"),
	
	ASSETPACKAGEIMPORT("enum.log-function-type.assetpackage-import"),
	
	BATCHMANAGEMENT("enum.log-function-type.batch-management"),
	
	ACTIVELOANBATCH("enum.log-function-type.active-loan-batch"),
	
	DELETELOANBATCH("enum.log-function-type.delete-loan-batch"),
	
	EXPORTREPAYEMNTPLAN("enum.log-function-type.export-repayment-plan"),
	
	ADDFINANCIALCONTRACT("enum.log-function-type.add-financial-contract"),
	
	EDITFINANCIALCONTRACT("enum.log-function-type.edit-financial-contract"),
	
	ONLINEBILLEXPORTCHECKING("enum.log-function-type.online-bill-export-checking"),
	
	ONLINEBILLEXPORTDAILYRETURNLIST("enum.log-function-type.online-bill-export-daily-return-list"),
	
    EDITORDER("enum.log-function-type.edit-order"),
	
    ADDOFFLINEBILL("enum.log-function-type.add-offline-bill"),
    
    OFFLINEBILLASSOCIATE("enum.log-function-type.offline-bill-assciate"),
    
	GUARANTEEEXPORT("enum.log-function-type.guarantee-export"),
	
	GUARANTEELAPSE("enum.log-function-type.guarantee-lapse"),
	
	GUARANTEEACTIVE("enum.log-function-type.guarantee-active"),
	
	ASSET_SET_COMMENT("enum.log-function-type.assetset-comment"),
	
	ASSET_SET_OVERDUE("enum.log-function-type.assetset-overdue"),
	
	JOURNAL_VOUCHER_AUDIT("enum.log-function-type.journal-voucher-audit"),
	
	CONTRACT_INVALIDATE("enum.log-function-type.contract-invalidate"),
	
	RESEND_REMITTANCE("enum.log-function-type.resend-remittance"),
	
	ACTIVE_PAYMENT_VOUCHER_CREATE("enum.log-function-type.active-payment-voucher-create");
	
	private String value;
	
	private LogFunctionType(String value){
		
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public int getOrdinal(){
		return this.ordinal();
	}
	public static LogFunctionType  fromValue(String value){
		
		for(LogFunctionType item : LogFunctionType.values()){
			
			if (StringUtils.equals(value, item.getValue())) {

				return item;
			}
		}
		return null;
	}
}
