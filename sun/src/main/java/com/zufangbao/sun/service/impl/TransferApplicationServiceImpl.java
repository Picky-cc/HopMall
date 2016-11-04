package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.DeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel4TransactionDetail;



/**
 * 
 * @author zhanghongbing
 *
 */
@Service("transferApplicationService")
public class TransferApplicationServiceImpl extends
		GenericServiceImpl<TransferApplication> implements
		TransferApplicationService {

	/******************************** new method start ********************************/
	@Override
	public List<TransferApplication> getTransferApplicationListBy(Order order,
			List<ExecutingDeductStatus> executingDeductStatusList) {
		if (order == null) {
			return Collections.emptyList();
		}
		StringBuffer querySentence = new StringBuffer(
				"From TransferApplication WHERE order=:order ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order", order);
		if (!CollectionUtils.isEmpty(executingDeductStatusList)) {
			querySentence
					.append(" and executingDeductStatus IN (:executingDeductStatusList)");
			params.put("executingDeductStatusList", executingDeductStatusList);
		}
		return genericDaoSupport
				.searchForList(querySentence.toString(), params);
	}

	@Override
	public List<TransferApplication> getTransferApplicationListBy(Order order) {
		return getTransferApplicationListBy(order,
				new ArrayList<ExecutingDeductStatus>());
	}

	@Override
	public List<TransferApplication> getDeductSucTransferApplicationListBy(
			Order order) {
		List<ExecutingDeductStatus> executingDeductStatusList = new ArrayList<ExecutingDeductStatus>();
		executingDeductStatusList.add(ExecutingDeductStatus.SUCCESS);
		return getTransferApplicationListBy(order, executingDeductStatusList);
	}

	@Override
	public boolean existUnFailTransferApplication(Order order) {
		List<ExecutingDeductStatus> executingDeductStatusList = new ArrayList<ExecutingDeductStatus>();
		executingDeductStatusList.add(ExecutingDeductStatus.CREATE);
		executingDeductStatusList.add(ExecutingDeductStatus.DOING);
		executingDeductStatusList.add(ExecutingDeductStatus.SUCCESS);
		executingDeductStatusList.add(ExecutingDeductStatus.TIME_OUT);
		List<TransferApplication> transferApplicationList = getTransferApplicationListBy(
				order, executingDeductStatusList);
		return !CollectionUtils.isEmpty(transferApplicationList);
	}

	@Override
	public List<TransferApplication> getNeedPayTransferApplicationListByDueDate(
			Date dueDate) {
		String querySentence = "From TransferApplication ta WHERE ta.deductStatus=:deductStatus "
				+ "AND ta.executingDeductStatus=:executingDeductStatus AND Date(ta.order.dueDate)=:dueDate ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("executingDeductStatus", ExecutingDeductStatus.CREATE);
		params.put("deductStatus", DeductStatus.UNCLEAR);
		params.put("dueDate", DateUtils.asDay(dueDate));
		return genericDaoSupport.searchForList(querySentence, params);
	}

	@Override
	public List<TransferApplication> getTodayAllNeedPayTransferApplicationList() {
		return getNeedPayTransferApplicationListByDueDate(new Date());
	}

	@Override
	public TransferApplication getSingleTransferApplicationBy(
			Long batchPayRecordId) {
		List<TransferApplication> transferApplicationList = genericDaoSupport
				.searchForList(
						"FROM TransferApplication WHERE batchPayRecordId = :batchPayRecordId",
						"batchPayRecordId", batchPayRecordId);
		if (CollectionUtils.isEmpty(transferApplicationList)) {
			return null;
		}
		return transferApplicationList.get(0);
	}

	@Override
	public List<TransferApplication> getInProcessTransferApplicationList() {
		String querySentence = "From TransferApplication ta WHERE ta.deductStatus=:deductStatus "
				+ "AND ta.executingDeductStatus=:executingDeductStatus ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("executingDeductStatus", ExecutingDeductStatus.DOING);
		params.put("deductStatus", DeductStatus.UNCLEAR);
		return genericDaoSupport.searchForList(querySentence, params);
	}

	private boolean isAllTransferApplicationFail(
			List<TransferApplication> transferApplicationList) {
		for (TransferApplication transferApplication : transferApplicationList) {
			if (transferApplication == null) {
				continue;
			}
			if (transferApplication.getExecutingDeductStatus() != ExecutingDeductStatus.FAIL) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAllTransferApplicationFail(Order order) {
		if (order == null) {
			return false;
		}
		List<TransferApplication> transferApplicationList = getTransferApplicationListBy(
				order, Collections.emptyList());
		if (CollectionUtils.isEmpty(transferApplicationList)) {
			return false;
		}
		return isAllTransferApplicationFail(transferApplicationList);
	}

	@Override
	public TransferApplication getLastCreatedTransferApplicationBy(Order order) {
		if (order == null) {
			return null;
		}
		StringBuffer querySentence = new StringBuffer(
				"From TransferApplication ta WHERE ta.order=:order ORDER BY ta.createTime DESC");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order", order);

		List<TransferApplication> transferApplicationList = genericDaoSupport
				.searchForList(querySentence.toString(), params);
		if (CollectionUtils.isEmpty(transferApplicationList)) {
			return null;
		}
		return transferApplicationList.get(0);
	}

	@Override
	public List<String> extractTransferApplicationNoListFrom(
			List<TransferApplication> transferApplicationList) {
		List<String> transferApplicationNoList = new ArrayList<String>();
		for (TransferApplication transferApplication : transferApplicationList) {
			if (transferApplication == null) {
				continue;
			}
			transferApplicationNoList.add(transferApplication
					.getTransferApplicationNo());
		}
		return transferApplicationNoList;
	}

	@Override
	public List<String> extractTransferApplicationNoListFrom(Order order) {
		if (order == null) {
			return null;
		}
		List<TransferApplication> transferApplicationList = getTransferApplicationListBy(order);
		return extractTransferApplicationNoListFrom(transferApplicationList);
	}

	@Override
	public List<TransferApplication> getTransferApplicationListBy(
			Long financialContractId,
			ExecutingDeductStatus executingDeductStatus, Date deductDate) {
		String querySetence = "From TransferApplication ta where ta.order.financialContract.id=:financialContractId and executingDeductStatus=:executingDeductStatus and Date(deductTime)=Date(:deductTime)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("financialContractId", financialContractId);
		params.put("executingDeductStatus", executingDeductStatus);
		params.put("deductTime", deductDate);
		return genericDaoSupport.searchForList(querySetence.toString(), params);
	}

	@Override
	public List<TransferApplication> queryTheDateTransferApplication(
			String queryDate, Long financialContractId) {
		Date fromDate = DateUtils.parseDate(queryDate, "yyyy-MM-dd");
		Date toDate = DateUtils.addDays(
				DateUtils.parseDate(queryDate, "yyyy-MM-dd"), 1);
		String querySetence = "From TransferApplication r where r.order.financialContract.id=:financialContractId and r.lastModifiedTime >=:fromDate and r.lastModifiedTime<:toDate ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("financialContractId", financialContractId);
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		return genericDaoSupport.searchForList(querySetence, params);
	}

	@Override
	public List<TransferApplication> queryTheDateTransferApplicationList(
			Long financialContractId,
			ExecutingDeductStatus executingDeductStatus, String queryDate) {
		Date fromDate = DateUtils.parseDate(queryDate, "yyyy-MM-dd");
		Date toDate = DateUtils.addDays(
				DateUtils.parseDate(queryDate, "yyyy-MM-dd"), 1);
		String querySetence = "From TransferApplication ta where ta.order.financialContract.id=:financialContractId and executingDeductStatus=:executingDeductStatus and lastModifiedTime >= :fromDate and lastModifiedTime < :toDate)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("financialContractId", financialContractId);
		params.put("executingDeductStatus", executingDeductStatus);
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		return genericDaoSupport.searchForList(querySetence.toString(), params);
	}

	@Override
	public List<TransferApplication> queryTheDateTransferApplicationOrderByStatus(
			Long financialContractId, String queryDate) {

		List<TransferApplication> onLineBillList = this
				.queryTheDateTransferApplication(queryDate, financialContractId);

		List<TransferApplication> noRepeateList = generateNoRepeateList(onLineBillList);

		List<TransferApplication> orderByList = generateOrderByList(noRepeateList);

		return orderByList;
	}

	private List<TransferApplication> generateOrderByList(
			List<TransferApplication> noRepeateList) {
		List<TransferApplication> successList = new ArrayList<TransferApplication>();
		List<TransferApplication> failList = new ArrayList<TransferApplication>();
		List<TransferApplication> doingList = new ArrayList<TransferApplication>();
		List<TransferApplication> createList = new ArrayList<TransferApplication>();
		List<TransferApplication> timeOutList = new ArrayList<TransferApplication>();
		List<TransferApplication> orderByList = new ArrayList<TransferApplication>();
		for (TransferApplication onLineBill : noRepeateList) {
			if (onLineBill.getExecutingDeductStatus() == ExecutingDeductStatus.SUCCESS) {
				successList.add(onLineBill);
				continue;
			}
			if (onLineBill.getExecutingDeductStatus() == ExecutingDeductStatus.FAIL) {
				failList.add(onLineBill);
				continue;
			}
			if (onLineBill.getExecutingDeductStatus() == ExecutingDeductStatus.DOING) {
				doingList.add(onLineBill);
				continue;
			}
			if (onLineBill.getExecutingDeductStatus() == ExecutingDeductStatus.CREATE) {
				createList.add(onLineBill);
				continue;
			}
			if (onLineBill.getExecutingDeductStatus() == ExecutingDeductStatus.TIME_OUT) {
				timeOutList.add(onLineBill);
				continue;
			}
		}
		sortAllList(successList, failList, doingList, createList, timeOutList);
		orderByList.addAll(successList);
		orderByList.addAll(failList);
		orderByList.addAll(doingList);
		orderByList.addAll(createList);
		orderByList.addAll(timeOutList);
		return orderByList;
	}

	private void sortAllList(List<TransferApplication> successList,
			List<TransferApplication> failList,
			List<TransferApplication> doingList,
			List<TransferApplication> createList,
			List<TransferApplication> timeOutList) {
		successList = sortList(successList);
		failList = sortList(failList);
		doingList = sortList(doingList);
		createList = sortList(createList);
		timeOutList = sortList(timeOutList);

	}

	private List<TransferApplication> sortList(List<TransferApplication> list) {
		Collections.sort(list, new Comparator<TransferApplication>() {
			@Override
			public int compare(TransferApplication b1, TransferApplication b2) {
				return b1.getLastModifiedTime().compareTo(
						b2.getLastModifiedTime());
			}

		});
		return list;

	}

	private List<TransferApplication> generateNoRepeateList(
			List<TransferApplication> onLineBillList) {
		Map<Long, TransferApplication> noRepeateMap = new HashMap<Long, TransferApplication>();
		for (TransferApplication bill : onLineBillList) {
			Long orderId = bill.getOrder().getId();
			noRepeateMap.put(orderId, bill);
			for (TransferApplication checkBill : onLineBillList) {
				if (checkBill.getOrder().getId() == orderId) {
					if (checkBill.getLastModifiedTime().after(
							bill.getLastModifiedTime())) {
						noRepeateMap.put(checkBill.getOrder().getId(),
								checkBill);
					}

				}

			}
		}
		return new ArrayList(noRepeateMap.values());
	}

	@Override
	public Map<String, Object> getTransferApplicationListByOnLinePaymentQuery(
			TransferApplicationQueryModel transferApplicationQueryModel, Page page) {
		StringBuffer sentence = new StringBuffer("FROM TransferApplication where 1=1");
		Map<String, Object> parameters = new HashMap<>();
		if (transferApplicationQueryModel.getPaymentWayEnum() != null) {
			sentence.append(" And paymentWay =:paymentWay");
			parameters.put("paymentWay", transferApplicationQueryModel.getPaymentWayEnum());
		}
		if (transferApplicationQueryModel.getExecutingDeductStatusEnum() != null) {
			sentence.append(" And executingDeductStatus =:executingDeductStatus");
			parameters.put("executingDeductStatus", transferApplicationQueryModel.getExecutingDeductStatusEnum());
		}
		if (is_where_condition(transferApplicationQueryModel.getBank())) {
			sentence.append(" And contractAccount.bank LIKE :bank");
			parameters.put("bank", "%" + transferApplicationQueryModel.getBank() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getAccountName())) {
			sentence.append(" And contractAccount.payerName LIKE :payerName");
			parameters.put("payerName", "%" + transferApplicationQueryModel.getAccountName() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getPaymentNo())) {
			sentence.append(" And transferApplicationNo LIKE :transferApplicationNo");
			parameters.put("transferApplicationNo", "%" + transferApplicationQueryModel.getPaymentNo() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getRepaymentNo())) {
			sentence.append(" And order.assetSet.singleLoanContractNo LIKE :singleLoanContractNo");
			parameters.put("singleLoanContractNo", "%" + transferApplicationQueryModel.getRepaymentNo() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getBillingNo())) {
			sentence.append(" And order.orderNo LIKE :orderNo");
			parameters.put("orderNo", "%" + transferApplicationQueryModel.getBillingNo() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getPayAcNo())) {
			sentence.append(" And contractAccount.payAcNo LIKE :payAcNo");
			parameters.put("payAcNo", "%" + transferApplicationQueryModel.getPayAcNo() + "%");
		}
		if (is_where_condition(transferApplicationQueryModel.getStartDateString())) {
			sentence.append(" And lastModifiedTime >= :startDate");
			parameters.put("startDate", DateUtils.asDay(transferApplicationQueryModel.getStartDateString()));
		}
		if (is_where_condition(transferApplicationQueryModel.getEndDateString())) {
			sentence.append(" And lastModifiedTime < :endDate");
			parameters.put("endDate", DateUtils.addDays(transferApplicationQueryModel.getEndDate(), 1));
		}
		if (is_where_condition(transferApplicationQueryModel.getComment())) {
			sentence.append(" And comment LIKE :comment");
			parameters.put("comment", "%" + transferApplicationQueryModel.getComment() + "%");
		}
		sentence.append(" order by lastModifiedTime desc, id desc ");
		List<TransferApplication> result = (List<TransferApplication>) genericDaoSupport.searchForList(sentence.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		int size = genericDaoSupport.count(sentence.toString(), parameters);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("size", size);
		resultMap.put("list", result);
		return resultMap;
	}

	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	@Override
	public boolean isTodayFirstTransferApplication(Order order, String message) {
		List<TransferApplication> transferApplicationList = getTransferApplicationListBy(order);
		List<TransferApplication> balanceNoEnoughList = new ArrayList<>();
		for (TransferApplication transferApplication : transferApplicationList) {
			if (org.apache.commons.lang.StringUtils.equals(message,
					transferApplication.getComment())) {
				balanceNoEnoughList.add(transferApplication);
			}
		}
		return (CollectionUtils.isEmpty(balanceNoEnoughList)
				|| balanceNoEnoughList.size() == 1) ;
	}

	@Override
	public List<TransferApplication> getRecentDayTransferApplicationList(
			AssetSet assetSet) {
		Map<String, Object> parameters = new HashMap<>();
		String today = DateUtils.today();
		parameters.put("assetSet", assetSet);
		parameters.put("today", today);
		String sqlGetMaxDate = "SELECT COALESCE(MAX(DATE_FORMAT(createTime, '%Y-%m-%d')), :today) FROM TransferApplication WHERE order.assetSet =:assetSet";
		List<String> maxDates = this.genericDaoSupport.searchForList(
				sqlGetMaxDate, parameters);
		String maxDate = CollectionUtils.isEmpty(maxDates) ? today : maxDates
				.get(0);

		String sentence = "SELECT ta FROM TransferApplication ta "
				+ "WHERE createTime >= CONCAT(:maxDate,' 0:00:00') AND createTime <= CONCAT(:maxDate,' 23:59:59') AND order.assetSet =:assetSet";
		parameters.put("maxDate", maxDate);
		return this.genericDaoSupport.searchForList(sentence, parameters);
	}

	@Override
	public TransferApplication getTransferApplicationListBy(String Uuid) {
		if(StringUtils.isEmpty(Uuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("transferApplicationUuid", Uuid);
		return this.list(TransferApplication.class, filter).get(0);
	}

	@Override
	public List<TransferApplication> queryTransferApplciationListBy(
			FinancialContract financialContract, String queryBeginDate,
			String queryEndDate) {
		Date beginDate = DateUtils.asDay(queryBeginDate);
		Date endDate   = DateUtils.asDay(queryEndDate);
		Map<String ,Object> params = new HashMap<String , Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		String querySentence = "from TransferApplication WHERE  Date(createTime) >=:beginDate and Date(createTime) <=:endDate";
		Long  financialContractId = financialContract.getId();
		querySentence +=" And order.financialContract.id = :financialContractId";
		params.put("financialContractId", financialContractId);
		return this.genericDaoSupport.searchForList(querySentence,params);
	}

	@Override
	public TransferApplication getTransferApplicationById(Long transferApplicationId) {
		return this.genericDaoSupport.get(TransferApplication.class, transferApplicationId);
	}

	@Override
	public List<BigDecimal> getCreditTradingVolume(int days, String paymentChannelUuid) {
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer querySb = new StringBuffer("SELECT ta FROM TransferApplication ta WHERE ta.deductStatus =:deductStatus");
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		
		if(!StringUtils.isEmpty(paymentChannelUuid)){
			querySb.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			params.put("paymentChannelUuid", paymentChannelUuid);
		}
		if(days > 0){
			Date today = new Date();
			String todayStr = DateUtils.today();
			Date someDaysBefore = DateUtils.addDays(today, -days);
			Date beginTime = DateUtils.asDay(someDaysBefore);
			Date endTime = DateUtils.asDay(today);
			String someDaysBeforeStr = DateUtils.format(someDaysBefore);
			querySb.append(" AND ta.createTime >= :beginTime AND ta.createTime < :endTime");
			params.put("beginTime", beginTime);
			params.put("endTime", endTime);
		}
		querySb.append(" ORDER BY ta.deductTime ASC");
		List<TransferApplication> transferApplications = this.genericDaoSupport.searchForList(querySb.toString(), params);
		
		Map<String, BigDecimal> result = transferApplications.stream().collect(Collectors.groupingBy(TransferApplication::getDeductTimeAsDay,TreeMap::new,
				Collectors.reducing(BigDecimal.ZERO, TransferApplication::getAmount, BigDecimal::add)));
		return new ArrayList<BigDecimal>(result.values());
		
	}
	
	@Override
	public List<BigDecimal> getDebitTradingVolume(int days, String paymentChannelUuid) {
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer querySb = new StringBuffer("SELECT ta FROM TransferApplication ta WHERE ta.deductStatus =:deductStatus");
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		if(!StringUtils.isEmpty(paymentChannelUuid)){
			querySb.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			params.put("paymentChannelUuid", paymentChannelUuid);
		}
		if(days > 0){
			Date today = new Date();
			Date endTime = DateUtils.asDay(today);
			String todayStr = DateUtils.today();
			Date someDaysBefore = DateUtils.addDays(today, -days);
			Date beginTime = DateUtils.asDay(someDaysBefore);
			String someDaysBeforeStr = DateUtils.format(someDaysBefore);
			querySb.append(" AND ta.createTime >=:beginDate AND ta.createTime < :endDate");
			params.put("beginDate", beginTime);
			params.put("endDate", endTime);
		}
		querySb.append(" ORDER BY ta.deductTime ASC");
		List<TransferApplication> transferApplications = this.genericDaoSupport.searchForList(querySb.toString(), params);
		Map<String, BigDecimal> result = transferApplications.stream().collect(Collectors.groupingBy(TransferApplication::getDeductTimeAsDay,TreeMap::new,
				Collectors.reducing(BigDecimal.ZERO, TransferApplication::getAmount, BigDecimal::add)));
		return new ArrayList<BigDecimal>(result.values());
	}
	
	@Override
	public List<TransferApplication> getTransferApplicationForStatistics(int days, int type, String paymentChannelUuid) {
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer querySb = new StringBuffer("SELECT ta FROM TransferApplication ta WHERE 1=1");
		if(!StringUtils.isEmpty(paymentChannelUuid)){
			if(type == 0){ // 全部
				querySb.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			}else if(type == 1){ // 收款
				querySb.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			}else if(type == 2){// 付款
				querySb.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			}
			params.put("paymentChannelUuid", paymentChannelUuid);
		}
		if(days > 0){
			Date today = new Date();
			String todayStr = DateUtils.today();
			Date someDaysBefore = DateUtils.addDays(today, -days);
			String someDaysBeforeStr = DateUtils.format(someDaysBefore);
			querySb.append(" AND ta.createTime >=:beginDate AND ta.createTime <= :endDate");
			params.put("beginDate", someDaysBefore);
			params.put("endDate", today);
		}
		querySb.append(" ORDER BY ta.createTime ASC");
		return this.genericDaoSupport.searchForList(querySb.toString(), params);
	}
	
	@Override
	public Map<String, Object> getTransferApplicationStatistics(List<TransferApplication> transferApplications){
		Map<String, Object> rtnDataMap = new HashMap<String, Object>(); 
		if(CollectionUtils.isEmpty(transferApplications)){
			return rtnDataMap;
		}
		int totalCount = transferApplications.size();
		Map<ExecutingDeductStatus, Integer> originDataMap = transferApplications.stream().collect(Collectors.groupingBy(TransferApplication::getExecutingDeductStatus, Collectors.summingInt(p->1)));
		if(! originDataMap.isEmpty()){
			for(Entry<ExecutingDeductStatus, Integer> entry : originDataMap.entrySet()){
				rtnDataMap.put(entry.getKey().toString(), totalCount == 0? BigDecimal.ZERO: new BigDecimal(entry.getValue()).divide(new BigDecimal(totalCount)));
			}
		}
		return rtnDataMap;
	}

	@Override
	public List<BigDecimal> getTotalTradingVolumeBy(String paymentChannelUuid) {
		Map<String ,Object> params = new HashMap<String , Object>();
		params.put("paymentChannelUuid", paymentChannelUuid);
		params.put("deductStatus", DeductStatus.CLEAR);
		String querySentence = "SELECT ta.amount FROM TransferApplication ta WHERE ta.deductStatus =:deductStatus AND ta.paymentChannelUuid =:paymentChannelUuid";
		return this.genericDaoSupport.searchForList(querySentence,params);
	}

	@Override
	public BigDecimal getTradingSuccessRateIn24Hours(String paymentChannelUuid) {
		Date endDate = new Date();
		Date beginDate = DateUtils.addDays(endDate, 1);
		Map<String ,Object> params = new HashMap<String , Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("paymentChannelUuid", paymentChannelUuid);
		String queryAllSentence = "from TransferApplication WHERE createTime >=:beginDate and createTime <=:endDate AND paymentChannelUuid =:paymentChannelUuid";
		int total = this.genericDaoSupport.count(queryAllSentence, params);
		if(total == 0){
			return BigDecimal.ZERO;
		}
		params.put("deductStatus", DeductStatus.CLEAR);
		String querySucessSentence = queryAllSentence + " AND deductStatus =:deductStatus";
		int successCnt = this.genericDaoSupport.count(querySucessSentence, params);
		return ( new BigDecimal(successCnt) ).divide( new BigDecimal(total) );
	}

	@Override
	public Map<String, Object> queryTransferApplicationBy(
			TransferApplicationQueryModel4TransactionDetail queryModel, Page page) {
		if(!queryModel.isValid()){
			return null;
		}
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer sentence = new StringBuffer("FROM TransferApplication ta WHERE ta.deductStatus =:deductStatus AND ta.paymentChannelUuid =:paymentChannelUuid");
		params.put("deductStatus", DeductStatus.CLEAR);
		params.put("paymentChannelUuid", queryModel.getPaymentChannelUuid());
		
		if (!StringUtils.isEmpty(queryModel.getStartDateString())) {
			sentence.append(" And ta.lastModifiedTime >= :startDate");
			params.put("startDate", DateUtils.asDay(queryModel.getStartDateString()));
		}
		if (!StringUtils.isEmpty(queryModel.getEndDateString())) {
			sentence.append(" And ta.lastModifiedTime <= :endDate");
			params.put("endDate", DateUtils.addDays(queryModel.getEndDate(), 1));
		}
		
		if(!StringUtils.isEmpty(queryModel.getTransferApplicationNo())){
			sentence.append(" And ta.transferApplicationNo =:transferApplicationNo");
			params.put("transferApplicationNo", queryModel.getTransferApplicationNo());
		}
		if("DESC".equals(queryModel.getIsAsc())){
			sentence.append(" ORDER BY ta.createTime DESC");
		}else if("ASC".equals(queryModel.getIsAsc())){
			sentence.append(" ORDER BY ta.createTime ASC");
		}
		List<TransferApplication> resultRtn = null;
		int size = 0;
		if(page == null){
			resultRtn = (List<TransferApplication>) genericDaoSupport.searchForList(sentence.toString(), params);
			size = resultRtn.size();
		}else{
			resultRtn = (List<TransferApplication>) genericDaoSupport.searchForList(sentence.toString(), params, page.getBeginIndex(), page.getEveryPage());
			size = genericDaoSupport.count(sentence.toString(), params);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("size", size);
		resultMap.put("list", resultRtn);
		return resultMap;
	}

	@Override
	public List<BigDecimal> getDebitTradingVolumeInMonths(int months, String paymentChannelUuid) {
		Date now = new Date();
		Date endTime = DateUtils.asMonth(now);
		Date serviceMonthsBefore = DateUtils.addMonths(now, -months);
		Date beginTime = DateUtils.asMonth(serviceMonthsBefore);
		String endTimeStr = DateUtils.format(DateUtils.asDay(now), "yyyy-MM")+"-01 00:00:00";
		String beginTimeStr = DateUtils.format(DateUtils.asDay(serviceMonthsBefore), "yyyy-MM")+"-01 00:00:00";
		
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer queryStringBuffer = new StringBuffer("SELECT ta FROM TransferApplication ta WHERE deductStatus =:deductStatus");
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		
		if(!StringUtils.isEmpty(paymentChannelUuid)){
			queryStringBuffer.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			params.put("paymentChannelUuid", paymentChannelUuid);
		}
		
		params.put("beginTime",beginTime);
		params.put("endTime",endTime);
		queryStringBuffer.append(" AND ta.createTime >=:beginTime AND ta.createTime <=:endTime");
		List<TransferApplication> transferApplications = this.genericDaoSupport.searchForList(queryStringBuffer.toString(), params);
		Map<String, BigDecimal> amountMap = transferApplications.parallelStream()
				.collect(Collectors.groupingBy(TransferApplication::getCreateTimeMonthStr,TreeMap::new,
						Collectors.reducing(BigDecimal.ZERO, TransferApplication::getAmount, BigDecimal::add)));
		List<BigDecimal> amountList = new ArrayList<>(amountMap.values());
		return amountList;
	}
	
	@Override
	public List<BigDecimal> getCreditTradingVolumeInMonths(int months, String paymentChannelUuid) {
		Date now = new Date();
		Date endTime = DateUtils.asMonth(now);
		Date severalMonthsBefore = DateUtils.addMonths(now, -months);
		Date beginTime = DateUtils.asMonth(severalMonthsBefore);
		String endTimeStr = DateUtils.format(DateUtils.asDay(now), "yyyy-MM")+"-01 00:00:00";
		String beginTimeStr = DateUtils.format(DateUtils.asDay(severalMonthsBefore), "yyyy-MM")+"-01 00:00:00";
		
		Map<String ,Object> params = new HashMap<String , Object>();
		StringBuffer queryStringBuffer = new StringBuffer("SELECT ta FROM TransferApplication ta WHERE ta.deductStatus =:deductStatus");
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		
		if(!StringUtils.isEmpty(paymentChannelUuid)){
			queryStringBuffer.append(" AND ta.paymentChannelUuid =:paymentChannelUuid");
			params.put("paymentChannelUuid", paymentChannelUuid);
		}
		params.put("beginTime",beginTime);
		params.put("endTime",endTime);
		queryStringBuffer.append(" AND ta.createTime >=:beginTime AND ta.createTime < :endTime");
		
		List<TransferApplication> transferApplications = this.genericDaoSupport.searchForList(queryStringBuffer.toString(), params);
		Map<String, BigDecimal> amountMap = transferApplications.stream()
				.collect(Collectors.groupingBy(TransferApplication::getCreateTimeMonthStr,TreeMap::new,
						Collectors.reducing(BigDecimal.ZERO, TransferApplication::getAmount, BigDecimal::add)));
		List<BigDecimal> amountList = new ArrayList<>(amountMap.values());
		return amountList;
	}

	@Override
	public List<TransferApplication> getTotalCreditTradingVolume() {
		Map<String ,Object> params = new HashMap<String , Object>();
		String queryString = "FROM TransferApplication WHERE deductStatus =:deductStatus";
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		// 付款的
//		return this.genericDaoSupport.searchForList(queryString, params);
		return new ArrayList<TransferApplication>();
	}

	@Override
	public List<TransferApplication> getTotalDebitTradingVolume() {
		Map<String ,Object> params = new HashMap<String , Object>();
		String queryString = "FROM TransferApplication WHERE deductStatus =:deductStatus";
		params.put("deductStatus", DeductStatus.CLEAR);//成功的
		// 收款的
		return this.genericDaoSupport.searchForList(queryString, params);
//		return new ArrayList<TransferApplication>();
	}

	@Override
	public List<TransferApplication> getTransferApplicationByRepaymentPlan(
			AssetSet repaymentPlan) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		String queryString  = "From  TransferApplication where order.assetSet.singleLoanContractNo =:repaymentPlanCode";
		params.put("repaymentPlanCode", repaymentPlan.getSingleLoanContractNo());
		return this.genericDaoSupport.searchForList(queryString, params);
	}
	
}
