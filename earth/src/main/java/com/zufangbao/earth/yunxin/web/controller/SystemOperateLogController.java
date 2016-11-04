package com.zufangbao.earth.yunxin.web.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.sun.yunxin.log.SystemOperateLogVO;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@Controller
@RequestMapping(value = "system-operate-log")
public class SystemOperateLogController {
	
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	
	@Autowired
	private SystemOperateLogService systemOperateLogService;

	@RequestMapping(value = "query", method = RequestMethod.GET)
	public @ResponseBody String querySystemOperateLog(
			@RequestParam(value = "objectUuid", required = false) String objectUuid, Page page) {
		Result result = new Result().data("size", 0).data("list", Collections.emptyList());
		try {
			if(StringUtils.isNotEmpty(objectUuid)) {
				page = new Page(page.getCurrentPage(), 5);
				long size = systemOperateLogService.countSystemOperaterLogsBy(objectUuid);
				List<SystemOperateLogVO> list = systemOperateLogHandler.getSystemOperateLogVOListBy(objectUuid, page);
				result.data("size", size);
				result.data("list", list);
			}
			return JsonUtils.toJsonString(result.success());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtils.toJsonString(result.fail());
		}
	}
	

}
