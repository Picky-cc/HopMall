package com.zufangbao.wellsfargo.yunxin.handler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel4TransactionDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel;

/**
 * 
 * @author zhanghongbing
 *
 */
public interface TransferApplicationHandler {
	public Serializable createTodayTransferApplicationByOrder(Order order);
	public List<Serializable> createTransferApplicationByOrderListAndOverDueStatus(List<Order> orderList, boolean isOverDue);
	
	public List<Serializable> todayRecycleAssetCreateTransferApplications();
	public List<Serializable> overDueAssetCreateTransferApplications();

	public BatchPayRecord createBatchPayRecordBy(TransferApplication transferApplication);
	
	public void updateTransferAppAndOrderAndAssetBy(TransferApplication transferApplication, boolean isSuccess, String message, Date deductTime);
	
	public void updateTodayOrderExecuteStatus();
	public void updateTodayOrderExecuteStatus(Order order);
	
	
	public void todayFirstTransferApplicationFailRemind(Order order, String message);
	
	
	public BigDecimal getTotalDebitAmount(String paymentChannelUuid);
	
	
	public BigDecimal getTotalCreditAmount(String paymentChannelUuid);
	
	
	public Map<String, Object> getCreditAndDebitAmountListBy(String time, String paymentChannelUuid);
	
	public Map<String, Object> queryTransferApplicationForTransactionDetail(TransferApplicationQueryModel4TransactionDetail queryModel4TransactionDetail, Page page);
	
	public Map<String, Object> getDataForEfficentAnalysis();
	
	public Map<String, Object> getTradingVolumeTrendIn(String time);
	
	public List<TransferApplicationStatisticsModel> getStatisticsDataIn24Hours(int type);
	
	
}
