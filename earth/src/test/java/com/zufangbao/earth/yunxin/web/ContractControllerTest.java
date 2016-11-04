package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.web.controller.assets.ContractController;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class ContractControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ContractController contractController;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private FinancialContractService financialContractService;
	
	private Map<String, FinancialContract> financialContractsMap = new HashMap<String, FinancialContract>();

	@Before
	public void setUp() {
		financialContractsMap.put("d7b3b325-719a-42af-a129-0ac861f18ebe", financialContractService.load(FinancialContract.class, 1L));
	}
	
	@Test
	@Sql("classpath:test/yunxin/testContractList.sql")
	public void testContractList() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("wqerqw");
		String contractNo = "DKHD-001";
		String carNo = "V001";
		String startDate = "2016-03-08";
		String endDate = "2016-03-09";
		String customerName = "name_1";
		String contractStates = "[0, 1, 2, 3]";
		Page page = new Page(1, 6);
		ContractQueryModel contractQueryModel = new ContractQueryModel("[5]", carNo,
				contractNo, startDate, endDate, customerName, contractStates);
		contractQueryModel.setFinancialContractsMap(financialContractsMap);
		String resultStr = contractController.searchContracts(contractQueryModel, page);
		Result result = JsonUtils.parse(resultStr, Result.class);
		List<Contract> contractList = JsonUtils.parseArray(result.get("list").toString(), Contract.class);
		Contract contract = contractList.get(0);
		Assert.assertEquals(new String("DKHD-001"), contract.getContractNo());
		Assert.assertEquals(20, contract.getPeriods());
		Assert.assertEquals(1, contract.getPaymentFrequency());
		Assert.assertEquals(new String("安美途"), contract.getApp().getName());
		Assert.assertEquals(0, contract.getAssetType().ordinal());
		Assert.assertEquals(new BigDecimal("15976.15"), contract.getMonthFee());
		Assert.assertEquals(DateUtils.parseDate(startDate, "yyyy-MM-dd"),
				contract.getBeginDate());

	}
	
	@Test
	@Sql("classpath:test/yunxin/testContractList.sql")
	public void testContractList_empty() {
		//test empty
		Page page = new Page(1, 6);
		ContractQueryModel contractQueryModel = new ContractQueryModel();
		String resultStr = contractController.searchContracts(contractQueryModel, page);
		Result result = JsonUtils.parse(resultStr, Result.class);
		List<Contract> contractList = JsonUtils.parseArray(result.get("list").toString(), Contract.class);
		assertEquals(0,contractList.size());
	}

	@Test
	@Sql("classpath:test/yunxin/testContractList_StartDateAndEndDate.sql")
	public void testContractList_StartDateAndEndDate() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("wqerqw");
		Principal principal = principalService.load(Principal.class, 1l);

		String app = "1";
		String contractNo = "DKHD-001";
		String carNo = "V001";
		String startDate = "2016-03-08";
		String endDate = "2016-03-09";
		String customerName = "";
		Page page = new Page(1, 6);
		ContractQueryModel contractQueryModel = new ContractQueryModel("[5]", carNo,
				contractNo, startDate, endDate, customerName);
		contractQueryModel.setContractStateOrdinals("[0,1,2,3]");
		contractQueryModel.setFinancialContractsMap(financialContractsMap);
		String resultStr = contractController.searchContracts(contractQueryModel, page);
		Result result = JsonUtils.parse(resultStr, Result.class);
		List<Contract> contractList = JsonUtils.parseArray(result.get("list").toString(), Contract.class);
		Assert.assertEquals(2, contractList.size());
	}

	@Test
	@Sql("classpath:test/yunxin/testContractList_StartDateAndEndDate.sql")
	public void testContractList_endDateEarlierThanStartDate() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("wqerqw");
		Principal principal = principalService.load(Principal.class, 1l);

		String app = "1";
		String contractNo = "DKHD-001";
		String carNo = "V001";
		String startDate = "2016-03-09";
		String endDate = "2016-03-08";
		String customerName = "";
		Page page = new Page(1, 6);
		ContractQueryModel contractQueryModel = new ContractQueryModel("[5]", carNo,
				contractNo, startDate, endDate, customerName);
		String resultStr = contractController.searchContracts(contractQueryModel, page);
		Result result = JsonUtils.parse(resultStr, Result.class);
		List<Contract> contractList = JsonUtils.parseArray(result.get("list").toString(), Contract.class);
		Assert.assertEquals(0, contractList.size());
	}

	@Test
	@Sql("classpath:test/yunxin/testContractDetail.sql")
	public void testContractDetail() {

		Long contractId = 1687l;
		ModelAndView modelAndView = contractController.showContractDetail(contractId, null);
		Map<String, Object> map = modelAndView.getModel();
		List<AssetSet> assetSets = (List<AssetSet>)map.get("assetSetList");
		AssetSet assetSet = assetSets.get(0);
		Assert.assertEquals(new String("DKHD-001-01"), assetSet.getSingleLoanContractNo());
		Assert.assertEquals(new String("DKHD-001"), assetSet.getContractNo());
		Assert.assertEquals(null, assetSet.getActualRecycleDate());
		Assert.assertEquals(new String("V001"), assetSet.getAssetNo());
		Assert.assertEquals(new BigDecimal("15.98"), assetSet.getAmount());
		Assert.assertEquals(PaymentStatus.UNUSUAL, assetSet.getPaymentStatus());
		
		AssetSet assetSet1 = assetSets.get(1);
		
		Assert.assertEquals(new String("DKHD-001-02"), assetSet1.getSingleLoanContractNo());
		Assert.assertEquals(new String("DKHD-001"), assetSet1.getContractNo());
		Assert.assertEquals(null, assetSet1.getActualRecycleDate());
		Assert.assertEquals(new String("V001"), assetSet1.getAssetNo());
		Assert.assertEquals(new BigDecimal("15976.15"), assetSet1.getAmount());
		Assert.assertEquals(PaymentStatus.DEFAULT, assetSet1.getPaymentStatus());
	}

}
