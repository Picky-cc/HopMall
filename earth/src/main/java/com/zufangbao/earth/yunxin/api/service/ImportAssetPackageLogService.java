package com.zufangbao.earth.yunxin.api.service;

import javax.servlet.http.HttpServletRequest;

import com.demo2do.core.service.GenericService;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageLog;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;

public interface ImportAssetPackageLogService   extends GenericService<ImportAssetPackageLog>{

	
	public void checkByRequestNo(String requestNo) throws ApiException;
	
	public void generateImportAssetPackageLog(ImportAssetPackageRequestModel model, HttpServletRequest request);
}
