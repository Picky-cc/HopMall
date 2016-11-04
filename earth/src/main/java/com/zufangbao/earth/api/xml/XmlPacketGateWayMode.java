package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.sun.entity.financial.GateWay;

@XStreamAlias("NTOPRMODX")
public class XmlPacketGateWayMode {
	
	@XStreamAlias("GATWAY")
	private String gateWay;

	public String getGateWay() {
		return gateWay;
	}

	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}
	
	public GateWay getGateWayEnum(){
		return GateWay.fromCode(this.gateWay);
	}
	
	
}
