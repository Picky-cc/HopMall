package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailBusinessModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherQueryModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherShowModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;

public interface SourceDocumentDetailHandler {

	List<VoucherShowModel> getBusinessVoucherShowModels(VoucherQueryModel voucherQueryModel, Page page);

	VoucherDetailModel getBusinessVoucherDetailModel(Long detailId);

	List<VoucherDetailBusinessModel> queryVoucherDetails(SourceDocumentDetail detail, Page page);

	List<VoucherShowModel> getActiveVoucherShowModels(VoucherQueryModel voucherQueryModel, Page page);

	VoucherDetailModel getActiveVoucherDetailModel(Long detailId);
}
