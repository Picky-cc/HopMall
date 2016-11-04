package com.zufangbao.sun.yunxin.entity.api;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncStatus;
import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;
import com.zufangbao.yunxin.entity.api.syncdata.model.SyncDataDecimalModel;

@Entity()
@Table(name = "t_interface_data_sync_log")
public class DataSyncLog {

	@Id
	@GeneratedValue
	private Long id;

	private String dataSyncLogUuid;

	private String productId;

	private String contractNo;

	private Date contractEndDate;

	private String contractUniqueId;

	private String repaymentPlanNo;

	private String assetSetUuid;

	private int contractFlag;

	@Enumerated(EnumType.ORDINAL)
	private RepayType repayType;

	private Date planRepayDate;

	private Date actualRepayDate;

	private String dataSyncBigDecimalDetails;

	private Date createTime;

	private Date lastModifyTime;

	private Boolean isSuccess;

	private String returnMessage;

	private String requestMessage;

	public DataSyncLog() {

	}

	// TODO
	public DataSyncLog(AssetSet repaymentPlan, SyncDataDecimalModel amountDetail, RepayType repaymentType,
			FinancialContract financialContract, AssetSet lastPeriodRepaymentPlan) {
		this.dataSyncLogUuid = UUID.randomUUID().toString();
		this.productId = financialContract.getContractNo();
		this.contractNo = repaymentPlan.getContract().getContractNo();
		this.contractEndDate = repaymentPlan.getContract().getEndDate();
		this.contractUniqueId = repaymentPlan.getContract().getUniqueId();
		this.repaymentPlanNo = repaymentPlan.getSingleLoanContractNo();
		this.assetSetUuid = repaymentPlan.getAssetUuid();
		this.repayType = repaymentType;
		this.planRepayDate = repaymentPlan.getAssetRecycleDate();
		this.actualRepayDate = repaymentPlan.getActualRecycleDate();
		this.dataSyncBigDecimalDetails = JsonUtils.toJsonString(amountDetail);
		this.createTime = new Date();
		this.lastModifyTime = new Date();
		this.isSuccess = false;
		this.returnMessage = "";
		this.contractFlag = lastPeriodRepaymentPlan.isPaidOff() ? 2 : 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractUniqueId() {
		return contractUniqueId;
	}

	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}

	public String getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(String repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public String getAssetSetUuid() {
		return assetSetUuid;
	}

	public void setAssetSetUuid(String assetSetUuid) {
		this.assetSetUuid = assetSetUuid;
	}

	public RepayType getRepayType() {
		return repayType;
	}

	public void setRepayType(RepayType repayType) {
		this.repayType = repayType;
	}

	public Date getPlanRepayDate() {
		return planRepayDate;
	}

	public void setPlanRepayDate(Date planRepayDate) {
		this.planRepayDate = planRepayDate;
	}

	public Date getActualRepayDate() {
		return actualRepayDate;
	}

	public void setActualRepayDate(Date actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}

	public String getDataSyncBigDecimalDetails() {
		return dataSyncBigDecimalDetails;
	}

	public void setDataSyncBigDecimalDetails(String dataSyncBigDecimalDetails) {
		this.dataSyncBigDecimalDetails = dataSyncBigDecimalDetails;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public SyncDataDecimalModel getDataSyncBigDecimalDetailsJson() {
		return JsonUtils.parse(this.dataSyncBigDecimalDetails, SyncDataDecimalModel.class);
	}

	public void setDataSyncBigDecimalDetailsJson(SyncDataDecimalModel syncDataDecimalModel) {
		this.dataSyncBigDecimalDetails = JsonUtils.toJsonString(syncDataDecimalModel);
	}

	public String getDataSyncLogUuid() {
		return dataSyncLogUuid;
	}

	public void setDataSyncLogUuid(String dataSyncLogUuid) {
		this.dataSyncLogUuid = dataSyncLogUuid;
	}

	public int getContractFlag() {
		return contractFlag;
	}

	public void setContractFlag(int contractFlag) {
		this.contractFlag = contractFlag;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

}
