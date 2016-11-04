package com.zufangbao.sun.entity.financial;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.yunxin.entity.AssetPackageFormat;

/**
 * 信托合同
 * 
 * @author gzs
 */
@Entity
@Table(name = "financial_contract")
public class FinancialContract {

	/**
	 * 信托合同ID
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 信托产品代码
	 */
	private String contractNo;

	private String financialContractUuid;
	/**
	 * 账本编号
	 */
	private String ledgerBookNo;

	/**
	 * 信托合同名称
	 */
	private String contractName;
	
	
	
	/**
	 * 资金账户
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Account capitalAccount;

	/**
	 * 融出方ID
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Company company;

	/**
	 * 融资方ID
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private App app;

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
	@Enumerated(EnumType.ORDINAL)
	private AssetPackageFormat assetPackageFormat;

	/**
	 * 信托合约类型 0:消费贷,1:小微企业贷款
	 */
	@Enumerated(EnumType.ORDINAL)
	private FinancialContractType financialContractType;

	/**
	 * 支付通道
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private PaymentChannel paymentChannel;

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
	private Date advaStartDate;

	/**
	 * 信托合同截止日期
	 */
	private Date thruDate;
	
	/**
	 * 系统正常扣款标示
	 */
	private boolean sysNormalDeductFlag = false;
	
	/**
	 * 系统逾期扣款标示
	 */
	private boolean sysOverdueDeductFlag = false;
	
	/**
	 * 是否由系统产生罚息
	 */
	private boolean sysCreatePenaltyFlag = false;

	/**
	 * 是否系统产生但保单
	 */
	private boolean sysCreateGuaranteeFlag = false;
	
	/**
	 * 是否系统产生结算单
	 */
	private boolean sysCreateStatementFlag = false;
	
	/**
	 * 是否允许变更当日还款计划
	 */
	private boolean unusualModifyFlag = false;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String lenderName) {
		this.contractName = lenderName;
	}

	public Account getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(Account paybackAccount) {
		this.capitalAccount = paybackAccount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
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

	public AssetPackageFormat getAssetPackageFormat() {
		return assetPackageFormat;
	}

	public void setAssetPackageFormat(AssetPackageFormat assetPackageFormat) {
		this.assetPackageFormat = assetPackageFormat;
	}

	public FinancialContractType getFinancialContractType() {
		return financialContractType;
	}
	
	public String getFinancialContractTypeMsg() {
		return financialContractType.toString();
	}

	public void setFinancialContractType(FinancialContractType financialContractType) {
		this.financialContractType = financialContractType;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
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

	public Date getAdvaStartDate() {
		return advaStartDate;
	}

	public void setAdvaStartDate(Date advaStartDate) {
		this.advaStartDate = advaStartDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}

	public String getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}
	
	public String getUuid(){
		return this.financialContractUuid;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public boolean isSysNormalDeductFlag() {
		return sysNormalDeductFlag;
	}

	public void setSysNormalDeductFlag(boolean sysNormalDeductFlag) {
		this.sysNormalDeductFlag = sysNormalDeductFlag;
	}

	public boolean isSysOverdueDeductFlag() {
		return sysOverdueDeductFlag;
	}

	public void setSysOverdueDeductFlag(boolean sysOverdueDeductFlag) {
		this.sysOverdueDeductFlag = sysOverdueDeductFlag;
	}

	public boolean isSysCreatePenaltyFlag() {
		return sysCreatePenaltyFlag;
	}

	public void setSysCreatePenaltyFlag(boolean sysCreatePenaltyFlag) {
		this.sysCreatePenaltyFlag = sysCreatePenaltyFlag;
	}

	public boolean isSysCreateGuaranteeFlag() {
		return sysCreateGuaranteeFlag;
	}

	public void setSysCreateGuaranteeFlag(boolean sysCreateGuaranteeFlag) {
		this.sysCreateGuaranteeFlag = sysCreateGuaranteeFlag;
	}
	
	public boolean isSysCreateStatementFlag() {
		return sysCreateStatementFlag;
	}

	public void setSysCreateStatementFlag(boolean sysCreateStatementFlag) {
		this.sysCreateStatementFlag = sysCreateStatementFlag;
	}

	public boolean isUnusualModifyFlag() {
		return unusualModifyFlag;
	}

	public void setUnusualModifyFlag(boolean unusualModifyFlag) {
		this.unusualModifyFlag = unusualModifyFlag;
	}

	public FinancialContract() {
		super();
	}
	
	public boolean isAllowDeduct(Boolean isOverdue) {
		return isOverdue ? isSysOverdueDeductFlag() : isSysNormalDeductFlag();
	}
	
	/** 提前还款时，是否自动冲销，现在默认可以。如果区分，则在financialContract上加字段*/
	public boolean isAllowWriteOffInAdvanceWhenPrepayment(){
		return true;
	}
	
}
