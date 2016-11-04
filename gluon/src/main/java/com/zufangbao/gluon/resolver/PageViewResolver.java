package com.zufangbao.gluon.resolver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.gluon.spec.global.PageViewSpec;

@Component
public class PageViewResolver {

	public ModelAndView redirectPageSpce(String viewName, Map<String, Object> data) {
		ModelAndView modelAndView = new ModelAndView("redirect:/"+viewName);
		modelAndView.addAllObjects(data);
		return modelAndView;
	}

	public ModelAndView redirectPageSpec(String viewName, String key, Object data) {
		Map<String,Object> dataMap  = new HashMap<String,Object>();
		dataMap.put(key,data);
		return redirectPageSpce(viewName,dataMap);
	}

	public ModelAndView redirectPageSpce(String viewName) {
		return redirectPageSpce(viewName,null);
	}
	
	public ModelAndView errorSpec() {
		return errorSpec(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
	}
	
	public ModelAndView errorSpec(String message) {
		return pageSpec(PageViewSpec.PAGE_ERROR, GlobalSpec.PARAM_MESSAGE, message);
	}
	
	public ModelAndView errorModalSpec(String message) {
		return pageSpec(PageViewSpec.PAGE_ERROR_MODAL, GlobalSpec.PARAM_MESSAGE, message);
	}
	
	public ModelAndView pageSpec(String viewName,Map<String, Object> data){
		
		ModelAndView modelAndView = new ModelAndView(viewName);
		
		modelAndView.addAllObjects(data);
		
		return modelAndView;
	}
	
	public ModelAndView pageSpec(String viewName, String key, Object data) {
		
		ModelAndView modelAndView = new ModelAndView(viewName);
		
		modelAndView.addObject(key, data);
		
		return modelAndView;
	}

}
