/**
 * 
 */
package com.suidifu.hathaway.job.handler;

import java.util.List;
import java.util.Set;

import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.mq.MqResponse;

/**
 * @author wukai
 *
 */
public interface TaskHandler {
	
	public void saveTask(Task task);
	
	public void saveAllTask(List<Task> taskList);
	
	public void updateTask(Task task, MqResponse mqResponse);
	
	public Task getOneTaskBy(String taskUuid);
	
	public void setTaskSetToStage(String stageUuid,Set<String> taskUuidSet );
	
	public void setTaskSetToStage(String stageUuid,List<Task> taskList);
	
	public void setTaskToStage(String stageUuid,String taskUuid);

	public Set<String> getAllTaskUuidListBy(String stageUuid);
	
	public List<Task> getAllTaskListBy(String stageUuid);
	
	public boolean isAllTaskDone(String stageUuid);
	
	public void purgeStage(String stageUuid,String taskUuid);
	
	public void purgeAllTask(String stageUuid);
	
}
