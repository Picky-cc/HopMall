package com.zufangbao.earth.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class PrincipalInfoModel {

	private Long principalId;
	
	private String username;
	
	private String role;
	
	private String realname;
	
	private String email;
	
	private String phone;
	
	private Long companyId;
	
	private String deptName; 
	
	private String positionName;
	
	private String remark;
	
	public Long getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = StringUtils.trim(username);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = StringUtils.trim(realname);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = StringUtils.trim(email);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = StringUtils.trim(phone);
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = StringUtils.trim(deptName);
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = StringUtils.trim(positionName);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public boolean isValidUsername() {
		if(StringUtils.isEmpty(this.username)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{5,15}$");
		Matcher m = pattern.matcher(this.username);
		return m.matches();
	}
	
	public PrincipalInfoModel() {
		super();
	}
	
}
