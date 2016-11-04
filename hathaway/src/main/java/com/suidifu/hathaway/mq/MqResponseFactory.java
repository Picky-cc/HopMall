package com.suidifu.hathaway.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.job.handler.TaskHandler;


/**
 * 
 * @author wukai
 *
 */
@Component
public class MqResponseFactory {
	
	@Autowired
	private TaskHandler taskHandler;
	
	private static Log logger = LogFactory.getLog(MqResponseFactory.class);

	public  String createSucMqResponse(String responseUuid,Object resultObject, boolean sync){
		return createMqResponse(new MqResponse(responseUuid, resultObject, null), sync);
	}
	public  String createErrorMqResponse(String responseUuid,Object exceptionObject, boolean sync){
		return createMqResponse(new MqResponse(responseUuid, null, exceptionObject), sync);
	}
	public  String createUnknownResponse(Object exceptionObject, boolean sync){
		return createMqResponse(new MqResponse("", null, exceptionObject), sync);
	}
	private String createMqResponse(MqResponse mqResponse, boolean sync){
		
		if(sync){
			return mqResponse.serializeToStrig();
		}
		
		Task taskInRedis = taskHandler.getOneTaskBy(mqResponse.getResponseUuid());;
		
		if(taskInRedis == null){
			
			logger.error("#MqResponseFactory# write back to redis occur error, taskInRedis is null  with request uuid["+mqResponse.getResponseUuid()+"]");
			
			return null;
		}
		taskHandler.updateTask(taskInRedis, mqResponse);
		
		return null;
	}
	
}
