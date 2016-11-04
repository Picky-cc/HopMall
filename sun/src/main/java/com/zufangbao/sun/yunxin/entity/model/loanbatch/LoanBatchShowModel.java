
package com.zufangbao.sun.yunxin.entity.model.loanbatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zufangbao.sun.entity.financial.AssetPackage;
import com.zufangbao.sun.entity.financial.LoanBatch;

public class LoanBatchShowModel {
	
	private Long loanBatchId;
	
	private String loanBatchCode;
	
	private Date dateTime;
	
	private boolean available;
	
    private String loanBatchFromRange;
    
    private String loanBatchThruRange;
    
    private String loanBatchTotalAmount;
    
    private int loanBatchTotalContractNum;

	public LoanBatchShowModel() {
		super();
	}
	
	public LoanBatchShowModel(LoanBatch loanBatch, List<AssetPackage> assetPackages) {
		super();
		this.loanBatchId = loanBatch.getId();
		this.loanBatchCode = loanBatch.getCode();
		this.available = loanBatch.isAvailable();
		if(available) {
			this.dateTime = loanBatch.getLoanDate();
		}else {
			this.dateTime = loanBatch.getCreateTime();
		}
		this.loanBatchFromRange = getLoanBatchFromRange(assetPackages);
		this.loanBatchThruRange = getLoanBatchThruRange(assetPackages);
		this.loanBatchTotalAmount = calculateTotalAmount(assetPackages);
		this.loanBatchTotalContractNum = assetPackages.size();
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Long getLoanBatchId() {
		return loanBatchId;
	}

	public void setLoanBatchId(Long loanBatchId) {
		this.loanBatchId = loanBatchId;
	}

	public String getLoanBatchCode() {
		return loanBatchCode;
	}

	public void setLoanBatchCode(String loanBatchCode) {
		this.loanBatchCode = loanBatchCode;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	private String getLoanBatchThruRange(List<AssetPackage> assetPackages) {
		try {
			return assetPackages.get(assetPackages.size() - 1).getContract().getContractNo();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getLoanBatchFromRange(List<AssetPackage> assetPackages) {
		try {
			return assetPackages.get(0).getContract().getContractNo();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String calculateTotalAmount(List<AssetPackage> assetPackages) {
		BigDecimal loanBatchTotalAmount = BigDecimal.ZERO;
		for (AssetPackage assetPackage : assetPackages) {
			if(assetPackage.getContract() == null || assetPackage.getContract().getTotalAmount() == null) {
				continue;
			}
			loanBatchTotalAmount = loanBatchTotalAmount.add(assetPackage.getContract().getTotalAmount());
		}
		return loanBatchTotalAmount.toString();
	}

	public String getLoanBatchFromRange() {
		return loanBatchFromRange;
	}

	public void setLoanBatchFromRange(String loanBatchFromRange) {
		this.loanBatchFromRange = loanBatchFromRange;
	}

	public String getLoanBatchThruRange() {
		return loanBatchThruRange;
	}

	public void setLoanBatchThruRange(String loanBatchThruRange) {
		this.loanBatchThruRange = loanBatchThruRange;
	}

	public String getLoanBatchTotalAmount() {
		return loanBatchTotalAmount;
	}

	public void setLoanBatchTotalAmount(String loanBatchTotalAmount) {
		this.loanBatchTotalAmount = loanBatchTotalAmount;
	}

	public int getLoanBatchTotalContractNum() {
		return loanBatchTotalContractNum;
	}

	public void setLoanBatchTotalContractNum(int loanBatchTotalPeriods) {
		this.loanBatchTotalContractNum = loanBatchTotalPeriods;
	}

}
