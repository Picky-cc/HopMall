package com.suidifu.hathaway.mq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.demo2do.core.utils.JsonUtils;

public class MqParameterTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	

	@Test
	public void testForString() {
		
		String paramValue = "stringValue";
		
		MqParameter mqParameter = new MqParameter( paramValue);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		assertEquals(paramValue,newMqParameter.parseParamValue(String.class));
	}
	@Test
	public void testForList() {
		
		String paramValue = "stringValue";
		
		List<String> list  = new ArrayList<String>(){
			
			{add(paramValue);}
		};
		MqParameter mqParameter = new MqParameter( list);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		assertEquals(list,newMqParameter.parseParamValue(List.class));
	}
	@Test
	public void testForListCase2() {
		
		List<MqRequest> list  = new ArrayList<MqRequest>(){
			
			{add(new MqRequestJson("","beanName", "methodName", true));}
		};
		MqParameter mqParameter = new MqParameter(list);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		List<MqRequest> rtnMqRequestList = (List<MqRequest>) newMqParameter.parseParamValue(new TypeReference<List<MqRequest>>(){}.getType());
		
		for(int i = 0;i< list.size();i++){
			
			MqRequest expectedMqRequest = list.get(i);
			
			MqRequest actualMqRequest = rtnMqRequestList.get(i);
			
			if(!StringUtils.equals(expectedMqRequest.getBeanName(), actualMqRequest.getBeanName())){
				
				fail(String.format("beanName expected value %s ,but was %s",expectedMqRequest.getBeanName(), actualMqRequest.getBeanName()));
			}
			if(!StringUtils.equals(expectedMqRequest.getMethodName(), actualMqRequest.getMethodName())){
				
				fail(String.format("methodName expected value %s ,but was %s",expectedMqRequest.getMethodName(), actualMqRequest.getMethodName()));
			}
		}
	}
	/**
	 * 
	 */
	@Test
	public void testForMap() {
		
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		
		map.put("key1",new BigDecimal("121212"));
		map.put("key2",new BigDecimal("12"));
		
		MqParameter mqParameter = new MqParameter(map);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		Map<String,BigDecimal> rtnMap = (Map<String, BigDecimal>) newMqParameter.parseParamValue(new TypeReference<Map<String,BigDecimal>>(){}.getType());
		
		for (String key : map.keySet()) {
			
			BigDecimal rtnVluae = rtnMap.get(key);
			
			if(map.get(key).compareTo(rtnVluae) != 0){
				fail(String.format("key %s,expected value %s ,but was %s",key,map.get(key),rtnVluae));
			}
		}
		
	}
	@Test
	public void testForMapCase2() {
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("key1","value1");
		map.put("key2","value2");
		
		MqParameter mqParameter = new MqParameter( map);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		assertEquals(map,newMqParameter.parseParamValue(Map.class));
		
	}
	@Test
	public void testForMapCase3() {
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("key1","value1");
		map.put("key2","value2");
		
		MqParameter mqParameter = new MqParameter(map);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		assertEquals(map,newMqParameter.parseParamValue(Map.class));
		
	}
	@Test
	public void testForMapCase4() {
		
		Map<String,MqParameter> map = new HashMap<String,MqParameter>();
		
		map.put("key1",new MqParameter("b"));
		map.put("key2",new MqParameter(121));
		
		MqParameter mqParameter = new MqParameter(map);
		
		String mqParameterJson = JsonUtils.toJsonString(mqParameter);
		
		MqParameter newMqParameter = JsonUtils.parse(mqParameterJson, MqParameter.class);
		
		Map<String,MqParameter> rtnMqParameters = (Map<String, MqParameter>) newMqParameter.parseParamValue(new TypeReference<Map<String,MqParameter>>(){}.getType());
		
		for(String key : map.keySet()){
			
			MqParameter expectedMqParameter = map.get(key);
			
			MqParameter actualMqParameter = rtnMqParameters.get(key);
			
			if(!expectedMqParameter.getLazyParamValue().equals(actualMqParameter.getLazyParamValue())){
				
				fail(String.format("expected value %s,but was %s", expectedMqParameter.getLazyParamValue(),actualMqParameter.getLazyParamValue()));
			}
		}
		
	}
	@Test
	public void testParseMap() throws Exception {
		
		Map<String,BigDecimal> a = new HashMap<String,BigDecimal>();
		
		BigDecimal actualBg = new BigDecimal("2323");
		
		String key = "key";
		
		a.put(key,actualBg );
		
		String aJson = JsonUtils.toJsonString(a);
		
		Map<String,BigDecimal> b = JSON.parseObject(aJson, new TypeReference<Map<String,BigDecimal>>(){});
		
		assertEquals(0,actualBg.compareTo(b.get(key)));
		
	}

}

