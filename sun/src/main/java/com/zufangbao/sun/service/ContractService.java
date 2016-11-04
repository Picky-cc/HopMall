package com.zufangbao.sun.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;
import com.zufangbao.sun.yunxin.entity.model.ProjectInformationQueryModel;

public interface ContractService extends GenericService<Contract>{
	
	public Contract getContract(Long id);
	
	public Contract getContract(String contractUuid);
	
	public Contract getContractByContractNo(String string);

	public List<Contract> getContractListByApp(Page page, App app);

	public List<Contract> getContractListByFilterforContractOnSearch(String contractNo, String customerName, String community, String address, Page page, App app);

	public Contract getContract(App app,String contractNo);
	
	List<Contract> queryContractListBy(ContractQueryModel contractQueryModel, Page page);
	
	public int queryContractCountBy(ContractQueryModel contractQueryModel);
	
	public Contract getContractByUniqueId(String uniqueId);
	
	public List<Contract> getContractsByFinancialContract(FinancialContract financialContract, Page page);

	public int countContractsByFinancialContract(FinancialContract financialContract);

	/**
	 * 贷款规模管理-计算 期初贷款本金总和
	 * @param financialContract 信托计划
	 * @param startDate 期初时间（不包含）
	 * @return 期初贷款本金总和
	 */
	public BigDecimal calculateBeginningPrincipal(FinancialContract financialContract, Date startDate);

	/**
	 * 贷款规模管理-计算 本期新增贷款本金总和
	 * @param financialContract 信托计划
	 * @param startDate 查询日首日（包含）
	 * @param endDate	查询日尾日（不包含）
	 * @return 本期新增贷款本金总和
	 */
	public BigDecimal calculateNewLoansPrincipal(FinancialContract financialContract, Date startDate, Date endDate);
	
	public List<ProjectInformationSQLReturnData> getContractListBy(ProjectInformationQueryModel projectInformationQueryModel , Page page );
	
	public int countContractListBy(ProjectInformationQueryModel projectInformationQueryModel);
	
	public Contract getContractByCustomer(Customer customer);

	public void updateContractActiveVersionNo(Contract contract, Integer oldVersionNo, Integer newVersionNo);

	public Integer getActiveVersionNo(String contractUuid);

 
}