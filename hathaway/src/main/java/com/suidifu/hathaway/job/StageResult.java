/**
 * 
 */
package com.suidifu.hathaway.job;

import java.util.Enumeration;
import java.util.List;

import com.suidifu.hathaway.util.KryoUtils;

/**
 * @author wukai
 * @param <T>
 *
 */
public class StageResult<T> implements java.io.Serializable,Enumeration<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3497867058040106614L;

	private String stageUuid;
	
	private String jobUuid;
	
	private List<String> result;
	
	private int size;
	
	private Class<T> clazz;
	
	private boolean isAllTaskDone;
	
	public StageResult(String stageUuid, String jobUuid,
			List<String> result, boolean isAllTaskDone) {
		super();
		this.stageUuid = stageUuid;
		this.jobUuid = jobUuid;
		this.result = result;
		this.size = result.size();
		this.isAllTaskDone = isAllTaskDone;
	}

	public List<String> getResult() {
		return result;
	}
	public String getStageUuid() {
		return stageUuid;
	}

	public void setStageUuid(String stageUuid) {
		this.stageUuid = stageUuid;
	}

	public String getJobUuid() {
		return jobUuid;
	}

	public void setJobUuid(String jobUuid) {
		this.jobUuid = jobUuid;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
	

	public boolean isAllTaskDone() {
		return isAllTaskDone;
	}

	public void setAllTaskDone(boolean isAllTaskDone) {
		this.isAllTaskDone = isAllTaskDone;
	}

	@Override
	public boolean hasMoreElements() {
		return this.size > 0;
	}
	
	

	@Override
	public T nextElement() {
		
		if(hasMoreElements()){
			
			T t = (T) KryoUtils.readClassAndObject(result.get(this.size -1 ), this.getClazz());
			
			--this.size;
					
			return t;
			
		}else{
			return null;
		}
	}

	public boolean hasTaskReachRetryTimes() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasTaskTimeOut() {
		// TODO Auto-generated method stub
		return false;
	}
}
