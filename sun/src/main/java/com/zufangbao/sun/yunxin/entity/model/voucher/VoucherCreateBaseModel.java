package com.zufangbao.sun.yunxin.entity.model.voucher;

import java.util.List;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.yunxin.entity.AssetSet;

/**
 * 新增凭证-贷款信息，账户信息
 * 
 * @author louguanyang
 *
 */
public class VoucherCreateBaseModel {
	/**
	 * 贷款合同
	 */
	private Contract contract;
	/**
	 * 贷款合同账户
	 */
	private ContractAccount contractAccount;
	/**
	 * 所有未清的还款计划
	 */
	private List<AssetSet> repaymentPlanList;

	public VoucherCreateBaseModel() {
		super();
	}

	public VoucherCreateBaseModel(Contract contract, ContractAccount contractAccount,
			List<AssetSet> repaymentPlanList) {
		super();
		this.contract = contract;
		this.contractAccount = contractAccount;
		this.repaymentPlanList = repaymentPlanList;
	}

	public List<AssetSet> getRepaymentPlanList() {
		return repaymentPlanList;
	}

	public void setRepaymentPlanList(List<AssetSet> repaymentPlanList) {
		this.repaymentPlanList = repaymentPlanList;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public ContractAccount getContractAccount() {
		return contractAccount;
	}

	public void setContractAccount(ContractAccount contractAccount) {
		this.contractAccount = contractAccount;
	}

}
