package com.zufangbao.sun.entity.directbank;

/**
 * 平安 配置
 * 
 * @author zjm
 *
 */
public class PABConfig {

	private String packetType;// 报文类别和目标系统编号A00101

	private String encoding;// 报文编码01：GBK缺省 02：UTF8 03：unicode 04：iso-8859-1

	private String protocol;// 通讯协议01:tcpip 缺省02：http03：webservice

	private String enterpriseCode;// 企业银企直连标准代码 银行提供给企业的20位唯一的标识代码

	private String retrieveDetailCode;// 查询流水交易码

	private String retrieveBalanceCode;// 查询余额交易码

	private String Transfer_Code;// 转账交易码

	private String getTransferInfoCode;// 查询转账状态交易码

	private String ccyCode;// 币种

	private String pageSize;// 每页明细数量

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String getRetrieveDetailCode() {
		return retrieveDetailCode;
	}

	public void setRetrieveDetailCode(String retrieveDetailCode) {
		this.retrieveDetailCode = retrieveDetailCode;
	}

	public String getRetrieveBalanceCode() {
		return retrieveBalanceCode;
	}

	public void setRetrieveBalanceCode(String retrieveBalanceCode) {
		this.retrieveBalanceCode = retrieveBalanceCode;
	}

	public String getTransfer_Code() {
		return Transfer_Code;
	}

	public void setTransfer_Code(String transfer_Code) {
		Transfer_Code = transfer_Code;
	}

	public String getGetTransferInfoCode() {
		return getTransferInfoCode;
	}

	public void setGetTransferInfoCode(String getTransferInfoCode) {
		this.getTransferInfoCode = getTransferInfoCode;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public void setCcyCode(String ccyCode) {
		this.ccyCode = ccyCode;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
