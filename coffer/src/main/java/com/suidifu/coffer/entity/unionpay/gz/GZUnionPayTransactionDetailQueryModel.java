package com.suidifu.coffer.entity.unionpay.gz;

import java.util.Map;

import com.demo2do.core.utils.StringUtils;
import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;

/**
 * 广银联-交易流水详情查询
 * @author louguanyang
 *
 */
public class GZUnionPayTransactionDetailQueryModel extends GZUnionPayBaseModel {

	/** 开始日期，格式：YYYYMMDD **/
	private String beginDate;
	
	/** 结束日期，格式：YYYYMMDD **/
	private String endDate;
	
	/** 页码 **/
	private String pageNum;

	/** 每页记录数 **/
	private String pageSize;
	
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
	public void initBaseParams(Map<String, String> workParms) {
		super.initBaseParams(workParms);
		this.beginDate = workParms.getOrDefault("beginDate", "");
		this.endDate = workParms.getOrDefault("endDate", "");
		this.pageNum = workParms.getOrDefault("pageNum", "");
		this.pageSize = workParms.getOrDefault("pageSize", "");
	}

	@Override
	public void process(Map<String, String> workParms) throws Exception {
		initBaseParams(workParms);
		
		String pageSize = StringUtils.isEmpty(this.getPageSize()) ? Constant.GZ_UNIONPAY_DEFAULT_TRANSACTION_DETAIL_QUERY_PAGE_SIZE : this.getPageSize();
		String xml = formatEscapeNull(Constant.GZ_UNIONPAY_TRANSACTION_DETAIL_QUERY_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getMerchantId(), this.getBeginDate(), this.getEndDate(), this.getPageNum(), pageSize);
		setXmlPacket(xml);
	}

	public GZUnionPayTransactionDetailQueryModel() {
		// TODO:
		super();
	}

	
}
