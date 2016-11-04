package com.zufangbao.sun;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.demo2do.core.utils.JsonUtils;

public class AutoTestUtils {

	public static void dbExecute(String path,DataSource dataSource) {
		Resource script = new ClassPathResource(
				path);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(script);
		populator.setSqlScriptEncoding("UTF-8");
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	public static String getJsonFromTest(String resourcePath){
		Resource jsonResource = new ClassPathResource(resourcePath);
		try {
			InputStream is = jsonResource.getInputStream();
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			return new String(bytes);
		} catch (Exception e){
			e.printStackTrace();
		}
		return StringUtils.EMPTY;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getOrdinals(Enum[] enums){
		List<Integer> ordinals = Arrays.asList(enums).stream().map(e->e.ordinal()).collect(Collectors.toList());
		return JsonUtils.toJsonString(ordinals);
	}

}
