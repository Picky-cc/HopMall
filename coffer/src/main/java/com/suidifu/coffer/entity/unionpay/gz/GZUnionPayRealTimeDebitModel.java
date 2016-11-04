package com.suidifu.coffer.entity.unionpay.gz;

import java.math.BigDecimal;
import java.util.Map;

import com.suidifu.coffer.Constant;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;
import com.suidifu.coffer.util.FinanceUtils;

public class GZUnionPayRealTimeDebitModel extends GZUnionPayBaseModel {

	private GZUnionPayDebitInfoModel infoModel;
	
	public GZUnionPayDebitInfoModel getInfoModel() {
		return infoModel;
	}

	public void setInfoModel(GZUnionPayDebitInfoModel infoModel) {
		this.infoModel = infoModel;
	}

	@Override
	public void initBaseParams(Map<String, String> workParms) {
		super.initBaseParams(workParms);
//		String sn = workParms.getOrDefault("sn", "");
//		String bankCode= workParms.getOrDefault("bankCode", "");
//		String accountNo= workParms.getOrDefault("accountNo", "");
//		String accountName= workParms.getOrDefault("accountName", "");
//		BigDecimal amount= new BigDecimal(workParms.getOrDefault("amount", ""));
//		String remark= workParms.getOrDefault("remark", "");
//		String idCardNum= workParms.getOrDefault("idCardNum", "");
//		String province= workParms.getOrDefault("province", "");
//		String city= workParms.getOrDefault("city", "");
//		GZUnionPayDebitInfoModel infoModel = new GZUnionPayDebitInfoModel(sn, bankCode, accountNo, accountName, amount, remark, idCardNum, province, city);
//		setInfoModel(infoModel);
	}

	@Override
	public void process(Map<String, String> workParms) {
		initBaseParams(workParms);
		
		GZUnionPayDebitInfoModel deductDetailInfoModel = this.getInfoModel();
		String amount = FinanceUtils.convert_yuan_to_cent(deductDetailInfoModel.getAmount());
		String detailXml = formatEscapeNull(Constant.GZ_UNIONPAY_DEBIT_PACKET_TRANS_DETAIL, deductDetailInfoModel.getSn(), deductDetailInfoModel.getBankCode(), deductDetailInfoModel.getAccountNo(), deductDetailInfoModel.getAccountName(), deductDetailInfoModel.getIdCardNum(), deductDetailInfoModel.getProvince(), deductDetailInfoModel.getCity(), amount, deductDetailInfoModel.getRemark());
		String xmlPacket = formatEscapeNull(Constant.GZ_UNIONPAY_REAL_TIME_DEBIT_PACKET, this.getUserName(), this.getUserPwd(), this.getReqNo(), this.getBusiCode(), this.getMerchantId(), amount, detailXml);
		setXmlPacket(xmlPacket);
	}
	
}
