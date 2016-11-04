package com.zufangbao.earth.api.xml;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg.ErrMSg4DirectBank;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.sun.utils.DateUtils;

@XStreamAlias("NTEBPINFX")
public class TransactionDetailQueryParams {
	@XStreamAlias("BGNDAT")
	private String beginDate;//起始日期
	@XStreamAlias("ENDDAT")
	private String endDate;//结束日期
	@XStreamAlias("ACCNBR")
	private String accountNo; //银行账户,查询网关为银企直联时必填;
	@XStreamAlias("MERNBR")
	private String merId; //银联商户号,查询网关为银联时必填;
	@XStreamAlias("RECACC")
	private String reckonAccount; //清算账户,可空
	public String getBeginDate() {
		return beginDate;
	}
	public Date get_begin_date_date() {
		return DateUtils.parseDate(beginDate, DateUtils.DATE_FORMAT_YYYYMMDD);
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public Date get_end_date_date() {
		return DateUtils.parseDate(endDate, DateUtils.DATE_FORMAT_YYYYMMDD);
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getReckonAccount() {
		return reckonAccount;
	}
	public void setReckonAccount(String reckonAccount) {
		this.reckonAccount = reckonAccount;
	}
	
	public TransactionDetailQueryParams(){
		
	}
	public TransactionDetailQueryParams(String beginDate, String endDate,
			String accountNo, String merId, String reckonAccount) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.accountNo = accountNo;
		this.merId = merId;
		this.reckonAccount = reckonAccount;
	}
	
	private Date validDateFmtAndNotNull(String dateString, String errMsg) throws TransactionDetailApiException{
		Date date =null;
		try {
			date = DateUtils.parseDate(dateString,DateUtils.DATE_FORMAT_YYYYMMDD);
		} catch(Exception e){
		}
		if(date==null){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,errMsg);
		}
		return date;
	}
	
	public void validForUnionpay() throws TransactionDetailApiException{
		Date beginDate_date = validDateFmtAndNotNull(beginDate,ErrMsg.ERR_MSG_WRONG_BEGIN_DATE_FORMAT);
		Date endDate_date = validDateFmtAndNotNull(endDate,ErrMsg.ERR_MSG_WRONG_END_DATE_FORMAT);
		
		if(DateUtils.compareTwoDatesOnDay(new Date(), beginDate_date)>60){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_MSG_TODAY_BEGIN_DATE_GT_60);
		}
		if(DateUtils.compareTwoDatesOnDay(endDate_date,beginDate_date)<0 || DateUtils.compareTwoDatesOnDay(endDate_date,beginDate_date)>2){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_MSG_END_DATE_BEGIN_DATE_GE_3);
		}
		if(StringUtils.isEmpty(merId)){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMsg.ERR_MSG_EMPTY_MERID);
		}
		
	}
	
	public void validForDirectBank() throws TransactionDetailApiException{
		Date beginDate_date = validDateFmtAndNotNull(beginDate,ErrMsg.ERR_MSG_WRONG_BEGIN_DATE_FORMAT);
		Date endDate_date = validDateFmtAndNotNull(endDate,ErrMsg.ERR_MSG_WRONG_END_DATE_FORMAT);
		if(DateUtils.compareTwoDatesOnDay(endDate_date,beginDate_date)<0 ||DateUtils.compareTwoDatesOnDay(endDate_date,beginDate_date)>=100){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMSg4DirectBank.ERR_MSG_END_DATE_BEGIN_DATE_GE_100);
		}
		if(StringUtils.isEmpty(accountNo)){
			throw new TransactionDetailApiException(RTNCode.WRONG_FORMAT,ErrMSg4DirectBank.ERR_MSG_EMPTY_ACCOUNT);
		}
		
	}
}
