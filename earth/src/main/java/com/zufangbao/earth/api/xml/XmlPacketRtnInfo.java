package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("INFO")
public class XmlPacketRtnInfo {

	@XStreamAlias("FUNNAM")
	private String funName;//函数名
	@XStreamAlias("RETCOD")
	private String retCode;//返回代码
	@XStreamAlias("ERRMSG")
	private String errMSG;//错误信息
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getErrMSG() {
		return errMSG;
	}
	public void setErrMSG(String errMSG) {
		this.errMSG = errMSG;
	}
	
	public XmlPacketRtnInfo(){
		
	}
	public XmlPacketRtnInfo(String funName, String retCode, String errMSG) {
		super();
		this.funName = funName;
		this.retCode = retCode;
		this.errMSG = errMSG;
	}
	
	
	
}
