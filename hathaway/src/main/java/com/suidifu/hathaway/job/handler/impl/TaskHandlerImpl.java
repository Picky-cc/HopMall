/**
 * 
 */
package com.suidifu.hathaway.job.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.JsonUtils;
import com.suidifu.hathaway.job.ExecutingResult;
import com.suidifu.hathaway.job.ExecutingStatus;
import com.suidifu.hathaway.job.JobFactory;
import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.job.handler.TaskHandler;
import com.suidifu.hathaway.mq.MqResponse;

/**
 * @author wukai
 *
 */
@Component("taskHandler")
public class TaskHandlerImpl implements TaskHandler {
	
	@Autowired
	private RedisTemplate<String,String> stageTaskSet;
	
	@Autowired
	private RedisTemplate<String, String> taskList;
	
	@PostConstruct
	public void init(){
//		stageTaskSet.setValueSerializer(new FastJsonRedisSerializer<>(Set.class));
	}

	@Override
	public void saveTask(Task task) {
		
		if(null == task){
			return;
		}
		taskList.opsForValue().set(task.getUuid(), JsonUtils.toJsonString(task));
		
	}
	

	@Override
	public Task getOneTaskBy(String taskUuid) {
		return  JsonUtils.parse(taskList.opsForValue().get(taskUuid),Task.class);
	}


	@Override
	public void updateTask(Task task, MqResponse mqResponse) {
		
		if(null == task){
			return;
		}
		
		String taskUuid = task.getUuid();
		
		Boolean taskExist = taskList.hasKey(taskUuid);
		
		if(null == taskExist || taskExist == false){
			return;
		}
		
		Task taskInRedis  = getOneTaskBy(taskUuid);
		
		if(null == taskInRedis){
			return;
		}
		taskInRedis.copyFrom(task);
		
		taskInRedis.setEndTime(new Date());
		taskInRedis.setLastModifiedTime(new Date());
	
		
		ExecutingResult executingResult  = null;
		ExecutingStatus executingStatus =  ExecutingStatus.DONE;
		
		if(mqResponse.isException_fired()){
			executingResult = ExecutingResult.FAIL;
			taskInRedis.setResult(null);
		}else{
			executingResult = ExecutingResult.SUC;
			taskInRedis.setResult(mqResponse.getResultJson());
		}
		taskInRedis.setExecutingResult(executingResult);
		taskInRedis.setExecutingStatus(executingStatus);
		
		taskList.delete(taskUuid);;
		
		taskList.opsForValue().set(taskUuid, JsonUtils.toJsonString(taskInRedis));
		
	}
	
	@Override
	public void setTaskSetToStage(String stageUuid, Set<String> taskUuidSet) {
		
		if(StringUtils.isBlank(stageUuid)){
			return;
		}
		if(null == taskUuidSet){
			taskUuidSet = new HashSet<String>();
		}
		stageTaskSet.opsForValue().set(stageUuid, JsonUtils.toJsonString(taskUuidSet));
		
	}

	@Override
	public Set<String> getAllTaskUuidListBy(String stageUuid) {
		
		if(StringUtils.isBlank(stageUuid)){
			
			return Collections.emptySet();
		}
		
		return JsonUtils.parse(stageTaskSet.opsForValue().get(stageUuid),Set.class);
	}
	@Override
	public void setTaskToStage(String stageUuid, String taskUuid) {
		
		if(StringUtils.isBlank(stageUuid) || StringUtils.isBlank(taskUuid)){
			return;
		}
		if(stageTaskSet.hasKey(stageUuid)){
			
			Set<String> valueInRedis = getAllTaskUuidListBy(stageUuid);
			
			valueInRedis.add(taskUuid);
			
			stageTaskSet.delete(stageUuid);
			
			setTaskSetToStage(stageUuid, valueInRedis);
			 
		}else{
			
			setTaskSetToStage(stageUuid, new HashSet<String>(){{add(taskUuid);}});
		}
		
	}

	@Override
	public void purgeStage(String stageUuid, String taskUuid) {
		
		if(StringUtils.isBlank(stageUuid) || StringUtils.isBlank(taskUuid)){
			return;
		}
		if(stageTaskSet.hasKey(stageUuid)){
			stageTaskSet.delete(stageUuid);
		}
		
	}

	@Override
	public void purgeAllTask(String stageUuid) {
		
		if(!stageTaskSet.hasKey(stageUuid)){
			return;
		}
		Set<String> taskUuids =  getAllTaskUuidListBy(stageUuid);
		
		taskList.delete(taskUuids);
		
		stageTaskSet.delete(stageUuid);
		
	}

	@Override
	public void saveAllTask(List<Task> taskList) {
		
		for (Task task : taskList) {
			saveTask(task);
		}
	}

	@Override
	public void setTaskSetToStage(String stageUuid, List<Task> taskList) {
		
		Set<String> taskUuidSet = extractTaskUuidSetFrom(taskList);
		
		setTaskSetToStage(stageUuid, taskUuidSet);;
	}

	private Set<String> extractTaskUuidSetFrom(List<Task> taskList) {
		
		Set<String> taskUuidSet = new HashSet<String>();
		
		for (Task task : taskList) {
			
			taskUuidSet.add(task.getUuid());
		}
		return taskUuidSet;
		
	}

	@Override
	public List<Task> getAllTaskListBy(String stageUuid) {
		
		Set<String> taskUuidSet = getAllTaskUuidListBy(stageUuid);
		
		if(CollectionUtils.isEmpty(taskUuidSet)){
			return Collections.emptyList();
		}
		
		List<Task> taskList = new ArrayList<Task>();
		
		for (String taskUuid : taskUuidSet) {
			
			taskList.add(getOneTaskBy(taskUuid));
		}
		return taskList;
	}

	@Override
	public boolean isAllTaskDone(String stageUuid) {
		
		List<Task> taskList = getAllTaskListBy(stageUuid);
		
		return JobFactory.isAllTaskDone(taskList);
	}

}
