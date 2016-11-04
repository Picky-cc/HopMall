package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;

public class SourceDocumentImporter {

	public static SourceDocument createSourceDocumentFrom(Long companyId, BatchPayRecord batchPayRecord, TransferApplication transferApplication,Account receiveAccount) {
		return initOnlineIntraSystemDeductSSourceDocument(companyId,batchPayRecord,transferApplication,receiveAccount);
	}

	public static SourceDocument createSourceDocumentFrom(Long companyId, OfflineBill offlineBill){
		return initSourceDocumentFromOfflineBill(companyId, offlineBill);
	}

	public static SourceDocument createDepositReceipt(Long companyId, CashFlow cashFlow, Customer customer,String virtualAccountUuid, String relatedContractUuid,String financialContractUuid, String virtualAccountNo, String remark, BigDecimal bookingAmount){
		SourceDocument sourceDocument = new SourceDocument();
		initDepositReceipt(sourceDocument,cashFlow,companyId, bookingAmount);
		String depositNo = GeneratorUtils.generateDepositNo();
		sourceDocument.initTypePart(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS, ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT, StringUtils.EMPTY, 
			ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS_CODE, ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT_CODE, StringUtils.EMPTY, 
			customer.getCustomerUuid(), String.valueOf(companyId), 
			virtualAccountUuid,relatedContractUuid,depositNo,financialContractUuid,
			customer.getCustomerType()==null?-1:customer.getCustomerType().ordinal(),customer.getName(),virtualAccountNo,
					SourceDocumentExcuteStatus.PREPARE, SourceDocumentExcuteResult.UNSUCCESS, RepaymentAuditStatus.CREATE);
		sourceDocument.setSourceDocumentStatus(SourceDocumentStatus.SIGNED);
		return sourceDocument;
	}

	public static SourceDocument createThirdPartyDeductionVoucher(Company company,DeductApplication deductApplication, ContractAccount contractAccount,Customer customer,String contractUuid){
		SourceDocument sourceDocument = new SourceDocument();
				createDeductSourceDocument(sourceDocument,company==null?null:company.getId(), deductApplication, contractAccount);
		sourceDocument.fillBillInfo(deductApplication.getFinancialContractUuid(), contractUuid);
		sourceDocument.fillPartyInfo(company, customer);
		return sourceDocument;
	}

	private static void createDeductSourceDocument(SourceDocument srcDoc,Long commpanyId,
			DeductApplication deductionApplication,
			ContractAccount contractAccount) {
		srcDoc.setSourceDocumentUuid(UUID.randomUUID().toString());
		srcDoc.setSourceDocumentType(SourceDocumentType.NOTIFY);
		srcDoc.setCreateTime(new Date());
		srcDoc.setSourceDocumentStatus(SourceDocumentStatus.SIGNED);
		srcDoc.setSourceAccountSide(AccountSide.DEBIT);
		srcDoc.setBookingAmount(deductionApplication.getActualDeductTotalAmount());
		srcDoc.setAuditStatus(RepaymentAuditStatus.CREATE);
		srcDoc.setOutlierDocumentUuid(deductionApplication.getDeductApplicationUuid());
		srcDoc.setOutlierTradeTime(deductionApplication.getLastModifiedTime());
		if(contractAccount!=null){
			srcDoc.setOutlierCounterPartyAccount(contractAccount.getPayAcNo());
			srcDoc.setOutlierCounterPartyName(contractAccount.getPayerName());
		}
		srcDoc.setOutlierCompanyId(commpanyId);
		//TODO 外部流水或者请求号标示
		srcDoc.setOutlierSerialGlobalIdentity(deductionApplication.getRequestNo());
		srcDoc.setOutlierMemo(deductionApplication.getRemark());
		srcDoc.setOutlierAmount(deductionApplication.getPlannedDeductTotalAmount());
		srcDoc.setOutlierSettlementModes(SettlementModes.REMITTANCE);
		srcDoc.setOutlierBreif(deductionApplication.getRepaymentPlanCodeList());
		srcDoc.setOutlierAccountSide(AccountSide.fromAccountSide(deductionApplication.getTranscationType()));
	
		srcDoc.setCompanyId(commpanyId);
		srcDoc.setFirstOutlierDocType(SourceDocument.FIRSTOUTLIER_DEDUCTAPPLICATION);
		srcDoc.setSourceDocumentNo(GeneratorUtils.generateDudectPlanVoucherNo());
	}

	public static SourceDocument initSourceDocumentFromOfflineBill(Long companyId, OfflineBill offlineBill)
	{
		return new SourceDocument(companyId,SourceDocumentType.NOTIFY, new Date(),
				null, SourceDocumentStatus.CREATE,
				AccountSide.DEBIT, BigDecimal.ZERO,
				offlineBill.getOfflineBillUuid(), offlineBill.getTradeTime(),
				offlineBill.getPayerAccountNo(), offlineBill.getPayerAccountName(),
				StringUtils.EMPTY, StringUtils.EMPTY,
				null, companyId,
				offlineBill.getSerialNo(), offlineBill.getAmount(), SettlementModes.REMITTANCE,
				AccountSide.DEBIT,SourceDocument.FIRSTOUTLIER_OFFLINEBILL,StringUtils.EMPTY,StringUtils.EMPTY,RepaymentAuditStatus.CREATE,
				StringUtils.EMPTY,StringUtils.EMPTY,StringUtils.EMPTY,
				StringUtils.EMPTY,StringUtils.EMPTY,StringUtils.EMPTY,
				null,null,StringUtils.EMPTY);
	}

