package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import com.zufangbao.sun.yunxin.entity.OfflineBill;

public class OfflineBillPayDetail {
	private JournalVoucher journalVoucher;
	private OfflineBill offlineBill;

	public OfflineBillPayDetail() {
		super();
	}

	public OfflineBillPayDetail(JournalVoucher journalVoucher, OfflineBill offlineBill) {
		super();
		this.journalVoucher = journalVoucher;
		this.offlineBill = offlineBill;
	}

	public JournalVoucher getJournalVoucher() {
		return journalVoucher;
	}

	public void setJournalVoucher(JournalVoucher journalVoucher) {
		this.journalVoucher = journalVoucher;
	}

	public OfflineBill getOfflineBill() {
		return offlineBill;
	}

	public void setOfflineBill(OfflineBill offlineBill) {
		this.offlineBill = offlineBill;
	}

}
