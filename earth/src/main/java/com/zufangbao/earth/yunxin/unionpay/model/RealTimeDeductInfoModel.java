package com.zufangbao.earth.yunxin.unionpay.model;

import java.math.BigDecimal;

import com.zufangbao.earth.model.ManualDeductWebModel;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.interfaces.IDeductInfoModel;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

/**
 * 实时代收信息模型
 * @author zhanghongbing
 *
 */
public class RealTimeDeductInfoModel extends GZUnionPayBasicInfoModel{

	private String businessCode; //业务代码
	
	private DeductDetailInfoModel deductDetailInfoModel;

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	public DeductDetailInfoModel getDeductDetailInfoModel() {
		return deductDetailInfoModel;
	}

	public void setDeductDetailInfoModel(DeductDetailInfoModel deductDetailInfoModel) {
		this.deductDetailInfoModel = deductDetailInfoModel;
	}

	public RealTimeDeductInfoModel() {
		super();
	}

	public RealTimeDeductInfoModel(TransferApplication transferApplication, PaymentChannel channel, String businessCode) {
		super(channel);
		this.setReqNo(transferApplication.getTransferApplicationNo());
		this.setBusinessCode(businessCode);
		this.setDeductDetailInfoModel(new DeductDetailInfoModel(transferApplication));
	}
	
	public RealTimeDeductInfoModel(ManualDeductWebModel manualDeductWebModel, PaymentChannel channel,String reqNo, String businessCode) {
		super(channel);
		this.setReqNo(reqNo);
		this.setBusinessCode(businessCode);
		this.setDeductDetailInfoModel(new DeductDetailInfoModel(manualDeductWebModel,reqNo));
	}
	
	public RealTimeDeductInfoModel(IDeductInfoModel iDeductInfoModel) {
		super(iDeductInfoModel.getPaymentChannel());
		
		String billReqNo = iDeductInfoModel.getReqNo();
		BigDecimal billAmount = iDeductInfoModel.getAmount();
		ContractAccount contractAccount = iDeductInfoModel.getContractAccount();
		
		this.setReqNo(billReqNo);
		this.setBusinessCode(iDeductInfoModel.getBusinessCode());
		this.setDeductDetailInfoModel(new DeductDetailInfoModel(billAmount, billReqNo, contractAccount));
	}
	
}