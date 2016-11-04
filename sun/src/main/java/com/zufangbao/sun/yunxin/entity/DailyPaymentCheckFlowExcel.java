package com.zufangbao.sun.yunxin.entity;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class DailyPaymentCheckFlowExcel {

	@ExcelVoAttribute(name = "扣款金额", column = "A")
	private String payAmount;
	@ExcelVoAttribute(name = "支付单号", column = "B")
	private String transferApplicationNo;
	@ExcelVoAttribute(name = "扣款结果时间", column = "C")
	private String deductTime;
	@ExcelVoAttribute(name = "支付单列表状态", column = "D")
	private String executingDeductStatus;
	@ExcelVoAttribute(name = "绑定号/账号", column = "E")
	private String requestBankIdorAccountNo;

	public DailyPaymentCheckFlowExcel() {

	}

	public DailyPaymentCheckFlowExcel(TransferApplication transferApplication) {
		setExecutingDeductStatus(transferApplication.getExecutingDeductStatus());
		setDeductTime(transferApplication.getDeductTime());

		this.payAmount = transferApplication.getAmount().toString();
		this.transferApplicationNo = transferApplication
				.getTransferApplicationNo();
		this.requestBankIdorAccountNo = transferApplication
				.getContractAccount().getPayAcNo();

	}

	public String getRequestBankIdorAccountNo() {
		return requestBankIdorAccountNo;
	}

	public void setRequestBankIdorAccountNo(String requestBankIdorAccountNo) {
		this.requestBankIdorAccountNo = requestBankIdorAccountNo;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getTransferApplicationNo() {
		return transferApplicationNo;
	}

	public void setTransferApplicationNo(String transferApplicationNo) {
		this.transferApplicationNo = transferApplicationNo;
	}

	public String getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(Date deductTime) {

		if (deductTime == null) {
			return;
		}
		this.deductTime = DateUtils.format(deductTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getExecutingDeductStatus() {
		return executingDeductStatus;
	}

	public void setExecutingDeductStatus(
			ExecutingDeductStatus executingDeductStatus) {
		if (executingDeductStatus.getOrdinal() == 0) {
			this.executingDeductStatus = new String("已创建");
		} else if (executingDeductStatus.getOrdinal() == 1) {
			this.executingDeductStatus = new String("处理中");
		} else if (executingDeductStatus.ordinal() == 2) {
			this.executingDeductStatus = new String("成功");
		} else if (executingDeductStatus.ordinal() == 3) {
			this.executingDeductStatus = new String("失败");
		} else if (executingDeductStatus.ordinal() == 4) {
			this.executingDeductStatus = new String("异常");
		}

	}

}
