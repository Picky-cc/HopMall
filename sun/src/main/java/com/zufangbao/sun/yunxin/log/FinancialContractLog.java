package com.zufangbao.sun.yunxin.log;

import java.util.HashMap;
import java.util.Map;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractType;
import com.zufangbao.sun.yunxin.entity.AssetPackageFormat;

public class FinancialContractLog {

	private String uuid;

	private String contractNo;

	/**
	 * 账本编号
	 */
	private String ledgerBookNo;

	/**
	 * 信托合同名称
	 */
	private String contractName;

	/**
	 * 信托专户开户行
	 */
	private String trustsBankNames;
	/**
	 * 信托专户户名
	 */
	private String trustsAccountName;
	/**
	 * 信托专户账号
	 */
	private String trustsAccountNo;

	/**
	 * 融出方ID
	 */
	private String company;

	/**
	 * 融资方ID
	 */
	private String app;

	/**
	 * 商户打款宽限期（日）
	 */
	private int advaMatuterm;

	/**
	 * 坏账
	 */
	private int advaRepoTerm;

	/**
	 * 资产包格式 0:等额本息，1:锯齿型
	 */
	private String assetPackageFormat;

	/**
	 * 信托合约类型 0:消费贷,1:小微企业贷款
	 */
	private String financialContractType;

	/**
	 * 支付通道
	 */
	private String paymentChannel;

	/**
	 * 贷款逾期开始日
	 */
	private int loanOverdueStartDay;

	/**
	 * 贷款逾期结束日
	 */
	private int loanOverdueEndDay;

	/**
	 * 信托合同起始日期
	 */
	private String advaStartDate;

	/**
	 * 信托合同截止日期
	 */
	private String thruDate;

	public static final Map<String, String> filedNameCorrespondStringName = new HashMap<String, String>() {
		private static final long serialVersionUID = 4097820765125254201L;

		{
			put("contractNo", "信托合同编号");
			put("uuid", "信托合同UUID");
			put("ledgerBookNo", "账本编号");
			put("contractName", "信托合同名称");
			put("capitalAccount", "资金账户");
			put("trustsBankNames", "信托专户开户行");
			put("trustsAccountName", "信托专户户名");
			put("trustsAccountNo", "信托专户账号");
			put("company", "信托公司");
			put("app", "信托商户名称");
			put("advaMatuterm", "商户打款宽限期（日）");
			put("advaRepoTerm", "贷款坏账");
			put("assetPackageFormat", "资产包格式");
			put("financialContractType", "信托合同类型");
			put("paymentChannel", "支付通道");
			put("loanOverdueStartDay", "贷款逾期开始日");
			put("loanOverdueEndDay", "贷款逾期结束日");
			put("advaStartDate", "信托合同起始日期");
			put("thruDate", "信托合同截止日期");
		}
	};

	public FinancialContractLog(FinancialContract financialContract) {
		this.uuid = financialContract.getUuid();
		this.advaMatuterm = financialContract.getAdvaMatuterm();
		this.advaRepoTerm = financialContract.getAdvaRepoTerm();
		if (financialContract.getAdvaStartDate() != null) {
			this.advaStartDate = DateUtils.format(
					financialContract.getAdvaStartDate(), "yyyy-MM-dd");
		}
		this.app = financialContract.getApp().getName();
		if (financialContract.getAssetPackageFormat() != null) {
			this.assetPackageFormat = formatAssetPackageFormat(financialContract
					.getAssetPackageFormat());
		}
		if (financialContract.getCapitalAccount() != null) {
			this.trustsBankNames = financialContract.getCapitalAccount()
					.getBankName();
			this.trustsAccountName = financialContract.getCapitalAccount()
					.getAccountName();
			this.trustsAccountNo = financialContract.getCapitalAccount()
					.getAccountNo();
		}
		this.company = financialContract.getCompany().getFullName();
		this.contractName = financialContract.getContractName();
		this.contractNo = financialContract.getContractNo();
		if (financialContract.getFinancialContractType() != null) {
			this.financialContractType = formatFinancialContractType(financialContract
					.getFinancialContractType());
		}
		this.ledgerBookNo = financialContract.getLedgerBookNo();
		this.loanOverdueEndDay = financialContract.getLoanOverdueEndDay();
		this.loanOverdueStartDay = financialContract.getLoanOverdueStartDay();
		if (financialContract.getThruDate() != null) {
			this.thruDate = DateUtils.format(financialContract.getThruDate(),
					"yyyy-MM-dd");
		}
	}

	public String formatAssetPackageFormat(AssetPackageFormat assetPackageFormat) {
		switch (assetPackageFormat.ordinal()) {
		case 0:
			return new String("等额本息");
		case 1:
			return new String("锯齿形");
		default:
			return new String("等额本息");
		}
	}

	public String formatFinancialContractType(
			FinancialContractType financialContractType) {
		switch (financialContractType.ordinal()) {
		case 0:
			return new String("消费贷款");
		case 1:
			return new String("小企业微贷");
		default:
			return new String("消费贷款");
		}
	}

	public FinancialContractLog() {

	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getTrustsBankNames() {
		return trustsBankNames;
	}

	public void setTrustsBankNames(String trustsBankNames) {
		this.trustsBankNames = trustsBankNames;
	}

	public String getTrustsAccountName() {
		return trustsAccountName;
	}

	public void setTrustsAccountName(String trustsAccountName) {
		this.trustsAccountName = trustsAccountName;
	}

	public String getTrustsAccountNo() {
		return trustsAccountNo;
	}

	public void setTrustsAccountNo(String trustsAccountNo) {
		this.trustsAccountNo = trustsAccountNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public int getAdvaMatuterm() {
		return advaMatuterm;
	}

	public void setAdvaMatuterm(int advaMatuterm) {
		this.advaMatuterm = advaMatuterm;
	}

	public int getAdvaRepoTerm() {
		return advaRepoTerm;
	}

	public void setAdvaRepoTerm(int advaRepoTerm) {
		this.advaRepoTerm = advaRepoTerm;
	}

	public String getAssetPackageFormat() {
		return assetPackageFormat;
	}

	public void setAssetPackageFormat(String assetPackageFormat) {
		this.assetPackageFormat = assetPackageFormat;
	}

	public String getFinancialContractType() {
		return financialContractType;
	}

	public void setFinancialContractType(String financialContractType) {
		this.financialContractType = financialContractType;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public int getLoanOverdueStartDay() {
		return loanOverdueStartDay;
	}

	public void setLoanOverdueStartDay(int loanOverdueStartDay) {
		this.loanOverdueStartDay = loanOverdueStartDay;
	}

	public int getLoanOverdueEndDay() {
		return loanOverdueEndDay;
	}

	public void setLoanOverdueEndDay(int loanOverdueEndDay) {
		this.loanOverdueEndDay = loanOverdueEndDay;
	}

	public String getAdvaStartDate() {
		return advaStartDate;
	}

	public void setAdvaStartDate(String advaStartDate) {
		this.advaStartDate = advaStartDate;
	}

	public String getThruDate() {
		return thruDate;
	}

	public void setThruDate(String thruDate) {
		this.thruDate = thruDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
