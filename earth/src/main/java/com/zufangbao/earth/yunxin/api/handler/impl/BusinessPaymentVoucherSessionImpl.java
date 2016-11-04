package com.zufangbao.earth.yunxin.api.handler.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherSession;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.VirtualAccountFlowService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.VirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.vouchertask.BusinessPaymentVoucherTaskHandler;

@Component("businessPaymentVoucherSession")
public class BusinessPaymentVoucherSessionImpl implements
		BusinessPaymentVoucherSession {

	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Autowired
	private CustomerService customerService;
	@Autowired BusinessPaymentVoucherTaskHandler businessPaymentVoucherHandler;
	@Autowired
	private VirtualAccountHandler virtualAccountHandler;
	@Autowired
	private VirtualAccountFlowService virtualAccountFlowService;
	@Autowired
	private VirtualAccountService virtualAccountService;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	
	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void handler_compensatory_loan_asset_recover(){
		List<Long> depositSourceDocumentIds =  sourceDocumentService.get_deposit_source_document_connected_by(SourceDocumentExcuteResult.UNSUCCESS, SourceDocumentExcuteStatus.PREPARE);
		for (Long sourceDocumentId : depositSourceDocumentIds) {
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(sourceDocumentId);
			String sourceDocumentUuid = sourceDocument.getSourceDocumentUuid();
			String outlierSerialGlobalIdentity = sourceDocument.getOutlierSerialGlobalIdentity();
			boolean isBusinessPaymentVoucher = sourceDocumentDetailService.exist(sourceDocumentUuid, VoucherSource.BUSINESS_PAYMENT_VOUCHER.getKey(), outlierSerialGlobalIdentity);
			//过滤未关联凭证的sd
			if(isBusinessPaymentVoucher==false){
				continue;
			}
			/**
			 * 存在校验失败——》跳过
			 * 全部核销--》跳过
			 * 
			 * 
			 */
			
			String financialContractUuid = sourceDocument.getFinancialContractUuid();
			FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			if(financialContract==null){
				continue;
			}
			List<Long> detailIds = sourceDocumentDetailService.getDetailIdsBySourceDocumentUuid(sourceDocumentUuid,outlierSerialGlobalIdentity);
			logger.info("compensatory_loan_recover details ids size:"+detailIds.size()+",sourceDocumenId:"+sourceDocumentId);
			logger.info("begin to sourceDocumentDetailValidator.");
			String financialContractNo = financialContract.getContractNo();
			String ledgerBookNo = financialContract.getLedgerBookNo();
			boolean check_result = sourceDocumentDetailValidator(detailIds, financialContractNo, ledgerBookNo);
			logger.info("end to sourceDocumentDetailValidator.");
			if(check_result == false){
				logger.error("valida result false.");
				return;
			}
			
			compensatory_recover_loan_assets(detailIds, sourceDocumentId);
			BigDecimal totalIssuedAmount = sourceDocumentDetailService.getTotalAmountOfSourceDocumentDetail(sourceDocument.getSourceDocumentUuid(), outlierSerialGlobalIdentity);
			sourceDocumentService.update_after_inter_account_transfer(sourceDocumentId,totalIssuedAmount);
		}
		
	}


	@Override
	public boolean compensatory_recover_loan_assets(List<Long> sourceDocumentDetailIds, Long sourceDocumentId){
		boolean total_result = true;
		if(CollectionUtils.isEmpty(sourceDocumentDetailIds)){
			return false;
		}
		
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(sourceDocumentId);
		if(sourceDocument== null){
			return false;
		}
		FinancialContract financialContract = financialContractService.getFinancialContractBy(sourceDocument.getFinancialContractUuid());
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		boolean exsitsIndependentsRemittance = ledgerItemService.exsitsIndependentsRemittanceBy(book.getLedgerBookNo(), sourceDocument.getSourceDocumentUuid(), sourceDocument.getBookingAmount()); 
		
		Customer companyCustomer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		VirtualAccount companyVirtualAccount = virtualAccountService.getVirtualAccountByCustomerUuid(companyCustomer.getCustomerUuid());
		if(exsitsIndependentsRemittance==false){
			LedgerTradeParty company_customer_party = new LedgerTradeParty(companyCustomer.getCustomerUuid(),"");
			BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(book.getLedgerBookNo(), companyCustomer.getCustomerUuid());
			if(balance.compareTo(sourceDocument.getBookingAmount())<0){
				return false;
			}
			ledgerBookHandler.book_compensatory_remittance_virtual_account(book, company_customer_party, "", "", sourceDocument.getSourceDocumentUuid(), sourceDocument.getBookingAmount(), AssetConvertor.convertEmptyAssetCategory());
			companyVirtualAccount = virtualAccountHandler.refreshVirtualAccountBalance(financialContract.getLedgerBookNo(), companyCustomer.getCustomerUuid(), financialContract.getFinancialContractUuid());
			virtualAccountFlowService.addAccountFlow(sourceDocument.getSourceDocumentNo(), companyVirtualAccount, sourceDocument.getBookingAmount(),AccountSide.CREDIT,VirtualAccountTransactionType.INTER_ACCOUNT_REMITTANCE);
		}
		
		logger.info("begin to recover details, size:"+sourceDocumentDetailIds.size());
		for (int i=0;i<sourceDocumentDetailIds.size();i++) {
			Long sourceDocumentDetailId = sourceDocumentDetailIds.get(i);
			try {
				long start = System.currentTimeMillis();
				logger.info("begin to recover single sourceDocumentDetail,id["+sourceDocumentDetailId+"],index["+i+"]");
				boolean single_result= businessPaymentVoucherHandler.compensatory_recover_loan_asset_detail(sourceDocumentDetailId, sourceDocument.getSourceDocumentNo(), book, companyVirtualAccount);
				logger.info("end to recover single sourceDocumentDetail,id["+sourceDocumentDetailId+"],index["+i+"], use times["+(System.currentTimeMillis()-start)+"]ms, result: "+single_result+".");

				if(single_result==false){
					total_result = false;
				}
			}catch(Exception e){
				logger.error("recover details, size:"+sourceDocumentDetailIds.size()+",id:"+sourceDocumentDetailId+",index:"+i);
				e.printStackTrace();
				total_result = false;
			}
			
		}
		return total_result;
	}
	
	@Override
	public boolean sourceDocumentDetailValidator(List<Long> detailIds, String financialContractNo, String ledgerBookNo){
		if(CollectionUtils.isEmpty(detailIds)) {
			return false;
		}
		boolean is_all_succ = true;
		for (Long detailId : detailIds) {
			boolean singleResult = businessPaymentVoucherHandler.sourceDocumentDetailValidatorSingle(detailId, financialContractNo, ledgerBookNo);
			if(singleResult==false){
				is_all_succ = false;
			}
		}
		return is_all_succ;
	}

	/**
	 * 单笔核销：先校验再核销
	 */
	@Override
	public void single_compensatory_recover_loan_asset(List<Long> sourceDocumentDetailIds, SourceDocument sourceDocument) {
		logger.info("begin to sourceDocumentDetailValidator.");
		if(sourceDocument == null){
			return;
		}
		FinancialContract financialContract = financialContractService.getFinancialContractBy(sourceDocument.getFinancialContractUuid());
		if(financialContract == null) {
			return;
		}
		String financialContractNo = financialContract.getContractNo();
		String ledgerBookNo = financialContract.getLedgerBookNo();
		boolean check_result = sourceDocumentDetailValidator(sourceDocumentDetailIds, financialContractNo, ledgerBookNo);
		logger.info("end to sourceDocumentDetailValidator.");
		if(check_result == false){
			logger.error("valida result false.");
			return;
		}
		compensatory_recover_loan_assets(sourceDocumentDetailIds, sourceDocument.getId());
	}
}
