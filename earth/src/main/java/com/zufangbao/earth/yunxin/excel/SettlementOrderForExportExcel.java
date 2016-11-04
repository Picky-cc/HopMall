package com.zufangbao.earth.yunxin.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.excel.SettlementOrderExcel;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;

@Component("settlementOrderForExportExcel")
public class SettlementOrderForExportExcel {

	@Autowired
	private SettlementOrderService settlementOrderService;

	public HSSFWorkbook createExcel(SettlementOrderQueryModel settlementOrderQueryModel) {

		List<SettlementOrder> settlementOrderList = settlementOrderService.getSettlementOrderListBy(settlementOrderQueryModel, null, 0, 0);

		List<SettlementOrderExcel> settlementOrderExcels = transferSettlementToExcelList(settlementOrderList);
		ExcelUtil<SettlementOrderExcel> excelUtil = new ExcelUtil<SettlementOrderExcel>(SettlementOrderExcel.class);

		return excelUtil.exportDataToHSSFWork(settlementOrderExcels, "结清单详情");
	}

	private List<SettlementOrderExcel> transferSettlementToExcelList(List<SettlementOrder> settlementOrderList) {

		List<SettlementOrderExcel> settlementOrderExcelList = new ArrayList<SettlementOrderExcel>();
		transferDataToExcel(settlementOrderList, settlementOrderExcelList);

		return settlementOrderExcelList;
	}

	private void transferDataToExcel(List<SettlementOrder> settlementOrderList,
			List<SettlementOrderExcel> settlementOrderExcelList) {
		for (SettlementOrder settlementOrder : settlementOrderList) {
			SettlementOrderExcel settlementOrderExcel = new SettlementOrderExcel();
			settlementOrderExcel.setSettleOrderNo(settlementOrder.getSettleOrderNo());
			settlementOrderExcel.setRepaymentNo(settlementOrder.getAssetSet().getSingleLoanContractNo());
			settlementOrderExcel.setRecycleDate(settlementOrder.getAssetSet().getAssetRecycleDate());
			settlementOrderExcel.setDueDate(settlementOrder.getDueDate());
			settlementOrderExcel.setAppId(settlementOrder.getAssetSet().getContract().getApp().getAppId());
			settlementOrderExcel.setPrincipalAndInterestAmount(settlementOrder.getAssetSet().getAssetInitialValue());
			settlementOrderExcel.setOverdueDays(settlementOrder.getOverdueDays());
			settlementOrderExcel.setOverduePenalty(settlementOrder.getOverduePenalty());
			settlementOrderExcel.setModifyTime(settlementOrder.getLastModifyTime());
			settlementOrderExcel.setSettlementAmount(settlementOrder.getSettlementAmount());
			settlementOrderExcel.setSettlementStatus(settlementOrder.getAssetSet().getSettlementStatus());
			settlementOrderExcel.setComment(settlementOrder.getComment());
			settlementOrderExcelList.add(settlementOrderExcel);
		}
	}

}
