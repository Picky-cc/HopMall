/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

/**
 * @author wk
 *
 */
public class PAYENTOutRtnPkg {

	/**联机批量标志*/
	private String onlBatF;
	/**入账方式*/
	private String settleMode;
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
	public PAYENTOutRtnPkg(String onlBatF, String settleMode, String recAccNo,
			String recAccNameCN, String recACCNameEN, String payType,
			String totalNum, String totalAmt) {
		super();
		this.onlBatF = onlBatF;
		this.settleMode = settleMode;
		this.totalNum = totalNum;
		this.totalAmt = totalAmt;
	}
	public PAYENTOutRtnPkg() {
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
		return String.format("支付服务：总笔数(%s)的总金额(%s)款项,入账方式是%s",
					this.totalNum,this.totalAmt,this.settleMode);
	}
}
