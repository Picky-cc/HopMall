package com.zufangbao.earth.yunxin.api.handler.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.ImportAssetPackageApiHandler;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageContent;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;
import com.zufangbao.earth.yunxin.api.model.response.ImportAssetPackageResponseModel;
import com.zufangbao.sun.entity.bank.Bank;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.AssetPackage;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.HouseService;
import com.zufangbao.sun.utils.AmountUtil;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;
import com.zufangbao.sun.yunxin.entity.model.api.ImportRepaymentPlanDetail;
import com.zufangbao.sun.yunxin.service.BankService;
import com.zufangbao.sun.yunxin.service.LoanBatchService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;
@Component("ImportAssetPackageApiHandler")
public class ImportAssetPackageApiHandlerImpl  implements ImportAssetPackageApiHandler {

	
	@Autowired
	private ContractService contractService;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private  ContractAccountService contractAccountService;
	@Autowired
	private BankService bankService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private LoanBatchService loanBatchService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService  financialContractService;
	@Autowired
	private RepaymentPlanExtraChargeService  repaymentPlanExtraChargeService;
	@Autowired
	private OrderHandler orderHandler;
	@Autowired
	private LedgerItemService ledgerItemService;
	
	private static final Log logger = LogFactory.getLog(ImportAssetPackageApiHandlerImpl.class);
	
	@Override
	public ImportAssetPackageResponseModel importAssetPackage(ImportAssetPackageRequestModel model) throws Exception {
		
		ImportAssetPackageContent requestContent = model.getRequestContentObject();
		
		requestContent.validateTotalAmountAndNumber();
		FinancialContract financialContract = judgeFinancialProductCodeAndReturnVaildFinancialContract(requestContent);
		
		LoanBatch loanBatch = saveLoanBatch(financialContract);
		for(ContractDetail contractDetail :requestContent.getContractDetails()){
			
			validateContractDetails(contractDetail);
			App app = financialContract.getApp();
			if (app == null) {
				processError(ApiResponseCode.NOT_SET_APP_IN_FINANCIAL_CONTRACT);
			}
			Customer customer = saveCustomer(contractDetail, app);
			House house = saveHouse(contractDetail ,app);
			
			Contract loan_contract = saveContract(contractDetail, app, customer, house, financialContract);
			saveContractAccount(contractDetail, loan_contract);
			saveAssetPackage(financialContract, loanBatch, loan_contract);
			List<AssetSet> assetSetList = new ArrayList<AssetSet>();
			if(!CollectionUtils.isEmpty(contractDetail.getRepaymentPlanDetails())){
				assetSetList= save_repayment_plan_list(contractDetail, loan_contract);
			} 
			
			book_assets_on_ledger_book_and_value_asset_order(financialContract, assetSetList, customer.getCustomerUuid());
			
		}
		return new ImportAssetPackageResponseModel(model.getRequestNo(),loanBatch.getCode());
	}

	private void book_assets_on_ledger_book_and_value_asset_order(FinancialContract financialContract,List<AssetSet> assetSetList, String customerUuid) throws Exception{
		try {
			//
			String companyUuid = financialContract.getCompany()==null?null:financialContract.getCompany().getUuid();
			LedgerTradeParty party = new LedgerTradeParty(companyUuid+"",customerUuid);
			ledgerBookHandler.book_loan_assets(financialContract.getLedgerBookNo(), assetSetList, party);	
		} catch(Exception e){
			e.printStackTrace();
			logger.error("book_assets_on_ledger_book error.  customerUuid["+customerUuid+"].");
			throw e;
		}
		
		for (AssetSet assetSet : assetSetList) {
			if(assetSet!=null && needValuationForImportAssetPackage(assetSet, new Date())){
				orderHandler.assetValuationAndCreateOrder(assetSet.getId(), new Date());
			}
		}
		
	}
	
	/**
	 * 导入当日或以前的还款计划，需要评估
	 */
	private boolean needValuationForImportAssetPackage(AssetSet assetSet, Date valuation_date) {
		if(assetSet.getAssetStatus().ordinal() != AssetClearStatus.UNCLEAR.ordinal()) {
			return false;
		}
		return DateUtils.compareTwoDatesOnDay(assetSet.getAssetRecycleDate(), valuation_date) <= 0;
	}
	
