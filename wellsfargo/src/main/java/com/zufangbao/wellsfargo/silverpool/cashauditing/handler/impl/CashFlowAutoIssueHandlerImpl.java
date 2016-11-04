package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.api.model.deduct.RecordStatus;
import com.zufangbao.sun.api.model.deduct.RepaymentType;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AppAccount;
import com.zufangbao.sun.entity.account.AppAccountActiveStatus;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.DepositeAccountHandler;
import com.zufangbao.sun.ledgerbook.InsufficientBalanceException;
import com.zufangbao.sun.ledgerbook.InsufficientPenaltyException;
import com.zufangbao.sun.ledgerbook.InvalidCarryOverException;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.ledgerbook.LackBusinessVoucherException;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.AppAccountService;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.VirtualAccountFlowService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationDetailService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductPlanService;
import com.zufangbao.sun.yunxin.entity.AssetRecoverType;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductPlan;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.BusinessDocumentTypeUuid;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.AssetWriteOffModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCheckingLevel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.BusinessVoucherHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowAutoIssueHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.UpdateAssetStatusLedgerBookHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.VirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

@Component("cashFlowAutoIssueHandler")
public class CashFlowAutoIssueHandlerImpl implements CashFlowAutoIssueHandler {

	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private VirtualAccountHandler virtualAccountHandler;
	@Autowired
	private BusinessVoucherHandler businessVoucherHandler;
	@Autowired
	private JournalVoucherService journalVoucherService;
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	@Autowired
	private OrderService orderService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private CashFlowService cashFlowService;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AppAccountService appAccountService;
	@Autowired
	private VirtualAccountService virtualAccountService;
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private DeductApplicationDetailService deductApplicationDetailService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private DeductApplicationService deductApplicationService;
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private VirtualAccountFlowService virtualAccountFlowService;
	//NOTICE REMOVE QUALIFER
	@Autowired
	BankAccountCache bankAccountCache;
	@Autowired
	private UpdateAssetStatusLedgerBookHandler updateAssetStatusLedgerBookHandler;
	@Autowired
	DepositeAccountHandler depositeAccountHandler;
	@Autowired
	private DeductPlanService deductPlanService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void charge_cash_into_virtual_account(CashFlow cashFlow) {
		//确定cash是否已被制证
		//TODO 在jv或者sourceDocument中验证是否已制证
		if(cashFlow==null || cashFlow.getAuditStatus()!=AuditStatus.CREATE|| !StringUtils.isEmpty(cashFlow.getTradeUuid())){
			//cashFlow不为已创建，tradeUuid不为空，这跳过。
			return;
		}
		//TODO 校验是否被充值过
		//TODO get one
		List<FinancialContract> financialContractList = financialContractService.getFinancialContractListBy(cashFlow.getHostAccountNo());
		if(CollectionUtils.isEmpty(financialContractList) ||financialContractList.size()>1){
			logger.info("find financialContractList size is 0 or >1.");
			return;
		}
		FinancialContract financialContract = financialContractList.get(0);
		Customer customer = null;
		AssetCategory assetCategory = null;
		//查找是否为商户付款账户
		AppAccount appAccount= appAccountService.getAppAccountByAccountNo(cashFlow.getCounterAccountNo(), AppAccountActiveStatus.VALID);
		if(appAccount!=null){
			logger.info("exists appAccount.");
			App app = appAccount.getApp();
			if(app==null){
				return;
			}
			customer = customerService.getCustomer(app, CustomerType.COMPANY);
			assetCategory = AssetConvertor.convertAppDepositAssetCategory();
		} else {
			//暂不查找贷款人的账户
/*			logger.info("appAccount not exist. ready to find contractAccount");
			List<Contract> contractList = contractAccountService.getEfficientContractsExactlyBy(cashFlow.getCounterAccountNo(), cashFlow.getCounterAccountName(), financialContract);
			if(CollectionUtils.isEmpty(contractList) || contractList.size()>1){
				return;
			}
			Contract contract = contractList.get(0);
			customer = contract.getCustomer();
			assetCategory = AssetConvertor.convertDepositAssetCategory(contract);*/
		}
		if(customer==null){
			logger.info("No customer.");
			return;
		}
		
		charge_cash_into_virtual_account(cashFlow, customer, cashFlow.getTransactionAmount(), financialContract, assetCategory, null);
	}
	
