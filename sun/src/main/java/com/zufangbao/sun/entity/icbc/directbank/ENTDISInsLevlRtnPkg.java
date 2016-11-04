/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.io.Serializable;
import java.util.List;

import com.zufangbao.sun.utils.FinanceUtils;

/**
 * 扣款服务的返回实体包 指令级别
 * 
 * @author wk
 *
 */
public class ENTDISInsLevlRtnPkg implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -4566692134354448205L;
  /** 返回报文公共头 */
  private PubRtnPkg pubRtnPkg;
  /** 返回的out数据 */
  ENTDISOutRtnPkg entdisOutRtnPkg;
  /** 查询当日明细的具体条目 */
  List<ENTDISRdRtnPkg> detailList;
  
  /**
   * 原始响应报文
   */
  private String responseData;

  /**
   * @return the pubRtnPkg
   */
  public PubRtnPkg getPubRtnPkg() {
    return pubRtnPkg;
  }

  /**
   * @param pubRtnPkg
   *          the pubRtnPkg to set
   */
  public void setPubRtnPkg(PubRtnPkg pubRtnPkg) {
    this.pubRtnPkg = pubRtnPkg;
  }

  /**
   * @return the detailList
   */
  public List<ENTDISRdRtnPkg> getDetailList() {
    return detailList;
  }

  /**
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<ENTDISRdRtnPkg> detailList) {
    this.detailList = detailList;
  }

  /**
   * @return the entdisOutRtnPkg
   */
  public ENTDISOutRtnPkg getEntdisOutRtnPkg() {
    return entdisOutRtnPkg;
  }

  /**
   * @param entdisOutRtnPkg
   *          the entdisOutRtnPkg to set
   */
  public void setEntdisOutRtnPkg(ENTDISOutRtnPkg entdisOutRtnPkg) {
    this.entdisOutRtnPkg = entdisOutRtnPkg;
  }
  

  public String getResponseData() {
	return responseData;
  }

  public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

  /**
   * 判断是否扣款提交成功
   * 
   * @return
   */
  public boolean is_successfully_submit() {
    return FinanceUtils.SUCCESS_RETURN_CODE.equals(getPubRtnPkg().getRetCode());
  }

  /**
   * 判断是否扣款成功
   * 
   * @return
   */
  public boolean is_successfully_deduct() {
    return FinanceUtils.SUCCESS_RETURN_CODE.equals(detailList.get(0)
        .getiRetCode());
  }
  
	public String getResultMsg() {
		return "错误码：" + pubRtnPkg.getRetCode() + " 描述：" + pubRtnPkg.getRetMsg()
				+ " 序列号：" + pubRtnPkg.getfSeqno();
	}

}
