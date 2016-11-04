package com.zufangbao.earth.yunxin.api.service;

import javax.servlet.http.HttpServletRequest;

import com.demo2do.core.service.GenericService;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateRepaymentInformationLog;
import com.zufangbao.sun.entity.contract.Contract;

public interface UpdateRepaymentInformationLogService  extends GenericService<UpdateRepaymentInformationLog>{
	
	public  void  generateAndSaveUpdateRepaymentInformationLog(RepaymentInformationModifyModel modifyModel,
			HttpServletRequest request, Contract contract);
	
	public void checkRequestNo(String requestNo) throws ApiException;
	
}
