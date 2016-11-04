package com.zufangbao.earth.api.test.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.sun.utils.StringUtils;

/**
 * 贷后接口测试用
 * @author zhanghongbing
 *
 */
public class BaseApiTestPost {
	
	private static final String TEST_MERCHANT_CREATE_SQL = "INSERT INTO `t_merchant_config` (`id`, `mer_id`, `secret`, `company_id`, `pub_key_path`, `pub_key`) VALUES (1, 't_merchant', '150e28698dcc8b689198f96b54b2e95a', 1, NULL, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl1BjinRJLLDiNc6jcOKW+nph9aNSWMaKMk0OxTdSATakyS7rwNxrLMFyJLkI9IpnHussBv1zgsHPUdZeRcHDkbcMdhYoRgpe3gZIVMJ09BMBjhAET4fensvk377L0Whzp+u9r9UxIWowH7YJuJe3yQ7R3RgYxzrPuTJYq/WeuUQIDAQAB');";
	//** 本地测试用 **//*
//	public static final String QUERY_URL_TEST = "http://127.0.0.1:9092/api/query";	
//	public static final String MODIFY_URL_TEST = "http://127.0.0.1:9090/api/modify";
//	public static final String COMMAND_URL_TEST = "http://127.0.0.1:9090/api/command";
	/** 测试发布用 **/

	public static final String QUERY_URL_TEST = "http://121.40.230.133:29084/api/query";
	public static final String MODIFY_URL_TEST = "http://yunxin.zufangbao.cn/api/modify";
	public static final String COMMAND_URL_TEST = "http://121.40.230.133:29084/api/command";
	
	public static final String TEST_MERID = "t_test_zfb";
	public static final String TEST_SECRET = "123456";
	
	/** t_merchant的私钥 **/
	public  String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOXUGOKdEkssOI1zqNw4pb6emH1o1JYxooyTQ7FN1IBNqTJLuvA3GsswXIkuQj0imce6ywG/XOCwc9R1l5FwcORtwx2FihGCl7eBkhUwnT0EwGOEARPh96ey+TfvsvRaHOn672v1TEhajAftgm4l7fJDtHdGBjHOs+5Mlir9Z65RAgMBAAECgYEArtAiUZR5yrYLGgTEhyWLZK+Le7CWKtv8MQL+tUlm/mST8s7JlVfEyJKzgCCwf4HnCJXbPiwJgFqW8B61uAmXw6cEoPftEnzvKBTyISt/iEf7DTWKGkDBnlYM9sFU6pU61jw17XEDQRtSBG6cfrlGSelqf25+c8onxu4YwTeLH/ECQQD/H69tPy0FYRvCJ5yXdXEVCKshNN01P+UdDzGtyysE/gmpalbewT+uznApa0qFntcYb8eSpUJzrUlItSCBGUpdAkEA5p4r3qF+4g5V7MBHm3+v1l9JKxYK76990AQJa122rfkY2EEVuvU+8KIAQpVflu/HpDe8QH4mQZTsZj24Skt8hQJAL5j2vrgRqzZB2ohPY8aKcXUrkEdvmdaw5SoHh7gm74iBvvTS/j4ppnBnZqLYxXMsCCgaoNZqPnCvAnygctWIFQJAHm2KLkKyohLwJV+tUwgC5E8IMWYkJUHLYNHXiFICE2xFaesUeel313oYfLCGvzx9493yubOrSoXitw63rR3OnQJBALwGSnGYodmJB5k7un0X6LlO4nSu/+SX9lweloZ1AUg15IuCIYxHAFKwOtOJmx/eMcITaLq8l1qzZ907UXY+Mfs=";
	
	/** 用于测试私钥错误，验签名失败 **/
	private String invalidPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN0yqPkLXlnhM+2H/57aHsYHaHXazr9pFQun907TMvmbR04wHChVsKVgGUF1hC0FN9hfeYT5v2SXg1WJSg2tSgk7F29SpsF0I36oSLCIszxdu7ClO7c22mxEVuCjmYpJdqb6XweAZzv4Is661jXP4PdrCTHRdVTU5zR9xUByiLSVAgMBAAECgYEAhznORRonHylm9oKaygEsqQGkYdBXbnsOS6busLi6xA+iovEUdbAVIrTCG9t854z2HAgaISoRUKyztJoOtJfI1wJaQU+XL+U3JIh4jmNx/k5UzJijfvfpT7Cv3ueMtqyAGBJrkLvXjiS7O5ylaCGuB0Qz711bWGkRrVoosPM3N6ECQQD8hVQUgnHEVHZYtvFqfcoq2g/onPbSqyjdrRu35a7PvgDAZx69Mr/XggGNTgT3jJn7+2XmiGkHM1fd1Ob/3uAdAkEA4D7aE3ZgXG/PQqlm3VbE/+4MvNl8xhjqOkByBOY2ZFfWKhlRziLEPSSAh16xEJ79WgY9iti+guLRAMravGrs2QJBAOmKWYeaWKNNxiIoF7/4VDgrcpkcSf3uRB44UjFSn8kLnWBUPo6WV+x1FQBdjqRviZ4NFGIP+KqrJnFHzNgJhVUCQFzCAukMDV4PLfeQJSmna8PFz2UKva8fvTutTryyEYu+PauaX5laDjyQbc4RIEMU0Q29CRX3BA8WDYg7YPGRdTkCQQCG+pjU2FB17ZLuKRlKEdtXNV6zQFTmFc1TKhlsDTtCkWs/xwkoCfZKstuV3Uc5J4BNJDkQOGm38pDRPcUDUh2/";
	
	public Map<String, String> getIdentityInfoMap(Map<String, String> requestParams) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("merId", TEST_MERID);
		headers.put("secret", TEST_SECRET);
		String signContent = ApiSignUtils.getSignCheckContent(requestParams);
		System.out.println(signContent);
		String sign = ApiSignUtils.rsaSign(signContent, privateKey);
		headers.put("sign", sign);
		return headers;
	}
	
	public String buildParams(Map<String, String> params) {
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
	
}
