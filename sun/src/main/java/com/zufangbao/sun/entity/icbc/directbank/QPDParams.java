/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.stereotype.Component;

/**
 * 查询当日明细入参
 * @author wk
 *
 */
@Component
public class QPDParams extends BasicParams{
	/**查询账号*/
	private String qryAccNo;
	/**查询账号名称*/
	private String qryAccName;
	/**
	 * 
	 */
	public QPDParams() {
		super();
	}
	/**
	 * @param qryAccNo
	 */
	public QPDParams(String qryAccNo) {
		super();
		this.qryAccNo = qryAccNo;
	}
	
	/**
	 * @param qryAccNo
	 * @param qryAccName
	 */
	public QPDParams(String qryAccNo, String qryAccName) {
		super();
		this.qryAccNo = qryAccNo;
		this.qryAccName = qryAccName;
	}
	public QPDParams initilize(String qryAccNo) {
		this.qryAccNo = qryAccNo;
		return this;
	}
	public QPDParams initilize(String qryAccNo, String qryAccName) {
		this.qryAccNo = qryAccNo;
		this.qryAccName = qryAccName;
		return this;
	}
	/**
	 * @return the qryAccNo
	 */
	public String getQryAccNo() {
		return qryAccNo;
	}
	/**
	 * @param qryAccNo the qryAccNo to set
	 */
	public void setQryAccNo(String qryAccNo) {
		this.qryAccNo = qryAccNo;
	}
	/**
	 * @return the qryAccName
	 */
	public String getQryAccName() {
		return qryAccName;
	}
	/**
	 * @param qryAccName the qryAccName to set
	 */
	public void setQryAccName(String qryAccName) {
		this.qryAccName = qryAccName;
	}
}
