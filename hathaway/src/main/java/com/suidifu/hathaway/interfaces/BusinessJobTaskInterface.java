package com.suidifu.hathaway.interfaces;

import java.util.HashMap;
import java.util.List;

import com.suidifu.hathaway.mq.RemoteServiceContainer;

public interface BusinessJobTaskInterface {

	public int taskChunckSize();
	
	public HashMap<String,List<Object>> taskPlanning(String jobIdentity,RemoteServiceContainer remoteServiceContainer );
	
	public <T> T prepareTaskWorkingContext(Object taskIdentity,Class<T> workingContextClazz);
}
