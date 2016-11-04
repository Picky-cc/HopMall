package com.zufangbao.earth.yunxin.api.model.modify;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_interface_import_asset_package")
public class ImportAssetPackageLog {

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 功能编码
	 */
	private String fnCode;

	/**
	 * 请求编号
	 */
	private String requestNo;

	/**
	 * 请求数据
	 */
	private String requestData;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 请求IP地址
	 */
	private String ip;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFnCode() {
		return fnCode;
	}
	public void setFnCode(String fnCode) {
		this.fnCode = fnCode;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
