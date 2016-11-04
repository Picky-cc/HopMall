/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.io.Serializable;
import java.util.List;

/**
 * 查询当日明细的返回实体包
 * 指令级别
 * @author wk
 *
 */
public class QPDInsLevlRtnPkg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4566692134354448205L;
	/**返回报文公共头*/
	private PubRtnPkg pubRtnPkg;
	/**返回的out数据*/
	QPDOutRtnPkg qpdOutRtnPkg;
	/**查询当日明细的具体条目*/
	List<QPDRdRtnPkg>detailList ;
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
	public List<QPDRdRtnPkg> getDetailList() {
		return detailList;
	}
	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<QPDRdRtnPkg> detailList) {
		this.detailList = detailList;
	}
	
	/**
	 * @return the qpdOutRtnPkg
	 */
	public QPDOutRtnPkg getQpdOutRtnPkg() {
		return qpdOutRtnPkg;
	}
	/**
	 * @param qpdOutRtnPkg the qpdOutRtnPkg to set
	 */
	public void setQpdOutRtnPkg(QPDOutRtnPkg qpdOutRtnPkg) {
		this.qpdOutRtnPkg = qpdOutRtnPkg;
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(null != this.pubRtnPkg){
			sb.append("<pub>"+this.pubRtnPkg.toString()+"</pub>\r\n");
		}
		if(null != this.qpdOutRtnPkg){
			sb.append("<out>"+this.qpdOutRtnPkg.toString()+"</out>\r\n");
		}
		return "";
	}
}

