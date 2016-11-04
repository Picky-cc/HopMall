package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherCommandModel;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.wellsfargo.yunxin.handler.vouchertask.BusinessPaymentVoucherTaskHandler;
/**
 * 商户付款凭证接口
 * 
 * @author louguanyang
 *
 */
public interface BusinessPaymentVoucherHandler extends BusinessPaymentVoucherTaskHandler {

	List<CashFlow> businessPaymentVoucher(BusinessPaymentVoucherCommandModel model, String ip);
	void undoBusinessPaymentVoucher(BusinessPaymentVoucherCommandModel model, String ip);
	void invalidSourceDocument(Long detailId);
	
	List<CashFlow> matchCashflow(Long detailId);

	void connectionCashFlow(Long detailId, String cashFlowUuid);
}
