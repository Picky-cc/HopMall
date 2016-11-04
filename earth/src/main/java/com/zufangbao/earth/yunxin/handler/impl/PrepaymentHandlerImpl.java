package com.zufangbao.earth.yunxin.handler.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.service.PrepaymentApplicationService;
import com.zufangbao.earth.yunxin.handler.PrepaymentHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.contract.RepaymentPlanOperateLog;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentApplication;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentStatus;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentType;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@Component("PrepaymentHandler")
public class PrepaymentHandlerImpl implements PrepaymentHandler {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	@Autowired
	private PrepaymentApplicationService prepaymentApplicationService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private ContractHandler contractHandler;
	@Autowired
	private ContractService contractService;
	
	private final static Log logger = LogFactory.getLog(PrepaymentHandlerImpl.class);
	
	@Override
	public List<Serializable> createPrepaymentTransferApplication() {
		List<Order> orders = orderService.getSettlementStatementInPrepaymentProcessing();
		return loopCreateTodayTransferApplicationByOrder(orders);
	}
	
	private List<Serializable> loopCreateTodayTransferApplicationByOrder(List<Order> orders) {
		if(CollectionUtils.isEmpty(orders)) {
			return Collections.emptyList();
		}
		
		List<Serializable> ids = new ArrayList<Serializable>();
		for (Order order : orders) {
			Serializable id = transferApplicationHandler.createTodayTransferApplicationByOrder(order);
			if(id != null) {
				ids.add(id);
			}
		}
		return ids;
	}

	@Override
	public void processingPrepaymentApplicationAfterSuccess(List<String> repaymentPlanUuids) {
		if(CollectionUtils.isEmpty(repaymentPlanUuids)) {
			return;
		}
		long start = System.currentTimeMillis();
		logger.info("#processingPrepaymentApplicationAfterSuccess start.");
		logger.info("#提前还款成功，开始同步（还款计划、提前还款申请）总计（"+ repaymentPlanUuids.size() +"）条 ! －uuids－" + repaymentPlanUuids);
		
		for (String repaymentPlanUuid : repaymentPlanUuids) {
			AssetSet repaymentPlan = repaymentPlanService.getUniqueRepaymentPlanByUuid(repaymentPlanUuid);
			//非（提前还款待处理&已结清）还款计划，不进行同步
			if (!isClearPrepaymentRepaymentPlan(repaymentPlan)) {
				logger.info("#提前还款成功，同步（还款计划、提前还款申请）失败，还款计划uuid["+ repaymentPlanUuid +"]");
				continue;
			}
			
			PrepaymentApplication prepaymentApplication = prepaymentApplicationService.getUniquePrepaymentApplicationByRepaymentPlanUuid(repaymentPlanUuid);
			processingSucceededPrepaymentApplicationBy(prepaymentApplication, repaymentPlan, prepaymentApplication.getType());
			logger.info("#提前还款成功，同步（还款计划、提前还款申请）成功，还款计划uuid["+ repaymentPlanUuid +"]");
		}
		
		long end = System.currentTimeMillis();
		logger.info("#processingPrepaymentApplicationAfterSuccess end(" + (start - end) + ").");
	}

	/**
	 * 符合已结清、提前还款待处理的还款计划
	 */
	private boolean isClearPrepaymentRepaymentPlan(AssetSet repaymentPlan) {
		return repaymentPlan.getActiveStatus() == AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING
				&& repaymentPlan.getAssetStatus() == AssetClearStatus.CLEAR;
	}

	/**
	 * 处理提前还款成功的提前还款申请，通过提前还款类型
	 * @param prepaymentApplication 提前还款申请
	 * @param repaymentPlan 提前还款的还款计划
	 * @param prepaymentType 提前还款类型
	 */
	private void processingSucceededPrepaymentApplicationBy(
			PrepaymentApplication prepaymentApplication,
			AssetSet repaymentPlan, PrepaymentType prepaymentType) {
		switch (prepaymentType) {
		case ALL:
			openRepaymentPlanForPrepaymentSuccess(repaymentPlan);
			updatePrepaymentApplicationStatus(prepaymentApplication, repaymentPlan, PrepaymentStatus.SUCCESS);
			break;
		default:
			//TODO
			break;
		}
	}

