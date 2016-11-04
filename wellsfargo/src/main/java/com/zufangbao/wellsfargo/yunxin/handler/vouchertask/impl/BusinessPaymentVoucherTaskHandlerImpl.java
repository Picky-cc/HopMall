package com.zufangbao.wellsfargo.yunxin.handler.vouchertask.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.voucher.VoucherParameter;
import com.zufangbao.gluon.exception.GlobalRuntimeException;
import com.zufangbao.gluon.spec.global.GlobalErrorMsgSpec.VoucherErrorMsgSpec;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.VoucherPayer;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCheckingLevel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailCheckState;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.BusinessVoucherHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.UpdateAssetStatusLedgerBookHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.yunxin.handler.vouchertask.BusinessPaymentVoucherTaskHandler;

/**
 * @author louguanyang
 *
 */
@Component("businessPaymentVoucherTaskHandler")
public class BusinessPaymentVoucherTaskHandlerImpl implements BusinessPaymentVoucherTaskHandler {

	private static final Log logger = LogFactory.getLog(BusinessPaymentVoucherTaskHandlerImpl.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private JournalVoucherService journalVoucherService;
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private BusinessVoucherHandler businessVoucherHandler;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UpdateAssetStatusLedgerBookHandler updateAssetStatusLedgerBookHandler;
	@Autowired
	private AssetPackageService assetPackageService;
	
	private Order createOrder(BigDecimal bookingAmount,AssetSet assetSet,Contract contract, FinancialContract financialContract){
		Order order = new Order(bookingAmount, "", assetSet, contract.getCustomer(), financialContract, new Date());
		order.updateClearStatus(new Date(), true);
		orderService.save(order);
		return order;
	}

	@Override
	public boolean compensatory_recover_loan_asset_detail(Long sourceDocumentDetailId, String sourceDocumentNo, LedgerBook book, VirtualAccount companyVirtualAccount) {
		if(book==null){
			logger.info("ledgerBook is null");
			return false;
		}
		SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.getSourceDocumentDetailBy(sourceDocumentDetailId);
		if(sourceDocumentDetail==null){ 
			logger.info("sourceDocumentDetail is null");
			return false;
		}
		if(sourceDocumentDetail.getCheckState()!=SourceDocumentDetailCheckState.CHECK_SUCCESS){ 
			logger.info("sourceDocumentDetail checkStatus is not success");
			return false;
		}
		if(sourceDocumentDetail.getStatus()!=SourceDocumentDetailStatus.UNSUCCESS){ 
			logger.info("sourceDocumentDetail status is not unsuccess");
			return false;
		}
		
		long start = System.currentTimeMillis();
		String repaymentPlanNo = sourceDocumentDetail.getRepaymentPlanNo();
		AssetSet assetSet = repaymentPlanService.getRepaymentPlanByRepaymentPlanNo(repaymentPlanNo);
		if(assetSet==null ||assetSet.isClearAssetSet()){
			logger.info("assetSet is null or not clear");
			return false;
		}
		Contract contract = assetSet.getContract();
		if(contract==null){
			logger.info("contract is null");
			return false;
		}
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		Long companyId = financialContract.getCompany().getId();
		Customer borrower_customer = contract.getCustomer();
		
		long predata_end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("predata_end use time["+(predata_end-start)+"]ms");
		}
		
		BigDecimal booking_amount =  sourceDocumentDetail.getAmount();
		BigDecimal receivalbeAmount = ledgerBookStatHandler.get_receivable_amount(financialContract.getLedgerBookNo(), assetSet.getAssetUuid(), borrower_customer.getCustomerUuid());
		
		Customer company_customer = customerService.getCustomer(financialContract.getApp(), CustomerType.COMPANY);
		if(company_customer==null){
			logger.info("company_customer is null");
			return false;
		}
		
		long get_amount_from_ledger_end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("get_amount_from_ledger_end use time["+(get_amount_from_ledger_end-predata_end)+"]ms");
		}
		if(receivalbeAmount.compareTo(BigDecimal.ZERO)<=0 ){
			logger.info("receivalbeAmount is LE 0 ");
			return false;
		}
		
		Order order = createOrder(booking_amount,assetSet, contract, financialContract);;
		
