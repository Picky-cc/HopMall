package com.zufangbao.earth.api.test.post;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentPlanModifyRequestDataModel;
import com.zufangbao.sun.utils.DateUtils;

public class ModifyRepaymentPlanApiPost extends BaseApiTestPost{
	
	@Test
	public void testApiRepaymentPlan() throws InterruptedException {
			
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 1; i++) {
					Map<String, String> requestParams = new HashMap<String, String>();
					requestParams.put("fn", "200001");
					requestParams.put("uniqueId", "fd7ae002-6118-4044-bb2f-54963a6ef004");
					requestParams.put("requestReason", "0");
					requestParams.put("requestNo", DateUtils.getNowFullDateTime());
					List<RepaymentPlanModifyRequestDataModel> list = new ArrayList<>();
						RepaymentPlanModifyRequestDataModel model = new RepaymentPlanModifyRequestDataModel();
						model.setAssetInterest(new BigDecimal("5.00"));
						model.setAssetPrincipal(new BigDecimal("2000.00"));
						model.setMaintenanceCharge(new BigDecimal("0.01"));
						model.setServiceCharge(new BigDecimal("0.01"));
						model.setOtherCharge(new BigDecimal("0.01"));
						model.setAssetType(0);
						model.setAssetRecycleDate("2016-09-14");
						
						RepaymentPlanModifyRequestDataModel model2 = new RepaymentPlanModifyRequestDataModel();
						model2.setAssetInterest(new BigDecimal("10.01"));
						model2.setAssetPrincipal(new BigDecimal("2000.00"));
						model2.setMaintenanceCharge(new BigDecimal("0.01"));
						model2.setServiceCharge(new BigDecimal("0.01"));
						model2.setOtherCharge(new BigDecimal("0.01"));
						model2.setAssetType(0);
						model2.setAssetRecycleDate("2016-09-14");
						
						list.add(model);
						list.add(model2);
					requestParams.put("requestData", JsonUtils.toJsonString(list));
//					requestParams.put("requestData", "[{\"assetRecycleDate\":\"2016-12-12\",\"assetInterest\":\"0.01\",\"serviceCharge\":\"1.00\",\"maintenanceCharge\":\"2.00\",\"otherCharge\":\"3.00\",\"assetPrincipal\":\"1400.00\"},{\"assetRecycleDate\":\"2017-02-12\",\"assetInterest\":\"120.00\",\"serviceCharge\":\"02.00\",\"maintenanceCharge\":\"10.00\",\"otherCharge\":\"20.00\",\"assetPrincipal\":\"1600.00\"}]");
					try {
						String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams,getIdentityInfoMap(requestParams));
						System.out.println("---------->" + sr);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			});
			thread1.start();
			thread1.join();
//		Map<String, String> requestParams = new HashMap<String, String>();
//		requestParams.put("fn", "200001");
//		requestParams.put("uniqueId", "4c05b1ea-fc25-47eb-9c76-dcabd0271e1e");
//		requestParams.put("contractNo", "20160722测试-1");
//		requestParams.put("requestReason", "0");
//		requestParams.put("requestNo", UUID.randomUUID().toString());
//		requestParams.put("requestData", "[{\"assetRecycleDate\":\"2016-09-12\",\"assetInterest\":\"0.01\",\"serviceCharge\":\"0.00\",\"maintenanceCharge\":\"0.00\",\"otherCharge\":\"0.00\",\"assetPrincipal\":\"0.01\"},{\"assetRecycleDate\":\"2016-11-12\",\"assetInterest\":\"0.00\",\"serviceCharge\":\"0.00\",\"maintenanceCharge\":\"0.00\",\"otherCharge\":\"0.00\",\"assetPrincipal\":\"0.01\"}]");
//		String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
//		System.out.println(sr);
//		Map<String, String> requestParams = new HashMap<String, String>();
//		requestParams.put("fn", "200001");
//		requestParams.put("uniqueId", "");
//		requestParams.put("contractNo", "20160722测试-1");
//		requestParams.put("requestReason", "变更还款计划（20160722测试-1）");
//		requestParams.put("requestNo", "1");
//		requestParams.put("requestData", "[{\"assetInterest\":150,\"assetPrincipal\":0,\"assetRecycleDate\":\"2018-02-02\",\"maintenanceCharge\":0,\"otherCharge\":0,\"serviceCharge\":0},{\"assetInterest\":50,\"assetPrincipal\":0,\"assetRecycleDate\":\"2019-02-02\",\"maintenanceCharge\":0,\"otherCharge\":0,\"serviceCharge\":0}]");
//		String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
//		System.out.println(sr);
	}
	
}
