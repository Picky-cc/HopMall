package com.zufangbao.sun.entity.directbank.pab;

import java.util.Date;
import java.util.Map;

import com.demo2do.core.utils.DateUtils;

/**
 * 平安 通讯报文头
 * 
 * @author zjm
 *
 */
public class CommHeader {

	private String PACKET_TYPE;
	private String ENCODING;
	private String PROTOCOL;
	private String ENTERPRISE_CODE;
	private String PACKET_LENGTH;// 10位 不够左补0
	private String TRADING_CODE;// 6位 不够右补空格
	private String OPERATOR_CODE;// 5位 无 用空格代替
	private String SERVICE_TYPE;// 2位 01请求 02应答
	private String TRADE_DATE;
	private String TRADE_TIME;
	private String SERIAL_NO;// 请求方流水号 20位 右补空格
	private String RESULT_CODE;// 6位 请求非必输
	private String RESULT_MESSAGE;// 100位，格式为“:交易成功”；其中冒号为英文格式半角
	private String SUBSEQUENT_FLAG;// 0-结束包，1-还有后续包
	private String REQUEST_TIMES;// 如果有后续包请求 000 001 002...
	private String SIGNATURE_FLAG;// 1位 1签名 2不签名 0企业不管 由银行客户端完成
	private String SIGNATURE_PATTERN;// 签名数据包格式
	private String SIGNATURE_ALGORITHM;// 12位 空格
	private String SIGNATURE_LENGTH;// 10位 空格
	private String ATTACHMENT_AMOUNT;// 1 没有 有的话 填具体数目

	public CommHeader ini_for_retrieve_detail(Map<String, Object> config, String body_length, Date currentTime) {
		common_initialize(config, body_length, currentTime);
		this.TRADING_CODE = String.format("%-6s", config.getOrDefault("RETRIEVE_DETAIL_CODE", "4013").toString());
		return this;
	}
	
	public CommHeader ini_for_transfer(Map<String, Object> config, String body_length, Date currentTime) {
		common_initialize(config, body_length, currentTime);
		this.TRADING_CODE = String.format("%-6s", config.getOrDefault("TRANSFER_CODE", "4004").toString());
		return this;
	}
	
	public CommHeader ini_for_retrieve_balance(Map<String, Object> config, String body_length, Date currentTime) {
		common_initialize(config, body_length, currentTime);
		this.TRADING_CODE = String.format("%-6s", config.getOrDefault("RETRIEVE_BALANCE_CODE", "4001").toString());
		return this;
	}
	
	public CommHeader ini_for_get_transfer_info(Map<String, Object> config, String body_length, Date currentTime) {
		common_initialize(config, body_length, currentTime);
		this.TRADING_CODE = String.format("%-6s", config.getOrDefault("GET_TRANSFER_INFO_CODE", "4005").toString());
		return this;
	}
	
	private void common_initialize(Map<String, Object> config, String body_length, Date currentTime) {
		this.PACKET_TYPE = config.getOrDefault("PACKET_TYPE", "A00101").toString();
		this.ENCODING = config.getOrDefault("ENCODING_CODE", "02").toString();
		this.PROTOCOL = config.getOrDefault("PROTOCOL", "01").toString();
		this.ENTERPRISE_CODE = config.get("ENTERPRISE_CODE").toString();
		this.PACKET_LENGTH = body_length;
		this.OPERATOR_CODE = config.getOrDefault("OPERATOR_CODE", "     ").toString();
		this.SERVICE_TYPE = config.getOrDefault("SERVICE_TYPE", "01").toString();
		this.TRADE_DATE = DateUtils.format(currentTime, "yyyyMMdd");
		this.TRADE_TIME = DateUtils.format(currentTime, "HHmmss");
		this.SERIAL_NO = String.format("%-20s", DateUtils.format(currentTime, "yyyyMMddHHmmssSSS"));
		this.RESULT_CODE = config.getOrDefault("RESULT_CODE", "      ").toString();
		this.RESULT_MESSAGE = config.getOrDefault("RESULT_MESSAGE", "                                                                                                    ").toString();
		this.SUBSEQUENT_FLAG = config.getOrDefault("SUBSEQUENT_FLAG", "0").toString();
		this.REQUEST_TIMES = config.getOrDefault("REQUEST_TIMES", "000").toString();
		this.SIGNATURE_FLAG = config.getOrDefault("SIGNATURE_FLAG", "0").toString();
		this.SIGNATURE_PATTERN = config.getOrDefault("SIGNATURE_PATTERN", " ").toString();
		this.SIGNATURE_ALGORITHM = config.getOrDefault("SIGNATURE_ALGORITHM", "            ").toString();
		this.SIGNATURE_LENGTH = config.getOrDefault("SIGNATURE_LENGTH", "          ").toString();
		this.ATTACHMENT_AMOUNT = config.getOrDefault("ATTACHMENT_AMOUNT", "0").toString();
	}