		// create jv
		// create bv: 是否创建bv,创建后，金额不会变，会有影响。
		String repaymentUuid = assetSet.getAssetUuid();
		BusinessVoucher businessVoucher = businessVoucherHandler.issueAppCompensatoryBusinessVoucher(repaymentUuid, companyId, booking_amount, assetSet.getAssetFairValue());
		//issue jv
		JournalVoucher journalVoucher = new JournalVoucher();
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		journalVoucher.createFromComposentoryVoucher(sourceDocumentDetail, sourceDocumentNo, AccountSide.DEBIT,financialContract.getCompany(), contractAccount, companyVirtualAccount);
		journalVoucher.fill_voucher_and_booking_amount(assetSet.getAssetUuid(), businessVoucher.getBusinessVoucherTypeUuid(),
				businessVoucher.getBusinessVoucherUuid(), assetSet.getAssetFairValue(), JournalVoucherStatus.VOUCHER_ISSUED, 
				JournalVoucherCheckingLevel.AUTO_BOOKING, JournalVoucherType.TRANSFER_BILL_BY_VOUCHER);

		journalVoucher.fillBillContractInfo(financialContract.getFinancialContractUuid(), contract.getUuid(),assetSet.getAssetUuid(), financialContract.getContractName(), contract.getContractNo(), assetSet.getSingleLoanContractNo(), order.getOrderNo());
		//用此sourcedocumentUuid的话，rollback时，就会把detail的其他金额给回滚。因此需单独创建sourceDocument。
		
