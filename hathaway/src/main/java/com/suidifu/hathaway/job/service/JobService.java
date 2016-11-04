/**
 * 
 */
package com.suidifu.hathaway.job.service;

import com.demo2do.core.service.GenericService;
import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.JobType;

/**
 * @author wukai
 *
 */
public interface JobService extends GenericService<Job> {

	public Job getJobBy(String jobUuid);
	
	public Job getJobBy(String fstBusinessCode,JobType jobType);
	
	public Job getJobBy(String fstBusinessCode,String sndBusinessCode,String trdBusinessCode,JobType jobType);
	
	public boolean hasRegeistedJob(String fstBusinessCode,JobType jobType);
	
}
