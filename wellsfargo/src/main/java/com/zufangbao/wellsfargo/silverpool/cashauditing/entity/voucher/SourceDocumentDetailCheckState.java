package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import com.zufangbao.sun.BasicEnum;

public enum SourceDocumentDetailCheckState implements BasicEnum {
	/**
	 * 未校验
	 */
	UNCHECKED("enum.source-document-detail-check-state.unchecked"),
	/**
	 * 校验失败
	 */
	CHECK_FAILS("enum.source-document-detail-check-state.check-fails"),
	/**
	 * 校验成功
	 */
	CHECK_SUCCESS("enum.source-document-detail-check-state.check-success");

	private String key;
	
	private SourceDocumentDetailCheckState(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

}
