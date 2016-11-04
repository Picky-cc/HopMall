package com.zufangbao.earth.yunxin.api.handler;

import java.text.ParseException;

import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;
import com.zufangbao.earth.yunxin.api.model.response.ImportAssetPackageResponseModel;

public interface ImportAssetPackageApiHandler {

	public ImportAssetPackageResponseModel importAssetPackage(ImportAssetPackageRequestModel model) throws ParseException, Exception;
	
}
