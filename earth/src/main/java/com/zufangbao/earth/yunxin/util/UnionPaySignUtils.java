package com.zufangbao.earth.yunxin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.demo2do.core.utils.StringUtils;
import com.zufangbao.earth.yunxin.exception.UnionPayRequestParamException;

/**
 * 银联相关签名工具
 * 
 * @author louguanyang
 *
 */
public class UnionPaySignUtils {
	
	private static Log logger = LogFactory.getLog(UnionPaySignUtils.class);

	/**
	 * 空字符串 “”
	 */
	private static final String BLANK = "";
	/**
	 * MD5加密方式
	 */
	private static final String MD5 = "MD5";
	/**
	 * 字符 “&”
	 */
	private static final String AND = "&";
	/**
	 * 字符 “=”
	 */
	private static final String EQUAL = "=";
	/**
	 * 签名
	 */
	private static final String SIGN = "sign";
	/**
	 * 编码
	 */
	private static final String ENCODE = "utf-8";

	/**
	 * 将所有请求参数值按升序排序（sign不参与签名）
	 * 
	 * POST请求时需做URLEncode
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws UnionPayRequestParamException 
	 */
	public static String sortParamsToSign(Map<String, String> params) throws UnsupportedEncodingException, UnionPayRequestParamException {
		List<String> keyList = Arrays.asList(params.keySet().toArray(new String[params.size()]));
		Collections.sort(keyList);
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : keyList) {
			if (key.equals(SIGN)) {
				continue;
			}
			String value = params.get(key);
			if(StringUtils.isEmpty(value)) {
				logger.error("请求参数" + key + "为空！");
				throw new UnionPayRequestParamException();
			}
			stringBuilder.append(key).append(EQUAL).append(URLEncoder.encode(value, ENCODE)).append(AND);
		}
		if (params.size() > 1)
			stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
		return stringBuilder.toString();
	}

	/**
	 * MD5加密处理
	 * 
	 * @param src
	 * @return
	 */
	public final static String MD5LowerCase(String src) {
		StringBuffer buf = new StringBuffer(BLANK);
		try {
			MessageDigest digest = MessageDigest.getInstance(MD5);
			digest.update(src.getBytes(ENCODE));
			byte[] b = digest.digest();
			int i = 0;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BLANK;
		}
		return buf.toString();
	}
}
