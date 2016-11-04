package com.zufangbao.earth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.sonar.runner.commonsio.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.earth.api.test.post.BaseApiTestPost;
import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.HouseService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@SuiteClasses({})
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })



 class A{
	private String a;
	
}
class B extends A{
	private  String b;
}

@TransactionConfiguration(defaultRollback = true)
public class AllTestOfZhenghangboInEarth  extends BaseApiTestPost{

	@Autowired
	private ContractService contractService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AppService appService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Autowired
	private FinancialContractService financialContractService;
    @Autowired
    private SessionFactory sessionFactory;
    
  /*  @Test
    public void testDateUtils(){
    	Date today = new Date();
    	Date he    = DateUtils.parseDate("2016-09-13","yyyy-MM-dd");
    	boolean is = he.equals(today);
    	System.out.println(is);
    }
	  @Test
	  public void testGenerateContracts1() throws IOException{
	  
	  SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss"); 
	  String now = format.format(new Date());
	  String fileUrl = "f:\\success\\"; 
	  String fileName = fileUrl+now+".sql";
	  
	  //write into file with breakline
	  File file = new File(fileUrl);
	  OutputStream os =null; 
	  if(file.exists())
	  { os = new
	  FileOutputStream(fileName); }else{ file.mkdirs(); os = new
	  FileOutputStream(fileName); }
	  
	  
	  for(int j = 1;j<=1000;j++){
	 
	  String customerSql =
	  "INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) "
	  + " VALUES (" + j + ", NULL, '13777776681', '王二', NULL, '1',"+
	  "\'"+UUID.randomUUID().toString()+"\'" + ");";
	  os.write(customerSql.getBytes()); String houseSql =
	  "INSERT INTO  `house` (`id`, `address`, `app_id`) " + "VALUES ("+
	  j+", 'wakaka', '1');"; os.write(houseSql.getBytes()); String contractSql
	  =
	  "INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`)"
	  + " VALUES (" + j +", NULL" + ", '2016-07-01',"+ "'20160723测试-"+j+"'" +
	  ", NULL, '1', '0.00', '1'," + j + ","+ j +
	  ", NULL, '2016-07-23 18:09:22', '0.1560000000', '0', '0', '5', '2', '200.00', '0.0005000000', '1', '');"
	  ;
	  
	  os.write(contractSql.getBytes()); String assetPackageSql =
	  "INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) "
	  + " VALUES (" + j +", NULL, NULL,"+ j+", NULL, '1', '2');";
	  os.write(assetPackageSql.getBytes());
	  
	  
	  String contractAccountSql =
	  "INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`, `from_date`, `thru_date`)"
	  + " VALUES ("+ j + ", '6217000000000003006', NULL,"+ j +
	  ", '测试用户1', '中国建设银行', NULL, '330000000000000001', '105', '安徽省', '亳州', '2016-07-23 18:09:22', '2900-01-01 00:00:00');"
	  ; os.write(contractAccountSql.getBytes());
	  
	  
	  String assetSetSql =
	  "INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`) "
	  + "VALUES  ("+ j*5 +
	  ", '0', '0', '800.00', '400.00', '400.00', '800.00', '2016-07-01', NULL, '0.00', '0', '1', '0', NULL,"
	  + "\'"+UUID.randomUUID().toString()+"\'"
	  +", '2016-07-23 18:09:23', '2016-07-23 18:09:23', NULL," +
	  "\'"+GeneratorUtils.generateAssetSetNo()+"\'"+","+ j +
	  ", NULL, '1', '0', NULL, '1', '0');"; os.write(assetSetSql.getBytes());
	  
	  String assetSetSql2 =
	  "INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`) "
	  + "VALUES  ("+ (j*5+1) +
	  ", '0', '0', '800.00', '400.00', '400.00', '800.00', '2016-08-01', NULL, '0.00', '0', '1', '0', NULL,"
	  + "\'"+UUID.randomUUID().toString()+"\'"
	  +", '2016-07-23 18:09:23', '2016-07-23 18:09:23', NULL," +
	  "\'"+GeneratorUtils.generateAssetSetNo()+"\'"+","+ j +
	  ", NULL, '2', '0', NULL, '1', '0');"; os.write(assetSetSql2.getBytes());
	  String assetSetSql3 =
	  "INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`) "
	  + "VALUES  ("+ (j*5+2) +
	  ", '0', '0', '800.00', '400.00', '400.00', '800.00', '2016-09-01', NULL, '0.00', '0', '1', '0', NULL,"
	  + "\'"+UUID.randomUUID().toString()+"\'"
	  +", '2016-07-23 18:09:23', '2016-07-23 18:09:23', NULL," +
	  "\'"+GeneratorUtils.generateAssetSetNo()+"\'"+","+ j +
	  ", NULL, '3', '0', NULL, '1', '0');"; os.write(assetSetSql3.getBytes());
	  
	  String assetSetSql4 =
	  "INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`) "
	  + "VALUES  ("+ (j*5+3) +
	  ", '0', '0', '800.00', '400.00', '400.00', '800.00', '2016-10-01', NULL, '0.00', '0', '1', '0', NULL,"
	  + "\'"+UUID.randomUUID().toString()+"\'"
	  +", '2016-07-23 18:09:23', '2016-07-23 18:09:23', NULL," +
	  "\'"+GeneratorUtils.generateAssetSetNo()+"\'"+","+ j +
	  ", NULL, '4', '0', NULL, '1', '0');"; os.write(assetSetSql4.getBytes());
	  
	  String assetSetSql5 =
	  "INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`) "
	  + "VALUES  ("+ (j*5+4) +
	  ", '0', '0', '800.00', '400.00', '400.00', '800.00', '2016-11-01', NULL, '0.00', '0', '1', '0', NULL,"
	  + "\'"+UUID.randomUUID().toString()+"\'"
	  +", '2016-07-23 18:09:23', '2016-07-23 18:09:23', NULL," +
	  "\'"+GeneratorUtils.generateAssetSetNo()+"\'"+","+ j +
	  ", NULL, '5', '0', NULL, '1', '0');"; os.write(assetSetSql5.getBytes());
	  
	  }
	  
	  os.flush(); os.close();
	  System.out.println("success write out");
	  
	 }
	 
	  
	  @Test
	  public void testRepyament(){
		  
		  List<RepaymentDetail> s = new ArrayList<RepaymentDetail>();
		  RepaymentDetail q = new RepaymentDetail();
		  
		  q.setRepaymentPlanNo("123456789");
		  q.setLoanFee(new BigDecimal("0.00"));
		  q.setOtherFee(new BigDecimal("0.00"));
		  q.setRepaymentAmount(new BigDecimal("500.00"));
		  q.setRepaymentInterest(new BigDecimal("400.00"));
		  q.setRepaymentPrincipal(new BigDecimal("100.00"));
		  q.setTechFee(new BigDecimal("0.00"));
		  q.setTotalOverdueFee(new BigDecimal("0.00"));
		  s.add(q);
		  System.out.println(JsonUtils.toJsonString(s));
		  
	  }

	  
	  
	  
	  
	@Test
	@Sql("classpath:test/yunxin/Contract/testsql.sql")
	public void testGenerateContracts() {
		App app = appService.load(App.class, 1l);
		System.out.println("这是开始时间" + new Date());
		for (int i = 1; i <= 2000; i++) {
			System.out.println("第" + i + "条");

			Customer customer = new Customer();
			customer.setApp(app);
			customer.setCustomerUuid(UUID.randomUUID().toString());
			customer.setName("王二");
			if (i % 3 == 0) {
				customer.setName("小四");
			}
			customer.setMobile("13777777777");
			customer.setAccount("123456789");
			customerService.save(customer);

			House house = new House();
			house.setAddress("wakaka");
			house.setApp(app);
			houseService.save(house);

			Contract contract = new Contract();
			contract.setApp(app);
			contract.setHouse(house);
			contract.setCustomer(customer);
			contract.setContractNo("云信信2016-78-DK(ZQ201604252" + i);
			contract.setAssetType(AssetType.NONGFENQISEED);
			contract.setTotalAmount(new BigDecimal(3600.00));
			contract.setPeriods(5);
			contract.setBeginDate(new Date());
			contract.setCreateTime(new Date());
			if (i % 3 == 0) {
				contract.setBeginDate(DateUtils.parseDate("2016-09-01"));
				contract.setCreateTime(new Date());
			}
			contract.setRepaymentWay(RepaymentWay.SAW_TOOTH);
			contract.setInterestRate(new BigDecimal(0.1560));
			contract.setPenaltyInterest(new BigDecimal(0.005));
			contract.setActiveVersionNo(1);
			contractService.save(contract);

			AssetPackage assetPackage = new AssetPackage();
			FinancialContract financialContract = financialContractService
					.load(FinancialContract.class, 1l);
			assetPackage.setContract(contract);
			assetPackage.setAssetPackageNo("yunxin-assetpackage" + i);
			assetPackage.setCreateTime(new Date());
			assetPackage.setFinancialContract(financialContract);
			assetPackage.setLoanBatchId(1l);
			assetPackageService.save(assetPackage);

			AssetSet assetSet = new AssetSet();
			assetSet.setVersionNo(contract.getActiveVersionNo());
			assetSet.setAssetUuid(UUID.randomUUID().toString());
			assetSet.setSingleLoanContractNo(GeneratorUtils
					.generateAssetSetNo());
			assetSet.setContract(contract);
			assetSet.setAssetStatus(AssetClearStatus.UNCLEAR);
			assetSet.setOnAccountStatus(OnAccountStatus.ON_ACCOUNT);
			;
			assetSet.setGuaranteeStatus(GuaranteeStatus.NOT_OCCURRED);
			assetSet.setSettlementStatus(SettlementStatus.NOT_OCCURRED);
			assetSet.setAssetRecycleDate(DateUtils.asDay("2016-08-01"));
			assetSet.setCurrentPeriod(1);
			assetSet.setAssetPrincipalValue(new BigDecimal(0.00));
			assetSet.setAssetInterestValue(new BigDecimal(1200.00));
			assetSet.setAssetInitialValue(new BigDecimal(1200.00));
			assetSet.setAssetFairValue(new BigDecimal(1200.00));
			assetSet.setCreateTime(new Date());
			assetSet.setLastModifiedTime(new Date());
			assetSet.setActiveStatus(AssetSetActiveStatus.OPEN);
			assetSet.setVersionNo(contract.getActiveVersionNo());
			repaymentPlanService.save(assetSet);

			AssetSet assetSet1 = new AssetSet();
			assetSet1.setVersionNo(contract.getActiveVersionNo());
			assetSet1.setAssetUuid(UUID.randomUUID().toString());
			assetSet1.setSingleLoanContractNo(GeneratorUtils
					.generateAssetSetNo());
			assetSet1.setContract(contract);
			assetSet1.setAssetStatus(AssetClearStatus.UNCLEAR);
			assetSet1.setOnAccountStatus(OnAccountStatus.ON_ACCOUNT);
			;
			assetSet1.setGuaranteeStatus(GuaranteeStatus.NOT_OCCURRED);
			assetSet1.setSettlementStatus(SettlementStatus.NOT_OCCURRED);
			assetSet1.setAssetRecycleDate(DateUtils.addMonths(
					DateUtils.asDay("2016-08-01"), 1));
			assetSet1.setCurrentPeriod(2);
			assetSet1.setAssetPrincipalValue(new BigDecimal(0.00));
			assetSet1.setAssetInterestValue(new BigDecimal(1200.00));
			assetSet1.setAssetInitialValue(new BigDecimal(1200.00));
			assetSet1.setAssetFairValue(new BigDecimal(1200.00));
			assetSet1.setCreateTime(new Date());
			assetSet1.setLastModifiedTime(new Date());
			assetSet1.setActiveStatus(AssetSetActiveStatus.OPEN);
			assetSet1.setVersionNo(contract.getActiveVersionNo());
			repaymentPlanService.save(assetSet1);

			AssetSet assetSet2 = new AssetSet();
			assetSet2.setVersionNo(contract.getActiveVersionNo());
			assetSet2.setAssetUuid(UUID.randomUUID().toString());
			assetSet2.setSingleLoanContractNo(GeneratorUtils
					.generateAssetSetNo());
			assetSet2.setContract(contract);
			assetSet2.setAssetStatus(AssetClearStatus.UNCLEAR);
			assetSet2.setOnAccountStatus(OnAccountStatus.ON_ACCOUNT);
			;
			assetSet2.setGuaranteeStatus(GuaranteeStatus.NOT_OCCURRED);
			assetSet2.setSettlementStatus(SettlementStatus.NOT_OCCURRED);
			assetSet2.setAssetRecycleDate(DateUtils.addMonths(
					DateUtils.asDay("2016-08-01"), 2));
			assetSet2.setCurrentPeriod(3);
			assetSet2.setAssetPrincipalValue(new BigDecimal(0.00));
			assetSet2.setAssetInterestValue(new BigDecimal(1200.00));
			assetSet2.setAssetInitialValue(new BigDecimal(1200.00));
			assetSet2.setAssetFairValue(new BigDecimal(1200.00));
			assetSet2.setCreateTime(new Date());
			assetSet2.setLastModifiedTime(new Date());
			assetSet2.setActiveStatus(AssetSetActiveStatus.OPEN);
			assetSet2.setVersionNo(contract.getActiveVersionNo());
			repaymentPlanService.save(assetSet2);

			AssetSet assetSet3 = new AssetSet();
			assetSet3.setVersionNo(contract.getActiveVersionNo());
			assetSet3.setAssetUuid(UUID.randomUUID().toString());
			assetSet3.setSingleLoanContractNo(GeneratorUtils
					.generateAssetSetNo());
			assetSet3.setContract(contract);
			assetSet3.setAssetStatus(AssetClearStatus.UNCLEAR);
			assetSet3.setOnAccountStatus(OnAccountStatus.ON_ACCOUNT);
			;
			assetSet3.setGuaranteeStatus(GuaranteeStatus.NOT_OCCURRED);
			assetSet3.setSettlementStatus(SettlementStatus.NOT_OCCURRED);
			assetSet3.setAssetRecycleDate(DateUtils.addMonths(
					DateUtils.asDay("2016-08-01"), 3));
			assetSet3.setCurrentPeriod(4);
			assetSet3.setAssetPrincipalValue(new BigDecimal(0.00));
			assetSet3.setAssetInterestValue(new BigDecimal(1200.00));
			assetSet3.setAssetInitialValue(new BigDecimal(1200.00));
			assetSet3.setAssetFairValue(new BigDecimal(1200.00));
			assetSet3.setCreateTime(new Date());
			assetSet3.setLastModifiedTime(new Date());
			assetSet3.setActiveStatus(AssetSetActiveStatus.OPEN);
			assetSet3.setVersionNo(contract.getActiveVersionNo());
			repaymentPlanService.save(assetSet3);

			AssetSet assetSet4 = new AssetSet();
			assetSet4.setVersionNo(contract.getActiveVersionNo());
			assetSet4.setAssetUuid(UUID.randomUUID().toString());
			assetSet4.setSingleLoanContractNo(GeneratorUtils
					.generateAssetSetNo());
			assetSet4.setContract(contract);
			assetSet4.setAssetStatus(AssetClearStatus.UNCLEAR);
			assetSet4.setOnAccountStatus(OnAccountStatus.ON_ACCOUNT);
			;
			assetSet4.setGuaranteeStatus(GuaranteeStatus.NOT_OCCURRED);
			assetSet4.setSettlementStatus(SettlementStatus.NOT_OCCURRED);
			assetSet4.setAssetRecycleDate(DateUtils.addMonths(
					DateUtils.asDay("2016-08-01"), 4));
			assetSet4.setCurrentPeriod(5);
			assetSet4.setAssetPrincipalValue(new BigDecimal(0.00));
			assetSet4.setAssetInterestValue(new BigDecimal(1200.00));
			assetSet4.setAssetInitialValue(new BigDecimal(1200.00));
			assetSet4.setAssetFairValue(new BigDecimal(1200.00));
			assetSet4.setCreateTime(new Date());
			assetSet4.setLastModifiedTime(new Date());
			assetSet4.setActiveStatus(AssetSetActiveStatus.OPEN);
			assetSet4.setVersionNo(contract.getActiveVersionNo());
			repaymentPlanService.save(assetSet4);

			ContractAccount contractAccount = new ContractAccount();
			contractAccount.setContract(contract);
			contractAccount.setFromDate(new Date());
			contractAccount.setThruDate(DateUtils.MAX_DATE);
			contractAccountService.save(contractAccount);
		}
		System.out.println("这是结束时间" + new Date());
	*/

