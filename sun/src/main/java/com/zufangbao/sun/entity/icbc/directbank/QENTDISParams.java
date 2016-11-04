package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.stereotype.Component;

@Component
public class QENTDISParams extends BasicParams {

	private String qryfSeqno;
	private String qrySerialNo;
	private String iSeqno;
	private String qryiSeqno;
	private String qryOrderNo;

	public QENTDISParams() {
		super();
	}

	public QENTDISParams(String qryfSeqno, String qrySerialNo, String iSeqno,
			String qryiSeqno, String qryOrderNo) {
		super();
		this.qryfSeqno = qryfSeqno;
		this.qrySerialNo = qrySerialNo;
		this.iSeqno = iSeqno;
		this.qryiSeqno = qryiSeqno;
		this.qryOrderNo = qryOrderNo;
	}
	
	public QENTDISParams initilize(String qryfSeqno){
		this.qryfSeqno = qryfSeqno;
		return this;
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

	public String getiSeqno() {
		return iSeqno;
	}

	public void setiSeqno(String iSeqno) {
		this.iSeqno = iSeqno;
	}

	public String getQryiSeqno() {
		return qryiSeqno;
	}

	public void setQryiSeqno(String qryiSeqno) {
		this.qryiSeqno = qryiSeqno;
	}

	public String getQryOrderNo() {
		return qryOrderNo;
	}

	public void setQryOrderNo(String qryOrderNo) {
		this.qryOrderNo = qryOrderNo;
	}

}
