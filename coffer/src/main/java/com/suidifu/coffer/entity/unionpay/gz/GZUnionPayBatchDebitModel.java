package com.suidifu.coffer.entity.unionpay.gz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.demo2do.core.utils.JsonUtils;
import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;
import com.suidifu.coffer.util.FinanceUtils;

public class GZUnionPayBatchDebitModel extends GZUnionPayBaseModel {
	
	/** 总记录数 **/
	private int totalItem;
	
	/** 总金额 **/
	private BigDecimal totalSum;

	/** 扣款详情列表 **/
	private List<GZUnionPayDebitInfoModel> detailInfos;

	@Override
	public void initBaseParams(Map<String, String> workParms) {
		super.initBaseParams(workParms);
		//this.totalItem = Integer.valueOf(workParms.getOrDefault("totalItem", ""));
		//this.totalSum = new BigDecimal(workParms.getOrDefault("totalSum", ""));
		//String detailInfos = workParms.getOrDefault("detailInfos", "");
		//this.detailInfos = JsonUtils.parseArray(detailInfos, GZUnionPayDebitInfoModel.class);
	}

	@Override
	public void process(Map<String, String> workParms) {
		initBaseParams(workParms);
		
		StringBuffer buffer = new StringBuffer();
		this.getDetailInfos().stream().forEach(detailInfo -> {
			String amount = FinanceUtils.convert_yuan_to_cent(detailInfo.getAmount());
			String detailXml = formatEscapeNull(Constant.GZ_UNIONPAY_DEBIT_PACKET_TRANS_DETAIL, detailInfo.getSn(), detailInfo.getBankCode(), detailInfo.getAccountNo(), detailInfo.getAccountName(), detailInfo.getIdCardNum(), detailInfo.getProvince(), detailInfo.getCity(), amount, detailInfo.getRemark());
			buffer.append(detailXml);
		});
		
		String totalSum = FinanceUtils.convert_yuan_to_cent(this.getTotalSum());
		String xmlPacket = formatEscapeNull(Constant.GZ_UNIONPAY_BATCH_DEBIT_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getBusiCode(), this.getMerchantId(), this.getTotalItem(), totalSum, buffer.toString());
		setXmlPacket(xmlPacket);
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public List<GZUnionPayDebitInfoModel> getDetailInfos() {
		return detailInfos;
	}

	public void setDetailInfos(List<GZUnionPayDebitInfoModel> detailInfos) {
		this.detailInfos = detailInfos;
	}
	
}
