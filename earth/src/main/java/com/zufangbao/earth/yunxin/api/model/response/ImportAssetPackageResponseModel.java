package com.zufangbao.earth.yunxin.api.model.response;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;

public class ImportAssetPackageResponseModel {

	private String successTime; 
	
	private String requestBatchCode;
	
	private String asseptBatchCode;
	
	
	public  ImportAssetPackageResponseModel(){
		
	}

	public ImportAssetPackageResponseModel(String requestNo, String asseptCode) {
		this.successTime = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:SS ");
		this.requestBatchCode = requestNo;
		this.asseptBatchCode = asseptCode;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public String getRequestBatchCode() {
		return requestBatchCode;
	}

	public void setRequestBatchCode(String requestBatchCode) {
		this.requestBatchCode = requestBatchCode;
	}

	public String getAsseptBatchCode() {
		return asseptBatchCode;
	}

	public void setAsseptBatchCode(String asseptBatchCode) {
		this.asseptBatchCode = asseptBatchCode;
	}
	
}
