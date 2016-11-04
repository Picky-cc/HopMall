package com.zufangbao.sun.yunxin.log;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;

@Entity
@Table(name = "system_operate_log")
public class SystemOperateLog {
	@Id
	@GeneratedValue
	private Long id;

	private Long userId;

	private String ip;

	private String objectUuid;

	private LogFunctionType logFunctionType;

	private LogOperateType logOperateType;

	private String keyContent;

	private String updateContentDetail;

	private String recordContentDetail;

	private String recordContent;

	private Date occurTime;

	public SystemOperateLog(Long userId, String ip,
			LogFunctionType logFunctionType, LogOperateType logOperateType) {
		this.ip = ip;
	    this.objectUuid = UUID.randomUUID().toString();
		this.logFunctionType = logFunctionType;
		this.logOperateType = logOperateType;
		this.occurTime = new Date();
		this.userId = userId;
	}
	public SystemOperateLog(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getObjectUuid() {
		return objectUuid;
	}

	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
	}

	public LogFunctionType getLogFunctionType() {
		return logFunctionType;
	}

	public void setLogFunctionType(LogFunctionType logFunctionType) {
		this.logFunctionType = logFunctionType;
	}

	public LogOperateType getLogOperateType() {
		return logOperateType;
	}

	public void setLogOperateType(LogOperateType logOperateType) {
		this.logOperateType = logOperateType;
	}

	public String getKeyContent() {
		return keyContent;
	}

	public void setKeyContent(String keyContext) {
		this.keyContent = keyContext;
	}

	public String getRecordContentDetail() {
		return recordContentDetail;
	}

	public void setRecordContentDetail(String recordContextDetail) {
		this.recordContentDetail = recordContextDetail;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContext) {
		this.recordContent = recordContext;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getUpdateContentDetail() {
		return updateContentDetail;
	}

	public void setUpdateContentDetail(
			List<UpdateContentDetail> updateContextDeatilList) {
		this.updateContentDetail = JsonUtils
				.toJsonString(updateContextDeatilList);
	}
	
	public static SystemOperateLog createAssetSetLog(Long userId, AssetSet assetSet, String recordContent,
			String ip, LogFunctionType logFunctionType, LogOperateType logOperateType) {
		String uuid = assetSet.getAssetUuid();
		String keyContext = assetSet.getAssetNo();
		return createLog(userId, recordContent, ip, logFunctionType, logOperateType, uuid, keyContext);
	}
	
	public static SystemOperateLog creatContractLog(Long userId, Contract contract, String recordContent,
			String ip, LogFunctionType logFunctionType, LogOperateType logOperateType) {
		String uuid = contract.getUuid();
		String keyContext = contract.getContractNo();
		return createLog(userId, recordContent, ip, logFunctionType, logOperateType, uuid, keyContext);
	}
	
	public static SystemOperateLog createLog(Long userId, String recordContent, String ip,
			LogFunctionType logFunctionType, LogOperateType logOperateType, String uuid, String keyContext) {
		SystemOperateLog log = new SystemOperateLog();
		log.setUserId(userId);
		log.setObjectUuid(uuid);
		log.setKeyContent(keyContext);
		log.setLogFunctionType(logFunctionType);
		log.setLogOperateType(logOperateType);
		log.setOccurTime(new Date());
		log.setRecordContent(recordContent);
		log.setIp(ip);
		return log;
	}

}
