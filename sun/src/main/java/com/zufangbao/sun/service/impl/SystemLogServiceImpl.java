package com.zufangbao.sun.service.impl;

import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.log.SystemLog;
import com.zufangbao.sun.service.SystemLogService;

@Service("systemLogService")
public class SystemLogServiceImpl extends GenericServiceImpl<SystemLog> implements SystemLogService {

}
