package com.zufangbao.wellsfargo.yunxin.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentVO;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CustomerDepositResult;

public interface SourceDocumentHandler {
	public void createSourceDocumentForTransferApplication(TransferApplication transferApplication, Long companyId, Account receiveAccount);
	public void createVouchersForOfflineBill(OfflineBill offlineBill, Long companyId, List<Order> orderList) throws VoucherException;
	public void createSouceDocument(Date date,FinancialContract financialContract);
	public void createOnlineSouceDocumentByDate(Date date);
	
	public OfflineBillConnectionState getOfflineBillConnectionState(OfflineBill offlineBill);
	public SourceDocument createDepositeReceipt(CashFlow cashFlow, Long companyId, BigDecimal amount,Customer customer, String relatedContractUuid, String financialContractUuid, String virtualAccountNo, String remark);
	public List<SourceDocumentVO> castSourceDocumentVO(List<SourceDocument> sourceDocumentList);
	
	public List<CustomerDepositResult> convertToDepositResult(List<SourceDocument> sourceDocumentList);
	
}
