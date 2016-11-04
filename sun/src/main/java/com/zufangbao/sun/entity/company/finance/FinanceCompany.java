package com.zufangbao.sun.entity.company.finance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.company.Company;
/**
 * 
 * @author zjm
 *
 */
@Entity
@Table(name = "finance_company")
public class FinanceCompany {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 信托公司
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Company company;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
