package com.zufangbao.earth.yunxin.api.service.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REPEAT_REQUEST_NO;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.service.PrepaymentApplicationService;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentApplication;

@Service("prepaymentApplicationService")
public class PrepaymentApplicationServiceImpl extends GenericServiceImpl<PrepaymentApplication> implements PrepaymentApplicationService {

	@Override
	public void checkByRequestNo(String requestNo) throws ApiException {
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<PrepaymentApplication> result = this.list(PrepaymentApplication.class, filter);
		if(CollectionUtils.isNotEmpty(result)) {
			throw new ApiException(REPEAT_REQUEST_NO);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PrepaymentApplication getUniquePrepaymentApplicationByRepaymentPlanUuid(
			String repaymentPlanUuid) {
		if(StringUtils.isEmpty(repaymentPlanUuid)) {
			return null;
		}
		
		String hql = "FROM PrepaymentApplication WHERE assetSetUuid =:repaymentPlanUuid";
		List<PrepaymentApplication> prepaymentApplicationList = this.genericDaoSupport.searchForList(hql, "repaymentPlanUuid", repaymentPlanUuid);
		if(CollectionUtils.isNotEmpty(prepaymentApplicationList)) {
			return prepaymentApplicationList.get(0);
		}
		
		return null;
	}

}
