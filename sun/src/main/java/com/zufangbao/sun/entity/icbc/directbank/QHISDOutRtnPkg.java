package com.zufangbao.sun.entity.icbc.directbank;

/**
 * 
 * @author zjm
 *
 */
public class QHISDOutRtnPkg {

  /** 账号 */
  private String accNo;
  /** 户名 */
  private String accName;
  /** 币种 */
  private String currType;
  /** 起始日期 */
  private String beginDate;
  /** 截止日期 */
  private String endDate;
  /** 发生额下限 */
  private String minAmt;
  /** 发生额上限 */
  private String maxAmt;
  /** 查询下页标志 */
  private String nextTag;
  /** 交易条数 */
  private String totalNum;

  /**
   * @return the accNo
   */
  public String getAccNo() {
    return accNo;
  }

  /**
   * @param accNo
   *          the accNo to set
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
   * @param accName
   *          the accName to set
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
   * @param currType
   *          the currType to set
   */
  public void setCurrType(String currType) {
    this.currType = currType;
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

  /**
   * @return the minAmt
   */
  public String getMinAmt() {
    return minAmt;
  }

  /**
   * @param minAmt
   *          the minAmt to set
   */
  public void setMinAmt(String minAmt) {
    this.minAmt = minAmt;
  }

  /**
   * @return the maxAmt
   */
  public String getMaxAmt() {
    return maxAmt;
  }

  /**
   * @param maxAmt
   *          the maxAmt to set
   */
  public void setMaxAmt(String maxAmt) {
    this.maxAmt = maxAmt;
  }

  /**
   * @return the nextTag
   */
  public String getNextTag() {
    return nextTag;
  }

  /**
   * @param nextTag
   *          the nextTag to set
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
   * @param totalNum
   *          the totalNum to set
   */
  public void setTotalNum(String totalNum) {
    this.totalNum = totalNum;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("查询历史明细服务：查询账户(账号=%s,名称=%s)的交易条数=%s", this.accNo,
        this.accName, this.totalNum);
  }

}
