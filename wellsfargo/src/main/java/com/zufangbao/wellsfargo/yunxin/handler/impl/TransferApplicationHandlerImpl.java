package com.zufangbao.wellsfargo.yunxin.handler.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.PaymentWay;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel4TransactionDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationResultModel4TransactionDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel;
import com.zufangbao.sun.yunxin.exception.SmsTemplateNotExsitException;
import com.zufangbao.sun.yunxin.handler.SmsQueneHandler;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

/**
 * 
 * @author zhanghongbing
 *
 */
@Component("transferApplicationHandler")
public class TransferApplicationHandlerImpl implements
		TransferApplicationHandler {

	@Autowired
	private TransferApplicationService transferApplicationService;

	@Autowired
	private BatchPayRecordService batchPayRecordService;

	@Autowired
	private ContractAccountService contractAccountService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private RepaymentPlanService repaymentPlanService;

	@Autowired
	private SmsQueneHandler smsQueneHandler;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	private static Log logger = LogFactory.getLog(TransferApplicationHandlerImpl.class);
	
	@Override
	public Serializable createTodayTransferApplicationByOrder(Order order) {
		if(order==null || !DateUtils.isSameDay(order.getDueDate(), new Date())){
			return null;
		}
		if(order.getExecutingSettlingStatus() == ExecutingSettlingStatus.FAIL){
			return null;
		}
		if(transferApplicationService.existUnFailTransferApplication(order)){
			return null;
		}
		
		TransferApplication transferApplication = create_transfer_application(order);
		Serializable id = transferApplicationService.save(transferApplication);
		
		order.setExecutingSettlingStatus(ExecutingSettlingStatus.DOING);
		order.setModifyTime(new Date());
		orderService.save(order);
		return id;
	}

	private TransferApplication create_transfer_application(Order order) {
		AssetSet assetSet = order.getAssetSet();
		Contract contract = assetSet.getContract();
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		return new TransferApplication(order.getTotalRent(), 
				contractAccount, order,PaymentWay.UNIONDEDUCT);
	}

	@Override
	public List<Serializable> todayRecycleAssetCreateTransferApplications() {
		return createTransferApplicationByOrderListAndOverDueStatus(false);
	}

	@Override
	public List<Serializable> overDueAssetCreateTransferApplications() {
		return createTransferApplicationByOrderListAndOverDueStatus(true);
	}

	private List<Serializable> createTransferApplicationByOrderListAndOverDueStatus(boolean isOverDue) {
		List<Order> orderRequiredToTransfer = orderService.getSettlementStatementInNormalProcessing();
		return createTransferApplicationByOrderListAndOverDueStatus(orderRequiredToTransfer, isOverDue);
	}
	
	@Override
	public List<Serializable> createTransferApplicationByOrderListAndOverDueStatus(List<Order> orderList, boolean isOverdue) {
		List<Serializable> ids = new ArrayList<Serializable>();
		orderList.stream().filter(
			order -> isOverdue == order.getAssetSet().isOverdueDate(new Date())
			&& order.getFinancialContract() != null
			&& order.getFinancialContract().isAllowDeduct(isOverdue)
		).forEach(
			order -> {
				Serializable id = createTodayTransferApplicationByOrder(order);
				if(id != null) {
					ids.add(id);
				}
			}
		);
		return ids;
	}

	@Override
	public BatchPayRecord createBatchPayRecordBy(TransferApplication transferApplication) {
		BatchPayRecord batchPayRecord = new BatchPayRecord(transferApplication.getAmount(), transferApplication.getUnionPayOrderNo());
		Long batchPayRecordId = (Long)batchPayRecordService.save(batchPayRecord);
		transferApplication.setLastModifiedTime(new Date());
		transferApplication.setBatchPayRecordId(batchPayRecordId);
		transferApplication.setExecutingDeductStatus(ExecutingDeductStatus.DOING);
		transferApplicationService.update(transferApplication);
		return batchPayRecordService.load(BatchPayRecord.class, batchPayRecordId);
	}
	
	@Override
	public void updateTransferAppAndOrderAndAssetBy(TransferApplication transferApplication, boolean isSuccess, String message, Date deductTime) {
		transferApplication.updateDeductStatus(isSuccess, message, deductTime);
		transferApplicationService.saveOrUpdate(transferApplication);
		if(isSuccess){
			Order order = transferApplication.getOrder();
			order.updateClearStatus(transferApplication.getDeductTime(),true);
			orderService.save(order);

			order.getAssetSet().updateClearStatus(transferApplication.getDeductTime(),true);
			repaymentPlanService.save(order.getAssetSet());
		}
	}

	@Override
	public void todayFirstTransferApplicationFailRemind(Order order, String message) {
		boolean isTodayFirst = transferApplicationService.isTodayFirstTransferApplication(order, message);
		if(isTodayFirst) {
			create_balance_not_enough_remind_sms(order);
		}
	}

	private void create_balance_not_enough_remind_sms(Order order) {
		try {
			boolean allowedSendStatus = dictionaryService.getSmsAllowSendFlag();
			smsQueneHandler.saveFailSmsQuene(order.getAssetSet(), allowedSendStatus);
		} catch (SmsTemplateNotExsitException e) {
			e.printStackTrace();
			logger.error("扣款失败短信模版不存在", e);
		}
	}

	@Override
	public void updateTodayOrderExecuteStatus() {
		List<Order> orderList = orderService.getAllSettlementStatementInProcessing();
		for (Order order : orderList) {
			try{
				updateTodayOrderExecuteStatus(order);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateTodayOrderExecuteStatus(Order order) {
		if(order == null){
			return;
		}
		if(!transferApplicationService.isAllTransferApplicationFail(order)){
			return;
		}
		order.setExecutingSettlingStatus(ExecutingSettlingStatus.FAIL);
		order.setModifyTime(new Date());
		orderService.save(order);
	}

	@Override
	public BigDecimal getTotalDebitAmount(String paymentChannelUuid) {
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return null;
		}
		List<BigDecimal> amountList = transferApplicationService.getDebitTradingVolume(-1, paymentChannelUuid);
		return amountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public BigDecimal getTotalCreditAmount(String paymentChannelUuid) { 
		if(StringUtils.isEmpty(paymentChannelUuid)){
			return null;
		}
		List<BigDecimal> amountList = transferApplicationService.getCreditTradingVolume(-1, paymentChannelUuid);
		return amountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Map<String, Object> getCreditAndDebitAmountListBy(String time, String paymentChannelUuid) {// TODO Test
		if (StringUtils.isEmpty(paymentChannelUuid)) {
			return null;
		}
		Map<String, Object> amountMap = new HashMap<String, Object>();
		List<BigDecimal> debitAmountList = null;
		List<BigDecimal> creditAmountList = null;
		if ("7d".equals(time)) {
			debitAmountList = transferApplicationService.getDebitTradingVolume(7, paymentChannelUuid);
			creditAmountList = transferApplicationService.getCreditTradingVolume(7, paymentChannelUuid);
		} else if ("6m".equals(time)) {
			debitAmountList = transferApplicationService.getDebitTradingVolumeInMonths(6, paymentChannelUuid);
			creditAmountList = transferApplicationService.getCreditTradingVolumeInMonths(6, paymentChannelUuid);
		}
		amountMap.put("debitAmountList", debitAmountList);
		amountMap.put("creditAmountList", creditAmountList);
		return amountMap;
	}

	@Override
	public Map<String, Object> queryTransferApplicationForTransactionDetail(
			TransferApplicationQueryModel4TransactionDetail queryModel4TransactionDetail,
			Page page) {
		Map<String, Object> resultMap = transferApplicationService.queryTransferApplicationBy(queryModel4TransactionDetail, page);
		List<TransferApplication> transferApplications = (List<TransferApplication>) resultMap.get("list");
		List<TransferApplicationResultModel4TransactionDetail> resultModels = new ArrayList<TransferApplicationResultModel4TransactionDetail>();
		if(CollectionUtils.isNotEmpty(transferApplications)){
			resultModels = transferApplications.stream().map(a->new TransferApplicationResultModel4TransactionDetail(a)).collect(Collectors.toList());
		}
		resultMap.put("list", resultModels);
		return resultMap;
	}

	@Override
	public Map<String, Object> getDataForEfficentAnalysis() {
		Map<String, Object> resultDataMap = new HashMap<String, Object>();
		List<TransferApplication> creditTransferApplications = transferApplicationService.getTotalCreditTradingVolume();
		resultDataMap.put("creditTransferApplicationCount", creditTransferApplications.size());
		BigDecimal creditTotalTradingVolume = creditTransferApplications.stream().map(TransferApplication::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		resultDataMap.put("creditTotalTradingVolume", creditTotalTradingVolume);
		resultDataMap.put("creditTotalFee", BigDecimal.ZERO);
		
		List<TransferApplication> debitTransferApplications = transferApplicationService.getTotalDebitTradingVolume();
		resultDataMap.put("debitTransferApplicationCount", debitTransferApplications.size());
		BigDecimal debitTotalTradingVolume = debitTransferApplications.stream().map(TransferApplication::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		resultDataMap.put("debitTotalTradingVolume", debitTotalTradingVolume);
		resultDataMap.put("debitTotalFee", BigDecimal.ZERO);
		
		return resultDataMap;
	}

	@Override
	public Map<String, Object> getTradingVolumeTrendIn(String time) {
		Map<String, Object> rtnDataMap = new HashMap<String, Object>();
		if("7d".equals(time)){
			rtnDataMap.put("creditAmountList", transferApplicationService.getCreditTradingVolume(7, null));
			rtnDataMap.put("debitAmountList", transferApplicationService.getDebitTradingVolume(7, null));
		}else if("6m".equals(time)){
			rtnDataMap.put("creditAmountList", transferApplicationService.getCreditTradingVolumeInMonths(6, null));
			rtnDataMap.put("debitAmountList", transferApplicationService.getDebitTradingVolumeInMonths(6, null));			
		}
		return rtnDataMap;
	}

	@Override
	public List<TransferApplicationStatisticsModel> getStatisticsDataIn24Hours(int type) {
		List<TransferApplicationStatisticsModel> transferApplicationStatisticsModels = paymentChannelInformationService.getTransferApplicationStatistics();
		for (TransferApplicationStatisticsModel transferApplicationStatisticsModel : transferApplicationStatisticsModels) {
			String paymentChannelUuid = transferApplicationStatisticsModel.getPaymentChannelUuid();
			List<TransferApplication> transferApplications = transferApplicationService.getTransferApplicationForStatistics(1, type, paymentChannelUuid);
			Map<String, Object> data = transferApplicationService.getTransferApplicationStatistics(transferApplications);
			transferApplicationStatisticsModel.setData(data);
		}
		return transferApplicationStatisticsModels;
	}
}