/**
 * 
 */
package com.suidifu.hathaway.job.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.JobFactory;
import com.suidifu.hathaway.job.Level;
import com.suidifu.hathaway.job.Stage;
import com.suidifu.hathaway.job.StageResult;
import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.job.handler.JobHandler;
import com.suidifu.hathaway.job.handler.JobTaskHandler;
import com.suidifu.hathaway.job.handler.TaskHandler;
import com.suidifu.hathaway.mq.MqRequest;
import com.suidifu.hathaway.mq.RemoteServiceContainer;
import com.suidifu.hathaway.mq.client.adapter.MqClientAdapter;


/**
 * @author wukai
 *
 */
@Component("jobTaskHandler")
public class JobTaskHandlerImpl implements JobTaskHandler {

	@Autowired
	private JobHandler jobHandler;
	
	@Autowired
	private TaskHandler taskHandler;
	
	@Autowired
	private MqClientAdapter mqClientAdapter;
	
	private static Log logger = LogFactory.getLog(JobTaskHandlerImpl.class);
	
	private List<Task> synchronizedTaskPlanning(String jobIdentity,Stage stage,RemoteServiceContainer remoteServiceContainer, ProceedingJoinPoint proceedingJoinPoint, Object[] args) throws Throwable
	{
		logger.info("#synchronizedTaskPlanning# with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"]");
		
		Map<String,List<Object>> taskPlanGroup= (Map<String, List<Object>>) proceedingJoinPoint.proceed(args);
		
		//
		logger.info("#synchronizedTaskPlanning# with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"] with taskPlanGroup size["+taskPlanGroup.size()+"]");
		
		Map<String, List<Object>> synchronizedTaskPlane = synchronizeTaskPlan(remoteServiceContainer, taskPlanGroup);
		
		logger.info("#synchronizedTaskPlanning# with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"] with synchronizedTaskPlane size["+synchronizedTaskPlane.size()+"]");

		Map<String, List<List<Object>>> splited_chuncks=splitTasks(stage.getChunkSize(),synchronizedTaskPlane);
		
		logger.info("#synchronizedTaskPlanning# with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"] with splited_chuncks size["+splited_chuncks.size()+"]");

		List<Task> taskList = deployTasks(remoteServiceContainer,splited_chuncks, stage);
		
		logger.info("#synchronizedTaskPlanning# with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"] with taskList size["+taskList.size()+"]");

		return taskList;
	}

	private  Map<String, List<Object>> synchronizeTaskPlan(RemoteServiceContainer remoteServiceContainer,Map<String, List<Object>> taskPlanGroup) {
		
		Map<String,List<Object>> synchronizedTaskPlan = new HashMap<String,List<Object>>();
		
		for(String synchronizedWorkingSpace:taskPlanGroup.keySet())
		{
			//String hashIndex = remoteServiceContainer.calculateHasIndex(synchronizedWorkingSpace);
			String hashIndex = remoteServiceContainer.md5(synchronizedWorkingSpace);
			
			List<Object> taskWorkingContext =null;
			
			if(synchronizedTaskPlan.containsKey(hashIndex))
			{
				taskWorkingContext=synchronizedTaskPlan.get(hashIndex);
			}
			else{
				taskWorkingContext=new ArrayList<Object>();
			}
			
			taskWorkingContext.addAll(taskPlanGroup.get(synchronizedWorkingSpace));
			
			synchronizedTaskPlan.put(hashIndex, taskWorkingContext);
		}
		return synchronizedTaskPlan;
	}

	private  List<Task> deployTasks(RemoteServiceContainer remoteServiceContainer,Map<String, List<List<Object>>> splited_chuncks, Stage stage) throws Throwable {
		
		List<Task> taskList = new ArrayList<Task>();
		
		for (String synchronizedWorkingSpace : splited_chuncks.keySet()) {
			
			List<List<Object>> workingContextChunckList = splited_chuncks.get(synchronizedWorkingSpace);
			
			for (List<Object> workingContextChunck : workingContextChunckList) {
				
				Task task =  deployTask(synchronizedWorkingSpace,workingContextChunck,remoteServiceContainer, stage);
				
				taskList.add(task);
			}
		
		}
		return taskList;
	}

