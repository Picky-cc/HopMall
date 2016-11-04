package com.zufangbao.wellsfargo.silverpool.cashauditing.webform;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;

public class CustomerDepositResult {
	private static String KEY_DEPOSIT_NO="depositNo";
	private static String KEY_VIRTUAL_ACCOUNT_NO="virtualAccountNo";
	private static String KEY_CUSTOMER_NAME="customerName";
	private static String KEY_CUSTOMER_TYPE_MSG="customerTypeMsg";
	private static String KEY_CONTRACT_NO="contractNo";
	private static String KEY_FINANCIAL_CONTRACT_NAME="financialContractName";
	
	private String sourceDocumentUuid;
	private BigDecimal balance;
	private BigDecimal depositAmount;
	private int sourceDocumentStatusInt;
	private String remark;
	private Map<String,Object> showData = new HashMap<String,Object>();
	
	public Map<String, Object> getShowData() {
		return showData;
	}
	public void setShowData(Map<String, Object> showData) {
		this.showData = showData;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public int getSourceDocumentStatusInt() {
		return sourceDocumentStatusInt;
	}
	public void setSourceDocumentStatusInt(int sourceDocumentStatusInt) {
		this.sourceDocumentStatusInt = sourceDocumentStatusInt;
	}
	public String getSourceDocumentUuid() {
		return sourceDocumentUuid;
	}
	public void setSourceDocumentUuid(String sourceDocumentUuid) {
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	
	public CustomerDepositResult(){
		
	}
	public CustomerDepositResult(String depositNo, String virtualAccountNo,
			String customerName, int customerTypeInt, String contractNo,
			String financialContractName, BigDecimal balance,
			BigDecimal depositAmount, String remark,
			int sourceDocumentStatusInt, String sourceDocumentUuid) {
		super();
		this.showData.put(KEY_DEPOSIT_NO, depositNo);
		this.showData.put(KEY_VIRTUAL_ACCOUNT_NO, virtualAccountNo);
		this.showData.put(KEY_CUSTOMER_NAME, customerName);
		CustomerType customerType = EnumUtil.fromOrdinal(CustomerType.class, customerTypeInt);
		this.showData.put(KEY_CUSTOMER_TYPE_MSG, customerType==null?"":customerType.getChineseName());
		this.showData.put(KEY_CONTRACT_NO, contractNo);
		this.showData.put(KEY_FINANCIAL_CONTRACT_NAME, financialContractName);
		this.remark=remark;
		this.balance = balance;
		this.depositAmount = depositAmount;
		this.sourceDocumentStatusInt = sourceDocumentStatusInt;
		this.sourceDocumentUuid = sourceDocumentUuid;
	}
	
	public CustomerDepositResult(SourceDocument sourceDocument, String contractNo, String financialContractName, String virtualAccountNo, BigDecimal balance){
		this(sourceDocument.getSourceDocumentNo(), virtualAccountNo,
				sourceDocument.getFirstPartyName(), sourceDocument.getFirstPartyType()==null?-1:sourceDocument.getFirstPartyType(), contractNo,
				financialContractName, balance,
				sourceDocument.getBookingAmount(), sourceDocument.getRemarkInAppendix(),
				sourceDocument.getSourceDocumentStatus()==null?-1:sourceDocument.getSourceDocumentStatus().ordinal(), sourceDocument.getSourceDocumentUuid());
	}
	
	public static CustomerDepositResult buildToDeposit(FinancialContract financialContract, Contract contract, BigDecimal balance, VirtualAccount virtualAccount){
		CustomerDepositResult customerDepositResult = new CustomerDepositResult(StringUtils.EMPTY, virtualAccount.getVirtualAccountNo(),
				virtualAccount.getOwnerName(),virtualAccount.getCustomerType()==null?-1:virtualAccount.getCustomerType(),contract==null?StringUtils.EMPTY:contract.getContractNo(),
				financialContract==null?StringUtils.EMPTY:financialContract.getContractName(), balance,
				BigDecimal.ZERO, StringUtils.EMPTY,
				SourceDocumentStatus.CREATE.ordinal(), StringUtils.EMPTY);
		return customerDepositResult;
	}
}
