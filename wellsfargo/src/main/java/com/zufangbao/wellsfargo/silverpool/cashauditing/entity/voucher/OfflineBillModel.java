package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;

import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;

public class OfflineBillModel {

	private OfflineBill offlineBill;

	private SourceDocument sourceDocument;

	public OfflineBillModel(OfflineBill offlineBill,
			SourceDocument sourceDocument) {
		this.offlineBill = offlineBill;
		this.sourceDocument = sourceDocument;
	}
	public OfflineBillModel() {

	}

	public OfflineBill getOfflineBill() {
		return offlineBill;
	}

	public void setOfflineBill(OfflineBill offlineBill) {
		this.offlineBill = offlineBill;
	}

	public SourceDocument getSourceDocument() {
		return sourceDocument;
	}

	public void setSourceDocument(SourceDocument sourceDocument) {
		this.sourceDocument = sourceDocument;
	}

	public String getOfflineBillConnectionState() {
		BigDecimal paidAmount = sourceDocument.getBookingAmount();
		if(BigDecimal.ZERO.compareTo(paidAmount) == 0) {
			return OfflineBillConnectionState.NONE.getChineseMessage();
		}
		if(BigDecimal.ZERO.compareTo(paidAmount) == -1) {
			BigDecimal amount = offlineBill.getAmount();
			if(paidAmount.compareTo(amount) == -1) {
				return OfflineBillConnectionState.PART.getChineseMessage();
			}
		}
		return OfflineBillConnectionState.ALL.getChineseMessage();
	}

}
