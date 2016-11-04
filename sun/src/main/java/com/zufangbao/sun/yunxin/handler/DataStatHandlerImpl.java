package com.zufangbao.sun.yunxin.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RemittanceDataStatistic;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;

@Component("dataStatHandler")
public class DataStatHandlerImpl implements DataStatHandler {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SettlementOrderService settlementOrderService;
	
	@Autowired
	private IRemittanceApplicationService iRemittanceApplicationService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Override
	public RepaymentDataStatistic countRepaymentData(List<Long> financialContractIds) {
		
		List<String> financialContractUuids = financialContractService.getFinancialContractUuidsByIds(financialContractIds);
		
		int processing_and_not_overdue_assets = count_assets_of_processing_payment_status_and_not_overdue(financialContractUuids);
		int unusual_and_not_overdue_assets = count_assets_of_unusual_payment_status_and_not_overdue(financialContractUuids);
		int overdue_unconfirmed_assets = count_assets_of_unconfirmed_overdue(financialContractUuids);
		int overdue_and_processing_unusual_assets = count_assets_of_overdue_and_processing_unusual_payment_status(financialContractUuids);
		int waiting_guarantee_orders = count_guarantee_orders_of_waiting(financialContractIds);
		int settlement_status_create_settlement_orders = count_settlement_orders_of_settlement_status_create(financialContractIds);
		RepaymentDataStatistic repaymentData = new RepaymentDataStatistic(processing_and_not_overdue_assets,unusual_and_not_overdue_assets,
				overdue_unconfirmed_assets,overdue_and_processing_unusual_assets,
				waiting_guarantee_orders,settlement_status_create_settlement_orders);
		return repaymentData;
	}
	
	private int count_assets_of_processing_payment_status_and_not_overdue(List<String> financialContractUuids){
		return repaymentPlanService.countAssets(financialContractUuids, AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING), AuditOverdueStatus.NORMAL);
	}
	
	private int count_assets_of_unusual_payment_status_and_not_overdue(List<String> financialContractUuids){
		return repaymentPlanService.countAssets(financialContractUuids, AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.UNUSUAL), AuditOverdueStatus.NORMAL);
	}
	
	private int count_assets_of_unconfirmed_overdue(List<String> financialContractUuids){
		return repaymentPlanService.countAssets(financialContractUuids, AssetSetActiveStatus.OPEN,Collections.emptyList(), AuditOverdueStatus.UNCONFIRMED);
	}
	private int count_assets_of_overdue_and_processing_unusual_payment_status(List<String> financialContractUuids){
		return repaymentPlanService.countAssets(financialContractUuids, AssetSetActiveStatus.OPEN,Arrays.asList(PaymentStatus.UNUSUAL,PaymentStatus.PROCESSING), AuditOverdueStatus.OVERDUE);
	}
	
	private int count_guarantee_orders_of_waiting(List<Long> financialContractIds){
		return orderService.countGuranteeOrders(financialContractIds, GuaranteeStatus.WAITING_GUARANTEE);
	}
	private int count_settlement_orders_of_settlement_status_create(List<Long> financialContractIds){
		return settlementOrderService.countSettlementOrderList(financialContractIds, SettlementStatus.WAITING);
	}

	@Override
	public RemittanceDataStatistic countRemittanceData(
			List<Long> financialContractIds) {
		Date calculateDate = new Date();
		int processingNums = iRemittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, Arrays.asList(ExecutionStatus.PROCESSING), calculateDate);
		int abnormalNums = iRemittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, Arrays.asList(ExecutionStatus.ABNORMAL), calculateDate);
		int failedNums = iRemittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, Arrays.asList(ExecutionStatus.FAIL), calculateDate);
		return new RemittanceDataStatistic(processingNums, abnormalNums, failedNums);
	}

}
