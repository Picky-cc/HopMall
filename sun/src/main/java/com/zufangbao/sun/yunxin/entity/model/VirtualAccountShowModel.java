package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.EnumUtil;

public class VirtualAccountShowModel {
	
	private String virtualAccountNo; //账户编号
	private String virtualAccountAlias; //账户名称
	private CustomerType customerType; //客户类型
	private String contractName; //信托合同名称
	private String contactNo; //贷款合同编号
	private BigDecimal totalBalance; //账户余额
	private Date createTime; //创建时间
	
	
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	public String getVirtualAccountAlias() {
		return virtualAccountAlias;
	}
	public void setVirtualAccountAlias(String virtualAccountAlias) {
		this.virtualAccountAlias = virtualAccountAlias;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public VirtualAccountShowModel(VirtualAccount virtualAccount,
			FinancialContract financialContract, Contract contract) {
		super();
		this.virtualAccountNo = virtualAccount.getVirtualAccountNo();
		this.virtualAccountAlias = virtualAccount.getVirtualAccountAlias();
		this.customerType = EnumUtil.fromOrdinal(CustomerType.class, virtualAccount.getCustomerType());
		this.contractName = financialContract == null?"":financialContract.getContractName();
		this.contactNo = contract == null?"":contract.getContractNo();
		this.totalBalance = virtualAccount.getTotalBalance();
		this.createTime = virtualAccount.getCreateTime();
	}
	
	public String getCustomerTypeName(){
		return customerType==null?"":customerType.getChineseName();
	}
	
	
	public CustomerType getCustomerTypeEnum(Integer customerType) {
		return CustomerType.fromOrdinal(customerType);
	}
}
