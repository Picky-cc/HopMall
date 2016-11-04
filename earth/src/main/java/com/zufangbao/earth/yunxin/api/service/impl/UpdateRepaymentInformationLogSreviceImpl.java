package com.zufangbao.earth.yunxin.api.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateRepaymentInformationLog;
import com.zufangbao.earth.yunxin.api.service.UpdateRepaymentInformationLogService;
import com.zufangbao.sun.entity.contract.Contract;


@Component("UpdateRepaymentInformationLogService")
public class UpdateRepaymentInformationLogSreviceImpl extends
		GenericServiceImpl<UpdateRepaymentInformationLog> implements
		UpdateRepaymentInformationLogService {

	@Override
	public void generateAndSaveUpdateRepaymentInformationLog(
			RepaymentInformationModifyModel modifyModel,
			HttpServletRequest request, Contract contract) {

		UpdateRepaymentInformationLog updateLog = new UpdateRepaymentInformationLog(
				modifyModel, request, contract);
		this.save(updateLog);

	}

	@Override
	public void checkRequestNo(String requestNo) throws ApiException {
		
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
	    List<UpdateRepaymentInformationLog> result = this.list(UpdateRepaymentInformationLog.class, filter);
	    if(CollectionUtils.isNotEmpty(result)){
	    	throw new ApiException(ApiResponseCode.REPEAT_REQUEST_NO);
	    }
	}

}
