package com.zufangbao.earth.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.log.SystemLog;
import com.zufangbao.sun.service.SystemLogService;

@Controller
@RequestMapping("logs")
@MenuSetting("menu-system")
public class LogsController {
	
	@Autowired
	private SystemLogService userLoginLogService;
	
	@RequestMapping(value="user-login-log")
	@MenuSetting("submenu-user-login-log")
	public ModelAndView showLogs(Page page){
		ModelAndView result = new ModelAndView("logs/user-login-log");
		List<SystemLog> logs = searchLogsByCreateTime(page);
		result.addObject("logs", logs);
		return result;
	}

	public List<SystemLog> searchLogsByCreateTime(Page page) {
		Order orderByOperateTime = new Order();
		orderByOperateTime.add("operateTime", "DESC");
		List<SystemLog> logs = this.userLoginLogService.list(SystemLog.class,orderByOperateTime,page);
		return logs;
	}
}
