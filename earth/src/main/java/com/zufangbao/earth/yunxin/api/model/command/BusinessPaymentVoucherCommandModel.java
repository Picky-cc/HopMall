package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;

/**
 * 商户付款凭证指令模型
 * 
 * @author louguanyang
 *
 */
public class BusinessPaymentVoucherCommandModel {
	/**
	 * 功能代码（必填）
	 */
	private String fn;
	/**
	 * 请求唯一标识（必填）
	 */
	private String requestNo;
	/**
	 * 交易类型(0:提交，1:撤销)（必填）
	 */
	private Integer transactionType;
//	/**
//	 * 凭证编号（必填）
//	 */
//	private String businessVoucherNo;
	/**
	 * 凭证类型(0:代偿，1:担保补足，2:回购，3:差额划拨)（必填）
	 */
	private Integer voucherType;
	/**
	 * 凭证金额（必填）
	 */
	private BigDecimal voucherAmount = BigDecimal.ZERO;
	/**
	 * 信托产品代码（必填）
	 */
	private String financialContractNo;
	/**
	 * 收款账户号（选填）
	 */
	private String receivableAccountNo;
	/**
	 * 付款账户号（必填）
	 */
	private String paymentAccountNo;
	/**
	 * 付款银行帐户名称（必填）
	 */
	private String paymentName;
	/**
	 * 付款银行名称（必填）
	 */
	private String paymentBank;
	/**
	 * 银行流水号（必填）
	 */
	private String bankTransactionNo;
	/**
	 * 明细（必填）
	 */
	private String detail;
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

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public String getBankTransactionNo() {
		return bankTransactionNo;
	}

	public void setBankTransactionNo(String bankTransactionNo) {
		this.bankTransactionNo = bankTransactionNo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}
	
	@JSONField(serialize = false)
	public List<BusinessPaymentVoucherDetail> getDetailModel() {
		List<BusinessPaymentVoucherDetail> detailModels = JsonUtils.parseArray(this.detail, BusinessPaymentVoucherDetail.class);
		if(CollectionUtils.isEmpty(detailModels)) {
			throw new ApiException(ApiResponseCode.WRONG_FORMAT);
		}
		return detailModels;
	}
	
	@JSONField(serialize = false)
	public boolean checkSubmitParams() {
		if(!checkParams()) {
			return false;
		}
		if(!checkDetail()) {
			return false;
		}
		return true;
	}
	
	@JSONField(serialize = false)
	public boolean checkUndoParams() {
		if(!checkNecessaryParam()) {
			return false;
		}
		return true;
	}

	@JSONField(serialize = false)
	private boolean checkNecessaryParam() {
		if (StringUtils.isEmpty(this.requestNo)) {
			this.checkFailedMsg = "请求唯一标识［requestNo］，不能为空！";
			return false;
		}
		if (StringUtils.isEmpty(this.bankTransactionNo)) {
			this.checkFailedMsg = "付款账户流水号［bankTransactionNo］，不能为空！";
			return false;
		}
		if (StringUtils.isEmpty(this.financialContractNo)) {
			this.checkFailedMsg = "信托产品代码［financialContractNo］，不能为空！";
			return false;
		}
		return true;
	}

	@JSONField(serialize = false)
	private boolean checkParams() {
		if(!checkNecessaryParam()) {
			return false;
		}
		if (this.voucherType == null) {
			this.checkFailedMsg = "凭证类型［voucherType］，不能为空！";
			return false;
		}
		if (this.voucherAmount.compareTo(BigDecimal.ZERO) <= 0) {
			this.checkFailedMsg = "凭证金额［voucherAmount］，必需大于0.00！";
			return false;
		}
		if (StringUtils.isEmpty(this.paymentAccountNo)) {
			this.checkFailedMsg = "付款账户号［paymentAccountNo］，不能为空！";
			return false;
		}
		if (StringUtils.isEmpty(this.paymentName)) {
			this.checkFailedMsg = "付款银行帐户名称［paymentName］，不能为空！";
			return false;
		}
		if (StringUtils.isEmpty(this.paymentBank)) {
			this.checkFailedMsg = "付款银行名称［paymentBank］，不能为空！";
			return false;
		}
		return true;
	}

	@JSONField(serialize = false)
	private boolean checkDetail() {
		if (StringUtils.isEmpty(this.detail)) {
			this.checkFailedMsg = "明细［detail］，不能为空！";
			return false;
		}
		if(getDetailModel().stream().filter(detail -> !detail.isValid()).count() > 0) {
			this.checkFailedMsg = "凭证明细内容错误［detail］，字段格式错误！";
			return false;
		}
		BigDecimal detailAmount = BigDecimal.ZERO;
		List<BusinessPaymentVoucherDetail> detailModel = getDetailModel();
		for (BusinessPaymentVoucherDetail detail : detailModel) {
			detailAmount = detailAmount.add(detail.getAmount());
		}
		if(this.voucherAmount.compareTo(detailAmount) != 0) {
			this.checkFailedMsg = "凭证金额与明细总金额不匹配！";
			return false;
		}
		return true;
	}

	public VoucherType getVoucherTypeEnum() {
		return VoucherType.business_payment_voucher_type_from_value(getVoucherType());
	}
}
