package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.ledgerbook.AssetCategory;

public interface CashFlowAutoIssueHandler {
	public void write_off_balance();

	public void charge_cash_into_virtual_account(CashFlow cashFlow);
	
	public void charge_cash_into_virtual_account(CashFlow cashFlow, Customer customer, BigDecimal bookingAmount, FinancialContract financialContract, AssetCategory assetCategory, String remark);
	
	public void deposit_cancel(String sourceDocumentUuid);
	
	public void recover_assets_online_deduct_by_interface();
	public void recover_assets_online_deduct_by_interface_each_financial_contract(FinancialContract financialContract);
}
