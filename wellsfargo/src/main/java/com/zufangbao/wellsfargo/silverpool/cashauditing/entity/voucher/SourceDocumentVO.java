package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;

public class SourceDocumentVO {
	
	private String sourceDocumentNo; //充值单号
	private String virtualAccountName; //账户名称
	private String virtualAccountNo; //账户编号
	private String firstPartyName;   //客户名称
	private CustomerType sourceDocumentType; //客户类型
	private String contractNo; //贷款合同编号
	private String contractName;     //信托项目名称
	private BigDecimal bookingAmount;    //充值金额
	private String summary;          //备注
	private SourceDocumentStatus sourceDocumentStatus;  //状态
	private String institutions; //机构流水
	public String getSourceDocumentNo() {
		return sourceDocumentNo;
	}
	public void setSourceDocumentNo(String sourceDocumentNo) {
		this.sourceDocumentNo = sourceDocumentNo;
	}
	public String getVirtualAccountName() {
		return virtualAccountName;
	}
	public void setVirtualAccountName(String virtualAccountName) {
		this.virtualAccountName = virtualAccountName;
	}
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	public String getFirstPartyName() {
		return firstPartyName;
	}
	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	public CustomerType getSourceDocumentType() {
		return sourceDocumentType;
	}
	public void setSourceDocumentType(CustomerType sourceDocumentType) {
		this.sourceDocumentType = sourceDocumentType;
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
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	public BigDecimal getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(BigDecimal bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public SourceDocumentStatus getSourceDocumentStatus() {
		return sourceDocumentStatus;
	}
	public void setSourceDocumentStatus(SourceDocumentStatus sourceDocumentStatus) {
		this.sourceDocumentStatus = sourceDocumentStatus;
	}
	public String getInstitutions() {
		return institutions;
	}
	public void setInstitutions(String institutions) {
		this.institutions = institutions;
	}
	public SourceDocumentVO(String sourceDocumentNo, String virtualAccountNane,
			String virtualAccountNo, String firstPartyName,
			CustomerType sourceDocumentType, String sourceDocument,
			String contractName, BigDecimal bookingAmount, String summary,
			SourceDocumentStatus sourceDocumentStatus, String institutions) {
		super();
		this.sourceDocumentNo = sourceDocumentNo;
		this.virtualAccountName = virtualAccountNane;
		this.virtualAccountNo = virtualAccountNo;
		this.firstPartyName = firstPartyName;
		this.sourceDocumentType = sourceDocumentType;
		this.contractNo = sourceDocument;
		this.contractName = contractName;
		this.bookingAmount = bookingAmount;
		this.summary = summary;
		this.sourceDocumentStatus = sourceDocumentStatus;
		this.institutions = institutions;
	}
	
	
	public SourceDocumentVO(SourceDocument sourceDocument, VirtualAccount virtualAccount,
			Contract contract, FinancialContract financialContract,CashFlow cashFlow) {
		super();
		this.sourceDocumentNo = sourceDocument.getSourceDocumentNo();
		this.virtualAccountName = virtualAccount == null?"":virtualAccount.getVirtualAccountAlias();
		this.virtualAccountNo = sourceDocument.getVirtualAccountNo();
		this.firstPartyName = sourceDocument.getFirstPartyName();
		this.sourceDocumentType = EnumUtil.fromOrdinal(CustomerType.class, sourceDocument.getFirstPartyType());
		this.contractNo = contract == null?"":contract.getContractNo();
		this.contractName = financialContract == null?"":financialContract.getContractName();
		this.bookingAmount = sourceDocument.getBookingAmount();
		this.summary = sourceDocument.getRemarkInAppendix();
		this.sourceDocumentStatus = sourceDocument.getSourceDocumentStatus();
		this.institutions = cashFlow == null?"":cashFlow.getBankSequenceNo();
	}
	
	
	public CustomerType getCustomerTypeEnum(Integer firstPartyType) {
		return CustomerType.fromOrdinal(firstPartyType);
	}
	
	public String getCustomerTypeName(){
		return sourceDocumentType==null?"":sourceDocumentType.getChineseName();
	}
	
	public String getSourceDocumentStatusName(){
		return sourceDocumentStatus==null?"":sourceDocumentStatus.getChineseName();
	}
}
