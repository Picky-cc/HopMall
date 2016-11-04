package com.suidifu.coffer.entity.unionpay.gz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.demo2do.core.utils.JsonUtils;
import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;
import com.suidifu.coffer.util.FinanceUtils;

/**
 * 广银联-实时放款
 * @author louguanyang
 *
 */
public class GZUnionPayRealTimePaymentModel extends GZUnionPayBaseModel {

	/** 总记录数 **/
	private int totalItem;
	
	/** 总金额 **/
	private String totalSum;
	
	private List<GZUnionPayPaymentDetailInfoModel> detailInfos;
	
	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public String getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}

	public List<GZUnionPayPaymentDetailInfoModel> getDetailInfos() {
		return detailInfos;
	}

	public void setDetailInfos(List<GZUnionPayPaymentDetailInfoModel> detailInfos) {
		this.detailInfos = detailInfos;
	}

	@Override
	public void initBaseParams(Map<String, String> workParms) {
		super.initBaseParams(workParms);
		this.totalItem = Integer.valueOf(workParms.getOrDefault("totalItem", ""));
		this.totalSum = workParms.getOrDefault("totalSum", "");
		String detailInfos = workParms.getOrDefault("detailInfos", "");
		this.detailInfos = JsonUtils.parseArray(detailInfos, GZUnionPayPaymentDetailInfoModel.class);;
	}

	@Override
	public void process(Map<String, String> workParms) throws Exception {
		initBaseParams(workParms);
		
		String totalSum = FinanceUtils.convert_yuan_to_cent(new BigDecimal( this.getTotalSum()) );
		StringBuffer buffer = new StringBuffer();
		this.getDetailInfos().stream().forEach(detailInfo -> {
			String amount = FinanceUtils.convert_yuan_to_cent(new BigDecimal( detailInfo.getAmount()) );
			String detailXml = formatEscapeNull(Constant.PAYMENT_PACKET_TRANS_DETAIL, detailInfo.getSn(), detailInfo.getBankCode(), detailInfo.getAccountNo(), detailInfo.getAccountName(), detailInfo.getIdNum(), detailInfo.getProvince(), detailInfo.getCity(), amount, detailInfo.getRemark());
			buffer.append(detailXml);
		});
		
		String xml = formatEscapeNull(Constant.REAL_TIME_PAYMENT_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getBusiCode(), this.getMerchantId(), this.getTotalItem(), totalSum, buffer.toString());
		setXmlPacket(xml);
	}

}
