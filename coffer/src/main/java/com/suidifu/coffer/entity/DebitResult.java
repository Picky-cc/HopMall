package com.suidifu.coffer.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class DebitResult {
	public static final String SUCCESS = "0";
	
	public static final String FAIL = "1";
	
	protected String code = FAIL; //0：成功， 1：失败
	
	protected String message; //信息
	
	protected Map<String, Object> data = new HashMap<String, Object>(); //数据
	
	protected String retCode; //原始响应code
	
	protected String retMsg; //原始响应msg
	
	protected String reqNo; //请求序列号
	
	protected String requestPacket; //请求数据包
	
	protected String responsePacket; //响应数据包
	
	protected String sendTime; //报文发送时间
	
	protected String receiveTime; //报文接收时间

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getRequestPacket() {
		return requestPacket;
	}

	public void setRequestPacket(String requestPacket) {
		this.requestPacket = requestPacket;
	}

	public String getResponsePacket() {
		return responsePacket;
	}

	public void setResponsePacket(String responsePacket) {
		this.responsePacket = responsePacket;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	
}
