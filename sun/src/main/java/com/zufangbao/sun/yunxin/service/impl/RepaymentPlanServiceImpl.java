package com.zufangbao.sun.yunxin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OnAccountStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncStatus;

@Service("RepaymentPlanService")
public class RepaymentPlanServiceImpl extends GenericServiceImpl<AssetSet> implements RepaymentPlanService{

	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;

	private static final Log logger = LogFactory.getLog(RepaymentPlanServiceImpl.class);
	
	@Override
	public List<AssetSet> getRepaymentPlansByContractAndActiveStatus(
			Contract contract, AssetSetActiveStatus activeStatus) {
		if(contract == null) {
			return Collections.emptyList();
		}
		Filter filter = new Filter();
		filter.addEquals("contract", contract);
		filter.addEquals("activeStatus", activeStatus);
		Order order = new Order();
		order.add("currentPeriod", "ASC");
		return this.list(AssetSet.class, filter, order);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date getMaxAssetRecycleDateInActiveAsset(Contract contract) {

		String querySentence = new String(
				"select max(a.assetRecycleDate)  from AssetSet a where a.contract.id = :contractId and a.activeStatus=:activeStatus ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractId", contract.getId());
		params.put("activeStatus", AssetSetActiveStatus.OPEN);
		List<Date> results = this.genericDaoSupport.searchForList(querySentence,params);
		if(CollectionUtils.isEmpty(results)){
			return null;
		}
		return results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> get_all_receivable_unclear_asset_set_list(
			Date date) {
		String queryString = "SELECT asst.id FROM AssetSet asst WHERE asst.assetRecycleDate <=:assetRecycleDate AND asst.assetStatus =:assetStatus AND asst.activeStatus IN(:activeStatusList)";
		Map<String, Object> parameters = new HashMap<>();
		Date dateAsDay = DateUtils.asDay(date);
		parameters.put("assetRecycleDate", dateAsDay);
		parameters.put("assetStatus", AssetClearStatus.UNCLEAR);
		parameters.put("orderType", OrderType.NORMAL);
		
		List<AssetSetActiveStatus> activeStatusList = new ArrayList<AssetSetActiveStatus>();
		activeStatusList.add(AssetSetActiveStatus.OPEN);
		//扩展查询，提前还款待处理的还款计划
		activeStatusList.add(AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
		
		parameters.put("activeStatusList", activeStatusList);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> loadAllNeedGuaranteeAssetSetList() {
		String queryString = "From AssetSet where Date(assetRecycleDate) < Date(NOW()) And assetStatus =:assetStatus AND guaranteeStatus = :guaranteeStatus AND activeStatus=:activeStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetStatus", AssetClearStatus.UNCLEAR);
		parameters.put("guaranteeStatus", GuaranteeStatus.NOT_OCCURRED);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> loadAllNeedSettlementAssetSetList() {
		String queryString = "From AssetSet where Date(assetRecycleDate) < Date(NOW()) And Date(assetRecycleDate)<Date(actualRecycleDate) And assetStatus =:assetStatus And settlementStatus =:settlementStatus AND guaranteeStatus = :guaranteeStatus AND activeStatus=:activeStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetStatus", AssetClearStatus.CLEAR);
		parameters.put("settlementStatus", SettlementStatus.NOT_OCCURRED);
		parameters.put("guaranteeStatus", GuaranteeStatus.HAS_GUARANTEE);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public Integer getMaxVersionNo(Contract contract) {
		if(contract==null){
			return null;
		}
		String queryString = "select max(versionNo) from AssetSet where contract =:contract";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("contract", contract);
		List<Integer> versionList = this.genericDaoSupport.searchForList(queryString, parameters);
		if(CollectionUtils.isEmpty(versionList)){
			return null;
		}
		Integer maxVersionNoInAssets = versionList.get(0);
		if(maxVersionNoInAssets==null){
			return null;
		}
		return maxVersionNoInAssets>contract.getActiveVersionNo()?maxVersionNoInAssets:contract.getActiveVersionNo();
	}

	@Override
	public void invalidateAssets(List<AssetSet> assetSetList) {
		for (AssetSet assetSet : assetSetList) {
			if(assetSet==null) continue;
			assetSet.setActiveStatus(AssetSetActiveStatus.INVALID);
			this.saveOrUpdate(assetSet);
			write_off_assets(assetSet);
		}
		
	}
	
	private void write_off_assets(AssetSet assetSet){
		try {
			if(assetSet==null) return;
			LedgerBook book = ledgerBookService.getBookByAsset(assetSet);
			AssetCategory assetCateGory = AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
			ledgerBookHandler.write_off_asset_set(book, assetCateGory,"","","");
		} catch(Exception e){
			logger.error("write_off_assets occur error.");
			e.printStackTrace();
			
		}
	}
	
	@Override
	public int countAssetSetByQueryModel(AssetSetQueryModel assetSetQueryModel) {
		queryAndSetFinancialContractsMap(assetSetQueryModel);

		if(assetSetQueryModel.is_illegal_query_condition()){
			return 0;
		}
			
		String queryString = "select count(id) from AssetSet where contract.financialContractUuid IN (:financialContractUuids) And overdueStatus IN (:overdueStatusList)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuids", assetSetQueryModel.getFinancialContractUuids());
		parameters.put("overdueStatusList", assetSetQueryModel.getAuditOverDueStatusEnumList());
		queryString +=generateSql(assetSetQueryModel.getPaymentStatusEnumList(), parameters);
		
		if (!StringUtils.isEmpty(assetSetQueryModel.getSingleLoanContractNo())) {
			queryString += " And singleLoanContractNo like :singleLoanContractNo";
			parameters.put("singleLoanContractNo",
					"%" + assetSetQueryModel.getSingleLoanContractNo() + "%");
		}
		if (!StringUtils.isEmpty(assetSetQueryModel.getContractNo())) {
			queryString += " And contract.contractNo like :contractNo";
			parameters.put("contractNo",
					"%" + assetSetQueryModel.getContractNo() + "%");
		}
		if (assetSetQueryModel.getStartDateValue() != null) {
			queryString += " And assetRecycleDate >=:startDate";
			parameters.put("startDate", assetSetQueryModel.getStartDateValue());
		}
		if (assetSetQueryModel.getEndDateValue() != null) {
			queryString += " And assetRecycleDate <=:endDate";
			parameters.put("endDate", assetSetQueryModel.getEndDateValue());
		}
		if (assetSetQueryModel.getOverDueStatusEnum() != null) {
			String date_operator = create_asset_recycle_date_query_operator(assetSetQueryModel);
			queryString += " And ((assetStatus =:unclear AND assetRecycleDate"+date_operator+":today) OR ( assetStatus =:clear AND assetRecycleDate"+date_operator+"Date(actualRecycleDate)))";
			parameters.put("today", DateUtils.asDay(new Date()));
			parameters.put("unclear", AssetClearStatus.UNCLEAR);
			parameters.put("clear", AssetClearStatus.CLEAR);
		}
		if (!StringUtils.isEmpty(assetSetQueryModel.getCustomerName())) {
			queryString += " And contract.customer.name LIKE :customerName";
			parameters.put("customerName",
					"%" + assetSetQueryModel.getCustomerName() + "%");
		}

		if (assetSetQueryModel.getActiveStatusEnum()!=null) {
			queryString += " And activeStatus=:activeStatus";
			parameters.put("activeStatus",assetSetQueryModel.getActiveStatusEnum());
		}
		return this.genericDaoSupport.searchForInt(queryString, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> queryAssetSetIdsByQueryModel(AssetSetQueryModel assetSetQueryModel, Page page) {
		queryAndSetFinancialContractsMap(assetSetQueryModel);
		
		if(assetSetQueryModel.is_illegal_query_condition()){
			return Collections.emptyList();
		}
			
		String queryString = "FROM AssetSet where contract.financialContractUuid IN (:financialContractUuids) And overdueStatus IN (:overdueStatusList)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuids", assetSetQueryModel.getFinancialContractUuids());
		parameters.put("overdueStatusList", assetSetQueryModel.getAuditOverDueStatusEnumList());
		queryString +=generateSql(assetSetQueryModel.getPaymentStatusEnumList(), parameters);
		
		if (!StringUtils.isEmpty(assetSetQueryModel.getSingleLoanContractNo())) {
			queryString += " And singleLoanContractNo like :singleLoanContractNo";
			parameters.put("singleLoanContractNo",
					"%" + assetSetQueryModel.getSingleLoanContractNo() + "%");
		}
		if (!StringUtils.isEmpty(assetSetQueryModel.getContractNo())) {
			queryString += " And contract.contractNo like :contractNo";
			parameters.put("contractNo",
					"%" + assetSetQueryModel.getContractNo() + "%");
		}
		if (assetSetQueryModel.getStartDateValue() != null) {
			queryString += " And assetRecycleDate >=:startDate";
			parameters.put("startDate", assetSetQueryModel.getStartDateValue());
		}
		if (assetSetQueryModel.getEndDateValue() != null) {
			queryString += " And assetRecycleDate <=:endDate";
			parameters.put("endDate", assetSetQueryModel.getEndDateValue());
		}
		if (assetSetQueryModel.getOverDueStatusEnum() != null) {
			String date_operator = create_asset_recycle_date_query_operator(assetSetQueryModel);
			queryString += " And ((assetStatus =:unclear AND assetRecycleDate"+date_operator+":today) OR ( assetStatus =:clear AND assetRecycleDate"+date_operator+"Date(actualRecycleDate)))";
			parameters.put("today", DateUtils.asDay(new Date()));
			parameters.put("unclear", AssetClearStatus.UNCLEAR);
			parameters.put("clear", AssetClearStatus.CLEAR);
		}
		if (!StringUtils.isEmpty(assetSetQueryModel.getCustomerName())) {
			queryString += " And contract.customer.name LIKE :customerName";
			parameters.put("customerName",
					"%" + assetSetQueryModel.getCustomerName() + "%");
		}

		if (assetSetQueryModel.getActiveStatusEnum()!=null) {
			queryString += " And activeStatus=:activeStatus";
			parameters.put("activeStatus",assetSetQueryModel.getActiveStatusEnum());
		}
		queryString += " order by contract.id , assetRecycleDate ";
		if (page == null) {
			return this.genericDaoSupport.searchForList(queryString, parameters);
		} else {
			return this.genericDaoSupport.searchForList(queryString, parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private void queryAndSetFinancialContractsMap(AssetSetQueryModel queryModel) {
		List<FinancialContract> financialContracts = financialContractService.getFinancialContractsByIds(queryModel.getFinancialContractIdList());
		Map<String, FinancialContract> financialContractsMap = financialContracts.stream().collect(Collectors.toMap(FinancialContract::getFinancialContractUuid, fc -> fc));
		queryModel.setFinancialContractsMap(financialContractsMap);
	}

	private String create_asset_recycle_date_query_operator(
			AssetSetQueryModel assetSetQueryModel) {
		if (assetSetQueryModel.getOverDueStatusEnum() == OverDueStatus.NOT_OVERDUE) {
			 return ">=";
		} else {
			 return "<";
		}
	}
	
	private String generateSql(PaymentStatus paymentStatus, Map<String,Object> params){
		params.put("assetUnclearStatus", AssetClearStatus.UNCLEAR);
		params.put("assetClearStatus", AssetClearStatus.CLEAR);
		params.put("assetRecycleDate", DateUtils.asDay(new Date()));
		switch (paymentStatus) {
		case DEFAULT:
			return " (assetStatus = :assetUnclearStatus And assetRecycleDate > :assetRecycleDate) ";
		case PROCESSING:
			return " (assetStatus = :assetUnclearStatus And assetRecycleDate = :assetRecycleDate) ";
		case UNUSUAL:
			return " (assetStatus = :assetUnclearStatus And assetRecycleDate < :assetRecycleDate) ";
		case SUCCESS:
			return " (assetStatus = :assetClearStatus) ";
		default:
			break;
		}
		return "";
	}

	private String generateSql(List<PaymentStatus> paymentStatusList, Map<String,Object> params){
		if(CollectionUtils.isEmpty(paymentStatusList)){
			return "";
		}
		String totalSql = paymentStatusList.stream().filter(paymentStatus->paymentStatus!=null).
				map(paymentStatus->generateSql(paymentStatus, params)).
				filter(sql->!StringUtils.isEmpty(sql)).collect(Collectors.joining(" OR "));
		if(!StringUtils.isEmpty(totalSql)){
			return " AND ("+totalSql+")";
		}
		return "";
		
	}
	
	@Override
	public void updateSettlementStatusAndSave(AssetSet assetSet) {
		assetSet.setSettlementStatus(SettlementStatus.CREATE);
		assetSet.setLastModifiedTime(new Date());
		this.save(assetSet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> get_all_needs_remind_assetSet_list(int remindDay) {
		String queryString = "From AssetSet where Date(assetRecycleDate) = Date(:assetRecycleDate) AND assetStatus =:assetStatus AND activeStatus =:activeStatus";
		Date assetRecycleDate = DateUtils.addDays(new Date(), remindDay);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetRecycleDate", DateUtils.asDay(assetRecycleDate));
		parameters.put("assetStatus", AssetClearStatus.UNCLEAR);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> getOverGracePeriodNoRepayAssetSets(Contract contract,
			FinancialContract financialContract, Date currentDate) {
		String hql = "From AssetSet where assetRecycleDate <:currentDate AND assetStatus =:assetStatus AND contract=:contract AND activeStatus =:activeStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("currentDate", currentDate);
		parameters.put("assetStatus", AssetClearStatus.UNCLEAR);
		parameters.put("contract", contract);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		List<AssetSet> allOverdueUnclearAssetSets = this.genericDaoSupport
				.searchForList(hql, parameters);

		int loanOverdueStartDay = financialContract.getLoanOverdueStartDay();

		List<AssetSet> result = new ArrayList<AssetSet>();
		for (AssetSet assetSet : allOverdueUnclearAssetSets) {
			int distanceDay = DateUtils.distanceDaysBetween(assetSet.getAssetRecycleDate(), currentDate);
			if (distanceDay >= loanOverdueStartDay) {
				result.add(assetSet);
			}
		}
		return result;
	}

	@Override
	public List<AssetSet> get_all_unclear_and_open_asset_set_list(Contract contract) {
		Filter filter = new Filter();
		filter.addEquals("contract", contract);
		filter.addEquals("assetStatus", AssetClearStatus.UNCLEAR);
		filter.addEquals("activeStatus", AssetSetActiveStatus.OPEN);
		Order order = new Order("currentPeriod", "asc");
		return list(AssetSet.class, filter, order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal calculateBeginningPaid(FinancialContract financialContract, Date startDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "select sum(assetPrincipalValue) from AssetSet where contract.financialContractUuid =:financialContractUuid"
				+ " and assetStatus = :assetStatus"
				+ " and activeStatus = :activeStatus"
				+ " and actualRecycleDate < :actualRecycleDate";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("assetStatus", AssetClearStatus.CLEAR);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("actualRecycleDate", startDate);
		List<BigDecimal> result = this.genericDaoSupport.searchForList(queryString, parameters);
		return processStatResult(result);
	}

	private BigDecimal processStatResult(List<BigDecimal> result) {
		if(CollectionUtils.isEmpty(result) || result.get(0) == null) {
			return BigDecimal.ZERO;
		}
		return (BigDecimal) result.get(0);
	}

	@SuppressWarnings("unchecked")
	private BigDecimal searchBigDecimalResult(String queryString, Map<String, Object> parameters) {
		List<BigDecimal> result = this.genericDaoSupport.searchForList(queryString, parameters);
		return processStatResult(result);
	}

	@Override
	public BigDecimal calculateReduceLoansPrincipal(FinancialContract financialContract, Date startDate, Date endDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "select sum(assetPrincipalValue) from AssetSet where contract.financialContractUuid =:financialContractUuid"
				+ " and assetStatus = :assetStatus"
				+ " and activeStatus = :activeStatus"
				+ " and actualRecycleDate >= :startDate"
				+ " and actualRecycleDate < :endDate";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("assetStatus", AssetClearStatus.CLEAR);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		return searchBigDecimalResult(queryString, parameters);
	}

	@Override
	public BigDecimal calculateBeginningAmountInterest(FinancialContract financialContract, Date startDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "SELECT sum(assetInterestValue) FROM AssetSet WHERE contract.financialContractUuid =:financialContractUuid "
				+ " AND contract.beginDate < :startDate "
				+ " AND activeStatus = :activeStatus ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("startDate", startDate);
		return searchBigDecimalResult(queryString, parameters);
	}

	@Override
	public BigDecimal calculateBeginningPaidInterest(FinancialContract financialContract, Date startDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "SELECT sum(assetInterestValue) FROM AssetSet WHERE contract.financialContractUuid =:financialContractUuid "
				+ " AND contract.beginDate < :startDate "
				+ " AND activeStatus = :activeStatus "
				+ " AND actualRecycleDate < :startDate "
				+ " AND assetStatus = :assetStatus ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("assetStatus", AssetClearStatus.CLEAR);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("startDate", startDate);
		return searchBigDecimalResult(queryString, parameters);
	}

	@Override
	public BigDecimal calculateBeginningInterest(FinancialContract financialContract, Date startDate) {
		BigDecimal beginningAmountInterest = calculateBeginningAmountInterest(financialContract, startDate);
		BigDecimal beginningPaidInterest = calculateBeginningPaidInterest(financialContract, startDate);
		return beginningAmountInterest.subtract(beginningPaidInterest);
	}

	@Override
	public BigDecimal calculateNewInterest(FinancialContract financialContract, Date startDate, Date endDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "SELECT sum(assetInterestValue) FROM AssetSet WHERE contract.financialContractUuid =:financialContractUuid "
				+ " AND contract.beginDate >= :startDate "
				+ " AND contract.beginDate < :endDate "
				+ " AND activeStatus = :activeStatus ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		return searchBigDecimalResult(queryString, parameters);
	}

	@Override
	public BigDecimal calculateReduceInterest(FinancialContract financialContract, Date startDate, Date endDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String queryString = "SELECT sum(assetInterestValue) FROM AssetSet WHERE contract.financialContractUuid =:financialContractUuid "
				+ " AND assetStatus = :assetStatus"
				+ " AND activeStatus = :activeStatus"
				+ " AND actualRecycleDate >= :startDate"
				+ " AND actualRecycleDate < :endDate";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("financialContractUuid", financialContract.getFinancialContractUuid());
		parameters.put("assetStatus", AssetClearStatus.CLEAR);
		parameters.put("activeStatus", AssetSetActiveStatus.OPEN);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		return searchBigDecimalResult(queryString, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPrepaymentSuccessRepaymentPlanUuids() {
		String queryString = "SELECT assetUuid FROM AssetSet WHERE activeStatus =:activeStatus AND assetStatus =:assetStatus AND onAccountStatus =:onAccountStatus";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activeStatus", AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
		params.put("assetStatus", AssetClearStatus.CLEAR);
		params.put("onAccountStatus", OnAccountStatus.WRITE_OFF);
		return this.genericDaoSupport.searchForList(queryString, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssetSet getUniqueRepaymentPlanByUuid(String repaymentPlanUuid) {
		if(StringUtils.isEmpty(repaymentPlanUuid)) {
			return null;
		}
		
		String hql = "FROM AssetSet WHERE assetUuid =:repaymentPlanUuid";
		List<AssetSet> repaymentPlanList = this.genericDaoSupport.searchForList(hql, "repaymentPlanUuid", repaymentPlanUuid);
		if(CollectionUtils.isNotEmpty(repaymentPlanList)) {
			return repaymentPlanList.get(0);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AssetSet getRepaymentPlanByRepaymentCode(String repaymentPlanCode){
		if(StringUtils.isEmpty(repaymentPlanCode)) {
			return null;
		}
		String hql = "FROM AssetSet WHERE singleLoanContractNo =:repaymentPlanCode";
		List<AssetSet> repaymentPlanList = this.genericDaoSupport.searchForList(hql, "repaymentPlanCode",repaymentPlanCode);
		if(CollectionUtils.isNotEmpty(repaymentPlanList)) {
			return repaymentPlanList.get(0);
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public AssetSet getRepaymentPlanByRepaymentCodeNotInVaild(String repaymentPlanCode){
		if(StringUtils.isEmpty(repaymentPlanCode)) {
			return null;
		}
		String hql = "FROM AssetSet WHERE singleLoanContractNo =:repaymentPlanCode AND activeStatus !=:activeStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("repaymentPlanCode", repaymentPlanCode);
		params.put("activeStatus", AssetSetActiveStatus.INVALID);
		List<AssetSet> repaymentPlanList = this.genericDaoSupport.searchForList(hql, params);
		if(CollectionUtils.isNotEmpty(repaymentPlanList)) {
			return repaymentPlanList.get(0);
		}
		
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> getExpiredUnclearPrepaymentRepaymentPlanList() {
		String hql = "FROM AssetSet WHERE activeStatus=:activeStatus AND assetStatus=:assetStatus AND assetRecycleDate <:today";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activeStatus", AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
		params.put("today", DateUtils.getToday());
		params.put("assetStatus", AssetClearStatus.UNCLEAR);
		return this.genericDaoSupport.searchForList(hql, params);
	}
	
	@Override
	public int countAssets(List<String> financialContractUuids,AssetSetActiveStatus activeStatus,List<PaymentStatus> paymentStatusList, AuditOverdueStatus auditOverdueStatus){
		if(CollectionUtils.isEmpty(financialContractUuids)){
			return 0;
		}
		
		String queryString = "FROM AssetSet where contract.financialContractUuid IN(:financialContractUuids) AND activeStatus=:activeStatus AND overdueStatus=:overdueStatus";
		Map<String, Object> params = new HashMap<>();
		params.put("financialContractUuids", financialContractUuids);
		params.put("activeStatus", activeStatus);
		params.put("overdueStatus", auditOverdueStatus);
		queryString += generateSql(paymentStatusList, params);
		return genericDaoSupport.count(queryString, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssetSet getRepaymentPlanByRepaymentPlanNo(String repaymentPlanNo) {
		if(StringUtils.isEmpty(repaymentPlanNo)) {
			return null;
		}
		String hql = "FROM AssetSet WHERE singleLoanContractNo =:singleLoanContractNo AND activeStatus <> :activeStatus";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("singleLoanContractNo", repaymentPlanNo);
		params.put("activeStatus", AssetSetActiveStatus.INVALID);
		List<AssetSet> repaymentPlanList = this.genericDaoSupport.searchForList(hql, params);
		if(CollectionUtils.isNotEmpty(repaymentPlanList)) {
			return repaymentPlanList.get(0);
		}
		return null;
	}
	
	@Override
	public AssetSet getFirstUnClearAsset(Contract contract) {
		if(contract==null){
			return null;
		}
		String queryString = "From AssetSet where assetStatus=:unclear and contract=:contract AND activeStatus=:activeStatus order by assetRecycleDate ASC";
		Map<String, Object> params = new HashMap<>();
		params.put("unclear", AssetClearStatus.UNCLEAR);
		params.put("contract", contract);
		params.put("activeStatus", AssetSetActiveStatus.OPEN);
		List<AssetSet> assetSetList = this.genericDaoSupport.searchForList(queryString, params);
		if(CollectionUtils.isEmpty(assetSetList)){
			return null;
		}
		return assetSetList.get(0);
	}

	@Override
	public BigDecimal get_the_outstanding_principal_amount_of_contract(Contract contract, Date calculateStartDate) {
		if(contract==null){
			return BigDecimal.ZERO;
		}
		String sentence = "SELECT sum(a.assetPrincipalValue) From AssetSet a where a.contract=:contract AND a.assetStatus=:unclear AND a.assetRecycleDate >= Date(:assetRecycleDate) AND a.activeStatus =:activeStatus order by currentPeriod ASC";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contract", contract);
		params.put("unclear", AssetClearStatus.UNCLEAR);
		params.put("assetRecycleDate", calculateStartDate);
		params.put("activeStatus", AssetSetActiveStatus.OPEN);
		return searchBigDecimalResult(sentence, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSet> get_unclear_asset_set_list(Contract contract, boolean isIncludeToday) {
		if(contract == null) {
			return Collections.emptyList();
		}
		String sql = "FROM AssetSet Where contract = :contract AND assetStatus = :assetStatus AND activeStatus = :activeStatus AND assetRecycleDate > Date(:assetRecycleDate) ORDER BY currentPeriod ASC";
		if(isIncludeToday) {
			   sql = "FROM AssetSet Where contract = :contract AND assetStatus = :assetStatus AND activeStatus = :activeStatus AND assetRecycleDate>= Date(:assetRecycleDate) ORDER BY currentPeriod ASC";
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contract", contract);
		params.put("assetStatus", AssetClearStatus.UNCLEAR);
		params.put("activeStatus", AssetSetActiveStatus.OPEN);
		params.put("assetRecycleDate", DateUtils.getToday());
		return genericDaoSupport.searchForList(sql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean is_repaymentPlan_belong_contract(List<String> repaymentPlanNoArray, Contract contract) {
		if(CollectionUtils.isEmpty(repaymentPlanNoArray) || null == contract) {
			return false;
		}
		String sql = "SELECT id FROM AssetSet Where contract = contract AND assetStatus = :assetStatus AND activeStatus = :activeStatus AND singleLoanContractNo IN(:repaymentPlanNoArray)";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contract", contract);
		params.put("assetStatus", AssetClearStatus.UNCLEAR);
		params.put("activeStatus", AssetSetActiveStatus.OPEN);
		params.put("repaymentPlanNoArray", repaymentPlanNoArray);
		List<Long> ids = genericDaoSupport.searchForList(sql, params);
		if(CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return repaymentPlanNoArray.size() == ids.size();
	}
	

	//TODO
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPaidOffAndNoSyncRepaymentPlans() {
		String queryStr = "SELECT assetUuid FROM AssetSet WHERE"
				+ " assetStatus = :assetStatus"
				+ " AND onAccountStatus =:onAccountStatus"
				+ " AND activeStatus <> :activeStatus"
				+ " AND overdueStatus <> :overdueStatus"
				+ " AND syncStatus  <> :syncStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("assetStatus", AssetClearStatus.CLEAR);
		params.put("onAccountStatus", OnAccountStatus.WRITE_OFF);
		params.put("activeStatus", AssetSetActiveStatus.INVALID);
		params.put("overdueStatus", AuditOverdueStatus.UNCONFIRMED);
		params.put("syncStatus", DataSyncStatus.HAS_SYNCED);
		return genericDaoSupport.searchForList(queryStr, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getNotPaidOffAndHasGuaranteeRepaymentPlans() {
		String queryStr = "SELECT assetUuid FROM AssetSet WHERE"
				+ " assetStatus <> :assetStatus"
				+ " AND onAccountStatus <> :onAccountStatus"
				+ " AND activeStatus <> :activeStatus"
				+ " AND overdueStatus <> :overdueStatus"
				+ " AND guaranteeStatus = :guaranteeStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("assetStatus", AssetClearStatus.CLEAR);
		params.put("onAccountStatus", OnAccountStatus.WRITE_OFF);
		params.put("activeStatus", AssetSetActiveStatus.INVALID);
		params.put("overdueStatus", AuditOverdueStatus.UNCONFIRMED);
		params.put("guaranteeStatus", GuaranteeStatus.HAS_GUARANTEE);
		return genericDaoSupport.searchForList(queryStr, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AssetSet getLastPeriodInTheContract(Contract contract) {
		
		String queryString =  "From AssetSet where activeStatus  <> :activeStatus  and contract.id=:contractId order by  currentPeriod desc" ;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("activeStatus", AssetSetActiveStatus.INVALID);
		params.put("contractId", contract.getId());
		List<AssetSet> assetSetList =  (List<AssetSet>) genericDaoSupport.searchForList(queryString, params);
		if(CollectionUtils.isEmpty(assetSetList)){
			return null;
		}
		return assetSetList.get(0);
	}

	@Override
    public  void  updateAssetSetSyncStatusWhenIsPaidOff(String assetUuid,DataSyncStatus syncStatus){
		
		String querySqlString  = "update asset_set set  sync_status =:syncStatus where asset_uuid =:assetSetUuid and assetStatus =:assetClearStatus and  onAccountStatus =:onAccountStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("syncStatus", syncStatus.getOrdinal());
		params.put("assetSetUuid", assetUuid);
		params.put("assetClearStatus", AssetClearStatus.CLEAR.ordinal());
		params.put("onAccountStatus", OnAccountStatus.WRITE_OFF.ordinal());
		this.genericDaoSupport.executeSQL(querySqlString, params);
	}
	
	@Override
	public BigDecimal get_principal_interest_and_extra_amount(AssetSet repaymentPlan){
		BigDecimal totalAssetSetExtraChargeAmount = repaymentPlanExtraChargeService.getTotalAmount(repaymentPlan.getAssetUuid());
		BigDecimal principalAndInterestAmount  = repaymentPlan.getAssetPrincipalValue().add(repaymentPlan.getAssetInterestValue());
		return totalAssetSetExtraChargeAmount.add(principalAndInterestAmount);
	}
	
}