	/**
	 * 开启提前还款成功的还款计划，记录日志
	 */
	private void openRepaymentPlanForPrepaymentSuccess(AssetSet repaymentPlan) {
		//更新合同有效版本号
		Contract contract = repaymentPlan.getContract();
		Integer oldVersionNo = contractService.getActiveVersionNo(contract.getUuid());
		int newVersion = repaymentPlanHandler.updateContractActiveVersionNo(contract, oldVersionNo);
		contract.setActiveVersionNo(newVersion);
		
		//开启提前还款成功的还款计划
		repaymentPlan.setActiveStatus(AssetSetActiveStatus.OPEN);
		repaymentPlan.setVersionNo(contract.getActiveVersionNo());
		repaymentPlanService.update(repaymentPlan);
		
		//记录还款计划操作日志
		List<AssetSet> open = Arrays.asList(repaymentPlan);
		List<AssetSet> invalid = repaymentPlanService.get_all_unclear_and_open_asset_set_list(contract);
		//作废剩余开启的还款计划
		repaymentPlanService.invalidateAssets(invalid);
		
		contractHandler.addRepaymentPlanOperateLog(contract, RepaymentPlanOperateLog.PREPAYMENT_SUCCESS, open, invalid, null, null, RepaymentPlanOperateLog.OPERATOR_SYSTEM);
	}
	
	/**
	 * 更新提前还款申请状态（状态、完成时间）
	 */
	private void updatePrepaymentApplicationStatus(
			PrepaymentApplication prepaymentApplication,
			AssetSet repaymentPlan, PrepaymentStatus prepaymentStatus) {
		prepaymentApplication.setPrepaymentStatus(prepaymentStatus);
		prepaymentApplication.setCompletedTime(repaymentPlan.getActualRecycleDate());
		prepaymentApplicationService.update(prepaymentApplication);
	}

	@Override
	public void processingPrepaymentApplicationAfterFail(List<String> repaymentPlanUuids) {
		if(CollectionUtils.isEmpty(repaymentPlanUuids)) {
			return;
		}
		long start = System.currentTimeMillis();
		logger.info("#processingPrepaymentApplicationAfterFail start.");
		logger.info("#提前还款失败，开始同步（还款计划、提前还款申请）总计（"+ repaymentPlanUuids.size() +"）条 ! －uuids－" + repaymentPlanUuids);
		
		for (String repaymentPlanUuid : repaymentPlanUuids) {
			AssetSet repaymentPlan = repaymentPlanService.getUniqueRepaymentPlanByUuid(repaymentPlanUuid);
			//非（已达到应收日&未结清&提前还款待处理）还款计划，不进行同步
			if (!isExpiredUnclearPrepaymentRepaymentPlan(repaymentPlan)) {
				logger.info("#提前还款失败，同步（还款计划、提前还款申请）失败，还款计划uuid["+ repaymentPlanUuid +"]");
				continue;
			}
			
			PrepaymentApplication prepaymentApplication = prepaymentApplicationService.getUniquePrepaymentApplicationByRepaymentPlanUuid(repaymentPlanUuid);
			processingFailPrepaymentApplicationBy(prepaymentApplication, repaymentPlan, prepaymentApplication.getType());
			logger.info("#提前还款失败，同步（还款计划、提前还款申请）成功，还款计划uuid["+ repaymentPlanUuid +"]");
		}
		
		long end = System.currentTimeMillis();
		logger.info("#processingPrepaymentApplicationAfterFail end(" + (start - end) + ").");
	}

	/**
	 * 处理提前还款失败的提前还款申请，通过提前还款类型
	 * @param prepaymentApplication 提前还款申请
	 * @param repaymentPlan 提前还款的还款计划
	 * @param prepaymentType 提前还款类型
	 */
	private void processingFailPrepaymentApplicationBy(
			PrepaymentApplication prepaymentApplication,
			AssetSet repaymentPlan, PrepaymentType prepaymentType) {
		switch (prepaymentType) {
		case ALL:
			markInvalidRepaymentPlanForPrepaymentFail(repaymentPlan);
			updatePrepaymentApplicationStatus(prepaymentApplication, repaymentPlan, PrepaymentStatus.FAIL);
			break;
		default:
			//TODO
			break;
		}
	}

	/**
	 * 标记提前还款失败的还款计划为作废状态，记录日志
	 */
	private void markInvalidRepaymentPlanForPrepaymentFail(
			AssetSet repaymentPlan) {
		
		//作废提前还款失败的还款计划
		List<AssetSet> invalid = Arrays.asList(repaymentPlan);
		repaymentPlanService.invalidateAssets(invalid);

		Contract contract = repaymentPlan.getContract();
		
		//记录还款计划操作日志
		contractHandler.addRepaymentPlanOperateLog(contract, RepaymentPlanOperateLog.PREPAYMENT_FAIL, null, invalid, null, null, RepaymentPlanOperateLog.OPERATOR_SYSTEM);
	}


	/**
	 * 符合到期、未结清、提前还款待处理的还款计划
	 */
	private boolean isExpiredUnclearPrepaymentRepaymentPlan(
			AssetSet repaymentPlan) {
		return repaymentPlan.getActiveStatus() == AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING
				&& repaymentPlan.getAssetStatus() == AssetClearStatus.UNCLEAR
				&& repaymentPlan.getAssetRecycleDate().compareTo(new Date()) < 0;
	}

}
