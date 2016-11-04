package com.zufangbao.earth.yunxin.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.sms.SmsQuene;
import com.zufangbao.sun.yunxin.entity.sms.SmsTemplateEnum;
import com.zufangbao.sun.yunxin.entity.sms.model.SmsQueneQueryModel;
import com.zufangbao.sun.yunxin.handler.SmsQueneHandler;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.SmsQueneService;

@Controller
@RequestMapping("message")
@MenuSetting("menu-message")
public class SmsMessageController {

	@Autowired
	private SmsQueneService smsQueneService;
	@Autowired
	private SmsQueneHandler smsQueneHandler;
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-message-activate")
	public ModelAndView queryMessage(HttpServletRequest request, Page page,
			@RequestParam(value = "allowedSendStatus", required = false, defaultValue = "-1") int allowedSendStatus,
			@RequestParam(value = "clientId", required = false) String clientId,
			@RequestParam(value = "smsTemplateEnum", required = false, defaultValue = "-1") int smsTemplateEnum,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		List<SmsQueneQueryModel> smsQueneQueryModelList = smsQueneHandler.querySmsQueneQueryModelList(allowedSendStatus, clientId, smsTemplateEnum, page, startDate, endDate);
		boolean allowedSendFlag = dictionaryService.getSmsAllowSendFlag();

		ModelAndView modelAndView = new ModelAndView("message/message");
		modelAndView.addObject("smsQueneQueryModelList", smsQueneQueryModelList);
		modelAndView.addObject("allowedSendFlag", allowedSendFlag);

		String queryString = request.getQueryString();
		if(!StringUtils.isEmpty(queryString)) {
			Map<String, String> queries = StringUtils.parseQueryString(queryString);
			if (queries.containsKey("page")) {
				queries.remove("page");
			}
			modelAndView.addObject("queryString", StringUtils.toQueryString(queries));
		}
		modelAndView.addObject("allowedSendStatus", allowedSendStatus);
		modelAndView.addObject("clientId", clientId);
		modelAndView.addObject("smsTemplateEnum", smsTemplateEnum);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("smsTemplateEnumList", Arrays.asList(SmsTemplateEnum.values()));
		return modelAndView;
	}

	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@MenuSetting("submenu-message-activate")
	public @ResponseBody String activateMessage(Page page, Long smsQueneId) {

		Result result = new Result();
		try {
			SmsQuene smsQueue = smsQueneService
					.load(SmsQuene.class, smsQueneId);
			smsQueneHandler.activateSmsQuene(smsQueue);
			result.success().message("激活允许发送成功！！");
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("激活失败！！");
			return JsonUtils.toJsonString(result);
		}
		return JsonUtils.toJsonString(result);
	}

	@RequestMapping(value = "/reSend", method = RequestMethod.POST)
	@MenuSetting("submenu-message-activate")
	public @ResponseBody String reSendMessage(Long smsQueneId) {
		Result result = new Result();
		try {
			SmsQuene smsQueue = smsQueneService.load(SmsQuene.class, smsQueneId);
			smsQueneHandler.reSendMessage(smsQueue);
			result.success().message("已添加到短信发送队列！");
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("重新发送失败！");
			return JsonUtils.toJsonString(result);
		}
		return JsonUtils.toJsonString(result);
	}

	@RequestMapping(value = "/changeAllowSend", method = RequestMethod.POST)
	@MenuSetting("submenu-message-activate")
	public @ResponseBody String changeAllowSend(boolean allowedSendFlag) {
		Result result = new Result();
		try {
			dictionaryService.updateAllowedSendStatus(allowedSendFlag);
			result.success().message("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("操作失败！" + e.getMessage());
			return JsonUtils.toJsonString(result);
		}
		return JsonUtils.toJsonString(result);
	}
	
	@RequestMapping(value = "/sendSuccSms", method = RequestMethod.POST)
	@MenuSetting("submenu-message-activate")
	public @ResponseBody String sendSuccSms() {
		Result result = new Result();
		try {
			smsQueneHandler.sendSuccSms();
			result.success().message("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("操作失败！" + e.getMessage());
			return JsonUtils.toJsonString(result);
		}
		return JsonUtils.toJsonString(result);
	}

	@RequestMapping(value = "/deleteNotSuccSms", method = RequestMethod.POST)
	@MenuSetting("submenu-message-activate")
	public @ResponseBody String deleteNotSuccSms() {
		Result result = new Result();
		try {
			smsQueneHandler.deleteNotSuccSms();
			result.success().message("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("操作失败！" + e.getMessage());
			return JsonUtils.toJsonString(result);
		}
		return JsonUtils.toJsonString(result);
	}
	
}
