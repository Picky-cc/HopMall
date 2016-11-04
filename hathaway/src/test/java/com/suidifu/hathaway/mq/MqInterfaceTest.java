/**
 * 
 */
package com.suidifu.hathaway.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.suidifu.hathaway.mq.MqParameter;
import com.suidifu.hathaway.voucher.VoucherParameter;

/**
 * @author wukai
 *
 */
@Component("mqInterfaceTest")
public class MqInterfaceTest {
	
	public void testAsync(){
		System.out.println("async method");
	}
	public String testSyncString(String a){
		return a+"b";
	}
	public int testSyncInt(int a){
		return a+12;
	}
	public boolean testSyncBoolean(boolean a){
		return !a;
	}
	public Map<String,Integer> testSyncMap(String key,int a){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		map.put(key, a);
		
		return map;
	}
	public List<String> testSyncList(String a,String b){
		
		List<String> list = new ArrayList<String>();
		
		list.add(b);
		list.add(a);
		
		return list;
	}
	public List<MqParameter> testSyncMqParameterList(String lazyParamValue){
		
		List<MqParameter> parameters = new ArrayList<MqParameter>();
		
		parameters.add(new MqParameter(lazyParamValue));
		
		return parameters;
	}
	public Map<String,MqParameter> testSyncMqParameterMap(String key,String lazyParamValue){
		
		Map<String,MqParameter> map = new HashMap<String,MqParameter>();
		
		map.put(key, new MqParameter(lazyParamValue));
		
		return map;
	}
	public MqParameter testSyncMqParameter(String lazyParamValue){
		
		return new MqParameter(lazyParamValue);
	}
	public List<VoucherParameter> testCheckSourceDocument(List<VoucherParameter> voucherParameters){
		return voucherParameters;
	}
	
	
}
