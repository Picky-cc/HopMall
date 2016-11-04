package com.zufangbao.earth.yunxin.api.interceptor;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_FN_KEY;
import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_MER_ID;
import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_SECRET;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.ApiJsonViewResolver;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.gluon.util.RequestUtil;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.api.ApiStatus;
import com.zufangbao.sun.yunxin.entity.api.TApiConfig;
import com.zufangbao.sun.yunxin.entity.api.TMerConfig;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.TApiConfigService;
import com.zufangbao.sun.yunxin.service.TMerConfigService;

/**
 * 接口访问拦截器
 * @author zhanghongbing
 *
 */
public class ApiAccessInterceptor extends HandlerInterceptorAdapter{

	private static final Log logger = LogFactory.getLog(ApiAccessInterceptor.class);
	
	@Autowired
	private TApiConfigService tApiConfigService;
	
	@Autowired
	private ApiJsonViewResolver jsonViewResolver;
	
	@Autowired
	private TMerConfigService tMerConfigService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		try {
			
			String requestURI = request.getRequestURI();
			String apiUrl = requestURI.endsWith("/") ? requestURI.substring(0, requestURI.length() - 1) : requestURI;
			String fnCode = request.getParameter(PARAMS_FN_KEY);
			
			logger.info("#" + DateUtils.getCurrentTimeMillis() + " : " + IpUtil.getIpAddress(request) + " CALL #API : "+ apiUrl + " #FN : " + fnCode + " #PARAMS : " + request.getParameterMap());
			
			//校验接口是否存在
			List<TApiConfig> tApiConfigs = tApiConfigService.getApiConfigListBy(apiUrl);
			if(CollectionUtils.isEmpty(tApiConfigs)) {
				writeJsonResult(response, ApiResponseCode.API_NOT_FOUND);
				return false;
			}

			//校验接口功能代码是否存在
			Optional<TApiConfig> optionalTApiConfig = tApiConfigs.stream()
					.filter(tac -> StringUtils.equals(tac.getFnCode(), fnCode))
					.findFirst();
			if(fnCode == null || !optionalTApiConfig.isPresent()) {
				writeJsonResult(response, ApiResponseCode.INVALID_FN_CODE);
				return false;
			}
			
			//接口启用状态校验
			TApiConfig tApiConfig = optionalTApiConfig.get();
			if(tApiConfig.getApiStatus() == ApiStatus.CLOSED) {
				writeJsonResult(response, ApiResponseCode.API_UNAVAILABLE);
				return false;
			}
			
			verifySign(request);
			
		} catch (RuntimeException e) { 
			return processRuntimeException(response, e);
		} catch (Exception e) {
			writeJsonResult(response, ApiResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}

	private boolean processRuntimeException(HttpServletResponse response, RuntimeException e) throws IOException {
		e.printStackTrace();
		if( e instanceof ApiException ) { 
			int error_code = ((ApiException) e).getCode();
			switch (error_code) {
			case ApiResponseCode.SIGN_MER_CONFIG_ERROR:
				writeJsonResult(response, ApiResponseCode.SIGN_MER_CONFIG_ERROR);
				return false;
			case ApiResponseCode.SIGN_VERIFY_FAIL:
				writeJsonResult(response, ApiResponseCode.SIGN_VERIFY_FAIL);
				return false;
			default:
				break;
			}
		}
		writeJsonResult(response, ApiResponseCode.SYSTEM_ERROR);
		return false;
	}
	
	private void verifySign(HttpServletRequest request) {
		String merId = request.getHeader(PARAMS_MER_ID);
		String secret = request.getHeader(PARAMS_SECRET);
		vaildateHeader(merId, secret);
		TMerConfig merConfig = tMerConfigService.getTMerConfig(merId, secret);
		Map<String, String> requestParams = RequestUtil.getRequestParams(request);
		if(!ApiSignUtils.verifySign(requestParams, request, merConfig)) 
			throw new ApiException(ApiResponseCode.SIGN_VERIFY_FAIL);
	}
	
	private void vaildateHeader(String merId, String secret) {
		if (StringUtils.isEmpty(merId) || StringUtils.isEmpty(secret))
			throw new ApiException(ApiResponseCode.SIGN_MER_CONFIG_ERROR);
	}
	
	private void writeJsonResult(HttpServletResponse response, int code) throws IOException {
		response.setContentType("application/json");
 		response.setCharacterEncoding("utf-8");
 		PrintWriter out = response.getWriter();
 		String jsonStr = signErrorResult(response, code);
 		out.write(jsonStr);
 		out.close();
	}

	public String signErrorResult(HttpServletResponse response, int error_code) {
		String result = jsonViewResolver.errorJsonResult(error_code);
		String privateKey = dictionaryService.getPlatformPrivateKey();
		return ApiSignUtils.sign_and_return_result(response, result, privateKey);
	}
	
}