	@Test
	public void testPressue() throws IOException{
		
		
//		RandomAccessFile file=new RandomAccessFile("0929导入资产包.txt","rwd");
		
		for (int  j=0; j<10;j++){
		
		File file = new File(j+".txt");
		
		StringBuffer sb = new StringBuffer();
		for(int x=0;x<1000;x++)
		{
		
		Map<String, String> requestParams = new HashMap<String, String>();
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("{'financialProductCode':'nfqtest001','thisBatchContractsTotalAmount':'1.00','thisBatchContractsTotalNumber':1,'contractDetails':[");
		
		
		for(int i=0;i<1;i++){
		sb1.append("{'bankCode':'C10102','bankOfTheCity':'110100','bankOfTheProvince':'330000','effectDate':'2016-9-16','expiryDate':'2099-01-01','iDCardNo':'330683199403062410','interestRateCycle':1,'loanContractNo':'"+UUID.randomUUID().toString()+"','loanCustomerName':'张飞','loanCustomerNo':'customerNo1','loanPeriods':1,'loanRates':'0.156','loanTotalAmount':'1.00','penalty':'0.0005','repaymentAccountNo':'111111111111','repaymentPlanDetails':[{'loanServiceFee':'0.00','otheFee':'0.00','repaymentDate':'2016-09-29','repaymentInterest':'0.00','repaymentPrincipal':'1.00','techMaintenanceFee':'0.00'}],'repaymentWay':2,'subjectMatterassetNo':'"+UUID.randomUUID().toString()+"','uniqueId':'"+UUID.randomUUID().toString()+"'},");
		}
		sb1.append("]}");
		requestParams.put("importAssetPackageContent", sb1.toString());
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("fn", "200004");
		String headerStr = "";
		for (Entry<String, String> header : getIdentityInfoMap(requestParams).entrySet()) {
			headerStr += header.getValue() + ",";
		}
		String  dataContent = new String("curl --data \""+buildParams(requestParams)+"\" -H sign:"+headerStr).replaceAll(",t_test_zfb,123456,", " -H merId:t_test_zfb -H secret:123456 -X POST http://192.168.1.33:8080/api/modify?fn=200004"+"\n");
			
//	    file.writeBytes(dataContent);
		sb.append(dataContent);
		
		}
		FileUtils.writeStringToFile(file, sb.toString());
		
		}
//		file.close();
	}
	
