package com.zufangbao.wellsfargo.silverpool.cashauditing.entity;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;

public class AssetWriteOffModel {
	private AssetSet assetSet;
	private SourceDocumentDetail sourceDocumentDetail;
	private DepositeAccountInfo accountInfo;
	private DeductApplication deductApplication;
	private BigDecimal bookingAmount;
	public AssetSet getAssetSet() {
		return assetSet;
	}
	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}
	public SourceDocumentDetail getSourceDocumentDetail() {
		return sourceDocumentDetail;
	}
	public void setSourceDocumentDetail(SourceDocumentDetail sourceDocumentDetail) {
		this.sourceDocumentDetail = sourceDocumentDetail;
	}
	public DepositeAccountInfo getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(DepositeAccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	
	public DeductApplication getDeductApplication() {
		return deductApplication;
	}
	public void setDeductApplication(DeductApplication deductApplication) {
		this.deductApplication = deductApplication;
	}
	
	public BigDecimal getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(BigDecimal bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public AssetWriteOffModel(){
		
	}
	public AssetWriteOffModel(AssetSet assetSet,
			SourceDocumentDetail sourceDocumentDetail,
			DepositeAccountInfo accountInfo, DeductApplication deductApplication,BigDecimal bookingAmount) {
		super();
		this.assetSet = assetSet;
		this.sourceDocumentDetail = sourceDocumentDetail;
		this.accountInfo = accountInfo;
		this.deductApplication = deductApplication;
		this.bookingAmount = bookingAmount;
	}
	
}
