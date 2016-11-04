package com.zufangbao.wellsfargo.yunxin.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentVO;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl.CashFlowHandlerImpl;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CustomerDepositResult;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

@Component("sourceDocumentHandler")
public class SourceDocumentHandlerImpl implements SourceDocumentHandler {

	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	
	@Autowired
	private VirtualAccountService virtualAccountService;
	
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CashFlowService cashFlowService;
	
	private static Log logger = LogFactory.getLog(SourceDocumentHandlerImpl.class);
	
	@Override
	public void createSourceDocumentForTransferApplication(TransferApplication transferApplication, Long companyId, Account receiveAccount) {
		if(transferApplication==null || companyId==null || receiveAccount==null){
			return;
		}
		BatchPayRecord batchPayRecord = batchPayRecordService.load(BatchPayRecord.class, transferApplication.getBatchPayRecordId());
		boolean existsSourceDocument = sourceDocumentService.existsBatchPayRecordUuid(batchPayRecord.getBatchPayRecordUuid(), AccountSide.DEBIT, companyId);
		if(existsSourceDocument){
			return;
		}
		SourceDocument sourceDocument = SourceDocumentImporter.createSourceDocumentFrom(companyId, batchPayRecord, transferApplication, receiveAccount);
		sourceDocumentService.save(sourceDocument);
	}

	@Override
	public void createVouchersForOfflineBill(OfflineBill offlineBill, Long companyId, List<Order> orderList) throws VoucherException{
		if(offlineBill==null || companyId==null){
			return;
		}
		SourceDocument sourceDocument = SourceDocumentImporter.createSourceDocumentFrom(companyId, offlineBill);
		sourceDocumentService.save(sourceDocument);
		journalVoucherHandler.create_JV_and_BV_from_sourceDocument(sourceDocument,orderList, JournalVoucherType.OFFLINE_BILL_ISSUE);
	}
	
	@Override
	public void createOnlineSouceDocumentByDate(Date date) {
		List<FinancialContract> financialContractList = financialContractService.list(FinancialContract.class,new Filter());
		for (FinancialContract financialContract : financialContractList) {
			try{
				createSouceDocument(date,financialContract);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createSouceDocument(Date date,FinancialContract financialContract) {
		if(date==null || financialContract==null){
			return;
		}
		List<TransferApplication> transferApplicationList = transferApplicationService.getTransferApplicationListBy(financialContract.getId(), ExecutingDeductStatus.SUCCESS, date);
		for (TransferApplication transferApplication : transferApplicationList) {
			try {
				createSourceDocumentForTransferApplication(transferApplication, financialContract.getCompany().getId(), financialContract.getCapitalAccount());
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public OfflineBillConnectionState getOfflineBillConnectionState(OfflineBill offlineBill) {
		List<SourceDocument> sourceDocuments = sourceDocumentService.getSourceDocumentByOfflineBillUuid(offlineBill.getOfflineBillUuid());
		SourceDocument sourceDocument = CollectionUtils.isNotEmpty(sourceDocuments) ? sourceDocuments.get(0) : null;
		
		BigDecimal paidAmount = sourceDocument.getBookingAmount();
		if(BigDecimal.ZERO.compareTo(paidAmount) == 0) {
			return OfflineBillConnectionState.NONE;
		}
		if(BigDecimal.ZERO.compareTo(paidAmount) == -1) {
			BigDecimal amount = offlineBill.getAmount();
			if(paidAmount.compareTo(amount) == -1) {
				return OfflineBillConnectionState.PART;
			}
		}
		return OfflineBillConnectionState.ALL;
	}
	
	@Override
	public SourceDocument createDepositeReceipt(CashFlow cashFlow, Long companyId, BigDecimal amount,Customer customer, String relatedContractUuid, String financialContractUuid, String virtualAccountNo, String remark) {
		VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByCustomerUuid(customer.getCustomerUuid());
		SourceDocument sourceDocument = SourceDocumentImporter.createDepositReceipt(companyId, cashFlow, customer, virtualAccount==null?"":virtualAccount.getVirtualAccountUuid(), relatedContractUuid,financialContractUuid,virtualAccountNo, null, amount);
		sourceDocumentService.save(sourceDocument);
		return sourceDocument;
	}

@Override
	public List<SourceDocumentVO> castSourceDocumentVO(
			List<SourceDocument> sourceDocumentList) {
		
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return Collections.emptyList();
		}
		
		List<SourceDocumentVO> sourceDocumentVOs = new ArrayList<SourceDocumentVO>();
		
		for (SourceDocument sourceDocument : sourceDocumentList) {
			VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByVirtualAccountUuid(sourceDocument.getVirtualAccountUuid());
			
			Contract contract = null;
			if(sourceDocument.getRelatedContractUuid() != null){
				contract = contractService.getContract(sourceDocument.getRelatedContractUuid());
			}
			
			CashFlow cashFlow  = null;
			if(sourceDocument.getOutlierDocumentUuid() != null){
				 cashFlow = cashFlowService.getCashFlowByCashFlowUuid(sourceDocument.getOutlierDocumentUuid());
			}
			
			FinancialContract financialContract = null;
			if(sourceDocument.getFinancialContractUuid() != null){
				 financialContract = financialContractService.getFinancialContractBy(sourceDocument.getFinancialContractUuid());
			}
			
			if(sourceDocument != null){
				SourceDocumentVO sourceDocumentVO = new SourceDocumentVO(sourceDocument, virtualAccount, contract, financialContract,cashFlow);
				sourceDocumentVOs.add(sourceDocumentVO);
			}
			
		}
		
		return sourceDocumentVOs;
	}
	
	@Override
	public List<CustomerDepositResult> convertToDepositResult(List<SourceDocument> sourceDocumentList){
		List<CustomerDepositResult> customerDepositResultList = new ArrayList<CustomerDepositResult>();
		if(CollectionUtils.isEmpty(sourceDocumentList)){
			return customerDepositResultList;
		}
		for (SourceDocument sourceDocument : sourceDocumentList) {
			if(sourceDocument==null){
				continue;
			}
			CustomerDepositResult customerDepositResult = convertToCustomerDepositResult(sourceDocument);
			customerDepositResultList.add(customerDepositResult);
		}
		return customerDepositResultList;
	}
	
	private CustomerDepositResult convertToCustomerDepositResult(SourceDocument sourceDocument){
		VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByVirtualAccountUuid(sourceDocument.getVirtualAccountUuid());
		String contractUuid = sourceDocument.getRelatedContractUuid();
		String contractNo = "";
		if(!StringUtils.isEmpty(contractUuid)){
			Contract contract = contractService.getContract(contractUuid);
			if(contract!=null){
				contractNo = contract.getContractNo();
			}
		}
		String financialContractName = "";
		BigDecimal balance = BigDecimal.ZERO;
		FinancialContract financialContract = financialContractService.getFinancialContractBy(sourceDocument.getFinancialContractUuid());
		if(financialContract!=null){
			financialContractName = financialContract.getContractName();
			balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(financialContract.getLedgerBookNo(), sourceDocument.getFirstPartyId());
		}
		
		CustomerDepositResult customerDepositResult = new CustomerDepositResult(sourceDocument, contractNo, financialContractName, virtualAccount.getVirtualAccountNo(), balance);
		return customerDepositResult;
	}

}
