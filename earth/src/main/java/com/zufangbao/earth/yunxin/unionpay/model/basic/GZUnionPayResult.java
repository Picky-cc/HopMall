package com.zufangbao.earth.yunxin.unionpay.model.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zufangbao.sun.utils.DateUtils;

/**
 * 广州银联接口执行结果信息模型
 * @author zhanghongbing
 *
 */
public class GZUnionPayResult{
	
	public static final String SUCCESS = "0";
	
	public static final String FAIL = "1";
	
	private String code = FAIL; //0：成功， 1：失败
	
	private String message; //信息
	
	private Map<String, Object> data = new HashMap<String, Object>(); //数据
	
	private String retCode; //原始响应code
	
	private String retMsg; //原始响应msg
	
	private String reqNo; //请求序列号
	
	private String requestPacket; //请求数据包
	
	private String responsePacket; //响应数据包
	
	private String sendTime; //报文发送时间
	
	private String receiveTime; //报文接收时间

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
		if(StringUtils.isNotEmpty(responsePacket)){
			try {
				Document document = DocumentHelper.parseText(responsePacket);
				Element infoEle = (Element) document.selectSingleNode("/GZELINK/INFO");
				this.retCode = infoEle.elementTextTrim("RET_CODE");
				this.retMsg = infoEle.elementTextTrim("ERR_MSG");
				this.reqNo = infoEle.elementTextTrim("REQ_SN");
			} catch (DocumentException e) {
				this.fail().message("XML解析错误！");
				e.printStackTrace();
			}
		}
	}

	public GZUnionPayResult() {
	
	}
	
	public GZUnionPayResult(String code) {
		this.code = code;
	}
	
	public GZUnionPayResult success() {
		this.code = SUCCESS;
		return this;
	}
	
	public GZUnionPayResult fail() {
		this.code = FAIL;
		return this;
	}
	
	public GZUnionPayResult message(String message) {
		this.message = message;
		return this;
	}
	
	public GZUnionPayResult data(String key, Object value) {
		this.data.put(key, value);
		return this;
	}
	
	public boolean isValid() {
		return StringUtils.equals(this.code, SUCCESS);
	}
	
	public Object get(String key) {
		return this.data.get(key);
	}
	
	public Date getSendDate() {
		return DateUtils.parseDate(this.sendTime, "yyyyMMddHHmmss");
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
	
	public String concactRetCodeAndRetMsg() {
		String retCodeStr = this.retCode == null ? "" :this.retCode;
		String retMsgStr = this.retMsg == null ? "" :this.retMsg;
		return String.join(" : ", retCodeStr, retMsgStr);
	}

	@Override
	public String toString() {
		return "GZUnionPayResult [code=" + code + ", message=" + message
				+ ", data=" + data + ", retCode=" + retCode + ", retMsg="
				+ retMsg + ", reqNo=" + reqNo + ", requestPacket="
				+ requestPacket + ", responsePacket=" + responsePacket
				+ ", sendTime=" + sendTime + ", receiveTime=" + receiveTime
				+ "]";
	}
	
	
	
}
