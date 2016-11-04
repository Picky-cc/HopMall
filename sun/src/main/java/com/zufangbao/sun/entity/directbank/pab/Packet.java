package com.zufangbao.sun.entity.directbank.pab;

import java.util.Iterator;
import java.util.Map;

import com.zufangbao.sun.Constant.BankCorpEps;

/**
 * 平安 通讯报文类
 * 
 * @author zjm
 *
 */
public class Packet {

	protected String header;
	protected String body;

	protected String RETCOD;
	protected String RETMSG;

	public String getHeader() {
		return header;
	}

	public void setHeader(CommHeader commHeader) {
		this.header = commHeader.getPACKET_TYPE() + commHeader.getENCODING()
				+ commHeader.getPROTOCOL() + commHeader.getENTERPRISE_CODE()
				+ commHeader.getPACKET_LENGTH() + commHeader.getTRADING_CODE()
				+ commHeader.getOPERATOR_CODE() + commHeader.getSERVICE_TYPE()
				+ commHeader.getTRADE_DATE() + commHeader.getTRADE_TIME()
				+ commHeader.getSERIAL_NO() + commHeader.getRESULT_CODE()
				+ commHeader.getRESULT_MESSAGE()
				+ commHeader.getSUBSEQUENT_FLAG()
				+ commHeader.getREQUEST_TIMES()
				+ commHeader.getSIGNATURE_FLAG()
				+ commHeader.getSIGNATURE_PATTERN()
				+ commHeader.getSIGNATURE_ALGORITHM()
				+ commHeader.getSIGNATURE_LENGTH()
				+ commHeader.getATTACHMENT_AMOUNT();
	}

	public String getRETCOD() {
		return RETCOD;
	}

	public void setRETCOD(String rETCOD) {
		RETCOD = rETCOD;
	}

	public String getRETMSG() {
		return RETMSG;
	}

	public void setRETMSG(String rETMSG) {
		RETMSG = rETMSG;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isError() {
		if (RETCOD.equals(BankCorpEps.PAB_SUCCESS_CODE)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isSuccess() {
		if (RETCOD.equals(BankCorpEps.PAB_SUCCESS_CODE)) {
			return true;
		} else {
			return false;
		}
	}

	public void toXmlString(String xml_encoding, Map<String, Object> data) {
		StringBuffer sfData = new StringBuffer("<?xml version=\"1.0\" encoding=\""
				+ xml_encoding + "\"?>");
		sfData.append("<Result>");

		Iterator itr = data.keySet().iterator();
		while (itr.hasNext()) {
			String datakey = (String) itr.next();
			sfData.append("<" + datakey + ">");
			String dataValue = (String) data.get(datakey);
			sfData.append(dataValue);
			sfData.append("</" + datakey + ">");
		}
		sfData.append("</Result>");
		this.body = sfData.toString();
	}

	public String assemble() {
		return header + body;
	}

	public static Packet valueOf(String message, Map<String, Object> config) {
		try {
			Packet pkt = new Packet();
			byte[] all = message.getBytes(config.getOrDefault("ENCODING", "UTF-8").toString());

			byte[] header = new byte[222];
			System.arraycopy(all, 0, header, 0, 222);
			pkt.header = new String(header);

			byte[] ret_code = new byte[6];
			System.arraycopy(all, 87, ret_code, 0, 6);
			pkt.RETCOD = new String(ret_code);

			byte[] ret_msg = new byte[100];
			System.arraycopy(all, 94, ret_msg, 0, 99);
			pkt.RETMSG = new String(ret_msg).trim();
			
			if (pkt.isError()) {
				return pkt;
			}

			byte[] body = new byte[all.length - 222];
			System.arraycopy(all, 222, body, 0, all.length - 222);

			pkt.body = new String(body);

			return pkt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
