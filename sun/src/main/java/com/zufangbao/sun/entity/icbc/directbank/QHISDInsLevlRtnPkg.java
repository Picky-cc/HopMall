package com.zufangbao.sun.entity.icbc.directbank;

import java.io.Serializable;
import java.util.List;

/**
 * 查询历史明细的返回实体包 指令级别
 * 
 * @author zjm
 *
 */
public class QHISDInsLevlRtnPkg implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 635929428283565966L;

  /** 返回报文公共头 */
  private PubRtnPkg pubRtnPkg;
  /** 返回的out数据 */
  QHISDOutRtnPkg qhisdOutRtnPkg;
  /** 查询历史明细的具体条目 */
  List<QHISDRdRtnPkg> detailList;

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
   * @return the qhisdOutRtnPkg
   */
  public QHISDOutRtnPkg getQhisdOutRtnPkg() {
    return qhisdOutRtnPkg;
  }

  /**
   * @param qhisdOutRtnPkg
   *          the qhisdOutRtnPkg to set
   */
  public void setQhisdOutRtnPkg(QHISDOutRtnPkg qhisdOutRtnPkg) {
    this.qhisdOutRtnPkg = qhisdOutRtnPkg;
  }

  /**
   * @return the detailList
   */
  public List<QHISDRdRtnPkg> getDetailList() {
    return detailList;
  }

  /**
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<QHISDRdRtnPkg> detailList) {
    this.detailList = detailList;
  }

}
