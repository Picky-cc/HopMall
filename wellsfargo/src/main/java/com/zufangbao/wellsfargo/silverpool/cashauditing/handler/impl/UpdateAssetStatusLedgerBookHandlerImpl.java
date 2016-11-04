package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.UpdateAssetStatusLedgerBookHandler;

@Component("updateAssetStatusLedgerBookHandler")
public class UpdateAssetStatusLedgerBookHandlerImpl implements UpdateAssetStatusLedgerBookHandler {

	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private OrderService orderService;
	@Override
	public void updateAssetsFromLedgerBook(String ledgerBookNo, List<String> assetSetUuids, boolean updateOrder) {
		LedgerBook book = ledgerBookService.getBookByBookNo(ledgerBookNo);
		if(book==null){
			return;
		}
		for (String assetSetUuid : assetSetUuids) {
			AssetSet assetSet = repaymentPlanService.getUniqueRepaymentPlanByUuid(assetSetUuid);
			updateAssetFromLedgerBook(book, assetSet, updateOrder);
		}
	}
	private void updateAssetFromLedgerBook(LedgerBook book, AssetSet assetSet, boolean isUpdateOrder) {
		if(assetSet==null){
			return;
		}
		Contract contract = assetSet.getContract();
		String customerUuid = contract.getCustomer().getCustomerUuid();
		BigDecimal receivable_amount = ledgerBookStatHandler.get_receivable_amount(book.getLedgerBookNo(), assetSet.getAssetUuid(), customerUuid);
		BigDecimal unearned_amount = ledgerBookStatHandler.get_unearned_amount(book.getLedgerBookNo(), assetSet.getAssetUuid());
		AssetClearStatus expectedAssetClearStatus = null;
		if(BigDecimal.ZERO.compareTo(unearned_amount)==0 && BigDecimal.ZERO.compareTo(receivable_amount)==0){
			expectedAssetClearStatus=AssetClearStatus.CLEAR;
		} else {
			expectedAssetClearStatus=AssetClearStatus.UNCLEAR;
		}
		if(expectedAssetClearStatus!=assetSet.getAssetStatus()){
			assetSet.updateClearStatus(new Date(), expectedAssetClearStatus==AssetClearStatus.CLEAR);
			repaymentPlanService.save(assetSet);
		}
		if(!isUpdateOrder){
			return;
		}
		Order order = orderService.getRepaymentOrder(assetSet,new Date());
		if(order==null){
			return;
		}
		order.updateClearStatus(new Date(), expectedAssetClearStatus==AssetClearStatus.CLEAR);
		orderService.save(order);
	}

}