	private String getContractNo(String contractUuid){
		Contract contract = contractService.getContract(contractUuid);
		if(contract!=null){
			return contract.getContractNo();
		}
		return "";
	}
	
	@Override
	public void charge_cash_into_virtual_account(CashFlow cashFlow, Customer customer, BigDecimal bookingAmount, FinancialContract financialContract, AssetCategory assetCategory, String remark){
		if(cashFlow==null || customer==null || bookingAmount==null ||financialContract ==null){
			return;
		}
		if(assetCategory==null){
			assetCategory= ledgerItemService.getDepositAssetCategoryByCustomer(customer);
		}
		Company company = financialContract.getCompany();
		Long companyId = company.getId();
		//create before
		VirtualAccount virtualAccount = virtualAccountService.create_if_not_exist_virtual_account(customer.getCustomerUuid(), financialContract.getFinancialContractUuid(), assetCategory.getRelatedContractUuid());
		
		SourceDocument sourceDocument = sourceDocumentHandler.createDepositeReceipt(cashFlow, companyId, bookingAmount, customer, assetCategory.getRelatedContractUuid(), financialContract.getFinancialContractUuid(),virtualAccount.getVirtualAccountNo(), null);
		//
		Contract contract = contractService.getContract(assetCategory.getRelatedContractUuid());
		//issue bv
		//BusinessVoucher businessVoucher = businessVoucherHandler.createIfNotExistBusinessVoucherForRepaymentBill(sourceDocument.getSourceDocumentUuid(), companyId, bookingAmount, BusinessDocumentTypeUuid.BUSINESS_DOC_BUSINESS_VOUCHER_TYPE_UUID, BusinessDocumentTypeUuid.BUSINESS_DOC_BILLING_PLAN_TYPE_UUID);
		//issue jv
		JournalVoucher journalVoucher = new JournalVoucher();
		journalVoucher.copyFromSourceDocument(sourceDocument);
		journalVoucher.copyFromCashFlow(cashFlow,companyId);
		journalVoucher.fill_voucher_and_booking_amount(sourceDocument.getSourceDocumentUuid(), BusinessDocumentTypeUuid.BUSINESS_DOC_BUSINESS_VOUCHER_TYPE_UUID,
		"", bookingAmount, JournalVoucherStatus.VOUCHER_ISSUED, JournalVoucherCheckingLevel.AUTO_BOOKING, JournalVoucherType.BANK_CASHFLOW_DEPOSIT);
//		journalVoucher.setJournalVoucherType(JournalVoucherType.BANK_CASHFLOW_DEPOSIT);
		journalVoucher.fillBillContractInfo(financialContract.getFinancialContractUuid(), assetCategory.getRelatedContractUuid(), "", financialContract.getContractName(), getContractNo(assetCategory.getRelatedContractUuid()), null, null);
		journalVoucher.fillCashFlowAccountInfo(cashFlow.getCounterAccountName());
		journalVoucherService.save(journalVoucher);
		
		//refresh appArriveRecord
		cashFlow.changeIssuedAmountAndAuditStatus(bookingAmount);
		cashFlowService.update(cashFlow);
		
		//ledger_book
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		LedgerTradeParty customerParty = new LedgerTradeParty(customer.getCustomerUuid(),company.getUuid());
		
		DepositeAccountInfo bankAccountInfo=bankAccountCache.extractFirstBankAccountFrom(financialContract);
		DepositeAccountInfo custmerAccountInfo=new DepositeAccountInfo(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT,customerParty,DepositeAccountType.VIRTUAL_ACCOUNT);
		
		ledgerBookVirtualAccountHandler.deposit_independent_account_assets(book, bankAccountInfo, custmerAccountInfo, assetCategory, bookingAmount, journalVoucher.getJournalVoucherUuid(), "", sourceDocument.getSourceDocumentUuid());
		
		
		//refresh virtualAccount
		VirtualAccount refreshedVirtualAccount = virtualAccountHandler.refreshVirtualAccountBalance(financialContract.getLedgerBookNo(), customer.getCustomerUuid(), financialContract.getFinancialContractUuid());
		virtualAccountFlowService.addAccountFlow(sourceDocument.getSourceDocumentNo(), refreshedVirtualAccount, bookingAmount,AccountSide.DEBIT,VirtualAccountTransactionType.DEPOSIT);
		
	}

