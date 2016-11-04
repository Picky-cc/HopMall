package com.zufangbao.gluon.opensdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StreamUtils;

import com.demo2do.core.entity.Result;

public class HttpClientUtils {
	
	private static final Log logger = LogFactory.getLog(HttpClientUtils.class);

	private static final int DEFAULT_TIME_OUT = 60000;
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static final String DATA_RESPONSE_PACKET = "responsePacket";
	
	/**
	 * 执行HttpPost请求
	 * @param url 请求地址
	 * @param requestBody 请求数据包
	 * @return 结果
	 */
	public static Result executePostRequest(String url, String requestBody, Map<String, String> headerParams){
		return executePostRequest(url, new StringEntity(requestBody, DEFAULT_CHARSET), headerParams);
	}
	
	/**
	 * 执行HttpPost请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 结果
	 */
	public static Result executePostRequest(String url, Map<String, String> params, Map<String, String> headerParams){
		try {
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
			if(params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
		
			return executePostRequest(url, new UrlEncodedFormEntity(formParams, DEFAULT_CHARSET), headerParams);
		} catch (UnsupportedEncodingException e) {
			logger.error("#executePostRequest,不支持的字符编码!");
			e.printStackTrace();
			return new Result().message("不支持的字符编码!");
		}
	}
	
	/**
	 * 执行HttpPost请求
	 * @param url 请求地址
	 * @param reqEntity 请求实体
	 * @return 结果
	 */
	private static Result executePostRequest(String url, HttpEntity reqEntity, Map<String, String> headerParams){
		HttpPost httpPost = new HttpPost(url);
		Result result = new Result();
		try {
			httpPost.setEntity(reqEntity);
			if(headerParams != null) {
				for (Entry<String, String> entry : headerParams.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_TIME_OUT).setConnectTimeout(DEFAULT_TIME_OUT).setConnectionRequestTimeout(DEFAULT_TIME_OUT).build();
			httpPost.setConfig(requestConfig);

			HttpResponse httpResp = httpclient.execute(httpPost);
			
			int statusCode = httpResp.getStatusLine().getStatusCode();
			HttpEntity rspEntity = httpResp.getEntity();
			
			InputStream in = rspEntity.getContent();
			String strResp = StreamUtils.copyToString(in, Charset.forName(DEFAULT_CHARSET));
			result.data(DATA_RESPONSE_PACKET,strResp);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("#executePostRequest,http响应失败！(" + statusCode + ")");
				return result.message("http响应失败！(" + statusCode + ")");
			}
			return result.success();
		} catch (ConnectException e) {
			logger.error("#executePostRequest,服务器请求超时！");
			return result.message("服务器请求超时！");
		} catch (SocketTimeoutException e) {
			logger.info("#executePostRequest,服务器响应超时!");
			return result.success();
		} catch (IOException e) {
			logger.error("#executePostRequest,网络异常!");
			e.printStackTrace();
			return result.message("网络异常!");
		} catch (Exception e) {
			logger.error("#executePostRequest,系统错误!" + e.getMessage());
			e.printStackTrace();
			return result.message("系统错误!");
		} finally {
			//释放连接
			httpPost.releaseConnection();
		}
	}
	
}