	public  String buildParams(Map<String, String> params) {
		StringBuffer buffer = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (Entry<String, String> entry : params.entrySet()) {
			String value = entry.getValue();
			value = StringUtils.isEmpty(value) ? "" : value;
			buffer.append(entry.getKey() + "=" + value + "&");
		}
		return buffer.toString();
	}      
	
	
	@Test
	public void testPressue2() throws IOException{
		
		
		RandomAccessFile file=new RandomAccessFile("d:\\package\\"+"0918扣款测试.txt","rwd");
		
		 try {           
	           BufferedReader reader = new BufferedReader(new FileReader("C:\\12333.csv"));//换成你的文件名         
	           String line = null;
	           while((line=reader.readLine())!=null){
				String item[] = line.split(",");
	               
	            Map<String, String> requestParams = new HashMap<String, String>();
	       		requestParams.put("fn", "300001");
	       		requestParams.put("requestNo", UUID.randomUUID().toString());
	       		requestParams.put("deductId",  UUID.randomUUID().toString());
	       		requestParams.put("financialProductCode", "G31700");
	       		requestParams.put("uniqueId",item[0] );
	       		requestParams.put("apiCalledTime", "2016-9-14");
	       		requestParams.put("amount", "1.00");
	       		requestParams.put("repaymentType", "0");
	       		requestParams.put("repaymentDetails", "[{'loanFee':0.00,'otherFee':0.00,'repaymentAmount':1.00,'repaymentInterest':0.00,'repaymentPlanNo':'"+item[1]+"','repaymentPrincipal':1.00,'techFee':0.00,'totalOverdueFee':0.00}]");
	       		String headerStr = "";
	       		for (Entry<String, String> header : getIdentityInfoMap(requestParams).entrySet()) {
	       			headerStr += header.getValue() + ",";
	       		}
	       		String dataContent = ("curl --data \""+buildParams(requestParams)+"\" -H sign:"+headerStr).replaceAll(",t_test_zfb,123456,", " -H merId:t_test_zfb -H secret:123456 -X POST http://120.55.85.54:27082/api/command?fn=300001");
	       		file.writeBytes(dataContent);
	       		file.writeBytes("\n");
	            }
	           file.close();
	       } 
		 
		 
		 catch (Exception e) {
			 e.printStackTrace();
	     }
		
	}
    
    @Test
    public void sadasd(){
    	List<DeductApplicationRepaymentDetail> deductApplicationRepaymentDetails =new ArrayList<DeductApplicationRepaymentDetail>();
    	DeductApplicationRepaymentDetail s = new DeductApplicationRepaymentDetail();
    	s.setRepaymentPlanCode("2346sdfghmrty");
    	DeductApplicationRepaymentDetail d = new DeductApplicationRepaymentDetail();
    	d.setRepaymentPlanCode("2346sdfghmrdy");
    	deductApplicationRepaymentDetails.add(s);
    	deductApplicationRepaymentDetails.add(d);
    	String ds=  deductApplicationRepaymentDetails.stream().map(fc -> fc.getRepaymentPlanCode()).collect(Collectors.joining(" "));
    	System.out.println(ds);
    }
		
}		
	

	
