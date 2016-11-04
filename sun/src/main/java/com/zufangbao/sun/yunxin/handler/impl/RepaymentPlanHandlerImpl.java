package com.zufangbao.sun.yunxin.handler.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.HttpUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.excel.OverDueRepaymentDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.RepaymentPlanDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetShowModel;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@Component("RepaymentPlanHandler")
public class RepaymentPlanHandlerImpl implements RepaymentPlanHandler{

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	
	@Autowired
	private DeductApplicationService deductApplicationService;

	private static final Log logger = LogFactory.getLog(RepaymentPlanHandlerImpl.class);
	
	@Override
	public List<AssetSet> getEffectiveRepaymentPlansByContract(Contract contract) {
		return repaymentPlanService.getRepaymentPlansByContractAndActiveStatus(contract, AssetSetActiveStatus.OPEN);
	}
	
	@Override
	public List<AssetSet> getPrepaymentWaitForProcessingRepaymentPlansByContract(
			Contract contract) {
		return repaymentPlanService.getRepaymentPlansByContractAndActiveStatus(contract, AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
	}
	
	public final static String REQUEST_FREQUENT_ERROR = "单个贷款合同变更还款计划请求过于频繁，请降低频率后重试";
	
	@Override
	public int updateContractActiveVersionNo(Contract contract, Integer oldActiveVersionNo) {
		if(contract==null){
			return Integer.MIN_VALUE;
		}
		Integer newVersionNo = java.util.UUID.randomUUID().hashCode();
		contractService.updateContractActiveVersionNo(contract, oldActiveVersionNo, newVersionNo);
		Integer updatedVersionNo = contractService.getActiveVersionNo(contract.getUuid());
		logger.info("oldActiveVersionNo:" + oldActiveVersionNo + "newVersionNo:" + newVersionNo + ",updateVersion:" + updatedVersionNo);
		if (newVersionNo.equals(updatedVersionNo)==false) {
			logger.info("change version failed");
			throw new RuntimeException(REQUEST_FREQUENT_ERROR);
		}
		logger.info("change version succ");
		return updatedVersionNo;
	}
	
	public void createOrderByAssetSet(AssetSet assetSet, Date executeDate) {
		Contract contract = assetSet.getContract();
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		//非系统创建结算单跳过
		if(!financialContract.isSysCreateStatementFlag()) {
			return;
		}
		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService
				.getAssetValuationDetailListByAsset(assetSet, executeDate);
		String assetValuationDetailIds = assetValuationDetailHandler
				.extractIdsJsonFrom(assetValuationDetails);
		BigDecimal totalAmount = assetValuationDetailHandler
				.get_asset_fair_value(assetValuationDetails);
		Order order = new Order(totalAmount, assetValuationDetailIds, assetSet,
				contract.getCustomer(), financialContract, executeDate);
		orderService.save(order);
	}

	@Override
	public List<Long> get_all_allow_valuation_and_create_normal_order_asset_set_list(
			Date date) {
		// 获取应收，未清，当日无结算单还款计划
		List<Long> receivable_unclear_asset_set_ids = repaymentPlanService.get_all_receivable_unclear_asset_set_list(date);
		
		List<Long> assetSetIds = new ArrayList<Long>();
		for (Long assetSetId : receivable_unclear_asset_set_ids) {
			// 尚未创建当日结算单
			if(orderService.exist_today_normal_order(assetSetId, date)) {
				continue;
			}
					
			// 过滤存在未处理完的结算单的还款计划
			if(orderService.exist_not_completed_normal_order(assetSetId)) {
				continue;
			}
			assetSetIds.add(assetSetId);
		}

		return assetSetIds;
	}

	@Override
	public List<Serializable> get_all_needs_remind_assetSet_id_list(
			int remindDay) {
		List<AssetSet> assetSetList = repaymentPlanService
				.get_all_needs_remind_assetSet_list(remindDay);
		return getIdsBy(assetSetList);
	}

	private List<Serializable> getIdsBy(List<AssetSet> assetSetList) {
		List<Serializable> ids = new ArrayList<>();
		for (AssetSet assetSet : assetSetList) {
			ids.add(assetSet.getId());
		}
		return ids;
	}

	@Override
	public List<RepaymentPlanDetailExcelVO> get_repayment_plan_Detail_excel(
			AssetSetQueryModel queryModel) {
		List<AssetSet> assetSets = queryAssetSetIds(queryModel, null);
		
		List<RepaymentPlanDetailExcelVO> repaymentPlanDetailExcelList = new ArrayList<RepaymentPlanDetailExcelVO>();
		for (AssetSet assetSet : assetSets) {
			Contract contract = assetSet.getContract();
			String financialContractUuid = contract.getFinancialContractUuid();
			FinancialContract financialContract = queryModel.getFinancialContractByUuid(financialContractUuid);
			
			ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
			RepaymentPlanDetailExcelVO repaymentPlanDetailExcel = new RepaymentPlanDetailExcelVO(contract, contractAccount, financialContract, assetSet);
			repaymentPlanDetailExcelList.add(repaymentPlanDetailExcel);
		}
		return repaymentPlanDetailExcelList;
	}

	@Override
	public List<OverDueRepaymentDetailExcelVO> get_overdue_than_over_due_start_day_repayment_detail_excel(
			AssetSetQueryModel queryModel) {
		List<AssetSet> assetSets = queryAssetSetIds(queryModel, null);
		if(CollectionUtils.isEmpty(assetSets)) {
			return Collections.emptyList();
		}
		
		List<OverDueRepaymentDetailExcelVO> excelVOs = new ArrayList<OverDueRepaymentDetailExcelVO>();
		for (AssetSet assetSet : assetSets) {
			String financialContractUuid = assetSet.getContract().getFinancialContractUuid();
			int overDueStartDay = queryModel.getFinancialContractByUuid(financialContractUuid).getLoanOverdueStartDay();
			
			int intervelDays = assetSet.getOverDueDays();
			if(intervelDays >= overDueStartDay) {
				OverDueRepaymentDetailExcelVO excelVO = new OverDueRepaymentDetailExcelVO(assetSet);
				excelVOs.add(excelVO);
			}
		}
		return excelVOs;
	}
	
	public void updateOverdueStatusAndSaveLog(Long assetId, Long userId, AuditOverdueStatus status, String reason, String overdueDate, String ip) {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
		String recordContent = getRecordContent(assetSet, status, overdueDate, reason);
		assetSet.setOverdueStatus(status);
		if(!StringUtils.isEmpty(overdueDate)) {
			assetSet.setOverdueDate(DateUtils.asDay(overdueDate));
		}
		repaymentPlanService.save(assetSet);
		SystemOperateLog log = SystemOperateLog.createAssetSetLog(userId, assetSet, recordContent, ip, LogFunctionType.ASSET_SET_OVERDUE, LogOperateType.UPDATE);
		systemOperateLogService.save(log);
	}

	private String getRecordContent(AssetSet assetSet, AuditOverdueStatus status, String overdueDate, String reason) {
		String recordContent = "逾期状态由【" + assetSet.getOverdueStatus().getChineseMessage() + "】变更为【" + status.getChineseMessage() + "】";
		if(!StringUtils.isEmpty(overdueDate)) {
			recordContent += "，确认逾期起始日：" + overdueDate;
		}
		recordContent += ", 修改原因：" + reason;
		return recordContent;
	}

	@Override
	public void updateCommentAndSaveLog(Long assetId, Long userId, String comment, String ip) {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
		String old_comment = assetSet.getComment() == null? "" : assetSet.getComment();
		String recordContent = "还款计划备注由【" + old_comment + "】变更为【" + comment + "】";
		assetSet.setComment(comment);
		repaymentPlanService.save(assetSet);
		SystemOperateLog log = SystemOperateLog.createAssetSetLog(userId, assetSet, recordContent, ip, LogFunctionType.ASSET_SET_COMMENT, LogOperateType.UPDATE);
		systemOperateLogService.save(log);
	}

	@Override
	public List<AssetSet> queryAssetSetIds(AssetSetQueryModel queryModel, Page page) {
		return repaymentPlanService.queryAssetSetIdsByQueryModel(queryModel, page);
	}
	
	@Override
	public int countAssetSetIds(AssetSetQueryModel queryModel) {
		return repaymentPlanService.countAssetSetByQueryModel(queryModel);
	}
	
	@Override
	public List<AssetSetShowModel> generateAssetSetModelList(AssetSetQueryModel queryModel, Page page) {
		List<AssetSet> assetSets = queryAssetSetIds(queryModel, page);
		if(CollectionUtils.isEmpty(assetSets)) {
			return Collections.emptyList();
		}
		
		return assetSets.stream().map(assetSet -> new AssetSetShowModel(assetSet, queryModel)).collect(Collectors.toList());
	}

	@Override
	public List<String> getPrepaymentFailRepaymentPlanUuids() {
		List<AssetSet> repaymentPlanList = repaymentPlanService.getExpiredUnclearPrepaymentRepaymentPlanList();
		List<String> repaymentPlanUuids = new ArrayList<String>();
		for (AssetSet repaymentPlan : repaymentPlanList) {
			if(orderService.existsSuccessOrDoingSettlementStatement(repaymentPlan)) {
				continue;
			}
			repaymentPlanUuids.add(repaymentPlan.getAssetUuid());
		}
		return repaymentPlanUuids;
	}

	@Value("#{config['bridgewaterHost']}")
	private String BRIDGEWATER_HOST = "";
	/** 请求BridgeWater检查是否有扣款请求 **/
	private String CHECK_DEDUCT_URL = "/api/command/containProcessingRepaymentplan";

	@Override
	public boolean is_exsit_processing_or_success_RepaymentPlan(List<AssetSet> assetList) throws IOException {
		try {
			if(CollectionUtils.isEmpty(assetList)) {
				return false;
			}
			Map<String, String> params = createParams(assetList);
			String resultString = HttpUtils.post(BRIDGEWATER_HOST + CHECK_DEDUCT_URL, params, "utf-8");
			logger.info("BridgeWater返回数据：" + resultString);
			Result result = JsonUtils.parse(resultString, Result.class);
			return result.isValid();
		} catch (IOException e) {
			throw e;
		} catch (NullPointerException e) {
			logger.info("BridgeWater返回数据解析Json失败");
			throw e;
		}
	}

	private Map<String, String> createParams(List<AssetSet> assetList) {
		List<String> assetUuidList = assetList.stream().map(asset -> asset.getAssetUuid()).collect(Collectors.toList());
		String assetUuidsJson = JsonUtils.toJsonString(assetUuidList);
		Map<String, String> params = new HashMap<String, String>();
		params.put("assetUuidsJson", assetUuidsJson);
		return params;
	}
	
}
