/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

/**
 * @author wk
 *
 */
public class ENTDISOutRtnPkg {

	/**联机批量标志*/
	private String onlBatF;
	/**入账方式*/
	private String settleMode;
	/**收方账号*/
	private String recAccNo;
	/**收方账户中文名称*/
	private String recAccNameCN;
	/**收方账户英文名称*/
	private String recACCNameEN;
	/**支付方式*/
	private String payType;
	/**总笔数*/
	private String totalNum;
	/**总金额*/
	private String totalAmt;
	
	/**
	 * @param onlBatF
	 * @param settleMode
	 * @param recAccNo
	 * @param recAccNameCN
	 * @param recACCNameEN
	 * @param payType
	 * @param totalNum
	 * @param totalAmt
	 */
	public ENTDISOutRtnPkg(String onlBatF, String settleMode, String recAccNo,
			String recAccNameCN, String recACCNameEN, String payType,
			String totalNum, String totalAmt) {
		super();
		this.onlBatF = onlBatF;
		this.settleMode = settleMode;
		this.recAccNo = recAccNo;
		this.recAccNameCN = recAccNameCN;
		this.recACCNameEN = recACCNameEN;
		this.payType = payType;
		this.totalNum = totalNum;
		this.totalAmt = totalAmt;
	}
	public ENTDISOutRtnPkg() {
	}
	/**
	 * @return the onlBatF
	 */
	public String getOnlBatF() {
		return onlBatF;
	}
	/**
	 * @param onlBatF the onlBatF to set
	 */
	public void setOnlBatF(String onlBatF) {
		this.onlBatF = onlBatF;
	}
	/**
	 * @return the settleMode
	 */
	public String getSettleMode() {
		return settleMode;
	}
	/**
	 * @param settleMode the settleMode to set
	 */
	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}
	/**
	 * @return the recAccNo
	 */
	public String getRecAccNo() {
		return recAccNo;
	}
	/**
	 * @param recAccNo the recAccNo to set
	 */
	public void setRecAccNo(String recAccNo) {
		this.recAccNo = recAccNo;
	}
	/**
	 * @return the recAccNameCN
	 */
	public String getRecAccNameCN() {
		return recAccNameCN;
	}
	/**
	 * @param recAccNameCN the recAccNameCN to set
	 */
	public void setRecAccNameCN(String recAccNameCN) {
		this.recAccNameCN = recAccNameCN;
	}
	/**
	 * @return the recACCNameEN
	 */
	public String getRecACCNameEN() {
		return recACCNameEN;
	}
	/**
	 * @param recACCNameEN the recACCNameEN to set
	 */
	public void setRecACCNameEN(String recACCNameEN) {
		this.recACCNameEN = recACCNameEN;
	}
	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
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
	/**
	 * @return the totalAmt
	 */
	public String getTotalAmt() {
		return totalAmt;
	}
	/**
	 * @param totalAmt the totalAmt to set
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	@Override
	public String toString(){
		return String.format("收方(账号=%s,账户名=%s]收到%s总笔数的%s总金额的款项,入账方式是%s,支付方式是%s",
					this.recAccNo,this.recAccNameCN,this.totalNum,this.totalAmt,this.settleMode,this.payType.equals("1")?"加急":"普通");
	}
}
