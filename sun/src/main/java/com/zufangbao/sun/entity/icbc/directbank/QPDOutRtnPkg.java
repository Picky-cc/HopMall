/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

/**
 * @author wk
 *
 */
public class QPDOutRtnPkg {

	/**账号*/
	private String accNo;
	/**户名*/
	private String accName;
	/**币种*/
	private String currType;
	/**地区代码*/
	private String areaCode;
	/**查询下页标志*/
	private String nextTag;
	/**交易条数*/
	private String totalNum;
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
	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}
	/**
	 * @param accName the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/**
	 * @return the currType
	 */
	public String getCurrType() {
		return currType;
	}
	/**
	 * @param currType the currType to set
	 */
	public void setCurrType(String currType) {
		this.currType = currType;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the nextTag
	 */
	public String getNextTag() {
		return nextTag;
	}
	/**
	 * @param nextTag the nextTag to set
	 */
	public void setNextTag(String nextTag) {
		this.nextTag = nextTag;
	}
	/**
	 * @return the totalNum
	 */
	public String getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	@Override
	public String toString(){
		return String.format("查询当日明细服务：查询账户(账号=%s,名称=%s)的交易条数=%s", this.accNo,this.accName,this.totalNum);
	}
}
