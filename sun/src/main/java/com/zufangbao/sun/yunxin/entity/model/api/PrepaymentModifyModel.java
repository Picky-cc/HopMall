package com.zufangbao.sun.yunxin.entity.model.api;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentType;

/**
 * 提前还款Model
 * 
 * @author louguanyang
 *
 */
public class PrepaymentModifyModel {
	private String uniqueId;
	private String contractNo;
	private String requestNo;
	private String assetRecycleDate;
	private String assetInitialValue;
	private int type;
	private String checkFailedMsg;
	
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

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getAssetRecycleDate() {
		return assetRecycleDate;
	}
	
	public Date getAssetRecycleDateValue() {
		try {
			Date date = DateUtils.asDay(assetRecycleDate);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setAssetRecycleDate(String assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}

	@JSONField(serialize = false)
	public BigDecimal getAssetInitialValueBD() {
		return new BigDecimal(this.assetInitialValue);
	}
	
	public String getAssetInitialValue() {
		return this.assetInitialValue;
	}

	public void setAssetInitialValue(String assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String errorMsg) {
		this.checkFailedMsg = errorMsg;
	}

	public boolean isValid() {
		if(StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		if(!(StringUtils.isEmpty(this.uniqueId) ^ StringUtils.isEmpty(this.contractNo))) {
			this.checkFailedMsg = "请选填其中一种编号［uniqueId，contractNo］！";
			return false;
		}
		if(this.type != PrepaymentType.ALL.ordinal()) {
			this.checkFailedMsg = "目前仅支持 [全部提前还款] 模式！";
			return false;
		}
		if(StringUtils.isEmpty(this.assetRecycleDate)) {
			this.checkFailedMsg = "计划还款日期 [assetRecycleDate], 不能为空！";
			return false;
		}
		Date date = getAssetRecycleDateValue();
		if (date == null) {
			this.checkFailedMsg = "计划还款日期格式错误 [assetRecycleDate] ， 格式[yyyy-MM-dd]！";
			return false;
		}
		Date tomorrow = DateUtils.addDays(DateUtils.getToday(), BigDecimal.ONE.intValue());
		if (date.before(tomorrow)) {
			this.checkFailedMsg = "提前还款日期不能早于明日, " + DateUtils.format(tomorrow) + " ！";
			return false;
		}
		try {
			BigDecimal amount = getAssetInitialValueBD();
			if(amount.compareTo(BigDecimal.ZERO) <= 0) {
				this.checkFailedMsg = "还款金额格式错误［assetInitialValue］，金额需高于0.00！";
				return false;
			}
			if(amount.scale() > 2) {
				this.checkFailedMsg = "还款金额格式错误［assetInitialValue］，小数点后只保留2位！";
				return false;
			}
		} catch (Exception e) {
			this.checkFailedMsg = "还款金额格式错误［assetInitialValue］！";
			return false;
		}
		return true;
	}

	
}
