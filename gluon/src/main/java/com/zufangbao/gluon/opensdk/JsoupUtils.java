/**
 * 
 */
package com.zufangbao.gluon.opensdk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;

/**
 * @author wukai
 *
 */
public abstract class JsoupUtils {
	
	private static final int DEFAULT_TIME_OUT = 60000;
	
	public static Result get(String url) throws Exception{
		
		return get(url, null);
	}
	
	public static Result get(String url,String key,Object value) throws Exception{
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put(key, value);
		
		return get(url, data);
	} 
	public static Result get(String url,String key,Object value,int timeout) throws Exception{
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put(key, value);
		
		return ajax(url, Method.GET, timeout, data);
	}
	public static Result get(String url,Map<String,Object> data) throws Exception{
		
		return ajax(url, Method.GET, DEFAULT_TIME_OUT, data);
	}
	public static Result get(String url,Map<String,Object> data,int timeout) throws Exception{
		
		return ajax(url, Method.GET, timeout, data);
	}
	public static Result post(String url,String key,Object value) throws Exception{
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put(key, value);
		
		return post(url, data);
	} 
	public static Result post(String url,String key,Object value,int timeout) throws Exception{
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put(key, value);
		
		return ajax(url, Method.POST, timeout, data);
	} 
	public static Result post(String url,Map<String,Object> data) throws Exception{
		
		return ajax(url, Method.POST, DEFAULT_TIME_OUT, data);
	}
	public static Result post(String url,Map<String,Object> data,int timeout) throws Exception{
		
		return ajax(url, Method.POST, timeout, data);
	}
	public static Result ajax(String url,Method jsoupMethod,int timeout,Map<String,Object> data) throws Exception{
		
		Connection connection = Jsoup.connect(url).method(jsoupMethod);
		
		if(null!= data){
			
			connection.data(convertTo(data));
		}
		Response response = connection
				 .timeout(timeout)
				 .execute();
		
		return JsonUtils.parse(response.body(),Result.class);
	}
	private static Map<String,String> convertTo(Map<String,Object> data){
		
		Set<String> keySet = data.keySet();
		
		Map<String,String> result = new HashMap<String,String>();
		
		for(String key : keySet){
			
			if(data.get(key) instanceof String){
				
				result.put(key, data.get(key).toString());
				
			}else{
				
				result.put(key, JsonUtils.toJsonString(data.get(key)));
				
			}
			
		}
		return result;
	}
	
	public static Response getIgnoreContentType(String url, Map<String,Object> data) throws IOException{
        Connection connection = Jsoup.connect(url).method(Method.GET);
        if(null!= data){
            
            connection.data(convertTo(data));
        }
        Response response = connection.ignoreContentType(true)
                .timeout(DEFAULT_TIME_OUT).execute();
        return response;
    }
    
    public static Response postIgnoreContentType(String url, Map<String,Object> data) throws IOException{
        Connection connection = Jsoup.connect(url).method(Method.POST);
        
        if(null!= data){
            
            connection.data(convertTo(data));
        }
        Response response = connection.ignoreContentType(true)
                .timeout(DEFAULT_TIME_OUT).execute();
        return response;
        
    }
}