	private FinancialContract judgeFinancialProductCodeAndReturnVaildFinancialContract (
			ImportAssetPackageContent requestContent) 
	{
		FinancialContract financialContract = financialContractService.getUniqueFinancialContractBy(requestContent.getFinancialProductCode());
		if(financialContract == null){
			processError(ApiResponseCode.FINANCIAL_PRODUCT_CODE_ERROR);
		}
		return financialContract;
	}


	private void validateContractDetails(ContractDetail contractDetail) {
		checkContractNoAndUniqueId(contractDetail);
		checkContractAmountAndNumber(contractDetail);
		checkFirstPeriodAssetRecycleDate(contractDetail);
		checkTheSubjectMatterNo(contractDetail);
	}

	private void checkContractNoAndUniqueId(ContractDetail contractDetail) {
		String contractNo = contractDetail.getLoanContractNo();
		String uniqueId   = contractDetail.getUniqueId();
		Contract exist_contract = contractService
				.getContractByContractNo(contractNo);
		if (null != exist_contract) {
			processError(ApiResponseCode.EXIST_LOAN_CONTRACT_NO);
		}
		exist_contract = contractService.getContractByUniqueId(uniqueId);
		if (null != exist_contract) {
			processError(ApiResponseCode.EXIST_LOAN_CONTRACT_UNIQUE_ID);
		}
	}
	
	private void checkTheSubjectMatterNo(ContractDetail contractDetail) {
		if(StringUtils.isEmpty(contractDetail.getSubjectMatterassetNo())){
			return;
		}
		House house = houseService.getHouseByAddress(contractDetail.getSubjectMatterassetNo());
		if(house != null ){
			 processError(ApiResponseCode.EXIST_THE_SUBJECT_MATTER);
		}
	}

	private void checkFirstPeriodAssetRecycleDate(ContractDetail contractDetail) {
		
		Date contractEffectDate = DateUtils.asDay(contractDetail.getEffectDate());
		
		Date firstPeriodAssetRecycleDate = getFirstRepaymentPlanRecycleDate(
				contractDetail, contractEffectDate);
		
		if(isFirstRecycleDateBeforeTodayAndEffectDate(contractEffectDate,firstPeriodAssetRecycleDate)){
			processError(ApiResponseCode.FIRST_REPAYMENT_DATE_NOT_CORRECT);
		}
	}

	private Date getFirstRepaymentPlanRecycleDate(
			ContractDetail contractDetail, Date contractEffectDate) {
		if(!CollectionUtils.isEmpty(contractDetail.getRepaymentPlanDetails())){
               return DateUtils.asDay(contractDetail.getRepaymentPlanDetails().get(0).getRepaymentDate());
		}
		return  DateUtils.addMonths(contractEffectDate, 1);
	}





	private boolean isFirstRecycleDateBeforeTodayAndEffectDate(
			Date contractEffectDate, Date firstPeriodAssetRecycleDate) {
//		return firstPeriodAssetRecycleDate.before(DateUtils.asDay(new Date())) || firstPeriodAssetRecycleDate.before(DateUtils.asDay(contractEffectDate));
		//去除限制：不允许导入今日之前的还款计划
		return firstPeriodAssetRecycleDate.before(DateUtils.asDay(contractEffectDate));
	}



