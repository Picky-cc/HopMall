package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class RepaymentPlanServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	private Map<String, FinancialContract> financialContractsMap = new HashMap<String, FinancialContract>();

	@Before
	public void setUp() {
		financialContractsMap.put("2d380fe1-7157-490d-9474-12c5a9901e29", financialContractService.load(FinancialContract.class, 1L));
	}
	
/*************** test calculateBeginningPaid start ***************/
	@Test
	public void test_calculateBeginningPaid_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		BigDecimal beginningPaid = repaymentPlanService.calculateBeginningPaid(null, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPaid);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateBeginningPaid_no_result() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal beginningPaid = repaymentPlanService.calculateBeginningPaid(financialContract, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPaid);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateBeginningPaid.sql")
	public void test_calculateBeginningPaid() {
		Date startDate = DateUtils.asDay("2016-07-21");
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal beginningPaid = repaymentPlanService.calculateBeginningPaid(financialContract, startDate);
		Assert.assertEquals(new BigDecimal("1000.00"), beginningPaid);
	}
/*************** test calculateBeginningPaid end ***************/
	
	
/*************** test calculateReduceLoans start ***************/
	@Test
	public void test_calculateReduceLoans_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");	
		BigDecimal reduceLoans = repaymentPlanService.calculateReduceLoansPrincipal(null, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, reduceLoans);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateReduceLoans_no_result() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");		
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal reduceLoans = repaymentPlanService.calculateReduceLoansPrincipal(financialContract, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, reduceLoans);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateReduceLoans.sql")
	public void test_calculateReduceLoans() {
		Date startDate = DateUtils.asDay("2016-05-01");	
		Date endDate = DateUtils.asDay("2016-05-31");		
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal reduceLoans = repaymentPlanService.calculateReduceLoansPrincipal(financialContract, startDate, endDate);
		Assert.assertEquals(new BigDecimal("1000.00"), reduceLoans);
	}
	
	
/*************** test calculateReduceLoans end ***************/
	
	
/*************** test calculateBeginningInterest start ***************/
	@Test
	public void test_calculateBeginningInterest_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-21");
		BigDecimal beginningInterest = repaymentPlanService.calculateBeginningInterest(null, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningInterest);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateBeginningInterest_no_result() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal beginningInterest = repaymentPlanService.calculateBeginningInterest(financialContract, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningInterest);
	}
/*************** test calculateBeginningInterest end ***************/

	
/*************** test calculateBeginningAmountInterest start ***************/
	@Test
	public void test_calculateBeginningAmountInterest_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-21");
		BigDecimal beginningAmountInterest = repaymentPlanService.calculateBeginningAmountInterest(null, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningAmountInterest);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateBeginningAmountInterest_no_result() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal beginningAmountInterest = repaymentPlanService.calculateBeginningAmountInterest(financialContract, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningAmountInterest);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateBeginningAmountInterest.sql")
	public void test_calculateBeginningAmountInterest() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal beginningAmountInterest = repaymentPlanService.calculateBeginningAmountInterest(financialContract, startDate);
		Assert.assertEquals(new BigDecimal("3200.00"), beginningAmountInterest);
	}
/*************** test calculateBeginningAmountInterest end ***************/
	

/*************** test calculateBeginningPaidInterest start ***************/
	@Test
	public void test_calculateBeginningPaidInterest_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-21");
		BigDecimal beginningPaidInterest = repaymentPlanService.calculateBeginningPaidInterest(null, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPaidInterest);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateBeginningPaidInterest_no_result() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal beginningPaidInterest = repaymentPlanService.calculateBeginningPaidInterest(financialContract, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPaidInterest);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateBeginningPaidInterest.sql")
	public void test_calculateBeginningPaidInterest() {
		Date startDate = DateUtils.asDay("2016-07-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal beginningPaidInterest = repaymentPlanService.calculateBeginningPaidInterest(financialContract, startDate);
		Assert.assertEquals(new BigDecimal("1400.00"), beginningPaidInterest);
	}
/*************** test calculateBeginningPaidInterest end ***************/
	

/*************** test calculateNewInterest start ***************/
	@Test
	public void test_calculateNewInterest_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");	
		BigDecimal newInterest = repaymentPlanService.calculateNewInterest(null, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, newInterest);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateNewInterest_no_result() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");		
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal newInterest = repaymentPlanService.calculateNewInterest(financialContract, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, newInterest);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateNewInterest.sql")
	public void test_calculateNewInterest() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");		
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal newInterest = repaymentPlanService.calculateNewInterest(financialContract, startDate, endDate);
		Assert.assertEquals(new BigDecimal("600.00"), newInterest);
	}
	
