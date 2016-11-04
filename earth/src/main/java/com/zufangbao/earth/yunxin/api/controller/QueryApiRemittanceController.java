package com.zufangbao.earth.yunxin.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zufangbao.earth.yunxin.api.BaseApiController;

@Controller
@RequestMapping("/api/query")
public class QueryApiRemittanceController extends BaseApiController{

	private static final Log logger = LogFactory.getLog(QueryApiRemittanceController.class);
	
//	/**
//	 * 放款结果查询
//	 */
//	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + QUERY_REMITTANCE_RESULT}, method = RequestMethod.POST)
//	public @ResponseBody String queryRemittanceResult() {
//		return null;
//	}
	
}
