package com.zufangbao.earth.yunxin.api.service.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REPEAT_REQUEST_NO;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageLog;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;
import com.zufangbao.earth.yunxin.api.service.ImportAssetPackageLogService;
import com.zufangbao.sun.utils.FileUtils;


@Service("importAssetPackageLogService")
public class ImportAssetPackageLogServiceImpl extends GenericServiceImpl<ImportAssetPackageLog> implements ImportAssetPackageLogService  {
	
	
	
	@Override
	public void checkByRequestNo(String requestNo) throws ApiException {
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<ImportAssetPackageLog> result = this.list(ImportAssetPackageLog.class, filter);
		if(CollectionUtils.isNotEmpty(result)) {
			throw new ApiException(REPEAT_REQUEST_NO);
		}
	}

	@Override
	public void generateImportAssetPackageLog(ImportAssetPackageRequestModel model, HttpServletRequest request) {
		
		ImportAssetPackageLog importLog = new ImportAssetPackageLog();
		importLog.setFnCode(model.getFn());
		importLog.setIp(IpUtil.getIpAddress(request));
//		importLog.setRequestData(model.getImportAssetPackageContent());
		importLog.setRequestNo(model.getRequestNo());
		importLog.setCreateTime(new Date());
		this.save(importLog);
	}

}
