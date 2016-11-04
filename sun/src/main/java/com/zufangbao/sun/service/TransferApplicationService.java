package com.zufangbao.sun.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel4TransactionDetail;

/**
 * 
 * @author 
 *
 */
public interface TransferApplicationService extends GenericService<TransferApplication>{

	/******************************** new method start ********************************/
	public List<TransferApplication> getTransferApplicationListBy(Order order,List<ExecutingDeductStatus> executingDeductStatusList);
	public List<TransferApplication> getTransferApplicationListBy(Order order);
	public List<TransferApplication> getDeductSucTransferApplicationListBy(Order order);
	public boolean existUnFailTransferApplication(Order order);
	
	public List<TransferApplication> getNeedPayTransferApplicationListByDueDate(Date dueDate);

	public List<TransferApplication> getTodayAllNeedPayTransferApplicationList();
	
	public TransferApplication getSingleTransferApplicationBy(Long batchPayRecordId);

	public List<TransferApplication> getInProcessTransferApplicationList();
	
	public Map<String, Object> getTransferApplicationListByOnLinePaymentQuery(TransferApplicationQueryModel transferApplicationQueryModel, Page page );
	
	public TransferApplication getLastCreatedTransferApplicationBy(Order order);
	
	public List<String> extractTransferApplicationNoListFrom(Order order);
	
	public List<String> extractTransferApplicationNoListFrom(List<TransferApplication> transferApplicationList);
	
	public boolean isAllTransferApplicationFail(Order order);
	
	public List<TransferApplication> queryTheDateTransferApplication(String queryDate, Long financialContractId);
	public List<TransferApplication> queryTheDateTransferApplicationList(Long financialContractId,ExecutingDeductStatus executingDeductStatus, String queryDate);
	
	public List<TransferApplication> getTransferApplicationListBy(Long financialContractId,ExecutingDeductStatus executingDeductStatus, Date deductDate);
	
	public boolean isTodayFirstTransferApplication(Order order, String message);
	
	public TransferApplication getTransferApplicationListBy(String Uuid);
	
	/**
	 * 获取近一日的线上支付单
	 * @return
	 */
	public List<TransferApplication> getRecentDayTransferApplicationList(AssetSet assetSet);
	
	public List<TransferApplication> queryTheDateTransferApplicationOrderByStatus(
			Long financialContractId, String queryDate);
	
	public List<TransferApplication>  queryTransferApplciationListBy(FinancialContract financialContract, String queryBeginDate ,String queryEndDate);

	public TransferApplication getTransferApplicationById(Long transferApplicationId);
	
	/**
	 * 获得指定天数的收款交易额
	 * @param days 天数;days>0,获得days天内的交易额;days<=0,获得总交易额;
	 * @param paymentChannelUuid 支付通道uuid null 表示所有通道
	 * @return
	 */
	public List<BigDecimal> getDebitTradingVolume(int days, String paymentChannelUuid);
	
	/**
	 * 获得指定天数的付款交易额
	 * @param days days>0,获得days天内的交易额;days<=0,获得总交易额;
	 * @param paymentChannelUuid
	 * @return
	 */
	public List<BigDecimal> getCreditTradingVolume(int days, String paymentChannelUuid);
	
	/**
	 * 获得总交易额
	 */
	public List<BigDecimal> getTotalTradingVolumeBy(String paymentChannelUuid);
	
	/**
	 * 获得所有通道的付款交易
	 */
	public List<TransferApplication> getTotalCreditTradingVolume();

	/**
	 * 获得所有通道的收款交易
	 */
	public List<TransferApplication> getTotalDebitTradingVolume();
	
	/**
	 * 获得近几个月的付款交易额
	 */
	public List<BigDecimal> getCreditTradingVolumeInMonths(int months, String paymentChannelUuid);
	
	
	/**
	 * 获得近几个月的收款交易额
	 */
	public List<BigDecimal> getDebitTradingVolumeInMonths(int months, String paymentChannelUuid);
	
	/**
	 * 24 hours trading success rate
	 */
	public BigDecimal getTradingSuccessRateIn24Hours(String paymentChannelUuid);
	
	/**
	 * 交易明细页的交易记录查询
	 */
	public Map<String, Object> queryTransferApplicationBy(TransferApplicationQueryModel4TransactionDetail queryModel, Page page);
	
	
	public Map<String, Object> getTransferApplicationStatistics(List<TransferApplication> transferApplications);
	
	public List<TransferApplication> getTransferApplicationForStatistics(int days, int type, String paymentChannelUuid);
	
	public List<TransferApplication> getTransferApplicationByRepaymentPlan(
			AssetSet repaymentPlan);
	
}
