package com.zufangbao.earth.yunxin.api.handler.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.ContractApiHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.utils.StringUtils;

@Component("contractApiHandler")
public class ContractApiHandlerImpl implements ContractApiHandler {

	@Autowired
	private ContractService contractService;

	@Override
	public Contract getContractBy(String uniqueId, String contractNo) throws ApiException {
		Contract contract = null;
		// TODO 是否需要判断 Contract 的条数
		if (!StringUtils.isEmpty(uniqueId)) {
			contract = contractService.getContractByUniqueId(uniqueId);
		}
		if (!StringUtils.isEmpty(contractNo)) {
			contract = contractService.getContractByContractNo(contractNo);
		}
		if (contract == null) {
			throw new ApiException(ApiResponseCode.CONTRACT_NOT_EXIST);
		}
		return contract;
	}

}
