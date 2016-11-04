package com.zufangbao.earth.yunxin.api.model.modify;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.StringUtils;


public class ImportAssetPackageRequestModel {

	/**
	 * 功能代码
	 */
	private String fn;
	
	/**
	 * 请求唯一标识
	 */
	private String requestNo;
	
	/**
	 * 导入资产包请求内容
	 */
	private String importAssetPackageContent;
	/**
	 * 校验失败信息
	 */
	private String checkFailedMsg;
	


	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getImportAssetPackageContent() {
		return importAssetPackageContent;
	}

	public void setImportAssetPackageContent(String importAssetPackageContent) {
		this.importAssetPackageContent = importAssetPackageContent;
	}
	
	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}
	
	
	public boolean  isValid(){
		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
	    ImportAssetPackageContent content = JsonUtils.parse(importAssetPackageContent, ImportAssetPackageContent.class);
		
		if(content == null){
			this.checkFailedMsg = "请求参数解析错误";
			return false;
		}
		return true;
	}
	
	public ImportAssetPackageContent getRequestContentObject(){
		
		return  JsonUtils.parse(importAssetPackageContent, ImportAssetPackageContent.class);
	}
	
	}
