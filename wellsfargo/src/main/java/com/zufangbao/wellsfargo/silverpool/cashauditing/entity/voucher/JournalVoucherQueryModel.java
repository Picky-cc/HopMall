package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.util.Date;


public interface JournalVoucherQueryModel {

	public abstract int getType();
	
	public abstract Long getAppId();

	public abstract String getOrderNo();
	
	public abstract Date getStartDate();
	
	public abstract Date getEndDate();

	public abstract Long getCompanyId();
	
	public abstract void setCompanyId(Long companyId);

	public abstract String getEndTime();

	public abstract String getStartTime();
	
}