	private void checkContractAmountAndNumber( ContractDetail contractDetail) {
		BigDecimal theContractLoanAmount = new BigDecimal(contractDetail.getLoanTotalAmount());
		int allPeriods =  contractDetail.getLoanPeriods();  
		List<ImportRepaymentPlanDetail> repaymentPlans = contractDetail.getRepaymentPlanDetails();
		if(allPeriods != repaymentPlans.size()){
			processError(ApiResponseCode.REPAYMENT_PLAN_TOTAL_PERIODS_ERROR);
		}
		BigDecimal sum = BigDecimal.ZERO;
		for(ImportRepaymentPlanDetail repaymentPlan:repaymentPlans){
			sum  =new BigDecimal(repaymentPlan.getRepaymentPrincipal()).add(sum);
		}
		if(theContractLoanAmount.compareTo(sum) != 0){
			processError(ApiResponseCode.REPAYMENT_PLAN_TOTAL_AMOUNT_ERROR);
		}
	}

	
	
	
	private AssetSet saveAssetSet(Contract loan_contract, int currentPeriod, ImportRepaymentPlanDetail repaymentPlan) {
		Date assetRecycleDate = DateUtils.asDay(repaymentPlan.getRepaymentDate());
		BigDecimal  assetPrincipalValue = new BigDecimal(repaymentPlan.getRepaymentPrincipal());
		BigDecimal  assetInterestValue =  new BigDecimal(repaymentPlan.getRepaymentInterest());
		
		BigDecimal  theAdditionalFee = repaymentPlan.getTheAdditionalFee();
		AssetSet assetSet = new AssetSet(loan_contract,  currentPeriod, assetRecycleDate,assetPrincipalValue,assetInterestValue,theAdditionalFee);
		
		saveAssetSetExtraCharge(assetSet,repaymentPlan);
		repaymentPlanService.saveOrUpdate(assetSet);
		return assetSet;
	}
	
	private void saveAssetSetExtraCharge(AssetSet assetSet,
			ImportRepaymentPlanDetail repaymentPlan) {
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet, repaymentPlan.getBDOtheFee(), ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet, repaymentPlan.getBDLoanServiceFee(), ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE);
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet, repaymentPlan.getBDTechMaintenanceFee(), ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE);
	}
	

	
	private List<AssetSet> save_repayment_plan_list(ContractDetail contractDetail,
			Contract loan_contract) {
		List<AssetSet> assetSets = new ArrayList<AssetSet>();
		List<ImportRepaymentPlanDetail> repaymentPlanDetails = contractDetail.getRepaymentPlanDetails();
		for (int index = 0; index < repaymentPlanDetails.size(); index++) {
			ImportRepaymentPlanDetail repaymentPlan = repaymentPlanDetails.get(index);
			int current_period = index+1;
			AssetSet assetSet = saveAssetSet(loan_contract, current_period, repaymentPlan);
			assetSets.add(assetSet);
		}
		return assetSets;
	}

	private void saveAssetPackage(FinancialContract financialContract, LoanBatch loanBatch, Contract loan_contract) {
		AssetPackage assetPackage = new AssetPackage(financialContract, loan_contract, loanBatch);
		assetPackageService.saveOrUpdate(assetPackage);
	}
	private LoanBatch saveLoanBatch(FinancialContract financialContract) {
		LoanBatch loanBatch  = loanBatchService.createAndSaveLoanBatch(financialContract, true);
		return loanBatch;
	}
	

	private void processError(int code) {
		throw new ApiException(code);
	}

	private Customer saveCustomer(ContractDetail contractDetail , App app) {
		Customer customer = new Customer(contractDetail, app);
		customerService.saveOrUpdate(customer);
		return customer;
	}
	private House saveHouse(ContractDetail contractDetail,App app) {
		House house = new House(contractDetail ,app);
		houseService.saveOrUpdate(house);
		return house;
	}

	private Contract saveContract(ContractDetail contractDetail , App app, Customer customer, House house, FinancialContract financialContract) throws ParseException {
		Contract loan_contract = new Contract();
		contractDetail.copyToContract(app, customer, house, loan_contract, financialContract);
		contractService.saveOrUpdate(loan_contract);
		return loan_contract;
	}
	
	private void saveContractAccount(ContractDetail contractDetail, Contract loan_contract) {
		
		String bankCode = contractDetail.getBankCode();
	    Bank bank =  bankService.getCachedBanks().get(bankCode);
	    if(bank == null){
	    	processError(ApiResponseCode.NO_MATCH_BANK);
	    }
	    Province province = provinceService.getProvinceByCode(contractDetail.getBankOfTheProvince());
	    if(province == null){
	    	processError(ApiResponseCode.NO_MATCH_PROVINCE);
	    }
	    City city = cityService.getCityByCityCode(contractDetail.getBankOfTheCity());
	    if(city == null){
	    	processError(ApiResponseCode.NO_MATCH_CITY);
	    }
		ContractAccount contractAccount = new ContractAccount(loan_contract, contractDetail,bank,province.getName(),city.getName());
		contractAccountService.saveOrUpdate(contractAccount);
	}
}