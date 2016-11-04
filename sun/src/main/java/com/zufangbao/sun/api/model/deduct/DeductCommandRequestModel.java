package com.zufangbao.sun.api.model.deduct;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.CheckFormatUtils;

/**
 * 扣款指令模型
 * @author zhanghongbing
 *
 */
public class DeductCommandRequestModel {

	
	private String deductApplicationUuid = UUID.randomUUID().toString();
	
	/**
	 * 功能代码
	 */
	private String fn;
	
	/**
	 * 请求唯一标识
	 */
	private String requestNo;
	
	/**
	 * 扣款唯一编号
	 */
	private String deductId;
	
	/**
	 * 信托产品代码
	 */
	private String financialProductCode;
	
	/**
	 * 接口调用时间
	 */
	private String apiCalledTime;
	
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	
	/**
	 * 扣款金额
	 */
	private String amount;
	
	/**
	 * 还款类型
	 */
	private int  repaymentType;
	
	
	private String  repaymentDetails;
	
	private String checkFailedMsg;
	
	
	private List<RepaymentDetail> repaymentDetailList;
	
	
	@JSONField(serialize = false)
	public String getDeductApplicationUuid() {
		return deductApplicationUuid;
	}


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


	public void setDeductId(String deductId) {
		this.deductId = deductId;
	}


	public String getFinancialProductCode() {
		return financialProductCode;
	}


	public void setFinancialProductCode(String financialProductCode) {
		this.financialProductCode = financialProductCode;
	}


	public String getApiCalledTime() {
		return apiCalledTime;
	}


	public void setApiCalledTime(String apiCalledTime) {
		this.apiCalledTime = apiCalledTime;
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


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public int getRepaymentType() {
		return repaymentType;
	}


	public void setRepaymentType(int repaymentType) {
		this.repaymentType = repaymentType;
	}


	public String getRepaymentDetails() {
		return repaymentDetails;
	}


	public void setRepaymentDetails(String repaymentDetails) {
		this.repaymentDetails = repaymentDetails;
	}
	
	public List<RepaymentDetail> getRepaymentDetailArray(){

		if(CollectionUtils.isNotEmpty(this.repaymentDetailList)) {
			return repaymentDetailList;
		}
		this.repaymentDetailList = JsonUtils.parseArray(this.repaymentDetails, RepaymentDetail.class);
		
		if(CollectionUtils.isEmpty(repaymentDetailList)){
			return Collections.emptyList();
		}
		return repaymentDetailList;
	}


	public boolean isValid() {
		
		
		if(!(StringUtils.isEmpty(this.uniqueId) ^ StringUtils.isEmpty(this.contractNo))) {
			this.checkFailedMsg = "请选填其中一种编号［uniqueId，contractNo］！";
			return false;
		}
		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		if(StringUtils.isEmpty(deductId)){
			this.checkFailedMsg = "扣款唯一编号不能为空！！！";
			return false;
		}
		if(DateUtils.asDay(apiCalledTime) == null){
			this.checkFailedMsg = "接口调用时间,时间格式有误！！！";
			return false;
		}
		if(!CheckFormatUtils.checkRMBCurrency(amount) ){
			this.checkFailedMsg = "扣款金额格式有误！！！";
			return false;
		}
		if(repaymentTypeIsNull() ){
			this.checkFailedMsg = "还款类型格式错误！！！";
			return false;
		}
		List<RepaymentDetail> parseRepaymentDetails = getRepaymentDetailArray();
		if( CollectionUtils.isEmpty(parseRepaymentDetails)){
			this.checkFailedMsg = "还款明细不能为空！！！";
			return false;
		}
		for(RepaymentDetail checkedRepayment:parseRepaymentDetails ){
			if(!checkedRepayment.isValid()){
				this.checkFailedMsg = "还款计划明细金额不能为空！！！";
				return false;
			}
		}
		
		Set<String> repaymentPlanCodeSet=parseRepaymentDetails.stream().
				collect(Collectors.mapping(RepaymentDetail::getRepaymentPlanNo, Collectors.toSet()));
		if(repaymentPlanCodeSet.size()!=parseRepaymentDetails.size())
		{		this.checkFailedMsg = "还款明细中有重复还款计划编号！！！";
			return false;
		}
		
		return true;
		
	}


	private boolean repaymentTypeIsNull() {
		return RepaymentType.fromValue(this.repaymentType)==null;
	}





	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}


	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}


	public List<RepaymentDetail> getRepaymentDetailList() {
		return repaymentDetailList;
	}


	public void setRepaymentDetailList(List<RepaymentDetail> repaymentDetailList) {
		this.repaymentDetailList = repaymentDetailList;
	}


	public void setDeductApplicationUuid(String deductApplicationUuid) {
		this.deductApplicationUuid = deductApplicationUuid;
	}
	
	


	

}
