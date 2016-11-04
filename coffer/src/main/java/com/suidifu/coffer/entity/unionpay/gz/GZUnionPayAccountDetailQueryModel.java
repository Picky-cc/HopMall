package com.suidifu.coffer.entity.unionpay.gz;

import java.util.Map;

import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;

/**
 * 广银联-账户信息查询
 * @author louguanyang
 *
 */
public class GZUnionPayAccountDetailQueryModel extends GZUnionPayBaseModel {

	/** 开始日期，格式：YYYYMMDD **/
	private String beginDate;
	
	/** 结束日期，格式：YYYYMMDD **/
	private String endDate;
	
	/** 页码 **/
	private String pageNum;

	/** 每页记录数 **/
	private String pageSize;
	
	/** 0：商户账户 1：机构账户 **/
	private String accountType;
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void process(Map<String, String> workParms) throws Exception {
		initBaseParams(workParms);
		
		String xml = formatEscapeNull(Constant.GZ_UNIONPAY_ACCOUNT_DETAIL_QUERY_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getAccountType(),this.getMerchantId(), this.getBeginDate(), this.getEndDate(), this.getPageNum(), this.getPageSize());
		setXmlPacket(xml);
	}

	public GZUnionPayAccountDetailQueryModel() {
		// TODO:
		super();
	}

	
}
