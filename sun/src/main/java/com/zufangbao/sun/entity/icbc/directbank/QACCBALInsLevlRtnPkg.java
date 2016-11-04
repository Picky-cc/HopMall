/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.io.Serializable;
import java.util.List;

/**
 * 查询当日余额的返回实体包
 * 指令级别
 * @author zjm
 *
 */
public class QACCBALInsLevlRtnPkg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4566692134354448205L;
	/**返回报文公共头*/
	private PubRtnPkg pubRtnPkg;
	/**查询当日余额的具体条目*/
	List<QACCBALRdRtnPkg>detailList ;
	/**
	 * @return the pubRtnPkg
	 */
	public PubRtnPkg getPubRtnPkg() {
		return pubRtnPkg;
	}
	/**
	 * @param pubRtnPkg the pubRtnPkg to set
	 */
	public void setPubRtnPkg(PubRtnPkg pubRtnPkg) {
		this.pubRtnPkg = pubRtnPkg;
	}
	
	/**
	 * @return the detailList
	 */
	public List<QACCBALRdRtnPkg> getDetailList() {
		return detailList;
	}
	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<QACCBALRdRtnPkg> detailList) {
		this.detailList = detailList;
	}
}

