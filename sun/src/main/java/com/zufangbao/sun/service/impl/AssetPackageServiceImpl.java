package com.zufangbao.sun.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.entity.Result;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.contract.RepaymentPlanOperateLog;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.AssetPackage;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.ledgerbook.DuplicateAssetsException;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.HouseService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.AssetPackageFormat;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.NFQLoanInformation;
import com.zufangbao.sun.yunxin.entity.NFQRepaymentPlan;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.service.LoanBatchService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

/**
 * @author lute
 *
 */
@Service("assetPackageService")
public class AssetPackageServiceImpl extends GenericServiceImpl<AssetPackage>
		implements AssetPackageService {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractHandler contractHandler;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private LoanBatchService loanBatchService;
	@Autowired
	private com.zufangbao.sun.ledgerbook.LedgerBookHandler anMeiTuLedgerBookHandler;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;

	private static final Log logger = LogFactory.getLog(AssetPackageServiceImpl.class);

	@SuppressWarnings("unchecked")
	public AssetPackage getAssetPackagesByContract(Contract contract) {
		if (null == contract) {
			return null;
		}

		List<AssetPackage> assetPackages = genericDaoSupport.searchForList(
				"FROM AssetPackage WHERE contract =:contract", "contract",
				contract);

		if (CollectionUtils.isNotEmpty(assetPackages)) {
			return assetPackages.get(0);
		}
		return null;
	}

	final int AVERAGE_CAPITAL_PLUS_INTEREST = AssetPackageFormat.AVERAGE_CAPITAL_PLUS_INTEREST
			.getOrdinal();

	@Override
	public Result importAssetPackagesViaExcel(InputStream input,
			Long financialContractId, String operatorName, String ipAddress) throws IOException,
			InvalidFormatException, DuplicateAssetsException {

		FinancialContract financialContract = financialContractService.load(
				FinancialContract.class, financialContractId);
		if (financialContract == null) {
			return create_error_result("没有这个信约合同！！！");
		}

		if (StringUtils.isBlank(financialContract.getContractNo())) {
			return create_error_result("信托合同编号为空");
		}

		AssetPackageFormat assetPackageFormat = financialContract
				.getAssetPackageFormat();
		if (assetPackageFormat == null) {
			return create_error_result("信托合同中未设置资产包格式");
		}

		Result result = new Result();
		LoanBatch loanBatch = loanBatchService.createAndSaveLoanBatch(financialContract, false);
		switch (assetPackageFormat) {
//		case AVERAGE_CAPITAL_PLUS_INTEREST:
//			break;
		case SAW_TOOTH:
			result = import_asset_package_excel_for_yunxin_nongfengqi_seed(
					input, financialContract, loanBatch, operatorName, ipAddress);
			break;
		default:
			return create_error_result("信托合同中资产包格式不支持");

		}
		if (result.isValid()) {
			result.data("loanBatchId", loanBatch.getId());
			result.setMessage("导入成功");
		}
		return result;
	}

	private Result create_error_result(String message) {
		Result result = new Result();
		result.setMessage(message);
		return result;
	}

	private Result import_asset_package_excel_for_yunxin_nongfengqi_seed(InputStream stream,
			FinancialContract financialContract, LoanBatch loanBatch, String operatorName, String ipAddress)
					throws IOException, InvalidFormatException, DuplicateAssetsException {
		LedgerBook ledgerBook = null;
		try {
			ledgerBook = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		Workbook workbook = WorkbookFactory.create(stream);
		List<NFQLoanInformation> loanInformationList = new ExcelUtil<>(
				NFQLoanInformation.class).importExcelHighVersion(0, workbook);
		List<NFQRepaymentPlan> repaymentPlanList = new ExcelUtil<>(
				NFQRepaymentPlan.class).importExcelHighVersion(1, workbook);
		if (CollectionUtils.isEmpty(loanInformationList)
				|| CollectionUtils.isEmpty(repaymentPlanList)) {
			processError("资产包格式错误，请检查");
		}
		for (NFQLoanInformation nfqLoanInformation : loanInformationList) {
			String contractNo = nfqLoanInformation.getContractNo();
			Contract exist_contract = contractService
					.getContractByContractNo(contractNo);
			if (null != exist_contract) {
				processError("贷款合同已存在，编号: " + contractNo);
			}
			List<NFQRepaymentPlan> repayment_plan_for_loan_contract = filter_repayment_plan_belong_to_loan_contract(
					repaymentPlanList, contractNo);
			if (nfqLoanInformation.getPeriodsIntValue() != repayment_plan_for_loan_contract.size()) {
				processError("还款计划期数错误, 编号: " + contractNo);
			}
			App app = financialContract.getApp();
			if (app == null) {
				processError("信托合同商户未设置" + contractNo);
			}
			Customer customer = saveCustomer(nfqLoanInformation, app);
			House house = saveHouse(app);
			Contract loan_contract = saveContract(nfqLoanInformation, app, customer, house, financialContract);
			saveContractAccount(nfqLoanInformation, loan_contract);
			saveAssetPackage(financialContract, loanBatch, loan_contract);

			LedgerTradeParty ledgerTradeParty = ledgerItemService.getAssetsImportLedgerTradeParty(financialContract,customer);
			List<AssetSet> openAssetSet = save_repayment_plan_list(repayment_plan_for_loan_contract, loan_contract, ledgerTradeParty, ledgerBook);
			//合同导入，还款计划操作日志
			contractHandler.addRepaymentPlanOperateLog(loan_contract, RepaymentPlanOperateLog.CONTRACT_IMPORT, openAssetSet, null, null, ipAddress, operatorName);
		}
		workbook.close();
		stream.close();
		return new Result().success();
	}

	private void saveAssetPackage(FinancialContract financialContract, LoanBatch loanBatch, Contract loan_contract) {
		AssetPackage assetPackage = new AssetPackage(financialContract, loan_contract, loanBatch);
		this.saveOrUpdate(assetPackage);
	}

	private void saveContractAccount(NFQLoanInformation nfqLoanInformation, Contract loan_contract) {
		
		ContractAccount contractAccount = new ContractAccount(loan_contract, nfqLoanInformation);
		contractAccountService.saveOrUpdate(contractAccount);
	}

	private Contract saveContract(NFQLoanInformation nfqLoanInformation, App app, Customer customer, House house, FinancialContract financialContract) {
		Contract loan_contract = new Contract();
		nfqLoanInformation.copyToContract(app, customer, house, loan_contract, financialContract);
		
		contractService.saveOrUpdate(loan_contract);
		return loan_contract;
	}

	private House saveHouse(App app) {
		House house = new House(app);
		houseService.saveOrUpdate(house);
		return house;
	}

	private Customer saveCustomer(NFQLoanInformation nfqLoanInformation, App app) {
		Customer customer = new Customer(nfqLoanInformation, app);
		customerService.saveOrUpdate(customer);
		return customer;
	}

	private void processError(String message) {
		logger.error(message);
		throw new RuntimeException(message);
	}

	private List<AssetSet> save_repayment_plan_list(List<NFQRepaymentPlan> repayment_plan_for_loan_contract,
			Contract loan_contract, LedgerTradeParty ledgerTradeParty, LedgerBook ledgerBook)
					throws DuplicateAssetsException {
		List<AssetSet> assetSets = new ArrayList<AssetSet>();
		for (int i = 0; i < repayment_plan_for_loan_contract.size(); i++) {
			NFQRepaymentPlan repaymentPlan = repayment_plan_for_loan_contract.get(i);
			AssetSet assetSet = saveAssetSet(loan_contract, i, repaymentPlan);
			assetSets.add(assetSet);
			try {
				logger.info("Ledger book(book_loan_asset) start. assetuuid[" + assetSet.getAssetUuid() + "].");
				anMeiTuLedgerBookHandler.book_loan_asset_V2(ledgerBook, assetSet, ledgerTradeParty);
				logger.info("Ledger book(book_loan_asset) end. assetuuid[" + assetSet.getAssetUuid() + "].");
			} catch (Exception e) {
				logger.error(
						"Ledger book(book_loan_asset) eccor error with assetuuid[" + assetSet.getAssetUuid() + "].");
				e.printStackTrace();
			}
		}
		return assetSets;
	}

	private AssetSet saveAssetSet(Contract loan_contract, int i, NFQRepaymentPlan repaymentPlan) {
		int current_period_num = i + 1;
		AssetSet assetSet = new AssetSet(loan_contract, repaymentPlan, current_period_num);
		repaymentPlanService.saveOrUpdate(assetSet);
		return assetSet;
	}

	private List<NFQRepaymentPlan> filter_repayment_plan_belong_to_loan_contract(
			List<NFQRepaymentPlan> repaymentPlanList, String contractNo) {
		List<NFQRepaymentPlan> repayment_plan_for_loan_contract = new ArrayList<NFQRepaymentPlan>();
		for (NFQRepaymentPlan nfqRepaymentPlan : repaymentPlanList) {
			if (is_repayment_plan_for_loan_contract(contractNo,
					nfqRepaymentPlan)) {
				repayment_plan_for_loan_contract.add(nfqRepaymentPlan);
			}
		}
		return repayment_plan_for_loan_contract;
	}

	private boolean is_repayment_plan_for_loan_contract(String contractNo,
			NFQRepaymentPlan nfqRepaymentPlan) {
		return StringUtils.equals(contractNo, nfqRepaymentPlan.getContractNo());
	}

	@Override
	public AssetPackage getAssetPackageByContractId(Long contractId) {
		if (null == contractId) {
			return null;
		}

		List<AssetPackage> assetPackageList = genericDaoSupport.searchForList(
				"FROM AssetPackage WHERE contract.id =:contractId",
				"contractId", contractId);

		if (assetPackageList.isEmpty()) {
			return null;
		}
		return assetPackageList.get(0);
	}
	
	

	@Override
	public FinancialContract getFinancialContract(Contract contract) {
		AssetPackage assetPackage = getAssetPackagesByContract(contract);
		if (assetPackage == null) {
			return null;
		}
		FinancialContract factroingContract = assetPackage.getFinancialContract();
		return factroingContract;
	}

	@Override
	public List<AssetPackage> getAssetPackageListByLoanBatchId(Long loanBatchId) {

		String queryString = "FROM AssetPackage where loanBatchId =:loanBatchId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loanBatchId", loanBatchId);
		return this.genericDaoSupport.searchForList(queryString, params);

	}
}
