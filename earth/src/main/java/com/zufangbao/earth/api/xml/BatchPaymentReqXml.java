package com.zufangbao.earth.api.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.sun.entity.financial.GateWay;

@XStreamAlias("SDKPGK")
public class BatchPaymentReqXml {
	
	@XStreamAlias("INFO")
	private XmlPacketReqInfo xmlPacketReqInfo;
	
	@XStreamAlias("NTOPRMODX")
	private XmlPacketGateWayMode xmlPacketGateWayMode;
	
	@XStreamImplicit(itemFieldName = "NTIBCOPRX")
	private List<PaymentDetail> paymentDetailList;

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

	public List<PaymentDetail> getPaymentDetailList() {
		return paymentDetailList;
	}

	public void setPaymentDetailList(List<PaymentDetail> paymentDetailList) {
		this.paymentDetailList = paymentDetailList;
	}

	public BatchPaymentReqXml(XmlPacketReqInfo xmlPacketReqInfo,
			XmlPacketGateWayMode xmlPacketGateWayMode,
			List<PaymentDetail> paymentDetailList) {
		super();
		this.xmlPacketReqInfo = xmlPacketReqInfo;
		this.xmlPacketGateWayMode = xmlPacketGateWayMode;
		this.paymentDetailList = paymentDetailList;
	}

	public BatchPaymentReqXml() {
		super();
	}
	
	
	public boolean hasAllNodes(){
		return xmlPacketReqInfo!=null && xmlPacketGateWayMode!=null && paymentDetailList!=null;
	}
	
	public boolean isUnionPay(){
		return GateWay.UnionPay.getCode().equals(xmlPacketGateWayMode.getGateWay());
	}
	
	public boolean isSuperBank(){
		return GateWay.SuperBank.getCode().equals(xmlPacketGateWayMode.getGateWay());
	}
	
	public void validNodes() throws TransactionDetailApiException {
		if (!hasAllNodes()) {
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_XML_FORMAT);
		}
		if (!FunctionName.FUNC_BATCH_PAYMENT.equals(xmlPacketReqInfo.getFunName())) {
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_FUNC_NAME);
		}

		if (isUnionPay()) {
			for (PaymentDetail paymentDetail : paymentDetailList) {
				if (paymentDetail.getProvince().isEmpty()
						|| paymentDetail.getCity().isEmpty()
						|| paymentDetail.getBankName().isEmpty()) {
					throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT, ErrMsg.ERR_XML_FORMAT);
				}
			}
		}

		// if(!isSuperBank()){
		// throw new
		// TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMsg.ERR_GATEWAY);
		// }
		// validateNotNullField();
		return;
	}
}

