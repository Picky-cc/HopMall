/**
 * 
 */
package com.suidifu.hathaway.job.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.suidifu.hathaway.job.ExecutingStatus;
import com.suidifu.hathaway.job.Job;
import com.suidifu.hathaway.job.JobType;
import com.suidifu.hathaway.job.service.JobService;

/**
 * @author wukai
 *
 */
@Service("jobService")
public class JobServiceImpl extends GenericServiceImpl<Job> implements JobService {

	@Override
	public Job getJobBy(String jobUuid) {
		
		Filter filter = new Filter();
		
		filter.addEquals("uuid", jobUuid);
		
		List<Job> jobList = this.list(Job.class, filter);
		
		return CollectionUtils.isEmpty(jobList) ? null : jobList.get(0);
	}

	@Override
	public Job getJobBy(String fstBusinessCode, String sndBusinessCode,
			String trdBusinessCode, JobType jobType) {
		
		Filter filter = new Filter();
		
		if(StringUtils.isNotBlank(fstBusinessCode)){
			
			filter.addEquals("fstBusinessCode", fstBusinessCode);
		}
		
		if(StringUtils.isNotBlank(sndBusinessCode)){
			
			filter.addEquals("sndBusinessCode", sndBusinessCode);
		}
		if(StringUtils.isNotBlank(trdBusinessCode)){
			
			filter.addEquals("trdBusinessCode", trdBusinessCode);
		}
		
		if(null != jobType){
			
			filter.addEquals("jobType", jobType);
		}
		
		List<Job> jobList = this.list(Job.class, filter);
		
		return CollectionUtils.isEmpty(jobList) ? null : jobList.get(0);
		
	}

	@Override
	public boolean hasRegeistedJob(String fstBusinessCode, JobType jobType) {
		
		String hql = "SELECT COUNT(*) FROM Job where fstBusinessCode =:fstBusinessCode AND jobType =:jobType";
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		
		parameters.put("fstBusinessCode", fstBusinessCode);
		parameters.put("jobType",jobType);
		
		return genericDaoSupport.searchForInt(hql, parameters) > 0;
	}

	@Override
	public Job getJobBy(String fstBusinessCode, JobType jobType) {
		return getJobBy(fstBusinessCode, StringUtils.EMPTY, StringUtils.EMPTY, jobType);
	}

}
