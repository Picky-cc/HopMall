package com.zufangbao.sun.entity.user;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.Company;

/**
 * 
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "t_user")
public class TUser{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String uuid;
	
	/**
	 * 用户名称
	 */
	private String name;
	
	/**
	 * 联系邮件
	 */
	private String email;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 所属公司
	 */
	@ManyToOne(fetch = FetchType.LAZY ,optional = true)
	private Company company;
	
	/**
	 * 所属部门
	 */
	private String deptName;
	
	/**
	 * 职位名称
	 */
	private String positionName;
	
	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TUser() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
	
}
