package com.zufangbao.earth.web.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.controller.ModifyApiController;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageContent;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;
import com.zufangbao.sun.yunxin.entity.model.api.ImportRepaymentPlanDetail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ModifyApiControllerTest {

	
	  @Autowired
	  private  ModifyApiController modifyApiController;
	

	  @Test
	  @Sql("classpath:test/yunxin/assetPackage/testImportAssetPackageApi.sql")
	  public void testImportAssetPackage(){
		  
		  ImportAssetPackageContent importAssetPackageContent = new ImportAssetPackageContent();
		  importAssetPackageContent.setThisBatchContractsTotalNumber(1);
		  importAssetPackageContent.setThisBatchContractsTotalAmount("4000.00");
		  importAssetPackageContent.setFinancialProductCode("G00000");
		  
		  List<ContractDetail> contracts= new ArrayList<ContractDetail>();
		  ContractDetail contractDetail = new ContractDetail();
		  contractDetail.setUniqueId("34567890");
		  contractDetail.setLoanContractNo("contractNo1");
		  contractDetail.setLoanCustomerNo("customerNo1");
		  contractDetail.setLoanCustomerName("郑航波");
		  contractDetail.setSubjectMatterassetNo("234567");
		  contractDetail.setIDCardNo("330683199403062411");
		  contractDetail.setBankCode("C10102");
		  contractDetail.setBankOfTheProvince("330000");
		  contractDetail.setBankOfTheCity("110100");
		  contractDetail.setRepaymentAccountNo("23456787654323456");
		  contractDetail.setLoanTotalAmount("4000.00");
		  contractDetail.setLoanPeriods(2);
		  contractDetail.setEffectDate("2016-8-1");
		  contractDetail.setExpiryDate("2099-01-01");
		  contractDetail.setLoanRates("0.156");
		  contractDetail.setInterestRateCycle(1);
		  contractDetail.setPenalty("0.0005");
		  contractDetail.setRepaymentWay(1);
		  
		  List<ImportRepaymentPlanDetail> repaymentPlanDetails = new ArrayList<ImportRepaymentPlanDetail>();
		  
		  ImportRepaymentPlanDetail repaymentPlanDetail1 = new ImportRepaymentPlanDetail();
		  repaymentPlanDetail1.setRepaymentPrincipal("2000.00");
		  repaymentPlanDetail1.setRepaymentInterest("20.00");
		  repaymentPlanDetail1.setRepaymentDate("2016-09-04");
		  repaymentPlanDetail1.setOtheFee("0.00");
		  repaymentPlanDetail1.setLoanServiceFee("0.00");
		  repaymentPlanDetail1.setTechMaintenanceFee("0.00");
		  repaymentPlanDetails.add(repaymentPlanDetail1);
		  
		  ImportRepaymentPlanDetail repaymentPlanDetail2 = new ImportRepaymentPlanDetail();
		  repaymentPlanDetail2.setRepaymentPrincipal("2000.00");
		  repaymentPlanDetail2.setRepaymentInterest("20.00");
		  repaymentPlanDetail2.setRepaymentDate("2016-10-04");
		  repaymentPlanDetail2.setOtheFee("0.00");
		  repaymentPlanDetail2.setLoanServiceFee("0.00");
		  repaymentPlanDetail2.setTechMaintenanceFee("0.00");
		  repaymentPlanDetails.add(repaymentPlanDetail2);
		  contractDetail.setRepaymentPlanDetails(repaymentPlanDetails);
		  
		  contracts.add(contractDetail);
		  importAssetPackageContent.setContractDetails(contracts);
		  
		  
		  ImportAssetPackageRequestModel requestModel = new ImportAssetPackageRequestModel();
		  requestModel.setFn("200004");
		  requestModel.setRequestNo("13456789");
		  requestModel.setImportAssetPackageContent(JsonUtils.toJsonString(importAssetPackageContent));
		  System.out.println(JsonUtils.toJsonString(importAssetPackageContent));
		  MockHttpServletRequest request = new MockHttpServletRequest();
		  MockHttpServletResponse response = new MockHttpServletResponse();
		  
		  modifyApiController.importAssetPackage(requestModel, request, response);
	  }
}
