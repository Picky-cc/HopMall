package com.zufangbao.sun.yunxin.entity.model.api;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 逾期扣款结果查询模型
 * @author zhanghongbing
 *
 */
public class OverdueDeductResultQueryModel {
	
	private String fn;

	/**
	 * 请求编号
	 */
	private String requestNo;

	/**
	 * 查询请求编号
	 */
	private String deductId;
	
	
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	
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

	public String getDeductId() {
		return deductId;
	}

	public void setDeductId(String queryRequestNo) {
		this.deductId = queryRequestNo;
	}

	
	@JSONField(serialize = false)
	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@JSONField(serialize = false)
	public boolean isValid() {
		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		
		if(StringUtils.isEmpty(this.deductId) && StringUtils.isEmpty(this.uniqueId)) {
			this.checkFailedMsg = "请选填其中一种编号［deductId，uniqueId］！";
			return false;
		}
		return true;
	}

}
