package com.zufangbao.sun.yunxin.entity.model.voucher;

import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;

/**
 * 凭证查询Model
 * @author louguanyang
 *
 */
public class VoucherQueryModel {
	private int voucherType = -1;
	private int voucherStatus = -1;
	private String hostAccount;
	private String counterName;
	private String counterNo;

	public boolean queryString(String str) {
		return !StringUtils.isEmpty(str);
	}
	
	public boolean queryType() {
		return voucherType != -1 && getVoucherTypeEnum() != null;
	}

	public VoucherType getVoucherTypeEnum() {
		return EnumUtil.fromOrdinal(VoucherType.class, voucherType);
	}
	
	public boolean queryStatus() {
		return voucherStatus != -1;
	}
	
	public int getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(int voucherType) {
		this.voucherType = voucherType;
	}

	public int getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(int voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public String getHostAccount() {
		return hostAccount;
	}

	public void setHostAccount(String hostAccount) {
		this.hostAccount = hostAccount;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public String getCounterNo() {
		return counterNo;
	}

	public void setCounterNo(String counterNo) {
		this.counterNo = counterNo;
	}

}
