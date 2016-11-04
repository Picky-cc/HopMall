package com.zufangbao.earth.yunxin.api.service.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REPEAT_REQUEST_NO;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateAssetLog;
import com.zufangbao.earth.yunxin.api.service.UpdateAssetLogService;

@Service("updateAssetLogService")
public class UpdateAssetLogServiceImpl extends GenericServiceImpl<UpdateAssetLog> implements UpdateAssetLogService {

	@Override
	public void checkByRequestNo(String requestNo) throws ApiException {
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<UpdateAssetLog> result = this.list(UpdateAssetLog.class, filter);
		if(CollectionUtils.isNotEmpty(result)) {
			throw new ApiException(REPEAT_REQUEST_NO);
		}
	}

}
