package com.zufangbao.earth.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "usb_key_account_relation")
public class USBKeyAccountRelation {

	@Id
	@GeneratedValue
	private Long id;

	private String accountUuid;

	private String usbKeyUuid;

	private GatewayType gateWayType;

	private Date startDate;

	private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	public String getUsbKeyUuid() {
		return usbKeyUuid;
	}

	public void setUsbKeyUuid(String usbKeyUuid) {
		this.usbKeyUuid = usbKeyUuid;
	}

	public GatewayType getGateWayType() {
		return gateWayType;
	}

	public void setGateWayType(GatewayType gateWayType) {
		this.gateWayType = gateWayType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
