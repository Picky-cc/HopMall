package com.zufangbao.earth.yunxin.handler.reportform.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.handler.FinancialContractHandler;
import com.zufangbao.earth.yunxin.handler.reportform.LoansReportFormHandler;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansQueryModel;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansShowModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("loansReportFormHandler")
public class LoansReportFormHandlerImpl implements LoansReportFormHandler{

	@Autowired
	private FinancialContractHandler financialContractHandler;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Override
	public List<LoansShowModel> query(LoansQueryModel queryModel) {
		List<FinancialContract> financialContractList = financialContractHandler.getFinancialContractList(queryModel.getFinancialContractId());
		List<LoansShowModel> showModels = new ArrayList<LoansShowModel>();
		for (FinancialContract financialContract : financialContractList) {
			BigDecimal beginningLoans = calculateBeginningLoans(financialContract, queryModel.getStartDateString());
			BigDecimal newLoans = calculateNewLoansPrincipal(financialContract, queryModel.getStartDateString(), queryModel.getEndDate());
			BigDecimal reduceLoans = calculateReduceLoansPrincipal(financialContract, queryModel.getStartDateString(), queryModel.getEndDate());
			BigDecimal endingLoans = beginningLoans.add(newLoans).subtract(reduceLoans);
			
			LoansShowModel loansShowModel = new LoansShowModel(financialContract, beginningLoans, newLoans, reduceLoans, endingLoans);
			showModels.add(loansShowModel);
		}
		return showModels;
	}

	/**
	 * 贷款规模管理-计算 本期减少本金总额
	 * @param financialContract
	 * @param startDateString
	 * @param endDateString
	 * @return
	 */
	private BigDecimal calculateReduceLoansPrincipal(FinancialContract financialContract, String startDateString, Date endDate) {
		Date startDate = DateUtils.asDay(startDateString);
		return repaymentPlanService.calculateReduceLoansPrincipal(financialContract, startDate, endDate);
	}

	/**
	 * 贷款规模管理-计算 本期新增本金总额 
	 * @param financialContract
	 * @param startDateString
	 * @param endDateString
	 * @return
	 */
	private BigDecimal calculateNewLoansPrincipal(FinancialContract financialContract, String startDateString, Date endDate) {
		Date startDate = DateUtils.asDay(startDateString);
		return contractService.calculateNewLoansPrincipal(financialContract, startDate, endDate);
	}

	private void validateStartDate(String startDateString) {
		if(StringUtils.isEmpty(startDateString) || DateUtils.asDay(startDateString) == null) {
			throw new RuntimeException("查询起始日期格式错误！");
		}
	}

	/**
	 * 贷款规模管理-计算 期初 贷款总本金余额
	 * @param financialContract
	 * @param startDateString
	 * @return
	 */
	private BigDecimal calculateBeginningLoans(FinancialContract financialContract, String startDateString) {
		validateStartDate(startDateString);
		Date startDate = DateUtils.asDay(startDateString);
		BigDecimal beginningPrincipal = contractService.calculateBeginningPrincipal(financialContract, startDate);
		BigDecimal beginningPaid = repaymentPlanService.calculateBeginningPaid(financialContract, startDate);
		return beginningPrincipal.subtract(beginningPaid);
	}

}
