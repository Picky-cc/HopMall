package com.zufangbao.earth.yunxin.unionpay.model;

import java.util.UUID;

import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;
import com.zufangbao.sun.entity.financial.PaymentChannel;

/**
 * 交易明细查询模型
 * @author zhanghongbing
 *
 */
public class TransactionDetailQueryInfoModel extends GZUnionPayBasicInfoModel{

	private String beginDate; //开始日期，格式：YYYYMMDD
	
	private String endDate; //结束日期，格式：YYYYMMDD
	
	private String pageNum; //页码

	private String pageSize; //每页记录数

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

	public TransactionDetailQueryInfoModel() {
	}

	public TransactionDetailQueryInfoModel(PaymentChannel channel,
			String beginDate, String endDate, String pageNum, String pageSize) {
		super(channel);
		this.setReqNo(UUID.randomUUID().toString());
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
}
