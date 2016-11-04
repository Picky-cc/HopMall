/**
 * 
 */
package com.zufangbao.wellsfargo.greypool.document.entity.busidocument.contract;

import java.util.Date;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.utils.DateUtils;

/**
 * @author wukai
 *
 */
public class SimplifiedContractInfo {

	private String businessContractUuid;
	
	private String contractName;
	
	private String contractType;
	
	private String tradePartyName;
	
	private Date effectiveTime;
	
	private String sourceContractNo;
	
	private String sourcePropertyNo; 
	
	private String address;

	public SimplifiedContractInfo() {
		super();
	}
	public SimplifiedContractInfo(String businessContractUuid,
			String contractName, String contractType, String tradePartyName,

			Date effectiveTime,String srcPropteryNo ,String srcContractNo,String Address) {
		super();
		this.businessContractUuid = businessContractUuid;
		this.contractName = contractName;
		this.contractType = contractType;
		this.tradePartyName = tradePartyName;
		this.effectiveTime = effectiveTime;
		this.sourceContractNo=srcContractNo;
		this.sourcePropertyNo=srcPropteryNo;
		this.address=Address;
	}

	//TODO add uuid sourceContractNo
	public SimplifiedContractInfo(Contract contract){
		super();
		this.businessContractUuid = contract.getId()+"";
		this.contractName = contract.getContractNo();
		this.contractType = "";
		this.tradePartyName = contract.getCustomer().getName();
		this.effectiveTime = contract.getBeginDate();
		this.sourceContractNo=contract.getContractNo();
		this.sourcePropertyNo="";
		this.address="";
	}

	public String getTradePartyName() {
		return tradePartyName;
	}
	public void setTradePartyName(String tradePartyName) {
		this.tradePartyName = tradePartyName;
	}
	public String getBusinessContractUuid() {
		return businessContractUuid;
	}
	public void setBusinessContractUuid(String businessContractUuid) {
		this.businessContractUuid = businessContractUuid;
	}
	public String getContractName() {
		return tradePartyName +"("+ DateUtils.format(effectiveTime, DateUtils.DATE_FORMAT_DOT)+")";
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getSourceContractNo() {
		return sourceContractNo;
	}
	public void setSourceContractNo(String sourceContractNo) {
		this.sourceContractNo = sourceContractNo;
	}
	public String getSourcePropertyNo() {
		return sourcePropertyNo;
	}
	public void setSourcePropertyNo(String sourcePropertyNo) {
		this.sourcePropertyNo = sourcePropertyNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
