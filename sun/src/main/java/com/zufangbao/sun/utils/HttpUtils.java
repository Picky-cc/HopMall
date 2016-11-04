package com.zufangbao.sun.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP请求相关
 * 
 * @author louguanyang
 *
 */
public class HttpUtils {
	private static CloseableHttpClient httpClient = null;
	private static int TIMEOUT = 30000;
	/** 空字符串 “” */
	private static final String BLANK = "";
	/** 编码 */
	public static final String ENCODE = "utf-8";

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setDefaultMaxPerRoute(40);
		cm.setMaxTotal(200);

		RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT)
				.setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

		httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig)
				.build();
	}

	public static String post(String url, Map<String, String> params, String encode) throws IOException {
		String result = BLANK;
		HttpPost post = new HttpPost(url);
		try {
			ArrayList<BasicNameValuePair> parameters = mapParamsToList(params);
			post.setEntity(new UrlEncodedFormEntity(parameters, encode));
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(post);
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
			try {
				result = EntityUtils.toString(httpEntity, encode);
				EntityUtils.consume(httpEntity);
			} catch (IOException e) {
				throw e;
			} finally {
				closeableHttpResponse.close();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			post.abort();
		}
		return result;
	}
	
	public static String post(String url, Map<String, String> params, Map<String, String> headers, String encode) throws IOException {
		String result = BLANK;
		HttpPost post = new HttpPost(url);
		try {
			ArrayList<BasicNameValuePair> parameters = mapParamsToList(params);
			post.setEntity(new UrlEncodedFormEntity(parameters, encode));
			if(headers != null) {
				for (Entry<String, String> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(post);
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
			try {
				result = EntityUtils.toString(httpEntity, encode);
				EntityUtils.consume(httpEntity);
			} catch (IOException e) {
				throw e;
			} finally {
				closeableHttpResponse.close();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			post.abort();
		}
		return result;
	}

	public static ArrayList<BasicNameValuePair> mapParamsToList(Map<String, String> params) {
		ArrayList<BasicNameValuePair> list = new ArrayList<>();
		for (String key : params.keySet()) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		return list;
	}
}