	public HashMap<String, List<List<Object>>>  splitTasks(int chunkSize,Map<String, List<Object>> synchronizedTaskPlane) {
		
		HashMap<String,List<List<Object>>> splited_chuncks = new HashMap<String,List<List<Object>>>();
		
		for (String synchronizedWorkingSpace : synchronizedTaskPlane.keySet()) {
			List<Object> workingContextChunck=synchronizedTaskPlane.get(synchronizedWorkingSpace);
			List<List<Object>> chunck_list=new ArrayList<List<Object>>();
			for(int startindex=0;startindex<workingContextChunck.size();)
			{
				int endIndex=Math.min(startindex+chunkSize,workingContextChunck.size());
				List<Object> raw=workingContextChunck.subList(startindex, endIndex);
				List<Object> chunck = new ArrayList<Object>(raw);
				startindex=endIndex;
				chunck_list.add(chunck);
			}
			splited_chuncks.put(synchronizedWorkingSpace, chunck_list);
			
		}
		return splited_chuncks;
	}
	
	public void doStage(String jobIdentity,Stage stage,RemoteServiceContainer remoteServiceContainer, ProceedingJoinPoint proceedingJointPoint, Object[] args) throws Throwable{
		
		if(JobFactory.isStageDone(stage)){
			
			logger.info("#doStage# the stage is done with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"]");
			
			return;
		}
		if(JobFactory.isStageAbandon(stage)){
			
			logger.info("#doStage# the stage is abandon with jobIdentity["+jobIdentity+"],stage uuid["+stage.getUuid()+"]");
			
			return;
		}
		
		if(JobFactory.isStageProcessing(stage)){
				
			String stageUuid = stage.getUuid();
			
			List<Task> taskList = taskHandler.getAllTaskListBy(stageUuid);
			
			if(JobFactory.isAllTaskDone(taskList))
			{	
				jobHandler.updateStageDone(stage, taskList);
				
				//taskHandler.purgeAllTask(stageUuid);
			}
		}
		if(JobFactory.isStageStart(stage))
		{
			jobHandler.updateStageProcessing(stage);
			
			List<Task>  taskList = synchronizedTaskPlanning(jobIdentity,stage,remoteServiceContainer,proceedingJointPoint, args);
		
			taskHandler.saveAllTask(taskList);
			
			taskHandler.setTaskSetToStage(stage.getUuid(), taskList);
			
		}
		//abandon
		
	}
	@Override
	public <T> StageResult<T> collectTaskResult(Job job,Class<T> clazz, Level level) {
		
		String stageUuid = job.getStageUuid(level);
		
		String jobUuid = job.getUuid();
		
		List<Task> taskList = taskHandler.getAllTaskListBy(stageUuid);
		
		logger.info("taskList size :"+taskList.size()+" with jobIdentity["+job.getUuid()+"]");
		
		if(!JobFactory.isAllTaskSucDone(taskList)){
			
			return JobFactory.createNoDoneStageResult(jobUuid, stageUuid);
		}
		List<String> result = collectResultFrom(taskList);
		
		logger.info("result size :"+taskList.size()+" with jobIdentity["+job.getUuid()+"]");
		
		StageResult<T> stageResult = new StageResult<T>(stageUuid, jobUuid, result, true);
		
		return stageResult;
		
	}
	private List<String> collectResultFrom(List<Task> taskList) {
		
		List<String> result = new ArrayList<String>();
		
		for (Task task : taskList) {
		
			result.add(task.getResult());
		}
		
		return result;
	}

		
	public Task deployTask(String synchronizedWorkingSpace,List<Object> working_context_chunck,RemoteServiceContainer remoteServiceContainer, Stage stage) throws Throwable{
		logger.info("deployTask## with parameters synchronizedWorkingSpace["+synchronizedWorkingSpace+"],stage uuid["+stage.getUuid()+"]");
		//todo genearate
		Date expiredTime = null;
				
		MqRequest mqRequest = MqRequest.createRequest("",stage.getBeanName(),stage.getMethodName(), false);
		
		mqRequest.pushAllArguments(new Object[]{working_context_chunck});
		
		Task task = JobFactory.createNewTask(mqRequest.getRequestUuid(), stage.getUuid(), stage.getJobUuid(), mqRequest, expiredTime, "queue_hash_index_"+synchronizedWorkingSpace);
		
		//mqClientAdapter.publishASyncRPCMessage(synchronizedWorkingSpace, mqRequest, remoteServiceContainer);
		mqClientAdapter.publishASyncRPCMessage(mqRequest, remoteServiceContainer);
		
		logger.info("enter into this Jobtask");
		
		return task;
		
	}

	@Override
	public void reDeploy(StageResult<Boolean> stageResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StageResult<Boolean> collectTimeOutTask(Job job,
			Class<Boolean> class1, Level fst) {
		// TODO Auto-generated method stub
		return null;
	}
}
