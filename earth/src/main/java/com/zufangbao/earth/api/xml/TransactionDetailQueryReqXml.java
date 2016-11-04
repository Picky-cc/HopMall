package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.sun.entity.financial.GateWay;

@XStreamAlias("SDKPGK")
public class TransactionDetailQueryReqXml {
	@XStreamAlias("INFO")
	private XmlPacketReqInfo xmlPacketReqInfo;
	@XStreamAlias("NTOPRMODX")
	private XmlPacketGateWayMode xmlPacketGateWayMode;
	@XStreamAlias("NTEBPINFX")
	private TransactionDetailQueryParams transactionDetailQueryParams;
	public XmlPacketReqInfo getXmlPacketReqInfo() {
		return xmlPacketReqInfo;
	}
	public void setXmlPacketReqInfo(XmlPacketReqInfo xmlPacketReqInfo) {
		this.xmlPacketReqInfo = xmlPacketReqInfo;
	}
	public XmlPacketGateWayMode getXmlPacketGateWayMode() {
		return xmlPacketGateWayMode;
	}
	public void setXmlPacketGateWayMode(XmlPacketGateWayMode xmlPacketGateWayMode) {
		this.xmlPacketGateWayMode = xmlPacketGateWayMode;
	}
	public TransactionDetailQueryParams getTransactionDetailQueryParams() {
		return transactionDetailQueryParams;
	}
	public void setTransactionDetailQueryParams(
			TransactionDetailQueryParams transactionDetailQueryParams) {
		this.transactionDetailQueryParams = transactionDetailQueryParams;
	}
	
	public boolean hasAllNodes(){
		return xmlPacketReqInfo!=null && xmlPacketGateWayMode!=null && transactionDetailQueryParams!=null;
	}
	public void validNodes() throws TransactionDetailApiException{
		if(!hasAllNodes()){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_XML_FORMAT);
		}
		if(!FunctionName.FUNC_TRANSACTION_DETAIL_QUERY.equals(xmlPacketReqInfo.getFunName()) ){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_FUNC_NAME);
		}
		if(getGateWay()==null){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_WRONG_GATE_WAY);
		}
	}
	
	public GateWay getGateWay(){
		return xmlPacketGateWayMode==null?null:this.xmlPacketGateWayMode.getGateWayEnum();
	}
	
}