		long createJv_end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("createJv_end use time["+(createJv_end-get_amount_from_ledger_end)+"]ms");
		}
		
		ledgerBookVirtualAccountHandler.remittance_from_virutal_account_to_receivable_overdue_assets(book, assetSet, booking_amount, journalVoucher.getJournalVoucherUuid(), businessVoucher.getBusinessVoucherUuid(), sourceDocumentDetail.getSourceDocumentUuid(), false);
		
		long remittance_virutal_account_end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("remittance_virutal_account_end use time["+(remittance_virutal_account_end-createJv_end)+"]ms");
		}
		journalVoucherService.save(journalVoucher);
		
		//update assets in ledgerBookVirtualAccountHandler;
		updateAssetStatusLedgerBookHandler.updateAssetsFromLedgerBook(financialContract.getLedgerBookNo(), Arrays.asList(assetSet.getAssetUuid()), false);
		
		long update_asset_end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("update_asset_end use time["+(update_asset_end-remittance_virutal_account_end)+"]ms");
		}
		sourceDocumentDetail.setStatus(SourceDocumentDetailStatus.SUCCESS);
		sourceDocumentDetailService.saveOrUpdate(sourceDocumentDetail);
		
		return true;
	}
	
	@Override
	public boolean sourceDocumentDetailValidatorSingle(Long detailId, String financialContractNo,String ledgerBookNo){
		SourceDocumentDetail detail = sourceDocumentDetailService.getSourceDocumentDetailBy(detailId);
		if(detail==null){
			return false;
		}
		if(detail.isUncheck()) {
			boolean is_all_check_pass = true;
			String error_msg = "";
			try {
				//logger.info("开始检查明细内容的真实性dfdffgffgfgdfgxcgd---> SourceDocumentDetailId : " + detailId);
				String contractUniqueId = detail.getContractUniqueId();
				logger.info("contractUniqueId "+contractUniqueId);
				String repaymentPlanNo = detail.getRepaymentPlanNo();
				logger.info("repaymentPlanNo "+repaymentPlanNo);
				int payer = detail.getPayer().ordinal();
				String key_of_voucherType = detail.getSecondType();
				BigDecimal amount = detail.getAmount();
				
				Contract contract = checkContract(contractUniqueId);//校验贷款合同zontract
				logger.info("Contract uuid" + contract.getUuid()+"ID　" +contract.getId());
				Customer customer=contract.getCustomer();
				logger.info("customer" + customer);
				logger.info("customer　id" + customer.getId());
					
				String customerUuid = contract.getCustomer().getCustomerUuid();
				checkFinancialContract(financialContractNo, contract);//校验信托计划FinancialContract
				AssetSet repaymentPlan = checkRepaymentPlan(contractUniqueId, repaymentPlanNo);//校验还款计划AssetSet
				String assetSetUuid = repaymentPlan.getAssetUuid();
				checkVoucherPayer(payer);//校验付款人
				checkVoucherAmount(key_of_voucherType, ledgerBookNo, assetSetUuid, customerUuid, amount);//校验明细金额
			} catch (GlobalRuntimeException e) {
				is_all_check_pass = false;
				error_msg = e.getMsg();
			}
			
			if(is_all_check_pass) {
				detail.setCheckState(SourceDocumentDetailCheckState.CHECK_SUCCESS);
				//logger.info("检查明细内容的真实性成功-----> SourceDocumentDetailId : " + detailId);
			}else {
				detail.setCheckState(SourceDocumentDetailCheckState.CHECK_FAILS);
				detail.setComment(error_msg);
				//logger.error("检查明细内容的真实性失败-----> SourceDocumentDetailId : " + detailId + ", 失败原因：" + error_msg);
			}
			sourceDocumentDetailService.saveOrUpdate(detail);
		}
		if(detail.getCheckState() == SourceDocumentDetailCheckState.CHECK_FAILS) {
			return false;
		}
		return true;
	}
	
	private Contract checkContract(String contractUniqueId) {
		Contract contract = contractService.getContractByUniqueId(contractUniqueId);
		if(contract == null) {
			logger.error("检查明细内容的真实性失败，贷款合同不存在");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_CONTRACT);
		}
		return contract;
	}
	
	private void checkFinancialContract(String financialContractNo, Contract contract) {
		FinancialContract financialContract = assetPackageService.getFinancialContract(contract); 
		if(financialContract == null || !StringUtils.equals(financialContractNo, financialContract.getContractNo())) {
			logger.error("检查明细内容的真实性失败，贷款合同找不到对应的信托计划");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_FINANCIAL_CONTRACT);
		}
	}
	
	private AssetSet checkRepaymentPlan(String contractUniqueId, String repaymentPlanNo) {
		AssetSet repaymentPlan = repaymentPlanService.getRepaymentPlanByRepaymentPlanNo(repaymentPlanNo);
		if(repaymentPlan == null || !StringUtils.equals(contractUniqueId, repaymentPlan.getContract().getUniqueId())) {
			logger.error("检查明细内容的真实性失败，还款计划不存在");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_REPAYMENT_PLAN_NO);
		}
		return repaymentPlan;
	}

	/**
	 * 校验还款人
	 * @param payer
	 */
	private void checkVoucherPayer(Integer payer) {
		if(payer != null && VoucherPayer.fromValue(payer) == null) {
			logger.info("检查明细内容的真实性失败，还款人填写错误");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_PAYER);
		}
	}

	/**
	 * 检查明细金额
	 * @param key_of_voucherType 凭证类型key
	 * @param ledgerBookNo 
	 * @param assetSetUuid
	 * @param customerUuid
	 * @param amount
	 */
	private void checkVoucherAmount(String key_of_voucherType, String ledgerBookNo, String assetSetUuid, String customerUuid, BigDecimal amount) {
		VoucherType voucherType = VoucherType.fromKey(key_of_voucherType);
		switch (voucherType) {
		case PAY:
			check_voucher_amount_of_type_pay(ledgerBookNo, assetSetUuid, customerUuid, amount);
			break;
		case GUARANTEE:
			check_voucher_amount_of_gurantee(ledgerBookNo, assetSetUuid, amount);
			break;
		case REPURCHASE:
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_VOUCHER_TYPE);// TODO 回购
		case MERCHANT_REFUND:
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_VOUCHER_TYPE);// TODO 差额划拨
		default:
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_VOUCHER_TYPE);
		}
	}

	private void check_voucher_amount_of_gurantee(String ledgerBookNo, String assetSetUuid, BigDecimal amount) {
		BigDecimal gurantee_amount = ledgerBookStatHandler.get_gurantee_amount(ledgerBookNo, assetSetUuid);
		if(amount.compareTo(gurantee_amount) == 1) {
			logger.error("检查担保补足明细金额失败，明细金额大于还款计划担保金额");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_GURANTEE_AMOUNT);
		}
	}
	
	private void check_voucher_amount_of_type_pay(String ledgerBookNo, String assetSetUuid, String customerUuid, BigDecimal amount) {
		BigDecimal receivable_amount = ledgerBookStatHandler.get_receivable_amount(ledgerBookNo, assetSetUuid, customerUuid);
		if(amount.compareTo(receivable_amount) == 1) {
			logger.error("检查代偿明细金额失败，明细金额大于还款计划应还金额");
			throw new GlobalRuntimeException(VoucherErrorMsgSpec.VOUCHER_ERROR_OF_RECEIVABLE_AMOUNT);
		}
	}

	@Override
	public boolean sourceDocumentDetailValidatorList(List<VoucherParameter> voucherParamters) {
		
		long startTime = System.currentTimeMillis();
		
		logger.info("begin to sourceDocumentDetailValidatorList at" +startTime );
		
		boolean is_all_succ = true;
		
		for (VoucherParameter voucherParameter : voucherParamters) {
			
			boolean singleResult = this.sourceDocumentDetailValidatorSingle(voucherParameter.getSourceDocumentDetailId(), voucherParameter.getFinancialContractNo(), voucherParameter.getLedgerBookNo());
			
			if(singleResult==false){
				
				is_all_succ = false;
			}
		}
		logger.info("end to sourceDocumentDetailValidatorList,consume time["+(System.currentTimeMillis()-startTime)+"]");
		
		return is_all_succ;
	}

}
