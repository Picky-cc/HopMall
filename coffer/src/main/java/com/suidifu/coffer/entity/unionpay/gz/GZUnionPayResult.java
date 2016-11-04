package com.suidifu.coffer.entity.unionpay.gz;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.suidifu.coffer.entity.DebitResult;
import com.suidifu.coffer.util.DateUtils;

public class GZUnionPayResult extends DebitResult {
	
	public GZUnionPayResult() {
		
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
	
	@Override
	public void setResponsePacket(String responsePacket) {
		super.setResponsePacket(responsePacket);
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
