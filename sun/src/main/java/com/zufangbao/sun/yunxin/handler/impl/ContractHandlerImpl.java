package com.zufangbao.sun.yunxin.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.contract.RepaymentPlanOperateLog;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.ContractState;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.excel.LoanContractDetailCheckExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.ProjectInformationExeclVo;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@Component("contractHandler")
public class ContractHandlerImpl implements ContractHandler{
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinancialContractService  financialContractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	
	@Override
	public List<LoanContractDetailCheckExcelVO> getLoanContractExcelVO(ContractQueryModel queryModel){
		List<Contract> contractInfoList = contractService.queryContractListBy(queryModel, null);
		Map<String, FinancialContract> financialContractsMap = queryModel.getFinancialContractsMap();
		
		List<LoanContractDetailCheckExcelVO> LoanContractDetailCheckExcelVOs = new ArrayList<LoanContractDetailCheckExcelVO>();
		
		for(Contract contract : contractInfoList){
			if(contract.getState() == ContractState.INVALIDATE) {
				continue;
			}
			
			List<AssetSet> assetSets = repaymentPlanHandler.getEffectiveRepaymentPlansByContract(contract);
			
			BigDecimal totalInterest = BigDecimal.ZERO;
			BigDecimal restSumPrincipal = BigDecimal.ZERO;
			BigDecimal restSumInterest = BigDecimal.ZERO;
			Date maxAssetRecycleDate = null;
			if(CollectionUtils.isNotEmpty(assetSets)) {
				int lastPeriod = assetSets.size() - 1;
				maxAssetRecycleDate = assetSets.get(lastPeriod).getAssetRecycleDate();
				for(AssetSet assetSet :assetSets){
					totalInterest = totalInterest.add(assetSet.getAssetInterestValue());
					if(!assetSet.isPaidOff()){	
						restSumPrincipal = restSumPrincipal.add(assetSet.getAssetPrincipalValue());
						restSumInterest = restSumInterest.add(assetSet.getAssetInterestValue());
					}
				}
			}
			
			//优化从预先得到的Map取financialContract
			FinancialContract financialContract = financialContractsMap.get(contract.getFinancialContractUuid());
			
			ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
			LoanContractDetailCheckExcelVO loanContractDetailCheckExcelVO = new LoanContractDetailCheckExcelVO(financialContract, contract, maxAssetRecycleDate,totalInterest,restSumPrincipal,restSumInterest,contractAccount);
			LoanContractDetailCheckExcelVOs.add(loanContractDetailCheckExcelVO);
		}
		return LoanContractDetailCheckExcelVOs;
	}
	
