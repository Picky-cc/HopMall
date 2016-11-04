/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.stereotype.Component;

/**
 * 多账户余额查询
 * @author zjm
 *
 */
@Component
public class QACCBALParams extends BasicParams{
	/**总笔数*/
	private int totalNum;
	/**账号*/
	private String accNo;
	/**
	 * 
	 */
	public QACCBALParams() {
		super();
	}
	
	/**
	 * @param accNo
	 */
	public QACCBALParams intialize(String accNo) {
		this.accNo = accNo;
		this.totalNum = 1;
		return this;
	}

	/**
	 * @param totalNum
	 * @param accNo
	 */
	public QACCBALParams intialize(int totalNum, String accNo) {
		this.totalNum = totalNum;
		this.accNo = accNo;
		return this;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
}
