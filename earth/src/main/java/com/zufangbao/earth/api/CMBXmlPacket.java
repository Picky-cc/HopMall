package com.zufangbao.earth.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * 招行XML通讯报文类
 * 
 * @author zjm
 *
 */
public class CMBXmlPacket {

	private String FUNNAM;

	private final String DATTYP = "2";// 报文类型固定为2

	private String LGNNAM;

	private String RETCOD;

	private String ERRMSG;

	private Map data; // <String,Vector>

	public CMBXmlPacket() {
		data = new Properties();
	}

	public CMBXmlPacket(String sFUNNAM) {
		FUNNAM = sFUNNAM;
		data = new Properties();
	}

	public CMBXmlPacket(String sFUNNAM, String sLGNNAM) {
		FUNNAM = sFUNNAM;
		LGNNAM = sLGNNAM;
		data = new Properties();
	}

	public String getFUNNAM() {
		return FUNNAM;
	}

	public void setFUNNAM(String fUNNAM) {
		FUNNAM = fUNNAM;
	}

	public String getLGNNAM() {
		return LGNNAM;
	}

	public void setLGNNAM(String lGNNAM) {
		LGNNAM = lGNNAM;
	}

	public String getRETCOD() {
		return RETCOD;
	}

	public void setRETCOD(String rETCOD) {
		RETCOD = rETCOD;
	}

	public String getERRMSG() {
		return ERRMSG;
	}

	public void setERRMSG(String eRRMSG) {
		ERRMSG = eRRMSG;
	}

	/**
	 * XML报文返回头中内容是否表示成功
	 * 
	 * @return
	 */
	public boolean isError() {
		return !"0".equals(RETCOD);
	}

	/**
	 * 插入数据记录
	 * 
	 * @param sSectionName
	 * @param mpData
	 *            <String, String>
	 */
	public void putProperty(String sSectionName, Map mpData) {
		if (data.containsKey(sSectionName)) {
			Vector vt = (Vector) data.get(sSectionName);
			vt.add(mpData);
		} else {
			Vector vt = new Vector();
			vt.add(mpData);
			data.put(sSectionName, vt);
		}
	}

	/**
	 * 取得指定接口的数据记录
	 * 
	 * @param sSectionName
	 * @param index
	 *            索引，从0开始
	 * @return Map<String,String>
	 */
	public Map getProperty(String sSectionName, int index) {
		if (data.containsKey(sSectionName)) {
			return (Map) ((Vector) data.get(sSectionName)).get(index);
		} else {
			return null;
		}
	}

	/**
	 * 取得指定接口数据记录数
	 * 
	 * @param sSectionName
	 * @return
	 */
	public int getSectionSize(String sSectionName) {
		if (data.containsKey(sSectionName)) {
			Vector sec = (Vector) data.get(sSectionName);
			return sec.size();
		}
		return 0;
	}

	/**
	 * 把报文转换成XML字符串
	 * 
	 * @return
	 */
	public String toXmlString() {
		StringBuffer sfData = new StringBuffer(
				"<?xml version='1.0' encoding = 'UTF-8'?>");
		sfData.append("<CMBSDKPGK>");
		sfData.append("<INFO><FUNNAM>" + FUNNAM + "</FUNNAM><DATTYP>" + DATTYP
				+ "</DATTYP><LGNNAM>" + LGNNAM + "</LGNNAM></INFO>");
		Iterator itr = data.keySet().iterator();
		while (itr.hasNext()) {
			String secName = (String) itr.next();
			Vector vt = (Vector) data.get(secName);
			for (int i = 0; i < vt.size(); i++) {
				Map record = (Map) vt.get(i);
				Iterator itr2 = record.keySet().iterator();
				sfData.append("<" + secName + ">");
				while (itr2.hasNext()) {
					String datakey = (String) itr2.next();
					String dataValue = (String) record.get(datakey);
					sfData.append("<" + datakey + ">");
					sfData.append(dataValue);
					sfData.append("</" + datakey + ">");
				}
				sfData.append("</" + secName + ">");
			}
		}
		sfData.append("</CMBSDKPGK>");
		return sfData.toString();
	}

	/**
	 * 解析xml字符串，并转换为报文对象
	 * 
	 * @param message
	 */
	public static CMBXmlPacket valueOf(String message) {
		SAXParserFactory saxfac = SAXParserFactory.newInstance();
		try {
			SAXParser saxparser = saxfac.newSAXParser();
			ByteArrayInputStream is = new ByteArrayInputStream(
					message.getBytes());
			CMBXmlPacket xmlPkt = new CMBXmlPacket();
			saxparser.parse(is, new CMBSaxHandler(xmlPkt));
			is.close();
			return xmlPkt;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
