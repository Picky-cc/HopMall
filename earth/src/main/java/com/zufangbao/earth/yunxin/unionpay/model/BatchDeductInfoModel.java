package com.zufangbao.earth.yunxin.unionpay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zufangbao.earth.model.ManualDeductWebModel;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.interfaces.IDeductInfoModel;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

/**
 * 银联批量扣款信息模型
 * @author zhanghongbing
 *
 */
public class BatchDeductInfoModel extends GZUnionPayBasicInfoModel{
	
	private String businessCode; //业务代码
	
	private int totalItem; //总记录数
	
	private BigDecimal totalSum; //总金额
	
	private List<DeductDetailInfoModel> detailInfos; //扣款详情列表
	
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
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
	
	public List<DeductDetailInfoModel> getDetailInfos() {
		return detailInfos;
	}

	public void setDetailInfos(List<DeductDetailInfoModel> detailInfos) {
		this.detailInfos = detailInfos;
	}
	
	public BatchDeductInfoModel() {
		super();
	}

	public BatchDeductInfoModel(TransferApplication transferApplication, PaymentChannel channel, String businessCode) {
		super(channel);
		this.setReqNo(transferApplication.getTransferApplicationNo());
		this.setBusinessCode(businessCode);
		this.setTotalItem(1);
		this.setTotalSum(transferApplication.getAmount());
		
		List<DeductDetailInfoModel> deductDetailInfoModels = new ArrayList<DeductDetailInfoModel>();
		deductDetailInfoModels.add(new DeductDetailInfoModel(transferApplication));
		
		this.setDetailInfos(deductDetailInfoModels);
	}
	
	public BatchDeductInfoModel(ManualDeductWebModel manualBatchDeductWebModel, PaymentChannel channel,String reqNo, String businessCode) {
		super(channel);
		//随机生成reqNo同时作为批次中的序号
		this.setReqNo(reqNo);
		this.setBusinessCode(businessCode);
		this.setTotalItem(1);
		this.setTotalSum(manualBatchDeductWebModel.getAmount());
		
		List<DeductDetailInfoModel> deductDetailInfoModels = new ArrayList<DeductDetailInfoModel>();
		deductDetailInfoModels.add(new DeductDetailInfoModel(manualBatchDeductWebModel,reqNo));
		
		this.setDetailInfos(deductDetailInfoModels);
	}
	
	public BatchDeductInfoModel(IDeductInfoModel iDeductInfoModel) {
		super(iDeductInfoModel.getPaymentChannel());
		
		String billReqNo = iDeductInfoModel.getReqNo();
		BigDecimal billAmount = iDeductInfoModel.getAmount();
		ContractAccount contractAccount = iDeductInfoModel.getContractAccount();
		
		this.setReqNo(billReqNo);
		this.setBusinessCode(iDeductInfoModel.getBusinessCode());
		this.setTotalItem(1);
		this.setTotalSum(billAmount);
		
		List<DeductDetailInfoModel> deductDetailInfoModels = new ArrayList<DeductDetailInfoModel>();
		deductDetailInfoModels.add(new DeductDetailInfoModel(billAmount, billReqNo, contractAccount));
		
		this.setDetailInfos(deductDetailInfoModels);
	}
	
}
