package com.zufangbao.gluon.opensdk;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 租房宝提供的sdk主要提供了四个功能：<br>
 *  1. 提供了由合作方进入租房宝收银台的入口url，对应于<code>SignatureUtilsForPartner.java</code>中的<code>createCheckoutCounterUrl</code>方法；</br>
 *  2. 提供了从租房宝收银台返回到的url的签名校验，对应于<code>SignatureUtilsForPartner.java</code>中的<code>checkCallBackUrl</code>方法。<br>
 *  3. 提供了校验合作方订单是否可以支付的URL,对应于<code>SignatureUtilsForPartner.java</code>中的<code>createIsInforceOrderUrl</code>的方法。<br>
 *  4. 提供给合作方进入租房宝系统查询指定交易日的流水记录的入口。对应于<code>SignatureUtilsForPartner.java</code>中的<code>createRetrieveTransactionRecordUrl</code>方法。<br>
 * 介绍
 * @author wk
 *
 */
public class SignatureUtilsForPartner {
	
	private static final char PARAM_SPLIT_CHAR  = '&';
	
	private static final char EQUAL_SPLIT_CHAR  = '=';
	
	private static final char URL_SPLIT_CHAR  = '?';

	/**
	 ** 供合作方生成进入租房宝收银台的一次支付请求url
	 * @param zfbHost	   租房宝收银台Host
	 * @param appId        合作方的id，由租房宝提供
	 * @param appSecret    合作方的安全签名值，由租房宝提供
	 * @param contractTerm 本次支付请求所对应的应收款的唯一编号，由合作方提供
	 * @param payAmount    本次支付请求的支付金额，单位元，由合作方提供
	 * @param payNo        本次支付请求所对应的合作方的系统流水号，由合作方提供
	 * @param returnUrl    本次支付请求返回时的页面回调的网址，供合作方客户查看最新状态信息，由合作方提供
	 * @param notifyUrl    本次支付请求结果回调通知的网址，供合作方系统更新状态，由合作方提供
	 * @return
	 */
	public static String createCheckoutCounterUrl(String zfbHost,String appId,String appSecret,String orderNo,String paidRent,String  payNo,String returnUrl,String notifyUrl){
		
		Map<String,Object> paramsMap = convertCheckoutCounterUrlParams2Map(appId, appSecret, orderNo, payNo, paidRent, returnUrl,notifyUrl);
		
		String signature = makeMD5Signature(paramsMap);
		
		paramsMap.put("signature", signature);
		paramsMap.remove("appSecret");
		
		StringBuffer result = new StringBuffer();
		
		result.append(zfbHost)
					.append(URL_SPLIT_CHAR)
					.append(createParamsUrl(paramsMap));
		
		return result.toString();
		
	}
	/**
	 * 验证租方宝回调请求是否被冒用的校验功能，各个参数从回调url的以相应的参数名对应取出对应的参数值
	 * @param appId     合作方AppId 
	 * @param appSecret 合作方的安全签名值
	 * @param tradeNo   本次支付的支付渠道的流水号
	 * @param payNo     本次支付请求所对应的合作方的系统流水号，由合作方在发生支付请求时提供
	 * @param paidRent  本次支付多少金额
	 * @param signature 本次回调请求的签名
	 * @param retCode   支付结果code，code为0表示成功
	 * @param payDate   支付时间
	 * @return
	 */
	public static boolean checkCallBackUrl(String appId,String appSecret,String tradeNo,String  payNo,String paidRent,String retCode,String payDate,String signature){
		
		Map<String,Object> notifyParams = covertCallbackUrlParams2Map(appId, appSecret, tradeNo, payNo,paidRent, retCode, payDate);
		
		String signatureParams  = makeMD5Signature(notifyParams);
		
		return signature.equals(signatureParams);
		
	}
	/**
	 * 获取查询指定交易日的交易纪录url
	 * @param zfbUrl 租房宝查询地址，由租房宝提供
	 * @param appId 合作方AppId 
	 * @param appSecret 合作方的安全签名值
	 * @param tranDate 交易日格式为yyyy-MM-dd
	 * @return
	 */
	public static String createRetrieveTransactionRecordUrl(String zfbUrl,String appId,String appSecret,String tranDate){
		
		
		Map<String,Object> paramMap = convertRetrieveTranRecordParam2Map(appId, appSecret, tranDate);
		
		String signature = makeMD5Signature(paramMap);
		
		paramMap.put("signature", signature);
		paramMap.remove("appSecret");
		
		StringBuffer result = new StringBuffer();
		
		result.append(zfbUrl)
					.append(URL_SPLIT_CHAR)
					.append(createParamsUrl(paramMap));
		
		return result.toString();
		
	}
	/**
	 * 获取查询订单是否可以支付的请求的url
	 * @param appId 合作方AppId 
	 * @param appSecret 合作方的安全签名值
	 * @param orderNo 
	 * @return
	 */
	public static String createIsInforceOrderUrl(String zfbUrl,String appId,String appSecret,String orderNo){
		
		Map<String,Object> paramMap = convertIsOrderFileKeptParam2Map(appId, appSecret, orderNo);
		
		String signature = makeMD5Signature(paramMap);
		
		paramMap.put("signature", signature);
		paramMap.remove("appSecret");
		
		StringBuffer result = new StringBuffer();
		
		result.append(zfbUrl)
					.append(URL_SPLIT_CHAR)
					.append(createParamsUrl(paramMap));
		
		return result.toString();
		
	}
	
