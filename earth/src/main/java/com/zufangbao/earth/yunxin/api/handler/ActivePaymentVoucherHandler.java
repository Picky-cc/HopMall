package com.zufangbao.earth.yunxin.api.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.api.model.command.ActivePaymentVoucherCommandModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateBaseModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateModel;

/**
 * 主动付款凭证接口
 * @author louguanyang
 *
 */
public interface ActivePaymentVoucherHandler {

	void save_file_to_service(MultipartHttpServletRequest fileRequest, String sourceDocumentUuid, String requestNo) throws IOException;

	void submitActivePaymentVoucher(ActivePaymentVoucherCommandModel model, Contract contract, MultipartHttpServletRequest fileRequest) throws IOException;

	void checkRequestNoAndSaveLog(ActivePaymentVoucherCommandModel model, String ip);

	void undoActivePaymentVoucher(Contract contract, String bankTransactionNo);

	VoucherCreateBaseModel searchAccountInfoByContractNo(String contractNo);

	List<ContractAccount> searchAccountInfoByName(String name);

	void saveActiveVoucher(VoucherCreateModel model, Principal principal, String ip) throws IOException;

	void updateActiveVoucherComment(Long detailId, String comment);

	List<String> getActiveVoucherResource(Long detailId);

	String uploadSingleFileReturnUUID(MultipartFile file) throws IOException;

}