	public static void initDepositReceipt(SourceDocument srcDoc,CashFlow cashFlow, Long outlierCompanyId,
			BigDecimal bookingAmount) {
		srcDoc.setSourceDocumentUuid(UUID.randomUUID().toString());
		srcDoc.setSourceDocumentType(SourceDocumentType.NOTIFY);
		srcDoc.setCreateTime(new Date());
		srcDoc.setSourceDocumentStatus(SourceDocumentStatus.CREATE);
		srcDoc.setSourceAccountSide(AccountSide.fromLedgerBookAccountSide(cashFlow.getAccountSide()));
		srcDoc.setBookingAmount(bookingAmount);
		srcDoc.setAuditStatus(RepaymentAuditStatus.CREATE);
		srcDoc.setOutlierDocumentUuid(cashFlow.getCashFlowUuid());
		srcDoc.setOutlierTradeTime(cashFlow.getTransactionTime());
		srcDoc.setOutlierCounterPartyAccount(cashFlow.getCounterAccountNo());
		srcDoc.setOutlierCounterPartyName(cashFlow.getCounterAccountName());
		srcDoc.setOutlierAccount(cashFlow.getHostAccountNo());
		srcDoc.setOutlierCompanyId(outlierCompanyId);
		srcDoc.setOutlierSerialGlobalIdentity(cashFlow.getBankSequenceNo());
		srcDoc.setOutlierMemo(cashFlow.getRemark());
		srcDoc.setOutlierAmount(cashFlow.getTransactionAmount());
		srcDoc.setOutlierSettlementModes(SettlementModes.REMITTANCE);
		srcDoc.setOutlierBreif(cashFlow.getRemark());
		srcDoc.setOutlierAccountSide(AccountSide.fromLedgerBookAccountSide(cashFlow.getAccountSide()));
		//TODO 
		srcDoc.setCompanyId(outlierCompanyId);
	}
	
	public static SourceDocument initOnlineIntraSystemDeductSSourceDocument(Long companyId, BatchPayRecord batchPayRecord, TransferApplication transferApplication,Account receiveAccount){
		return new SourceDocument(companyId,SourceDocumentType.NOTIFY, new Date(),
				null, SourceDocumentStatus.CREATE,
				AccountSide.DEBIT, BigDecimal.ZERO,
				batchPayRecord.getBatchPayRecordUuid(), batchPayRecord.getTransDateTimeDateValue(),
				transferApplication.getContractAccount().getPayAcNo(), transferApplication.getContractAccount().getPayerName(),
				receiveAccount.getAccountNo(), receiveAccount.getAccountName(),
				receiveAccount.getId(), companyId,
				batchPayRecord.getSerialNo(), transferApplication.getAmount(), SettlementModes.REMITTANCE,
				AccountSide.DEBIT,SourceDocument.FIRSTOUTLIER_BATCHPAY_RECORD,StringUtils.EMPTY,StringUtils.EMPTY,RepaymentAuditStatus.CREATE,
				StringUtils.EMPTY,StringUtils.EMPTY,StringUtils.EMPTY,
				StringUtils.EMPTY,StringUtils.EMPTY,StringUtils.EMPTY,
				null,null,StringUtils.EMPTY);
	}

	public static SourceDocument createActivePaymentVoucherSourceDocument(FinancialContract financialContract, Contract contract, CashFlow cashFlow, VirtualAccount virtualAccount){
		Long companyId = financialContract.getCompany().getId();
		String virtualAccountUuid = virtualAccount.getVirtualAccountUuid();
		String virtualAccountNo = virtualAccount.getVirtualAccountNo();
		String contractUuid = contract.getUuid();
		Customer customer = contract.getCustomer();
		String financialContractUuid = financialContract.getUuid();

		SourceDocument sourceDocument = new SourceDocument();
		initDepositReceipt(sourceDocument, cashFlow, companyId, BigDecimal.ZERO);
		String sourceDocumentNo = GeneratorUtils.generateDepositNo();
		sourceDocument.initTypePart(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS,
				ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT, StringUtils.EMPTY,
				ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS_CODE,
				ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT_CODE, StringUtils.EMPTY,
				customer.getCustomerUuid(), String.valueOf(companyId), virtualAccountUuid, contractUuid,
				sourceDocumentNo, financialContractUuid, customer.getCustomerType().ordinal(), customer.getName(),
				virtualAccountNo, SourceDocumentExcuteStatus.PREPARE, SourceDocumentExcuteResult.UNSUCCESS,
				RepaymentAuditStatus.CREATE);
		return sourceDocument;
	}
}
