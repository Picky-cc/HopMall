package com.zufangbao.sun.yunxin.entity.model.voucher;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;

public class VoucherCreateModel {
	/**
	 * 凭证类型(5:主动付款，6:他人代付)（必填）
	 */
	private Integer voucherType;
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
	 * 备注
	 */
	private String comment;
	
	/**
	 * 已上传的资源文件uuid 
	 * JsonArray
	 */
	private String resourceUuids;
	
 	/**
	 * 校验失败信息
	 */
	private String checkFailedMsg;

	public String getResourceUuids() {
		return resourceUuids;
	}

	public void setResourceUuids(String resourceUuids) {
		this.resourceUuids = resourceUuids;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
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
	
	public boolean paramsHasError() {
		if (StringUtils.isEmpty(this.contractNo)) {
			this.checkFailedMsg = "合同编号不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.bankTransactionNo)) {
			this.checkFailedMsg = "银行流水号不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.repaymentPlanNo) || null == getRepaymentPlanNoArray()) {
			this.checkFailedMsg = "还款编号不能为空！";
			return true;
		}
		if (this.voucherType == null) {
			this.checkFailedMsg = "请选择一种凭证类型！";
			return true;
		}
		if(this.getVoucherTypeEnum() == null) {
			this.checkFailedMsg = "凭证类型错误！";
			return true;
		}
		if (this.voucherAmount.compareTo(BigDecimal.ZERO) <= 0) {
			this.checkFailedMsg = "凭证金额必需大于0.00！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentAccountNo)) {
			this.checkFailedMsg = "机构账户号不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentName)) {
			this.checkFailedMsg = "还款方姓名不能为空！";
			return true;
		}
		if (StringUtils.isEmpty(this.paymentBank)) {
			this.checkFailedMsg = "来往机构名称不能为空！";
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
	
	public List<String> getResourceUuidArray() {
		return JsonUtils.parseArray(this.getResourceUuids(), String.class);
	}

	@Override
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("凭证类型");
			sb.append(getVoucherTypeEnum().getChineseMessage());
			sb.append("贷款合同编号");
			sb.append(contractNo);
			sb.append("还款计划编号");
			sb.append(repaymentPlanNo);
			sb.append("收款账户号");
			sb.append(receivableAccountNo);
			sb.append("付款银行名称");
			sb.append(paymentBank);
			sb.append("银行流水号");
			sb.append(bankTransactionNo);
			sb.append("凭证金额");
			sb.append(voucherAmount.toString());
			sb.append("付款银行帐户名称");
			sb.append(paymentName);
			sb.append("付款账户号");
			sb.append(paymentAccountNo);
			sb.append("备注");
			sb.append(comment);
			return sb.toString();
		} catch (Exception e) {
			return super.toString();
		}
	}
	
	
}
