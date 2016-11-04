package com.zufangbao.earth.yunxin.handler.deduct.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.entity.deduct.model.AccountInformation;
import com.zufangbao.earth.yunxin.entity.deduct.model.DeductApplicationDetailShowModel;
import com.zufangbao.earth.yunxin.entity.deduct.model.DeductInformation;
import com.zufangbao.earth.yunxin.entity.deduct.model.LoanInformation;
import com.zufangbao.earth.yunxin.entity.deduct.model.PaymentOrderInformation;
import com.zufangbao.earth.yunxin.entity.deduct.model.QueryDeductApplicationShowModel;
import com.zufangbao.earth.yunxin.entity.deduct.model.RepaymentPlanDetailShowModel;
import com.zufangbao.earth.yunxin.handler.deduct.DeductApplicationHandler;
import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationDetailService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductPlanService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductPlan;
import com.zufangbao.sun.yunxin.entity.deduct.DeductApplicationQeuryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;


@Component("DeductApplicationHandler")
public class DeductApplicationHandlerImpl   implements DeductApplicationHandler	{

	@Autowired
	private   DeductApplicationService deductApplicationService;
	
	
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private DeductApplicationDetailService deductApplicationDetailService;
	
	@Autowired
	private DeductPlanService deductPlanService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Override
	public int countQueryDeductApplicationNumber( DeductApplicationQeuryModel queryModel) {
		if(CollectionUtils.isEmpty(queryModel.getExecutionStatusEnumList()) || CollectionUtils.isEmpty(queryModel.getFinancialContractIdList()) || CollectionUtils.isEmpty(queryModel.getRepaymentTypeList())) {
			return 0;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		String querySql = deductApplicationService.generateQuerySentence(queryModel, params);
		
		return this.genericDaoSupport.count(querySql, params);
		
	}

	

	private List<QueryDeductApplicationShowModel> castShowModel(
			List<DeductApplication> deductApplications) {
		
		
		List<QueryDeductApplicationShowModel> showModels = new ArrayList<QueryDeductApplicationShowModel>();
		for(DeductApplication deductApplication:deductApplications){
			FinancialContract financialContract = financialContractService.getFinancialContractBy(deductApplication.getFinancialContractUuid());
			showModels.add(new QueryDeductApplicationShowModel(deductApplication,financialContract));
		}
		return showModels;
	}



	@Override
	public List<QueryDeductApplicationShowModel> queryDeductApplicationShowModel(DeductApplicationQeuryModel queryModel, Page page){
		
		
		if(CollectionUtils.isEmpty(queryModel.getExecutionStatusEnumList()) || CollectionUtils.isEmpty(queryModel.getFinancialContractIdList()) || CollectionUtils.isEmpty(queryModel.getRepaymentTypeList())) {
			return Collections.emptyList();
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		String querySql = deductApplicationService.generateQuerySentence(queryModel, params);
		
		List<DeductApplication> deductApplications =  new ArrayList<DeductApplication>();
		if (page == null) {
		      deductApplications =  this.genericDaoSupport.searchForList(querySql, params);
		} else {
			  deductApplications = this.genericDaoSupport.searchForList(querySql, params, page.getBeginIndex(), page.getEveryPage());
		}
		
		return castShowModel(deductApplications);
	}




	@Override
	public DeductApplicationDetailShowModel assembleDeductApplicationDetailShowModel(
			String deductApplicationUuid) {
		
		DeductApplication deductApplication =  deductApplicationService.getDeductApplicationByDeductId(deductApplicationUuid);
	
		Contract contract = contractService.getContractByUniqueId(deductApplication.getContractUniqueId());
		
		ContractAccount  contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
	
	    List<DeductApplicationRepaymentDetail> deductApplicationRepaymentDetails  = deductApplicationDetailService.getRepaymentDetailsBy(deductApplication.getDeductApplicationUuid()); 
		
		List<DeductPlan> deductPlans = deductPlanService.getDeductPlanByDeductApplicationUuid(deductApplication.getDeductApplicationUuid());
		
		return  castDeductApplicationDetailShowModel(deductApplication, contract,contractAccount, deductApplicationRepaymentDetails, deductPlans);
		
	}



	private DeductApplicationDetailShowModel castDeductApplicationDetailShowModel(DeductApplication deductApplication,
			Contract contract, ContractAccount contractAccount,
			List<DeductApplicationRepaymentDetail> deductApplicationRepaymentDetails, List<DeductPlan> deductPlans) {
		LoanInformation loanInformation = new LoanInformation(contract,deductApplicationRepaymentDetails);
		
		DeductInformation deductInformation = new DeductInformation(deductApplication);
		
		AccountInformation accountInformation = new AccountInformation(contractAccount);
		
		List<PaymentOrderInformation> paymentOrderInformationList = new ArrayList<PaymentOrderInformation>();
		for(DeductPlan deductPlan :deductPlans){
			paymentOrderInformationList.add(new PaymentOrderInformation(deductPlan,deductApplication));
		}
		
		List<RepaymentPlanDetailShowModel> reapymentPlanDetailShowModelList = new ArrayList<RepaymentPlanDetailShowModel>();
		for(DeductApplicationRepaymentDetail deductApplicationRepaymentDetail: deductApplicationRepaymentDetails){
			AssetSet repaymentPlan = repaymentPlanService.getUniqueRepaymentPlanByUuid(deductApplicationRepaymentDetail.getAssetSetUuid());
			reapymentPlanDetailShowModelList.add(new RepaymentPlanDetailShowModel(deductApplicationRepaymentDetail,repaymentPlan));
		}
		return new DeductApplicationDetailShowModel(loanInformation,deductInformation,accountInformation,paymentOrderInformationList,reapymentPlanDetailShowModelList);
	}
	
}
