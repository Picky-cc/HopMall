package com.zufangbao.earth.yunxin.api.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateAssetLog;

public interface UpdateAssetLogService extends GenericService<UpdateAssetLog>{

	void checkByRequestNo(String requestNo) throws ApiException;

}
