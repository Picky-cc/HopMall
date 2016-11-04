package com.suidifu.coffer.entity.unionpay.gz;

import java.util.Map;

import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;

/**
 * 广银联-代扣查询
 * @author louguanyang
 *
 */
public class GZUnionPayDebitQueryModel extends GZUnionPayBaseModel {
	/** 要查询的交易流水号 **/
	private String queryReqNo;

	public String getQueryReqNo() {
		return queryReqNo;
	}

	public void setQueryReqNo(String queryReqNo) {
		this.queryReqNo = queryReqNo;
	}

	@Override
	public void initBaseParams(Map<String, String> workParms) {
		super.initBaseParams(workParms);
		//this.queryReqNo = workParms.getOrDefault("queryReqNo", "");
	}

	@Override
	public void process(Map<String, String> workParms) throws Exception {
		initBaseParams(workParms);
		
		String xmlPacket = formatEscapeNull(Constant.GZ_UNIONPAY_BATCH_QUERY_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getQueryReqNo());
		setXmlPacket(xmlPacket);
	}
}
