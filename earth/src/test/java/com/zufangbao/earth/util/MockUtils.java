package com.zufangbao.earth.util;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.zufangbao.gluon.resolver.JsonViewResolver;


@Component
public class MockUtils {
	
	@Autowired
	private JsonViewResolver jsonViewResolver;
	
	private static WireMockServer wireMockServer;

	
	private static final Log logger = LogFactory
			.getLog(MockUtils.class);
	
	public static String hostUrl = "http://127.0.0.1:8888";
	public static String moduleValue = "/testUrl";
	
    public void mockReceivableTransactionRequestByPost(String urlPrefix, List list,String key){
		
		stubFor(post(urlEqualTo(urlPrefix))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody(jsonViewResolver.sucJsonResult(key, list))));
    }
    
    public void mockPostRequest(String urlPrefix, Result result){
    	
    	mockPostRequest(urlPrefix, JsonUtils.toJsonString(result));
    }
    
    public void mockPostRequest(String urlPrefix, String jsonResult){
    	stubFor(post(urlEqualTo(urlPrefix))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody(jsonResult)));
    }
    
    public void mockGetRequest(String urlPrefix, String jsonResult){
    	stubFor(get(urlEqualTo(urlPrefix))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody(jsonResult)));
    }
    
    public void mockGetRquest(String urlPrefix, Result result){
    	stubFor(get(urlEqualTo(urlPrefix))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody(JsonUtils.toJsonString(result))));
    }
    
    public static void init(){
    	init("localhost",8888);
    }
    
    public static void end(){
    	wireMockServer.stop();
    }
    
    public static void init(int port){
    	init("localhost",port);
    }
    public static  void init(String host, int port){
    	wireMockServer = new WireMockServer(wireMockConfig().port(port));
    	WireMock.configureFor(host, port);
        wireMockServer.start(); 
        logger.info("mock start with host["+host+"],port["+port+"].");
    }
}
