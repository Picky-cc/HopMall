package com.zufangbao.sun.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;


public class GeneratorUtilsTest {

	@Test
	public void testCreateOrderNo() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			String generateOrderNo = GeneratorUtils.generateUnionPayNo();
			list.add(generateOrderNo);
			System.out.println(generateOrderNo);
		}
		HashSet<String> hs = new HashSet<String>(list );
		if(list.size() > hs.size()) {
			System.out.println("有重复！共重复 " + (list.size() - hs.size()));
		}
	}
	
}