/*************** test calculateNewInterest end ***************/

	
/*************** test calculateReduceInterest start ***************/
	@Test
	public void test_calculateReduceInterest_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");	
		BigDecimal reduceInterest = repaymentPlanService.calculateReduceInterest(null, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, reduceInterest);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateReduceInterest_no_result() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");		
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal reduceInterest = repaymentPlanService.calculateReduceInterest(financialContract, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, reduceInterest);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateReduceInterest.sql")
	public void test_calculateReduceInterest() {
		Date startDate = DateUtils.asDay("2016-05-01");	
		Date endDate = DateUtils.asDay("2016-05-31");			
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		financialContract.setFinancialContractUuid("2d380fe1-7157-490d-9474-12c5a9901e29");
		BigDecimal reduceInterest = repaymentPlanService.calculateReduceInterest(financialContract, startDate, endDate);
		Assert.assertEquals(new BigDecimal("1400.00"), reduceInterest);
	}
/*************** test calculateReduceInterest end ***************/
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateReduceInterest.sql")
	public void test_queryAssetSetIds(){
		
		AssetSetQueryModel assetSetQueryModel = new AssetSetQueryModel();
		assetSetQueryModel.setFinancialContractIds("[1]");
		assetSetQueryModel.setFinancialContractsMap(financialContractsMap);
		assetSetQueryModel.setAuditOverDueStatusOrdinals("[0,1,2]");
		assetSetQueryModel.setPaymentStatusOrdinals("[0,1,2,3]");
		List<AssetSet> assetSets = repaymentPlanService.queryAssetSetIdsByQueryModel(assetSetQueryModel, null);
		Assert.assertEquals(4, assetSets.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/testQueryModelOfRepaymentService.sql")
	public void test_assetSet_query_model_empty(){
		AssetSetQueryModel model = new AssetSetQueryModel();
		model.setFinancialContractIds("");
		List<AssetSet> ids = repaymentPlanService.queryAssetSetIdsByQueryModel(model, null);
		assertTrue(CollectionUtils.isEmpty(ids));
		
		model.setFinancialContractIds("[1]");
		model.setPaymentStatusOrdinals("[]");
		ids = repaymentPlanService.queryAssetSetIdsByQueryModel(model, null);
		assertTrue(CollectionUtils.isEmpty(ids));
		
		model.setFinancialContractIds("[1]");
		model.setPaymentStatusOrdinals("[0,1]");
		model.setAuditOverDueStatusOrdinals("[]");
		ids = repaymentPlanService.queryAssetSetIdsByQueryModel(model, null);
		assertTrue(CollectionUtils.isEmpty(ids));
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/testQueryModelOfRepaymentService.sql")
	public void test_assetSet_query(){
		// asset1,2为已逾期,asset3为待确认逾期， asset1为处理中,asset2,3为异常
		//asset1,2属于信托1，asset3，4为信托2
		AssetSetQueryModel model = new AssetSetQueryModel();
		model.setFinancialContractIds("[1]");
		model.setAuditOverDueStatusOrdinals("[1,2]");
		model.setPaymentStatusOrdinals("[1]");
		Map<String, FinancialContract> financialContractsMap = new HashMap<String, FinancialContract>();
		financialContractsMap.put("1", financialContractService.load(FinancialContract.class, 1L));
		financialContractsMap.put("2", financialContractService.load(FinancialContract.class, 2L));
		model.setFinancialContractsMap(financialContractsMap);
		List<AssetSet> ids = repaymentPlanService.queryAssetSetIdsByQueryModel(model, null);
		assertEquals(1,ids.size());
		assertEquals(new Long(1L),ids.get(0).getId());
		
		model = new AssetSetQueryModel();
		model.setFinancialContractIds("[1,2]");
		model.setAuditOverDueStatusOrdinals("[1]");
		model.setPaymentStatusOrdinals("[1,2]");
		model.setFinancialContractsMap(financialContractsMap);
		ids = repaymentPlanService.queryAssetSetIdsByQueryModel(model, null);
		assertEquals(1,ids.size());
		assertEquals(new Long(3L),ids.get(0).getId());
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/testQueryModelOfRepaymentService.sql")
	public void testCountAssets(){
		// asset1,2为已逾期,asset3为待确认逾期， asset1为处理中,asset2,3为异常
		//asset1,2属于信托1，asset3，4为信托2
		int count = repaymentPlanService.countAssets( new ArrayList<String>(), AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING), AuditOverdueStatus.OVERDUE);
		assertEquals(0,count);
		count = repaymentPlanService.countAssets( Arrays.asList("1","2"), AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING), AuditOverdueStatus.OVERDUE);
		assertEquals(1,count);
		count = repaymentPlanService.countAssets( Arrays.asList("1","2"), AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING,PaymentStatus.UNUSUAL), AuditOverdueStatus.OVERDUE);
		assertEquals(2,count);
		count = repaymentPlanService.countAssets( Arrays.asList("1","2"), AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING,PaymentStatus.UNUSUAL), AuditOverdueStatus.UNCONFIRMED);
		assertEquals(1,count);
		count = repaymentPlanService.countAssets( Arrays.asList("1","2"), AssetSetActiveStatus.OPEN, Arrays.asList(PaymentStatus.PROCESSING), AuditOverdueStatus.OVERDUE);
		assertEquals(1,count);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateReduceInterest.sql")
	public void testGetUniqueRepaymentPlanByUuid() {
		
		String expectedUuid = "b2453ef0-853a-47a7-a41e-de6fe2bad389";
		
		AssetSet repaymentPlanIsNull1 = repaymentPlanService.getUniqueRepaymentPlanByUuid(null);
		Assert.assertEquals(null, repaymentPlanIsNull1);
		
		AssetSet repaymentPlanIsNull2 = repaymentPlanService.getUniqueRepaymentPlanByUuid("");
		Assert.assertEquals(null, repaymentPlanIsNull2);
		
		AssetSet repaymentPlanUuidNotExists = repaymentPlanService.getUniqueRepaymentPlanByUuid("uuid_not_exists");
		Assert.assertEquals(null, repaymentPlanUuidNotExists);
		
		AssetSet repaymentPlanUuidExists = repaymentPlanService.getUniqueRepaymentPlanByUuid(expectedUuid);
		Assert.assertNotEquals(null, repaymentPlanUuidExists);
		Assert.assertEquals(expectedUuid, repaymentPlanUuidExists.getAssetUuid());
		Assert.assertEquals(Long.valueOf(2L), repaymentPlanUuidExists.getContract().getId());
		
	}
	
/*************** test testGetTheOutstandingPrincipalAmount start ***************/
	@Test
	public void testGetTheOutstandingPrincipalAmount_ZERO_1() {
		Contract contract = null;
		Date assetRecycleDate = DateUtils.asDay(DateUtils.today());
		BigDecimal the_outstanding_principal_amount_of_contract = repaymentPlanService.get_the_outstanding_principal_amount_of_contract(contract, assetRecycleDate );
		Assert.assertEquals(BigDecimal.ZERO, the_outstanding_principal_amount_of_contract);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/getTheOutstandingPrincipalAmount_ZERO_2.sql")
	public void testGetTheOutstandingPrincipalAmount_ZERO_2() {
		Contract contract = new Contract();
		contract.setId(36L);
		Date assetRecycleDate = DateUtils.asDay(DateUtils.today());
		BigDecimal the_outstanding_principal_amount_of_contract = repaymentPlanService.get_the_outstanding_principal_amount_of_contract(contract, assetRecycleDate);
		Assert.assertEquals(BigDecimal.ZERO, the_outstanding_principal_amount_of_contract);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/getTheOutstandingPrincipalAmount.sql")
	public void testGetTheOutstandingPrincipalAmount() {
		Contract contract = new Contract();
		contract.setId(36L);
		Date assetRecycleDate = DateUtils.asDay("2016-09-02");
		BigDecimal the_outstanding_principal_amount_of_contract = repaymentPlanService.get_the_outstanding_principal_amount_of_contract(contract, assetRecycleDate);
		Assert.assertEquals(new BigDecimal("10000.00"), the_outstanding_principal_amount_of_contract);
	}
/*************** test testGetTheOutstandingPrincipalAmount end ***************/
	
	@Test
	public void testGet_today_recycle_unclear_asset_set_list_Empty() {
		List<AssetSet> asset_set_list = repaymentPlanService.get_unclear_asset_set_list(null, false);
		Assert.assertTrue(CollectionUtils.isEmpty(asset_set_list));
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/get_today_recycle_unclear_asset_set_list_Empty_2.sql")
	public void testGet_today_recycle_unclear_asset_set_list_Empty_2() {
		Contract contract = new Contract();
		contract.setId(36L);
		List<AssetSet> asset_set_list = repaymentPlanService.get_unclear_asset_set_list(contract, false);
		Assert.assertTrue(CollectionUtils.isEmpty(asset_set_list));
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/get_today_recycle_unclear_asset_set_list_today.sql")
	public void testGet_today_recycle_unclear_asset_set_list_today() {
		Contract contract = new Contract();
		contract.setId(36L);
		List<AssetSet> asset_set_list = repaymentPlanService.get_unclear_asset_set_list(contract, true);
		Assert.assertEquals(1, asset_set_list.size());
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/get_today_recycle_unclear_asset_set_list_tomorrow.sql")
	public void testGet_today_recycle_unclear_asset_set_list_tomorrow() {
		Contract contract = new Contract();
		contract.setId(36L);
		List<AssetSet> asset_set_list = repaymentPlanService.get_unclear_asset_set_list(contract, false);
		Assert.assertEquals(1, asset_set_list.size());
	}
}
