
package com.zufangbao.sun.entity.icbc.business;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.bank.Bank;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.NFQLoanInformation;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;
/**
 * 租约账号对应类
 * @author zjm
 *
 */
@Entity
@Table(name = "contract_account")
public class ContractAccount {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 贷款合同
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Contract contract;
	/**
	 * 还款账户卡号
	 */
	private String payAcNo;
	/**
	 * 还款人姓名
	 */
	private String payerName;
	/**
	 * 开户行名称
	 */
	private String bank;
	/**
	 * 还款账户类型 0：借记卡 , 1：信用卡
	 */
	@Enumerated(EnumType.ORDINAL)
	private BankcardType bankcardType; 
	/**
	 * 绑定Id
	 */
	private String bindId;
	/**
	 * 还款人身份证号
	 */
	private String idCardNum;
	/**
	 * 还款账户银行代码
	 */
	private String bankCode;
	/**
	 * 还款账户开户行所在省
	 */
	private String province;
	
	
	private String provinceCode;
	/**
	 * 还款账户开户行所在市
	 */
	private String city;
	
	private String cityCode;
	
	private String standardBankCode;
	
	private Date fromDate;
	
	private Date thruDate;

	public ContractAccount() {
		super();
	}

	public ContractAccount(Contract contract, NFQLoanInformation loanInformation) {
		super();
		this.contract = contract;
		
		this.payerName = loanInformation.getCustomerName();
		this.payAcNo = loanInformation.getCustomerAccountNo();
		this.idCardNum = loanInformation.getCustomerIDNo();
		
		this.bank = loanInformation.getCustomerBankName();
		this.bankCode = loanInformation.getCustomerBankCode();
		this.province = loanInformation.getCustomerBankProvince();
		this.city = loanInformation.getCustomerBankCity();
		
		this.fromDate = new Date();
		this.thruDate = DateUtils.MAX_DATE;
	}
	
	public ContractAccount(ContractAccount contractAccount) {
		this.contract = contractAccount.getContract();
		this.bank = contractAccount.getBank();
		this.bankcardType =contractAccount.getBankcardType();
		this.bindId = contractAccount.getBindId();
		this.city = contractAccount.getCity();
		this.fromDate =contractAccount.getFromDate();
		this.idCardNum = contractAccount.getIdCardNum();
		this.payAcNo = contractAccount.getPayAcNo();
		this.payerName = contractAccount.getPayerName();
		this.province = contractAccount.getProvince();
		this.thruDate = contractAccount.getThruDate();
		this.bankCode = contractAccount.getBankCode();
	}

	public ContractAccount(Contract loan_contract, ContractDetail contractDetail,Bank bank, String  province,String  city	) {
		super();
		this.contract = loan_contract;
		this.payerName = contractDetail.getLoanCustomerName();
		this.payAcNo = contractDetail.getRepaymentAccountNo();
		this.idCardNum = contractDetail.getIDCardNo();
		this.bank = bank.getBankName();
		this.standardBankCode = bank.getBankCode();
		this.province = province;
		this.city = city;
		
		
		this.provinceCode = contractDetail.getBankOfTheProvince();
		this.cityCode     = contractDetail.getBankOfTheCity();
		this.fromDate = new Date();
		this.thruDate = DateUtils.MAX_DATE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getPayAcNo() {
		return payAcNo;
	}

	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BankcardType getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(BankcardType bankcardType) {
		this.bankcardType = bankcardType;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getLastFourAccountNo() {
		if(StringUtils.isEmpty(payAcNo)) {
			return "";
		}
		return StringUtils.right(payAcNo, 4);
	}
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}
	public String getStandardBankCode() {
		return standardBankCode;
	}

	public void setStandardBankCode(String standardBankCode) {
		this.standardBankCode = standardBankCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	

	
}
