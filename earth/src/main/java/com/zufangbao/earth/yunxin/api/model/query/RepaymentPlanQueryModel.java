package com.zufangbao.earth.yunxin.api.model.query;

import com.zufangbao.sun.utils.StringUtils;


public class RepaymentPlanQueryModel {

	private String uniqueId;

	private String requestNo;

	private String contractNo;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public boolean isValid() {
		return uniqueIdOrContractNoHasOne();
	}

	private boolean uniqueIdOrContractNoHasOne() {
		return ((StringUtils.isEmpty(uniqueId) ^ StringUtils.isEmpty(contractNo))
				&& !StringUtils.isEmpty(requestNo));
			
	}

}
