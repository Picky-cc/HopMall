package com.suidifu.coffer.entity.pab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.suidifu.coffer.GlobalSpec.BankCorpEps;

public class PABPacket {

	private String header;
	private String body;

	private String retCode;
	private String retMsg;

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public boolean isError() {
		return !retCode.equals(BankCorpEps.PAB_SUCCESS_CODE);
	}

	@SuppressWarnings("rawtypes")
	public void toXmlString(String xmlEncoding, Map<String, String> requestData) {
		StringBuffer sfData = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"" + xmlEncoding + "\"?>");
		sfData.append("<Result>");

		Iterator itr = requestData.keySet().iterator();
		while (itr.hasNext()) {
			String datakey = (String) itr.next();
			sfData.append("<" + datakey + ">");
			String dataValue = (String) requestData.get(datakey);
			sfData.append(dataValue);
			sfData.append("</" + datakey + ">");
		}
		sfData.append("</Result>");
		this.body = sfData.toString();
	}

	public String assemble() {
		return header + body;
	}

	public static PABPacket valueOf(String message, String encoding) {
		try {
			PABPacket pkt = new PABPacket();
			byte[] all = message.getBytes(encoding);

			byte[] header = new byte[222];
			System.arraycopy(all, 0, header, 0, 222);
			pkt.header = new String(header, encoding);

			byte[] ret_code = new byte[6];
			System.arraycopy(all, 87, ret_code, 0, 6);
			pkt.retCode = new String(ret_code, encoding);

			byte[] ret_msg = new byte[100];
			System.arraycopy(all, 94, ret_msg, 0, 99);
			pkt.retMsg = new String(ret_msg, encoding).trim();

			if (pkt.isError()) {
				return pkt;
			}

			byte[] body = new byte[all.length - 222];
			System.arraycopy(all, 222, body, 0, all.length - 222);

			pkt.body = new String(body, encoding);

			return pkt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	public Map<String, String> extractSingleResultDetail() {
		Map<String, String> resultDetail = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(this.body);
			Element root = document.getRootElement();
			List<Element> resultElements = root.elements();
			if (CollectionUtils.isEmpty(resultElements)) {
				return resultDetail;
			}

			for (Element resultElement : resultElements) {
				resultDetail.put(resultElement.getName(),
						resultElement.getText());
			}
			return resultDetail;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, String>> extractMultipleResultDetail() {
		List<Map<String, String>> resultDetails = new ArrayList<Map<String,String>>();
		try {
			Document document = DocumentHelper.parseText(this.body);
			Element root = document.getRootElement();
			List<Element> resultElements = root.selectNodes("/Result/list");
			
			if(null != resultElements) {
				for(Element resultElement : resultElements) {
					Map<String, String> resultMap = new HashMap<String, String>();
					Iterator detailItr = resultElement.elementIterator();
					while (detailItr.hasNext()) {
						Element detailInfo = (Element)detailItr.next();
						resultMap.put(detailInfo.getName(), detailInfo.getText());
					}
					resultDetails.add(resultMap);
				}
			}

			return resultDetails;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new ArrayList<Map<String,String>>();
	}

}
