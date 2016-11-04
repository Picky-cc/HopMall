/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;

/**
 * 工行公共请求
 * @author wk
 *
 */
@Component
@Scope(value = "prototype")
public class PubRequest {
	private String hostIp;
	private String serviceUrl;
	private String signUrl;
	private String httpServicePort;
	private String signServicePort;
	private String xmlHeader;
	private String xmlBody;
	private String xmlPackets;
	private String version;
	private String xmlEncodig;
	/**是否是签名服务*/
	private boolean isSign;
	/**
	 * 
	 */
	public PubRequest() {
		super();
	}
	/**
	 * NetSafe签名服务
	 * @param sign
	 * @return
	 */
	public PubRequest initialize(boolean isSign){
		this.isSign = isSign;
		this.signUrl = String.format(this.signUrl,this.hostIp,this.signServicePort);
		return this;
	}
	/***
	 * 构造当日明细查询实体
	 * @param transParams
	 * @param account
	 * @return
	 */
	public PubRequest initialize(TransParams transParams,QPDParams account){
		
			this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());
			this.xmlHeader =String.format(this.getCommonXmlHeader(),
														this.version,
														 transParams.getTransCode(),
														 account.getBankCode(),
														 account.getGroupCIS(),
														 account.getCertificateId(),
														 transParams.getPackageID());
			this.xmlBody = String.format(this.getCommonXmlBody(), 
														this.xmlEncodig,
														transParams.getTransCode(),
														account.getGroupCIS(),
														account.getBankCode(),
														account.getCertificateId(),
														transParams.getTranDate(),
														transParams.getTranTime(),
														transParams.getPackageID(),
														account.getQryAccNo()
														);
			this.xmlPackets = this.xmlHeader + this.xmlBody;
			this.isSign = false;
			return this;
	}
	/**
	 * 构造历史明细查询实体
	 * @param transParams
	 * @param account
	 * @return
	 */
	public PubRequest initialize(TransParams transParams,QHISDParams account){

    this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());
    this.xmlHeader =String.format(this.getCommonXmlHeader(),
                          this.version,
                           transParams.getTransCode(),
                           account.getBankCode(),
                           account.getGroupCIS(),
                           account.getCertificateId(),
                           transParams.getPackageID());
    this.xmlBody = String.format(this.getQHISDXmlBody(), 
                          this.xmlEncodig,
                          transParams.getTransCode(),
                          account.getGroupCIS(),
                          account.getBankCode(),
                          account.getCertificateId(),
                          transParams.getTranDate(),
                          transParams.getTranTime(),
                          transParams.getPackageID(),
                          account.getQryAccNo(),
                          account.getBeginDate(),
                          account.getEndDate()
                          );
    this.xmlPackets = this.xmlHeader + this.xmlBody;
    this.isSign = false;
    return this;
	}
	/***
	 * 构造查询余额的实体
	 * @param transParams
	 * @param account
	 * @return
	 */
	public PubRequest initialize(TransParams transParams,QACCBALParams account){
		
			this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());
			this.xmlHeader =String.format(this.getCommonXmlHeader(),
														this.version,
														 transParams.getTransCode(),
														 account.getBankCode(),
														 account.getGroupCIS(),
														 account.getCertificateId(),
														 transParams.getPackageID());
			this.xmlBody = String.format(this.getQaccbalXmlBodyTemplate(), 
														this.xmlEncodig,
														transParams.getTransCode(),
														account.getGroupCIS(),
														account.getBankCode(),
														account.getCertificateId(),
														transParams.getTranDate(),
														transParams.getTranTime(),
														transParams.getPackageID(),
														account.getTotalNum(),
														transParams.getiSeqNo(),
														account.getAccNo()
														);
			this.xmlPackets = this.xmlHeader + this.xmlBody;
			this.isSign = false;
			return this;
	}
	/***
	 * 构造支付实体
	 * @param transParams
	 * @param account
	 * @return
	 */
	public PubRequest initialize(TransParams transParams,ENTDISAndPAYENTParams account){
		
		this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());
		this.xmlHeader =String.format(this.getCommonXmlHeader(),
													this.version,
													 transParams.getTransCode(),
													 account.getBankCode(),
													 account.getGroupCIS(),
													 account.getCertificateId(),
													 transParams.getPackageID());
		if(transParams.getTransCode().equals("ENTDIS")){
			
			this.xmlBody = String.format(this.getEntDisXmlBodyTemplate(),
					transParams.getTransCode(),
					account.getGroupCIS(),
					account.getBankCode(),
					account.getCertificateId(),
					transParams.getTranDate(),
					transParams.getTranTime(),
					transParams.getPackageID(),
					account.getSettleMode(),
					account.getRecAccNo(),
					account.getRecAccNameCN(),
					account.getPayType(),
					account.getTotalNum(),
					account.getTotalAmt(),
					DateUtils.format(account.getSignTime(),"yyyyMMddHHmmssSSS"),
					account.getiSeqNo(),
					account.getPayAccNo(),
					account.getPayAccNameCN(),
					account.getPayBranch(),
					account.getPayAmt().toString(),
					account.getPostScript()												
					);
		}else{
			this.xmlBody = String.format(this.getPayentXmlBodyTemplate(),
					this.xmlEncodig,
					transParams.getTransCode(),
					account.getGroupCIS(),
					account.getBankCode(),
					account.getCertificateId(),
					transParams.getTranDate(),
					transParams.getTranTime(),
					transParams.getPackageID(),
					account.getSettleMode(),
					account.getTotalNum(),
					account.getTotalAmt(),
					DateUtils.format(account.getSignTime(),"yyyyMMddHHmmssSSS"),
					account.getiSeqNo(),
					account.getPayType(),
					account.getPayAccNo(),
					account.getPayAccNameCN(),
					account.getRecAccNo(),
					account.getRecAccNameCN(),
					account.getSysIOFlg(),
					account.getIsSameCity(),
					account.getProp(),
					account.getRecCityName(),
					account.getRecBankNo(),
					account.getRecBankName(),
					account.getPayAmt().toString(),
					account.getPostScript()												
					);
		}
		this.isSign = false;
		return this;
}

	/**
	 * 构造查询支付状态的实体
	 */
	public PubRequest initialize(TransParams transParams,QPAYENTParams account){
		this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());

		this.xmlHeader =String.format(this.getCommonXmlHeader(),
				this.version,
				 transParams.getTransCode(),
				 account.getBankCode(),
				 account.getGroupCIS(),
				 account.getCertificateId(),
				 transParams.getPackageID());
		
		this.xmlBody = String.format(this.getQpayentXmlBodyTemplate(), 
				this.xmlEncodig,
				transParams.getTransCode(),
				account.getGroupCIS(),
				account.getBankCode(),
				account.getCertificateId(),
				transParams.getTranDate(),
				transParams.getTranTime(),
				transParams.getPackageID(),
				account.getQryfSeqno()
				);
		this.xmlPackets = this.xmlHeader + this.xmlBody;
		this.isSign = false;
		return this;
	}

	
	/**
	 * 构造查询扣款状态的实体
	 */
	public PubRequest initialize(TransParams transParams,QENTDISParams account){
		this.serviceUrl = String.format(this.getCommonServiceUrl(),this.hostIp, this.httpServicePort,account.getCertificateId(),transParams.getPackageID(),transParams.getSendTime());

		this.xmlHeader =String.format(this.getCommonXmlHeader(),
				this.version,
				 transParams.getTransCode(),
				 account.getBankCode(),
				 account.getGroupCIS(),
				 account.getCertificateId(),
				 transParams.getPackageID());
		
		this.xmlBody = String.format(this.getQentdisXmlBodyTemplate(), 
				this.xmlEncodig,
				transParams.getTransCode(),
				account.getGroupCIS(),
				account.getBankCode(),
				account.getCertificateId(),
				transParams.getTranDate(),
				transParams.getTranTime(),
				transParams.getPackageID(),
				account.getQryfSeqno()
				);
		this.xmlPackets = this.xmlHeader + this.xmlBody;
		this.isSign = false;
		return this;
	}
	
	/**
	 * @return the serviceUrl
	 */
	public String getServiceUrl() {
		return this.isSign ? signUrl : serviceUrl;
	}

	/**
	 * @return the xmlPackets
	 */
	public String getXmlPackets() {
		return xmlPackets;
	}

	/**
	 * @return the httpServicePort
	 */
	public String getHttpServicePort() {
		return httpServicePort;
	}

	/**
	 * @param httpServicePort the httpServicePort to set
	 */
	public void setHttpServicePort(String httpServicePort) {
		this.httpServicePort = httpServicePort;
	}

	/**
	 * @return the signServicePort
	 */
	public String getSignServicePort() {
		return signServicePort;
	}

	/**
	 * @param signServicePort the signServicePort to set
	 */
	public void setSignServicePort(String signServicePort) {
		this.signServicePort = signServicePort;
	}

	/**
	 * @return the xmlHeader
	 */
	public String getXmlHeader() {
		return xmlHeader;
	}

	/**
	 * @param xmlHeader the xmlHeader to set
	 */
	public void setXmlHeader(String xmlHeader) {
		this.xmlHeader = xmlHeader;
	}

	/**
	 * @return the xmlBody
	 */
	public String getXmlBody() {
		return xmlBody;
	}

	/**
	 * @param xmlBody the xmlBody to set
	 */
	public void setXmlBody(String xmlBody) {
		this.xmlBody = xmlBody;
	}

	/**
	 * @param serviceUrl the serviceUrl to set
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * @param xmlPackets the xmlPackets to set
	 */
	public void setXmlPackets(String xmlPackets) {
		this.xmlPackets = xmlPackets;
	}

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the xmlEncodig
	 */
	public String getXmlEncodig() {
		return xmlEncodig;
	}

	/**
	 * @param xmlEncodig the xmlEncodig to set
	 */
	public void setXmlEncodig(String xmlEncodig) {
		this.xmlEncodig = xmlEncodig;
	}
	
	/**
	 * @return the signUrl
	 */
	public String getSignUrl() {
		return signUrl;
	}
	/**
	 * @param signUrl the signUrl to set
	 */
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	/**
	 * @return the isSign
	 */
	public boolean isSign() {
		return isSign;
	}
	/**
	 * @param isSign the isSign to set
	 */
	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}
	/***
	 * 获取支付的xml body（同行）
	 * @return
	 */
	private String getPayentXmlBodyTemplate(){
		
		String xml = "<?xml version=\"1.0\" encoding = \"%s\"?><CMS><eb><pub><TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode><ID>%s</ID>"
				+ "<TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub><in><OnlBatF>1</OnlBatF><SettleMode>%s</SettleMode><TotalNum>%s</TotalNum><TotalAmt>%s</TotalAmt>"
				+ "<SignTime>%s</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>%s</iSeqno><ReimburseNo></ReimburseNo><ReimburseNum></ReimburseNum>"
				+ "<StartDate></StartDate><StartTime></StartTime><PayType>%s</PayType><PayAccNo>%s</PayAccNo><PayAccNameCN>%s</PayAccNameCN><PayAccNameEN></PayAccNameEN><RecAccNo>%s</RecAccNo><RecAccNameCN>%s</RecAccNameCN><RecAccNameEN>"
				+ "</RecAccNameEN><SysIOFlg>%s</SysIOFlg><IsSameCity>%s</IsSameCity><Prop>%s</Prop><RecICBCCode></RecICBCCode><RecCityName>%s</RecCityName><RecBankNo>%s</RecBankNo><RecBankName>%s</RecBankName><CurrType>001</CurrType><PayAmt>%s</PayAmt>"
				+ "<UseCode></UseCode><UseCN></UseCN><EnSummary></EnSummary><PostScript>%s</PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode></BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName>"
				+ "<CrvouhNo></CrvouhNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>";
		return xml;
	}
	
	/***
	 * 获取扣款的xml body
	 * @return
	 */
	private String getEntDisXmlBodyTemplate(){
		
		String xml ="<?xml version=\"1.0\" encoding=\"gb2312\"?>"
				+"<CMS><eb><pub><TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode><ID>%s</ID><TranDate>%s</TranDate>"
				+ "<TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub>"
				+"<in><OnlBatF>1</OnlBatF><SettleMode>%s</SettleMode><RecAccNo>%s</RecAccNo><RecAccNameCN>%s</RecAccNameCN><RecAccNameEN/>"
				+"<PayType>%s</PayType><TotalNum>%s</TotalNum><TotalAmt>%s</TotalAmt><SignTime>%s</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2>"
				+"<rd><iSeqno>%s</iSeqno><PayAccNo>%s</PayAccNo><PayAccNameCN>%s</PayAccNameCN><PayAccNameEN></PayAccNameEN><PayBranch>%s</PayBranch>"
				+"<CurrType>001</CurrType><PayAmt>%s</PayAmt><UseCode/><UseCN/><EnSummary/><PostScript>%s</PostScript><Summary/><Ref/><Oref/><ERPSqn/><BusCode/><ERPcheckno/><CrvouhType/><CrvouhName/><CrvouhNo/>"
				+"<ReqReserved3/><ReqReserved4/></rd></in></eb></CMS>";
		return xml;
	}
	/***
	 * 创建查询余额的xml body
	 * @return
	 */
	private String getQaccbalXmlBodyTemplate(){
		String  xml="<?xml version=\"1.0\" encoding =\"%s\"?><CMS><eb><pub>"
				+ "<TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode>"
				+ "<ID>%s</ID><TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub>"
				+ "<in><TotalNum>%s</TotalNum><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2>"
				+ "<rd><iSeqno>%s</iSeqno><AccNo>%s</AccNo><CurrType></CurrType><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4>"
				+ "</rd>"
				+ "</in></eb></CMS>";
		return xml;
	}
	
	/**
	 * 创建查询支付状态的xml body
	 */
	private String getQpayentXmlBodyTemplate(){
		String  xml = "<?xml version=\"1.0\" encoding =\"%s\"?><CMS><eb><pub>"
				+ "<TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode>"
				+ "<ID>%s</ID><TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub>"
				+ "<in><QryfSeqno>%s</QryfSeqno><QrySerialNo></QrySerialNo><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2>"
				+ "</in></eb></CMS>";
		
		return xml;
	}
	
	
	/**
	 * 创建查询扣款状态的xml body
	 */
	private String getQentdisXmlBodyTemplate(){
		String  xml = "<?xml version=\"1.0\" encoding =\"%s\"?><CMS><eb><pub>"
				+ "<TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode>"
				+ "<ID>%s</ID><TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub>"
				+ "<in><QryfSeqno>%s</QryfSeqno><QrySerialNo></QrySerialNo><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2>"
				+ "</in></eb></CMS>";
		
		return xml;
	}
	
	/**
	 * 创建公共的报文头
	 * @return
	 */
	private String getCommonXmlHeader(){
		return "xml_header=Version=%s&TransCode=%s&BankCode=%s&GroupCIS=%s&ID=%s&PackageID=%s&Cert=&reqData=";
	}
	/**
	 * 创建公共的报文body
	 * @return
	 */
	private String getCommonXmlBody(){
		return "<?xml version=\"1.0\" encoding = \"%s\" ?><CMS><eb><pub><TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode><ID>%s</ID><TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub><in><AccNo>%s</AccNo><AreaCode></AreaCode><MinAmt>0</MinAmt><MaxAmt>1000000000</MaxAmt><BeginTime/><EndTime/><NextTag/><ReqReserved1/><ReqReserved2/></in></eb></CMS>";
	}
	
	/**
   * 创建查询历史明细的报文body
   * @return
   */
  private String getQHISDXmlBody(){
    return "<?xml version=\"1.0\" encoding = \"%s\" ?><CMS><eb><pub><TransCode>%s</TransCode><CIS>%s</CIS><BankCode>%s</BankCode><ID>%s</ID><TranDate>%s</TranDate><TranTime>%s</TranTime><fSeqno>%s</fSeqno></pub><in><AccNo>%s</AccNo><BeginDate>%s</BeginDate><EndDate>%s</EndDate><MinAmt>0</MinAmt><MaxAmt>1000000000</MaxAmt><NextTag/><ReqReserved1/><ReqReserved2/></in></eb></CMS>";
  }
	
	/***
	 * 创建公共的http url
	 * @return
	 */
	private String getCommonServiceUrl(){
		return "http://%s:%s/servlet/ICBCCMPAPIReqServlet?userID=%s&PackageID=%s&SendTime=%s";
	}
}
