package com.zufangbao.sun.yunxin.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.AssetPackage;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AssetType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
public class AssetsPackageServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private LoanBatchService loanBatchService;
	@Autowired
	private ContractService contractService;
	
	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Fail_No_FinancialContractNo.sql")
	public void test_Import_Fail_No_FinancialContractNo() {
		Long financialContractId = 2L;
		FinancialContract financialContract = financialContractService.load(FinancialContract.class,financialContractId);
		Assert.assertTrue(StringUtils.isEmpty(financialContract.getContractNo()));
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ.xls";
		Result result = new Result();
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			result = assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
			Assert.assertEquals("信托合同编号为空", result.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Fail_No_AssetPackageFormat.sql")
	public void test_Import_Fail_No_AssetPackageFormat() {
		Long financialContractId = 2L;
		FinancialContract financialContract = financialContractService.load(FinancialContract.class,financialContractId);
		Assert.assertNull(financialContract.getAssetPackageFormat());
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ.xls";
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			Result result = assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
			Assert.assertEquals("信托合同中未设置资产包格式", result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Fail_Exist_Contract.sql")
	public void test_Import_Fail_Exist_Contract() {
		Long financialContractId = 2L;
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ.xls";
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
		} catch (RuntimeException e) {
			Assert.assertEquals("贷款合同已存在，编号: 云信信2016-78-DK(ZQ2016042522479)", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Succ_NFQ.sql")
	public void test_Import_Fail_Wrong_Periods() {
		Long financialContractId = 2L;
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ-Wrong-Periods.xlsx";
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
			Assert.fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("还款计划期数错误, 编号: 云信信2016-78-DK(ZQ2016042522479)", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		} 
	}

	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Succ_NFQ.sql")
	public void test_Import_NFQ_Fail_Wrong_Excel() {
		Long financialContractId = 2L;
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ-Wrong-Excel.xlsx";
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("资产包格式错误，请检查", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	

	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Succ_NFQ.sql")
	public void test_Import_NFQ_Succ_XLSX() {
		Long financialContractId = 2L;
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ.xlsx";
		Result result = new Result();
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			result = assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
			Assert.assertEquals("导入成功", result.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	} 

	@Test
	@Sql("classpath:test/yunxin/assetPackage/Import_Succ_NFQ.sql")
	public void test_Import_NFQ_Succ_XLS() {
		Long financialContractId = 2L;
		String fileName = "./src/test/resources/test/yunxin/excel/2016-04-29-NFQ.xls";
		Result result = new Result();
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream(file);
			result = assetPackageService.importAssetPackagesViaExcel(input,
					financialContractId, "test", "127.0.0.1");
			Assert.assertEquals("导入成功", result.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	
}