	@Override
	public void write_off_balance() {
		
		FinancialContract financialContract = new FinancialContract();
		Long companyId = financialContract.getCompany().getId();
		//1. get customer whose balance >0
		//2. find today order
		//3. check money before write off
		//4. write off
		List<VirtualAccount> virtualAccounts = virtualAccountHandler.get_account_with_balance();
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		for (VirtualAccount virtualAccount : virtualAccounts) {
			String customerUuid = virtualAccount.getOwnerUuid();
			Customer customer = customerService.getCustomer(customerUuid); // get customer
			if(customer==null || customer.getCustomerType()==CustomerType.COMPANY){
				continue;
			}
			Contract contract = new Contract();//get Contract() or  get from virtual_account;
			
			// getOrderList
			List<Order> orderList = orderService.get_repayment_order_not_in_process_and_asset_not_clear(contract, new Date(), AssetSetActiveStatus.OPEN);
			if(CollectionUtils.isEmpty(orderList) ||orderList.size()>1){
				continue;
			}
			
			//update orderStatus,assetStatus
			//issue jv, bv, source(冲销单)
			Order order = orderList.get(0);
			
			BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(financialContract.getLedgerBookNo(), customerUuid); // getAmount
			BigDecimal shouldPayMoney = null;//get pay money;
			if(balance.compareTo(shouldPayMoney)<0){
				continue;
			}
			//find existed sourceDocument
			
			BusinessVoucher businessVoucher = businessVoucherHandler.createIfNotExistBusinessVoucherForRepaymentBill(order.getRepaymentBillId(), companyId, order.getTotalRent(), DefaultTypeUuid.DEFAULT_BILLING_PLAN_TYPE_UUID, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			businessVoucherHandler.issueDefaultBusinessVoucher(order, companyId, shouldPayMoney);
			//issue jv
			//TODO query or create sourceDocument
			JournalVoucher journalVoucher = new JournalVoucher();
			journalVoucher.fill_voucher_and_booking_amount("", DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID,
					businessVoucher.getBusinessVoucherUuid(), order.getTotalRent(), JournalVoucherStatus.VOUCHER_ISSUED,
					JournalVoucherCheckingLevel.AUTO_BOOKING, JournalVoucherType.VIRTUAL_ACCOUNT_TRANSFER);
			journalVoucher.fillBillContractInfo(financialContract.getFinancialContractUuid(), contract.getUuid(), order.getAssetSet().getAssetUuid(), financialContract.getContractName(), contract.getContractNo(), order.getAssetSet().getSingleLoanContractNo(), order.getOrderNo());
			journalVoucherService.save(journalVoucher);
			
			ledgerBookVirtualAccountHandler.inner_transfer_independent_account_assets(book, order, order.getTotalRent(), journalVoucher.getJournalVoucherUuid(), businessVoucher.getBusinessVoucherUuid(), "");
			//refresh 
			VirtualAccount refreshedVirtualAccount = virtualAccountHandler.refreshVirtualAccountBalance(financialContract.getLedgerBookNo(), customer.getCustomerUuid(), financialContract.getFinancialContractUuid());
			virtualAccountFlowService.addAccountFlow(journalVoucher.getJournalVoucherNo(), refreshedVirtualAccount, journalVoucher.getBookingAmount(),AccountSide.CREDIT,VirtualAccountTransactionType.INTER_ACCOUNT_REMITTANCE);
			//update order or asset status from jv or ledgerbook
			
		}
		
	}

	@Override
	public void deposit_cancel(String sourceDocumentUuid) {
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(sourceDocumentUuid);
		if(sourceDocument==null){
			return;
		}
		String customerUuid = sourceDocument.getFirstPartyId();
		FinancialContract financialContract = financialContractService.getFinancialContractBy(sourceDocument.getFinancialContractUuid());
		Long companyId = financialContract.getCompany().getId();
		BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(financialContract.getLedgerBookNo(), customerUuid);
		// checkout virtualAccount amount, sourceDocument;
		if(balance.compareTo(sourceDocument.getBookingAmount())<=0){
			return;
			// throw exception
		}
		
		//lapse jv, bv
		
		JournalVoucher jv = journalVoucherService.getInforceDepositJournalVoucher(companyId, sourceDocumentUuid);//get jv from sourcedoc;  type + sourceDocumentUuid
		BusinessVoucher bv = null;//get bv from sourcedoc 
		
		
		//roll_back virtualAccount
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		Customer customer = customerService.getCustomer(customerUuid);
		AssetCategory assetCategory = ledgerItemService.getDepositAssetCategoryByCustomer(customer);
		ledgerItemService.roll_back_ledgers_by_voucher(book, assetCategory, jv.getJournalVoucherUuid(), bv.getBusinessVoucherUuid(), sourceDocumentUuid);
		
		//lapse sd;
		
		//refresh virtualAccount;
		//refresh cash
		
	}

	@Override
	public void recover_assets_online_deduct_by_interface(){
		List<FinancialContract> financialContractList = financialContractService.list(FinancialContract.class, new Filter());
		for (FinancialContract financialContract : financialContractList) {
			try{
				logger.info("begin to recover_assets_online_deduct_by_interface_each_financial_contract, financialContractNo["+financialContract.getContractNo()+"].");
				recover_assets_online_deduct_by_interface_each_financial_contract(financialContract);
				logger.info("end to recover_assets_online_deduct_by_interface_each_financial_contract, financialContractNo["+financialContract.getContractNo()+"].");
			} catch(Exception e){
				logger.error("occur error when recover_assets_online_deduct_by_interface_each_financial_contract, financialContractNo["+financialContract.getContractNo()+"].");
				e.printStackTrace();
			}
		}
	}
	
	private String getMerIdClearingNo(String deductApplicationUuid){
		DeductPlan deductPlan = deductPlanService.getDeductPlanByDeductApplicationUuid(deductApplicationUuid, DeductApplicationExecutionStatus.SUCCESS);
		if(deductPlan==null){
			return "";
		}
		return deductPlan.getMerId_ClearingNo();
		
	}
	
	@Override
	public void recover_assets_online_deduct_by_interface_each_financial_contract(FinancialContract financialContract){
		List<DeductApplication> deductApplicationList = deductApplicationService.get_success_un_write_off_application(financialContract.getFinancialContractUuid());
		Account account = financialContract.getCapitalAccount();
		Company company = financialContract.getCompany();
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		//1. 扫描扣款成功为未销账的扣款结果。
		//2. 根据是否已入账，来判断提前还款是否已被处理； 根据 unearned!=0(或者repaymentType) && 应收 != 0 来判断是否为应收资产，是否已被核销完；
		//2. 生成sourceDocument 以及sourceDocumentDetail
		//3. 生成 jv:  billingPlanUuid(assetUuid),
		//4. create order and refesh asset
		Map<String,AssetWriteOffModel> asset_uuid_and_write_off_map = new HashMap<String,AssetWriteOffModel>();
		for (DeductApplication deductApplication : deductApplicationList) {
			String contractUniqueId = deductApplication.getContractUniqueId();
			Contract contract = contractService.getContractByUniqueId(contractUniqueId);
			ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
			Customer customer = contract.getCustomer();
			List<DeductApplicationRepaymentDetail> dedutRepaymentDetailList = deductApplicationDetailService.getRepaymentDetailsBy(deductApplication.getDeductApplicationUuid()); 
			String merIdClearingNo = getMerIdClearingNo(deductApplication.getDeductApplicationUuid());
			//生成sourceDocument
			if(deductApplication.getRecordStatus()==RecordStatus.UNRECORDED){
				
				SourceDocument sourceDocument = SourceDocumentImporter.createThirdPartyDeductionVoucher(company,deductApplication, contractAccount,customer,contract.getUuid());
				List<BigDecimal> amountList = new ArrayList<BigDecimal>();
				sourceDocumentService.save(sourceDocument);
				if(deductApplication.getRepaymentType()==RepaymentType.ADVANCE && deductApplication.getRecordStatus()==RecordStatus.UNRECORDED
						&& !financialContract.isAllowWriteOffInAdvanceWhenPrepayment()){
					LedgerTradeParty tradeParty = new LedgerTradeParty(financialContract.getCompany().getUuid(),"");
					DepositeAccountInfo unionAccountInfo = bankAccountCache.extractUnionPayAccountFrom(financialContract,merIdClearingNo);
					AssetCategory assetCategory = AssetConvertor.convertAssetCategory(contract);
					//book时是否用次sourceDocumentUuid?
					ledgerBookHandler.book_bank_saving_and_deposit_received(book, assetCategory, tradeParty, unionAccountInfo, "", "", sourceDocument.getSourceDocumentUuid(), amountList);
					deductApplication.setRecordStatus(RecordStatus.RECORDED);
				}
				for (DeductApplicationRepaymentDetail deductDetail : dedutRepaymentDetailList) {
					SourceDocumentDetail sourceDocumentDetail = SourceDocumentDetailImporter.createSourceDocumentDetail(sourceDocument.getSourceDocumentUuid(), deductDetail, deductApplication.getRequestNo(), contractAccount, account.getAccountNo());
					//挂帐
					sourceDocumentDetailService.save(sourceDocumentDetail);
					amountList.add(sourceDocumentDetail.getAmount());
				}
				
			}
			for (DeductApplicationRepaymentDetail deductDetail : dedutRepaymentDetailList) {
				try{
					
					DepositeAccountInfo accountInfo = null;
					String assetSetUuid = deductDetail.getAssetSetUuid();
					AssetSet assetSet = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
					BigDecimal unearnedAmount = ledgerBookStatHandler.get_unearned_amount(book.getLedgerBookNo(), assetSetUuid);
					BigDecimal receivalbeAmount = ledgerBookStatHandler.get_receivable_amount(book.getLedgerBookNo(), assetSetUuid, customer.getCustomerUuid());
					if(deductApplication.getRecordStatus()==RecordStatus.RECORDED){
						if(BigDecimal.ZERO.compareTo(unearnedAmount)!=0 && BigDecimal.ZERO.compareTo(receivalbeAmount)==0){
							//为未到期资产
							continue;
						}
						//预收款核销
						accountInfo = depositeAccountHandler.extractDepositReceivedAccount(assetSet);
					
					} else if(deductApplication.getRecordStatus()==RecordStatus.UNRECORDED){
						if(BigDecimal.ZERO.compareTo(unearnedAmount)!=0 && BigDecimal.ZERO.compareTo(receivalbeAmount)==0){
							//为未到期资产
							ledgerBookHandler.amortize_loan_asset_to_receivable(book, assetSet);
						}
						//银行存款核销
						accountInfo = bankAccountCache.extractUnionPayAccountFrom(financialContract, merIdClearingNo);
					}
					SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.getValidSourceDocumentDetail(VoucherSource.THIRD_PARTY_DEDUCTION_VOUCHER.getKey(),deductApplication.getDeductApplicationUuid() ,VoucherType.THIRD_PARTY_DEDUCTION_VOUCHER.getKey(),deductDetail.getDeductApplicationDetailUuid());
					if(sourceDocumentDetail==null){
						continue;
					}
					AssetWriteOffModel writeOffModel = new AssetWriteOffModel(assetSet, sourceDocumentDetail,accountInfo, deductApplication,deductDetail.getTotalAmount());
					asset_uuid_and_write_off_map.put(assetSetUuid, writeOffModel);
					
				} catch (Exception e){
					e.printStackTrace();
					
				}
				
			}
			
		}
		//TODO by each deductApplication	
		for(AssetWriteOffModel writeOffModel :asset_uuid_and_write_off_map.values()){
			try{
				logger.info("begin to recover asset by thirdPartyDeduct, assetId["+(writeOffModel.getAssetSet()==null?"":writeOffModel.getAssetSet().getId())+"].");
				DeductApplication deductApplication = writeOffModel.getDeductApplication();
				write_off(financialContract, writeOffModel.getBookingAmount(), writeOffModel.getSourceDocumentDetail(),
						writeOffModel.getAssetSet(),writeOffModel.getAccountInfo());
				deductApplication.setRecordStatus(RecordStatus.WRITE_OFF);
				deductApplicationService.save(deductApplication);
			} catch(Exception e){
				logger.error("recover asset by thirdPartyDeduct occur error.");
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	private void write_off(FinancialContract financialContract, BigDecimal bookingAmount, SourceDocumentDetail sourceDocumentDetail
			,AssetSet assetSet,DepositeAccountInfo accountInfo) throws LackBusinessVoucherException, AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException{
		Contract contract = assetSet.getContract();
		
		Order order = new Order(bookingAmount, "", assetSet,
				contract.getCustomer(), financialContract, new Date());
		order.updateClearStatus(new Date(), true);
		orderService.save(order);
		
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(sourceDocumentDetail.getSourceDocumentUuid());
		
		JournalVoucher journalVoucher = new JournalVoucher();
		journalVoucher.createFromThirdPartyDeductionVoucher(sourceDocumentDetail,AccountSide.DEBIT, financialContract.getCompany(),financialContract.getCapitalAccount(), sourceDocument==null?"":sourceDocument.getSourceDocumentNo());
//		journalVoucher.setJournalVoucherType(JournalVoucherType.THIRD_PARTY_DEDUCT_VOUCHER);
		journalVoucher.fill_voucher_and_booking_amount(assetSet.getAssetUuid(), BusinessDocumentTypeUuid.THIRD_PARTY_DEDUCT_BV_UUID,
				"", bookingAmount, JournalVoucherStatus.VOUCHER_ISSUED, JournalVoucherCheckingLevel.AUTO_BOOKING, JournalVoucherType.THIRD_PARTY_DEDUCT_VOUCHER);
		journalVoucher.fillBillContractInfo(financialContract.getFinancialContractUuid(), contract.getUuid(), assetSet.getAssetUuid(), financialContract.getContractName(), contract.getContractNo(), assetSet.getSingleLoanContractNo(), order.getOrderNo());
		journalVoucher.fillCashFlowAccountInfo("");
		journalVoucherService.save(journalVoucher);
		
		journalVoucherHandler.recover_loan_asset_or_guarantee(assetSet, AssetRecoverType.LOAN_ASSET, bookingAmount, journalVoucher.getJournalVoucherUuid(), "", "", accountInfo, true);
		
		//update asset
		updateAssetStatusLedgerBookHandler.updateAssetsFromLedgerBook(financialContract.getLedgerBookNo(), Arrays.asList(assetSet.getAssetUuid()), false);
		
	}

}