	public static String createEnterAppHome(String zfbUrl,String appId,String appSecret,String customerId){
		
		Map<String,Object> paramMap = convertEnterAppHomeParam2Map(appId, appSecret, customerId, null);
		
		String signature = makeMD5Signature(paramMap);
		
		paramMap.put("signature", signature);
		paramMap.remove("appSecret");
		
		StringBuffer result = new StringBuffer();
		
		result.append(zfbUrl)
					.append(URL_SPLIT_CHAR)
					.append(createParamsUrl(paramMap));
		
		return result.toString();
		
	}
	/**
	 * 将查询订单转换成map对象
	 * @param appId
	 * @param appSecret
	 * @param orderNo
	 */
	public static Map<String,Object> convertIsOrderFileKeptParam2Map(String appId,
			String appSecret, String orderNo) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("appId",appId);
		paramMap.put("appSecret", appSecret);
		paramMap.put("orderNo",encodeValue(orderNo));
		
		return paramMap;
	}
	/**
	 * 将查询订单转换成map对象
	 * @param appId
	 * @param appSecret
	 * @param unqiueBillId 
	 * @param orderNo
	 */
	public static Map<String,Object> convertEnterAppHomeParam2Map(String appId,
			String appSecret, String customerId, String unqiueBillId) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("appId",appId);
		paramMap.put("appSecret", appSecret);
		paramMap.put("customerId",customerId);
		paramMap.put("uniqueBillId", unqiueBillId);
	
		
		return paramMap;
	}
	/**
	 * 
	 * @param appId
	 * @param appSecret
	 * @param orderNo
	 * @param signature
	 * @return
	 */
	public static boolean checkOrderFileKept(String appId,String appSecret,String orderNo,String signature){
		
		Map<String,Object> paramMap = convertIsOrderFileKeptParam2Map(appId, appSecret, orderNo);
		
		return makeMD5Signature(paramMap).equals(signature);
	}
	/**
	 * 将交易参数转换成map对象
	 * @param appId
	 * @param appSecret
	 * @param tranTime
	 */
	public static Map<String,Object> convertRetrieveTranRecordParam2Map(String appId,
			String appSecret, String tranTime) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("appId",appId);
		paramMap.put("appSecret", appSecret);
		paramMap.put("tranDate",encodeValue(tranTime));
		
		return paramMap;
	}
	/***
	 * 校验合作方查询交易流水是否合法
	 * @param appId
	 * @param appSecret
	 * @param tranTime
	 * @param signature
	 * @return
	 */
	public static boolean checkRetrieveTodayTranRecordValid(String appId,String appSecret,String tranTime,String signature){
		
		Map<String,Object> paramMap = convertRetrieveTranRecordParam2Map(appId, appSecret, tranTime);
		
		return makeMD5Signature(paramMap).equals(signature);
	}
	/**
	 * @param appId
	 * @param appSecret
	 * @param tradeNo
	 * @param payNo
	 * @param retCode
	 * @param payDate
	 */
	public static Map<String,Object> covertCallbackUrlParams2Map(String appId, String appSecret,
			String tradeNo, String payNo, String paidRent,String retCode, String payDate) {
		
		Map<String,Object> notifyParamsMap = new HashMap<String,Object>();
		
		notifyParamsMap.put("appId", appId);
		notifyParamsMap.put("appSecret", appSecret);
		notifyParamsMap.put("tradeNo", encodeValue(tradeNo));
		notifyParamsMap.put("payNo", encodeValue(payNo));
		notifyParamsMap.put("paidRent", encodeValue(paidRent));
		notifyParamsMap.put("retCode", encodeValue(retCode));
		notifyParamsMap.put("payDate", encodeValue(payDate));
		
		return notifyParamsMap;
	}
	/**
	 * 将Map对象拼装成url格式
	 * @param parameters
	 * @return
	 */
	public  static String createParamsUrl(Map<String,Object> parameters){
		
		if(null == parameters || parameters.isEmpty()){
			return null;
		}
		Set<String> keySet = parameters.keySet();
		List<String> keyList = new ArrayList<String>(keySet);
		Collections.sort(keyList);
		StringBuffer sb = new StringBuffer();
		for(String key : keyList){
			sb.append(key).append(EQUAL_SPLIT_CHAR).append(parameters.get(key)).append(PARAM_SPLIT_CHAR);
		}
		String result = sb.toString();
		result = result.substring(0, result.lastIndexOf(PARAM_SPLIT_CHAR));
		
		return result;
		
	}
	/**
	 * 将收银台入参转换成Map
	 * @param appId
	 * @param appSecret
	 * @param orderNo
	 * @param payNo
	 * @param paidRent
	 * @param signature
	 * @return
	 */
	public  static Map<String,Object> convertCheckoutCounterUrlParams2Map(String appId,String appSecret,String orderNo,String  payNo,String paidRent,String returnUrl,String notifyUrl){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("appId",appId);
		map.put("appSecret",appSecret);
		map.put("orderNo",encodeValue(orderNo));
		map.put("payNo",encodeValue(payNo));
		map.put("paidRent", encodeValue(paidRent));
		map.put("returnUrl", encodeValue(returnUrl));
		map.put("notifyUrl", encodeValue(notifyUrl));
		
		return map;
	}
	/**
	 * 将参数值编码
	 * @param value
	 * @return
	 */
	public static String encodeValue(String value){
		
		return Base64.getUrlEncoder().encodeToString(value.getBytes(Charset.forName("utf-8")));
	}
	/**
	 * 将参数值解码
	 * @param value
	 * @return
	 */
	public static String decodeValue(String value){
		
		return new String(Base64.getUrlDecoder().decode(value.getBytes(Charset.forName("utf-8"))));
	}
	 /***
	  *MD5签名
	  * @param params
	  * @return
	  */
	 public static String makeMD5Signature(Map<String,Object> params){
		 
		String url = createParamsUrl(params);
		
		return makeMD5Encrypt(url);
		
	 }
	 /**
	  * md5加密
	  * @param value
	  * @return
	  */
	 public static String makeMD5Encrypt(String value) {
			
		 	if(null == value || value.isEmpty()){
		 		return null;
		 	}
			try 
			{
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				
				byte[] bytesOfValue = value.getBytes("UTF-8");
				byte[] digest = messageDigest.digest(bytesOfValue);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int i = 0; i < digest.length; i++) {
					stringBuffer.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
				}
				return stringBuffer.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	 
	 public static Map<String,Object> getMapFromHttpServletRequest(HttpServletRequest request) {
		 
		 if(null == request){
			 return Collections.emptyMap();
		 }
		 
		 Map<String,String[]> requestOfMapFromRequest = request.getParameterMap();
		 
		 
		 Map<String,Object> requestOfMap = new HashMap<String,Object>();
		 
		 Set<String> keySet = requestOfMapFromRequest.keySet();
			
		for (String key : keySet) {
			String[] values = requestOfMapFromRequest.get(key);
			String value = values.length > 0 ? values[0] : StringUtils.EMPTY;
			requestOfMap.put(key, value);
		}
		
		return requestOfMap;
	 }
}
