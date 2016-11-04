package com.zufangbao.wellsfargo.greypool.document.entity.busidocument.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.contract.SimplifiedContractInfo;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.handler.BusinessContractHandler;

@Component("businessContractHandler")
public class BusinessContractHandlerImpl implements BusinessContractHandler {

	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractAccountService contractAccountService;
	
	@Override
	public List<SimplifiedContractInfo> getSimpliedContractInfoByMatch(String keyword) {
		List<ContractAccount> contractAccountList = contractAccountService.getContractAccountsByMatch(keyword);
		List<SimplifiedContractInfo> simplifiedContractInfoList = new ArrayList<SimplifiedContractInfo>();
		for (ContractAccount contractAccount : contractAccountList) {
			SimplifiedContractInfo simplifiedContractInfo = new SimplifiedContractInfo(contractAccount.getContract());
			simplifiedContractInfoList.add(simplifiedContractInfo);
		}
		return simplifiedContractInfoList;
	}
	
	
	
}
