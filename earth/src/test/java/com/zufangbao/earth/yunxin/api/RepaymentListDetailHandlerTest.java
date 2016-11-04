package com.zufangbao.earth.yunxin.api;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.handler.RepaymentListApitHandler;
import com.zufangbao.earth.yunxin.api.model.RepaymentListDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentListQueryModel;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class RepaymentListDetailHandlerTest {

	@Autowired
	private  RepaymentListApitHandler repaymentListApitHandler;
	
	
	
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentListDetailsFactoringContractNoisNotExist.sql")
	public void testQueryRepaymentListDetailsFactoringContractNoisNotExist(){
		try {
			
			RepaymentListQueryModel queryModel = new RepaymentListQueryModel();
			queryModel.setRequestNo("1234567");
			queryModel.setFinancialContractNo("F-NFQ-LR903");
			queryModel.setQueryStartDate("2016-6-15");
			queryModel.setQueryEndDate("2016-07-15");
			repaymentListApitHandler.queryRepaymentList(queryModel);
			
		} catch (ApiException e) {
			Assert.assertEquals(30003, e.getCode());
		}

	}
	

	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentListDetailForOnlineRepayment.sql")
	public void testQueryRepaymentListDetailForOnlineRepayment(){
			
			RepaymentListQueryModel queryModel = new RepaymentListQueryModel();
			queryModel.setRequestNo("1234567");
			queryModel.setFinancialContractNo("DCF-NFQ-LR903");
			queryModel.setQueryStartDate("2016-6-15");
			queryModel.setQueryEndDate("2016-07-15");
			List<RepaymentListDetail>  RepaymentListDetails = repaymentListApitHandler.queryRepaymentList(queryModel);
			Assert.assertEquals(2, RepaymentListDetails.size());
			RepaymentListDetail RepaymentListDetail = RepaymentListDetails.get(0);
			Assert.assertEquals("ZC27367D23EF4A36F4", RepaymentListDetail.getAssetSetNo());
			Assert.assertEquals(null, RepaymentListDetail.getCompleteDate());
			Assert.assertEquals("中国邮政储蓄银行", RepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("测试用户1", RepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("6217000000000003006", RepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("1217.40", RepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-15", RepaymentListDetail.getDeductDate());
			Assert.assertEquals("27367DBAA552A85B", RepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals(null, RepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", RepaymentListDetail.getContractNo());

	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentListDetailForOfflineRepayment.sql")
	public void testQueryRepaymentListDetailForOfflineRepayment(){
			
			RepaymentListQueryModel queryModel = new RepaymentListQueryModel();
			queryModel.setRequestNo("1234567");
			queryModel.setFinancialContractNo("DCF-NFQ-LR903");
			queryModel.setQueryStartDate("2016-6-15");
			queryModel.setQueryEndDate("2016-07-15");
			List<RepaymentListDetail>  RepaymentListDetails = repaymentListApitHandler.queryRepaymentList(queryModel);
			Assert.assertEquals(3, RepaymentListDetails.size());
			RepaymentListDetail RepaymentListDetail = RepaymentListDetails.get(0);
			Assert.assertEquals("ZC27367D23EF4A36F4", RepaymentListDetail.getAssetSetNo());
			Assert.assertEquals(null, RepaymentListDetail.getCompleteDate());
			Assert.assertEquals("中国邮政储蓄银行", RepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("测试用户1", RepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("6217000000000003006", RepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("1217.40", RepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-15", RepaymentListDetail.getDeductDate());
			Assert.assertEquals("27367DBAA552A85B", RepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals(null, RepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", RepaymentListDetail.getContractNo());
			
			RepaymentListDetail offlineRepaymentListDetail = RepaymentListDetails.get(2);
			Assert.assertEquals("2016-06-23", offlineRepaymentListDetail.getCompleteDate());
			Assert.assertEquals("杭州银行", offlineRepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("佳佳", offlineRepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("213123321213", offlineRepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("23444.00", offlineRepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-23", offlineRepaymentListDetail.getDeductDate());
			Assert.assertEquals("XX273659A6884EA971", offlineRepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals("21414144412412", offlineRepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", offlineRepaymentListDetail.getContractNo());
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentListDetailForOverDueRepaymentList.sql")
	public void testQueryRepaymentListDetailForOverDueRepaymentList(){
			
			RepaymentListQueryModel queryModel = new RepaymentListQueryModel();
			queryModel.setRequestNo("1234567");
			queryModel.setFinancialContractNo("DCF-NFQ-LR903");
			queryModel.setQueryStartDate("2016-6-15");
			queryModel.setQueryEndDate("2016-07-15");
			List<RepaymentListDetail>  RepaymentListDetails = repaymentListApitHandler.queryRepaymentList(queryModel);
			Assert.assertEquals(4, RepaymentListDetails.size());
			RepaymentListDetail RepaymentListDetail = RepaymentListDetails.get(0);
			Assert.assertEquals("ZC27367D23EF4A36F4", RepaymentListDetail.getAssetSetNo());
			Assert.assertEquals(null, RepaymentListDetail.getCompleteDate());
			Assert.assertEquals("中国邮政储蓄银行", RepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("测试用户1", RepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("6217000000000003006", RepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("1217.40", RepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-15", RepaymentListDetail.getDeductDate());
			Assert.assertEquals("27367DBAA552A85B", RepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals(null, RepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", RepaymentListDetail.getContractNo());
			
			RepaymentListDetail offlineRepaymentListDetail = RepaymentListDetails.get(3);
			Assert.assertEquals("2016-06-23", offlineRepaymentListDetail.getCompleteDate());
			Assert.assertEquals("杭州银行", offlineRepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("佳佳", offlineRepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("213123321213", offlineRepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("23444.00", offlineRepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-23", offlineRepaymentListDetail.getDeductDate());
			Assert.assertEquals("XX273659A6884EA971", offlineRepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals("21414144412412", offlineRepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", offlineRepaymentListDetail.getContractNo());
			
			
			RepaymentListDetail overDueRepaymentListDetail = RepaymentListDetails.get(2);
			Assert.assertEquals("2016-06-16", overDueRepaymentListDetail.getCompleteDate());
			Assert.assertEquals("中国邮政储蓄银行", overDueRepaymentListDetail.getDeductAccountBank());
			Assert.assertEquals("测试用户1", overDueRepaymentListDetail.getDeductAccountName());
			Assert.assertEquals("6217000000000003006", overDueRepaymentListDetail.getDeductAccountNo());
			Assert.assertEquals("1234.00", overDueRepaymentListDetail.getDeductAmount());
			Assert.assertEquals("2016-06-16", overDueRepaymentListDetail.getDeductDate());
			Assert.assertEquals("dasdasdsdsa", overDueRepaymentListDetail.getDeductRequestNo());
			Assert.assertEquals("weqa", overDueRepaymentListDetail.getFlowNo());
			Assert.assertEquals("云信信2016-78-DK(ZQ2016042522479)", overDueRepaymentListDetail.getContractNo());
			
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentListDetailForOverDueRepaymentList.sql")
	public void testQueryRepaymentListDetailForSort(){
			
			RepaymentListQueryModel queryModel = new RepaymentListQueryModel();
			queryModel.setRequestNo("1234567");
			queryModel.setFinancialContractNo("DCF-NFQ-LR903");
			queryModel.setQueryStartDate("2016-6-15");
			queryModel.setQueryEndDate("2016-07-15");
			List<RepaymentListDetail>  RepaymentListDetails = repaymentListApitHandler.queryRepaymentList(queryModel);
			Assert.assertEquals(4, RepaymentListDetails.size());
			RepaymentListDetail RepaymentListDetail = RepaymentListDetails.get(0);
			RepaymentListDetail RepaymentListDetail2 = RepaymentListDetails.get(1);
			RepaymentListDetail RepaymentListDetail3 = RepaymentListDetails.get(2);
			RepaymentListDetail RepaymentListDetail4 = RepaymentListDetails.get(3);
			Assert.assertEquals("2016-06-15", RepaymentListDetail.getDeductDate());
			Assert.assertEquals("2016-06-15", RepaymentListDetail2.getDeductDate());
			Assert.assertEquals("2016-06-16", RepaymentListDetail3.getDeductDate());
			Assert.assertEquals("2016-06-23", RepaymentListDetail4.getDeductDate());
	}
	
	
}
