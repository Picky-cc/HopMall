/**
 * 
 */
package com.suidifu.hathaway.job;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.suidifu.hathaway.mq.MqRequest;

/**
 * @author wukai
 *
 */
public class JobFactory {

	public static boolean isJobCreated(Job job){
		return checkJobStatus(job, ExecutingStatus.CREATE);
	}
	
	public static boolean isJobDone(Job job){
		return checkJobStatus(job, ExecutingStatus.DONE);
	}
	public static boolean isJobAbandon(Job job){
		return checkJobStatus(job, ExecutingStatus.ABANDON);
	}
	private static boolean checkJobStatus(Job job,ExecutingStatus status){
		return job != null && job.getExcutingStatus() == status;
	}
	public static boolean isStageDone(Stage stage){
		return checkStageStatus(stage, ExecutingStatus.DONE);
	}
	public static boolean isStageAbandon(Stage stage){
		return checkStageStatus(stage, ExecutingStatus.ABANDON);
	}
	public static boolean isStageProcessing(Stage stage){
		return checkStageStatus(stage, ExecutingStatus.PROCESSING);
	}
	public static boolean isStageStart(Stage stage){
		return checkStageStatus(stage, ExecutingStatus.CREATE);
	}
	private static boolean checkStageStatus(Stage stage,ExecutingStatus executingStatus){
		return stage != null && stage.getExecutingStatus() == executingStatus;
	}
	public static boolean isAllStageDone(List<Stage> stageList){
		
		if(CollectionUtils.isEmpty(stageList)){
			return false;
		}
		for (Stage stage : stageList) {
			
			if(!isStageDone(stage)){
				
				return false;
			}
		}
		return true;
	}
	public static boolean isAllStageDone(Job job){
		return isAllStageDone(job.extractStageList());
	}
	public static Job createNewJob(String fstBusinessCode, JobType jobType,String jobName,String issuerIdentity,String issuerIP) {
		Job job = new Job(fstBusinessCode, StringUtils.EMPTY, StringUtils.EMPTY, jobType, jobName, new Date(), null, issuerIdentity, issuerIP);
		return job;
	}
	public static Task createNewTask(String requestUuid,String stageUuid,String jobUuid,MqRequest request,Date expiredTime, String workingQueue){
		
		Task task = new Task(requestUuid,stageUuid, jobUuid, request.toString(), null, null, ExecutingStatus.CREATE, new Date(), null, expiredTime, workingQueue);
		
		return task;
	}
	public static boolean isAllTaskDone(List<Task> taskList){
		
		if(CollectionUtils.isEmpty(taskList)){
			return false;
		}
		
		for (Task task : taskList) {
			
			if(!isTaskDone(task)){
				return false;
			}
		}
		return true;
	}
	public static boolean isTaskDone(Task task){
		return checkTaskStatus(task, ExecutingStatus.DONE);
	}
	private static  boolean checkTaskStatus(Task task,ExecutingStatus executingStatus){
		return task != null && task.getExecutingStatus() == executingStatus;
	}
	
	public static boolean isAllTaskSucDone(List<Task> taskList){
		return isAllTaskDone(taskList) && isAllTaskSuc(taskList);
	}
	
	public static boolean isAllTaskSuc(List<Task> taskList){
		
		for (Task task : taskList) {
			
			if(!isTaskSuc(task)){
				return false;
			}
		}
		return true;
	}
	public static boolean isTaskSuc(Task task){
		return checkTaskResult(task, ExecutingResult.SUC);
	}
	private static  boolean checkTaskResult(Task task,ExecutingResult executingResult){
		return task != null && task.getExecutingResult() == executingResult;
	}
	public static StageResult createNoDoneStageResult(String jobUuid,String stageUuid){
		
		StageResult stageResult = new StageResult(stageUuid, jobUuid, Collections.emptyList(), false);
		
		return stageResult;
	}
	
}
