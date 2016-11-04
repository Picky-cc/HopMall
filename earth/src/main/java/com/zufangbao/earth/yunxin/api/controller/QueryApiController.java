package com.zufangbao.earth.yunxin.api.controller;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_FN_KEY_WITH_COMBINATORS;
import static com.zufangbao.earth.yunxin.api.constant.QueryOpsFunctionCodes.QUERY_REPAYMENT_PLAN;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zufangbao.earth.yunxin.api.BaseApiController;
import com.zufangbao.earth.yunxin.api.constant.QueryOpsFunctionCodes;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.RepaymentListApitHandler;
import com.zufangbao.earth.yunxin.api.handler.RepaymentPlanApiHandler;
import com.zufangbao.earth.yunxin.api.model.RepaymentListDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentListQueryModel;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanQueryModel;

@Controller
@RequestMapping("/api/query")
public class QueryApiController extends BaseApiController{

	private static final Log logger = LogFactory.getLog(QueryApiController.class);
	
	@Autowired
	private RepaymentPlanApiHandler repaymentPlanAPIHandler;
	
	
	@Autowired
	private RepaymentListApitHandler repaymentListApitHandler;
	/**
	 * 查询还款计划
	 */
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + QUERY_REPAYMENT_PLAN}, method = RequestMethod.POST)
	public @ResponseBody String queryRepaymentPlan(@ModelAttribute RepaymentPlanQueryModel queryModel, HttpServletResponse response) {
		try {
			if (queryModel == null) {
				return signErrorResult(response, ApiResponseCode.WRONG_FORMAT);
			}
			
			if(!queryModel.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS);
			}
			
			List<RepaymentPlanDetail> repaymentPlanDetails = repaymentPlanAPIHandler.queryRepaymentPlanDetail(queryModel);
			return signSucResult(response, "repaymentPlanDetails", repaymentPlanDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return signErrorResult(response, e);
		}
	}
	
	/**
	 * 查询还款清单接口
	 */
	
	@RequestMapping(value= "" ,params ={PARAMS_FN_KEY_WITH_COMBINATORS + QueryOpsFunctionCodes.QUERY_REPAYMENT_LIST}, method = RequestMethod.POST)
	public @ResponseBody String queryRepaymentList(@ModelAttribute RepaymentListQueryModel queryModel, HttpServletResponse response){
		try {
			if (queryModel == null) {
				return signErrorResult(response, ApiResponseCode.WRONG_FORMAT);
			}
			if(!queryModel.isVaild() ){
				return signErrorResult(response, ApiResponseCode.DATE_RANGE_ERROR);
			}
			if(!queryModel.isQueryDateBetweenThreeMonths()){
				return signErrorResult(response, ApiResponseCode.DATE_RANGE_NOT_ALLOWED_THAN_THREE_MONTHS);
			}
			
			List<RepaymentListDetail> repaymentListDetails= repaymentListApitHandler.queryRepaymentList(queryModel);
			return signSucResult(response, "repaymentListDetail", repaymentListDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#queryOverdueDeductResult occur error [requestNo : "+ queryModel.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}
		
	}
/*	//扣款查询接口
	@RequestMapping(value= "" ,params ={PARAMS_FN_KEY_WITH_COMBINATORS + QueryOpsFunctionCodes.QUERY_DEDUCT_RESULT}, method = RequestMethod.POST)
	public @ResponseBody String queryOverdueDeductResult(@ModelAttribute OverdueDeductResultQueryModel queryModel, HttpServletResponse response,HttpServletRequest request) {
		try {
			if(!queryModel.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, queryModel.getCheckFailedMsg());
			}
			
			//校验请求号，生成扣款查询记录
			deductApplicationLogHandler.checkAndSaveRequest(queryModel,request);
			
			DeductQueryResult queryResult = deductQueryApiHandler.apideductQuery(queryModel,request);
			
			return signSucResult(response,"DeductQueryResult",queryResult);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#queryOverdueDeductResult occur error [requestNo : "+ queryModel.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}
	}
*/
	
	
	
	
}
