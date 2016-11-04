package com.zufangbao.sun.yunxin.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("assetValuationDetailHandler")
public class AssetValuationDetailHandlerImpl implements AssetValuationDetailHandler {
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	private static Log logger = LogFactory.getLog(AssetValuationDetailHandlerImpl.class);
	
	/**
	 * 评估单个资产
	 * 
	 * @param assetSet
	 * @param current_valuation_date 当前评估日期
	 * @throws AlreadayCarryOverException 
	 * @throws InvalidLedgerException 
	 */
	public void assetValuation(AssetSet assetSet, Date current_valuation_date) throws AlreadayCarryOverException, InvalidLedgerException {
		LedgerBook ledgerBook =null;
		LedgerTradeParty guaranteeLedgerTradeParty =null;
		FinancialContract financialContract = null;
		try {
			String financialContractUuid = assetSet.getContract().getFinancialContractUuid();
			financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			String LedgerBookNo = financialContract.getLedgerBookNo();
			ledgerBook = ledgerBookService.getBookByBookNo(LedgerBookNo);
			guaranteeLedgerTradeParty = ledgerItemService.getGuranteLedgerTradeParty(assetSet);
		} catch (Exception e){
			logger.error("Ledger book(amortize_loan_asset_to_receivable) prepare data occur error.");
			e.printStackTrace();
			
		}
		
		int loanOverdueStartDay = financialContract.getLoanOverdueStartDay();
		Date history_valuation_date = assetSet.getAssetRecycleDate();
		//FIXME:性能优化
		while (!history_valuation_date.after(current_valuation_date)) {
			create_and_save_missing_valuation_detail(assetSet, history_valuation_date, ledgerBook,
					guaranteeLedgerTradeParty, loanOverdueStartDay, financialContract);
			history_valuation_date = DateUtils.addDays(history_valuation_date, 1);
		}
		update_asset_set_after_valuation(assetSet, current_valuation_date);
		auto_update_assetSet_overdue_status(assetSet, loanOverdueStartDay);
	}

	/**
	 * 自动更新 超出免息期的还款计划的逾期状态 为 待确认
	 * @param assetSet
	 * @param loanOverdueStartDay
	 */
	//TODO 增加自动测试
	private void auto_update_assetSet_overdue_status(AssetSet assetSet, int loanOverdueStartDay) {
		// 已付清的，已逾期的还款计划不能自动修改逾期状态
		if(assetSet.isClearAssetSet() || assetSet.getOverdueStatus() == AuditOverdueStatus.OVERDUE) {
			return;
		}
		Date valuationDate = new Date();
		Date assetRecycleDate = assetSet.getAssetRecycleDate();
		boolean is_in_Interest_free_period = assetSet.is_in_Interest_free_period(valuationDate, assetRecycleDate, loanOverdueStartDay);
		if(!is_in_Interest_free_period) {
			assetSet.setOverdueStatus(AuditOverdueStatus.UNCONFIRMED);
			assetSet.setLastModifiedTime(new Date());
			repaymentPlanService.update(assetSet);
		};
	}

	private void create_and_save_missing_valuation_detail(AssetSet assetSet, Date history_valuation_date,
			LedgerBook ledgerBook,
			LedgerTradeParty guaranteeLedgerTradeParty, int loanOverdueStartDay, FinancialContract financialContract) {
		if (assetSet.isAssetRecycleDate(history_valuation_date)) {
			AssetValuationDetail createdDetail= create_and_save_assetset_valuation_detail(assetSet, history_valuation_date, AssetValuationSubject.REPAYMENT_AMOUNT, loanOverdueStartDay);
			if(createdDetail!=null){
				amortize_loan_asset_to_receivable(assetSet, ledgerBook);
			}
			return;
		}
		if (assetSet.isOverdueDate(history_valuation_date)) {
			if(financialContract != null && financialContract.isSysCreatePenaltyFlag()) {
				AssetValuationDetail createdDetail=create_and_save_assetset_valuation_detail(assetSet, history_valuation_date, AssetValuationSubject.PENALTY_INTEREST, loanOverdueStartDay);
				
				if(createdDetail!=null && !assetSet.is_in_Interest_free_period(history_valuation_date, assetSet.getAssetRecycleDate(), loanOverdueStartDay)){
					classify_receivable_loan_asset_to_overdue(assetSet,
							ledgerBook, guaranteeLedgerTradeParty,
							history_valuation_date, createdDetail);
				}
			}
			return;
		}
	}

	private void classify_receivable_loan_asset_to_overdue(AssetSet assetSet,
			LedgerBook ledgerBook, LedgerTradeParty guaranteeLedgerTradeParty,
			Date history_valuation_date, AssetValuationDetail createdDetail) {
		try{
			AssetCategory assetCategory = AssetConvertor.convertAnMeiTupPenaltyAssetToAssetCategory(assetSet, createdDetail);
			if(!ledgerBookHandler.is_zero_balanced_account(ledgerBook, ChartOfAccount.SND_RECIEVABLE_LOAN_ASSET, assetCategory))
				ledgerBookHandler.classify_receivable_loan_asset_to_overdue(history_valuation_date, ledgerBook, assetSet);
			if(!ledgerBookHandler.is_zero_balanced_account(ledgerBook, ChartOfAccount.SND_RECIEVABLE_OVERDUE_LOAN_ASSET, assetCategory))
				ledgerBookHandler.claim_penalty_on_overdue_loan(ledgerBook, assetSet, createdDetail);
		
		}catch(Exception e){
			logger.error("Ledger book(classify_receivable_loan_asset_to_overdue and add penalty) error.");
			e.printStackTrace();
			throw e;
		}
	}

