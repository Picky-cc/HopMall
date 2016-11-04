package com.zufangbao.earth.yunxin.api.dataSync.task;

import java.util.List;
import java.util.UUID;

import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncRequestModel;

public class SyncRequestData {
	
	
	private String requestId;
	
	private List<DataSyncRequestModel>  SDFDatas;

	public SyncRequestData(List<DataSyncRequestModel> requestModels) {
		this.requestId = UUID.randomUUID().toString();
		this.SDFDatas = requestModels;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<DataSyncRequestModel> getSDFDatas() {
		return SDFDatas;
	}

	public void setSDFDatas(List<DataSyncRequestModel> requestModels) {
		this.SDFDatas = requestModels;
	}
	
	
}
