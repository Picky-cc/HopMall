package com.zufangbao.sun.entity.icbc.directbank;

import org.springframework.stereotype.Component;

/**
 * 查询历史明细入参
 * 
 * @author zjm
 *
 */
@Component
public class QHISDParams extends BasicParams {

  /** 查询账号 */
  private String qryAccNo;
  /** 查询账号名称 */
  private String qryAccName;
  /** 起始日期 */
  private String beginDate;
  /** 起始日期 */
  private String endDate;

  /**
   * 
   */
  public QHISDParams() {
    super();
  }

  /**
   * @param qryAccNo
   */
  public QHISDParams(String qryAccNo) {
    super();
    this.qryAccNo = qryAccNo;
  }

  /**
   * @param qryAccNo
   * @param qryAccName
   */
  public QHISDParams(String qryAccNo, String qryAccName) {
    super();
    this.qryAccNo = qryAccNo;
    this.qryAccName = qryAccName;
  }

  /**
   * @param qryAccNo
   * @param qryAccName
   * @param beginDate
   * @param endDate
   */
  public QHISDParams(String qryAccNo, String qryAccName, String beginDate,
      String endDate) {
    super();
    this.qryAccNo = qryAccNo;
    this.qryAccName = qryAccName;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }

  public QHISDParams initilize(String qryAccNo) {
    this.qryAccNo = qryAccNo;
    return this;
  }

  public QHISDParams initilize(String qryAccNo, String qryAccName, String startDate, String endDate) {
    this.qryAccNo = qryAccNo;
    this.qryAccName = qryAccName;
    this.beginDate = startDate;
    this.endDate = endDate;
    return this;
  }

  /**
   * @return the qryAccNo
   */
  public String getQryAccNo() {
    return qryAccNo;
  }

  /**
   * @param qryAccNo
   *          the qryAccNo to set
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
   * @param qryAccName
   *          the qryAccName to set
   */
  public void setQryAccName(String qryAccName) {
    this.qryAccName = qryAccName;
  }

  /**
   * @return the beginDate
   */
  public String getBeginDate() {
    return beginDate;
  }

  /**
   * @param beginDate
   *          the beginDate to set
   */
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate
   *          the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

}
