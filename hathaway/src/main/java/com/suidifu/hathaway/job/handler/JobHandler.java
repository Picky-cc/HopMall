/**
 * 
 */
package com.suidifu.hathaway.job.handler;

import java.util.List;

import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.JobType;
import com.suidifu.hathaway.job.Level;
import com.suidifu.hathaway.job.Stage;
import com.suidifu.hathaway.job.Task;

/**
 * @author wukai
 *
 */
public interface JobHandler{

	public Job registeJob(String fstBusinessCode,JobType jobType);
	
	public Job registeStage(Job job,Level level,int retryTimes,long timeout, String beanName, String methodName, int chunkSize);
	
	public Job registeStage(Job job,Level level, String beanName, String methodName, int chunkSize);
	
	public List<Stage> stagePlanning(Job job);
	
	public void updateStageDone(Stage stage, List<Task> taskList);
	
	public void updateJobDone(Job job);

	public void updateJobProcessing(Job job);
	
	public void updateStageProcessing(Stage stage);
}