	public String getPACKET_TYPE() {
		return PACKET_TYPE;
	}

	public void setPACKET_TYPE(String pACKET_TYPE) {
		PACKET_TYPE = pACKET_TYPE;
	}

	public String getENCODING() {
		return ENCODING;
	}

	public void setENCODING(String eNCODING) {
		ENCODING = eNCODING;
	}

	public String getPROTOCOL() {
		return PROTOCOL;
	}

	public void setPROTOCOL(String pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}

	public String getENTERPRISE_CODE() {
		return ENTERPRISE_CODE;
	}

	public void setENTERPRISE_CODE(String eNTERPRISE_CODE) {
		ENTERPRISE_CODE = eNTERPRISE_CODE;
	}

	public String getPACKET_LENGTH() {
		return PACKET_LENGTH;
	}

	public void setPACKET_LENGTH(String pACKET_LENGTH) {
		PACKET_LENGTH = pACKET_LENGTH;
	}

	public String getTRADING_CODE() {
		return TRADING_CODE;
	}

	public void setTRADING_CODE(String tRADING_CODE) {
		TRADING_CODE = tRADING_CODE;
	}

	public String getOPERATOR_CODE() {
		return OPERATOR_CODE;
	}

	public void setOPERATOR_CODE(String oPERATOR_CODE) {
		OPERATOR_CODE = oPERATOR_CODE;
	}

	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}

	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
	}

	public String getTRADE_DATE() {
		return TRADE_DATE;
	}

	public void setTRADE_DATE(String tRADE_DATE) {
		TRADE_DATE = tRADE_DATE;
	}

	public String getTRADE_TIME() {
		return TRADE_TIME;
	}

	public void setTRADE_TIME(String tRADE_TIME) {
		TRADE_TIME = tRADE_TIME;
	}

	public String getSERIAL_NO() {
		return SERIAL_NO;
	}

	public void setSERIAL_NO(String sERIAL_NO) {
		SERIAL_NO = sERIAL_NO;
	}

	public String getRESULT_CODE() {
		return RESULT_CODE;
	}

	public void setRESULT_CODE(String rESULT_CODE) {
		RESULT_CODE = rESULT_CODE;
	}

	public String getRESULT_MESSAGE() {
		return RESULT_MESSAGE;
	}

	public void setRESULT_MESSAGE(String rESULT_MESSAGE) {
		RESULT_MESSAGE = rESULT_MESSAGE;
	}

	public String getSUBSEQUENT_FLAG() {
		return SUBSEQUENT_FLAG;
	}

	public void setSUBSEQUENT_FLAG(String sUBSEQUENT_FLAG) {
		SUBSEQUENT_FLAG = sUBSEQUENT_FLAG;
	}

	public String getREQUEST_TIMES() {
		return REQUEST_TIMES;
	}

	public void setREQUEST_TIMES(String rEQUEST_TIMES) {
		REQUEST_TIMES = rEQUEST_TIMES;
	}

	public String getSIGNATURE_FLAG() {
		return SIGNATURE_FLAG;
	}

	public void setSIGNATURE_FLAG(String sIGNATURE_FLAG) {
		SIGNATURE_FLAG = sIGNATURE_FLAG;
	}

	public String getSIGNATURE_PATTERN() {
		return SIGNATURE_PATTERN;
	}

	public void setSIGNATURE_PATTERN(String sIGNATURE_PATTERN) {
		SIGNATURE_PATTERN = sIGNATURE_PATTERN;
	}

	public String getSIGNATURE_ALGORITHM() {
		return SIGNATURE_ALGORITHM;
	}

	public void setSIGNATURE_ALGORITHM(String sIGNATURE_ALGORITHM) {
		SIGNATURE_ALGORITHM = sIGNATURE_ALGORITHM;
	}

	public String getSIGNATURE_LENGTH() {
		return SIGNATURE_LENGTH;
	}

	public void setSIGNATURE_LENGTH(String sIGNATURE_LENGTH) {
		SIGNATURE_LENGTH = sIGNATURE_LENGTH;
	}

	public String getATTACHMENT_AMOUNT() {
		return ATTACHMENT_AMOUNT;
	}

	public void setATTACHMENT_AMOUNT(String aTTACHMENT_AMOUNT) {
		ATTACHMENT_AMOUNT = aTTACHMENT_AMOUNT;
	}

}
