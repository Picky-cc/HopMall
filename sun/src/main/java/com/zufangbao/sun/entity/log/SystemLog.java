package com.zufangbao.sun.entity.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_log")
public class SystemLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5963389356814211653L;

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 日志类型 0:登录日志,1:操作日志
	 */
	@Enumerated(EnumType.ORDINAL)
	private LogType logType;
	
	private Long userId;
	
	private String ip;
	
	private String event;
	/**
	 * 日志内容
	 */
	private String content;

	public SystemLog() {
		super();
	}

	public SystemLog(Date operateTime, LogType logType, Long userId, String ip,
			String event, String content) {
		super();
		this.operateTime = operateTime;
		this.logType = logType;
		this.userId = userId;
		this.ip = ip;
		this.event = event;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

}
