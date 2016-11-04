package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("INFO")
public class XmlPacketReqInfo {

	@XStreamAlias("FUNNAM")
	private String funName;//函数名
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	
}
