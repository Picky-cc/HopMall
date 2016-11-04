package com.zufangbao.earth.yunxin.api.handler;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.sun.entity.contract.Contract;

public interface ContractApiHandler {

	public Contract getContractBy(String uniqueId, String contractNo) throws ApiException;

}
