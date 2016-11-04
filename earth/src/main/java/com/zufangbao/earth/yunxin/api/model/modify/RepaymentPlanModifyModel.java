package com.zufangbao.earth.yunxin.api.model.modify;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.INVALID_INTEREST_AMOUNT;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.INVALID_PRINCIPAL_AMOUNT;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.utils.StringUtils;

/**
 * 还款计划变更模型
 * @author zhanghongbing
 *
 */
public class RepaymentPlanModifyModel {
	
	/**
	 * 功能代码
	 */
	private String fn;
	
	/**
	 * 请求唯一标识
	 */
	private String requestNo;
	
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	
	/**
	 * 请求原因
	 */
	private String requestReason;
	
	/**
	 * 具体变更内容
	 */
	private String requestData;
	
	/**
	 * 校验失败信息
	 */
	private String checkFailedMsg;
	
	/**
	 * 还款计划变更类型
	 */
	private int type = RepaymentPlanModifyType.NORMAL.ordinal();

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}
	
	@JSONField(serialize = false)
	public List<RepaymentPlanModifyRequestDataModel> getRequestDataModel() {
		List<RepaymentPlanModifyRequestDataModel> models = JsonUtils.parseArray(this.requestData, RepaymentPlanModifyRequestDataModel.class);
		if (CollectionUtils.isEmpty(models)) {
			throw new ApiException(ApiResponseCode.WRONG_FORMAT);
		}
		return models;
	}
	
	@JSONField(serialize = false)
	public String getRequestParamsInfo(HttpServletRequest request) {
		return "[ uniqueId: " + uniqueId + ", contractNo: "
				+ contractNo + ", requestNo: " + requestNo
				+ ", requestReason: " + requestReason
				+ ", repayment plan json data: " + requestData + ", ip:"
				+ IpUtil.getIpAddress(request) + "]";
	}
	
	@JSONField(serialize = false)
	private boolean paramsError() {
		if (!(StringUtils.isEmpty(this.uniqueId) ^ StringUtils.isEmpty(this.contractNo))) {
			this.checkFailedMsg = "请选填其中一种编号［uniqueId，contractNo］！";
			return true;
		}
		if (StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.requestReason)) {
			this.checkFailedMsg = "请求原因［requestReason］，不能为空！";
			return true;
		}
		try {
			Integer reasonValue = Integer.valueOf(this.requestReason);
			if(EnumUtil.fromOrdinal(RepaymentPlanModifyReason.class, reasonValue) == null) {
				this.checkFailedMsg = "请求原因［requestReason］，内容不合法！";
				return true;
			}
		} catch (NumberFormatException e) {
			this.checkFailedMsg = "请求原因［requestReason］必须是数字！";
			return true;
		}
		if (StringUtils.isEmpty(requestData)) {
			this.checkFailedMsg = "具体变更内容［requestData］，不能为空！";
			return true;
		}
		return false;
	}

	/**
	 * 校验本金，利息，还款日期（递增）
	 */
	@JSONField(serialize = false)
	private void checkRequestDataList() {
		Date maxDate = DateUtils.getToday();
		for (RepaymentPlanModifyRequestDataModel assetSetUpdateModel : this.getRequestDataModel()) {
			BigDecimal assetPrincipal = assetSetUpdateModel.getAssetPrincipal();
			if (BigDecimal.ZERO.compareTo(assetPrincipal) == 1) {
				throw new ApiException(INVALID_PRINCIPAL_AMOUNT);
			}
			BigDecimal assetInterest = assetSetUpdateModel.getAssetInterest();
			if (BigDecimal.ZERO.compareTo(assetInterest) == 1) {
				throw new ApiException(INVALID_INTEREST_AMOUNT);
			}
			Date assetRecycleDate = assetSetUpdateModel.getDate();
			if (maxDate.after(assetRecycleDate)) {
				throw new ApiException(ApiResponseCode.WRONG_ASSET_RECYCLE_DATE);
			}
			maxDate = assetRecycleDate;
		}
	}
	
	@JSONField(serialize = false)
	public Date getPlanEndDate() {
		List<RepaymentPlanModifyRequestDataModel> modelList = getRequestDataModel();
		return modelList.get(modelList.size() - 1).getDate();
	}
	
	@JSONField(serialize = false)
	public Date getPlanBeginDate() {
		List<RepaymentPlanModifyRequestDataModel> modelList = getRequestDataModel();
		return modelList.get(0).getDate();
	}
	
	@JSONField(serialize = false)
	public BigDecimal getPlanPrincipalAmount() {
		BigDecimal planPrincipalAmount = BigDecimal.ZERO;
		List<RepaymentPlanModifyRequestDataModel> modelList = getRequestDataModel();
		for (RepaymentPlanModifyRequestDataModel model : modelList) {
			planPrincipalAmount = planPrincipalAmount.add(model.getAssetPrincipal());
		}
		return planPrincipalAmount;
	}

	/**
	 * 异常变更校验
	 * @return
	 */
	@JSONField(serialize = false)
	public boolean paramsHasError() {
		if(paramsError()) {
			return true;
		}
		checkRequestDataList();
		return false;
	}

	/**
	 * 是否存在变更当日的还款计划
	 * @return
	 */
	public boolean hasModifyTodayPlan() {
		List<RepaymentPlanModifyRequestDataModel> list = getRequestDataModel();
		return list.stream().filter(model -> DateUtils.isSameDay(DateUtils.getToday(), model.getDate())).count() > 0;
	}
	
}
