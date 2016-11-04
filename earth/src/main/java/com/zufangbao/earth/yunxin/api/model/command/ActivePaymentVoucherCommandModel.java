package com.zufangbao.earth.yunxin.api.model.command;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;

/**
 * 主动付款凭证指令模型
 * 
 * @author louguanyang
 *
 */
public class ActivePaymentVoucherCommandModel {
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
	/**
	 * 凭证类型(5:主动付款，6:他人代付)（必填）
	 */
	private Integer voucherType;
	/**
	 * 贷款合同唯一编号
	 */
	private String uniqueId;
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	/**
	 * 还款计划编号（必填）Json
	 */
	private String repaymentPlanNo;
	/**
	 * 收款账户号（选填）
	 */
	private String receivableAccountNo;
	/**
	 * 付款银行名称（必填）
	 */
	private String paymentBank;
	/**
	 * 银行流水号（必填）
	 */
	private String bankTransactionNo;
	/**
	 * 凭证金额（必填）
	 */
	private BigDecimal voucherAmount = BigDecimal.ZERO;
	/**
	 * 付款银行帐户名称（必填）
	 */
	private String paymentName;
	/**
	 * 付款账户号（必填）
	 */
	private String paymentAccountNo;
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

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
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

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public String getCheckFailedMsg() {
		return checkFailedMsg;
	}

	public void setCheckFailedMsg(String checkFailedMsg) {
		this.checkFailedMsg = checkFailedMsg;
	}

	public boolean submitParamsError() {
		return paramsError();
	}

	private boolean paramsError() {
		if(isNecessaryParamsError()) {
			return true;
		}
		if (StringUtils.isEmpty(this.repaymentPlanNo) || null == getRepaymentPlanNoArray()) {
			this.checkFailedMsg = "还款计划编号［repaymentPlanNo］，不能为空！";
			return true;
		}
		if (this.voucherType == null) {
			this.checkFailedMsg = "凭证类型［voucherType］，不能为空！";
			return true;
		}
		if(this.getVoucherTypeEnum() == null) {
			this.checkFailedMsg = "凭证类型［voucherType］错误，内容不支持！";
			return true;
		}
		if (this.voucherAmount.compareTo(BigDecimal.ZERO) <= 0) {
			this.checkFailedMsg = "凭证金额［voucherAmount］，必需大于0.00！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentAccountNo)) {
			this.checkFailedMsg = "付款账户号［paymentAccountNo］，不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentName)) {
			this.checkFailedMsg = "付款银行帐户名称［paymentName］，不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentBank)) {
			this.checkFailedMsg = "付款银行名称［paymentBank］，不能为空！";
			return true;
		}
		return false;
	}

	public VoucherType getVoucherTypeEnum() {
		return VoucherType.active_payment_voucher_type_from_value(this.voucherType);
	}

	public List<String> getRepaymentPlanNoArray() {
		return JsonUtils.parseArray(this.getRepaymentPlanNo(), String.class);
	}
	
	public boolean isNecessaryParamsError() {
		if (!(StringUtils.isEmpty(this.uniqueId) ^ StringUtils.isEmpty(this.contractNo))) {
			this.checkFailedMsg = "请选填其中一种编号［uniqueId，contractNo］！";
			return true;
		}
		if (StringUtils.isEmpty(this.bankTransactionNo)) {
			this.checkFailedMsg = "付款账户流水号［bankTransactionNo］，不能为空！";
			return true;
		}
		return false;
	}

	public boolean undoParamsError() {
		return isNecessaryParamsError();
	}
}
