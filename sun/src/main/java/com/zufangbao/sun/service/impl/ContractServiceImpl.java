package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.GenericJdbcSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.ContractState;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;
import com.zufangbao.sun.yunxin.entity.model.ProjectInformationQueryModel;

@Service("contractService")
public class ContractServiceImpl extends GenericServiceImpl<Contract> implements ContractService{
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private GenericJdbcSupport genericJdbcSupport;
	
	public Contract getContract(Long id) {
		if(id == null){
			return null;
		}
		return genericDaoSupport.get(Contract.class, id);
	}
	
	@Override
	public Contract getContract(String contractUuid){
		Filter filter = new Filter();
		filter.addEquals("uuid", contractUuid);
		List<Contract> contractList = list(Contract.class, filter);
		if(CollectionUtils.isEmpty(contractList)){
			return null;
		}
		return contractList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contract getContractByContractNo(String contractNo) {
		List<Contract> contractLists = this.genericDaoSupport.searchForList("from Contract where contractNo=:contractNo", "contractNo",contractNo);
		if(contractLists.size()==0){
			return null;
		}
		return contractLists.get(0);
	}
	
	@Override
	public List<Contract> getContractListByApp(Page page, App app) {
		Filter filter=new Filter();
		filter.addEquals("app", app);
		List<Contract> contractList = list(Contract.class,filter, page);
		return contractList;
	}
	@Override
	public List<Contract> getContractListByFilterforContractOnSearch(String contractNo, String customerName, String community, String address, Page page, App app) {
		Filter filter = getContractFilter(contractNo, customerName,community, address,app);
		
		List<Contract>	contractList = list(Contract.class, filter,page);
		return contractList;
	}
	private Filter getContractFilter(String contractNo, String customerName,
			String community, String address, App app) {
		Filter filter = new Filter();
		if (isEmpty(contractNo)) {
			filter.addLike("contractNo", contractNo);
		}
		if (isEmpty(customerName)) {
			filter.addLike("customer.name", customerName);
		}
		if (isEmpty(community)) {
			filter.addLike("house.community", community);
		}
		if (isEmpty(address)) {
			filter.addLike("house.address", address);
		}
		filter.addEquals("app", app);
		return filter;
	}

	private boolean isEmpty(String message) {
		return !StringUtils.isEmpty(message);
	}

	@Override
	public Contract getContract(App app, String contractNo) {
		
		Filter filter = new Filter();
		
		filter.addEquals("contractNo", contractNo);
		
		filter.addEquals("app", app);
		
		List<Contract> contracts = this.list(Contract.class, filter);
		
		if(CollectionUtils.isEmpty(contracts)){
			
			return null;
		}
		return contracts.get(0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> queryContractListBy(ContractQueryModel contractQueryModel, Page page) {
		
		if (CollectionUtils.isEmpty(contractQueryModel.getFinancialContractUuids())
				|| CollectionUtils.isEmpty(contractQueryModel.getContractStateList()))
			return Collections.emptyList();
		
		StringBuffer querySb = new StringBuffer(200);
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(querySb, parameters, contractQueryModel);
		if(page == null) {
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		}else {
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private void genQuerySentence(StringBuffer querySb, Map<String, Object> parameters, ContractQueryModel contractQueryModel){
		querySb.append("FROM Contract WHERE financialContractUuid IN (:financialContractUuids) AND state IN (:states)");
		parameters.put("financialContractUuids", contractQueryModel.getFinancialContractUuids());
		parameters.put("states", contractQueryModel.getContractStateList());
		
		if(!StringUtils.isEmpty(contractQueryModel.getContractNo())) {
			querySb.append(" And contractNo like :contractNo");
			parameters.put("contractNo", "%"+contractQueryModel.getContractNo()+"%");
		}
		
		if(!StringUtils.isEmpty(contractQueryModel.getUnderlyingAsset())) {
			querySb.append(" And house.address like :underlyingAsset");
			parameters.put("underlyingAsset", "%"+contractQueryModel.getUnderlyingAsset()+"%");
		}
		if(contractQueryModel.getStartDate()!=null){
			querySb.append(" And Date(beginDate) >= :startDate");
			parameters.put("startDate", contractQueryModel.getStartDate());
		}
		if(contractQueryModel.getEndDate()!=null){
			querySb.append(" And Date(beginDate) <= :endDate");
			parameters.put("endDate", contractQueryModel.getEndDate());
		}
		if(!StringUtils.isEmpty(contractQueryModel.getCustomerName())){
			querySb.append(" And customer.name LIKE :customerName ");
			parameters.put("customerName", "%"+contractQueryModel.getCustomerName()+"%");
		}
		querySb.append(" order by id desc");
	}
	

	@Override
	public int queryContractCountBy(ContractQueryModel contractQueryModel) {
		if (CollectionUtils.isEmpty(contractQueryModel.getFinancialContractUuids())
				|| CollectionUtils.isEmpty(contractQueryModel.getContractStateList())){
			return 0;
		}
		
		StringBuffer querySb = new StringBuffer("SELECT count(id) ");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(querySb, parameters, contractQueryModel);
		
		return this.genericDaoSupport.searchForInt(querySb.toString(), parameters);
	}

	@Override
	public Contract getContractByUniqueId(String uniqueId) {
		Filter filter = new Filter();
		filter.addEquals("uniqueId", uniqueId);
		List<Contract> contracts = this.list(Contract.class, filter);
		if(CollectionUtils.isEmpty(contracts)){
			return null;
		}
		return contracts.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Contract> getContractsByFinancialContract(FinancialContract financialContract, Page page) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return Collections.emptyList();
		}
		String queryHql = "FROM Contract WHERE financialContractUuid =:financialContractUuid ORDER BY id DESC";

		return this.genericDaoSupport.searchForList(queryHql, "financialContractUuid", financialContract.getFinancialContractUuid(), page.getBeginIndex(), page.getEveryPage());
	}
	
	@Override
	public int countContractsByFinancialContract(FinancialContract financialContract) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return 0;
		}
		String countHql = "SELECT count(id) FROM Contract WHERE financialContractUuid =:financialContractUuid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("financialContractUuid", financialContract.getFinancialContractUuid());
		
		return this.genericDaoSupport.searchForInt(countHql, params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BigDecimal calculateBeginningPrincipal(FinancialContract financialContract, Date startDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String querySentence = "SELECT sum(totalAmount) FROM Contract WHERE financialContractUuid =:financialContractUuid"
				+ " And Date(beginDate) < :startDate"
				+ " And state <>:contractState";
		HashMap<String,Object> params = new HashMap<>();
		params.put("financialContractUuid", financialContract.getFinancialContractUuid());
		params.put("startDate", startDate);
		params.put("contractState", ContractState.INVALIDATE);
		List result = this.genericDaoSupport.searchForList(querySentence, params);
		return processStatResult(result);
	}

	@SuppressWarnings("rawtypes")
	private BigDecimal processStatResult(List result) {
		if(CollectionUtils.isEmpty(result) || result.get(0) == null) {
			return BigDecimal.ZERO;
		}
		return (BigDecimal) result.get(0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BigDecimal calculateNewLoansPrincipal(FinancialContract financialContract, Date startDate, Date endDate) {
		if(financialContract == null || StringUtils.isEmpty(financialContract.getFinancialContractUuid())) {
			return BigDecimal.ZERO;
		}
		String querySentence = "SELECT sum(totalAmount) FROM Contract WHERE financialContractUuid =:financialContractUuid "
				+ " And Date(beginDate) >= :startDate"
				+ " And Date(beginDate) < :endDate"
				+ " And state <>:contractState";
		HashMap<String,Object> params = new HashMap<>();
		params.put("financialContractUuid", financialContract.getFinancialContractUuid());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("contractState", ContractState.INVALIDATE);
		List result = this.genericDaoSupport.searchForList(querySentence, params);
		return processStatResult(result);
}
	
	@Override
	public List<ProjectInformationSQLReturnData> getContractListBy(ProjectInformationQueryModel projectInformationQueryModel, Page page) {
		String queryString = assembleQueryStringSql(projectInformationQueryModel);
		
		Map<String, Object> params = buildParamsBy(projectInformationQueryModel);
		if(page == null) {
			return this.genericDaoSupport.queryForList(queryString, params,ProjectInformationSQLReturnData.class);
		}else {
			return this.genericDaoSupport.queryForList(queryString, params,ProjectInformationSQLReturnData.class, page.getBeginIndex(), page.getEveryPage());
		}
	}

	private String assembleQueryStringSql(ProjectInformationQueryModel projectInformationQueryModel) {
		String queryString = new String("SELECT c.*, ct_max_asset_recycle_date.max_asset_recycle_date ");
		queryString += " FROM contract c, ";
		queryString += " (SELECT contract_id, MAX(asset_recycle_date) AS max_asset_recycle_date FROM `asset_set` as1 WHERE as1.active_status = :activeStatus GROUP BY contract_id ) ct_max_asset_recycle_date, ";
		queryString += " customer cs ";
		queryString += " WHERE c.id = ct_max_asset_recycle_date.contract_id AND c.customer_id = cs.id";
		
		queryString = buildQueryStringBy(projectInformationQueryModel, queryString);
		queryString +=" order by c.id desc";
		return queryString;
	}
	
	@Override
	public int countContractListBy(ProjectInformationQueryModel projectInformationQueryModel) {
		String queryString = "SELECT count(c.id) FROM contract c, "
				+ " (SELECT contract_id, MAX(asset_recycle_date) AS max_asset_recycle_date FROM `asset_set` as1 WHERE as1.active_status = :activeStatus GROUP BY contract_id ) ct_max_asset_recycle_date, "
				+ " customer cs "
				+ " WHERE c.id = ct_max_asset_recycle_date.contract_id AND c.customer_id = cs.id";
		
		queryString = buildQueryStringBy(projectInformationQueryModel, queryString);
		return this.genericDaoSupport.queryForInt(queryString, buildParamsBy(projectInformationQueryModel));
	}
	
	private String buildQueryStringBy(ProjectInformationQueryModel projectInformationQueryModel, String queryString) {
		if(!StringUtils.isEmpty(projectInformationQueryModel.getContractNo())) {
			queryString += " And c.contract_no LIKE :contractNo ";
		}
		if(StringUtils.isNotEmpty(projectInformationQueryModel.getFinancialContractUuid())){
			queryString += " And c.financial_contract_uuid =:financialContractUuid ";
		}
		if(!StringUtils.isEmpty(projectInformationQueryModel.getCustomerName())){
			queryString += " And cs.`name`  LIKE :customerName ";
		}
		if(projectInformationQueryModel.getLoanEffectStartDate()!=null){
			queryString += " And c.begin_date >= :loanEffectStartDate ";
		}
		if(projectInformationQueryModel.getLoanEffectEndDate()!=null){
			queryString += " And c.begin_date < :loanEffectEndDate ";
		}
		if(projectInformationQueryModel.getLoanExpectTerminateStartDate() != null){
			queryString +=" And ct_max_asset_recycle_date.max_asset_recycle_date >= :loanExpectTerminateStartDate ";
		}
		if(projectInformationQueryModel.getLoanExpectTerminateEndDate() != null){
			queryString +=" And ct_max_asset_recycle_date.max_asset_recycle_date < :loanExpectTerminateEndDate ";
		}
		return queryString;
	}
	
	private Map<String, Object> buildParamsBy(ProjectInformationQueryModel projectInformationQueryModel) {
		Map<String, Object> params = new HashMap<>();
		params.put("activeStatus",AssetSetActiveStatus.OPEN);
		if(!StringUtils.isEmpty(projectInformationQueryModel.getContractNo())) {
			params.put("contractNo", "%"+projectInformationQueryModel.getContractNo()+"%");
		}
		if(StringUtils.isNotEmpty(projectInformationQueryModel.getFinancialContractUuid())){
			params.put("financialContractUuid", projectInformationQueryModel.getFinancialContractUuid());
		}
		if(!StringUtils.isEmpty(projectInformationQueryModel.getCustomerName())){
			params.put("customerName", "%"+projectInformationQueryModel.getCustomerName()+"%");
		}
		if(projectInformationQueryModel.getLoanEffectStartDate()!=null){
			params.put("loanEffectStartDate", projectInformationQueryModel.getLoanEffectStartDate());
		}
		if(projectInformationQueryModel.getLoanEffectEndDate()!=null){
			params.put("loanEffectEndDate", projectInformationQueryModel.getLoanEffectEndDateAddOneDay());
		}
		if(projectInformationQueryModel.getLoanExpectTerminateStartDate() != null){
			params.put("loanExpectTerminateStartDate", projectInformationQueryModel.getLoanExpectTerminateStartDate());
		}
		if(projectInformationQueryModel.getLoanExpectTerminateEndDate() != null){
			params.put("loanExpectTerminateEndDate", projectInformationQueryModel.getLoanExpectTerminateEndDateAddOneDay());
		}
		return params;
	}
	
	@Override
	public Contract getContractByCustomer(Customer customer){
		if(customer==null){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("customer", customer);
		List<Contract> contractList = this.list(Contract.class, filter);
		if(CollectionUtils.isEmpty(contractList)){
			return null;
		}
		return contractList.get(0);
	}

	@Override
	public void updateContractActiveVersionNo(Contract contract, Integer oldVersionNo, Integer newVersionNo) {
		String sql = "Update contract SET active_version_no = :newVersionNo WHERE uuid = :contractUuid AND active_version_no = :oldVersionNo";
		HashMap<String,Object> params = new HashMap<>();
		params.put("newVersionNo", newVersionNo);
		params.put("contractUuid", contract.getUuid());
		params.put("oldVersionNo", oldVersionNo);
		genericJdbcSupport.executeSQL(sql, params);
	}

	@Override
	public Integer getActiveVersionNo(String contractUuid) {
		String sql = "SELECT active_version_no FROM contract where uuid = :contractUuid";
		HashMap<String,Object> params = new HashMap<>();
		params.put("contractUuid", contractUuid);
		List<Integer> result = genericJdbcSupport.queryForSingleColumnList(sql, params, Integer.class);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}

}


