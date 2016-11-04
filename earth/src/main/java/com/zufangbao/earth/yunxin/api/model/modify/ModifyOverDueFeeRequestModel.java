package com.zufangbao.earth.yunxin.api.model.modify;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.utils.CheckFormatUtils;

public class ModifyOverDueFeeRequestModel {
	
	private String requestNo;
	
	
	private String modifyOverDueFeeDetails;
	
	
	private String checkFailedMsg;
	
	
	

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}



	public boolean isValid() {

		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails = JsonUtils.parseArray(this.modifyOverDueFeeDetails, ModifyOverDueFeeDetail.class);
		if(CollectionUtils.isEmpty(modifyOverDueFeeDetails)){
			this.checkFailedMsg = "逾期费用明细解析失败！！！";
			return false;
		}
		for(ModifyOverDueFeeDetail modifyOverDueFeeDetail:modifyOverDueFeeDetails){
			
			if(StringUtils.isEmpty(modifyOverDueFeeDetail.getContractUniqueId())) {
				this.checkFailedMsg = "请求唯一标识［uniqueId］,不能为空！";
				return false;
			}
			if(StringUtils.isEmpty(modifyOverDueFeeDetail.getRepaymentPlanNo())) {
				this.checkFailedMsg = "还款计划编号［repaymentPlanNo］，不能为空！";
				return false;
			}
			if(StringUtils.isEmpty(modifyOverDueFeeDetail.getOverDueFeeCalcDate())|| DateUtils.asDay(modifyOverDueFeeDetail.getOverDueFeeCalcDate()) ==null) {
				this.checkFailedMsg = "请求唯一标识［overDueFeeCalcDate］，不能为空！";
				return false;
			}
			Map<String,BigDecimal> amountFieldsMap = CheckFormatUtils.getAllAmountsFields(modifyOverDueFeeDetail);
			
			for(String key: amountFieldsMap.keySet()){
				if(!CheckFormatUtils.checkRMBCurrencyBigDecimal(amountFieldsMap.get(key))){
					this.checkFailedMsg = "请求金额［"+key+"］，不能为空！";
				}
			}
			if(new BigDecimal(modifyOverDueFeeDetail.getTotalOverdueFee()).compareTo(modifyOverDueFeeDetail.calcTotalAmount()) != 0){
				this.checkFailedMsg = "修改逾期费用金额明细总额与总金额不相等！！！";
				return false;
			}
			
		}
		
		return true;
	}

	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}

	
	
	public String getModifyOverDueFeeDetails() {
		return modifyOverDueFeeDetails;
	}

	public void setModifyOverDueFeeDetails(String modifyOverDueFeeDetails) {
		this.modifyOverDueFeeDetails = modifyOverDueFeeDetails;
	}
	
	public List<ModifyOverDueFeeDetail> getModifyOverDueFeeDetailsParseJson(){
		if(StringUtils.isEmpty(this.modifyOverDueFeeDetails)){
			return Collections.EMPTY_LIST;
		}
		return JsonUtils.parseArray(this.modifyOverDueFeeDetails, ModifyOverDueFeeDetail.class);
	}
	
	public void setModifyOverDueFeeDetailsParseJson(List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails){
	    
		this.modifyOverDueFeeDetails = "{}";
		if(CollectionUtils.isNotEmpty(modifyOverDueFeeDetails)){
			this.modifyOverDueFeeDetails = JsonUtils.toJsonString(modifyOverDueFeeDetails);
		}
		
	}
	

	
	

}
