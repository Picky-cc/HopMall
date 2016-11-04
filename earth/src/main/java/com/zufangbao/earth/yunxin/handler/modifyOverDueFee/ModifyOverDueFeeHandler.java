package com.zufangbao.earth.yunxin.handler.modifyOverDueFee;

import javax.servlet.http.HttpServletRequest;

import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeRequestModel;

public interface ModifyOverDueFeeHandler {

	public void modifyOverDueFeeAndCheckData(ModifyOverDueFeeRequestModel model, HttpServletRequest request);
	
}

