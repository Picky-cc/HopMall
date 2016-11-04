/**
 * 
 */
package com.suidifu.hathaway.job.handler;

import org.aspectj.lang.ProceedingJoinPoint;

import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.Level;
import com.suidifu.hathaway.job.Stage;
import com.suidifu.hathaway.job.StageResult;
import com.suidifu.hathaway.mq.RemoteServiceContainer;

/**
 * @author wukai
 *
 */
public interface JobTaskHandler {
	
	public <T> StageResult<T> collectTaskResult(Job job,Class<T> clazz, Level level);

	public abstract void doStage(String jobIdentity, Stage stage, RemoteServiceContainer remoteServiceContainer, ProceedingJoinPoint  proceedingJointPoint, Object[] args) throws Throwable;

	public void reDeploy(StageResult<Boolean> stageResult);

	public StageResult<Boolean> collectTimeOutTask(Job job,
			Class<Boolean> class1, Level fst);

}
