package com.zufangbao.wellsfargo.silverpool.cashauditing.service;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;

public interface SourceDocumentDetailService extends GenericService<SourceDocumentDetail> {

	void cancelDetails(String sourceDocumentUuid, String firstNo, String secondNo);

	List<Long> get_voucher_ids_by_first_type(VoucherQueryModel voucherQueryModel, String firstType, Page page);

	List<SourceDocumentDetail> getSameVersionDetails(SourceDocumentDetail detail, Page page);

	SourceDocumentDetail getSourceDocumentDetailBy(Long id);

	List<SourceDocumentDetail> getDetailsBySourceDocumentUuid(String sourceDocumentUuid, String secondNo);
		
	List<Long> getDetailIdsBySourceDocumentUuid(String sourceDocumentUuid, String secondNo);

	public boolean exist(String sourceDocumentUuid, String firstType, String secondNo);
	
	int countSameVersionDetails(SourceDocumentDetail detail);

	int countBusinessVouchers(VoucherQueryModel voucherQueryModel);
	
	int countActiveVouchers(VoucherQueryModel voucherQueryModel);

	boolean isSourceDocumentDetailsCheckFails(SourceDocumentDetail detail);
	
	public SourceDocumentDetail getValidSourceDocumentDetail(String firstType, String firstNo, String secondType, String secondNo);

	public BigDecimal getTotalAmountOfSourceDocumentDetail(String sourceDocumentUuid, String secondNo);

	BigDecimal getVoucherAmountOfSourceDocument(String firstNo, String secondNo);

	/**
	 * SourceDocumentDetail表新增sourceDocumentUuid
	 * @param sourceDocumentUuid
	 * @param firstNo
	 * @param secondNo
	 */
	void appendSourceDocumentUuid(String sourceDocumentUuid, String firstNo, String secondNo);
}
