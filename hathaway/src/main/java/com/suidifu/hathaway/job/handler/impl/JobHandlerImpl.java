/**
 * 
 */
package com.suidifu.hathaway.job.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.job.ExecutingResult;
import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.JobFactory;
import com.suidifu.hathaway.job.JobType;
import com.suidifu.hathaway.job.Level;
import com.suidifu.hathaway.job.Stage;
import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.job.handler.JobHandler;
import com.suidifu.hathaway.job.handler.TaskHandler;
import com.suidifu.hathaway.job.service.JobService;

/**
 * @author wukai
 *
 */
@Component("jobHandler")
public class JobHandlerImpl implements JobHandler {

	@Autowired
	private JobService jobService;
	
	@Value("#{config['issuerIdentity']}")
	private String issuerIdentity;
	
	@Value("#{config['issuerIP']}")
	private String issuerIP;
	
	@Autowired
	private TaskHandler taskHandler;

	@Override
	public Job registeJob(String fstBusinessCode, JobType jobType) {
		
		Job job = JobFactory.createNewJob(fstBusinessCode, jobType, String.format("jobType_%s_fstBusinessCode_%s", jobType.name(),fstBusinessCode), issuerIdentity, issuerIP);
		
		jobService.save(job);
		
		return job;
	}

	@Override
	public List<Stage> stagePlanning(Job job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStageDone(Stage stage, List<Task> taskList) {
		
		if(null == stage){
			return;
		}
		
		Job job = jobService.getJobBy(stage.getJobUuid());
		
		if(null == job){
			return;
		}
		
		ExecutingResult executingResult = calculateStageExecutingResult(taskList);
		
		job.updateStageDone(stage.getLevel(), executingResult);
		
		jobService.update(job);
	}

	private ExecutingResult calculateStageExecutingResult(List<Task> taskList) {
		
		for (Task task : taskList) {
			
			if(task.getExecutingResult() == ExecutingResult.FAIL){
				
				return ExecutingResult.SUC;
			}
		}
		return ExecutingResult.SUC;
	}

	@Override
	public void updateJobDone(Job job) {
		
		if(null == job){
			return;
		}
		job.updateJobDone();
		
		jobService.update(job);
	}

	@Override
	public Job registeStage(Job job, Level level, int retryTimes, long timeout, String beanName, String methodName, int chunkSize) {
		
		if(null == job){
			return null;
		}
		job.registeStage(level, retryTimes, timeout, beanName, methodName, chunkSize);
		
		jobService.update(job);
		
		return job;
	}

	@Override
	public Job registeStage(Job job, Level level, String beanName, String methodName, int chunkSize) {
		return registeStage(job, level, Job.DEFAULT_TRY_TIMES, Job.DEFAULT_TIME_OUT, beanName, methodName, chunkSize);
	}

	@Override
	public void updateJobProcessing(Job job) {
		
		if(null == job){
			return;
		}
		job.updateJobProcessing();
		jobService.update(job);
	}

	@Override
	public void updateStageProcessing(Stage stage) {
		// TODO Auto-generated method stub
		if(null == stage){
			return;
		}
		String jobIdentity = stage.getJobUuid();
		
		Job job = jobService.getJobBy(jobIdentity);
		
		job.updateStageProcessing(stage.getLevel());
		
		jobService.update(job);
	}
}