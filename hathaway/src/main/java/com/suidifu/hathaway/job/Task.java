/**
 * 
 */
package com.suidifu.hathaway.job;

import java.util.Date;

import org.springframework.beans.BeanUtils;

/**
 * @author wukai
 *
 */
public class Task {
	
	private String uuid;
	
	private String stageUuid;
	
	private String jobUUid;
	
	private String request;
	
	private String result;
	
	private ExecutingResult executingResult;
	
	private ExecutingStatus executingStatus;//succ failed  abandon
	
	private Date startTime;
	
	private Date endTime;
	
    private Date expiredTime;
    
    private String workingQueue;
    
    private Date createTime;
    
    private Date lastModifiedTime;
    
	public Task() {
		super();
	}
	
	public Task(String requestUuid,String stageUuid, String jobUUid, String request,
			String response, ExecutingResult executingResult, ExecutingStatus executingStatus,
			Date startTime, Date endTime, Date expiredTime, String workingQueue) {
		super();
		this.uuid = requestUuid;
		this.stageUuid = stageUuid;
		this.jobUUid = jobUUid;
		this.request = request;
		this.result = response;
		this.executingResult = executingResult;
		this.executingStatus = executingStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expiredTime = expiredTime;
		this.workingQueue = workingQueue;
		
		this.createTime = new Date();
		this.lastModifiedTime = new Date();
	}



	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStageUuid() {
		return stageUuid;
	}

	public void setStageUuid(String stageUuid) {
		this.stageUuid = stageUuid;
	}

	public String getJobUUid() {
		return jobUUid;
	}

	public void setJobUUid(String jobUUid) {
		this.jobUUid = jobUUid;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ExecutingResult getExecutingResult() {
		return executingResult;
	}

	public void setExecutingResult(ExecutingResult executingResult) {
		this.executingResult = executingResult;
	}

	public ExecutingStatus getExecutingStatus() {
		return this.executingStatus;
	}

	public void setExecutingStatus(ExecutingStatus executingStatus) {
		this.executingStatus = executingStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getWorkingQueue() {
		return workingQueue;
	}

	public void setWorkingQueue(String workingQueue) {
		this.workingQueue = workingQueue;
	}
	
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public void copyFrom(Task task){
		if(null == task){return;}
		BeanUtils.copyProperties(task, this, "uuid","createTime");
		this.setLastModifiedTime(new Date());
	}
    
}