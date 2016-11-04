package com.zufangbao.sun.entity.icbc.directbank;

/**
 * 
 * @author zjm
 *
 */
public class QPAYENTOutRtnPkg {

	private String qryfSeqno;
	private String qrySerialNo;
	private String onlBatF;
	private String settleMode;
	private String busType;

	public QPAYENTOutRtnPkg(String qryfSeqno, String qrySerialNo,
			String onlBatF, String settleMode, String busType) {
		super();
		this.qryfSeqno = qryfSeqno;
		this.qrySerialNo = qrySerialNo;
		this.onlBatF = onlBatF;
		this.settleMode = settleMode;
		this.busType = busType;
	}

	public QPAYENTOutRtnPkg() {
		super();
	}

	public String getQryfSeqno() {
		return qryfSeqno;
	}

	public void setQryfSeqno(String qryfSeqno) {
		this.qryfSeqno = qryfSeqno;
	}

	public String getQrySerialNo() {
		return qrySerialNo;
	}

	public void setQrySerialNo(String qrySerialNo) {
		this.qrySerialNo = qrySerialNo;
	}

	public String getOnlBatF() {
		return onlBatF;
	}

	public void setOnlBatF(String onlBatF) {
		this.onlBatF = onlBatF;
	}

	public String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

}