	@Override
	public List<ProjectInformationExeclVo> castProjectInformationShowVOs(
			List<ProjectInformationSQLReturnData> queryContractInfoResults) {
		
		List<ProjectInformationExeclVo> showVOs = new ArrayList<ProjectInformationExeclVo>(); 
		
		for(ProjectInformationSQLReturnData contract_info:queryContractInfoResults){
			long start = System.currentTimeMillis();
			Contract contract = contractService.getContract(contract_info.getId());
			List<AssetSet> active_asset_sets = repaymentPlanService.getRepaymentPlansByContractAndActiveStatus(contract,  AssetSetActiveStatus.OPEN);
			String actualTermainalDate = getActualTermainalDate(active_asset_sets);
			String expectTerminalDate  = contract_info.getMaxAssetRecycleDate();
	
			Date today = DateUtils.parseDate(DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			
			AssetSet current_asset_set = select_current_asset_set(
					active_asset_sets, today);
	
			String current_repayment_date = null;
			if (current_asset_set != null) {
				current_repayment_date = DateUtils.format(
						current_asset_set.getAssetRecycleDate(), "yyyy-MM-dd");
			}
	       
	        String repaymentProgress = calc_repayment_progress(active_asset_sets,
					today, current_asset_set);
			
	        ProjectInformationExeclVo projectInformationShowVO = new ProjectInformationExeclVo(contract ,actualTermainalDate,repaymentProgress,current_repayment_date,current_asset_set,expectTerminalDate);
			showVOs.add(projectInformationShowVO);
			System.out.println("=============== " + (System.currentTimeMillis() - start));
		}
		return showVOs;
	}

	private String calc_repayment_progress(List<AssetSet> active_asset_sets,
			Date today, AssetSet current_asset_set) {
		int allPeriods = active_asset_sets.size();
        int executed_period_number = active_asset_sets.size();
        if (current_asset_set!=null){
    		executed_period_number = count_executed_period_number(today,
    				current_asset_set);
           }
        return executed_period_number+"/"+allPeriods;
	}

	private AssetSet select_current_asset_set(List<AssetSet> active_asset_sets,
			Date reference_date) {
		
	
		for(AssetSet assetSet :active_asset_sets){
			if(assetSet.isPaidOff()) continue;
			if(is_current_asset_set(reference_date, assetSet)){
				return assetSet;
			}
		}
		return null;
	}

	private int count_executed_period_number(Date today, AssetSet assetSet) {
		//TODO
		return assetSet.getAssetRecycleDate().equals(today)? assetSet.getCurrentPeriod():assetSet.getCurrentPeriod()-1;
	}

	private boolean is_current_asset_set(Date reference_date, AssetSet assetSet) {
		if(assetSet.getContract().getPeriods() == assetSet.getCurrentPeriod()){
			return true;
		}
		return reference_date.before( assetSet.getAssetRecycleDate()) || assetSet.getAssetRecycleDate().equals(reference_date);
	}

	private String getActualTermainalDate(List<AssetSet> assetSets) {
		Date actualTermainalDate = getLastAssetSetInList(assetSets).getActualRecycleDate();
		if(actualTermainalDate !=null){
			return DateUtils.format(actualTermainalDate, "yyyy-MM-dd");
		}
		return null;
	}


	private AssetSet getLastAssetSetInList(List<AssetSet> assetSets) {
		int  maxPeriod =assetSets.size();
		return assetSets.get(maxPeriod-1);
	}

	@Override
	public void addRepaymentPlanOperateLog(Contract contract,
			Integer triggerEvent, List<AssetSet> open, List<AssetSet> invalid,
			List<AssetSet> prepayment, String ipAddress, String operatorName) {
		//获取DB中的日志数组
		String logsStr = contract.getRepaymentPlanOperateLogs();
		List<RepaymentPlanOperateLog> logsInDB = JsonUtils.parseArray(logsStr, RepaymentPlanOperateLog.class);
		if(logsInDB == null) {
			logsInDB = new LinkedList<RepaymentPlanOperateLog>();
		}
		
		//插入新还款计划操作日志
		String occurTime = DateUtils.format(new Date(), DateUtils.LONG_DATE_FORMAT);
		RepaymentPlanOperateLog newLog = new RepaymentPlanOperateLog(triggerEvent, occurTime, open, invalid, prepayment, operatorName, ipAddress);
		logsInDB.add(newLog);
		
		//保存还款计划操作日志列表
		logsStr = JsonUtils.toJsonString(logsInDB);
		contract.setRepaymentPlanOperateLogs(logsStr);
		contractService.saveOrUpdate(contract);
	}

	@Override
	public void invalidateContract(Contract contract, long userId, String operatorName, String comment, String ipAddress) {
		List<AssetSet> repaymentPlans = repaymentPlanService.getRepaymentPlansByContractAndActiveStatus(contract, AssetSetActiveStatus.OPEN);
		long count = repaymentPlans.stream().filter(a -> new Date().after(a.getAssetRecycleDate()) || a.getAssetStatus() == AssetClearStatus.CLEAR).count();
		if (count > 0) {
			throw new RuntimeException("贷款合同已开始执行，无法中止!");
		}
		repaymentPlanService.invalidateAssets(repaymentPlans);
		addRepaymentPlanOperateLog(contract, RepaymentPlanOperateLog.INVALIDATE_CONTRACT, null, repaymentPlans, null, ipAddress, operatorName);
		contract.endContract();
		contractService.saveOrUpdate(contract);
		
		String recordContent = "中止贷款合同:" + contract.getContractNo() + ",中止原因:" + comment;
		SystemOperateLog log = SystemOperateLog.creatContractLog(userId, contract, recordContent , ipAddress, LogFunctionType.CONTRACT_INVALIDATE, LogOperateType.INVALIDATE);
		systemOperateLogService.save(log);
	}




	@Override
	public boolean isContainRepaymentPlanInTheContract(String repaymentPlanNo, Contract contract) {

		AssetSet assetSet = repaymentPlanService.getRepaymentPlanByRepaymentCode(repaymentPlanNo);
	    if(assetSet.getContract() == contract){
	    	return true;
	    }
	    return false;
	    
	}
	
}