package com.zufangbao.earth.yunxin.handler.modifyOverDueFee.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeDetail;
import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeRequestModel;
import com.zufangbao.earth.yunxin.handler.modifyOverDueFee.ModifyOverDueFeeHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.api.ModifyOverdueFeeLog;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanExtraChargeHandler;
import com.zufangbao.sun.yunxin.service.ModifyOverDueFeeLogService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("modifyOverDueFeeHandler")
public class ModifyOverDueFeeHandlerImpl implements ModifyOverDueFeeHandler {

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ModifyOverDueFeeLogService modifyOverDueFeeLogService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private  FinancialContractService financialContractService;
	
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	@Autowired
	private RepaymentPlanExtraChargeHandler repaymentPlanExtraChargeHandler;
	
	@Override
	public void modifyOverDueFeeAndCheckData(ModifyOverDueFeeRequestModel model, HttpServletRequest request){
		
		List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails = model.getModifyOverDueFeeDetailsParseJson();
		
		checkRepeateReuqestNo(model, request);
		//校验还款计划是否在合同中
		checkRepaymentInContract(modifyOverDueFeeDetails);
		//校验逾期费用计算日晚于逾期罚息计算日
		checkOverDueFeeCalcDateAfterOverDueDate(modifyOverDueFeeDetails);
		for(ModifyOverDueFeeDetail modifyOverDueFeeDetail: modifyOverDueFeeDetails) {
			Map<String,BigDecimal> account_delta_amount_map = new HashMap<String,BigDecimal>();
			AssetSet repaymentPlan = repaymentPlanService.getRepaymentPlanByRepaymentCode( modifyOverDueFeeDetail.getRepaymentPlanNo());
			BigDecimal penalty = repaymentPlanExtraChargeHandler.modifyFeeInAssetSetExtraCharge(repaymentPlan, new BigDecimal(modifyOverDueFeeDetail.getPenaltyFee()), ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY);
			BigDecimal oligation = repaymentPlanExtraChargeHandler.modifyFeeInAssetSetExtraCharge(repaymentPlan, new BigDecimal(modifyOverDueFeeDetail.getLatePenalty()), ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION);
			BigDecimal serviceFee = repaymentPlanExtraChargeHandler.modifyFeeInAssetSetExtraCharge(repaymentPlan, new BigDecimal(modifyOverDueFeeDetail.getLateFee()), ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE);
			BigDecimal otherFee = repaymentPlanExtraChargeHandler.modifyFeeInAssetSetExtraCharge(repaymentPlan, new BigDecimal(modifyOverDueFeeDetail.getLateOtherCost()), ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE);
			account_delta_amount_map.put(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY, penalty);
			account_delta_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION, oligation);
			account_delta_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE, serviceFee);
			account_delta_amount_map.put(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE, otherFee);
			modifyOverDueFeeInLedgerBook(repaymentPlan,account_delta_amount_map);
			updateAssetFairAmount(repaymentPlan);
		}
		
	}
	

	private void updateAssetFairAmount(AssetSet repaymentPlan) {
		BigDecimal assetSetFairAmount = repaymentPlanService.get_principal_interest_and_extra_amount(repaymentPlan);
		repaymentPlan.update_asset_fair_value_and_valution_time(assetSetFairAmount);
		repaymentPlanService.update(repaymentPlan);
	}
	
	private void checkRepeateReuqestNo(ModifyOverDueFeeRequestModel model, HttpServletRequest request) {
		
		String requestNo = model.getRequestNo();
		
		if(modifyOverDueFeeLogService.getLogByRequestNo(requestNo) != null){
			throw new ApiException(ApiResponseCode.REPEAT_REQUEST_NO);
		}
		ModifyOverdueFeeLog  modifyLog = new ModifyOverdueFeeLog(requestNo,model.getModifyOverDueFeeDetails(),IpUtil.getIpAddress(request));
		modifyOverDueFeeLogService.save(modifyLog);
		
	}

	private void modifyOverDueFeeInLedgerBook(AssetSet repaymentPlan,
			Map<String, BigDecimal> account_delta_amount_map){
		try {
			Contract contract = repaymentPlan.getContract();
			FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			LedgerBook book = ledgerBookService.getBookByAsset(repaymentPlan);
			AssetCategory assetCategory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(repaymentPlan);
			LedgerTradeParty ledgerTradeParty = new LedgerTradeParty(financialContract.getCompany().getUuid(),contract.getCustomer().getCustomerUuid());
			ledgerBookHandler.refresh_receivable_overDue_fee(book, assetCategory, ledgerTradeParty, account_delta_amount_map);
			if(!ledgerBookHandler.is_zero_balanced_account(book, ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, assetCategory)){
				ledgerBookHandler.classify_receivable_loan_asset_to_overdue(new Date(), book, repaymentPlan);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}






	private void checkOverDueFeeCalcDateAfterOverDueDate(
			List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails) {
		for(ModifyOverDueFeeDetail modifyOverDueFeeDetail: modifyOverDueFeeDetails) {
			
			Date overDueFeeCalcDate = DateUtils.asDay(modifyOverDueFeeDetail.getOverDueFeeCalcDate());
			
			AssetSet repaymentPlan = repaymentPlanService.getRepaymentPlanByRepaymentCode( modifyOverDueFeeDetail.getRepaymentPlanNo());
			Contract contract = repaymentPlan.getContract();
			FinancialContract financiaContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			
			if(!overDueFeeCalcDateAfterOverDueDate(overDueFeeCalcDate,
					repaymentPlan, financiaContract)){
				throw new ApiException(ApiResponseCode.OVER_DUE_FEE_CALC_DATE_AFTER_OVER_DUE_DATE);
			}
		}
	}



	private boolean overDueFeeCalcDateAfterOverDueDate(Date overDueFeeCalcDate,
			AssetSet repaymentPlan, FinancialContract financiaContract) {
		return DateUtils.compareTwoDatesOnDay(overDueFeeCalcDate, DateUtils.addDays(repaymentPlan.getAssetRecycleDate(), financiaContract.getLoanOverdueStartDay())) > 0;
	}
	
	
	
	private void checkRepaymentInContract(
			List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails) {
		for(ModifyOverDueFeeDetail modifyOverDueFeeDetail: modifyOverDueFeeDetails) {
			Contract modifyContract = contractService.getContractByUniqueId(modifyOverDueFeeDetail.getContractUniqueId());
			if ( modifyContract == null ){
				throw new ApiException(ApiResponseCode.CONTRACT_NOT_EXIST);
			} 
			AssetSet repaymentPlan  = repaymentPlanService.getRepaymentPlanByRepaymentCode( modifyOverDueFeeDetail.getRepaymentPlanNo());
			if(repaymentPlan == null || !repaymetPlanInTheModifyContract(modifyContract, repaymentPlan) ){
				throw new ApiException(ApiResponseCode.REPAYMENT_CODE_NOT_IN_CONTRACT);
			}
			if(repaymentPlan.getActiveStatus() != AssetSetActiveStatus.OPEN){
				throw new ApiException(ApiResponseCode.REPAYMENT_PLAN_NOT_OPEN);
			}
			if(repaymentPlan.isPaidOff()){
				 throw new ApiException(ApiResponseCode.REPAYMENT_PLAN_IS_PAID_OFF);
			}
		}
	}
	private boolean repaymetPlanInTheModifyContract(Contract modifyContract,
			AssetSet repaymentPlan) {
		return repaymentPlan.getContract().getUniqueId().equals(modifyContract.getUniqueId());
	}

}