	private void amortize_loan_asset_to_receivable(AssetSet assetSet,
			LedgerBook ledgerBook) {
		try{
			ledgerBookHandler.amortize_loan_asset_to_receivable(ledgerBook, assetSet);
		} catch(Exception e){
			logger.error("Ledger book(amortize_loan_asset_to_receivable) error.");
			e.printStackTrace();
			throw e;
		}
	}

	private AssetValuationDetail create_and_save_assetset_valuation_detail(AssetSet assetSet, Date valuationDate,
			AssetValuationSubject assetValuationSubject, int loanOverdueStartDay) {
		
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet, assetValuationSubject, valuationDate);
		if (assetValuationDetail != null) {
			return null;
		}
		BigDecimal amount = getAmountBySubject(assetValuationSubject, valuationDate, loanOverdueStartDay,assetSet);
		return assetValuationDetailService.insert_asset_valuation_detail(assetSet, valuationDate, amount, assetValuationSubject);
	}

	/**
	 * 更新资产公允值以及最后修改时间
	 * 
	 * @param assetSet
	 * @param valuation_date_time 
	 */
	private void update_asset_set_after_valuation(AssetSet assetSet, Date valuation_date_time) {
		List<AssetValuationDetail> assetValuationDetailList = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet, valuation_date_time);
		if (assetValuationDetailList.isEmpty()) {
			return;
		}
		BigDecimal assetFairValue = get_asset_fair_value(assetValuationDetailList);
		assetSet.update_asset_fair_value_and_valution_time(assetFairValue);
		repaymentPlanService.update(assetSet);
	}

	public BigDecimal get_asset_fair_value(List<AssetValuationDetail> assetValuationDetailList) {
		BigDecimal assetFairValue = BigDecimal.ZERO;
		for (AssetValuationDetail assetValuationDetail : assetValuationDetailList) {
			assetFairValue = assetFairValue.add(assetValuationDetail.getAmount());
		}
		return assetFairValue;
	}

	@Override
	public List<Long> extractIdsFrom(List<AssetValuationDetail> assetValuationDetailList) {
		if(CollectionUtils.isEmpty(assetValuationDetailList)){
			return Collections.emptyList();
		}
		List<Long> ids = new ArrayList<Long>();
		for(AssetValuationDetail assetValuationDetail: assetValuationDetailList){
			if(assetValuationDetail==null){
				continue;
			}
			ids.add(assetValuationDetail.getId());
		}
		return ids;
	}

	@Override
	public String extractIdsJsonFrom(List<AssetValuationDetail> assetValuationDetailList) {
		List<Long> ids = extractIdsFrom(assetValuationDetailList);
		return JsonUtils.toJsonString(ids);
	}

	@Override
	public Order add_asset_valuation_and_modify_amount_of_asset_order(Order order,BigDecimal deltaAmount, Date valuationTime, String comment){
		if(order==null){
			return null;
		}
		AssetSet assetSet = order.getAssetSet();
		AssetValuationDetail assetValuationDetail = assetValuationDetailService.insert_asset_valuation_detail(assetSet, valuationTime,deltaAmount,AssetValuationSubject.AMOUNT_ADJUSTMENT);
		update_asset_set_after_valuation(assetSet, valuationTime);
		
		update_asset_in_ledger_book(order.getFinancialContract(),assetSet,assetValuationDetail);
		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet, valuationTime);
		String assetValuationDetailIds = extractIdsJsonFrom(assetValuationDetails);
		BigDecimal totalAmount = get_asset_fair_value(assetValuationDetails);
		order.setTotalRent(totalAmount);
		order.setUserUploadParam(assetValuationDetailIds);
		order.setComment(comment);
		orderService.save(order);
		return order;
		
	}

	private void update_asset_in_ledger_book(FinancialContract financialContract,AssetSet assetSet,AssetValuationDetail assetValuationDetail) {
		if(financialContract==null ||assetSet==null ||assetValuationDetail==null){
			return;
		}
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		if(book==null){
			return;
		}
		try {
			ledgerBookHandler.claim_penalty_on_overdue_loan(book, assetSet, assetValuationDetail);
		} catch (InvalidLedgerException e) {
			logger.error("claim penalty occur error when penalty is to be modifed. assetUuid["+assetSet.getAssetUuid()+"],deltaAmount["+assetValuationDetail.getAmount()+"].");
			e.printStackTrace();
		}
		
	}

	@Override
	public BigDecimal getAmountBySubject(AssetValuationSubject assetValuationSubject, Date valuationDate,int loanOverdueStartDay, AssetSet assetSet) {
		switch(assetValuationSubject){
		case REPAYMENT_AMOUNT:
			return repaymentPlanService.get_principal_interest_and_extra_amount(assetSet);
			
		case PENALTY_INTEREST:
			return assetSet.calc_daily_penalty_interest_amount(valuationDate, loanOverdueStartDay);
	
		case AMOUNT_ADJUSTMENT:
		default:
			return BigDecimal.ZERO;
		
		}
	}
}
