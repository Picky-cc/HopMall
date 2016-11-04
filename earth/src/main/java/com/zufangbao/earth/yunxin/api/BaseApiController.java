package com.zufangbao.earth.yunxin.api;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_MER_ID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.gluon.resolver.PageViewResolver;
import com.zufangbao.sun.yunxin.service.DictionaryService;

public class BaseApiController {

	@Autowired
	public PageViewResolver pageViewResolver;

	@Autowired
	private ApiJsonViewResolver jsonViewResolver;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	public String getMerchantId(HttpServletRequest request) {
		return request.getHeader(PARAMS_MER_ID);
	}

	public String signSucResult(HttpServletResponse response) {
		return signSucResult(response, null, null);
	}
	
	public String signSucResult(HttpServletResponse response, String key, Object data) {
		return signSucResult(response, null, key, data);
	}
	
	public String signSucResult(HttpServletResponse response, String message, String key, Object data) {
		String result = jsonViewResolver.sucJsonResult(message, key, data);
		return getPriKeyAndSign(response, result);
	}

	public String signErrorResult(HttpServletResponse response, Exception e) {
		String result = jsonViewResolver.errorJsonResult(e);
		return getPriKeyAndSign(response, result);
	}
	
	public String signErrorResult(HttpServletResponse response, int error_code) {
		return signErrorResult(response, error_code, null);
	}
	
	public String signErrorResult(HttpServletResponse response, int error_code, String error_msg) {
		String result = jsonViewResolver.errorJsonResult(error_code, error_msg);
		return getPriKeyAndSign(response, result);
	}

	private String getPriKeyAndSign(HttpServletResponse response, String result) {
		String privateKey = dictionaryService.getPlatformPrivateKey();
		return ApiSignUtils.sign_and_return_result(response, result, privateKey);
	}

}
