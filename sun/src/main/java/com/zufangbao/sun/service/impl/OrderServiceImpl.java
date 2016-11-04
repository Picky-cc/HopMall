/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.model.GuaranteeOrderModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderQueryModel;

/**
 * @author lute
 *
 */

@Service("orderService")
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

	@SuppressWarnings("unchecked")
	@Override
	public Order getOrder(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {
			return null;
		}

		List<Order> orders = genericDaoSupport.searchForList("FROM Order order WHERE order.orderNo = :orderNo", "orderNo", orderNo);
		if (CollectionUtils.isEmpty(orders)) {
			return null;

		}
		return orders.get(0);
	}
	
	private Order queryOrderbyFieldAndId(String field, String value) {
		try {
			List<Order> orderList = query_order_list(field, value);
			if (CollectionUtils.isEmpty(orderList)) {
				return null;
			}
			return orderList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Order> query_order_list(String field, String value) {
		if (StringUtils.isEmpty(value)) {
			return Collections.emptyList();
		}
		Filter filter = new Filter();
		filter.addEquals(field, value);
		return list(Order.class, filter);
	}

	@Override
	public Order getOrderByRepaymentBillId(String repaymentBillId) {
		return queryOrderbyFieldAndId("repaymentBillId", repaymentBillId);

	}

	@Override
	public Order getOrderById(Long orderId, OrderType orderType) {
		if(orderId == null || orderType == null) {
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("id", orderId);
		filter.addEquals("orderType", orderType);
		List<Order> orders = this.list(Order.class, filter);
		if(CollectionUtils.isEmpty(orders)) {
			return null;
		}
		return orders.get(0);
	}

	/** new method begin */
	
	@Override
	public List<Order> getOrderListByAssetSetId(Long assetSetId) {
		if (assetSetId == null) {
			return Collections.emptyList();
		}
		String searchString = "From Order where assetSet.id=:assetSetId";
		return genericDaoSupport.searchForList(searchString, "assetSetId", assetSetId);
	}

	@Override
	public List<Order> getOrderListBy(AssetSet assetSet, Date date, List<OrderClearStatus> clearingStatusList) {
		if(assetSet==null && date==null && CollectionUtils.isEmpty(clearingStatusList)){
			return Collections.emptyList();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer searchStringBuffer = new StringBuffer("From Order where 1=1 ");
		if (assetSet != null) {
			searchStringBuffer.append(" AND assetSet=:assetSet ");
			params.put("assetSet", assetSet);
		}
		if (date != null) {
			searchStringBuffer.append(" AND Date(dueDate)=:date ");
			params.put("date", DateUtils.asDay(date));
		}
		if (!CollectionUtils.isEmpty(clearingStatusList)) {
			searchStringBuffer.append(" AND clearingStatus in :clearingStatusList");
			params.put("clearingStatusList", clearingStatusList);
		}

		return genericDaoSupport.searchForList(searchStringBuffer.toString(), params);
	}

	@Override
	public List<Order> getTodayOrderListBy(AssetSet assetSet) {
		if (assetSet == null) {
			return Collections.emptyList();
		}
		return getOrderListBy(assetSet, new Date(), Collections.emptyList());
	}

	@Override
	public List<Order> listOrder(OrderQueryModel orderQueryModel, int begin, int max) {
		if (orderQueryModel == null ||CollectionUtils.isEmpty(orderQueryModel.getFinancialContractIdList())) {
			return Collections.emptyList();
		}
		StringBuffer queryBuffer = new StringBuffer("From Order WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer assembledStringBuffer = asembleQueryStringBuffer(orderQueryModel, queryBuffer, params);
		if (begin == 0 && max == 0) {
			return genericDaoSupport.searchForList(assembledStringBuffer.toString(), params);
		} else {
			return genericDaoSupport.searchForList(assembledStringBuffer.toString(), params, begin, max);
		}
	}

	private StringBuffer asembleQueryStringBuffer(OrderQueryModel orderQueryModel, StringBuffer queryBuffer,
			Map<String, Object> params) {
		queryBuffer.append(" AND financialContract.id IN(:financialContractId) ");
		params.put("financialContractId", orderQueryModel.getFinancialContractIdList());
		if(orderQueryModel.getOrderTypeEnum()!=null){
			queryBuffer.append(" AND orderType=:orderType ");
			params.put("orderType", orderQueryModel.getOrderTypeEnum());
		}
		if (orderQueryModel.isExecutingSettlingStatusIntInput()) {
			queryBuffer.append(" AND executingSettlingStatus=:executingSettlingStatus ");
			params.put("executingSettlingStatus", orderQueryModel.getExecutingSettlingStatusEnum());
		}
		if (orderQueryModel.isOverDueStatusInput()) {
			if (OverDueStatus.NOT_OVERDUE == orderQueryModel.getOverDueStatusEnum()) {
				queryBuffer.append(" AND dueDate<=assetSet.assetRecycleDate ");
			} else {
				queryBuffer.append(" AND dueDate>assetSet.assetRecycleDate ");
			}
		}
		if (!StringUtils.isEmpty(orderQueryModel.getOrderNo())) {
			queryBuffer.append(" AND orderNo LIKE :orderNo ");
			params.put("orderNo", "%" + orderQueryModel.getOrderNo() + "%");
		}
		if (!StringUtils.isEmpty(orderQueryModel.getSingleLoanContractNo())) {
			queryBuffer.append(" AND assetSet.singleLoanContractNo LIKE :singleLoanContractNo ");
			params.put("singleLoanContractNo", "%" + orderQueryModel.getSingleLoanContractNo() + "%");
		}
		if (orderQueryModel.getSettlementStartDate() != null) {
			queryBuffer.append(" AND dueDate >=:settlementStartDate ");
			params.put("settlementStartDate", orderQueryModel.getSettlementStartDate());
		}
		if (orderQueryModel.getSettlementEndDate() != null) {
			queryBuffer.append(" AND dueDate <=:settlementEndDate ");
			params.put("settlementEndDate", orderQueryModel.getSettlementEndDate());
		}
		if (orderQueryModel.getAssetRecycleStartDate() != null) {
			queryBuffer.append(" AND assetSet.assetRecycleDate >=:assetRecycleStartDate ");
			params.put("assetRecycleStartDate", orderQueryModel.getAssetRecycleStartDate());
		}
		if (orderQueryModel.getAssetRecycleEndDate() != null) {
			queryBuffer.append(" AND assetSet.assetRecycleDate <=:assetRecycleEndDate ");
			params.put("assetRecycleEndDate", orderQueryModel.getAssetRecycleEndDate());
		}
		return queryBuffer.append(orderQueryModel.getOrderBySentence());
	}
	
	@Override
	public int  countOrderList(OrderQueryModel orderQueryModel){
		
		if (orderQueryModel == null ||CollectionUtils.isEmpty(orderQueryModel.getFinancialContractIdList())) {
			return 0;
		}
		StringBuffer queryBuffer = new StringBuffer("select count(0) From Order WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer assembledStringBuffer = asembleQueryStringBuffer(orderQueryModel, queryBuffer, params);
		return genericDaoSupport.searchForInt(assembledStringBuffer.toString(),params);
	}
	
	
	

	private boolean is_query_condition(String condition) {
		return !StringUtils.isEmpty(condition);
	}

	private Filter getGuranteeOrderModelFilter(GuaranteeOrderModel orderModel){
		Filter filter = new Filter();
		if (orderModel.getGuaranteeStatusEnum() != null) {
			filter.addEquals("assetSet.guaranteeStatus", orderModel.getGuaranteeStatusEnum());
		}
		if (is_query_condition(orderModel.getOrderNo())) {
			filter.addLike("orderNo", orderModel.getOrderNo());
		}
		if (is_query_condition(orderModel.getSingleLoanContractNo())) {
			filter.addLike("assetSet.singleLoanContractNo", orderModel.getSingleLoanContractNo());
		}
		if (is_query_condition(orderModel.getAppId())) {
			filter.addLike("assetSet.contract.app.appId", orderModel.getAppId());
		}
		if (is_query_condition(orderModel.getStartDate())) {
			Date startDay = DateUtils.addDays(DateUtils.asDay(orderModel.getStartDate()), -1);
			filter.addGreaterThan("assetSet.assetRecycleDate", startDay);
		}
		if (is_query_condition(orderModel.getEndDate())) {
			Date endDay = DateUtils.addDays(DateUtils.asDay(orderModel.getEndDate()), 1);
			filter.addLessThan("assetSet.assetRecycleDate", endDay);
		}
		if (is_query_condition(orderModel.getDueStartDate())) {
			Date startDay = DateUtils.addDays(DateUtils.asDay(orderModel.getDueStartDate()), -1);
			filter.addGreaterThan("dueDate", startDay);
		}
		if (is_query_condition(orderModel.getDueEndDate())) {
			Date endDay = DateUtils.addDays(DateUtils.asDay(orderModel.getDueEndDate()), 1);
			filter.addLessThan("dueDate", endDay);
		}
		filter.addEquals("orderType", OrderType.GUARANTEE);
		return filter;
	}
	
	// TODO common method
	private String getSentence(Class<?> persistentClass,Filter filter, com.demo2do.core.persistence.support.Order order){
		String orderSentence = "";
		if(order!=null){
			orderSentence = order.getSentence();
		}
		return "FROM " + ClassUtils.getShortClassName(persistentClass) + filter.getWhereSentence() + orderSentence;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getGuaranteeOrders(GuaranteeOrderModel orderModel, Page page) {
		StringBuffer sentence = new StringBuffer("From Order where orderType=:orderType");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderType", OrderType.GUARANTEE);
		if(CollectionUtils.isEmpty(orderModel.getFinancialContractIdList())){
			return Collections.emptyList();
		}else {
			sentence.append(" AND financialContract.id IN(:financialContractIdList) ");
			params.put("financialContractIdList", orderModel.getFinancialContractIdList());
		}
		if(orderModel.getGuaranteeStatusEnum() != null){
			sentence.append(" AND assetSet.guaranteeStatus=:guaranteeStatus");
			params.put("guaranteeStatus",orderModel.getGuaranteeStatusEnum());
		}
		if(!StringUtils.isEmpty(orderModel.getOrderNo())){
			sentence.append(" AND orderNo LIKE :orderNo");
			params.put("orderNo","%"+orderModel.getOrderNo()+"%");
		}
		if(!StringUtils.isEmpty(orderModel.getSingleLoanContractNo())){
			sentence.append(" AND assetSet.singleLoanContractNo LIKE :singleLoanContractNo");
			params.put("singleLoanContractNo","%"+orderModel.getSingleLoanContractNo()+"%");
		}
		if(!StringUtils.isEmpty(orderModel.getAppId())){
			sentence.append(" AND assetSet.contract.app.appId LIKE :appId");
			params.put("appId","%"+orderModel.getAppId()+"%");
		}
		if(orderModel.getStartDate_date()!=null){
			sentence.append(" AND assetSet.assetRecycleDate >= :startDate");
			params.put("startDate",orderModel.getStartDate_date());
		}
		if(orderModel.getEndDate_date()!=null){
			sentence.append(" AND assetSet.assetRecycleDate <= :EndDate");
			params.put("EndDate",orderModel.getEndDate_date());
		}
		if(orderModel.getDueStartDate_date()!=null){
			sentence.append(" AND dueDate >=:dueStartDate");
			params.put("dueStartDate",orderModel.getDueStartDate_date());
		}
		if(orderModel.getDueEndDate_date()!=null){
			sentence.append(" AND dueDate <=:dueEndDate");
			params.put("dueEndDate",orderModel.getDueEndDate_date());
		}
		
		sentence.append(" order by modifyTime desc ");

		if(page==null){
			return genericDaoSupport.searchForList(sentence.toString(),params);
		}else {
			return genericDaoSupport.searchForList(sentence.toString(),params,page.getBeginIndex(),page.getEveryPage());
		}
	}
	
	@Override
	public List<Order> listOrder(List<String> repaymentUuidList, OrderType orderType) {
		if (CollectionUtils.isEmpty(repaymentUuidList) || orderType == null) {
			return Collections.emptyList();
		}
		String querySentence = "From Order where repaymentBillId IN (:repaymentUuidList) AND orderType =:orderType ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("repaymentUuidList", repaymentUuidList);
		params.put("orderType", orderType);

		return genericDaoSupport.searchForList(querySentence, params);
	}

	@Override
	public Long getCompanyId(Order order){
		if(order==null) return null;
		FinancialContract financialContract = order.getFinancialContract();
		if(financialContract==null) return null;
		Company company = financialContract.getCompany();
		if(company==null) return null;
		return company.getId();
	}
	
	@Override
	public Order getGuranteeOrder(AssetSet assetSet) {
		return getGuranteeOrder(assetSet, OrderType.GUARANTEE);
	}
	
	@Override
	public int countGuranteeOrders(List<Long> financialContractIds, GuaranteeStatus guaranteeStatus){
		if(CollectionUtils.isEmpty(financialContractIds)){
			return 0;
		}
		String querySentence = "From Order where financialContract.id IN(:financialContractIds) AND orderType =:orderType "
				+ " AND assetSet.guaranteeStatus=:guaranteeStatus";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("financialContractIds", financialContractIds);
		params.put("guaranteeStatus", guaranteeStatus);
		params.put("orderType", OrderType.GUARANTEE);
		return genericDaoSupport.count(querySentence, params);
	}

	private Order getGuranteeOrder(AssetSet assetSet, OrderType orderType) {
		if(assetSet == null) {
			return null;
		}
		String querySentence = "From Order where assetSet =:assetSet AND orderType =:orderType ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetSet", assetSet);
		params.put("orderType", orderType);
		List<Order> result = genericDaoSupport.searchForList(querySentence, params);
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	/** new method end */

	@Override
	public List<Order> getOrderListBy(ContractAccount contractAccount) {
		if(null == contractAccount || null == contractAccount.getContract() || null == contractAccount.getContract().getCustomer()) {
			return Collections.emptyList();
		}
		Filter filter = new Filter();
		filter.addEquals("clearingStatus", OrderClearStatus.UNCLEAR);
		filter.addEquals("customer", contractAccount.getContract().getCustomer());
		filter.addEquals("dueDate", DateUtils.asDay(new Date()));
		filter.addEquals("orderType", OrderType.NORMAL);
		return this.list(Order.class, filter);
	}

	@Override
	public boolean exist_not_completed_normal_order(Long assetSetId) {
		String querySentence = "SELECT COUNT(id) FROM TransferApplication WHERE order.assetSet.id =:assetSetId AND executingDeductStatus IN(:executingDeductStatus)";
		
		List<ExecutingDeductStatus> executingDeductStatus = new ArrayList<ExecutingDeductStatus>();
		executingDeductStatus.add(ExecutingDeductStatus.DOING);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("assetSetId", assetSetId);
		parameters.put("executingDeductStatus", executingDeductStatus);
		
		return genericDaoSupport.searchForInt(querySentence, parameters) > 0;
	}
	
	@Override
	public boolean exist_today_normal_order(Long assetSetId, Date date) {
		String querySentence = "SELECT COUNT(id) FROM Order WHERE assetSet.id =:assetSetId AND dueDate =:dueDate AND orderType =:orderType";
		
		Date dateAsDay = DateUtils.asDay(date);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("assetSetId", assetSetId);
		parameters.put("orderType", OrderType.NORMAL);
		parameters.put("dueDate", dateAsDay);
		
		return genericDaoSupport.searchForInt(querySentence, parameters) > 0;
	}

	@Override
	public List<Order> getOrderListExceptLapsedGuarantee(OrderMatchQueryModel orderMatchQueryModel) {
		if (orderMatchQueryModel == null) {
			return Collections.emptyList();
		}
		StringBuffer queryBuffer = new StringBuffer("From Order WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(orderMatchQueryModel.getOrderTypeEnum()!=null){
			queryBuffer.append(" AND orderType=:orderType ");
			params.put("orderType", orderMatchQueryModel.getOrderTypeEnum());
		}
		if(orderMatchQueryModel.getOrderTypeEnum()!=OrderType.NORMAL){
			queryBuffer.append(" AND ((orderType=:guaranteeOrderType AND assetSet.guaranteeStatus!=:lapseGauranteeStatus) OR orderType!=:guaranteeOrderType) ");
			params.put("guaranteeOrderType", OrderType.GUARANTEE);
			params.put("lapseGauranteeStatus", GuaranteeStatus.LAPSE_GUARANTEE);
		}
		if(!StringUtils.isEmpty(orderMatchQueryModel.getOrderNo())){
			queryBuffer.append(" AND orderNo LIKE :orderNo ");
			params.put("orderNo", "%"+orderMatchQueryModel.getOrderNo()+"%");
		}
		if(!StringUtils.isEmpty(orderMatchQueryModel.getCustomerName())){
			queryBuffer.append(" AND customer.name LIKE :customerName ");
			params.put("customerName", "%"+orderMatchQueryModel.getCustomerName()+"%");
		}
		if(orderMatchQueryModel.getAssetRecycleStartDate()!=null){
			queryBuffer.append(" AND assetSet.assetRecycleDate >=:assetRecycleStartDate ");
			params.put("assetRecycleStartDate", orderMatchQueryModel.getAssetRecycleStartDate());
		}
		if(orderMatchQueryModel.getAssetRecycleEndDate()!=null){
			queryBuffer.append(" AND assetSet.assetRecycleDate <=:assetRecycleEndDate ");
			params.put("assetRecycleEndDate", orderMatchQueryModel.getAssetRecycleEndDate());
		}
		queryBuffer.append(" order by modifyTime DESC ");
		return genericDaoSupport.searchForList(queryBuffer.toString(), params);
	}

	@Override
	public Order getLastUnclearedRepaymentOrder(AssetSet assetSet) {
		if(assetSet==null){
			return null;
		}
		String querySentence = "From Order repaymentOrder where orderType=:normal and clearingStatus=:unclear and assetSet=:assetSet order by dueDate DESC ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("normal", OrderType.NORMAL);
		params.put("unclear", OrderClearStatus.UNCLEAR);
		params.put("assetSet", assetSet);
		List<Order> orderList =  genericDaoSupport.searchForList(querySentence,params);
		if(CollectionUtils.isEmpty(orderList)){
			return null;
		}
		return orderList.get(0);
	}
	
	public List<Order> getSettlementStatementInNormalProcessing() {
		return getTodayIncompleteSettementStatementBy(AssetSetActiveStatus.OPEN);
	}
	
	@Override
	public List<Order> getSettlementStatementInPrepaymentProcessing() {
		return getTodayIncompleteSettementStatementBy(AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
	}
	

	@Override
	public List<Order> getAllSettlementStatementInProcessing() {
		return getTodayIncompleteSettementStatementBy(AssetSetActiveStatus.OPEN, AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getTodayIncompleteSettementStatementBy(AssetSetActiveStatus... assetSetActiveStatusList) {
		if(ArrayUtils.isEmpty(assetSetActiveStatusList)) {
			return Collections.emptyList();
		}
		String querySentence = "From Order where Date(dueDate) =:dueDate AND clearingStatus=:clearingStatus AND executingSettlingStatus IN(:executingSettlingStatusList) AND orderType=:orderType AND assetSet.activeStatus IN(:activeStatusList)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clearingStatus", OrderClearStatus.UNCLEAR);
		List<ExecutingSettlingStatus> executingSettlingStatusList = new ArrayList<ExecutingSettlingStatus>();
		executingSettlingStatusList.add(ExecutingSettlingStatus.CREATE);
		executingSettlingStatusList.add(ExecutingSettlingStatus.DOING);
		params.put("executingSettlingStatusList", executingSettlingStatusList);
		params.put("dueDate", DateUtils.asDay(new Date()));
		params.put("orderType", OrderType.NORMAL);
		params.put("activeStatusList", Arrays.asList(assetSetActiveStatusList));
		return genericDaoSupport.searchForList(querySentence,params);
	}

	@Override
	public boolean existsSuccessOrDoingSettlementStatement(
			AssetSet repaymentPlan) {
		String hql = "FROM Order WHERE assetSet=:assetSet AND executingSettlingStatus IN(:executingSettlingStatusList) AND orderType=:orderType";
		Map<String, Object> params = new HashMap<String, Object>();
		List<ExecutingSettlingStatus> executingSettlingStatusList = new ArrayList<ExecutingSettlingStatus>();
		executingSettlingStatusList.add(ExecutingSettlingStatus.SUCCESS);
		executingSettlingStatusList.add(ExecutingSettlingStatus.DOING);
		params.put("executingSettlingStatusList", executingSettlingStatusList);
		params.put("orderType", OrderType.NORMAL);
		params.put("assetSet", repaymentPlan);
		return this.genericDaoSupport.count(hql, params) > 0;
	}
	
	@Override
	public List<Order> getUnclearedRepaymentOrder(Long contractId) {
		String querySentence = "From Order where ((orderType=:normal) or (orderType=:guaranteeOrderType and assetSet.guaranteeStatus=:waitingGurarantee)) and clearingStatus=:unclear and assetSet.contract.id=:contractId order by dueDate DESC ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("normal", OrderType.NORMAL);
		params.put("unclear", OrderClearStatus.UNCLEAR);
		params.put("contractId", contractId);
		params.put("guaranteeOrderType",OrderType.GUARANTEE);
		params.put("waitingGurarantee", GuaranteeStatus.WAITING_GUARANTEE);
		return genericDaoSupport.searchForList(querySentence,params);
	}

	@Override
	public List<Order> get_repayment_order_not_in_process_and_asset_not_clear(Contract contract, Date dueDate, AssetSetActiveStatus assetSetActiveStatus) {
		if(contract==null){
			return null;
		}
		String querySentence = "From Order o where orderType=:orderType and executingSettlingStatus IN(:executingSettlingStatusList) "
				+ " AND dueDate=:dueDate "
				+ " AND assetSet.activeStatus=:activeStatus AND assetSet.contract=:contract AND assetSet.assetStatus=:assetUnclearStatus"
				+ " order by dueDate DESC ";
		List<ExecutingSettlingStatus> executingSettlingStatusList= new ArrayList<ExecutingSettlingStatus>();
		executingSettlingStatusList.add(ExecutingSettlingStatus.CREATE);
		executingSettlingStatusList.add(ExecutingSettlingStatus.FAIL);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderType", OrderType.NORMAL);
		params.put("assetUnclearStatus", AssetClearStatus.UNCLEAR);
		params.put("executingSettlingStatusList", executingSettlingStatusList);
		params.put("dueDate", DateUtils.asDay(dueDate));
		params.put("contract", contract);
		params.put("activeStatus", assetSetActiveStatus);
		
		return genericDaoSupport.searchForList(querySentence,params);
		
	}
	
	@Override
	public Order getRepaymentOrder(AssetSet assetSet, Date date){
		String querySentence = "From Order where orderType=:normal and assetSet=:assetSet AND Date(dueDate)=:today ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("normal", OrderType.NORMAL);
		params.put("assetSet", assetSet);
		params.put("today", DateUtils.asDay(date));
		List<Order> orderList =  genericDaoSupport.searchForList(querySentence,params);
		if(CollectionUtils.isEmpty(orderList)){
			return null;
		}
		return orderList.get(0);
	}
}

