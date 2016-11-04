package com.zufangbao.sun.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.handler.IDirectBankHandler;
import com.zufangbao.sun.handler.ScanCashFlowHandler;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.USBKeyService;
import com.zufangbao.sun.utils.DirectBankHandlerFactory;

@Component("directbankScanCashFlowHandler")
public class DirectbankScanCashFlowHandlerImpl implements ScanCashFlowHandler {

	private static final Log logger = LogFactory.getLog(DirectbankScanCashFlowHandlerImpl.class);
	
	@Autowired
	private AppService appService;
	@Autowired
	private USBKeyService usbKeyService;
	@Autowired
	private FinancialContractService financialContractService;
	@Override
	public void scan_cash_flow_by(Long financialContractId) {
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, financialContractId);
		if(financialContract==null){
			return;
		}
		Account account = financialContract.getCapitalAccount();
		if(!account.isScanCashFlowSwitch()){
			return;
		}
		logger.info("san flow begin, account["+account.getAccountNo()+"]");
		Company company = financialContract.getCompany();
		//存入的app应该为所属app的流水，所以存入的app为  (类型为金融公司，名称为yunxin)的app。
		App app = appService.getAppByCompanyId(company.getId());
		USBKey usbKey = usbKeyService.getUSBKeyByUUID(account.getUsbUuid());
		if (usbKey == null) {
			logger.error("scan company[" + company.getFullName()
					+ "],accountNo[ " + account.getAccountNo()
					+ "], error[" + "USBKey未正确设置" + "]");
			return;
		}
		
		IDirectBankHandler directBankHandler = DirectBankHandlerFactory.newInstance(usbKey.getBankCode());
		directBankHandler.scanBankCashFlow(account, app, usbKey);
	}

}
