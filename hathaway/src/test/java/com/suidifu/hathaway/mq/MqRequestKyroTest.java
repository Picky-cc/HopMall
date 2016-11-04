package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.TypeReference;

public class MqRequestKyroTest {

	@Test
	public void MqRequestKyroSerializationTest()
	{
		
		MqRequestKryo mqRequest=new MqRequestKryo();
		Object [] args=new  Object[4];
		
		args[0]="test";
		Map<String,String> mapArgu=new HashMap();
		mapArgu.put("test2","test2");
		Map<String,BigDecimal> mapArgu2=new HashMap();
		mapArgu2.put("test3",BigDecimal.valueOf(10));
		Map<String,List<MqParameter>> mapArgu3=new HashMap();
		List<MqParameter> mqParameters=new ArrayList<MqParameter>();
		mqParameters.add(new MqParameter("dddss"));
		mapArgu3.put("test4", mqParameters);
	    
		args[1]=mapArgu;
		args[2]=mapArgu2;
		args[3]=mapArgu3;
		
		Type [] types=new Type[4];
		types[0]=String.class;
		types[1] =new TypeReference<Map<String,String> >(){}.getType();
		types[2] =new TypeReference<Map<String,BigDecimal> >(){}.getType();
		types[3] =new TypeReference<Map<String,List<MqParameter>> >(){}.getType();
		
		
		mqRequest.pushAllArguments(args);
		
		Object [] newargs=mqRequest.extractArguments(types);
		
		
		
		
		
		
		
	}
}
